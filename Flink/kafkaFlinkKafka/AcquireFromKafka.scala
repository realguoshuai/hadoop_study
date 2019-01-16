package com.guoshuai.mtdap.hwdemo.flinkkafka.demo

import java.util.Properties

import com.guoshuai.mtdap.utils.InitPropertiesUtil
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

/*实时计算编译出错调用这个包*/
import org.apache.flink.streaming.api.scala.createTypeInformation

/**
  * Created with IDEA  
  * author 郭帅 
  * date 9:17 2019/1/16
  * 从kafka 获取数据源
  **/
object AcquireFromKafka {
    lazy val kafkaProp = InitPropertiesUtil.initKafkaProp

    /**
     * Description  从配置文件中获取kafka 配置  并简单测试flink是否可以接收到
     **/
    def main(args: Array[String]): Unit = {
        val  topic =kafkaProp.getProperty("source.topic")
        val prop = new Properties()
        prop.setProperty("bootstrap.servers",kafkaProp.getProperty("bootstrap.servers"))

        /*初始化消费者  new FlinkKafkaConsumer010 (topic,反序列化格式:与kafka对应,prop配置文件)
         * String topic, DeserializationSchema<T> valueDeserializer, Properties props
         */
        val consumer010 = new FlinkKafkaConsumer010(topic,new SimpleStringSchema(),prop)
        /*设置消费点  最近 最远 */
        consumer010.setStartFromEarliest()
        //consumer010.setStartFromLatest()
        /*flink 测试,打印出来*/

        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        val dataStream = env.addSource(consumer010)

        /*打印出来 */
        dataStream.print()

        env.execute()
    }
}
