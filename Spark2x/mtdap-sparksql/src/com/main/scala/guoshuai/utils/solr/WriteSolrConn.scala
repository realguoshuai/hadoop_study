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
        val zkHost = "ip1,ip2,ip3:24002/solr"
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
