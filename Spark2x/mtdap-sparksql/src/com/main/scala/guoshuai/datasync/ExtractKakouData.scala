package com.main.scala.guoshuai.datasync

import java.sql.{Connection, Date, DriverManager, PreparedStatement, Timestamp}
import java.text.SimpleDateFormat
import java.util.Calendar

import com.huawei.hadoop.security.LoginUtil
import com.main.scala.guoshuai.utils.DBProper
import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.storage.StorageLevel

import scala.collection.immutable.ListMap

/**
  * hive2phoenix
  * Kerberos 安全认证
  *
  */
object ExtractKakouData {

    def main(args: Array[String]) {
        //kerberos 用户文件
        val userPrincipal = "xxxx"
        val userKeytabPath = "/opt/sh/all_data/user.keytab"
        val krb5ConfPath = "/opt/sh/all_data/krb5.conf"

        val batch:Int = 20

        //华为认证工具类
        val hadoopConf: Configuration = new Configuration()
        LoginUtil.login(userPrincipal, userKeytabPath, krb5ConfPath, hadoopConf)

        val spark = SparkSession.builder()
          .appName("ExtractKakouData")
          .config("spark.sql.shuffle.partitions", 200)
          .enableHiveSupport()
          .getOrCreate()
        import spark.sql

        //获取昨天日期
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        var yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())

        //默认跑昨天的
        if (args.length != 0) {
            yesterday = args(0)
        }
        val date: Array[String] = yesterday.split("-")
        val year = date(0)
        val month = date(1)
        //val day = date(2)

        println("============================================================================================")

        val topTime = System.currentTimeMillis()
        //读hive数据
        val kakoudata = sql(
            s"""select point_id,passing_time,license_number,license_type from ods.ext_kkdata
               | where year = '${year}'
               | and month = '${month}'
             """.stripMargin)
          .withColumn("lines", (monotonically_increasing_id() + 1) % batch)
          .repartition(5).persist(StorageLevel.MEMORY_AND_DISK)


        //数据格式  hive字段
        val map = ListMap("passing_time" -> "varchar",
            "license_type" -> "varchar",
            "license_number" -> "varchar",
            "point_id" -> "varchar"
            ) //data 还是varchar

        //写数据
        val phoenixSql = s"upsert into mtdap2.RTC_KKDATA values (to_date(?), ?, ?, ?)"
        println("------------------------------------------------------------------------------")
        //  println(vehTrack.count())
        val startTime = System.currentTimeMillis()
        //分批次
        for (i <- 0 until batch) {
            println(s"~~~~~~~~~~~~~~~~~~~~~第${i}次跑批开始~~~~~~~~~~~~~~~~~~~~~~~")
            kakoudata.select("lines").printSchema()
            writePhoenixConn(kakoudata.filter(col("lines").cast("Int").equalTo(i)), map, phoenixSql, 200000)
        }

        val endTime = System.currentTimeMillis()
        println("spark程序总共耗时：" + (endTime - topTime))
        println("数据插入phoenix总共耗时：" + (endTime - startTime))
        spark.stop()

    }


    /**
      * 写入phoenix 工具类
      * @param df
      * @param map
      * @param phoenixSql
      * @param batchSize
      */
    def writePhoenixConn(df: DataFrame, map: ListMap[String, String], phoenixSql: String, batchSize: Int): Unit = {
        var mapValue: String = null
        var row: Row = null
        var conn: Connection = null
        var ppst: PreparedStatement = null
        var startTime: Long = 0L
        var endTime: Long = 0L
        var coreTime: Long = 0L
        var a: Int = 1
        var number:Int = 0
        //df.printSchema()
        conn = DriverManager.getConnection(DBProper.phoenixUrl)
        ppst = conn.prepareStatement(phoenixSql)
        conn.setAutoCommit(false)
       //数据量大时,count 很耗性能
        val count: Int = df.count().toInt
        println(count)

        val list = df.collectAsList()
        val its = list.iterator()
        try {
            while (its.hasNext) {
                startTime = System.currentTimeMillis()
                for (num: Int <- 1 to count) {
                    row = its.next()
                    a = 1
                    for (mapKey <- map.keySet) {
                        mapValue = map(mapKey)
                        if ("varchar".equals(mapValue) || "char".equals(mapValue)) {
                            ppst.setString(a, row.getAs[String](mapKey))
                        } else if ("date".equals(mapValue)) {
                            ppst.setDate(a, row.getAs[Date](mapKey))
                        } else if ("bigint".equals(mapValue)) {
                            ppst.setLong(a, row.getAs[Long](mapKey))
                        } else if ("double".equals(mapValue)) {
                            ppst.setDouble(a, row.getAs[Double](mapKey))
                        } else if ("timestamp".equals(mapValue)) {
                            ppst.setTimestamp(a, row.getAs[Timestamp](mapKey))
                        } else if ("boolean".equals(mapValue)) {
                            ppst.setBoolean(a, row.getAs[Boolean](mapKey))
                        } else if ("long".equals(mapValue)) {
                            ppst.setLong(a, row.getAs[Long](mapKey))
                        } else if ("int".equals(mapValue)) {
                            ppst.setInt(a, row.getAs[Int](mapKey))
                        } else if ("integer".equals(mapValue)) {
                            ppst.setInt(a, row.getAs[Integer](mapKey))
                        } else {
                            println("***************  phoenix类型不存在,请添加新类型 --> " + mapValue)
                        }
                        a += 1
                    }
                    ppst.addBatch()
                    if (num % batchSize == 0 || count - num == 0) {
                        ppst.executeBatch()
                        conn.commit()
                        number+=1
                        endTime = System.currentTimeMillis()
                        coreTime = endTime - startTime
                        startTime = endTime
                        println(s"*************** 第 ${number} 次共插入phoenix了 ${num} 条，共用时 ${coreTime} 毫秒")
                    }
                }
            }
        } catch {
            case e: Exception =>
                println("插入phoenix数据异常 " + e.getMessage + "############" + e.printStackTrace())
        } finally {
            if (ppst != null)
                ppst.close()
            if (conn != null)
                conn.close()
        }
    }
}
