package mtdap.es;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.ietf.jgss.GSSException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class RestClientTest {

	private static final Logger logger = LoggerFactory.getLogger(RestClientTest.class);
    private static String isSecureMode;
    private static String esServerHost;
    private static HttpHost[] hostsArray;
    private static List<HttpHost> hosts=new ArrayList<HttpHost>();
    private static int MaxRetryTimeoutMillis;
    private static String index;
    private static String type;
    private static int id;
    private static int shardNum;
    private static int replicaNum;
    private static int ConnectTimeout;
    private static int SocketTimeout;
    private static RestClient restClient = null;

    private static void initProperties() {
        Properties properties = new Properties();
        String path = System.getProperty("user.dir") + File.separator + "conf" + File.separator
                + "es.properties";
        path = path.replace("\\", "\\\\");        
        String schema="https";
        try {
            properties.load(new FileInputStream(new File(path)));
        } catch (Exception e) {
            //throw new EsException("Failed to load properties file : " + path);
            logger.info("Failed to load properties file : " + path);
        }
        esServerHost = properties.getProperty("EsServerHost");
        //EsServerHost in es.properties must as ip1:port1,ip2:port2,ip3:port3....  eg:1.1.1.1:24100,2.2.2.2:24100,3.3.3.3:24100
        MaxRetryTimeoutMillis = Integer.valueOf(properties.getProperty("MaxRetryTimeoutMillis"));
        ConnectTimeout = Integer.valueOf(properties.getProperty("ConnectTimeout"));
        SocketTimeout = Integer.valueOf(properties.getProperty("SocketTimeout"));
        isSecureMode = properties.getProperty("isSecureMode");
        index = properties.getProperty("index");
        type = properties.getProperty("type");
        id = Integer.valueOf(properties.getProperty("id"));
        shardNum = Integer.valueOf(properties.getProperty("shardNum"));
        replicaNum = Integer.valueOf(properties.getProperty("replicaNum"));
        
        if(("false").equals(isSecureMode)) {
        	schema="http";
        }
        String[] hostArray1 = esServerHost.split(",");
        for(String host:hostArray1){
        	String[] ipPort= host.split(":");
        	HttpHost hostNew =new HttpHost(ipPort[0],Integer.valueOf(ipPort[1]),schema);
        	hosts.add(hostNew);
        }
        hostsArray=hosts.toArray(new HttpHost[] {});
        
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
	private static void setSecConfig() {
		String krb5ConfFile = RestClientTest.class.getClassLoader().getResource("krb5.conf").getFile();
		logger.info("krb5ConfFile:" + krb5ConfFile);
		System.setProperty("java.security.krb5.conf", krb5ConfFile);

		String jaasPath = RestClientTest.class.getClassLoader().getResource("jaas.conf").getFile();
		logger.info("jaasPath:" + jaasPath);
		System.setProperty("java.security.auth.login.config", jaasPath);
		System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");

	}

	/**
	 * get one rest client instance.
	 * @return
	 * @throws Exception 
	 */
	private static RestClient getRestClient(){
		RestClientBuilder builder = null;
		RestClient restClient = null;
		if (isSecureMode.equals("true")) {
			setSecConfig();
			builder = RestClient.builder(hostsArray);
		}
		else {
			builder = RestClient.builder(hostsArray);
		}
		Header[] defaultHeaders = new Header[] { new BasicHeader("Accept", "application/json"),
				new BasicHeader("Content-type", "application/json") };

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
	 * @param
	 * @throws Exception 
	 */
	//private static void queryEsClusterHealth(RestClient restClient) throws Exception {
	private static void queryEsClusterHealth() throws Exception {
		Response rsp = null;
		try {
			rsp = restClient.performRequest("GET", "/_cluster/health");
			String result= EntityUtils.toString(rsp.getEntity());
			logger.info("QueryEsClusterHealth, result is : {}" ,result);
		}catch (Exception e) {
			//if it is gss exception, please close the old client and create a new client.
			if (e instanceof GSSException) {
				if (restClient != null) {
					restClient.close();
				}
				restClient=getRestClient();
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
	private static void createIndexWithShardNum() throws Exception{	
		try {
			Map<String, String> params = Collections.singletonMap("pretty", "true");
			String jsonString = "{" + "\"settings\":{" + "\"number_of_shards\":\"" + shardNum + "\","
					+ "\"number_of_replicas\":\"" + replicaNum + "\"" + "}}";

			HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
			Response response = null;
			response = restClient.performRequest("PUT", "/" + index, params, entity);
			logger.info("CreateIndexWithShardNum,response entity is :{} ", EntityUtils.toString(response.getEntity()));
		}catch (Exception e) {
			if (e instanceof GSSException) {
				if (restClient != null) {
					restClient.close();
				}
				restClient=getRestClient();
				createIndexWithShardNum();
			} else {
				throw e;
			}
		}
		

	}


	/**
	 * put one doc to the index
	 * @param
	 * @throws Exception
	 */
	private static void putData() throws Exception {
		String jsonString = "{" + "\"name\":\"Elasticsearch Reference\"," + "\"author\":\"Alex Yang \","
				+ "\"pubinfo\":\"Beijing,China. \"," + "\"pubtime\":\"2016-07-16\","
				+ "\"desc\":\"Elasticsearch is a highly scalable open-source full-text search and analytics engine.\""
				+ "}";
		String jsonString1 = "{" + "\"name\":\"es\"," + "\"author\":\"lisi\","
				+ "\"pubinfo\":\"上海\"," + "\"pubtime\":\"2019-07-10\","
				+ "\"desc\":\"hello world.\""
				+ "}";


		Map<String, String> params = Collections.singletonMap("pretty", "true");
		HttpEntity entity = new NStringEntity(jsonString1, ContentType.APPLICATION_JSON);
		Response response = null;

		try {
			response = restClient.performRequest("PUT", "/" + index + "/" + type + "/" + id, params, entity);
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()|| HttpStatus.SC_CREATED==response.getStatusLine().getStatusCode()) {
				logger.info("PutData,response entity is : " + EntityUtils.toString(response.getEntity()));
			}
			else {
				logger.error("PutData failed.");
			}
		}catch (Exception e) {
			if (e instanceof GSSException) {
				if (restClient != null) {
					restClient.close();
				}
				restClient=getRestClient();
				putData();
				
			} else {
				throw e;
			}
		}
	}
	/**
	 * query all datas of one index.
	 * @param
	 * @throws Exception
	 */
	private static void queryData() throws Exception {
		try {
			Map<String, String> params = Collections.singletonMap("pretty", "true");		
			Response rsp = restClient.performRequest("GET", "/" + index+ "/" + type+ "/" + id, params);


			logger.info("QueryData,response entity is : " + EntityUtils.toString(rsp.getEntity()));
		}catch (Exception e) {
			if (e instanceof GSSException) {
				if (restClient != null) {
					restClient.close();
				}
				restClient=getRestClient();
				queryData();
			} else {
				throw e;
			}
		}		
	}

	/**
	 * delete one index
	 * @param
	 * @throws Exception
	 */
	private static void delete() throws Exception {
		try {
			Response rsp = null;
			rsp = restClient.performRequest("DELETE", "/" + index + "?&pretty=true");
			Assert.assertEquals(rsp.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
			logger.info("Delete index,response entity is : " + EntityUtils.toString(rsp.getEntity()));
		}catch (Exception e) {
			if (e instanceof GSSException) {
				if (restClient != null) {
					restClient.close();
				}
				restClient=getRestClient();
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
			//queryEsClusterHealth();
			//createIndexWithShardNum();
			//putData();
			//queryData();
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
