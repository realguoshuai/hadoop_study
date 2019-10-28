package com.guoshuai.mtdap.es.dao.impl;


import com.guoshuai.mtdap.common.helper.ResultHelper;
import com.guoshuai.mtdap.common.helper.ResultPojo;
import com.guoshuai.mtdap.common.page.Page;
import com.guoshuai.mtdap.es.dao.IEsVehSearDao;
import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.guoshuai.mtdap.common.helper.TimeHelper.ChangeRecordTime2DocTime;

@Service
public class EsVehSearDao implements IEsVehSearDao {
    public static final Logger logger = Logger.getLogger(EsVehSearDao.class);
    public long start = 0L;
    private static SearchResponse searchResponse = null;
    private static RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("x.x.x.19", 24148, "https"),
                    (new HttpHost("x.x.x.196", 24100, "https")),
                    (new HttpHost("x.x.x.197", 24100, "https")),
                    (new HttpHost("x.x.x.198", 24100, "https"))));

    /**
     * 对过车记录进行查询并获取分页结果
     * @return 包含过车信息的page
     */
    @Override
    public ResultPojo getPageVehicleSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, ArrayList dataSource, String vehColor, String speedMin, String speedMax, String laneNumber, String srRightShield, String srRightBelt, String srRightCallup, String srPendant, String srYellowLabel, String srChemicals, String srBrand, String srSubBrand, List<String> regionIds, Page page) {

        logger.info("***get params:   licenseNumber:" + licenseNumber + " licenseType:" + licenseType
                + " pointIds:" + pointId + " directionCode:" + directionCode + " startTime:" + startTime + " endTime:" + endTime
                + " deptId:" + deptId + " dataSource:" + dataSource + "vehColor: " + vehColor + " speedMax: " + speedMax + " speedMin: " + speedMin + " srBrand: " + srBrand + " srSubBrand: " + srSubBrand + " regionIds: " + regionIds
                + " page:" + page);

        //传入日期
        String beginT = ChangeRecordTime2DocTime(startTime);
        String endT = ChangeRecordTime2DocTime(endTime);
        int from = 0;
        int size = 10;

        //1:创建搜索请求
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.types("kkrecords");
        //2:用SearchSourceBuilder来构造查询请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //时间范围查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("passing_time")
                .gte(beginT)
                .lte(endT));

        /*点位id*/
        if (pointId != null && pointId.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("( ");
            for (int i = 0; i < pointId.size(); i++) {
                if (i == (pointId.size() - 1))
                    sb.append(pointId.get(i)).append("*").append(")");
                else
                    sb.append(pointId.get(i)).append("*").append(" or ");
            }
            boolQueryBuilder.must(QueryBuilders.matchQuery("point_id", sb.toString()));
        }

        /*号牌类型*/
        if (licenseType != null && licenseType.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("license_type", licenseType));
        }

        //TODO  模糊查询  使用must+matchPhraseQuery 汉字 + *  ok  字母 + *  查不到结果
        if (licenseNumber != null && licenseNumber.length() > 0) {
            if (licenseNumber.length() == 7) {
                boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("license_number", licenseNumber));
            } else {
                /*通过检测GBK的方式判断是否包含中文 true 包含 false 不包含*/
                boolean isExistZW = false;
                try {
                    isExistZW = licenseNumber.getBytes("GBK").length == licenseNumber.length() ? false : true;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (isExistZW) {
                    //包含中文 只需要添加后缀通配符 也有可能是最后一个是汉字 不严谨
                    /*判断输入开头的赣?  也可能是最后一位警?*/
                   /* if(licenseNumber.length()==1){
                        boolQueryBuilder.must(QueryBuilders.matchQuery("veh_attribution",licenseNumber));
                    }else{
                        boolQueryBuilder.must(QueryBuilders.wildcardQuery("license_number",licenseNumber+"*"));
                    }*/
                    //TODO  should  代替  must  有返回结果  返回结果相识度不高
                    //试一下 must + matchQuery()
                    boolQueryBuilder.should(QueryBuilders.wildcardQuery("license_number", licenseNumber + "*"));
                } else {
                    boolQueryBuilder.should(QueryBuilders.wildcardQuery("license_number", "*" + licenseNumber + "*"));
                }
            }
        }

        /*点位方向*/
        if (directionCode != null && directionCode.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("direction", directionCode));
        }

        /*管理部门(多)*/
        if (deptId != null && deptId.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("( ");
            for (int i = 0; i < deptId.size(); i++) {
                if (i == (deptId.size() - 1))
                    sb.append(deptId.get(i)).append(")");
                else
                    sb.append(deptId.get(i)).append(" or ");
            }
            boolQueryBuilder.must(QueryBuilders.matchQuery("dept_id", sb.toString()));
        }

        /*卡口属性(多个) pointAttribute/dataSource */
        if (dataSource != null && dataSource.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("( ");
            for (int i = 0; i < dataSource.size(); i++) {
                if (i == (dataSource.size() - 1))
                    sb.append(dataSource.get(i)).append(")");
                else
                    sb.append(dataSource.get(i)).append(" or ");
            }
            boolQueryBuilder.must(QueryBuilders.matchQuery("data_source", sb.toString()));
        }

        /*新增加 车身颜色,行驶速度作为查询条件*/
        if (vehColor != null && vehColor.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("veh_color", vehColor));
        }

        //范围查询
        if ((speedMax != null && speedMax.length() > 0) && (speedMin != null && speedMin.length() > 0)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("speed")
                    .gte(speedMin)
                    .lte(speedMax));
        }

        /*车道数,遮阳板,安全带,打电话,有挂件,黄标车,危化车*/
        if (laneNumber != null && laneNumber.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("lane_number", laneNumber));
        }
        if (srRightShield != null && srRightShield.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_right_shield", srRightShield));
        }
        if (srRightBelt != null && srRightBelt.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_right_belt", srRightBelt));
        }
        if (srRightCallup != null && srRightCallup.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_right_callup", srRightCallup));
        }
        if (srPendant != null && srPendant.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_pendant", srPendant));
        }
        if (srYellowLabel != null && srYellowLabel.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_yellow_label", srYellowLabel));
        }
        if (srChemicals != null && srChemicals.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_chemicals", srChemicals));
        }

        /*二次识别车辆品牌 车辆子品牌*/
        if (srBrand != null && srBrand.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_brand", srBrand));
        }
        if (srSubBrand != null && srSubBrand.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_subbrand", srSubBrand));
        }

        /*多区域id 查询*/
        if (regionIds != null && regionIds.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("( ");
            for (int i = 0; i < regionIds.size(); i++) {
                if (i == (regionIds.size() - 1))
                    sb.append(regionIds.get(i)).append(")");
                else
                    sb.append(regionIds.get(i)).append(" or ");
            }
            boolQueryBuilder.must(QueryBuilders.matchQuery("region_id", sb.toString()));
        }

        //bool(布尔)过滤器   用过复合过滤
        sourceBuilder.query(boolQueryBuilder);

        //分页 限制区间
        from = from <= -1 ? 0 : from;
        size = size >= 1000 ? 1000 : size;
        size = size <= 0 ? 10 : size;
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        //60s超时
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //是否返回_source字段:查询的document 信息
        sourceBuilder.fetchSource(true);

        //排序
        sourceBuilder.sort(new FieldSortBuilder("passing_time").order(SortOrder.DESC));

        //将sourceBuilder添加到searchRequest
        searchRequest.source(sourceBuilder);


        //统计查询时长
        start = System.currentTimeMillis();

        //3:发送请求
        try {
            System.out.println("查询参数: " + sourceBuilder);
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //4:处理响应 SearchResponse(并在对象中包含查询执行的细节和符合条件的文档集合)
        RestStatus status = searchResponse.status();
        System.out.println("查询状态: " + status);
        //System.out.println("查询结果 : " + searchResponse.toString());

        //5:获取hits 以及
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();
        System.out.println("文档数量: " + totalHits + " maxScore: " + maxScore);
        System.out.println("查询花费时间: " + (System.currentTimeMillis() - start) + "ms");
        SearchHit[] searchHits = hits.getHits();
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : searchHits) {
            //获取文档信息 String  或 HashMap
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }

        //6:查询结果json格式返回
        /*将查询的map放到list中  在将list转成json */
       /* Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println(json);*/
        page.setTotalCount((int) hits.getTotalHits());
        page.setResult(list);
        return ResultHelper.getResult(page);
    }

    /**
     * 对过车记录进行聚合查询并获取分页结果
     *
     * @param licenseNumber
     * @param licenseType
     * @param pointId
     * @param directionCode
     * @param startTime
     * @param endTime
     * @param deptId
     * @param dataSource
     * @param vehColor
     * @param speedMin
     * @param speedMax
     * @param laneNumber
     * @param srRightShield
     * @param srRightBelt
     * @param srRightCallup
     * @param srPendant
     * @param srYellowLabel
     * @param srChemicals
     * @param srBrand
     * @param srSubBrand
     * @param regionIds
     * @param page
     * @return 包含过车信息的page
     */
    @Override
    public ResultPojo getAggVeh(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, ArrayList dataSource, String vehColor, String speedMin, String speedMax, String laneNumber, String srRightShield, String srRightBelt, String srRightCallup, String srPendant, String srYellowLabel, String srChemicals, String srBrand, String srSubBrand, List<String> regionIds, Page page) {
        logger.info("***get params:   licenseNumber:" + licenseNumber + " licenseType:" + licenseType
                + " pointIds:" + pointId + " directionCode:" + directionCode + " startTime:" + startTime + " endTime:" + endTime
                + " deptId:" + deptId + " dataSource:" + dataSource + "vehColor: " + vehColor + " speedMax: " + speedMax + " speedMin: " + speedMin + " srBrand: " + srBrand + " srSubBrand: " + srSubBrand + " regionIds: " + regionIds
                + " page:" + page);

        //传入日期
        String beginT = ChangeRecordTime2DocTime(startTime);
        String endT = ChangeRecordTime2DocTime(endTime);
        int from = 0;
        int size = 10;

        //1:创建搜索请求
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.types("kkrecords");
        //2:用SearchSourceBuilder来构造查询请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //测试聚合查询
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_pointId").field("point_id")
                .order(BucketOrder.aggregation("average_balance", true));
        aggregation.subAggregation(AggregationBuilders.avg("average_balance")
                .field("balance"));



        //时间范围查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("passing_time")
                .gte(beginT)
                .lte(endT));

        //多条件查询
        /*点位id*/
        if (pointId != null && pointId.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("( ");
            for (int i = 0; i < pointId.size(); i++) {
                if (i == (pointId.size() - 1))
                    sb.append(pointId.get(i)).append("*").append(")");
                else
                    sb.append(pointId.get(i)).append("*").append(" or ");
            }
            boolQueryBuilder.must(QueryBuilders.matchQuery("point_id", sb.toString()));
        }

        /*号牌类型*/
        if (licenseType != null && licenseType.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("license_type", licenseType));
        }

        //TODO  模糊查询  使用must+matchPhraseQuery 汉字 + *  ok  字母 + *  查不到结果
        if (licenseNumber != null && licenseNumber.length() > 0) {
            if (licenseNumber.length() == 7) {
                boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("license_number", licenseNumber));
            } else {
                /*通过检测GBK的方式判断是否包含中文 true 包含 false 不包含*/
                boolean isExistZW = false;
                try {
                    isExistZW = licenseNumber.getBytes("GBK").length == licenseNumber.length() ? false : true;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (isExistZW) {
                    boolQueryBuilder.should(QueryBuilders.wildcardQuery("license_number", licenseNumber + "*"));
                } else {
                    boolQueryBuilder.should(QueryBuilders.wildcardQuery("license_number", "*" + licenseNumber + "*"));
                }
            }
        }

        /*点位方向*/
        if (directionCode != null && directionCode.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("direction", directionCode));
        }

        /*管理部门(多)*/
        if (deptId != null && deptId.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("( ");
            for (int i = 0; i < deptId.size(); i++) {
                if (i == (deptId.size() - 1))
                    sb.append(deptId.get(i)).append(")");
                else
                    sb.append(deptId.get(i)).append(" or ");
            }
            boolQueryBuilder.must(QueryBuilders.matchQuery("dept_id", sb.toString()));
        }

        /*卡口属性(多个) pointAttribute/dataSource */
        if (dataSource != null && dataSource.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("( ");
            for (int i = 0; i < dataSource.size(); i++) {
                if (i == (dataSource.size() - 1))
                    sb.append(dataSource.get(i)).append(")");
                else
                    sb.append(dataSource.get(i)).append(" or ");
            }
            boolQueryBuilder.must(QueryBuilders.matchQuery("data_source", sb.toString()));
        }

        /*新增加 车身颜色,行驶速度作为查询条件*/
        if (vehColor != null && vehColor.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("veh_color", vehColor));
        }

        //范围查询
        if ((speedMax != null && speedMax.length() > 0) && (speedMin != null && speedMin.length() > 0)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("speed")
                    .gte(speedMin)
                    .lte(speedMax));
        }

        /*车道数,遮阳板,安全带,打电话,有挂件,黄标车,危化车*/
        if (laneNumber != null && laneNumber.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("lane_number", laneNumber));
        }
        if (srRightShield != null && srRightShield.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_right_shield", srRightShield));
        }
        if (srRightBelt != null && srRightBelt.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_right_belt", srRightBelt));
        }
        if (srRightCallup != null && srRightCallup.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_right_callup", srRightCallup));
        }
        if (srPendant != null && srPendant.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_pendant", srPendant));
        }
        if (srYellowLabel != null && srYellowLabel.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_yellow_label", srYellowLabel));
        }
        if (srChemicals != null && srChemicals.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_chemicals", srChemicals));
        }

        /*二次识别车辆品牌 车辆子品牌*/
        if (srBrand != null && srBrand.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_brand", srBrand));
        }
        if (srSubBrand != null && srSubBrand.length() > 0) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sr_subbrand", srSubBrand));
        }

        /*多区域id 查询*/
        if (regionIds != null && regionIds.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("( ");
            for (int i = 0; i < regionIds.size(); i++) {
                if (i == (regionIds.size() - 1))
                    sb.append(regionIds.get(i)).append(")");
                else
                    sb.append(regionIds.get(i)).append(" or ");
            }
            boolQueryBuilder.must(QueryBuilders.matchQuery("region_id", sb.toString()));
        }




        //bool(布尔)过滤器   用过复合过滤
        sourceBuilder.query(boolQueryBuilder);

        //分页 限制区间
        from = from <= -1 ? 0 : from;
        size = size >= 1000 ? 1000 : size;
        size = size <= 0 ? 10 : size;
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        //60s超时
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //是否返回_source字段:查询的document 信息
        sourceBuilder.fetchSource(true);

        //排序
        sourceBuilder.sort(new FieldSortBuilder("passing_time").order(SortOrder.DESC));

        //将sourceBuilder添加到searchRequest
        searchRequest.source(sourceBuilder);


        //统计查询时长
        start = System.currentTimeMillis();

        //3:发送请求
        try {
            System.out.println("查询参数: " + sourceBuilder);
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //4:处理响应 SearchResponse(并在对象中包含查询执行的细节和符合条件的文档集合)
        RestStatus status = searchResponse.status();
        System.out.println("查询状态: " + status);
        //System.out.println("查询结果 : " + searchResponse.toString());

        //5:获取hits 以及
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();
        System.out.println("文档数量: " + totalHits + " maxScore: " + maxScore);
        System.out.println("查询花费时间: " + (System.currentTimeMillis() - start) + "ms");
        SearchHit[] searchHits = hits.getHits();
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : searchHits) {
            //获取文档信息 String  或 HashMap
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }

        //6:查询结果json格式返回
        /*将查询的map放到list中  在将list转成json */
       /* Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println(json);*/
        page.setTotalCount((int) hits.getTotalHits());
        page.setResult(list);
        return ResultHelper.getResult(page);
    }
}
