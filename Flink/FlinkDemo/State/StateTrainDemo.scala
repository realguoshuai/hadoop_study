package com.guoshuai.mtdap3.flink.state

import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.util.Collector

/**
  * Description  测试有状态的计算  当前demo基于 flink1.4.0
  * Created with guoshuai
  * date 2019/11/20 15:37
  **/
object StateTrainDemo {

    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        //不设置状态管理 默认是在taskmanager的内存中
        env.setStateBackend(new FsStateBackend("file:///d:/tmp-checkpoint")) // 状态存在文件中HDFS/本地都行

        //从集合中获取数据
        env.fromCollection(List(
            (1L, 3L),
            (1L, 5L),
            (1L, 7L),
            (1L, 5L),
            (1L, 10L)
        )).keyBy(_._1)
          .flatMap(new CountWindowAverage())
          .print()

        //计算2次 (1,4) && (1,5)
        //计算3次 (1,4) && (1,5) && (1,5) && (1,6)

        env.execute("ExampleManagedState")
    }

    class CountWindowAverage extends RichFlatMapFunction[(Long, Long), (Long, Long)] {
        //(计数,总和)
        private var stateSum: ValueState[(Long, Long)] = _

        /**
          * Description
          * Param [parameters]
          * return void
          **/
        override def open(parameters: Configuration): Unit = {
            stateSum = getRuntimeContext.getState(
                //createTypeInformation 导不进去 直接引入 import org.apache.flink.api.scala._
                new ValueStateDescriptor[(Long, Long)]("average", createTypeInformation[(Long, Long)])
            )
        }

        /**
          * Description
          * Param [in, out]
          * return void
          **/
        override def flatMap(in: (Long, Long), out: Collector[(Long, Long)]): Unit = {
            //access the state value  需要先获取到当前流中state,在open中获取,否则抛NullPointerException
            val tmpCurrentSum = stateSum.value

            val currentSum = if (tmpCurrentSum != null) {
                tmpCurrentSum
            } else {
                (0L, 0L)
            }
            val newSum = (currentSum._1 + 1, currentSum._2 + in._2)

            //update the state
            stateSum.update(newSum)

            //如果计数达到2,则发出平均值并清除状态
            if (newSum._1 >= 2) {
                out.collect(in._1, in._2 / in._1)
                //clear
                stateSum.clear()
            }
        }
    }

}
