package com.main.scala.guoshuai.utils.solr

import java.util

import com.main.scala.guoshuai.utils.common.ENV
import org.apache.solr.common.SolrInputDocument
import org.apache.spark.sql.{DataFrame, Row}

import scala.collection.Map


/**
  * Description  写入工具类
  * Created with guoshuai 
  * date 2019/9/24 11:50
  **/
object WriteSolrConn extends ENV {

    def writeSolrConn(df: DataFrame, map: Map[String, String], collectionName: String): Unit = {
        val zkHost = "192.169.101.1:24002,192.169.101.2:24002,192.169.101.3:24002/solr"
        val batchCommitSize = 1000000


        val cloudSolrClient = SolrConnectionPool.getInstance().getConnection(zkHost, collectionName)
        //查看zookeeper状态
        val zkStateReader = cloudSolrClient.getZkStateReader()
        val clusterState = zkStateReader.getClusterState
        println("The zookeeper state is : " + clusterState)

        var mapKey: String = null
        var mapValue: String = null

        // 文档集合
        val docs: util.Collection[SolrInputDocument] = new util.ArrayList[SolrInputDocument]()
        var doc: SolrInputDocument = null
        var row: Row = null
        //获取数据集
        println(s"count is : ${df.count()}")
        val begin = System.currentTimeMillis()
        val ca = df.collectAsList()
        println("df collectAsList over and cost : " +((System.currentTimeMillis()-begin)/1000)+"s")
        val its = ca.iterator()
        try {
            val startTime = System.currentTimeMillis()
            while (its.hasNext) {
                doc = new SolrInputDocument()
                row = its.next()
                //println("***"+row)
                //[5020530563507539,360113000101,360113,[],VEHPLATETYPE02,PLATECOLOR02,VEHSUBTYPE077,2,21,0,,赣MU2578,12,2019-06-01 00:04:10,0,0,VEHCOLOR02,
                // ,雪佛兰,2019-06-01 00:04:14,2019-06-01 00:04:14,ftp://xnckk3:xnckk3@192.168.110.122/2019/06/01/hbaxhnl001/,hbaxhnl0012019060100041082602.jpg,
                // hbaxhnl0012019060100041082602_tz.jpg,,,,,,0,VEHPLATETYPE02,0,PLATECOLOR02,0,VEHSUBTYPE077,0,雪佛兰,0,,0,,0,,0,,0,,0,,0,,0,,0,,0,,0,,0,,null,0000,null,null,2019,06,01]
                for (mapKey <- map.keySet) {
                    if (mapKey.equals("record_id")) {
                        doc.setField("id", row.getAs[String](mapKey))
                    } else if (mapKey.equals("passing_time")) {
                        doc.setField(mapKey, row.getAs[String](mapKey).replace(" ", "T") + "Z")
                    } else if(mapKey.equals("license_number")){
                        doc.setField(mapKey, row.getAs[String](mapKey))
                        doc.setField("veh_attribution", row.getAs[String](mapKey).substring(0,1))
                    } else{
                        doc.setField(mapKey, row.getAs[String](mapKey))
                    }
                }
                //println("---------"+doc)
                /*(fields: [record_id=5022569524557685, sr_left_callup_conf=0, license_color=PLATECOLOR02, sr_license_type=VEHPLATETYPE02, sr_subbrand=, sr_yellow_label=, receive_time=2019-06-01 00:37:39, sr_left_callup=, sr_license_type_conf=0, sr_yellow_label_conf=0, sr_veh_type_conf=0, sr_receive_time=, record_type=, vio_code=null, sr_pendant=, sr_damage=, sr_veh_color_conf=0, surveil_type=, sr_brand=, sr_right_shield_conf=0, sr_right_callup=, speed_limit_max=0, sr_veh_color=, region_id=360112, sr_right_belt_conf=0, sr_license_pic_path=null, sr_right_callup_conf=0, point_id=360112020035409, brand=, sr_damage_conf=null, sr_veh_type=VEHSUBTYPE079, pic_directory=http://172.118.126.144:6120/, sr_left_belt_conf=0, sr_chemicals=, sr_brand_conf=0, video_path=, sr_left_belt=, sr_right_belt=, veh_length=0, sr_pendant_conf=0, sr_submit_time=, speed_limit_min=0, sr_chemicals_conf=0, sr_subbrand_conf=0, standby_pic=, dept_id=[], sr_license_number=, sr_right_shield=, sr_license_color=PLATECOLOR02, sr_license_color_conf=0, sr_left_shield=, lane_number=0, sr_face_pic_path=0000, veh_type=VEHSUBTYPE079, sr_license_number_conf=0, license_type=VEHPLATETYPE02, passing_time=2019-06-01 00:37:41, data_source=13, license_number=赣AZ2255, insert_time=2019-06-01 00:37:39, character_pic=/pic?AC01F06B0740A90560DE*NCTWSQ-DX/631/23535;15593202864519665104129?pic*1487975640*3261*5517*AC01F06B0740A90560DE-2*1559320662, sr_left_shield_conf=0, veh_color=VEHCOLOR10, veh_pic=/pic?AC01F06B0740A90560DE*NCTWSQ-DX/631/23535;15593202864519665104129?pic*1487798963*176677*5516*AC01F06B0740A90560DE-2*1559320662, speed=0])*/
                docs.add(doc)

                val startTime = System.currentTimeMillis()
                // 批量提交
                if (docs.size % batchCommitSize == 0) {
                    try {
                        cloudSolrClient.add(docs)
                        cloudSolrClient.commit()
                        docs.clear()
                        println("批量写入" + batchCommitSize + "条: " + (System.currentTimeMillis() - startTime) / 1000 + "s")
                    } catch {
                        case e: Exception => e.printStackTrace()
                    }
                }
            }

            // 最后一次提交
            if (docs.size >= 1) {
                println("****last commit size:" + docs.size)
                try {
                    cloudSolrClient.add(docs)
                    cloudSolrClient.commit()
                    //println("****add over")
                    docs.clear()
                } catch {
                    case e: Exception => e.printStackTrace()
                }
            }
            println("数据插入solr总共耗时：" + ((System.currentTimeMillis() - startTime) / 1000 + "s"))

            try {
                cloudSolrClient.close()
            } catch {
                case e: Exception =>
                    e.printStackTrace()
            }
        } catch {
            case e: Exception =>
                println("spark插入solr异常 " + e.getMessage)
        }
    }
}
