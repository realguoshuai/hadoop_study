package com.guoshuai.soa.traffic.core.bigdata.util;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IDEA
 * author 郭帅
 * date 13:08 2018/12/24
 */
public class SolrConnectionPool implements Serializable {
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
    private static int unAvailableTimes;

    private static Logger logger = Logger.getLogger(SolrConnectionPool.class);
    private static SolrConnectionPool instance = new SolrConnectionPool();
    private static CloudSolrClient cloudSolrClient = null;
    /*ConcurrentHashMap类是Java并发包中提供的一个线程安全且高效的HashMap实现*/
    private static Map<String, LinkedBlockingQueue<CloudSolrClient>> poolMap = new ConcurrentHashMap<>();
    private static LinkedBlockingQueue<CloudSolrClient> connectionPools;


    public static SolrConnectionPool getInstance() {
        return SolrConnectionPool.instance;
    }

    private void initProperties(String collectName) throws Exception {
        new SolrHealthCheckThread().run();
        Properties properties = new Properties();
        String proPath = System.getProperty("user.dir") + File.separator + "conf" + File.separator + "solr.properties";

        try {
            properties.load(new FileInputStream(new File(proPath)));
        } catch (Exception e) {
            throw new Exception("Failed to load properties file : " + proPath);
        }
        clientType = properties.getProperty(collectName + "_client_type") == null ? "alone" : properties.getProperty(collectName + "_client_type");
        clientType = properties.getProperty("client_type") == null ? "alone" : properties.getProperty("client_type");
        solrKbsEnable = properties.getProperty("SOLR_KBS_ENABLED");
        zkClientTimeout = Integer.valueOf(properties.getProperty("zkClientTimeout"));
        zkConnectTimeout = Integer.valueOf(properties.getProperty("zkConnectTimeout"));
        zkHost = properties.getProperty("zkHost");
        zookeeperDefaultServerPrincipal = properties.getProperty("ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL");
        collectionName = collectName != null ? collectName : properties.getProperty("COLLECTION_NAME");
        defaultConfigName = properties.getProperty("DEFAULT_CONFIG_NAME");
        shardNum = Integer.valueOf(properties.getProperty("shardNum"));
        replicaNum = Integer.valueOf(properties.getProperty("replicaNum"));
        principal = properties.getProperty("principal");
    }

    public LinkedBlockingQueue<CloudSolrClient> getConnectionPool(String collectionName, int poolSize) throws Exception {
        initProperties(collectionName);
        if (null == poolMap.get(collectionName)) {
            connectionPools = new LinkedBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHost).build();
                cloudSolrClient.setDefaultCollection(collectionName);
                cloudSolrClient.setZkClientTimeout(zkClientTimeout);
                cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
                cloudSolrClient.connect();
                connectionPools.add(cloudSolrClient);
            }
            poolMap.put(collectionName, connectionPools);
        }
        return poolMap.get(collectionName);
    }

    public boolean isExistConnection() {
        boolean isExist = false;
        if (null != cloudSolrClient) {
            isExist = true;
        }
        return isExist;
    }

    public CloudSolrClient getConnection(String collectionName) throws Exception {
        initProperties(collectionName);
        if (null == cloudSolrClient || null == cloudSolrClient.getZkStateReader()) {
            cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHost).build();
            cloudSolrClient.setDefaultCollection(collectionName);
            cloudSolrClient.setZkClientTimeout(zkClientTimeout);
            cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
            cloudSolrClient.connect();
            logger.info("connect success!");
        }
        /*在调用连接前先判断连接池中是否存在连接,有则使用,没有就创建一个连接池*/
        /*if(isExistConnection() && poolMap.get(collectionName).size()>0){
            connectionPools = poolMap.get(collectionName);
            for(CloudSolrClient cloudSolrClient:connectionPools){
                return cloudSolrClient;
            }
        }else{
            connectionPools = getConnectionPool(collectionName, 5);
            for( CloudSolrClient cloudSolrClient:connectionPools){
                //System.out.println(cloudSolrClient);
                return cloudSolrClient;
            }
        }*/
        return cloudSolrClient;
    }

    public CloudSolrClient getSimpleConnection(String collectionName) throws Exception {
        initProperties(collectionName);
        if(isExistConnection() && poolMap.get(collectionName).size()>0){
            connectionPools = poolMap.get(collectionName);
            for(CloudSolrClient cloudSolrClient:connectionPools){
                return cloudSolrClient;
            }
        }else{
            connectionPools = getConnectionPool(collectionName, 5);
            for( CloudSolrClient cloudSolrClient:connectionPools){
                return cloudSolrClient;
            }
        }
        return cloudSolrClient;
    }

    public void clearPool() throws InterruptedException {
        for (LinkedBlockingQueue<CloudSolrClient> pool : poolMap.values()) {
            while (!pool.isEmpty()) {
                CloudSolrClient cloudSolrClient = pool.take();
                try {
                    cloudSolrClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void healthCheck() {
        try {
            unAvailableTimes = 0;
            System.out.println("healthCheck is  runing");
            if (unAvailableTimes >= 1) {
                System.out.print("solr cluster unavailable, will reset solr connection pool.");
                clearPool();
                unAvailableTimes = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            unAvailableTimes++;
        }
    }

    class SolrHealthCheckThread implements Runnable {
        public void run() {
            /*新开一个线程巡检*/
            healthCheck();
        }
    }

    public static void main(String[] args) {
        try {
            LinkedBlockingQueue<CloudSolrClient> cloudSolrClients = new SolrConnectionPool().getConnectionPool("kkrecords", 5);
            System.out.println(cloudSolrClients.size());
            for (CloudSolrClient cloudSolrClient : cloudSolrClients) {
                System.out.println(cloudSolrClient);
            }

            CloudSolrClient cloudSolrClient = new SolrConnectionPool().getConnection("kkrecords");
            System.out.println("getConnection " + cloudSolrClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
