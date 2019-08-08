package com.guoshuai.realtime.details

import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks
import org.apache.flink.streaming.api.watermark.Watermark

/**
  * Description  自定义时间戳和水印  传参:计算等待最大时延
  * Created with guoshuai 
  * date 2019/3/21 9:38
  **/
class TimestampExtractor(val maxLaggedTime: Int) extends AssignerWithPunctuatedWatermarks[(String, String, String, String, Long, String)] with Serializable {
    //Event-time的最大值,初始时为0L
    var currentMaxTimestamp = 0L

    override def checkAndGetNextWatermark(lastElement: (String, String, String, String, Long, String), extractedTimestamp: Long): Watermark = {
        /* 水印时间 = 当前流上时间戳-最大时延 */
        val watermark = new Watermark(currentMaxTimestamp - maxLaggedTime * 1000)
        watermark
    }

    //参数是根据上游Tuple决定的
    override def extractTimestamp(element:(String, String, String, String, Long, String), previousElementTimestamp: Long): Long = {
        /*拿到最近一次的过车时间 作为时间戳*/
        val timestamp = element._5
        //判断并保存event-time的最大值
        currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp)
        //println("do extractTimestamp " + timestamp)
        timestamp
    }
}
