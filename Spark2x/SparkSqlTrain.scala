package com.main.scala.train

import java.util

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
/**
  * Description  测试sparksql
  *    DSL写法:算子  或者 sql("sql语句")
  * Created with guoshuai
  * date 2020/11/5 8:58
  **/
object SparkSqlTrain {

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
        import spark.sql
        import spark.implicits._

        //val path ="E:\\development\\test\\mtdap-sparksql\\src\\com\\main\\scala\\enjoyor\\train\\cls.json"
        val users= Array("zhangsan 30 云A12345 VEHPLATETYPE02", "lisi 29 浙A12345 VEHPLATETYPE99", "wanger 28 京A12345 VEHPLATETYPE02", "niuer 28 赣A12345 VEHPLATETYPE01")
        val users1= Array("zhangsan 30 北京市", "lisi 29 杭州市", "wanger 28 南昌市", "niuer 28 厦门市","yi 22 上海市")

        val tuples: Array[(String,Int,String,String)] = users.map(x=>(x.split(" ")(0),x.split(" ")(1).toInt,x.split(" ")(2),x.split(" ")(3)))
        val tuples1: Array[(String,Int,String)] = users.map(x=>(x.split(" ")(0),x.split(" ")(1).toInt,x.split(" ")(2)))

        //转成RDD
        val uRdd: RDD[(String, Int, String, String)] = spark.sparkContext.parallelize(tuples)
        val uRdd1: RDD[(String, Int, String)] = spark.sparkContext.parallelize(tuples1)
        //RDD转DF
        val df = uRdd.toDF("name","age","license_num","license_type")
        val df1 = uRdd1.toDF("name","age","address")
        df.show(5)

        //DF转成RDD
        val rdd: RDD[Row] = df.rdd
        //遍历
        /*for(row <-rdd){
            println(row.getString(0))
            println(row.getInt(1))
            println(row.getString(2))
            println(row.getString(3))
        }*/
        //DF转成Dataset
        val ds: Dataset[DriverInfo] = df.as[DriverInfo]

        //创建表格
        df.createOrReplaceTempView("driver_info")

        val selectDf: DataFrame = sql(
            """
              | select * from driver_info where age > 28
            """.stripMargin)


        //TODO DF函数测试
        //显示指定函数
        selectDf.limit(1).show()
        //获取列名
        val colsArr = selectDf.columns
        for(col<-colsArr){
            println(col)
        }
        //显示字段信息
        df.printSchema()
        //显示概况信息
        df.describe().show()
        //显示行数
        df.count()
        //将所有数据放入一个变量,返回Array对象
        val rows: Array[Row] = df.collect()
        val rowss: util.List[Row] = df.collectAsList()
        //获取头几行(head take作用完全一样)
        val headArrRow: Array[Row] = df.head(3)
        val takeArrRow: Array[Row] = df.take(3)
        for(headI<-headArrRow){
            val i: Row = headI
            println(i)
        }
        //判断是否非空  is null,is not null,isNull,<> ''
        df.filter("license_num is null").select("name").show(1)
        df.filter("license_num is not null").select("name").show(1)
        df.filter(df("license_num").isNull).select("name").show(1)
        df.filter("license_num <> ''").select("name").show(1)
        //起别名
        df.select(df("name").as("username")).show()
        //查询出所有列, 只修改设置的列名
        df.withColumnRenamed("name", "stuname").show()
        //去重
        df.distinct().show()
        //根据指定列名去重
        df.dropDuplicates(Seq("name")).show()
        //条件查询
        df.select("name","age").where("age>28").show()
        df.select("name","age").where("age>28 and name like '%n%'").show()

        //排序
        df.select("name","age").sort("age").show()
        df.select("name","age").sort(df("age").asc).show()
        df.select("name","age").sort($"age".desc).show()
        df.select("name","age").orderBy($"age".desc,$"name".asc).show(10)

        //分组
        df.select("name","age").groupBy("age").count().show()

        //汇总 + 函数 max() min() avg()
        df.select("name","age").groupBy($"age").sum().show()
        df.select("name","age").groupBy(df("age")).max().show()

        df.select("name","age").groupBy().avg("age").show()

        //聚合函数agg  DSL语法常用
        df.agg("name"->"max","age"->"avg").show()
        df.groupBy("age").agg("name"->"max").show()
        df.groupBy("name").agg("age"->"avg").agg("name"->"max").show()
        /* 把上一个覆盖掉了
        +---------+
        |max(name)|
        +---------+
        | zhangsan|
        +---------+*/
        df.groupBy("name").agg("age"->"avg","license_num"->"max").show()
        /*
        +--------+--------+----------------+
        |    name|avg(age)|max(license_num)|
        +--------+--------+----------------+
        |   niuer|    28.0|         赣A12345|
        |zhangsan|    30.0|         云A12345|
        |  wanger|    28.0|         京A12345|
        |    lisi|    29.0|         浙A12345|
        +--------+--------+----------------+**/
        //agg 聚合省略 group by  => select max(age) from table
        df.agg("age"->"max").show()
        df.groupBy().agg("age"->"max").show()


        //结果合并 union 增加行
        df.union(df).show()


        //连接 至少有一个相同的字段
        df.join(df1, "name").show()
        //多个字段关联,inner left/left_outer right/right_outer outer
        df.join(df1, Seq("name", "age"), "inner").show()
        df.join(df1, Seq("name", "age"), "left").show()
        df.join(df1, Seq("name"), "right").show()
        //新加一列
        df.withColumn("newCol", df("age")).show()

        //修改某列的值
        df.withColumn("age",df("age")+10).show()

        //是否存在   存在(这一行的)输出
        df.intersect(df.limit(1)).show()
        //不存在     不存在(第一行)输出
        df.except(df.limit(1)).show()

        spark.stop()
    }
}
