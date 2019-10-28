package com.guoshuai.mtdap3
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}

/**
  * Description 测试 flink 读取 socket
  * Created with guoshuai 
  * date 2019/8/2 11:43
  **/
object FlinkConnSocket {

    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val dataStream: DataStream[String] = env.socketTextStream("192.168.56.5", 8001)

        //转成元组
        val count = dataStream.flatMap(x=>x.split("\\s+"))
            .map(etlData(_))
            .keyBy(0).sum(1)
        count.print() //1>  2>
        env.execute("FlinkConnSocket")
    }

    /**
     * Description 对数据进行简单处理 并给定值
     * Param [word]
     * return (word,1)
     **/
    def etlData(word:String):(String,Int) ={
        val regExp = """/^[\u4e00-\u9fa5_a-zA-Z0-9\s\·\~\！\@\#\￥\%\……\&\*\（\）\——\-\+\=\【\】\{\}\、\|\；\‘\’\：\“\”\《\》\？\，\。\、\`\~\!\#\$\%\^\&\*\(\)\_\[\]{\}\\\|\;\'\'\:\"\"\,\.\/\<\>\?]+$/""".r
        val reg = ","
        val result = if(word.contains(reg)) word.drop(word.last) else word
        (result,1)
    }
}
