package com.guoshuai.soa.traffic.rest.solr.util;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class SolrClientHelper0515 {
    private static HttpSolrClient httpSolrClient = null;
    private static CloudSolrClient cloudSolrClient = null;
    private static String clientType;
    private static String solrKbsEnable;
    private static int zkClientTimeout;
    private static int zkConnectTimeout;
    private static String zkHost;
    private static String zookeeperDefaultServerPrincipal;
    //collectionName 不能被所有实例共享
    private  static String collectionName;
    private  static String collectionName_suggest;
    private static String defaultConfigName;
    private static int shardNum;
    private static int replicaNum;
    private static String principal;
    private static SolrClient client;
    private static SolrClient suggest_client;
    private static final String PROPERTIES_PATH = "solr.properties";

    static {
        client = getClient();
        suggest_client = getSuggestClient();
    }

    public static SolrClient getClient() {
       // System.out.println("***** getClient is : "+client);
        try {
            if(null == client){
                initProperties();
                if(null != clientType && clientType.equals("cluster")){
                    return getCloudSolrClient();
                }else {
                    return getSolrClient();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return client;
    }

    public static SolrClient getSuggestClient() {
        try {
            if(null == suggest_client){
                //加一层判断 是搜车还是suggest
                initProperties_suggest();
                if(null != clientType && clientType.equals("cluster")){
                    return getCloudSolrClient();
                }else {
                    return getSolrClient();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return client;
    }

    /***
    * @Description: getSolrClient 单机solr
    * @return: org.apache.solr.client.solrj.impl.HttpSolrClient
    */
    public static HttpSolrClient getSolrClient()throws Exception {
        if(null == httpSolrClient)
            httpSolrClient = createSimpleSolrClient();
        return httpSolrClient;
    }

    /***
    * @Description: getCloudSolrClient  集群solr
    * @return: org.apache.solr.client.solrj.impl.CloudSolrClient
    */
    public static CloudSolrClient getCloudSolrClient()throws Exception {
        if(null == cloudSolrClient)
            cloudSolrClient = createCloudSolrClient();
        return cloudSolrClient;
    }

    /**
     * initProperties 初始化配置文件
     * @throws Exception
     */
    private static void initProperties() throws Exception {

        URL resource = SolrClientHelper0515.class.getClassLoader().getResource(PROPERTIES_PATH);
        if (resource == null) {
            throw new FileNotFoundException("没有找到指定的配置文件:" + PROPERTIES_PATH);
        }
        //加载配置文件
        InputStream input = SolrClientHelper0515.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
        Properties properties = new Properties();
        properties.load(input);
        clientType = properties.getProperty("client_type") == null ? "alone" :  properties.getProperty("client_type");
        solrKbsEnable = properties.getProperty("SOLR_KBS_ENABLED");
        zkClientTimeout = Integer.valueOf(properties.getProperty("zkClientTimeout"));
        zkConnectTimeout = Integer.valueOf(properties.getProperty("zkConnectTimeout"));
        zkHost = properties.getProperty("zkHost");
        //zookeeperDefaultServerPrincipal = properties.getProperty("ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL");
        collectionName = properties.getProperty("COLLECTION_NAME");
        defaultConfigName = properties.getProperty("DEFAULT_CONFIG_NAME");
        shardNum = Integer.valueOf(properties.getProperty("shardNum"));
        replicaNum = Integer.valueOf(properties.getProperty("replicaNum"));
        principal = properties.getProperty("principal");

        System.out.println("*****"+" initProperties init succ "+clientType +"  "+solrKbsEnable+" "+zkHost
        +" "+zookeeperDefaultServerPrincipal+" "+collectionName);
    }

    /*单机solr 单机集群不能共存,修改配置文件  */
    private static void initProperties_alone() throws Exception {

        URL resource = SolrClientHelper0515.class.getClassLoader().getResource(PROPERTIES_PATH);
        if (resource == null) {
            throw new FileNotFoundException("没有找到指定的配置文件:" + PROPERTIES_PATH);
        }
        //加载配置文件
        InputStream input = SolrClientHelper0515.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
        Properties properties = new Properties();
        properties.load(input);
        clientType = properties.getProperty("alone_client_type") == null ? "alone" :  properties.getProperty("client_type");
        solrKbsEnable = properties.getProperty("alone_KBS_ENABLED");
        zkClientTimeout = Integer.valueOf(properties.getProperty("alone_zkClientTimeout"));
        zkConnectTimeout = Integer.valueOf(properties.getProperty("alone_zkConnectTimeout"));
        zkHost = properties.getProperty("alone_zkHost");
        zookeeperDefaultServerPrincipal = properties.getProperty("alone_ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL");
        collectionName = properties.getProperty("alone_COLLECTION_NAME");
        defaultConfigName = properties.getProperty("alone_DEFAULT_CONFIG_NAME");
        shardNum = Integer.valueOf(properties.getProperty("alone_shardNum"));
        replicaNum = Integer.valueOf(properties.getProperty("alone_replicaNum"));
        principal = properties.getProperty("alone_principal");

        System.out.println("*****"+"init succ "+clientType +"  "+solrKbsEnable+" "+zkHost
                +" "+zookeeperDefaultServerPrincipal+" "+collectionName);
    }

    /*号牌智能提醒 初始化*/
    private static void initProperties_suggest() throws Exception {

        URL resource = SolrClientHelper0515.class.getClassLoader().getResource(PROPERTIES_PATH);
        if (resource == null) {
            throw new FileNotFoundException("没有找到指定的配置文件:" + PROPERTIES_PATH);
        }
        //加载配置文件
        InputStream input = SolrClientHelper0515.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
        Properties properties = new Properties();
        properties.load(input);
        clientType = properties.getProperty("client_type") == null ? "alone" :  properties.getProperty("client_type");
        solrKbsEnable = properties.getProperty("suggest_SOLR_KBS_ENABLED");
        zkClientTimeout = Integer.valueOf(properties.getProperty("suggest_zkClientTimeout"));
        zkConnectTimeout = Integer.valueOf(properties.getProperty("suggest_zkConnectTimeout"));
        zkHost = properties.getProperty("suggest_zkHost");
        zookeeperDefaultServerPrincipal = properties.getProperty("suggest_ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL");
        collectionName = properties.getProperty("suggest_COLLECTION_NAME");
        defaultConfigName = properties.getProperty("suggest_DEFAULT_CONFIG_NAME");
        shardNum = Integer.valueOf(properties.getProperty("suggest_shardNum"));
        replicaNum = Integer.valueOf(properties.getProperty("suggest_replicaNum"));
        principal = properties.getProperty("suggest_principal");

        System.out.println("*****"+" initProperties_suggest init succ "+clientType +"  "+solrKbsEnable+" "+zkHost
                +" "+zookeeperDefaultServerPrincipal+" "+collectionName);
    }

    /**
     * 获取cloude中包含的所有collection
     *
     * @param cloudSolrClient
     * @return
     */
    public  static  List<String> queryAllCollections(CloudSolrClient cloudSolrClient) {
        CollectionAdminRequest.List list = new CollectionAdminRequest.List();
        CollectionAdminResponse listRes = null;

        try {
            listRes = list.process(cloudSolrClient);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> collectionNames = (List<String>) listRes.getResponse().get("collections");
        return collectionNames;
    }

    public static List<SolrQuery.SortClause> getSortClause(String sortStr) {
        List<SolrQuery.SortClause> sortClauseList = new ArrayList<SolrQuery.SortClause>();
        String[] clause = sortStr.split(",");
        for (String str : clause) {
            String[] ele = str.trim().split(" ");
            SolrQuery.SortClause sortClause = new SolrQuery.SortClause(ele[0], ele[1]);
            sortClauseList.add(sortClause);
        }
        return sortClauseList;
    }


    private static HttpSolrClient createSimpleSolrClient() {
        try {

            System.out.println("server address:" + zkHost);
            httpSolrClient = new HttpSolrClient(zkHost );
            httpSolrClient.setConnectionTimeout(30000);
            httpSolrClient.setDefaultMaxConnectionsPerHost(100);
            httpSolrClient.setMaxTotalConnections(100);
            httpSolrClient.setSoTimeout(30000);
            return httpSolrClient;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * 从zookeeper中获取cloude信息，并建立client对象
     * @return
     * @throws Exception
     */
    private static CloudSolrClient createCloudSolrClient() throws Exception {
        //initProperties();
        System.out.println("-------- createCloudSolrClient()");
        CloudSolrClient.Builder builder = new CloudSolrClient.Builder();

        builder.withZkHost(zkHost);
        cloudSolrClient = builder.build();

        cloudSolrClient.setZkClientTimeout(zkClientTimeout);
        cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
        cloudSolrClient.setDefaultCollection(collectionName);
        cloudSolrClient.connect();
        return cloudSolrClient;
    }

    public static void addDocuments(Collection<SolrInputDocument> docs){
        System.out.println("======================add many docs  ===================");
        try {
           // UpdateResponse rsp = getClient().add(docs);
            UpdateResponse rsp = client.add(docs);
            System.out.println("Add doc size: " + docs.size() + " resultStatus:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
            //TODO Add doc size: 1 resultStatus:0 Qtime:0
            //如果没有commit  既不软提交也不硬提交 只有重新reload数据才展示出来
            //UpdateResponse rspcommit = getClient().commit();
            UpdateResponse rspcommit = client.commit();
            //System.out.println("commit doc to index" + " resultStatus:" + rspcommit.getStatus() + " Qtime:" + rspcommit.getQTime());

        } catch (Exception  e) {
            e.printStackTrace();
        }
    }

    public static void addDocument(SolrInputDocument doc){
        System.out.println("======================add one docs  ===================");
        try {
            UpdateResponse rsp = client.add(doc);
            System.out.println("Add one doc resultStatus:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());

           // UpdateResponse rspcommit = client.commit();
           // System.out.println("commit one doc to index resultStatus:" + rspcommit.getStatus() + " Qtime:" + rspcommit.getQTime());

        } catch (Exception  e) {
            System.out.println("addDocument error : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteById(String id) {
        System.out.println("======================deleteById ===================");
        try {
            UpdateResponse rsp = client.deleteById(id);
            client.commit();
            System.out.println("delete id:" + id + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteByQuery(String queryCon) {
        System.out.println("======================deleteByQuery ===================");
        try {
            UpdateRequest commit = new UpdateRequest();
            commit.deleteByQuery(queryCon);
            commit.setCommitWithin(5000);
            commit.process(client);
            System.out.println("url:"+commit.getPath()+"\t xml:"+commit.getXML()+" method:"+commit.getMethod());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void queryDocs(String queryCon) {
        SolrQuery params = new SolrQuery();
        System.out.println("======================query===================");
        //params.set("q", "addparam_s:*");
        params.set("q", queryCon);
        params.set("start", 0);
        params.set("rows", 10);
        params.set("sort", "passingTime desc");

        try {
            QueryResponse rsp = client.query(params);
            SolrDocumentList docs = rsp.getResults();
            System.out.println("查询内容:" + params);
            System.out.println("文档数量：" + docs.getNumFound());
            System.out.println("查询花费时间:" + rsp.getQTime());

            System.out.println("------query data:------");
            for (SolrDocument doc : docs) {
                // 多值查询
               /* @SuppressWarnings("unchecked")
                List<String> collectTime = (List<String>) doc.getFieldValue("collectTime");
                String clientmac_s = (String) doc.getFieldValue("clientmac_s");
                System.out.println("collectTime:" + collectTime + "\t clientmac_s:" + clientmac_s);*/
            }
            System.out.println("-----------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
