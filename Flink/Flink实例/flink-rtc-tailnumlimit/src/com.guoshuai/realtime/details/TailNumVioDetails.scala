package com.guoshuai.realtime.details

import java.util.{Date, Properties}

import com.enjoyor.mtdap3.utils.TimeHelper._
import com.enjoyor.mtdap3.utils.{InitPropertiesUtil, JedisClusterUtil, RegularExpressUtil, TimeHelper}
import net.minidev.json.JSONObject
import net.minidev.json.parser.JSONParser
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer010, FlinkKafkaProducer010}
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

import scala.collection.mutable.ArrayBuffer

/**
  * Description 违反车辆限行汇总
  * Created with guoshuai 
  * date 2019/6/17 9:52
  **/
object TailNumVioDetails {
    lazy val kafkaProp: Properties = InitPropertiesUtil.initKafkaProp
    lazy val basicProp: Properties = InitPropertiesUtil.initBasicProp
    lazy val businessProp: Properties = InitPropertiesUtil.initBusinessProp

    def main(args: Array[String]): Unit = {
        //获取执行容器
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        //env.setParallelism(1)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        //每隔10秒启动一个检查点
        env.enableCheckpointing(10000)
        //精确获取一次(默认值)
        env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
        //确保检查点之间有5秒的进度
        env.getCheckpointConfig.setMinPauseBetweenCheckpoints(5000)
        //检查点必须在3分钟内完成或被丢弃
        env.getCheckpointConfig.setCheckpointTimeout(180000)
        //同一时间只允许进行一个检查点
        env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)
        //设置checkpoints的保存目录
        env.setStateBackend(new FsStateBackend("hdfs:///flink/checkpoints"))

        //设置kafka参数
        val properties = new Properties()
        properties.setProperty("bootstrap.servers", kafkaProp.getProperty("bootstrap.servers"))
        properties.setProperty("security.protocol", kafkaProp.getProperty("security.protocol"))
        properties.setProperty("sasl.kerberos.service.name", kafkaProp.getProperty("sasl.kerberos.service.name"))
        properties.setProperty("group.id", kafkaProp.getProperty("group2.id"))
        //初始化kafka生产者
        val producer010 = new FlinkKafkaProducer010(kafkaProp.getProperty("target2.topic"),
            new SimpleStringSchema, properties)

        //初始化kafka消费者
        val consumer010 = new FlinkKafkaConsumer010(kafkaProp.getProperty("source.topic"),
            new SimpleStringSchema, properties)
        consumer010.setStartFromLatest() //从最新的offset开始消费

        //将kafka设为stream的source
        val dataStream = env.addSource(consumer010)

