package com.gs.train.project

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.flink.api.common.functions.{AggregateFunction, RuntimeContext}
import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.api.java.tuple.{Tuple, Tuple1}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.elasticsearch.{ElasticsearchSinkFunction, RequestIndexer}
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink
import org.apache.flink.util.Collector
import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.Requests
import org.slf4j.LoggerFactory

import scala.collection.mutable.ListBuffer

/**
  * Description:使用flink实时统计热门商品 数据写入到es
  * 功能：最近1分钟商品热度,5s统计一次,取Top10
  * Created with guoshuai
  * date 2019/10/21 13:40
  */
object LogAnalysis2Es {

    //输入数据格式(用户id,商品id,商品类别id,用户行为,时间/ms时间戳)
    case class UserBehavior(userId: Long, itemId: Long, categoryId: Int, behavior: String, timestamp: Long)

    // 输出数据样例类(商品id,窗口闭合时间,浏览量)
    case class ItemViewCount(itemId: Long, windowEnd: Long, count: Long)

    val logger = LoggerFactory.getLogger("LogAnalysis")
    val sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    def main(args: Array[String]): Unit = {
        //引入开发环境
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        //设置事件时间作为flink的基准时间
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        //设置重启策略 重启5次,中间间隔60s
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5,60000))

        import org.apache.flink.api.scala._
        //引入socket的数据源
        val dataSource: DataStream[String] = env.socketTextStream("localhost", 8001)
        val resultData = dataSource.map(x => {//数据预处理
            val linearray = x.split(",")
            UserBehavior(linearray(0).toLong, linearray(1).toLong, linearray(2).toInt, linearray(3), linearray(4).toLong)
        }).assignAscendingTimestamps(_.timestamp * 1000)//快速生成水印
          .filter(_.behavior == "pv") //保留pv
          .keyBy("itemId") //按照商品id分流
          .timeWindow(Time.minutes(1), Time.seconds(5))//最近一分钟窗口 5秒钟滑动一次
          .aggregate(new CountAgg(), new WindowResultFunction())
          .keyBy("windowEnd")
          .process(new TopNHotItems(10))


        //数据Sink写入到ES
        val httpHosts = new java.util.ArrayList[HttpHost]
        httpHosts.add(new HttpHost("192.168.94.41", 9200, "http"))
        val esSinkBuilder = new ElasticsearchSink.Builder[String](
            httpHosts, new ElasticsearchSinkFunction[String] {

                def createIndexRequest(element: String): IndexRequest = {
                    val json = new java.util.HashMap[String, Any]
                    val splits = element.split("\t")

                    if (splits.length > 2) {
                        //0为null 从1开始 1,12596,3  2,25503,2  3,2123538,2
                        println("No1: "+splits(1) + " No2: " + splits(2) + " No3: " + splits(3))
                        var i = 0
                        for (s <- splits) {
                            if (i == 0) {
                                print("")
                            } else
                                json.put("rank_No" + s"$i", s)
                            i += 1
                        }
                    }

                    //保存到ES中的id
                    val id = System.nanoTime().toString
                    Requests.indexRequest()
                      .index("loganalysis_project") //无需提前创建es,但必须小写
                      .`type`("traffic")
                      .id(id)
                      .source(json)
                }

                override def process(t: String, runtimeContext: RuntimeContext, requestIndexer: RequestIndexer): Unit = {
                    requestIndexer.add(createIndexRequest(t))
                }
            }
        )

        //设置要为每个批量请求缓冲的最大操作数
        esSinkBuilder.setBulkFlushMaxActions(1)
        resultData.addSink(esSinkBuilder.build) //.setParallelism(5)
        //resultData.print()
        env.execute("LogAnalysis")
    }


    /**
      * 自己实现的聚合函数 实现累计
      */
    class CountAgg extends AggregateFunction[UserBehavior, Long, Long] {
        override def add(value: UserBehavior, accumulator: Long): Long = {
            //println("value"+UserBehavior +" accumulator: "+accumulator) //累加器 accumulator:累计值
            accumulator + 1
        }

        override def createAccumulator(): Long = 0L

        override def getResult(accumulator: Long): Long = accumulator

        override def merge(a: Long, b: Long): Long = a + b

    }

    // 自定义实现Window Function，输出ItemViewCount格式
    class WindowResultFunction extends WindowFunction[Long, ItemViewCount, Tuple, TimeWindow] {
        override def apply(key: Tuple, window: TimeWindow, input: Iterable[Long], out: Collector[ItemViewCount]): Unit = {
            val itemId: Long = key.asInstanceOf[Tuple1[Long]].f0
            val count = input.iterator.next()
            out.collect(ItemViewCount(itemId, window.getEnd, count))
        }
    }


    // 自定义实现ProcessFunction 低水平的api 便于扩展,用于实现高级功能
    // 主要提供定时器的功能
    class TopNHotItems(topSize: Int) extends KeyedProcessFunction[Tuple, ItemViewCount, String] {

        // 定义状态ListState
        private var itemState: ListState[ItemViewCount] = _

        override def open(parameters: Configuration): Unit = {
            super.open(parameters)
            // 命名状态变量的名字和类型
            val itemStateDesc = new ListStateDescriptor[ItemViewCount]("itemState", classOf[ItemViewCount])
            itemState = getRuntimeContext.getListState(itemStateDesc)
        }

        override def processElement(i: ItemViewCount, context: KeyedProcessFunction[Tuple, ItemViewCount, String]#Context, collector: Collector[String]): Unit = {
            itemState.add(i)
            // 注册定时器，触发时间定为 windowEnd + 1，触发时说明window已经收集完成所有数据
            context.timerService.registerEventTimeTimer(i.windowEnd + 1)
        }

        // 定时器触发操作，从state里取出所有数据，对数据排序取TopN，输出
        override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Tuple, ItemViewCount, String]#OnTimerContext, out: Collector[String]): Unit = {
            // 获取所有的商品点击信息
            val allItems: ListBuffer[ItemViewCount] = ListBuffer()
            import scala.collection.JavaConversions._
            for (item <- itemState.get) {
                allItems += item
            }
            itemState.clear()

            // 按照点击量从大到小排序，选取TopN
            val sortedItems = allItems.sortBy(_.count)(Ordering.Long.reverse).take(topSize)

            // 将排名数据格式化，便于打印输出
            val result: StringBuilder = new StringBuilder
            for (i <- sortedItems.indices) {
                val currentItem: ItemViewCount = sortedItems(i)
                result.append("\t").append(i + 1)
                  .append("|shop_id:")
                  .append(currentItem.itemId)
                  .append("|statis_time:")
                  .append(sourceFormat.format(new Date(currentItem.windowEnd)))
                  .append("|pv_number:")
                  .append(currentItem.count)
            }
            // 控制输出频率
            Thread.sleep(1000)

            println("resultData:" + result.toString()) //打印最终排序结果
            out.collect(result.toString)
        }
    }

}