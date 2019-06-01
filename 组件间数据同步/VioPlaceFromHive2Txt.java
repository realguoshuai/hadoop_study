package test;

import com.guoshuai.mtdap.pojo.ChooseOnePojo;
import com.guoshuai.mtdap.util.SolrException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class VioPlaceFromHive2Txt {
    private static final Logger LOG = LoggerFactory.getLogger(VioPlaceFromHive2Txt.class);
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

        /*将map写入到TXT文本*/
        try {
            String line = System.getProperty("line.separator");
            StringBuffer str = new StringBuffer();
            FileWriter fw = new FileWriter("D:\\dict.txt", true);
            Set set = map1.entrySet();
            Iterator iter = set.iterator();
            int i=1;
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                /*只要map中的值*/
                str.append(entry.getValue()).append(line);
                System.out.println("添加 "+i+"次 ");
                i++;
            }
            fw.write(str.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        //collectionName = "VioSuggestTest";
        defaultConfigName = "confWithSchema";
        //defaultConfigName = "VioLicenseConf";
        shardNum = 6;
        //shardNum = 2;
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

    /*日期格式转换*/
    private static String ChangeTimeFormat(String recordTime) {
        String sub1 = recordTime.substring(0, 10);//2018-07-29
        String sub2 = recordTime.substring(11);//00:08:57
        StringBuffer sb =new StringBuffer();
        StringBuffer buffer = sb.append(sub1).append("T").append(sub2).append("Z");
        String concat = buffer.toString();
        return concat;
    }

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
            /*拿到所有的 id,name*/
            map1.put(hiveRs.getString("id"),hiveRs.getString("name"));
        }
        System.out.println(map1.size());
        return map1;
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
}
