package com.guoshuai.realtime

import java.util.Properties

import com.guoshuai.utils._
import net.minidev.json.JSONObject
import net.minidev.json.parser.JSONParser
import org.apache.flink.api.common.functions.RichReduceFunction
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer010, FlinkKafkaProducer010}
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
import org.apache.flink.util.Collector

/**
  * Description  测试增量 窗口函数  reduce + process
  * 窗口函数是触发器确定窗口数据全部到达后开始执行的函数
  * 代码基于flink 1.4.0
  * Created with guoshuai 
  * date 2019/11/21 08:36
  **/
object WindowFunTrain {
    lazy val kafkaProp: Properties = InitPropertiesUtil.initKafkaProp
    lazy val basicProp: Properties = InitPropertiesUtil.initBasicProp
    lazy val businessProp: Properties = InitPropertiesUtil.initBusinessProp

    def main(args: Array[String]): Unit = {
        //获取开发环境
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
        //设置checkpoints的保存目录 状态管理
        env.setStateBackend(new FsStateBackend("hdfs:///flink/checkpoints"))
        //env.setStateBackend(new FsStateBackend("file:///d:/sr-fakevehicle-checkpoint"))


        //设置kafka参数
        val properties = new Properties()
        properties.setProperty("bootstrap.servers", kafkaProp.getProperty("bootstrap.servers"))
        //kerberos安全认证
        properties.setProperty("security.protocol", kafkaProp.getProperty("security.protocol"))
        properties.setProperty("sasl.kerberos.service.name", kafkaProp.getProperty("sasl.kerberos.service.name"))
        properties.setProperty("group.id", kafkaProp.getProperty("group.id"))
        //初始化kafka生产者
        val producer010 = new FlinkKafkaProducer010(kafkaProp.getProperty("target.topic"),
            new SimpleStringSchema, properties)


        //初始化kafka消费者
        val consumer010 = new FlinkKafkaConsumer010(kafkaProp.getProperty("source.topic"),
            new SimpleStringSchema, properties)

        consumer010.setStartFromLatest() //从最新的offset开始消费

        //将kafka设为stream的source
        val dataStream = env.addSource(consumer010)
		//本地测试用的socket
        //val dataStream = env.socketTextStream("localhost", 8001)
        val outputStream = dataStream.map(getRecord(_))
          .filter(_._1 != null)
          .keyBy(0)
          .timeWindow(Time.minutes(5))
          //reduce + process 实现增量+灵活性处理
          .reduce(new reduceTrain, new processTrain)

        //将结果写入kafka
        //outputStream.addSink(producer010)
        outputStream.print()
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

    /**
      * 简单测试 reduce 数据原样输出    窗口增量计算
      */
    class reduceTrain extends RichReduceFunction[(String, String, String, String)] {
        override def reduce(t: (String, String, String, String), t1: (String, String, String, String)): (String, String, String, String) = {
            (String, String, String, String)
        }
    }

    /**
      * 简单测试 数据原样输出   利用reduce增量下 使用更灵活的process函数处理数据
      */
    class processTrain extends ProcessWindowFunction[(String, String, String, String), (String, String, String, String), Tuple, TimeWindow] {
        override def process(key: Tuple, context: Context, elements: Iterable[(String, String, String, String)], out: Collector[(String, String, String, String)]): Unit = {
            val tmp: Map[String, Iterable[(String, String, String, String)]] = elements.groupBy(_._1)

            for (element <- tmp) {
                val key: String = element._1
                val values: Iterable[(String, String, String, String)] = element._2

                var passTime = ""
                var duration = ""
                var unblock = ""
                var freq = ""
                for (value <- values) {
                    passTime = value._2
                    duration = value._3
                    unblock = value._4
                    freq = value._1
                }
                out.collect(passTime, duration, unblock, freq)
            }
        }
    }

}

