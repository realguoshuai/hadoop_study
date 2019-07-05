package com.guoshuai.mtdap3.flink.cep

import java.util

/*import java.lang.Double
import java.lang.String*/

import org.apache.flink.cep.{PatternSelectFunction, PatternStream}

import org.apache.flink.cep.CEP
import org.apache.flink.cep.pattern.Pattern

import org.apache.flink.cep.pattern.conditions.SimpleCondition
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment


/**
  * Description  测试cep
  * Created with guoshuai 
  * date 2019/7/4 13:37
  **/
object FlinkCEP {

    case class Event(licenseType: String, licenseNum: String, speed: Double)


    def main(args: Array[String]): Unit = {

        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val input: DataStream[Event] = env.fromElements(
            new Event("VEHPLATETYPE99", "浙A12345", 3.0),
            new Event("VEHPLATETYPE99", "浙A12345", 32.2),
            new Event("VEHPLATETYPE02", "浙A12345", 63.2),
            new Event("VEHPLATETYPE01", "浙A12345", 26.2),
            new Event("VEHPLATETYPE02", "浙A12345", 2.2),
            new Event("VEHPLATETYPE02", "浙A12345", 122.2),
            new Event("VEHPLATETYPE01", "浙A12345", 22.2),
            new Event("VEHPLATETYPE99", "浙A12345", 21.2),
            new Event("VEHPLATETYPE02", "浙A12345", 252.2)
        )
        //设置匹配规则


        val pattern: Pattern[Event, Event] = Pattern.begin[Event]("start")
          .subtype(classOf[Event])
          .where(new SimpleCondition[Event] {
              override def filter(t: Event): Boolean = {
                  val flag = if (t.speed > 50) true else false
                  flag
              }
          }).times(2)
        //{start=[Event(VEHPLATETYPE02,浙A12345,63.2), Event(VEHPLATETYPE02,浙A12345,122.2)]}


        val patternStream: PatternStream[Event] = CEP.pattern(input, pattern)
        val result = patternStream.select(new PatternSelectFunction[Event, String] {
            override def select(pattern: util.Map[String, util.List[Event]]): String = {
                try {
                    val map = pattern.keySet().iterator()
                    var event = ("", "", 0.0)
                    while (map.hasNext) {
                        val key = map.next
                        val speed: Double = pattern.get(key).get(1).speed
                        event = (pattern.get(key).get(1).licenseType, pattern.get(key).get(1).licenseType, speed)
                        println(key + ":" + pattern.get(key).get(1)) //start:Event(VEHPLATETYPE02,浙A12345,122.2)
                        event
                    }
                    //event._3.toString()
                    event.toString() //(VEHPLATETYPE02,VEHPLATETYPE02,252.2)
                } catch {
                    case e: Exception => "1"
                }
            }
        })


        result.print()
        env.execute()
    }
}
