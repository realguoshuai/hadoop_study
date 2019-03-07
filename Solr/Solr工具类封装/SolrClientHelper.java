package com.guoshuai.mtdap.common;


import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class SolrClientHelper {
    private static HttpSolrClient httpSolrClient = null;
    private static CloudSolrClient cloudSolrClient = null;
    private static String clientType;
    private static String solrKbsEnable;
    private static int zkClientTimeout;
    private static int zkConnectTimeout;
    private static String zkHost;
    private static String zookeeperDefaultServerPrincipal;
    private static String collectionName;
    private static String defaultConfigName;
    private static int shardNum;
    private static int replicaNum;
    private static String principal;
    private static SolrClient client;
    private static final String PROPERTIES_PATH = "solr.properties";
    private static Logger logger = Logger.getLogger(SolrClientHelper.class.getName());



    static {
        client = getClient();
    }

    public static SolrClient getClient() {
        try {
            if (null == client) {
                initProperties();
                if (null != clientType && clientType.equals("cluster")) {
                    return getCloudSolrClient();
                } else {
                    return getSolrClient();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    /***
     * @Description: getSolrClient 单机solr
     * @return: org.apache.solr.client.solrj.impl.HttpSolrClient
     */
    public static HttpSolrClient getSolrClient() throws Exception {
        if (null == httpSolrClient)
            httpSolrClient = createSimpleSolrClient();
        return httpSolrClient;
    }

    /***
     * @Description: getCloudSolrClient  集群solr
     * @return: org.apache.solr.client.solrj.impl.CloudSolrClient
     */
    public static CloudSolrClient getCloudSolrClient() throws Exception {
        if (null == cloudSolrClient)
            cloudSolrClient = createCloudSolrClient();
        return cloudSolrClient;
    }

    /**
     * initProperties 初始化配置文件
     *
     * @throws Exception
     */
    private static void initProperties() throws Exception {

        URL resource = SolrClientHelper.class.getClassLoader().getResource(PROPERTIES_PATH);
        if (resource == null) {
            throw new FileNotFoundException("没有找到指定solr的配置文件:" + PROPERTIES_PATH);
        }
        //加载配置文件
        InputStream input = SolrClientHelper.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
        Properties properties = new Properties();
        properties.load(input);

        clientType = properties.getProperty("client_type") == null ? "alone" : properties.getProperty("client_type");
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
    }

    /**
     * 获取cloude中包含的所有collection
     *
     * @param cloudSolrClient
     * @return
     */
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
            logger.info("*********server address:" + zkHost);
            httpSolrClient = new HttpSolrClient(zkHost);
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
     *
     * @return
     * @throws Exception
     */
    private static CloudSolrClient createCloudSolrClient() throws Exception {
        //initProperties();
        CloudSolrClient.Builder builder = new CloudSolrClient.Builder();

        builder.withZkHost(zkHost);
        cloudSolrClient = builder.build();

        cloudSolrClient.setZkClientTimeout(zkClientTimeout);
        cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
        cloudSolrClient.setDefaultCollection(collectionName);
        cloudSolrClient.connect();
        return cloudSolrClient;
    }

    public static void addDucuments(Collection<SolrInputDocument> docs) {
        System.out.println("======================add many docs  ===================");
        try {
            ///long start = System.currentTimeMillis();
            /*  源码add(String collection, Collection<SolrInputDocument> docs)*/
            UpdateResponse rsp = getClient().add(docs);
            //UpdateResponse rsp = getClient().add("kkrecords",docs);
            //System.out.println("helper-add(doc) cost time "+(System.currentTimeMillis()-start)+"ms");
            System.out.println("Add doc size: " + docs.size() + " resultStatus:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
            //long start1 = System.currentTimeMillis();
            /*源码中 add()方法 已经提交到solr  多余的commit是多余的,没设等待时长 重复提交 且 空等几秒 前提配置中已经配过软提交*/
            /*UpdateResponse rspcommit = getClient().commit();
            System.out.println("commit doc to index" + " resultStatus:" + rspcommit.getStatus() + " Qtime:" + rspcommit.getQTime());*/
            //System.out.println("helper-commit cost time "+(System.currentTimeMillis()-start1)+"ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addDucument(SolrInputDocument doc) {
        System.out.println("======================add one docs  ===================");
        try {
            UpdateResponse rsp = client.add(doc);
            System.out.println("Add one doc resultStatus:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());

           /* UpdateResponse rspcommit = client.commit();
            System.out.println("commit one doc to index resultStatus:" + rspcommit.getStatus() + " Qtime:" + rspcommit.getQTime());
*/
        } catch (Exception e) {
            System.out.println("addDucument error : " + e.getMessage());
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
           /* UpdateRequest commit = new UpdateRequest();
            commit.deleteByQuery(queryCon);
            commit.commit(client,collectionName);
            *//*commit.setCommitWithin(5000);
            commit.process(client);
            System.out.println("url:"+commit.getPath()+"\t xml:"+commit.getXML()+" method:"+commit.getMethod());*/

            UpdateResponse rsp = client.deleteByQuery(queryCon);
            logger.info("开始删除....");
            client.commit();
            logger.info("delete query:" + queryCon + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
            System.out.println("delete query:" + queryCon + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
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

    /**
     * Description  重命名newcore  适用于单机,测试通过
     * Param [coreName, newName, client]
     * return void
     **/
    public static void renameCore(String coreName, String newName) {

        CoreAdminRequest renameRequest = new CoreAdminRequest();
        renameRequest.setCoreName(coreName);
        renameRequest.setOtherCoreName(newName);
        renameRequest.setAction(CoreAdminParams.CoreAdminAction.RENAME);
        try {
            client.request(renameRequest);
            logger.info("*************** do over");
        } catch (SolrServerException e) {
            e.printStackTrace();
            logger.info("********** " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("********* " + e.getMessage());
        }
    }

    /**
     * Description 测试添加单条Core的schema,用做api调试
     * Param [collectionName]
     **/
    public static void addField(String collectionName) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "auto_create_test4");
        hashMap.put("type", "int");
        hashMap.put("stored", true);
        hashMap.put("indexed", false); //TODO indexed 不设置 默认为true
        /*hashMap.put("multiValued",true);*/ //默认不创建
        SchemaRequest.AddField addField = new SchemaRequest.AddField(hashMap);
        try {
            //TODO properties文件中不用指定 最后的coreName,拼接后会找不到路径 ../core/core/schema
            SchemaResponse.UpdateResponse process = addField.process(client, collectionName);
            System.out.println("return status: " + process.getStatus());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 一键创建Schema设计:
       1:从xml文件中读取第一个key-value分别作为参数name-type对应的值
       2:从xml文件中读取第二个key-value分别作为参数stored-indexed对应的布尔值
       3:todo 做成事务性的  避免(新创建的字段已经存在于原有的配置集中且域相同,域属性不同)
        如果有字段重复 属性不同的 打印差异 不创建
        只有在现行字段跟创建字段没有冲突下才创建成功
        有冲突的打印出差异性,由使用者消除差异或者修改配置 重新创建
        可能是在用:不能程序中删除 代码中将差异返回
        提供删除schema 接口
       */
    public static void addBatchField(String collectionName) {
        ArrayList<String> list = InitPropertiesUtil.readXML();
        for (int i = 0; i < list.size(); ) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", list.get(i).toString());
            hashMap.put("type", list.get(i + 1).toString());
            hashMap.put("stored", list.get(i + 2).toString().equals("true") && list.get(i + 2).toString().equals("false")
                    ? Boolean.parseBoolean(list.get(i + 2).toString()) : list.get(i + 2).toString());
            hashMap.put("indexed", list.get(i + 3).toString().equals("true") && list.get(i + 3).toString().equals("false")
                    ? Boolean.parseBoolean(list.get(i + 3).toString()) : list.get(i + 3).toString());
            if (list.get(i).equals("dept_id")) hashMap.put("multiValued", true);
            i += 4;
            SchemaRequest.AddField addField = new SchemaRequest.AddField(hashMap);
            try {
                SchemaResponse.UpdateResponse process = addField.process(client, collectionName);
                System.out.println("return status: " + process.getStatus());
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Description 获取单个schema
     * Param collectionName
     * return list
     **/
    public static List getSchemaField(String collectionName, String fieldName) {
        ArrayList<String> list = new ArrayList<>();
        //SchemaRequest request = new SchemaRequest();
        SchemaRequest.Field field = new SchemaRequest.Field(fieldName);
        //request.setPath("/schema");
        try {
            // SchemaResponse response = request.process(client, collectionName);
            SchemaResponse.FieldResponse response = field.process(client, collectionName);
            NamedList<Object> result = response.getResponse();
            result = (NamedList<Object>) result.get("field");

            //遍历
            for (Map.Entry<String, Object> entry : result) {
                String key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key + ": " + (value == null ? "" : value.toString()));
            }
            //List<NamedList<Object>> namedLists = (List<NamedList<Object>>) result.get("fields");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Description 获取schema中所有fields
     * Param        collectionName
     * return void
     **/
    public static LinkedHashMap getSchemaFields(String collectionName) {
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        ModifiableSolrParams solrParams = new ModifiableSolrParams();
        /*去除默认的且去除默认字段属性,动态类型返回自定义的*/
        //solrParams.add("showDefaults", "true");
        //solrParams.add("includeDynamic", "true");
        //solrParams.add("f1", "id,name,product_name,_version_");
        SchemaRequest.Fields fields = new SchemaRequest.Fields(solrParams);
        try {
           // HashMap<String, Object> hashMap = new HashMap<>();
            SchemaResponse.FieldsResponse response = fields.process(client, collectionName);
            NamedList<Object> result = response.getResponse();
            List<NamedList<Object>> namedLists = (List<NamedList<Object>>) result.get("fields");
            for(NamedList<Object> field:namedLists){
                for (Map.Entry<String, Object> entry : field) {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    //System.out.println(key + " : " + (value == null ? "" : value.toString()));
                    //测试本地core 162个 还包含一般的系统默认的 去除默认的还剩下 94个
                    if("name".equals(key) || "type".equals(key) || "indexed".equals(key) ||"stored".equals(key)){
                      //  System.out.println(key+":"+value);
                        //hashMap,并不能保证顺序,使用linkedHashMap可以
                        //hashMap.put(key,value);
                        linkedHashMap.put(key,value);
                    }
                }
                /*测试hashMap读取*/
                /*for(Map.Entry<String, Object>map: hashMap.entrySet()){
                    System.out.println(map.getKey()+" : "+map.getValue());
                }
                */
                /*测试linkedHashMap读取*/
                for(Map.Entry<String,Object> map:linkedHashMap.entrySet()){
                    System.out.println(map.getKey()+":"+map.getValue());
                }
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linkedHashMap;
    }

    /*TODO 测试下集群创建Schema是否通用*/
    public static void addCollectionField() {

    }


    public static void renameCollection() {

    }

    /**
     * Description 合并Core索引
     * Param [name, srcCores, client]  主core,从cores,SolrClient连接
     * return void
     **/
    public static void CoreMerge(String name, String[] srcCores, SolrClient client) {
        CoreAdminRequest.MergeIndexes mergeIndexes = new CoreAdminRequest.MergeIndexes();
        mergeIndexes.setCoreName(name);
        mergeIndexes.setSrcCores(Arrays.asList(srcCores));
        try {
            client.request(mergeIndexes);
        } catch (SolrServerException e) {
            e.printStackTrace();
            logger.info("*********** " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("************ " + e.getMessage());
        }
    }

    /**
     * Description  Core拆分
     * Param []
     * return void
     **/
    public static void CoreSplit() {

    }


    /*
     * Description  url从properties配置文件中读
     * Param
     * return void
     **/
    public static void createField(List<String> arrayList) {
        logger.info("**************** createField ");
        // 先写死 调试通过后 重构使用从配置文件读
        //HttpSolrClient client = new HttpSolrClient.Builder(url).build();
        try {
            URL schemaUrl = new URL(url);
            HttpURLConnection schemaUrlContent = (HttpURLConnection) schemaUrl.getContent();
            schemaUrlContent.setRequestMethod("POST");
            schemaUrlContent.setRequestProperty("Content-type", "application/json");
            schemaUrlContent.setDoOutput(true);
            schemaUrlContent.setDoInput(true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(schemaUrlContent.getOutputStream()));
            JSONObject field = new JSONObject();
            // field  fieldType     stored  index
            for (int i = 0; i <= arrayList.size(); ) {
                System.out.println(arrayList.get(i) + " --- " + arrayList.get(i + 1));
                //从xml文件读
                field.put("name", arrayList.get(i));
                field.put("type", arrayList.get(i + 1));

                //从xml2中读
                field.put("stored", true);
                field.put("index", false);
                i += 2;
            }

            JSONObject addDoc = new JSONObject();
            addDoc.put("add-field", field);
            System.out.println("******* " + addDoc.toString());
            writer.write(addDoc.toString());
            writer.flush();
            writer.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(schemaUrlContent.getInputStream()));
            while (reader.ready()) {
                System.out.println("****** " + reader.readLine());
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void AutoCreateField() {
        HttpSolrClient client = new HttpSolrClient
                .Builder(url)
                .build();
        SolrQuery queryParam = new SolrQuery();
        queryParam.addFilterQuery("q:*:*");
        queryParam.addField("id");
        queryParam.setRows(20);
        try {
            client.query(queryParam);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