        val outputStream = dataStream
          .map(v => getRecord(v)) //(点位编码, 经过时间, 号牌类型, 号牌号码)
          .filter(_._1 != null) //过滤出错记录 (null,null,null,null)
          .flatMap(v => getKeyAreaByPoint(v)) //(区域编码,卡口编码,经过时间,号牌类型,号牌号码)
          .filter(!_._1.isEmpty) //过滤出错记录
          .map(v => recordProcess(v)) //(区域编码,号牌类型,号牌号码,限行规则(周一尾号限行(1和6),经过时间,过车点位)
          .assignTimestampsAndWatermarks(new TimestampExtractor(basicProp.getProperty("max.lagged.time").toInt)) //提取event-time
          .keyBy(0)
          .timeWindow(Time.seconds(basicProp.getProperty("job.interval").toInt))
          .reduce((v1, v2) => (v1._1, v1._2, v1._3, v1._4, v1._5, v1._6))
          .map(v => toJson(v)) //转换成JSON字符串
        //将结果写入kafka
        outputStream.addSink(producer010)
        env.execute(basicProp.getProperty("application.name"))
    }

    /**
      * 从卡口过车记录中提取需要的字段
      *
      * @param in 卡口过车记录
      * @return (点位, 经过时间, 号牌类型, 号牌号码)
      */
    def getRecord(in: String): (String, String, String, String) = {
        try {
            val jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE)
            val elems: JSONObject = jsonParser.parse(in).asInstanceOf[JSONObject]

            val pointId = elems.get(businessProp.getProperty("point.id")).toString //点位
            val passTime = elems.get(businessProp.getProperty("passing.time")).toString //经过时间
            val licenseType = elems.get(businessProp.getProperty("license.type")).toString //号牌类型
            val licenseNum = elems.get(businessProp.getProperty("license.number")).toString //号牌号码
            //判断当前时间是否限行  true 是 false 不是
            val flag = if (isTailNumberLimitTime(getTimeMillis(passTime))) true else false
            //车辆首次出现 过滤 ok
            //val isFirstCatchFlag = uniqueJudgeFlag(licenseType, licenseNum) //true  首次出现 false 出现过

            flag match {
                //case true => if(isFirstCatchFlag)(pointId, passTime, licenseType, licenseNum) else (null, null, null, null)
                case true => (pointId, passTime, licenseType, licenseNum)
                case false => (null, null, null, null)
            }
        } catch {
            case e: Exception => println("***************** getRecord jsonParser error: " + e.getMessage)
                (null, null, null, null)
        }
    }

    /** 主体计算函数
      * 将每条过车记录判断是否是违反限行车辆 (计算指标兼容违反限行车辆展示)
      *
      * @param v (区域编码,卡口编码,经过时间,号牌类型,号牌号码)
      * @return (区域编码,号牌类型,号牌号码,限行规则(周一尾号限行(1和6),经过时间,过车点位)
      */
    def recordProcess(v: (String, String, String, String, String)): (String, String, String, String, Long, String) = {
        try {

            //解决过车数据异常
            var passTime = getTimeMillis(v._3)
            if (passTime - System.currentTimeMillis() > 0) {
                passTime = System.currentTimeMillis()
            }

            val areaId = v._1
            val licenseType = v._4
            val licenseNum = v._5

            //TODO 每次都的调用 性能太低  需要修改
            var limitRules = ""
            val limitRulesArray = getLimitRules(passTime) //限行规则
            var limitOne: String = ""
            var limitTwo: String = ""
            for (i <- 0 to (limitRulesArray.length - 1)) {
                //getOrElse
               /* limitRulesArray(i)
                if (i == 0) limitOne = limitRulesArray(i).toString
                if (i == 1) limitTwo = limitRulesArray(i).toString*/
                i match{
                    case 0 => limitOne = limitRulesArray(i)/*.toString*/
                    case 1 => limitTwo = limitRulesArray(i)/*.toString*/
                }
            }
            limitRules = limitOne + "_" + limitTwo   //Some(2_7)
            //limitRules.substring(5,8)
            // ok
            //println("recordProcess result"+(areaId, licenseType, licenseNum, limitRules, passTime, v._2))
            //判断号牌是否违法 ok
            val flag = if (isTailNumberVio(licenseNum, passTime)) true else false
            val pointId = v._2
            flag match {
                case true => (areaId, licenseType, licenseNum, limitRules.substring(5,8), passTime, pointId)
                case false => (null, null, null, null, 0L, null)
            }
        } catch {
            case e: Exception => println("***************** getRecord jsonParser error: " + e.getMessage)
                (null, null, null, null, 0L, null)
        }

    }


    /**
      * 判断当前号牌是否违反尾号限行  调用JudgeLastTypeIsInt
      *
      * @param licenseNumStr 号牌号码
      * @return 违反true  正常false  默认false
      */
    def isTailNumberVio(licenseNumStr: String, passingTime: Long): Boolean = {
        val licenseNum = licenseNumStr
        val passTime = passingTime
        val vehLastNum = JudgeLastTypeIsInt(licenseNum) //ok String
        //拿到号牌号码的最后一个数字  跟redis作对比
        val limitRulesArray = getLimitRules(passTime) //限行规则
        var str = ""
        //测试正常
        for (i <- 0 to (limitRulesArray.length - 1)) {
            str = str + limitRulesArray(i) //12
        }
        // println("alllimitnumber is: "+str+ "     "+licenseNum+"    " +"tailnum  is : "+ vehLastNum)
        val flag = if (str.contains(vehLastNum)) true else false
        //println("is "+flag) //ok
        flag
    }

    /**
      * 判断当前时间段是否是限行时段
      *
      * @param passingTime 号牌号码
      * @return 是true  否false  根据需求添加(可能加上 设计时先搭好框架)
      */
    def isTailNumberLimitTimeFlag(passingTime: Long): Boolean = {

        val passTime = passingTime
        //调用工具类  2019-06-17 09:21:56 -> 对比 小时分钟 是否在7:00-21:00(可配置)
        val flag = if (isTailNumberLimitTime(passTime)) true else false
        flag
    }

    /**
      * Description 获取配置的限行规则   redis Hash(1:1_6;2:2_7;3:3_8;4:4_9;5:5_0)
      * Param [passingTime]
      * return Array[String] eg[1,6]
      **/
    def getLimitRules(passingTime: Long): Array[String] = {
        val passTime = passingTime
        val time = milliSecondToTimestampString(passTime)
        val flag = dayOfWeek(time).toString //返回数字 1->7
        //获取redis中的当前工作日的限行号码
        /*val getLimitRules: Option[String] = JedisClusterUtil.hget("mtdap3_limit_rule", flag)
        val limitNum = ArrayBuffer[String]()
        for (rules <- getLimitRules) {
            val element = rules.split("_")
            limitNum += (element(0))
            limitNum += (element(1))
        }*/
        val getLimitRules: String = JedisClusterUtil.hget("mtdap3_limit_rule", flag).toString
        val limitNum = ArrayBuffer[String]()
        val element = getLimitRules.split("_")
        limitNum += (element(0))
        limitNum += (element(1))
        limitNum.toArray
    }

    /**
      * 获取车辆号牌的最后一位数字
      *
      * @param licenseNumStr
      * @return String
      */
    def JudgeLastTypeIsInt(licenseNumStr: String): String = {
        val licenseNumber = licenseNumStr
        import java.util.regex.Pattern
        val regEx = "[^0-9]"
        val pattern = Pattern.compile(regEx)
        val m = pattern.matcher(licenseNumber)
        val result = m.replaceAll("").trim
        val vehLastNum = result.substring(result.length - 1)
        //println("-"+result.substring(result.length-1))
        vehLastNum
    }


    /**
      * 判断当前号牌是否正确识别
      *
      * @param licenseNum 号牌号码
      * @return
      */
    def licenseJudge(licenseNum: String): Boolean = {
        RegularExpressUtil.isValid(licenseNum)
    }

    /**
      * 将point_id转换为对应的area_id，由于存在着一对多的情况，所以返回的是一个数组
      * 限行区域是否是使用配置页的重点区域的 mtdap3_keyarea
      *
      * @param v (点位,经过时间,号牌类型,号牌号码)
      * @return Array[(区域编码,卡口编码,经过时间,号牌类型,号牌号码)]
      */
    def getKeyAreaByPoint(v: (String, String, String, String)): Array[(String, String, String, String, String)] = {
        //println("getrecord: "+v._1 +" " +v._2+" " +v._3+" " +v._4)
        //根据pointId获取areaId
        /*1) "360106000201_119069444435"
        2) "{\"area_name\":\"\xe6\xb5\x8b\xe8\xaf\x951\"," +
          "\"point_id\":\"360106000201\",\"in_out\":\"9\",\"area_id\":\"119069444435\"}"*/

        val pointAndArea = JedisClusterUtil.hkeys("mtdap3_keyarea")
        val areas = getAreaId(pointAndArea, v._1)

        val aBuffer = new ArrayBuffer[(String, String, String, String, String)]()
        val pointId = v._1
        //如果在redis中发现了缓存的区域信息，则将它返回
        for (areaId <- areas) {
            val opt = JedisClusterUtil.hget("mtdap3_keyarea", v._1 + "_" + areaId)
            if (opt.isDefined && !opt.get.equals("")) {
                val jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE)
                val jsonObj: JSONObject = jsonParser.parse(opt.get).asInstanceOf[JSONObject]
                val element = (jsonObj.get("area_id").toString, pointId, v._2, v._3, v._4)
                aBuffer += element
            } else {
                val element = (null, null, null, null, null)
                aBuffer += element
            }
        }

        aBuffer.toArray
    }

    /**
      * 利用Set集合来判断当前区域内的车辆是否具有唯一性
      *
      * @param areaId
      * @param licenseType
      * @param licenseNum
      * @return 新车1  已出现0
      */
    def areaUniqueJudge(areaId: String, licenseType: String, licenseNum: String): Int = {
        if (!RegularExpressUtil.isValid(licenseNum)) {
            0 //若不能识别合法车牌，则记为0
        } else {
            if (!JedisClusterUtil.exists("mtdap3_keyarealimit_" + areaId)) { //如果key不存在，则新建一个set集合
                JedisClusterUtil.sAdd("mtdap3_keyarealimit_" + areaId, licenseType + "," + licenseNum) //将当前车辆放进set集合中
                //JedisClusterUtil.expire("key_area_" + areaId, 8 * 60 * 60) //测试环境中暂时设置为8小时之后失效
                JedisClusterUtil.pexpireAt("mtdap3_keyarealimit_" + areaId, TimeHelper.timestampOfMidnight(new Date)) //设置key的过期时间为当日午夜
                1
            } else {
                if (JedisClusterUtil.isSetMember("mtdap3_keyarealimit_" + areaId, licenseType + "," + licenseNum)) {
                    0 //如果set集合中已经有当前车辆了，则不计数
                } else {
                    JedisClusterUtil.sAdd("mtdap3_keyarealimit_" + areaId, licenseType + "," + licenseNum)
                    1 //否则先放进新车辆，然后返回1
                }
            }
        }
    }

    /**
      * 返回一个区域编码的数组，由于一个点位可能对应多个区域，所以将多个区域放在Array中
      *
      * @param pointAndArea 包含点位_区域对应关系的迭代器
      * @param pointId      待匹配的点位
      * @return Array[String]
      */
    def getAreaId(pointAndArea: java.util.Iterator[String], pointId: String): Array[String] = {
        val areas = ArrayBuffer[String]()
        while (pointAndArea.hasNext) {
            val element = pointAndArea.next.split("_")
            if (element(0).equals(pointId)) {
                areas += element(1)
            }
        }
        areas.toArray
    }

    /**
      * 判断当前车辆是否首次出现  注意key
      *
      * @param licenseType 车辆类型
      * @param licenseNum  号牌号码
      * @return isNotFirstFindFlag 是:true,否:false
      */
    def uniqueJudgeFlag(licenseType: String, licenseNum: String): Boolean = {
        if (!JedisClusterUtil.exists("mtdap3_tail_num_vio_detail")) {
            AnyRef.synchronized { //为了防止多线程同时设置超时时间而设
                JedisClusterUtil.sAdd("mtdap3_tail_num_vio_detail", licenseType + licenseNum) //将当前车辆放进set集合中
                JedisClusterUtil.pexpireAt("mtdap3_tail_num_vio_detail", TimeHelper.timestampOfMidnight(new Date)) //设置key的过期时间为当日午夜
            }
            true
        } else {
            AnyRef.synchronized {
                //为了保证过期设置生效，在新记录到来时都检查一下
                if (JedisClusterUtil.ttl("mtdap3_tail_num_vio_detail").get == -1) {
                    JedisClusterUtil.pexpireAt("mtdap3_tail_num_vio_detail", TimeHelper.timestampOfMidnight(new Date)) //设置key在午夜时分过期
                }

                if (JedisClusterUtil.isSetMember("mtdap3_tail_num_vio_detail", licenseType + licenseNum)) {
                    false //如果set集合中已经有当前车辆了，则不计数
                } else {
                    JedisClusterUtil.sAdd("mtdap3_tail_num_vio_detail", licenseType + licenseNum)
                    true //否则先放进新车辆，然后返回true
                }
            }
        }
    }


    /**
      * 根据本地车规则判断当前车辆是否为本地车辆
      *
      * @param licenseNum 号牌号码
      * @return
      */
    def isLocalVehicle(licenseNum: String): Boolean = {
        val localVehicleFlag = JedisClusterUtil.get("local_vehicle").get.split(",") //将本地车辆标志放在redis中，可以避免配置文件中文编码的问题
        var isLocal = false

        for (flag <- localVehicleFlag) {
            if (licenseNum.contains(flag))
                isLocal = true
        }
        isLocal
    }

    /**
      * Description  转成json
      * Param (区域编码,号牌类型,号牌号码,限行规则(周一尾号限行(1和6),经过时间,过车点位)
      * return java.lang.Object
      **/
    def toJson(row: (String, String, String, String, Long, String)): String = {
        println("toJson: " + row._1 + " " + row._2 + " " + row._3 + " " + row._4 + " " + row._5 + " " + row._6)
        val obj: JSONObject = new JSONObject
        val passTime = milliSecondToTimestampString(row._5)
        obj.put("area_id", row._1)
        obj.put("license_type", row._2)
        obj.put("license_num", row._3)
        obj.put("limit_rules", row._4)
        obj.put("passing_time", passTime)
        obj.put("point_id", row._6)

        println("---" + obj.toJSONString)
        obj.toJSONString
    }
}
