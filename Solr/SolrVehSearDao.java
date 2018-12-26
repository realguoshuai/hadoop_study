package com.guoshuai.soa.traffic.core.bigdata.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.guoshuai.soa.traffic.core.bigdata.dao.ISolrVehSearDao;
import com.guoshuai.soa.traffic.core.bigdata.pojo.PassInfoPojo;
import com.guoshuai.soa.traffic.core.bigdata.util.CommonUtils;
import com.guoshuai.soa.traffic.core.bigdata.util.LoggerHelper;
import com.guoshuai.soa.traffic.core.bigdata.util.SolrClientHelper;
import com.guoshuai.soa.traffic.util.pojo.Page;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Repository
public class SolrVehSearDao implements ISolrVehSearDao {
   // private static SolrClient client;
    private static CloudSolrClient client;
    private static Map<String, Boolean> fileds = new HashMap<String, Boolean>();
    private static ArrayList<String> licenseNumberList = new ArrayList();
    private static Map<String, Integer> licenseNumberMap = new HashMap<String, Integer>();
    /*前缀匹配 2位 赣A*/
    private static String prefixMatch = null;
    /*前缀匹配 1位 赣*/
    private static String prefixMatchA = null;

    //初始化solr client、并获取到fileds；
    private static SolrClient getClient() throws Exception{
    	if(client==null){synchronized(SolrVehSearDao.class){if(client==null){
            long start1 = System.currentTimeMillis();

            SolrClientHelper solrClientHelper = new SolrClientHelper();
             try {
                 client = (CloudSolrClient) solrClientHelper.getClient("kkrecords");
                  //client = new SolrConnectionPool().getConnection("kkrecords");
                  //client = new SolrConnectionPool().getSimpleConnection("kkrecords");
                 System.out.println("调用工具类 时长"+(System.currentTimeMillis()-start1)/1000+"秒");
                 SolrQuery params = new SolrQuery();
                 System.out.println("======================init-start==================");
                 params.setQuery("*:*");
                 params.setStart(0);
                 params.setRows(10);
                 params.setParam("shards.tolerant", true);
                 //QueryResponse rsp = SolrVehSearDao.client.query(params);
                 QueryResponse rsp = client.query(params);
                 SolrDocumentList docs = rsp.getResults();
                 System.out.println("第一次查询时间: "+rsp.getQTime());
                 for(SolrDocument sd:docs){
                	 for(String field:sd.getFieldNames()){
                	     fileds.put(field, true);
                     }
                     String licenseNumber = sd.getFieldValue("licenseNumber").toString();
                     licenseNumberList.add(licenseNumber.substring(0,2));
                 }
                 for(String str:licenseNumberList){
                     Integer count = licenseNumberMap.get(str);
                     if(count != null) {
                         licenseNumberMap.put(str,count+1);
                     }else{
                         licenseNumberMap.put(str,1);
                     }
                 }

                 ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(licenseNumberMap.entrySet());
                 Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                     @Override
                     public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                         return o2.getValue().compareTo(o1.getValue());
                     }
                 });
                 for(Map.Entry<String, Integer> map :list){
                     System.out.println("map中排序效果: "+map.getKey()+" "+map.getValue());
                     if(list.size()>=1){
                         prefixMatch = map.getKey();
                         prefixMatchA=map.getKey().substring(0,1);
                         /*拿到第一位,退出循环*/
                         break;
                     }
                 }

                 System.out.println("获取到当地的号牌前两位 "+prefixMatch);
                 System.out.println("获取到当地的号牌前一位 "+prefixMatchA);
                 System.out.println("getClient 时长"+(System.currentTimeMillis()-start1)/1000+"秒");
                 System.out.println("----------init-over-------------");
             } catch (Exception e) {
                 LoggerHelper.LOG.error("instantiaze SolrClient failed :" + e.getMessage());
                 throw new Exception("instantiaze SolrClient failed :" + e.getMessage());
             }
    	}}}
         return client;
    }
    
    
    static <T> T getFiledValue(SolrDocument doc,Class<T> classType,String... fileds){
    	T ret=null;
    	try{for(String filed:fileds){
    		ret=CommonUtils.castTo(doc.get(filed),classType);
    		if(ret!=null)break;
    	}}catch(Throwable e){}
    	return ret;
    }
    public static List<PassInfoPojo> solrDocs2PojoLst(SolrDocumentList docs){
    	List<PassInfoPojo> lst = new ArrayList<PassInfoPojo>();
    	for(SolrDocument doc:docs){
	    	PassInfoPojo pojo = new PassInfoPojo();
	    	pojo.setId(getFiledValue(doc,String.class,"id"));
	    	pojo.setPointId(getFiledValue(doc,String.class,"bayonet_id","pointId"));
	    	pojo.setDirection(getFiledValue(doc,String.class,"direction"));
	    	pojo.setDirectionName(getFiledValue(doc,String.class,"dir_name"));
	    	pojo.setLaneNumber(getFiledValue(doc,String.class,"laneNumber"));
	    	pojo.setLicenseType(getFiledValue(doc,String.class,"vehicle_plate_type","licenseType"));
	    	pojo.setLicenseNumber(getFiledValue(doc,String.class,"vehicle_plate","licenseNumber"));
	    	pojo.setPassingTime(getFiledValue(doc,String.class,"pass_time","passingTime"));
	    	pojo.setSpeed(getFiledValue(doc,String.class,"speed"));
	    	pojo.setColor(getFiledValue(doc,String.class,"vehicle_color","color"));
	    	pojo.setVehLength(getFiledValue(doc,String.class,"vehLength"));
	    	pojo.setPicPath(getFiledValue(doc,String.class,"pic_url1","picPath"));
	    	pojo.setPicPath2(getFiledValue(doc,String.class,"pic_url2","picPath2"));
	    	pojo.setPicPath3(getFiledValue(doc,String.class,"pic_url3","picPath3"));
	    	pojo.setPicPath4(getFiledValue(doc,String.class,"pic_url4","picPath4"));
	    	pojo.setVideoUrl(getFiledValue(doc,String.class,"videoUrl","video_url"));
	    	pojo.setVehiclePlateColor(getFiledValue(doc,String.class,"vehicle_plate_color"));
	    	pojo.setVehiclePlateColorName(getFiledValue(doc,String.class,"vehicle_plate_color_name"));
	    	pojo.setVehicleType(getFiledValue(doc,String.class,"vehicle_type"));
	    	pojo.setVehicleTypeName(getFiledValue(doc,String.class,"vehicle_type_name"));
	    	pojo.setVehicleColor(getFiledValue(doc,String.class,"vehicle_color"));
	    	pojo.setVehicleColorName(getFiledValue(doc,String.class,"vehicle_color_name"));
	    	pojo.setVehicleBrand(getFiledValue(doc,String.class,"vehicle_brand"));
	    	pojo.setMinSpeed(getFiledValue(doc,Integer.class,"min_speed"));
	    	pojo.setMaxSpeed(getFiledValue(doc,Integer.class,"max_speed"));
	    	pojo.setDataSource(getFiledValue(doc,String.class,"data_source"));
	    	pojo.setDataSourceName(getFiledValue(doc,String.class,"data_source_name"));
	    	pojo.setCollectType(getFiledValue(doc,String.class,"collect_type"));
	    	pojo.setCollectTypeName(getFiledValue(doc,String.class,"collect_type_name"));
	    	pojo.setRecordTime(getFiledValue(doc,Date.class,"record_time"));
	    	lst.add(pojo);
    	}
    	return lst;
    }
    
    
    @Override
    public Page getPageVehSear(String id, String licenseType, String licenseNumber, String beginTime, String endTime, String pointId, Integer minSpeed, Integer maxSpeed, Page page) throws Exception {
        try {
            getClient();
            SolrQuery params = new SolrQuery();
            System.out.println("======================query-start==================");
            //车牌 & trackId:查询
            params.setQuery("*:*");

            //时间、号牌类型、点位：条件过滤
            String[] strarr = new String[]{};
            List<String> filterParams = new ArrayList<>();
            if(id ==null && licenseNumber ==null){

            }
            if (id != null) {
                filterParams.add("id:" + id);
            }
            if(licenseNumber!=null){//对用户输入车牌判断
                String filed = (fileds.containsKey("licenseNumber")?"licenseNumber":"vehicle_plate");
                if(licenseNumber.length()==7){//代表完整号牌
                    System.out.println("判断车牌完整性生效");
                    filterParams.add(filed + ":" +  licenseNumber);
                }else{
                    /*通过检测GBK的方式判断是否包含中文 true 包含 false 不包含*/
                    boolean isExistZW =licenseNumber.getBytes("GBK").length == licenseNumber.length() ? false : true;
                    if(isExistZW){
                        System.out.println("判断车牌包含中文生效");
                        //包含中文 只需要添加后缀通配符
                        filterParams.add(filed + ":" +  licenseNumber+"*");
                    }else{
                        //不包含中文 需要判断 首字母是否包含 字母
                        String regex=".*[a-zA-Z]+.*";
                        boolean isExistYW=Pattern.compile(regex).matcher(licenseNumber.substring(0,1)).matches()? true : false;
                        if(isExistYW){//包含英文  使用prefixMatchA
                            System.out.println("判断车牌包含英文但不包含中文生效");
                            filterParams.add(filed + ":" + prefixMatchA + licenseNumber+"*");
                        }else{//不包含英文  使用prefixMatch
                            System.out.println("判断车牌既不包含英文也不包含中文生效");
                            filterParams.add(filed + ":" + prefixMatch + licenseNumber+"*");
                        }
                    }
                }
            }



            if(null != beginTime || null != endTime){
            	String filed = (fileds.containsKey("passingTime")?"passingTime":"pass_time");
                if(null != beginTime && null != endTime){
                    filterParams.add(filed+":[" + beginTime.replace(" ", "T") + "Z" + " TO " + endTime.replace(" ", "T") + "Z" + "]");
                }else if(null == beginTime){
                    filterParams.add(filed+":[* TO " + endTime.replace(" ", "T") + "Z" + "]");
                }else {
                    filterParams.add(filed+":[" + beginTime.replace(" ", "T") + "Z" + " TO *]");
                }

            }
            if (null != licenseType){
            	String filed = (fileds.containsKey("licenseType")?"licenseType":"vehicle_plate_type");
            	filterParams.add( filed+":" + licenseType);
            }
            if (null != pointId){
            	String filed = (fileds.containsKey("pointId")?"pointId":"bayonet_id");
                filterParams.add(filed+":" + pointId);
            }
            if(null != minSpeed || null != maxSpeed){
               String minSpe = minSpeed == null ? "*": minSpeed.toString();
               String maxSpe = maxSpeed == null ? "*": maxSpeed.toString();
               filterParams.add("speed:[" + minSpe + " TO " + maxSpe + "]");
            }
            params.setFilterQueries(filterParams.toArray(strarr));

            params.setStart((page.getCurrentPage() - 1) * page.getPageSize() < 0 ? 0 : (page.getCurrentPage() - 1) * page.getPageSize());
            params.setRows(page.getPageSize());
            params.setParam("shards.tolerant", true);
            List<SolrQuery.SortClause> sortClause = SolrClientHelper.getSortClause("passingTime desc");
            params.setSorts(sortClause);

            System.out.println("查询内容:" + params);
            QueryResponse rsp = client.query(params);
            SolrDocumentList docs = rsp.getResults();
            System.out.println("查询内容:" + params);
            System.out.println("文档数量：" + docs.getNumFound());
            System.out.println("查询花费时间:" + rsp.getQTime());
            List<PassInfoPojo> beanList = solrDocs2PojoLst(docs);
            page.setTotalCount((int) docs.getNumFound());
            page.setResult(beanList);
            System.out.println("----------query-over-------------");
        } catch (Exception e) {
        	e.printStackTrace();
            LoggerHelper.LOG.error("SolrClient query failed :" + e.getMessage());
            throw new Exception("SolrClient query failed" + e.getMessage());
        }
        return page;
    }


    @Override
    public List<PassInfoPojo> getVehSear(String id, String licenseType, String licenseNumber, String beginTime, String endTime, String pointId, Integer minSpeed, Integer maxSpeed) throws Exception {
        try {

        	getClient();
            List<PassInfoPojo> beanList = null;
            SolrQuery params = new SolrQuery();
            System.out.println("======================query=start==================");

            //车牌 & trackId:查询
            if(licenseNumber == null && id == null){
                params.setQuery("*:*");
            }else{
                licenseNumber = licenseNumber == null ? "*" : (licenseNumber + "*");
                if (id != null) {
                    //params.setQuery("licenseNumber:" + licenseNumber + " and id:" + id);
                    params.setQuery("id:" + id);
                } else {
                	String filed = (fileds.containsKey("licenseNumber")?"licenseNumber":"vehicle_plate");
                    params.setQuery(filed+":" + licenseNumber);
                }
            }

            //时间、号牌类型、点位：条件过滤
            String[] strarr = new String[]{};
            List<String> filterParams = new ArrayList<>();
            if(null != beginTime || null != endTime){
            	String filed = (fileds.containsKey("passingTime")?"passingTime":"pass_time");
                if(null != beginTime && null != endTime){
                    filterParams.add(filed+":[" + beginTime.replace(" ", "T") + "Z" + " TO " + endTime.replace(" ", "T") + "Z" + "]");
                }else if(null == beginTime){
                    filterParams.add(filed+":[* TO " + endTime.replace(" ", "T") + "Z" + "]");
                }else {
                    filterParams.add(filed+":[" + beginTime.replace(" ", "T") + "Z" + " TO *]");
                }

            }
            if (null != licenseType){
            	String filed = (fileds.containsKey("licenseType")?"licenseType":"vehicle_plate_type");
                filterParams.add( filed+":" + licenseType);
            }
            if (null != pointId){
            	String filed = (fileds.containsKey("pointId")?"pointId":"bayonet_id");
                filterParams.add(filed+":" + pointId);
            }
            if(null != minSpeed || null != maxSpeed){
                String minSpe = minSpeed == null ? "*": minSpeed.toString();
                String maxSpe = maxSpeed == null ? "*": maxSpeed.toString();
                filterParams.add("speed:[" + minSpe + " TO " + maxSpe + "]");
            }
            params.setFilterQueries(filterParams.toArray(strarr));

            params.setStart(0);
            params.setRows(1000);
            params.setParam("shards.tolerant", true);
            List<SolrQuery.SortClause> sortClause = SolrClientHelper.getSortClause("passingTime desc");
            params.setSorts(sortClause);

            QueryResponse rsp = getClient().query(params);
            SolrDocumentList docs = rsp.getResults();
            System.out.println("查询内容:" + params);
            System.out.println("文档数量：" + docs.getNumFound());
            System.out.println("查询花费时间:" + rsp.getQTime());
            beanList = solrDocs2PojoLst(docs);
            System.out.println("-------------query-over--------------"+JSONObject.toJSONString(beanList));
            return beanList;
        } catch (Exception e) {
            LoggerHelper.LOG.error("SolrClient query failed :" + e.getMessage());
            throw new Exception("SolrClient query failed" + e.getMessage());
        }
    }


    @Override
    public List<PassInfoPojo> getVehSear(List<String> ids) throws Exception {
        try {

        	getClient();
            SolrQuery params = new SolrQuery();
            System.out.println("======================query_start==================");
            String q = "";
            for (String id : ids) {
                q += (q.isEmpty() ? "" : " and ") + "id:" + id;
            }
            params.setQuery(q);
            params.setStart(0);
            params.setRows(1000);
            params.setParam("shards.tolerant", true);
            List<SolrQuery.SortClause> sortClause = SolrClientHelper.getSortClause("passingTime desc");
            params.setSorts(sortClause);


            QueryResponse rsp = getClient().query(params);
            SolrDocumentList docs = rsp.getResults();
            System.out.println("查询内容:" + params);
            System.out.println("文档数量：" + docs.getNumFound());
            System.out.println("查询花费时间:" + rsp.getQTime());
            List<PassInfoPojo>  beanList = solrDocs2PojoLst(docs);
            System.out.println("-------------query-over--------------");
            return beanList;
        } catch (Exception e) {
            LoggerHelper.LOG.error("SolrClient query failed :" + e.getMessage(), e);
            throw new Exception("SolrClient query failed" + e.getMessage());
        }
    }

    @Override
	public List<String> getVehSuggestSear(String licenseNumber) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        SolrClientHelper helper = new SolrClientHelper();
        SolrClient client = null;
        try {
            client = helper.getClient("kkrecords");
        } catch (Exception e) {
            LoggerHelper.LOG.error("instantiaze SolrClient failed :" + e.getMessage());
        }

        try {
            List<PassInfoPojo> beanList = null;
            SolrQuery params = new SolrQuery();
            System.out.println("======================query=start==================");

            if (licenseNumber != null) {
                if (licenseNumber != null) {
                    /*使用suggest引擎*/
                    params.set("qt","/suggest?");
                    params.set("q",licenseNumber);

                    params.set("spellcheck", "on");
                    params.set("spellcheck.build", "true");
                    params.set("spellcheck.count", "10");
                    params.set("spellcheck.onlyMorePopular", "true");
                    params.set("spellcheck.alternativeTermCount", "4");
                    params.set("spellcheck.onlyMorePopular", "true");
                    params.set("spellcheck.extendedResults", "true");
                    params.set("spellcheck.maxResultsForSuggest", "5");
                    params.set("spellcheck.collate", "true");
                    params.set("spellcheck.collateExtendedResults", "true");
                    params.set("spellcheck.maxCollationTries", "5");
                    params.set("spellcheck.maxCollations", "3");
                }
            }
            QueryResponse rsp = client.query(params);
            System.out.println("params "+params);
            SuggesterResponse suggesterResponse = rsp.getSuggesterResponse();
            if(suggesterResponse!=null){
                Map<String, List<String>> listMap = suggesterResponse.getSuggestedTerms();
                for(List<String> list1:listMap.values()){
                    for (String word : list1) {
                        System.out.println(word + " ");
                        /*suggest 测试环境结果输出了两遍重复数据 先这样处理*/
                        if (list.contains(word)) break;
                        list.add(word);
                    }
                }
            }
            System.out.println("查询花费时间:" + rsp.getQTime());
            System.out.println("-------------query-over--------------");
        } catch (Exception e) {
            LoggerHelper.LOG.error("SolrClient query failed :" + e.getMessage());
        }
        return list;
	}
    
    public static void main(String[] args) {
    	SolrVehSearDao solr = new SolrVehSearDao();
    	try {
    		Page page = new Page();
    		solr.getPageVehSear(null, null, "", null, null, null, null, null, page);
			List<String> list = solr.getVehSuggestSear("赣A");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
