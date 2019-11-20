package com.guoshuai.realtime

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util
import java.util.Properties

import com.guoshuai.utils.{DBProper, InitPropertiesUtil}
import net.minidev.json.JSONObject
import net.minidev.json.parser.JSONParser
import org.apache.flink.configuration
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.SimpleStringSchema


/**
  * Description  Flink 接入kafka数据,简单预处理后写到 mysql, 批量写入
  * Created with guoshuai 
  * date 2019/11/19 14:43
  **/
object Kafka2Mysql {

    case class kakouData(passTime: String, licenseType: String, licenseNumber: String, pointId: String)


    lazy val kafkaProp: Properties = InitPropertiesUtil.initKafkaProp
    lazy val basicProp: Properties = InitPropertiesUtil.initBasicProp
    lazy val businessProp: Properties = InitPropertiesUtil.initBusinessProp

    var kakouList: util.ArrayList[kakouData] = new util.ArrayList[kakouData]()
    var flagTime: Long = System.currentTimeMillis()

    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment

        //数据质量不高 先使用ProcessingTime
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
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

        //获取批量数据,使用一分钟窗口
        dataStream.map(x => getRecord(x)).map(x => ToKakouData(x))
			.addSink(new WriteToMysql)

        env.execute("kafka2mysql")
    }


    /**
      * Description  格式转换
      * Param [tuple]
      * return com.guoshuai.realtime.Kafka2Phoenix.kakouData
      **/
    def ToKakouData(tuple: (String, String, String, String)): kakouData = {
        kakouData(tuple._2, tuple._3, tuple._4, tuple._1)
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
            (pointId, passTime, licenseType, licenseNum)
        } catch {
            case e: Exception => println("***************** getRecord jsonParser error: " + e.getMessage)
                (null, null, null, null)
        }
    }

    /**
      * 自定义Mysql sink
      */
    class WriteToMysql extends RichSinkFunction[kakouData] {

        var conn: Connection = _
        var ppst: PreparedStatement = _


        override def open(parameters: configuration.Configuration): Unit = {
            Class.forName("com.mysql.jdbc.Driver")
            conn = DriverManager.getConnection("连接", "用户名", "密码")
            val sql = s"insert ignore into flink_to_mysql values (?,?,?,?)"
            println(s"$conn  begin insert mysql .. ")
            ppst = conn.prepareStatement(sql)
        }


        override def close(): Unit = {
            super.close()
            if (conn != null) conn.close
            if (ppst != null) ppst.close
        }

        /**
          * invoke 一直没有触发 invoke
          *
          * @param value
          * @param context
          */
        override def invoke(value: kakouData, context: SinkFunction.Context[_]): Unit = {
            try {
                kakouList.add(value)

                if (kakouList.size() % 20000 == 0 || System.currentTimeMillis() - flagTime > 10000) {
                    for (i <- 0 until kakouList.size()) {
                        ppst.setString(1, kakouList.get(i).passTime)
                        ppst.setString(2, kakouList.get(i).licenseType)
                        ppst.setString(3, kakouList.get(i).licenseNumber)
                        ppst.setString(4, kakouList.get(i).pointId)
                        ppst.addBatch()
                    }

                    println("begin executeBatch ...")
                    val count = ppst.executeBatch() //批量执行
                    ppst.executeBatch()
                    println(s" add batch ${count}")

                    kakouList.clear()
                    flagTime = System.currentTimeMillis()
                }
            } catch {
                case e: Exception => e.getMessage
                    println("insert mysql error " + e.getMessage)
            }

        }
    }

}
