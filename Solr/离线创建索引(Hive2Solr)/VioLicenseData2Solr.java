package com.enjoyor.mtdap.synchrodata.fastsearch;

import com.enjoyor.mtdap.pojo.ChooseOnePojo;
import com.enjoyor.mtdap.util.SolrException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class VioLicenseData2Solr {
    private static final Logger LOG = LoggerFactory.getLogger(VioLicenseData2Solr.class);
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

    private static HashMap map1 = null;
    private static HashMap map2 = null;
    private static HashMap map3 = null;
    private static int queryCount;

    /*
    * 从hive中同步大数据到Solr中
    * 现场非现场从两张表得到
    * mtdap_nanchang.base_vio_violation where message_source = '1'非现场
    * mtdap_nanchang.base_vio_surveil 非现场
    * 第一次在全量导,第二次根据从Solr中拿到的最近的id,在hive中更新
    * 先实现全量导
    * 传入三个参数: url,0(现场表)或1(非现场),date(可为空);// 获取需要同步数据的日期
    * 2:
    * 将字典表放到HashMap(id,name)中 在hive查到值后做转义 存获取到id对应的value
    * 做转义
    * */
    public static void main(String[] args) throws SolrException, SQLException, ClassNotFoundException {


        if (args.length < 1) {
            System.err.println("The number of parameter should be one.");
            System.err.println("Usage:  cmd \"jdbc:fiber://fiberconfig=<fiber.xml_path>;defaultDriver=<driver_name>\" <spark_data_path> <hive_data_path>");
            System.exit(-1);
        }
        //2. 获取参数
        String url = args[0]; //jdbc 连接的url
        String date = null;
        String tableNum=null;
        /*三个参数 就不走着一段了*/
        if (args.length>=2) {
            tableNum=args[1];
        }
        /*第三个参数指定同步哪张表*/
        if(args.length>=3){
            date = args[2];// 获取需要同步数据的日期
        }

         //获取字典表
         map1 = DicConverPlace(url);
         map2 = DicConverClassify(url);
         map3 = DicConverBehavior(url);

        //3. 注册驱动包
        try {
            Class.forName("com.huawei.fiber.FiberDriver");
            System.out.println("加载FiberDriver");
        } catch (Exception e) {
            LOG.error("注册hive驱动包失败：" + e.getStackTrace());
            System.out.println("注册hive驱动包失败：" + e.getStackTrace());
        }

        //4.1 声明hive的jdbc对象
        Connection hiveConn = null;
        Statement hiveStmt = null;
        ResultSet hiveRs = null;

        try {
            //5.1 创建hive的Connection和Statement
            LOG.info("创建连接Hive的Connection和Statement...");
            System.out.println("创建连接Hive的Connection和Statement...");
            hiveConn = DriverManager.getConnection(url);
            hiveStmt = hiveConn.createStatement();
        } catch (Exception e) {
            LOG.error("创建Hive连接失败!!!" + e.getStackTrace());
            System.out.println("创建Hive连接失败!!!" + e.getStackTrace());
        }

        //1.选择hive execution engine
        LOG.info("正在切换hive execution engine... ");
        /*现场违法数据*/
        String hiveTableName =tableNum.toString().equals("0") ?
                "mtdap_nanchang.base_vio_violation":
                "mtdap_nanchang.base_vio_surveil";
        LOG.info("hiveTableName: "+hiveTableName);
        String sql = tableNum.toString().equals("0")?
                "select vio_num,vio_time,vio_place,license_num,license_type,vio_classify,vio_behavior from "+hiveTableName :
                "select record_id,vio_time,vio_place,license_num,license_type,vio_classify,vio_behavior from "+hiveTableName;
        System.out.println("sql: "+sql);
        try {
            String result =null;
            hiveStmt.execute("set fiber.execution.engine = hive");
            if (date != null && tableNum.toString().equals("0")) {
                hiveStmt.executeQuery(sql +" where   vio_time >= '" + date + "' and vio_time < date_add('" + date + "',1)"
                +" and message_source = '1'");
            }else if(date != null && !tableNum.toString().equals("0")){
                hiveStmt.executeQuery(sql +" where    vio_time >= '" + date + "' and vio_time < date_add('" + date + "',1)");
            }else{
                hiveStmt.executeQuery(sql);
            }
            LOG.info("result: "+result);
            hiveRs = hiveStmt.getResultSet();
        } catch (Exception e) {
            LOG.error("hive读取数据失败：" + e.getStackTrace());
        }

        VioLicenseData2Solr VioLicenseData2Solr = new VioLicenseData2Solr();

        VioLicenseData2Solr.initProperties();

        CloudSolrClient cloudSolrClient = null;

        long start = System.currentTimeMillis();
        long counter = 0;
        int i = 0;

        try {
            cloudSolrClient = VioLicenseData2Solr.getCloudSolrClient(VioLicenseData2Solr.zkHost);
            cloudSolrClient.setDefaultCollection(VioLicenseData2Solr.collectionName);
            Collection<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
            SolrInputDocument doc;
            ChooseOnePojo pojo = chooseOne(hiveTableName);
            while (hiveRs.next()) {
                /*System.out.println(hiveRs.getString(pojo.getId())
                        + " " + hiveRs.getString("vio_time")
                        + " " + hiveRs.getString("vio_place")
                        + " " + hiveRs.getString("license_num")
                        + " " + hiveRs.getString("license_type")
                        + " " + hiveRs.getString("vio_classify")
                        + " " + hiveRs.getString("vio_behavior")
                );*/
                doc = new SolrInputDocument();
                /*添加到对应的field中*/
                doc.addField("id", hiveRs.getString(pojo.getId()));
                //日期格式转换
                if (hiveRs.getString("vio_time") != null)
                    doc.addField("vio_time", ChangeTimeFormat(hiveRs.getTimestamp("vio_time").toString()));
                if (hiveRs.getString("vio_place") != null)
                    doc.addField("vio_place", DicConverPlace(map1, hiveRs.getString("vio_place")));
                if (hiveRs.getString("license_num") != null)
                    doc.addField("license_num", hiveRs.getString("license_num"));
                if (hiveRs.getString("license_type") != null)
                    doc.addField("license_type", hiveRs.getString("license_type"));
                if (hiveRs.getString("vio_classify") != null)
                    doc.addField("vio_classify", DicConverClassify(map2, hiveRs.getString("vio_classify")));
                if (hiveRs.getString("vio_behavior") != null)
                    doc.addField("vio_behavior", DicConverBehavior(map3, hiveRs.getString("vio_behavior")));
                doc.addField("be_from", pojo.getBe_from());
                System.out.println("doc: " + doc);
                boolean add = documents.add(doc);
                System.out.println("添加到documents结果 " + add + " 添加条数: " + counter);
                counter++;

                if (counter % 1000 == 0) {
                    i++;
                    LOG.info("---------- 正在插入 第 " + i + " 批次");
                    UpdateResponse updateResponse = cloudSolrClient.add(documents);
                    cloudSolrClient.commit();
                    long end = System.currentTimeMillis();
                    documents = new ArrayList<SolrInputDocument>();
                    LOG.info("1000 条耗费时间：" + (end - start));
                }
            }
            /*补数据 剩余的数据逐条补*/
            if (documents.size() > 0) {
                UpdateResponse updateResponse = cloudSolrClient.add(documents);
            }
            /*提交，将剩下的一次型提交到索引中*/
            cloudSolrClient.commit();
        } catch (Exception e) {
            LOG.error("solr插入数据失败：" + e.getStackTrace());
        } finally {
            try {
                if (hiveConn != null) {
                    hiveConn.close();
                }
            } catch (Exception e) {
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
        String proPath = System.getProperty("user.dir") + File.separator + "conf" + File.separator + "solr.properties";
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
        collectionName = "VioLicenseCollection";
        defaultConfigName = "confWithSchema";
        shardNum = 6;
        replicaNum = 2;
        principal = properties.getProperty("principal");

    }

    private CloudSolrClient getCloudSolrClient(String zkHost) throws SolrException {

        CloudSolrClient.Builder builder = new CloudSolrClient.Builder();
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

    private static ChooseOnePojo chooseOne(String hiveTableName){
        ChooseOnePojo pojo = new ChooseOnePojo();
        if(hiveTableName.equals("mtdap_nanchang.base_vio_violation")){
            pojo.setId("vio_num");
            pojo.setBe_from("现场");
        }else if(hiveTableName.equals("mtdap_nanchang.base_vio_surveil")){
            pojo.setId("record_id");
            pojo.setBe_from("非现场");
        }else{
            LOG.warn("hiveTableName is error");
        }
        return pojo;
    }
    /*日期格式转换*/
    private static String ChangeTimeFormat(String recordTime) {
        String sub1 = recordTime.substring(0, 10);//2018-07-29
        String sub2 = recordTime.substring(11);//00:08:57
        StringBuffer sb =new StringBuffer();
        StringBuffer buffer = sb.append(sub1).append("T").append(sub2).append("Z");
        String concat = buffer.toString();
        return concat;
    }
    /*字典转换
    * 实现:需要先从hive中查出三张字典表,分别放入到3个HashMap中(id,name)
    * 在result时,根据查出到id,获取到对应的name,存到Solr中
    * */

    private static HashMap DicConverPlace(String url)throws SQLException, ClassNotFoundException{
        Class.forName("com.huawei.fiber.FiberDriver");
        Connection hiveConn = null;
        Statement hiveStmt = null;
        ResultSet hiveRs = null;
        hiveConn = DriverManager.getConnection(url);
        hiveStmt = hiveConn.createStatement();
        String sql1 ="select * from dictionary.dic_vio_place";
        hiveStmt.execute("set fiber.execution.engine = hive");
        hiveStmt.executeQuery(sql1);
        hiveRs = hiveStmt.getResultSet();

        HashMap<String, String> map1 = new HashMap<>();
        while(hiveRs.next()){
            map1.put(hiveRs.getString("id"),hiveRs.getString("name"));
        }
        System.out.println(map1.size());
        return map1;
    }

    private static HashMap DicConverClassify(String url)throws SQLException, ClassNotFoundException{
        Class.forName("com.huawei.fiber.FiberDriver");
        Connection hiveConn2 = null;
        Statement hiveStmt2 = null;
        ResultSet hiveRs2 = null;
        hiveConn2 = DriverManager.getConnection(url);
        hiveStmt2 = hiveConn2.createStatement();
        String sql2 ="select * from dictionary.dic_vio_classify";
        hiveStmt2.execute("set fiber.execution.engine = hive");
        hiveStmt2.executeQuery(sql2);
        hiveRs2 = hiveStmt2.getResultSet();

        HashMap<String, String> map2  = new HashMap<>();
        while(hiveRs2.next()){
            map2.put(hiveRs2.getString("id"),hiveRs2.getString("name"));
        }
        System.out.println(map2.size());
        return map2;
    }

    private static HashMap DicConverBehavior(String url)throws SQLException, ClassNotFoundException{
        Class.forName("com.huawei.fiber.FiberDriver");
        Connection hiveConn3 = null;
        Statement hiveStmt3 = null;
        ResultSet hiveRs3 = null;
        hiveConn3 = DriverManager.getConnection(url);
        hiveStmt3 = hiveConn3.createStatement();
        String sql3 ="select * from dictionary.dic_vio_behavior";
        hiveStmt3.execute("set fiber.execution.engine = hive");
        hiveStmt3.executeQuery(sql3);
        hiveRs3 = hiveStmt3.getResultSet();

        HashMap<String, String> map3 = new HashMap<>();
        while(hiveRs3.next()){
            map3.put(hiveRs3.getString("id"),hiveRs3.getString("name"));
        }
        System.out.println(map3.size());
       return map3;
    }

    private  static int queryCount(String tableNum,String date)throws SQLException, ClassNotFoundException{
        int count =0;
        Class.forName("com.huawei.fiber.FiberDriver");
        Connection hiveConn = null;
        Statement hiveStmt = null;
        ResultSet hiveRs = null;
        hiveConn = DriverManager.getConnection("jdbc:fiber://fiberconfig=D:/fiber.xml;defaultDriver=hive");
        hiveStmt = hiveConn.createStatement();
        String sql = tableNum.toString().equals("0")?
                "select count(1)  from mtdap_nanchang.base_vio_violation where  vio_time>='"+date +
                        "' and vio_time < date_add('" + date+"',1)" +
                        " and message_source='1'" :
                "select count(1)  from mtdap_nanchang.base_vio_surveil  where   vio_time>='"+date +
                        "' and vio_time < date_add('" + date+"',1)";
        System.out.println("sql: "+sql);
        hiveStmt.execute("set fiber.execution.engine = hive");
        hiveStmt.executeQuery(sql);
        hiveRs = hiveStmt.getResultSet();
        while(hiveRs.next()){
            count = hiveRs.getInt(1);
        }
        return count;
    }

    private static String DicConverPlace(HashMap<String,String> map1,String id){
        String name =null;
        Set<String> keySet = map1.keySet();
        for(String key:keySet){
            if(id.equals(key)){
                name = map1.get(key);
            }
        }
        return name;
    }

    private static String DicConverClassify(HashMap<String,String> map2,String id){
        String name =null;
        Set<String> keySet = map2.keySet();
        for(String key:keySet){
            if(id.equals(key)){
                name = map2.get(key);
            }
        }
        return name;
    }

    private static String DicConverBehavior(HashMap<String,String> map3,String id){
        String name =null;
        Set<String> keySet = map3.keySet();
        for(String key:keySet){
            if(id.equals(key)){
                name = map3.get(key);
            }
        }
        return name;
    }

}
