package com.guoshuai.mtdap3.flink.streamJoin

import java.lang
import java.text.SimpleDateFormat

import org.apache.flink.api.common.functions.CoGroupFunction
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector
/**
  * Description  Flink Table 实现双流join
  * Created with guoshuai 
  * date 2019/8/8 15:13
  **/
object FlinkStreamJoinDemo {

    /*股票交易*/
    case class StockTransaction(tx_time: String, tx_code: String, tx_value: Double)

    /*股票快照*/
    case class StockSnapshot(md_time: String, md_code: String, md_value: Double)


    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        //接收数据
        val dataStreamA = env.readTextFile("E:\\development\\test\\mtdap-flink\\src\\com\\guoshuai\\mtdap3\\flink\\streamJoin\\conf\\socketA.txt")
        val dataStreamB = env.readTextFile("E:\\development\\test\\mtdap-flink\\src\\com\\guoshuai\\mtdap3\\flink\\streamJoin\\conf\\socketB.txt")


        val simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

        val streamA = dataStreamA.map(x => {
            val splitsA = x.split(",")
            StockTransaction(splitsA(0), splitsA(1), splitsA(2).toDouble)
        }).assignAscendingTimestamps(x => simpleDataFormat.parse(x.tx_time).getTime)

        val StreamB = dataStreamB.map(x => {
            val splitsB = x.split(",")
            StockSnapshot(splitsB(0), splitsB(1), splitsB(2).toDouble)
        }).assignAscendingTimestamps(x => simpleDataFormat.parse(x.md_time).getTime)


        //双流join + 滑动窗口
        val joinStream = streamA.coGroup(StreamB)
          .where(_.tx_code).equalTo(_.md_code)
          .window(TumblingEventTimeWindows.of(Time.seconds(3)))

        /*apply 传入函数作参*/
        joinStream.apply(new userCustomInnerJoinFunction).name("inner join").print
        joinStream.apply(new userCustomInnerJoinFunction).name("left join").print
        joinStream.apply(new userCustomInnerJoinFunction).name("right join").print

        env.execute("flink table join test")
    }

    //InnerJoin
    class userCustomInnerJoinFunction extends CoGroupFunction[StockTransaction,
      StockSnapshot,
      (String, String, String, Double, Double, String)] {
        /*重写coGroup方法*/
        override def coGroup(t1: lang.Iterable[StockTransaction],
                             t2: lang.Iterable[StockSnapshot],
                             out: Collector[(String, String, String, Double, Double, String)]): Unit = {
            //TODO  将java的集合转成scalade 集合  scala操作集合高效 简洁
            import scala.collection.JavaConverters._
            val scalaT1 = t1.asScala.toList
            val scalaT2 = t2.asScala.toList

            /*Inner Join比较的是同一个key下,同一个时间窗口的数据 */
            if (scalaT1.nonEmpty && scalaT2.nonEmpty) {
                for (transaction <- scalaT1) {
                    for (snapshot <- scalaT2) {
                        out.collect(transaction.tx_code, transaction.tx_time, snapshot.md_time, transaction.tx_value, snapshot.md_value, "Inner Join Test")
                    }
                }
            }


        }
    }

    // left join

    class userCustomLeftJoinFunction extends CoGroupFunction[StockTransaction,
      StockSnapshot,
      (String, String, String, Double, Double, String)] {
        /*重写coGroup方法*/
        override def coGroup(t1: lang.Iterable[StockTransaction],
                             t2: lang.Iterable[StockSnapshot],
                             out: Collector[(String, String, String, Double, Double, String)]): Unit = {
            //TODO  将java的集合转成scalade 集合  scala操作集合高效 简洁
            import scala.collection.JavaConverters._
            val scalaT1 = t1.asScala.toList
            val scalaT2 = t2.asScala.toList

            /*Left Join比较的是同一个key下,同一个时间窗口的数据 */
            if (scalaT1.nonEmpty && scalaT2.isEmpty) {
                for (transaction <- scalaT1) {
                    out.collect(transaction.tx_code, transaction.tx_time, "", transaction.tx_value, 0, "Left Join Test")
                }
            }


        }
    }


    //right join
    class userCustomRightJoinFunction extends CoGroupFunction[StockTransaction,
      StockSnapshot,
      (String, String, String, Double, Double, String)] {
        /*重写coGroup方法*/
        override def coGroup(t1: lang.Iterable[StockTransaction],
                             t2: lang.Iterable[StockSnapshot],
                             out: Collector[(String, String, String, Double, Double, String)]): Unit = {
            //TODO  将java的集合转成scalade 集合  scala操作集合高效 简洁
            import scala.collection.JavaConverters._
            val scalaT1 = t1.asScala.toList
            val scalaT2 = t2.asScala.toList

            /*Right Join比较的是同一个key下,同一个时间窗口的数据 */
            if (scalaT1.isEmpty && scalaT2.nonEmpty) {
                for (snapshot <- scalaT2) {
                    out.collect(snapshot.md_code, "", snapshot.md_time, 0, snapshot.md_value, "Right Join Test")
                }
            }
        }
    }

}
