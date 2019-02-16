package com.guoshuai.synchrodata.fastsearch;

import com.guoshuai.util.SolrException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient.Builder;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Random;


public class NCHiveToSolr {

    private static final Logger LOG = LoggerFactory.getLogger(NCHiveToSolr.class);
        private String solrKbsEnable;
        private int zkClientTimeout;
        private int zkConnectTimeout;
        private String zkHost;
        private String zookeeperDefaultServerPrincipal;
        private String collectionName;
        private String defaultConfigName;
        private int shardNum;
        private int replicaNum;
        private String principal;

        public static void main(String[] args) throws SolrException {
            if (args.length < 1) {
                System.err.println("The number of parameter should be one.");
                System.err.println("Usage:  cmd \"jdbc:fiber://fiberconfig=<fiber.xml_path>;defaultDriver=<driver_name>\" <spark_data_path> <hive_data_path>");
                System.exit(-1);
            }

            //sdf.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

            //2. 获取参数
            String url = args[0]; //jdbc 连接的url
            String date = null;
            if (args.length == 2){
                date = args[1];// 获取需要同步数据的日期
            }
            //String date = args[1];
            //当前同步的批次 默认为1
       /* String dsBatch = "1";
        if (args[2] != null){
            dsBatch = args[2];
        }*/
            String hiveTableName = "mtdap_nanchang.base_kkdata_valid";

            //3. 注册驱动包
            try{
                Class.forName("com.huawei.fiber.FiberDriver");
            }catch (Exception e){

            }

            //4.1 声明hive的jdbc对象
            Connection hiveConn = null;
            Statement hiveStmt = null;
            ResultSet hiveRs = null;


            try {
                //5.1 创建hive的Connection和Statement
                LOG.info("创建连接Hive的Connection和Statement...");
                hiveConn = DriverManager.getConnection(url);

                hiveStmt = hiveConn.createStatement();
            } catch (Exception e) {
                LOG.error("创建Hive连接失败!!!" +  e.getStackTrace());
            }

            try {

            } catch (Exception e) {
                LOG.error("创建Phoenix连接失败!!!" + e.getStackTrace());
            }
            //1.选择hive execution engine
            LOG.info("正在切换hive execution engine... ");

            try{

                hiveStmt.execute("set fiber.execution.engine = hive");

                if(date != null){
                    //hiveStmt.execute("select * from " + hiveTableName +" where stat_day = '" +date+ "'");
                    System.out.println("select * from " + hiveTableName +" where year = '" +date+ "'");
                    //hiveStmt.execute("select * from " + hiveTableName +" where pass_time = '" +date+ "'");
                    hiveStmt.execute("select * from " + hiveTableName +" where year = '" +date+ "'");
                }else {
                    hiveStmt.execute("select * from " + hiveTableName);
                }
                System.out.println("hive query over");
                hiveRs = hiveStmt.getResultSet();
            }catch (Exception e){
                e.printStackTrace();
            }

            NCHiveToSolr HiveToSolr = new NCHiveToSolr();

            HiveToSolr.initProperties();

            CloudSolrClient cloudSolrClient = null;

            long start = System.currentTimeMillis();
            long counter = 0;
            int i = 0;


            /*if (HiveToSolr.solrKbsEnable.equals("true")) {
                HiveToSolr.setSecConfig();
            }*/

            try {
                cloudSolrClient = HiveToSolr.getCloudSolrClient(HiveToSolr.zkHost);

                cloudSolrClient.setDefaultCollection(HiveToSolr.collectionName);

                Collection<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
                SolrInputDocument doc = null;
                Random random = new Random();


              while (hiveRs.next()){

                  doc = new SolrInputDocument();

                  doc.addField("id", hiveRs.getLong(1));
                  doc.addField("pointId", hiveRs.getString(2));
                  doc.addField("licenseType", hiveRs.getString(5));
                  doc.addField("licenseNumber", hiveRs.getString(6));
                  doc.addField("passingTime", hiveRs.getString(8).replace(" ", "T") + "Z");
                  doc.addField("laneNumber", hiveRs.getString(4));
                  doc.addField("speed", hiveRs.getInt(7));
                  doc.addField("direction", hiveRs.getString(3));
                  doc.addField("picPath", "file:///D:/pic/" + (random.nextInt(4) + 1) + ".png");


                  documents.add(doc);

                  counter++;
                  if (counter % 100000 == 0) {
                      i++;
                      LOG.info("---------- 正在插入 第 " + i + " 批次");
                      UpdateResponse updateResponse = cloudSolrClient.add(documents);


                      cloudSolrClient.commit();
                      long end = System.currentTimeMillis();
                      documents = new ArrayList<SolrInputDocument>();
                      LOG.info("100000 条耗费时间：" + (end - start));
                      start = System.currentTimeMillis();
                  }

              }

                cloudSolrClient.commit();//提交，将所有更新提交到索引中
            } catch (Exception e) {
                throw new SolrException();
            } finally {
                try {
                    if (hiveConn != null) {
                        hiveConn.close();
                    }
                }catch (Exception e){
                    LOG.warn("Failed to close hiveConn", e);
                }


                if (cloudSolrClient != null) {
                    try {
                        cloudSolrClient.close();
                    } catch (IOException e) {
                        LOG.warn("Failed to close cloudSolrClient", e);
                    }
                }
            }

        }

        private void initProperties() throws SolrException {
            Properties properties = new Properties();
            String proPath = System.getProperty("user.dir") + File.separator + "conf" + File.separator + "solr-example.properties";
            try {
                properties.load(new FileInputStream(new File(proPath)));
            } catch (IOException e) {

                throw new SolrException("Failed to load properties file : " + proPath);
            }
            solrKbsEnable = properties.getProperty("SOLR_KBS_ENABLED");
            zkClientTimeout = Integer.valueOf(properties.getProperty("zkClientTimeout"));
            zkConnectTimeout = Integer.valueOf(properties.getProperty("zkConnectTimeout"));
            zkHost = properties.getProperty("zkHost");
            zookeeperDefaultServerPrincipal = properties.getProperty("ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL");
            //collectionName = properties.getProperty("COLLECTION_NAME");
            /*临时用*/
            collectionName = "kk_test";
            defaultConfigName = properties.getProperty("DEFAULT_CONFIG_NAME");
            shardNum = Integer.valueOf(properties.getProperty("shardNum"));
            replicaNum = Integer.valueOf(properties.getProperty("replicaNum"));
            principal = properties.getProperty("principal");

        }



        private CloudSolrClient getCloudSolrClient(String zkHost) throws SolrException {

            Builder builder = new Builder();
            builder.withZkHost(zkHost);
            CloudSolrClient cloudSolrClient = builder.build();

            cloudSolrClient.setZkClientTimeout(zkClientTimeout);
            cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
            cloudSolrClient.connect();
            LOG.info("The cloud Server has been connected !!!!");

            ZkStateReader zkStateReader = cloudSolrClient.getZkStateReader();
            ClusterState cloudState = zkStateReader.getClusterState();
            LOG.info("The zookeeper state is : {}", cloudState);

            return cloudSolrClient;
        }
}
