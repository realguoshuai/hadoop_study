package com.main.scala.train

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, Row, SparkSession}

/**
  * Description  测试spark/hive sql 常用窗口函数
  * Created with guoshuai
  * date 2020/12/2 9:13
  **/
object SparkWindowSqlTrain {

    case  class DriverInfo(name:String,age:Int,license_num:String,license_type:String)

    def main(args: Array[String]): Unit = {
        //本地测试关闭kerberos
        System.setProperty("java.security.krb5.realm","")
        System.setProperty("java.security.krb5.kdc","")

        val spark = SparkSession
          .builder()
          .appName("SparkSqlTrain")
          .master("local[*]")
          //.enableHiveSupport()
          //.config("spark.sql.crossJoin.enabled",true)
          .getOrCreate()


        import spark.implicits._
        import spark.sql

        //模拟数据
        val users= Array("zhangsan 30 云A12345 VEHPLATETYPE02", "lisi 29 浙A12345 VEHPLATETYPE99", "wanger 28 京A12345 VEHPLATETYPE02", "niuer 28 赣A12345 VEHPLATETYPE01")
        val users1= Array("zhangsan 30 北京市", "lisi 29 杭州市", "wanger 28 南昌市", "niuer 28 厦门市","yi 22 上海市")

        //转list(tuple)
        val tuples: Array[(String,Int,String,String)] = users.map(x=>(x.split(" ")(0),x.split(" ")(1).toInt,x.split(" ")(2),x.split(" ")(3)))
        val tuples1: Array[(String,Int,String)] = users.map(x=>(x.split(" ")(0),x.split(" ")(1).toInt,x.split(" ")(2)))

        //转RDD
        val uRdd: RDD[(String, Int, String, String)] = spark.sparkContext.parallelize(tuples)
        val uRdd1: RDD[(String, Int, String)] = spark.sparkContext.parallelize(tuples1)
        //RDD转DF
        val df = uRdd.toDF("name","age","license_num","license_type")
        val df1 = uRdd1.toDF("name","age","address")
        //todo 测试df ds rdd之间相互转换
        //DF转成Dataset
        val ds: Dataset[DriverInfo] = df.as[DriverInfo]
        //ds转rdd
        val rdd1: RDD[DriverInfo] = ds.rdd
        //DF转成RDD
        val rdd2: RDD[Row] = df.rdd
        //遍历RDD[Row]
        /*for(row <-rdd){
            println(row.getString(0))
            println(row.getInt(1))
            println(row.getString(2))
            println(row.getString(3))
        }*/


        //创建表格
        df.createOrReplaceTempView("driver_info")
        df.show()
      /*+--------+---+-----------+--------------+
        |    name|age|license_num|  license_type|
        +--------+---+-----------+--------------+
        |zhangsan| 30|    云A12345|VEHPLATETYPE02|
        |    lisi| 29|    浙A12345|VEHPLATETYPE99|
        |  wanger| 28|    京A12345|VEHPLATETYPE02|
        |   niuer| 28|    赣A12345|VEHPLATETYPE01|
        +--------+---+-----------+--------------+*/

        //TODO 下面是常见的聚合开窗函数

        //count开窗函数
        sql(s"""
               |select name,age,license_num,license_type
               |---- 以符合条件的所有行作为窗口
               |,count(license_num) over()  count1
               |-- 以按license_num分组的所有行作为窗口
               |,count(license_num) over(partition by license_type) count2
               |-- 以按license_num分组、按age排序后、按到当前行(含当前行)的所有行作为窗口
               |,count(license_num) over(partition by license_type order by age desc) count3
               |--以按license_type分组、按age排序、按当前行+往前1行+往后2行的行作为窗口
               |,count(license_num) over(partition by license_type order by age  rows between 1 preceding and 2 following) count4
               |from driver_info
             """.stripMargin).show()

        //sum开窗函数
        sql(s"""
               |select name,age,license_num,license_type
               |,sum(age) over() sum1
               |,sum(age) over(partition by license_type) sum2
               |,sum(age) over(partition by license_type order by age desc) sum3
               |-- 按当前行+往前1行+往后2行的行作为窗口
               |,sum(age) over(partition by license_type order by age rows between 1 preceding and 2 following) sum4
               |from driver_info
             """.stripMargin).show(10)

        //min开窗函数
        sql(s"""
               |select name,age,license_num,license_type
               |,min(age) over() min1
               |,min(age) over(partition by license_type) min2
               |,min(age) over(partition by license_type order by age desc) min3
               |-- 按当前行+往前1行+往后2行的行作为窗口
               |,min(age) over(partition by license_type order by age rows between 1 preceding and 2 following) min4
               |from driver_info
             """.stripMargin).show(10)

        //max开窗函数
        sql(s"""
               |select name,age,license_num,license_type
               |,max(age) over() max1
               |,max(age) over(partition by license_type) max2
               |,max(age) over(partition by license_type order by age desc) max3
               |-- 按当前行+往前1行+往后2行的行作为窗口
               |,max(age) over(partition by license_type order by age rows between 1 preceding and 2 following) max4
               |from driver_info
             """.stripMargin).show(10)

        //avg开窗函数
        sql(s"""
               |select name,age,license_num,license_type
               |,avg(age) over() avg1
               |,avg(age) over(partition by license_type) avg2
               |,avg(age) over(partition by license_type order by age desc) avg3
               |-- 按当前行+往前1行+往后2行的行作为窗口
               |,avg(age) over(partition by license_type order by age rows between 1 preceding and 2 following) avg4
               |from driver_info
             """.stripMargin).show(10)

        //TODO first_value开窗函数  返回分区中的第一个值
        sql(s"""
               |select name,age,license_num,license_type
               |,first_value(age) over() first_value1
               |,first_value(age) over(partition by license_type) first_value2
               |,first_value(age) over(partition by license_type order by age desc) first_value3
               |,first_value(age) over(partition by license_type order by age rows between 1 preceding and 2 following) first_value4
               |from driver_info
             """.stripMargin).show(10)
            /*+--------+---+-----------+--------------+------------+------------+------------+------------+
            |    name|age|license_num|  license_type|first_value1|first_value2|first_value3|first_value4|
            +--------+---+-----------+--------------+------------+------------+------------+------------+
            |   niuer| 28|    赣A12345|VEHPLATETYPE01|          30|          28|          28|          28|
            |  wanger| 28|    京A12345|VEHPLATETYPE02|          30|          30|          30|          28|
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|          30|          30|          30|          28|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99|          30|          29|          29|          29|
            +--------+---+-----------+--------------+------------+------------+------------+------------+*/

        //last_value开窗函数  返回分区中的最后一个值
        sql(s"""
               |select name,age,license_num,license_type
               |,last_value(age) over() last_value1
               |,last_value(age) over(partition by license_type) last_value2
               |,last_value(age) over(partition by license_type order by age desc) last_value3
               |,last_value(age) over(partition by license_type order by age rows between 1 preceding and 2 following) last_value4
               |from driver_info
             """.stripMargin).show(10)
            /*+--------+---+-----------+--------------+-----------+-----------+-----------+-----------+
            |    name|age|license_num|  license_type|last_value1|last_value2|last_value3|last_value4|
            +--------+---+-----------+--------------+-----------+-----------+-----------+-----------+
            |   niuer| 28|    赣A12345|VEHPLATETYPE01|         29|         28|         28|         28|
            |  wanger| 28|    京A12345|VEHPLATETYPE02|         29|         28|         28|         30|
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|         29|         28|         30|         30|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99|         29|         29|         29|         29|
            +--------+---+-----------+--------------+-----------+-----------+-----------+-----------+*/

        //TODO lag开窗函数 向上取值
        //lag(col,n,default)     col:列名 n:往上第n行 default:往上第n行为NULL时候，取默认值,不指定则取NULL

        sql(s"""
               |select name,age,license_num,license_type
               |--需要partition by + order by
               |,lag(age,1) over(partition by license_type order by age) lag1
               |-- 给定默认值100
               |,lag(age,1,100) over(partition by license_type order by age desc) lag2
               |from  driver_info
             """.stripMargin).show(10)
            /*+--------+---+-----------+--------------+----+----+
            |    name|age|license_num|  license_type|lag1|lag2|
            +--------+---+-----------+--------------+----+----+
            |  wanger| 28|    京A12345|VEHPLATETYPE02|null|  30|
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|  28| 100|
            |   niuer| 28|    赣A12345|VEHPLATETYPE01|null| 100|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99|null| 100|
            +--------+---+-----------+--------------+----+----+*/

        //TODO lead开窗函数  用于统计窗口内往下第n个值
        //lead(col,n,default)  col:列名 n:往下第n行 default:往下第n行为NULL时候，取默认值,不指定则取NULL

        sql(s"""
               |select name,age,license_num,license_type
               |,lead(age,1) over(partition by license_type order by age desc) lead1
               |,lead(age,1,-100) over(partition by license_type order by age desc) lead2
               |from driver_info
             """.stripMargin).show(10)
            /*+--------+---+-----------+--------------+-----+-----+
            |    name|age|license_num|  license_type|lead1|lead2|
            +--------+---+-----------+--------------+-----+-----+
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|   28|   28|
            |  wanger| 28|    京A12345|VEHPLATETYPE02| null| -100|
            |   niuer| 28|    赣A12345|VEHPLATETYPE01| null| -100|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99| null| -100|
            +--------+---+-----------+--------------+-----+-----+*/

        //cume_dist开窗函数 计算某个窗口或分区中某个值的累积分布
        //小于等于当前值x的行数 / 窗口或partition分区内的总行数
        sql(s"""
               |select name,age,license_num,license_type
               |--统计小于等于当前年龄(当前行)的占比
               |,cume_dist() over(order by age) cume_list1
               |--统计大于等于当前年龄的占比
               |,cume_dist() over(order by age desc) cume_sist2
               |--统计分区内小于等于当前年龄的占比
               |,cume_dist() over(partition by license_type order by age) cume_dist3
               |--统计分区内大于等于当前年龄的占比
               |,cume_dist() over(partition by license_type order by age desc) cume_dist4
               |from driver_info
             """.stripMargin).show(10)
            /*+--------+---+-----------+--------------+----------+----------+----------+----------+
            |    name|age|license_num|  license_type|cume_list1|cume_sist2|cume_dist3|cume_dist4|
            +--------+---+-----------+--------------+----------+----------+----------+----------+
            |   niuer| 28|    赣A12345|VEHPLATETYPE01|       0.5|       1.0|       1.0|       1.0|
            |  wanger| 28|    京A12345|VEHPLATETYPE02|       0.5|       1.0|       0.5|       1.0|
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|       1.0|      0.25|       1.0|       0.5|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99|      0.75|       0.5|       1.0|       1.0|
            +--------+---+-----------+--------------+----------+----------+----------+----------+*/

        //TODO rank 排序开窗函数
        //rank 开窗函数基于 over 子句中的 order by 确定一组值中一个值的排名
        //如果存在partition by 则为每个分区组中的每个值排名 排名可能不是连续的 两个行的排名为1 则下一个排名为3
        sql(s"""
               |select name,age,license_num,license_type
               |,rank() over(order by age desc) rank1
               |,rank() over(partition by license_type order by age desc) rank2
               |from driver_info
             """.stripMargin).show(10)
            /*+--------+---+-----------+--------------+-----+-----+
            |    name|age|license_num|  license_type|rank1|rank2|
            +--------+---+-----------+--------------+-----+-----+
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|    1|    1|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99|    2|    1|
            |  wanger| 28|    京A12345|VEHPLATETYPE02|    3|    2|
            |   niuer| 28|    赣A12345|VEHPLATETYPE01|    3|    1|
            +--------+---+-----------+--------------+-----+-----+*/

        //dense_rank开窗函数
        //跟rank区别: 当排名一样的时候,接下来的行是连续的
        sql(s"""
               |select name,age,license_num,license_type
               |,dense_rank() over(order by age desc) dense_rank1
               |,dense_rank() over(partition by license_type order by age desc) dense_rank2
               |from driver_info
             """.stripMargin).show(10)
            /*+--------+---+-----------+--------------+-----------+-----------+
            |    name|age|license_num|  license_type|dense_rank1|dense_rank2|
            +--------+---+-----------+--------------+-----------+-----------+
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|          1|          1|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99|          2|          1|
            |  wanger| 28|    京A12345|VEHPLATETYPE02|          3|          2|
            |   niuer| 28|    赣A12345|VEHPLATETYPE01|          3|          1|
            +--------+---+-----------+--------------+-----------+-----------+*/

        //TODO row_number 开窗函数 从1开始对分区内的数据排序
        sql(s"""
               |select name,age,license_num,license_type
               |,row_number() over(partition by license_type order by age desc)rn1
               |,row_number() over(partition by license_type,license_num order by age desc) rn2
               |from driver_info
             """.stripMargin).show(10)
            /*+--------+---+-----------+--------------+---+---+
            |    name|age|license_num|  license_type|rn1|rn2|
            +--------+---+-----------+--------------+---+---+
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|  1|  1|
            |  wanger| 28|    京A12345|VEHPLATETYPE02|  2|  1|
            |   niuer| 28|    赣A12345|VEHPLATETYPE01|  1|  1|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99|  1|  1|
            +--------+---+-----------+--------------+---+---+*/

        //percent_rank 开窗函数  统计给定行的百分比排名 超过百分比
        sql(s"""
               |select name,age,license_num,license_type
               |,row_number() over(partition by license_type order by age desc) rn
               |,percent_rank() over(partition by license_type order by age desc) pr
               |from driver_info
             """.stripMargin).show(10)
            /*+--------+---+-----------+--------------+---+---+
            |    name|age|license_num|  license_type| rn| pr|
            +--------+---+-----------+--------------+---+---+
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|  1|0.0|
            |  wanger| 28|    京A12345|VEHPLATETYPE02|  2|1.0|
            |   niuer| 28|    赣A12345|VEHPLATETYPE01|  1|0.0|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99|  1|0.0|
            +--------+---+-----------+--------------+---+---+*/

        //ntile开窗函数 将分区排序数据分执行组,给定组的排名
        sql(s"""
               |select name,age,license_num,license_type
               |,ntile(2) over(partition by license_type order by age desc)ntile1
               |from driver_info
             """.stripMargin).show()
          /*+--------+---+-----------+--------------+------+
            |    name|age|license_num|  license_type|ntile1|
            +--------+---+-----------+--------------+------+
            |zhangsan| 30|    云A12345|VEHPLATETYPE02|     1|
            |  wanger| 28|    京A12345|VEHPLATETYPE02|     2|
            |   niuer| 28|    赣A12345|VEHPLATETYPE01|     1|
            |    lisi| 29|    浙A12345|VEHPLATETYPE99|     1|
            +--------+---+-----------+--------------+------+*/

        spark.stop()
    }
}