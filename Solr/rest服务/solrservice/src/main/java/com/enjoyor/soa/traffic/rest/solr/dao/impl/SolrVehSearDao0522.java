/*
package com.enjoyor.soa.traffic.rest.solr.dao.impl;

import com.enjoyor.soa.traffic.rest.solr.bean.PassInfoPojo;
import com.enjoyor.soa.traffic.rest.solr.bean.SolrPassInfoPojo;
import com.enjoyor.soa.traffic.rest.solr.dao.ISolrVehSearDao;
import com.enjoyor.soa.traffic.rest.solr.util.CommonUtils;
import com.enjoyor.soa.traffic.rest.solr.util.SolrClientHelper;
import com.enjoyor.soa.traffic.util.enums.EnumAppCode;
import com.enjoyor.soa.traffic.util.helper.ResultHelper;
import com.enjoyor.soa.traffic.util.helper.TimeHelper;
import com.enjoyor.soa.traffic.util.pojo.Page;
import com.enjoyor.soa.traffic.util.pojo.ResultPojo;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.enjoyor.soa.traffic.rest.solr.util.SolrConnectionPool;

@Service
public class SolrVehSearDao0522 implements ISolrVehSearDao {
    public SolrClient client = null;
    public SolrClient suggest_client = null;

    public static final Logger logger = Logger.getLogger(SolrVehSearDao0522.class);

    public SolrVehSearDao0522() {
        this.client = getClient();
        this.suggest_client =getSuggestClient();
    }

    public static SolrClient getClient() {
        SolrClientHelper helper = new SolrClientHelper();
        SolrClient client = helper.getClient();
        return client;
    }

    public static SolrClient getSuggestClient(){
        SolrClientHelper helper = new SolrClientHelper();
        SolrClient client = helper.getSuggestClient();
        return client;
    }

    static <T> T getFiledValue(SolrDocument doc, Class<T> classType, String... fileds) {
        T ret = null;
        try {
            for (String filed : fileds) {
                ret = CommonUtils.castTo(doc.get(filed), classType);
                if (ret != null) break;
            }
        } catch (Throwable e) {
        }
        return ret;
    }

    */
/*public static List<SolrPassInfoPojo> solrDocs2PojoLst(SolrDocumentList docs) {
        List<SolrPassInfoPojo> lst = new ArrayList<SolrPassInfoPojo>();
        for (SolrDocument doc : docs) {
            SolrPassInfoPojo pojo = new SolrPassInfoPojo();
            //单机  集群
            pojo.setId(getFiledValue(doc, String.class, "id"));
            pojo.setPoint_id(getFiledValue(doc, String.class, "bayonet_id", "point_id"));
            pojo.setRegion_id(getFiledValue(doc, String.class, "region_id"));
            //pojo.setDept_id(getFiledValue(doc, String.class, "dept_id"));
            pojo.setDept_id(getFiledValue(doc, ArrayList.class, "dept_id"));
            pojo.setLicense_type(getFiledValue(doc, String.class, "vehicle_plate_type", "license_type"));
            pojo.setLane_number(getFiledValue(doc, String.class, "laneNumber"));
            pojo.setLicense_number(getFiledValue(doc, String.class, "vehicle_plate", "licenseNumber"));
            pojo.setPassing_time(getFiledValue(doc, String.class, "pass_time", "passing_time"));
            pojo.setSpeed(getFiledValue(doc, Integer.class, "speed"));
            pojo.setVeh_color(getFiledValue(doc, String.class, "vehicle_color", "color"));
            pojo.setVeh_length(getFiledValue(doc, String.class, "vehLength"));
            pojo.setPic_directory(getFiledValue(doc, String.class, "pic_url1", "picPath"));
            pojo.setVeh_pic(getFiledValue(doc, String.class, "pic_url2", "picPath2"));
            pojo.setCharacter_pic(getFiledValue(doc, String.class, "pic_url3", "picPath3"));
            pojo.setVeh_type(getFiledValue(doc, String.class, "vehicle_type"));
            pojo.setVeh_color(getFiledValue(doc, String.class, "vehicle_color"));
            pojo.setBrand(getFiledValue(doc, String.class, "vehicle_brand"));
            pojo.setSpeed_limit_min(getFiledValue(doc, Integer.class, "min_speed"));
            pojo.setSpeed_limit_max(getFiledValue(doc, String.class, "max_speed"));
            lst.add(pojo);
        }
        return lst;
    }
*//*

    @Override
    public ResultPojo getPageVehicleSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, String dataSource, Page page) {
        //集群 (不管单机集群都是使用二次识别字段 就用这一个)
        try {
            logger.info("***get params:   licenseNumber:" + licenseNumber + " licenseType:" + licenseType
                    + " pointIds:" + pointId + " directionCode:" + directionCode + " startTime:" + startTime + " endTime:" + endTime
                    + " deptId:" + deptId + " dataSource:" + dataSource + " page:" + page);

            SolrQuery params = new SolrQuery();
            QueryResponse rsp = null;
            List<SolrPassInfoPojo> list = new ArrayList<>();
            String[] strarr = new String[]{};

            //fq 一次只能加一个   需要使用fq+list
            List<String> filterParams = new ArrayList<>();
            */
