/*
package com.guoshuai.soa.traffic.rest.solr.util;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

*/
/**
 * Created by wulei on 2017/12/7.
 *
 * @Description:
 *//*

public  class SolrClientHelper_bak {
    public static HttpSolrClient client = null;
    public static CloudSolrClient cloudSolrClient = null;
    private static final Logger logger = LoggerFactory.getLogger(SolrClientHelper_bak.class);
    private static String clientType;
    private static String solrKbsEnable;
    private static int zkClientTimeout;
    private static int zkConnectTimeout;
    private static String zkHost;
    private static String zookeeperDefaultServerPrincipal;
    public static String collectionName;
    private static String defaultConfigName;
    private static int shardNum;
    private static int replicaNum;
    private static String principal;
    private static String solrUrl;

    public static SolrClient getClient() throws Exception {
        initProperties();
        if (null != clientType && clientType.equals("cluster")) {
            return getCloudSolrClient();
        } else {
            return getSolrClient();
        }
    }

    //自定义collection名称
    public static SolrClient getClient(String collectionName) throws Exception {
        initProperties(collectionName);//
        if (null != clientType && clientType.equals("cluster")) {
            logger.info("collection name : " + getCloudSolrClient().getDefaultCollection());
            return getCloudSolrClient();
        } else {
            return getSolrClient();
        }
    }

    public static HttpSolrClient getSolrClient() throws Exception {
        if (null == client)
            client = createSimpleSolrClient();
        return client;
    }

    public static CloudSolrClient getCloudSolrClient() throws Exception {
        if (null == cloudSolrClient)
            cloudSolrClient = createCloudSolrClient();
        return cloudSolrClient;
    }

    */
/**
     * 获取cloude中包含的所有collection
     *
     * @param cloudSolrClient
     * @return
     *//*

    public static List<String> queryAllCollections(CloudSolrClient cloudSolrClient) {
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

    private static void initProperties() throws Exception {
        Properties properties = new Properties();
        InputStream in = SolrQuery.class.getResourceAsStream("/solr.properties");

        try {
            properties.load(new BufferedInputStream(in));
        } catch (Exception e) {
            throw new Exception("Failed to load properties file : " + in);
        }
        clientType = properties.getProperty("client_type") == null ? "alone" : properties.getProperty("client_type");
        System.out.println("***** "+clientType);
        solrKbsEnable = properties.getProperty("SOLR_KBS_ENABLED");
        zkClientTimeout = Integer.valueOf(properties.getProperty("zkClientTimeout"));
        zkConnectTimeout = Integer.valueOf(properties.getProperty("zkConnectTimeout"));
        zkHost = properties.getProperty("zkHost");
        zookeeperDefaultServerPrincipal = properties.getProperty("ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL");
        collectionName = properties.getProperty("COLLECTION_NAME");
        defaultConfigName = properties.getProperty("DEFAULT_CONFIG_NAME");
        shardNum = Integer.valueOf(properties.getProperty("shardNum"));
        replicaNum = Integer.valueOf(properties.getProperty("replicaNum"));
        principal = properties.getProperty("principal");

        //solrUrl = properties.getProperty("solrUrl");
    }

    //自定义collection名称
    private static void initProperties(String collectName) throws Exception {
        Properties properties = new Properties();
        InputStream in = SolrQuery.class.getResourceAsStream("/solr.properties");
        try {
            properties.load(new BufferedInputStream(in));
        } catch (Exception e) {
            throw new Exception("Failed to load properties file : " + in);
        }
        clientType = properties.getProperty(collectName + "_client_type") == null ? "alone" : properties.getProperty(collectName + "_client_type");


        clientType = properties.getProperty("client_type") == null ? "alone" : properties.getProperty("client_type");

        solrKbsEnable = properties.getProperty("SOLR_KBS_ENABLED");
        zkClientTimeout = Integer.valueOf(properties.getProperty("zkClientTimeout"));
        zkConnectTimeout = Integer.valueOf(properties.getProperty("zkConnectTimeout"));
        zkHost = properties.getProperty("zkHost");
        zookeeperDefaultServerPrincipal = properties.getProperty("ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL");
        collectionName = collectName!=null? collectName:properties.getProperty("COLLECTION_NAME");
        defaultConfigName = properties.getProperty("DEFAULT_CONFIG_NAME");
        shardNum = Integer.valueOf(properties.getProperty("shardNum"));
        replicaNum = Integer.valueOf(properties.getProperty("replicaNum"));
        principal = properties.getProperty("principal");
        //solrUrl = properties.getProperty("solrUrl");
    }

    */
/*private static void initSimpleProperties() throws Exception {
        Properties properties = new Properties();
        String proPath = System.getProperty("user.dir") + File.separator + "conf" + File.separator + "solr.properties";

        try {
            properties.load(new FileInputStream(new File(proPath)));
        } catch (Exception e) {
            throw new Exception("Failed to load properties file : " + proPath);
        }
        solrUrl = properties.getProperty("solrUrl");
    }*//*



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
            //initSimpleProperties();
            logger.info("server address:" + zkHost);
            client = new HttpSolrClient(zkHost);
            client.setConnectionTimeout(30000);
            client.setDefaultMaxConnectionsPerHost(100);
            client.setMaxTotalConnections(100);
            client.setSoTimeout(30000);
            return client;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    */
/**
     * 从zookeeper中获取cloude信息，并建立client对象
     *
     * @return
     * @throws Exception
     *//*

    private static CloudSolrClient createCloudSolrClient() throws Exception {
        long start = System.currentTimeMillis();
        CloudSolrClient.Builder builder = new CloudSolrClient.Builder();

        builder.withZkHost(zkHost);
        cloudSolrClient = builder.build();

        cloudSolrClient.setZkClientTimeout(zkClientTimeout);
        cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
        cloudSolrClient.setDefaultCollection(collectionName);
        cloudSolrClient.connect();

        logger.info("createCloudSolrClient: "+(System.currentTimeMillis()-start)+"ms");
        return cloudSolrClient;
    }
}
*/
