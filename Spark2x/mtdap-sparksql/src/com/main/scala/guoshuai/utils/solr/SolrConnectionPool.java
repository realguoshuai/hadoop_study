package com.main.scala.guoshuai.utils.solr;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description
 * Created with guoshuai
 * date 2019/9/24 13:55
 */
public class SolrConnectionPool {
    private static int zkConnectTimeout = 60000;
    private static int zkClientTimeout = 60000;
    private static Map<String, LinkedBlockingQueue<CloudSolrClient>> poolMap = new ConcurrentHashMap<>();
    private static CloudSolrClient cloudSolrClient = null;
    private static SolrConnectionPool instance = new SolrConnectionPool();

    public static SolrConnectionPool getInstance() {
        return SolrConnectionPool.instance;
    }

    public synchronized static boolean hasCollection(String zkHost, String collection) {
        CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHost).build();
        cloudSolrClient.setDefaultCollection(collection);
        cloudSolrClient.setZkClientTimeout(zkClientTimeout);
        cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
        cloudSolrClient.connect();
        ZkStateReader zkStateReader = cloudSolrClient.getZkStateReader();
        ClusterState cloudState = zkStateReader.getClusterState();
        boolean existCollection = cloudState.hasCollection(collection);
        try {
            cloudSolrClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existCollection;
    }

    public synchronized LinkedBlockingQueue<CloudSolrClient> getConnectionPool(String zkHost, String collection, final int poolSize) {
        if (poolMap.get(collection) == null && hasCollection(zkHost, collection)) {
            LinkedBlockingQueue<CloudSolrClient> cloudSolrClientList = new LinkedBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHost).build();
                cloudSolrClient.setDefaultCollection(collection);
                cloudSolrClient.setZkClientTimeout(zkClientTimeout);
                cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
                cloudSolrClient.connect();
                cloudSolrClientList.add(cloudSolrClient);
            }
            poolMap.put(collection, cloudSolrClientList);
        }
        return poolMap.get(collection);
    }

    public synchronized CloudSolrClient getConnection(String zkHost, String collection) {
        if (cloudSolrClient == null || cloudSolrClient.getZkStateReader() == null) {
            if (hasCollection(zkHost, collection)) {
                cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHost).build();
                cloudSolrClient.setDefaultCollection(collection);
                cloudSolrClient.setZkClientTimeout(zkClientTimeout);
                cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
                cloudSolrClient.connect();
            }
        }
        return cloudSolrClient;
    }

    public void shutdown() throws InterruptedException {
        for (LinkedBlockingQueue<CloudSolrClient> pool : poolMap.values()) {
            while (!pool.isEmpty()) {
                CloudSolrClient cloudSolrClient = pool.take();
                try {
                    cloudSolrClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