/*全查询优化: q:*:* + fq + sort 最优*//*

            params.setQuery("*:*");
            */
/*过车时间查询*//*

            if ((startTime != null && startTime.length() > 0) && (endTime != null && endTime.length() > 0)) {
                Date startTimestr = TimeHelper.StringToDate(startTime, "yyyy-MM-dd HH:mm:ss");
                Date endTimestr = TimeHelper.StringToDate(endTime, "yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String time = "passing_time:[" + sdf.format(startTimestr) + " TO " + sdf.format(endTimestr) + "]";
                filterParams.add(time);
            }

            if (licenseType != null && licenseType.length() > 0) {
                filterParams.add("license_type:" + licenseType);
            }


            */
/*对用户车牌做出判断是否使用模糊匹配*//*

            if (licenseNumber != null && licenseNumber.length() > 0) {
                if (licenseNumber.length() == 7) {//代表完整号牌
                    System.out.println("判断车牌完整性生效");
                    filterParams.add("license_number:" + licenseNumber);
                } else {
                    */
/*通过检测GBK的方式判断是否包含中文 true 包含 false 不包含*//*

                    boolean isExistZW = licenseNumber.getBytes("GBK").length == licenseNumber.length() ? false : true;
                    if (isExistZW) {
                        System.out.println("判断车牌包含中文生效");
                        //包含中文 只需要添加后缀通配符 也有可能是最后一个是汉字 不严谨
                        filterParams.add("license_number:" + licenseNumber + "*");
                    } else {
                        filterParams.add("license_number:" + "*" + licenseNumber + "*");
                    }
                }
            }
            */
/*点位方向*//*

            if (directionCode != null && directionCode.length() > 0) {
                filterParams.add("direction:" + directionCode);
            }
            */
/*多点位 包含模糊匹配*//*

            if (pointId != null && pointId.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("( ");
                for (int i = 0; i < pointId.size(); i++) {
                    if (i == (pointId.size() - 1))
                        sb.append(pointId.get(i)).append("*").append(")");
                    else
                        sb.append(pointId.get(i)).append("*").append(" or ");
                }
                filterParams.add("point_id:" + sb.toString());
            }

            */
/*多部门 TODO 未测试*//*

            if (deptId != null && deptId.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("( ");
                for (int i = 0; i < deptId.size(); i++) {
                    if (i == (deptId.size() - 1))
                        sb.append(deptId.get(i)).append("*").append(")");
                    else
                        sb.append(deptId.get(i)).append("*").append(" or ");
                }
                filterParams.add("dept_id:" + sb.toString());
            }

            */
/*卡口属性 pointAttribute/dataSource 未测试*//*

            if (dataSource != null && dataSource.length() > 0) {
                //filterParams.add(" point_attribute:" +pointAttribute);
                filterParams.add("data_source:" + dataSource);
            }

            params.setFilterQueries(filterParams.toArray(strarr));
            params.setSort("passing_time", SolrQuery.ORDER.desc);
            params.setStart((page.getCurrentPage() - 1) * page.getPageSize() < 0 ? 0 : (page.getCurrentPage() - 1) * page.getPageSize());
            params.setRows(page.getPageSize());
            params.setParam("shards.tolerant", true);

            rsp = client.query(params);
            SolrDocumentList docs = rsp.getResults();
            System.out.println("查询内容:" + params);
            System.out.println("文档数量:" + docs.getNumFound());
            System.out.println("查询花费时间:" + rsp.getQTime());
            //日志打印
            DocumentObjectBinder binder = new DocumentObjectBinder();
            //类型转换 1 需要一一对应
            //list = binder.getBeans(SolrPassInfoPojo.class, docs);
            //类型转换 2 转换bean时格式不需要完全对照
            list = (List<SolrPassInfoPojo>) CommonUtils.toBeanList(docs, SolrPassInfoPojo.class);
            page.setTotalCount((int) docs.getNumFound());
            page.setResult(list);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return ResultHelper.getResult(null, EnumAppCode.UNKNOW, e.getMessage(), null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultHelper.getResult(null, EnumAppCode.UNKNOW, e.getMessage(), null);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultHelper.getResult(null, EnumAppCode.UNKNOW, e.getMessage(), null);
        }
        return ResultHelper.getResult(page);
    }

    */
