package com.guoshuai.mtdap.hwdemo.flinkkafka.demo

import java.util.Properties

import com.guoshuai.mtdap.utils.InitPropertiesUtil
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer010, FlinkKafkaProducer010}
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

/*实时计算编译阶段出错调用这个包*/

import org.apache.flink.streaming.api.scala.createTypeInformation

/**
  * Created with IDEA  
  * author 郭帅 
  * date 9:57 2019/1/16
  *
  * Flink 实现的写入流
  **/
object WriteIntoKafka {
    lazy val kafkaProp = InitPropertiesUtil.initKafkaProp

    def main(args: Array[String]): Unit = {

        val prop = new Properties()
        prop.setProperty("bootstrap.servers", kafkaProp.getProperty("bootstrap.servers"))
        val sourceTopic = kafkaProp.getProperty("source.topic")
        val targetTopic = kafkaProp.getProperty("target.topic")
        val product010 = new FlinkKafkaProducer010(targetTopic, new SimpleStringSchema(), prop)
        val consumer010 = new FlinkKafkaConsumer010(sourceTopic, new SimpleStringSchema(), prop)

        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(3)

        //Source -> Sink 最简单实现
        env.addSource(consumer010).addSink(product010)

        //自定义输入流 将数据发送到kafka
        /* env.addSource(new SourceFunction[String] {
             var isRunning =true
             var i = 0

             override def run(ctx: SourceFunction.SourceContext[String]): Unit = {
                 while(isRunning){
                     ctx.collect("element: "+i) //1,2,3,4,5,
                     i+=1
                     Thread.sleep(1000)
                 }
             }
             override def cancel(): Unit = {
                 isRunning=false
             }
         }).addSink(product010)*/

        //对输入流做处理 再发送到kafka
        env.addSource(consumer010)
        env.execute()
    }

}
