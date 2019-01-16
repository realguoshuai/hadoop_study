package com.guoshuai.mtdap.hwdemo.checkpoint

import java.util

import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
/*隐式转换   使用scala 高级特性*/
/*import org.apache.flink.streaming.api.scala._
* import org.apache.flink.api.scala._
* */

/*import org.apache.flink.api.scala.createTypeInformation*/
import org.apache.flink.streaming.api.scala.createTypeInformation
/**
  * Created with IDEA  
  * author 郭帅 
  * date 14:12 2019/1/15
  * 执行入口  在flink程序中集成检查点
  **/
object DemoFlinkEventTimeAndChkMain {
    private var total = 0L
    def main(args: Array[String]): Unit = {
        val checkPath = ParameterTool.fromArgs(args).get("chkPath")
        if (checkPath == null) System.exit(1)

        /*flink开发环境*/
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        /*env添加状态管理  不设置 默认是在内存中
          setStateBackend(new MemoryStateBackend 内存中,默认/new FsStateBackend 文件系统/RocksDBStateBackend)*/
        env.setStateBackend(new FsStateBackend(checkPath))
        /*设置处理流中采用的时间类型 使用事件时间*/
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        /*每隔N秒自动向流中注入一个水印  新的覆盖掉旧的
            *水印 解决时序  并发流中数据的前后顺序得不到保证*/
        env.getConfig.setAutoWatermarkInterval(3000)
        /*设置检查点的mode模式  CheckpointingMode: AT_LEAST_ONCE:至少一次  EXACTLY_ONCE:正好一次 */
        env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
        /*设置检查点的时间间隔*/
        env.getCheckpointConfig.setCheckpointInterval(10000)
        /*环境中设置并行度  默认是2*/
        env.setParallelism(5)

        val dataStream = env.addSource(new DemoEventsSource)
          /*自定义时间戳和水印*/
          .assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[SEvent] {
              override def getCurrentWatermark: Watermark = {
                  new Watermark(System.currentTimeMillis())
              }

              override def extractTimestamp(element: SEvent, previousElementTimestamp: Long): Long = {
                  System.currentTimeMillis()
              }
          })
          /*分组*/
          .keyBy(0)
          /*滑动事件时间窗口  5s计算一次  3s滑动一次 */
          .window(SlidingEventTimeWindows.of(Time.seconds(5),Time.seconds(3)))
          /*应用一个自定义函数  计算窗口的数据*/
            .apply(new WindowFunction[SEvent, Long, Tuple, TimeWindow] with ListCheckpointed[UDFState] {
            override def apply(key: Tuple, window: TimeWindow, input: Iterable[SEvent], out: Collector[Long]): Unit = {
            //apply 专注于处理数据
                var count = 0L
                for(elem <- input) {
                    count+= 1L
                    //println(elem)
                }
                total += count
                out.collect(count)
            }
            /*从自定义快照中恢复数据*/
            override def restoreState(state: util.List[UDFState]): Unit = {
                /*从头开始算*/
                val udfState = state.get(0)
                total = udfState.getState
            }
            /*实现自定义快照*/
            override def snapshotState(checkpointId: Long, timestamp: Long): util.List[UDFState] = {
                /*将最新的计算 保存起来*/
                val udfList: util.ArrayList[UDFState] = new util.ArrayList[UDFState]()
                val udfState = new UDFState
                udfState.setState(total)
                udfList.add(udfState)
                udfList
            }
        })
          .print()

        env.execute()
    }
}