/*单机*//*

    @Override
    public ResultPojo getPageVehSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, String dataSource, Page page) {
        try {
            System.out.println("***get params:   licenseNumber:" + licenseNumber + " licenseType:" + licenseType
                    + " pointIds:" + pointId + " directionCode:" + directionCode + " startTime:" + startTime + " endTime:" + endTime
                    + " deptId:" + deptId + " page:" + page);

            SolrQuery params = new SolrQuery();
            QueryResponse rsp = null;
            String[] strarr = new String[]{};
            //fq 一次只能加一个   需要使用fq+list
            List<String> filterParams = new ArrayList<>();
            //实体类不一样  不同字段类型对应不同的实体类
            List<PassInfoPojo> list = new ArrayList<>();
            */
/*全查询优化: q:*:* + fq + sort 最优*//*

            params.setQuery("*:*");
            */
/*过车时间查询*//*


            if ((startTime != null && startTime.length() > 0) && (endTime != null && endTime.length() > 0)) {
                Date startTimestr = TimeHelper.StringToDate(startTime, "yyyy-MM-dd HH:mm:ss");
                Date endTimestr = TimeHelper.StringToDate(endTime, "yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String time = "pass_time:[" + sdf.format(startTimestr) + " TO " + sdf.format(endTimestr) + "]";
                System.out.println(time);
                filterParams.add(time);
                //params.setFilterQueries(time);
            }

            if (licenseType != null && licenseType.length() > 0) {
                filterParams.add("vehicle_plate_type:" + licenseType);
                //params.setFilterQueries("vehicle_plate_type:" + licenseType);
            }
*/
/*            if (licenseNumber != null && licenseNumber.length() > 0) {
                //左模糊匹配  先不加上 数据量大影响性能呢过
                //filterParams.add("vehicle_plate:" +"*" +licenseNumber);
                filterParams.add("vehicle_plate:" +licenseNumber);
                //params.setFilterQueries("vehicle_plate:" + licenseNumber);
            }*//*

            */
/*对用户车牌做出判断是否使用模糊匹配*//*

            if (licenseNumber != null && licenseNumber.length() > 0) {
                if (licenseNumber.length() == 7) {//代表完整号牌
                    System.out.println("判断车牌完整性生效");
                    filterParams.add("vehicle_plate:" + licenseNumber);
                } else {
                    boolean isExistZW = licenseNumber.getBytes("GBK").length == licenseNumber.length() ? false : true;
                    if (isExistZW) {
                        System.out.println("判断车牌包含中文生效");
                        filterParams.add("vehicle_plate:" + licenseNumber + "*");
                    } else {
                        filterParams.add("vehicle_plate:" + "*" + licenseNumber + "*");
                    }
                }
            }


            */
/*点位方向*//*

            if (directionCode != null && directionCode.length() > 0) {
                filterParams.add("direction:" + directionCode);
                //params.setFilterQueries("direction:"+directionCode);
            }


            */
/*多点位 包含模糊匹配*//*

            if (pointId != null && pointId.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("( ");
                for (int i = 0; i < pointId.size(); i++) {
                    if (i == (pointId.size() - 1))
                        sb.append(pointId.get(i)).append("*").append(")");
                    else
                        sb.append(pointId.get(i)).append("*").append(" or ");
                }
                filterParams.add("bayonet_id:" + sb.toString());
                //params.setFilterQueries("bayonet_id:" + sb.toString());
            }

            */
/*多部门,暂时没有这个字段  TODO 未测试*//*

            if (deptId != null && deptId.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("( ");
                for (int i = 0; i < deptId.size(); i++) {
                    if (i == (deptId.size() - 1))
                        sb.append(deptId.get(i)).append("*").append(")");
                    else
                        sb.append(deptId.get(i)).append("*").append(" or ");
                }
                filterParams.add("dept_id:" + sb.toString());
                //params.setFilterQueries("dept_id:" + sb.toString());
            }

            */
