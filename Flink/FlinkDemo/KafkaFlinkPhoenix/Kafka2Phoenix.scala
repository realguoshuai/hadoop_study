package com.guoshuai.realtime

import java.sql.{Connection, PreparedStatement}
import java.util
import java.util.Properties

import com.guoshuai.utils.{InitPropertiesUtil, LoginUtil, PhoenixConnPool}
import net.minidev.json.JSONObject
import net.minidev.json.parser.JSONParser
import org.apache.flink.configuration
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
import org.apache.hadoop.conf.Configuration


/**
  * Description  kafka写数据到phoenix,用于实时ETL   测试代码(2w条或10s中提交一次)
  * Created with guoshuai
  * date 2019/11/14 9:23
  **/
object Kafka2Phoenix {

    case class kakouData(passTime: String, licenseType: String, licenseNumber: String, pointId: String)

    lazy val kafkaProp: Properties = InitPropertiesUtil.initKafkaProp
    lazy val basicProp: Properties = InitPropertiesUtil.initBasicProp
    lazy val businessProp: Properties = InitPropertiesUtil.initBusinessProp
    var count: Long = 1
    var flagTime: Long = System.currentTimeMillis()

    //@AtomicInteger //加不加没影响
    var kakouList: util.ArrayList[kakouData] = new util.ArrayList[kakouData]()

    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment

        //数据质量不高 先使用ProcessingTime
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        //每隔1分钟启动一个检查点
        env.enableCheckpointing(60000)
        //精确获取一次(默认值)
        env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
        //确保检查点之间有30秒的进度
        env.getCheckpointConfig.setMinPauseBetweenCheckpoints(30000)
        //检查点必须在10分钟内完成或被丢弃
        env.getCheckpointConfig.setCheckpointTimeout(600000)
        //同一时间只允许进行一个检查点
        env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)
        //设置checkpoints的保存目录
        env.setStateBackend(new FsStateBackend("hdfs:///flink/checkpoints"))

        //设置kafka参数
        val properties = new Properties()
        properties.setProperty("bootstrap.servers", kafkaProp.getProperty("bootstrap.servers"))
        properties.setProperty("security.protocol", kafkaProp.getProperty("security.protocol"))
        properties.setProperty("sasl.kerberos.service.name", kafkaProp.getProperty("sasl.kerberos.service.name"))
        properties.setProperty("group.id", kafkaProp.getProperty("group.id"))

        //初始化kafka消费者
        val consumer010 = new FlinkKafkaConsumer010(kafkaProp.getProperty("source.topic"),
            new SimpleStringSchema, properties)

        consumer010.setStartFromLatest() //从最新的offset开始消费
        //将kafka设为stream的source
        val dataStream = env.addSource(consumer010)

        //调用安全认证
        LoginKerberos()

        val a = new WritePhoenixSink //使用同一个对象,避免每条数据都new 一个对象
        //临时测试使用socket
        //val dataStream = env.socketTextStream("localhost", 8001)
        println("-------------------------------------------------------------")
        //dataStream.print() //上下两个print都会输出
        val result = dataStream.map(getRecord(_))
          .map(ToKakouData(_))

        //result.print()
        result.addSink(a)
        env.execute("Kafka2phoenix")
    }

    /**
      * Huawei kerberos 安全认证  多次调用? 1:n
      */
    def LoginKerberos(): Unit = {
        val userPrincipal = "xxxxx"
        val userKeytabPath = "/xx/xx/xx/user.keytab"
        val krb5ConfPath = "/xx/xx/xx/krb5.conf"
        val hadoopConf: Configuration = new Configuration()
        LoginUtil.login(userPrincipal, userKeytabPath, krb5ConfPath, hadoopConf)
        println("LoginKerberos ok...")
    }

    /**
      * Description   格式转换
      * Param [tuple]
      * return com.guoshuai.realtime.Kafka2Phoenix.kakouData
      **/
    def ToKakouData(tuple: (String, String, String, String)): kakouData = {
        kakouData(tuple._2, tuple._3, tuple._4, tuple._1)
    }

    /**
      * 自定义输出到phoenix  每批 2w或 5秒后  提交一次
      */
    class WritePhoenixSink extends RichSinkFunction[kakouData] {

        private var count = 0L

        /**
          * open方法是初始化方法，会在invoke方法之前执行，执行一次
          */
        override def open(parameters: configuration.Configuration): Unit = super.open(parameters)

        /**
          * invoke实际调用函数
          */
        override def invoke(kkData: kakouData, context: SinkFunction.Context[_]): Unit = {
            //下面的控制台打印不出来了 在Stdout中查看
            try {
                kakouList.add(kkData)

                if (kakouList.size() % 20000 == 0 || System.currentTimeMillis() - flagTime > 10000) {
                    val phoenixSql: String = "upsert into  mtdap2.flink_to_phoenix_test values (to_date(?),?, ?, ?)"
                    var conn: Connection = null
                    var ppst: PreparedStatement = null

                    conn = PhoenixConnPool.getConnection()
                    ppst = conn.prepareStatement(phoenixSql)
                    conn.setAutoCommit(false)
                    for (i <- 0 until kakouList.size()) {
                        ppst.setString(1, kakouList.get(i).passTime)
                        ppst.setString(2, kakouList.get(i).licenseType)
                        ppst.setString(3, kakouList.get(i).licenseNumber)
                        ppst.setString(4, kakouList.get(i).pointId)
                        ppst.addBatch()
                    }
                    ppst.executeBatch()
                    conn.commit()

                    kakouList.clear()
                    flagTime = System.currentTimeMillis()
                }
            } catch {
                case e: Exception => e.getMessage
                    println("insert phoenix error " + e.getMessage)
            }
        }
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
            //count += 1
            //println(s"${count}   " + pointId + "  " + passTime)
            (pointId, passTime, licenseType, licenseNum)
        } catch {
            case e: Exception => println("***************** getRecord jsonParser error: " + e.getMessage)
                (null, null, null, null)
        }
    }


    /**
      * 将计算结果转换为JSON字符串
      *
      * @param row (点位, 经过时间, 号牌类型, 号牌号码)
      * @return
      */
    def toJson(row: (String, String, String, String)): String = {
        val obj: JSONObject = new JSONObject

        obj.put("point_id", row._1)
        obj.put("pass_time", row._2)
        obj.put("license_type", row._3)
        obj.put("license_num", row._4)
        obj.toJSONString
    }
}
