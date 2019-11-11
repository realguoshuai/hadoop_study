package com.guoshuai.mtdap3.flink.sql

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.scala._
import org.apache.flink.table.api.{Table, TableEnvironment}

import scala.language.postfixOps

/**
  * Description  测试flink sql  需要1.7版本 +
  * Created with guoshuai 
  * date 2019/8/8 13:55
  **/

object FlinkSqlDemo {
    /**
      * 定义样例类封装数据
      */
    case class  Person1(name:String ,gender:String)

    def main(args: Array[String]): Unit = {
        //获取执行环境
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        //获取table
        val tableEnv = TableEnvironment.getTableEnvironment(env)

        //获取数据源
        val sourceA = env.readTextFile("E:\\development\\test\\mtdap-flink\\src\\com\\guoshuai\\mtdap3\\flink\\sql\\conf\\person.txt")
        //etl source
        val sourceB = sourceA.map(x=>{
            val split = x.split("\\s+")
            Person1(split(0), split(1))
        })

        //将dataStream转化成Table
        val tableA = tableEnv.fromDataStream(sourceB)
        //注册表,表名为person
        tableEnv.registerTable("person",tableA)

        //查询表
        val rs:Table = tableEnv.sqlQuery("select * from person ")

        val result = rs.select("name").
        //将表转成dataStream
        toAppendStream[String]

        result.print()
        env.execute("flinkSQL")
    }
}