/*卡口属性 pointAttribute 未测试*//*

            if (dataSource != null && dataSource.length() > 0) {
                filterParams.add("point_attribute:" + dataSource);
            }

            */
/*添加fq*//*

            params.setFilterQueries(filterParams.toArray(strarr));
            */
/*排序*//*

            params.setSort("pass_time", SolrQuery.ORDER.desc);
            params.setStart((page.getCurrentPage() - 1) * page.getPageSize() < 0 ? 0 : (page.getCurrentPage() - 1) * page.getPageSize());
            params.setRows(page.getPageSize());
            params.setParam("shards.tolerant", true);


            rsp = client.query(params);
            SolrDocumentList docs = rsp.getResults();
            System.out.println("查询内容:" + params);
            System.out.println("文档数量：" + docs.getNumFound());
            System.out.println("查询花费时间:" + rsp.getQTime());
            //日志打印
            DocumentObjectBinder binder = new DocumentObjectBinder();
            list = (List<PassInfoPojo>) CommonUtils.toBeanList(docs, PassInfoPojo.class);
            page.setTotalCount((int) docs.getNumFound());
            page.setResult(list);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return ResultHelper.getResult(null, EnumAppCode.UNKNOW, e.getMessage(), null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultHelper.getResult(null, EnumAppCode.UNKNOW, e.getMessage(), null);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultHelper.getResult(null, EnumAppCode.UNKNOW, e.getMessage(), null);
        }
        return ResultHelper.getResult(page);
    }

    */
/*车辆智能提醒*//*

    @Override
    public ResultPojo getLicenseNumberSuggest(String licenseNumber, Page page) {

        logger.info("***get params:   licenseNumber:" + licenseNumber);
        try {
            SolrQuery params = new SolrQuery();
            QueryResponse rsp = null;
            String[] strarr = new String[]{};
            //fq 一次只能加一个   需要使用fq+list
            List<String> filterParams = new ArrayList<>();
            //实体类不一样  不同字段类型对应不同的实体类
            List<PassInfoPojo> list = new ArrayList<>();
            */
/*全查询优化: q:*:* + fq + sort 最优*//*

            params.setQuery("*:*");
            */
/*过车时间查询*//*


            if (licenseNumber != null && licenseNumber.length() > 0) {
                filterParams.add("licenseNumber:" +"*"+ licenseNumber+"*");
            }

            */
/*添加fq*//*

            params.setFilterQueries(filterParams.toArray(strarr));
            params.setStart((page.getCurrentPage() - 1) * page.getPageSize() < 0 ? 0 : (page.getCurrentPage() - 1) * page.getPageSize());
            params.setRows(page.getPageSize());
            params.setParam("shards.tolerant", true);

            //rsp = client.query(params);
            rsp = suggest_client.query(params);
            SolrDocumentList docs = rsp.getResults();
            System.out.println("查询内容:" + params);
            System.out.println("文档数量：" + docs.getNumFound());
            System.out.println("查询花费时间:" + rsp.getQTime());
            //日志打印
            DocumentObjectBinder binder = new DocumentObjectBinder();
            list = (List<PassInfoPojo>) CommonUtils.toBeanList(docs, PassInfoPojo.class);
            page.setTotalCount((int) docs.getNumFound());
            page.setResult(list);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return ResultHelper.getResult(null, EnumAppCode.UNKNOW, e.getMessage(), null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultHelper.getResult(null, EnumAppCode.UNKNOW, e.getMessage(), null);
        }
        return ResultHelper.getResult(page);
    }


    */
