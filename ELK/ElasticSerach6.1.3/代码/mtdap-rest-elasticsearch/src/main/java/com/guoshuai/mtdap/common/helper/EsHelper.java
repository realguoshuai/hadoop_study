package com.guoshuai.mtdap.common.helper;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.ietf.jgss.GSSException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * es 工具类  Rest Client为推荐使用，
 * TransportClient高版本计划废弃(网上版本较多),这里不用
 */
public class EsHelper {

    private static final Logger logger = LoggerFactory.getLogger(EsHelper.class);
    private static String isSecureMode;
    private static String esServerHost;
    private static HttpHost[] hostsArray;
    private static List<HttpHost> hosts = new ArrayList<HttpHost>();
    private static int MaxRetryTimeoutMillis;
    private static String index;
    private static String type;
    private static long id;
    private static int shardNum;
    private static int replicaNum;
    private static int ConnectTimeout;
    private static int SocketTimeout;
    private static RestClient restClient = null;
    private static SearchResponse searchResponse = null;
    private static final String PROPERTIES_PATH = "es.properties";

    //单机
    /*private static RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(
            new HttpHost("x.x.x.x", 9200, "http")));*/
    //集群
    private static RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("x.x.x.19", 24148, "https"),
                (new HttpHost("x.x.x.196",24100,"https")),
            (new HttpHost("x.x.x.197",24100,"https")),
            (new HttpHost("x.x.x.198",24100,"https"))));

    public EsHelper() {
        initProperties();
        restClient = getRestClient();
    }


    public static void initProperties() {

        URL resource = EsHelper.class.getClassLoader().getResource(PROPERTIES_PATH);
        if (resource == null) {
            try {
                throw new FileNotFoundException("没有找到指定的配置文件:" + PROPERTIES_PATH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //加载配置文件
        InputStream input = EsHelper.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String schema = "https";
        esServerHost = properties.getProperty("EsServerHost");
        MaxRetryTimeoutMillis = Integer.valueOf(properties.getProperty("MaxRetryTimeoutMillis"));
        ConnectTimeout = Integer.valueOf(properties.getProperty("ConnectTimeout"));
        SocketTimeout = Integer.valueOf(properties.getProperty("SocketTimeout"));
        isSecureMode = properties.getProperty("isSecureMode");
        index = properties.getProperty("index");
        type = properties.getProperty("type");
        id = Long.valueOf(properties.getProperty("id"));
        shardNum = Integer.valueOf(properties.getProperty("shardNum"));
        replicaNum = Integer.valueOf(properties.getProperty("replicaNum"));

        if (("false").equals(isSecureMode)) {
            schema = "http";
        }
        String[] hostArray1 = esServerHost.split(",");
        for (String host : hostArray1) {
            String[] ipPort = host.split(":");
            HttpHost hostNew = new HttpHost(ipPort[0], Integer.valueOf(ipPort[1]), schema);
            hosts.add(hostNew);
        }
        hostsArray = hosts.toArray(new HttpHost[]{});

        logger.info("EsServerHost:" + esServerHost);
        //logger.info("MaxRetryTimeoutMillis:" + MaxRetryTimeoutMillis);
        logger.info("index:" + index);
        //logger.info("shardNum:" + shardNum);
        //logger.info("replicaNum:" + replicaNum);
        //logger.info("isSecureMode:" + isSecureMode);
        //logger.info("type:" + type);
        //logger.info("id:" + id);
    }

    /**
     * set configurations about authentication.
     */
    public static void setSecConfig() {
        String krb5ConfFile = EsHelper.class.getClassLoader().getResource("krb5.conf").getFile();
        logger.info("krb5ConfFile:" + krb5ConfFile);
        System.setProperty("java.security.krb5.conf", krb5ConfFile);

        String jaasPath = EsHelper.class.getClassLoader().getResource("jaas.conf").getFile();
        logger.info("jaasPath:" + jaasPath);
        System.setProperty("java.security.auth.login.config", jaasPath);
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
       // System.setProperty("es.security.indication", "true");
    }

    /**
     * get one rest client instance.
     *
     * @return
     * @throws Exception
     */
    public static RestClient getRestClient() {
        RestClientBuilder builder = null;
        RestClient restClient = null;
        if (isSecureMode.equals("true")) {
            setSecConfig();
            builder = RestClient.builder(hostsArray);
        } else {
            builder = RestClient.builder(hostsArray);
        }
        Header[] defaultHeaders = new Header[]{new BasicHeader("Accept", "application/json"),
                new BasicHeader("Content-type", "application/json")};

        builder = builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder.setConnectTimeout(ConnectTimeout).setSocketTimeout(SocketTimeout);
            }
        }).setMaxRetryTimeoutMillis(MaxRetryTimeoutMillis);

        builder.setDefaultHeaders(defaultHeaders);
        restClient = builder.build();
        logger.info("The RestClient has been created !");
        return restClient;
    }


    /**
     * query the cluster's health
     *
     * @param
     * @throws Exception
     */
    public static void queryEsClusterHealth() throws Exception {
        Response rsp = null;
        try {
            rsp = restClient.performRequest("GET", "/_cluster/health");
            String result = EntityUtils.toString(rsp.getEntity());
            logger.info("QueryEsClusterHealth, result is : {}", result);
        } catch (Exception e) {
            if (e instanceof GSSException) {
                if (restClient != null) {
                    restClient.close();
                }
                restClient = getRestClient();
                queryEsClusterHealth();
            } else {
                throw e;
            }
        }
    }

    /**
     * create one index with shard number.
     * @param
     * @throws IOException
     */
    public static void createIndexWithShardNum() throws Exception {
        try {
            Map<String, String> params = Collections.singletonMap("pretty", "true");
            String jsonString = "{" + "\"settings\":{" + "\"number_of_shards\":\"" + shardNum + "\","
                    + "\"number_of_replicas\":\"" + replicaNum + "\"" + "}}";

            HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
            Response response = null;
            response = restClient.performRequest("PUT", "/" + index, params, entity);
            logger.info("CreateIndexWithShardNum,response entity is :{} ", EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            if (e instanceof GSSException) {
                if (restClient != null) {
                    restClient.close();
                }
                restClient = getRestClient();
                createIndexWithShardNum();
            } else {
                throw e;
            }
        }


    }


    /**
     * put one doc to the index
     *
     * @param
     * @throws Exception
     */
    public static void putData() throws Exception {
        String jsonString1 = "{" + "\"name\":\"\"," + "\"author\":\"lisi\","
                + "\"pubinfo\":\"上海\"," + "\"pubtime\":\"2019-07-10\","
                + "\"desc\":\"hello world.\""
                + "}";

        System.out.println(jsonString1);
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        HttpEntity entity = new NStringEntity(jsonString1, ContentType.APPLICATION_JSON);
        Response response = null;

        try {
            response = restClient.performRequest("PUT", "/" + index + "/" + type + "/" + System.nanoTime(), params, entity);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode() || HttpStatus.SC_CREATED == response.getStatusLine().getStatusCode()) {
                logger.info("PutData,response entity is : " + EntityUtils.toString(response.getEntity()));
            } else {
                logger.error("PutData failed.");
            }
        } catch (Exception e) {
            if (e instanceof GSSException) {
                if (restClient != null) {
                    restClient.close();
                }
                restClient = getRestClient();
                putData();

            } else {
                throw e;
            }
        }
    }

    /**
     * 接入kafka json 单条 入库  put
     */
    public static void putData(String json) throws Exception {
        //initProperties();
        //restClient = getRestClient();


        String jsonString1 = "{" + "\"name\":\"es\"," + "\"author\":\"lisi\","
                + "\"pubinfo\":\"上海\"," + "\"pubtime\":\"2019-07-10\","
                + "\"desc\":\"hello world.\""
                + "}";
        //System.out.println(jsonString1);
        //System.out.println("-----------------"+json);
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        //JSONObject map = JSONObject.fromObject(json);

        //HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
        HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
        Response response = null;

        try {
            if (entity != null) {
                //System.out.println("*******"+map);
                response = restClient.performRequest("PUT", "/" + index + "/" + type + "/" + System.nanoTime(), params, entity);
            }
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode() || HttpStatus.SC_CREATED == response.getStatusLine().getStatusCode()) {
                //logger.info("PutData,response entity is : " + EntityUtils.toString(response.getEntity()));
            } else {
                logger.error("PutData failed.");
            }
        } catch (Exception e) {
            if (e instanceof GSSException) {
                if (restClient != null) {
                    restClient.close();
                }
                restClient = getRestClient();
                putData();

            } else {
                throw e;
            }
        }
    }



    /**
     * 使用rest client批量写入数据
     * param   接入kafka数据 经过change2EsJson转成的json格式传入
     * 控制 5s  或 50000条  bulk批量提交
     * @throws Exception
     */
    public static void bulk3(LinkedList<String> linkedListJson) {

        Long count = 0L;
        StringEntity entity = null;
        long start = System.currentTimeMillis();
        //请求前缀 元数据
        String str = "{ \"index\" : { \"_index\" : \"" + "kkrecords" + "\", \"_type\" :  \"" + "kkrecords" + "\"} }";
        StringBuffer buffer = new StringBuffer();
        for (int j = 0; j < linkedListJson.size(); j++) {
            count++;
            buffer.append(str).append("\n");
            buffer.append(linkedListJson.get(j)).append("\n");
        }
        //数据打印
        //System.out.println(buffer);


        entity = new StringEntity(buffer.toString(), ContentType.APPLICATION_JSON);
        entity.setContentEncoding("UTF-8");
        Response rsp = null;
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        try {
            //rest 批量提交
            //{"point_id":"3604003003360303","passing_time":"2019-07-22T21:43:14Z","speed_limit_min":"0","license_type":"VEHPLATETYPE02","region_id":"360402","receive_time":"2019-07-22 21:43:13","veh_type":"VEHSUBTYPE146","insert_time":"2019-07-23 09:24:42","lane_number":"2","speed":"18","data_source":"OFFSITESCENE02","license_number":"赣G8M688","veh_color":"VEHCOLOR10","license_color":"PLATECOLOR02","id":"214331668700783","veh_pic":"http://172.100.100.6:8081/20190722bucketNamea/1001195$1$0$0/20190722/16/5815-370510-0.jpg","speed_limit_max":"0","veh_length":"18.0","direction":"ROADDIRECT03"}

            rsp = restClient.performRequest("PUT", "/_bulk", params, entity); //
            if (HttpStatus.SC_OK == rsp.getStatusLine().getStatusCode()) {
                System.out.println("======================add many docs===================");
                System.out.println("Bulk successful. Add doc size: "+count);
                System.out.println("commit  once batch time: "+(System.currentTimeMillis() - start)+"ms");
            } else {
                logger.error("Bulk failed.");
            }
            //logger.info("Bulk response entity is : " + EntityUtils.toString(rsp.getEntity()));
        } catch (Exception e) {
            logger.error("Bulk failed, exception occurred.", e);
        }
    }

    /**
     * query all datas of one index.
     *
     * @param
     * @throws Exception
     */
    public static void queryData() throws Exception {
        try {
            Map<String, String> params = Collections.singletonMap("pretty", "true");
            Response rsp = restClient.performRequest("GET", "/" + index + "/" + type + "/" + id, params);
            logger.info("QueryData,response entity is : " + EntityUtils.toString(rsp.getEntity()));
        } catch (Exception e) {
            if (e instanceof GSSException) {
                if (restClient != null) {
                    restClient.close();
                }
                restClient = getRestClient();
                queryData();
            } else {
                throw e;
            }
        }
    }

    public static void query() {
        //1:创建搜索请求
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.types(type);

        //2:用SearchSourceBuilder来构造查询请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //构造QueryBuilder

        //执行查询
        sourceBuilder.query(QueryBuilders.termQuery("license_number", "赣GX8933"));
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //是否返回_source字段
        sourceBuilder.fetchSource(false);

        //设置返回哪些字段

        //排序
        //sourceBuilder.sort(new FieldSortBuilder("passing_time").order(SortOrder.DESC));

        //将请求体加入到请求中
        searchRequest.source(sourceBuilder);


        //3:发送请求
        /*try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //4:处理响应 SearchResponse
        RestStatus status = searchResponse.status();
        logger.info("*****" + status);

    }

    /**
     * 复杂查询
     * @return
     */
    public static Map<String, Object> getEsData(String field1,String value1) {
        HashMap<String, Object> infoMap = new HashMap<>();

        StringBuilder requestBody = new StringBuilder();
        requestBody.append("{\"size\":\"").append(10).append("\",");
        requestBody.append("\"query\": {\"bool\":{\"must\":{\"term\":{\"")
                .append(field1).append("\":\"")
                .append(value1);
        requestBody.append("\"}}}}}");
        infoMap.put("esQuery", requestBody.toString());
        //infoMap.put("oldEsUrl", String.format("/%s/_search?scroll=%s", oldEsUrl, esScrollTime));
        System.out.println("****"+ infoMap);

        return infoMap;
    }

    /**
     * delete one index
     * @param
     * @throws Exception
     */
    public static void delete() throws Exception {
        try {
            Response rsp = null;
            rsp = restClient.performRequest("DELETE", "/" + index + "?&pretty=true");
            Assert.assertEquals(rsp.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
            logger.info("Delete index,response entity is : " + EntityUtils.toString(rsp.getEntity()));
        } catch (Exception e) {
            if (e instanceof GSSException) {
                if (restClient != null) {
                    restClient.close();
                }
                restClient = getRestClient();
                delete();
            } else {
                throw e;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        logger.info("Start to do es test !");
        initProperties();
        try {
            restClient = getRestClient();
           // queryEsClusterHealth();
            createIndexWithShardNum();
            //putData();
           // queryData();  //官方
            //getEsData("license_number","赣GX8933");
            //query();
            //delete();
        } finally {
            try {
                if (restClient != null) {
                    restClient.close();
                }
            } catch (IOException e) {
                logger.error("Failed to close RestClient", e);
            }
        }

    }

}
