package com.guoshuai.mtdap.hwdemo.flinkstream.main

import java.io.{BufferedInputStream, InputStream}
import java.util.Properties

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, createTypeInformation}
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * Created with IDEA  
  * author 郭帅 
  * date 11:46 2019/1/16
  * 从文件中读取数据  将两个输入流union    按照姓名,性别分组 使用滑动窗口 聚合 ,输出Tuple(String,String,Int)
  **/

object FlinkReadFileStream {

    def main(args: Array[String]): Unit = {

        val filePath1 = initBaseProp.getProperty("file1")
        val filePath2 = initBaseProp.getProperty("file2")

        val windowTime = initBaseProp.getProperty("windowTime")

        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        env.setParallelism(1)

        /*将两个文件将两个流联合成一个流*/
        val unionStream = if (filePath1!=null && filePath2!=null) {
            val firstStream = env.readTextFile(filePath1)
            val secondStream = env.readTextFile(filePath2)
            firstStream.union(secondStream)
        }else{
            println("ERROR :  filePath maybe null")
            null
        }
        /*这一段并不会执行  直到调用执行器*/
        unionStream
            .map(x=>getRecord(x))
            .assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks[UserRecord] {
                override def checkAndGetNextWatermark(lastElement: UserRecord, extractedTimestamp: Long): Watermark = {
                    new Watermark(System.currentTimeMillis()-60)
                }

                override def extractTimestamp(element: UserRecord, previousElementTimestamp: Long): Long = {
                    System.currentTimeMillis()
                }
            })
          .filter(_.name!="GuoYijun")
          /*分组  keyBy(0) 指定整个tuple作为key*/
            .keyBy("name","sexy")
            //.keyBy("shoppingTime")
          /*窗口 使用滚动窗口,滑动窗口*/
            .window(TumblingEventTimeWindows.of(Time.seconds(windowTime.toInt)))
          /*聚合 reduce*/
            .reduce((e1,e2)=>UserRecord(e1.name,e1.sexy,e1.shoppingTime+e2.shoppingTime))
          .print()

        env.execute("FlinkReadFileStream")
    }

    /**
     * Description 获取记录 并对结果进行处理
     * Param [line]
     * return pojo UserRecord
     **/
    def getRecord(line: String): UserRecord = {
        val elems = line.split(",")
        assert(elems.length == 3)
        val name = elems(0)
        val sexy = elems(1)
        val time = elems(2).toInt
        UserRecord(name, sexy, time)
    }

    def initBaseProp: Properties = {
        val prop: Properties = new Properties
        val in: InputStream = getClass.getResourceAsStream("/base.properties")
        if (in == null) {
            println("ERROR : base's properties init failed in is null")
        }
        prop.load(new BufferedInputStream(in))
        prop
    }

    /*内置pojo类*/
    case class UserRecord(name: String, sexy: String, shoppingTime: Int)

    private class Record2TimestampExtractor extends AssignerWithPunctuatedWatermarks[UserRecord] {

        override def extractTimestamp(element: UserRecord, previousTimestamp: Long): Long = {
            System.currentTimeMillis()
        }
        def checkAndGetNextWatermark(lastElement: UserRecord, extractedTimestamp: Long): Watermark = {
            new Watermark(extractedTimestamp - 1)
        }
    }


}