/**
     * 执行 指定日期 +排序 + 封装成对象 + 分页 返回
     *
     * @return
     *//*

    public Page getPageVehSear(String token, String id, String license_num, String license_type, List<String> pointId, String startTime, String endTime, Page page) {
        try {

            SolrQuery params = new SolrQuery();
            List<SolrPassInfoPojo> list = new ArrayList<>();
            params.setQuery("*:*");
            if ((startTime != null && startTime.length() > 0) && (endTime != null && endTime.length() > 0)) {
                Date startTimestr = TimeHelper.StringToDate(startTime, "yyyy-MM-dd HH:mm:ss");
                Date endTimestr = TimeHelper.StringToDate(endTime, "yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String time = "passing_time:[" + sdf.format(startTimestr) + " TO " + sdf.format(endTimestr) + "]";
                params.setFilterQueries(time);
            }
            if (license_type != null && license_type.length() > 0) {
                params.setFilterQueries("license_type" + license_type);
            }
            if (license_num != null && license_num.length() > 0) {
                params.setFilterQueries("license_number:" + license_num);
            }
            if (id != null && id.length() > 0) {
                params.setFilterQueries("id:" + id);
            }
            if (pointId != null && pointId.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("( ");
                for (int i = 0; i < pointId.size(); i++) {
                    if (i == (pointId.size() - 1))
                        sb.append(pointId.get(i)).append("*").append(")");
                    else
                        sb.append(pointId.get(i)).append("*").append(" or ");
                }
                params.setFilterQueries("point_id:" + sb.toString());
            }

            params.setSort("passing_time", SolrQuery.ORDER.desc);
            params.setStart((page.getCurrentPage() - 1) * page.getPageSize() < 0 ? 0 : (page.getCurrentPage() - 1) * page.getPageSize());
            params.setRows(page.getPageSize());
            params.setParam("shards.tolerant", true);
            QueryResponse rsp = null;

            Long start4 = System.currentTimeMillis();
            rsp = client.query(params);
            SolrDocumentList docs = rsp.getResults();

            Long start1 = System.currentTimeMillis();
            list = (List<SolrPassInfoPojo>) CommonUtils.toBeanList(docs, SolrPassInfoPojo.class);
            page.setTotalCount((int) docs.getNumFound());
            page.setResult(list);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return page;
    }



    */
/*@Override
    public List<SolrPassInfoPojo> getVehSear(String id, String licenseType, String licenseNumber, String vehType, List<String> pointId, String startTime, String endTime) throws Exception {
        return null;
    }*//*


    */
/**
     * 执行 指定日期 +排序 + 封装成对象 + 分页 返回
     * @return
     *//*

   */
/* public List<SolrPassInfoPojo> getVehSear(String id, String license_type, String licenseNumber, String vehType, List<String> pointId, String startTime, String endTime) {
        List<SolrPassInfoPojo> beanList = null;
        try {
            getClient();
            SolrQuery params = new SolrQuery();
            List<SolrPassInfoPojo> list = new ArrayList<>();
            params.setQuery("*:*");
            if ((startTime != null && startTime.length() > 0) && (endTime != null && endTime.length() > 0)) {
                Date startTimestr = TimeHelper.StringToDate(startTime, "yyyy-MM-dd HH:mm:ss");
                Date endTimestr = TimeHelper.StringToDate(endTime, "yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String time = "passing_time:[" + sdf.format(startTimestr) + " TO " + sdf.format(endTimestr) + "]";
                params.setFilterQueries(time);
            }
            if (license_type != null && license_type.length() > 0) {
                params.setFilterQueries("license_type" + license_type);
            }
            if (license_num != null && license_num.length() > 0) {
                params.setFilterQueries("license_number:" + license_num);
            }
            if (id != null && id.length() > 0) {
                params.setFilterQueries("id:" + id);
            }
            if (pointId != null && pointId.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("( ");
                for (int i = 0; i < pointId.size(); i++) {
                    if (i == (pointId.size() - 1))
                        sb.append(pointId.get(i)).append("*").append(")");
                    else
                        sb.append(pointId.get(i)).append("*").append(" or ");
                }
                params.setFilterQueries("point_id:" + sb.toString());
            }

            params.setSort("passing_time", SolrQuery.ORDER.desc);
            params.setStart((page.getCurrentPage() - 1) * page.getPageSize() < 0 ? 0 : (page.getCurrentPage() - 1) * page.getPageSize());
            params.setRows(page.getPageSize());
            params.setParam("shards.tolerant", true);
            QueryResponse rsp = null;

            Long start4 = System.currentTimeMillis();
            rsp = client.query(params);
            SolrDocumentList docs = rsp.getResults();
            beanList = solrDocs2PojoLst(docs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanList;
    }


    @Override
    public List<SolrPassInfoPojo> getVehSear(List<String> ids) throws Exception {
        return null;
    }

    @Override
    public List<SolrPassInfoPojo> getlist(String token, String id, String license_num, String license_type, List<String> pointId, String startTime, String endTime, Page page) {
        return null;
    }*//*

}
*/
