package com.main.scala.guoshuai.datasync

import com.main.scala.guoshuai.utils.ConnUtil
import com.main.scala.guoshuai.utils.solr.WriteSolrConn
import org.apache.log4j.Logger
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.sql.functions._

/**
  * Description  同步hive表 数据到solr历史表
  * Created with guoshuai 
  * date 2019/9/24 11:41
  **/

object Hive2Solr {
    lazy val logger: Logger = Logger.getLogger("HiveToSolr")

    case class kkdataModel(
                            record_id: String,
                            point_id: String,
                            region_id: String,
                            dept_id: String,
                            license_type: String,
                            license_color: String,
                            veh_type: String,
                            lane_number: String,
                            speed: String,
                            veh_length: String,
                            record_type: String,
                            license_number: String,
                            data_source: String,
                            passing_time: String,
                            speed_limit_max: String,
                            speed_limit_min: String,
                            veh_color: String,
                            surveil_type: String,
                            brand: String,
                            receive_time: String,
                            insert_time: String,
                            pic_directory: String,
                            veh_pic: String,
                            character_pic: String,
                            standby_pic: String,
                            video_path: String,
                            sr_submit_time: String,
                            sr_receive_time: String,
                            sr_license_number: String,
                            sr_license_number_conf: String,
                            sr_license_type: String,
                            sr_license_type_conf: String,
                            sr_license_color: String,
                            sr_license_color_conf: String,
                            sr_veh_type: String,
                            sr_veh_type_conf: String,
                            sr_veh_color: String,
                            sr_veh_color_conf: String,
                            sr_brand: String,
                            sr_brand_conf: String,
                            sr_subbrand: String,
                            sr_subbrand_conf: String,
                            sr_right_belt: String,
                            sr_right_belt_conf: String,
                            sr_left_belt: String,
                            sr_left_belt_conf: String,
                            sr_right_shield: String,
                            sr_right_shield_conf: String,
                            sr_left_shield: String,
                            sr_left_shield_conf: String,
                            sr_right_callup: String,
                            sr_right_callup_conf: String,
                            sr_left_callup: String,
                            sr_left_callup_conf: String,
                            sr_pendant: String,
                            sr_pendant_conf: String,
                            sr_yellow_label: String,
                            sr_yellow_label_conf: String,
                            sr_chemicals: String,
                            sr_chemicals_conf: String,
                            sr_damage: String,
                            sr_damage_conf: String,
                            sr_face_pic_path: String,
                            sr_license_pic_path: String,
                            vio_code: String
                          )

    def main(args: Array[String]): Unit = {
        if (args == 0) logger.info("参数异常") else {
            ConnUtil.getHuawei2()
            /*构建spark入口函数*/
            val spark = ConnUtil.getHiveConn("hive2Solr", 600)
            process(spark, args(0))
            spark.stop()
        }
    }

    /**
      * Description  执行查询,写入函数
      * Param [spark, time]
      * return void
      **/
    def process(spark: SparkSession, time: String): Unit = {
        val year = time.substring(0, 4)
        val month = time.substring(5, 7)
        val day = time.substring(8, 10)
        import spark.sql
        val topTime = System.currentTimeMillis()
        val result: DataFrame = sql(//""""""前+s后可以在字符串中传参数
            s"""select *,hour(passing_time)hours from ods.ext_kkdata
               | where year = '${year}'
               | and month = '${month}'
               | and day = '${day}'
               | """.stripMargin)
          .withColumn("minutes", minute(col("passing_time")))
        //.withColumn("minutes",minute(col("passing_time")+1))


        //        result.show(3)

        val map: Map[String, String] = Map("record_id" -> "varchar",
            "point_id" -> "varchar",
            "region_id" -> "varchar",
            "dept_id" -> "varchar",
            "license_type" -> "varchar",
            "license_color" -> "varchar",
            "veh_type" -> "varchar",
            "lane_number" -> "varchar",
            "speed" -> "varchar",
            "veh_length" -> "varchar",
            "record_type" -> "varchar",
            "license_number" -> "varchar",
            "data_source" -> "varchar",
            "passing_time" -> "varchar",
            "speed_limit_max" -> "varchar",
            "speed_limit_min" -> "varchar",
            "veh_color" -> "varchar",
            "surveil_type" -> "varchar",
            "brand" -> "varchar",
            "receive_time" -> "varchar",
            "insert_time" -> "varchar",
            "pic_directory" -> "varchar",
            "veh_pic" -> "varchar",
            "character_pic" -> "varchar",
            "standby_pic" -> "varchar",
            "video_path" -> "varchar",
            "sr_submit_time" -> "varchar",
            "sr_receive_time" -> "varchar",
            "sr_license_number" -> "varchar",
            "sr_license_number_conf" -> "varchar",
            "sr_license_type" -> "varchar",
            "sr_license_type_conf" -> "varchar",
            "sr_license_color" -> "varchar",
            "sr_license_color_conf" -> "varchar",
            "sr_veh_type" -> "varchar",
            "sr_veh_type_conf" -> "varchar",
            "sr_veh_color" -> "varchar",
            "sr_veh_color_conf" -> "varchar",
            "sr_brand" -> "varchar",
            "sr_brand_conf" -> "varchar",
            "sr_subbrand" -> "varchar",
            "sr_subbrand_conf" -> "varchar",
            "sr_right_belt" -> "varchar",
            "sr_right_belt_conf" -> "varchar",
            "sr_left_belt" -> "varchar",
            "sr_left_belt_conf" -> "varchar",
            "sr_right_shield" -> "varchar",
            "sr_right_shield_conf" -> "varchar",
            "sr_left_shield" -> "varchar",
            "sr_left_shield_conf" -> "varchar",
            "sr_right_callup" -> "varchar",
            "sr_right_callup_conf" -> "varchar",
            "sr_left_callup" -> "varchar",
            "sr_left_callup_conf" -> "varchar",
            "sr_pendant" -> "varchar",
            "sr_pendant_conf" -> "varchar",
            "sr_yellow_label" -> "varchar",
            "sr_yellow_label_conf" -> "varchar",
            "sr_chemicals" -> "varchar",
            "sr_chemicals_conf" -> "varchar",
            "sr_damage" -> "varchar",
            "sr_damage_conf" -> "varchar",
            "sr_face_pic_path" -> "varchar",
            "sr_license_pic_path" -> "varchar",
            "vio_code" -> "varchar")


        //分发写到solr
        for (i <- 0 to 23) {
            println(s"${i}点 - ${i + 1}点数据开始同步")
            if (i < 21 && i > 6) {
                for (j <- 0 to 1) {
                    println(s"${j}")
                    WriteSolrConn.writeSolrConn(result.repartition(50).persist(StorageLevel.MEMORY_AND_DISK_SER)
                      .filter(s"hours = '${i}' and (minutes % 2) = '${j}'")
                        , map, "kkrecordsHisDB")
                }
            }else{
                WriteSolrConn.writeSolrConn(result.repartition(50).persist(StorageLevel.MEMORY_AND_DISK_SER)
                  .filter(s"hours = '${i}'")
                    , map, "kkrecordsHisDB")
            }
        }
        val endTime = System.currentTimeMillis()
        println("spark程序总共耗时：" + (endTime - topTime) / 1000 + "s")
    }


}
