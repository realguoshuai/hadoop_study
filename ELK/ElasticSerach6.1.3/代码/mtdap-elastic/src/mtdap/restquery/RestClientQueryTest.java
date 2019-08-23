package mtdap.restquery;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
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
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static mtdap.common.TimeHelper.ChangeRecordTime2DocTime;


/**
 * Description  RestClient 查询测试  使用 RestHighLevelClient
 * Created with guoshuai
 * date 2019/8/19 14:00
 */
public class RestClientQueryTest {

    private static RestHighLevelClient restHighLevelClient = null;
    private static SearchResponse searchResponse = null;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    public RestClientQueryTest() {
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(
                new HttpHost("192.168.94.41", 9200, "http")));
    }

    public static void query() {
        //
        String beginTime = ChangeRecordTime2DocTime("2019-08-23 00:00:00");
        String endTime = ChangeRecordTime2DocTime("2019-08-23 23:59:59");
/*
        String beginTime = ChangeRecordTime2DocTime("2019-08-20 00:00:00");
        String endTime = ChangeRecordTime2DocTime("2019-08-20 23:59:59");
*/
        int from =0;
        int size =10;

        //1:创建搜索请求
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.types("kkrecords");

        //2:用SearchSourceBuilder来构造查询请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //构造查询条件
        //sourceBuilder.query(QueryBuilders.matchPhrasePrefixQuery("license_number", "赣G99J90"));
        //TODO 会覆盖上面的
        //sourceBuilder.query(QueryBuilders.matchPhrasePrefixQuery("point_id", "231500625935985"));
        /*sourceBuilder.query(QueryBuilders.termQuery("license_number","赣G99J90"));
        //会覆盖上面的
        sourceBuilder.query(QueryBuilders.termQuery("point_id","231500625935985"));*/
        /*sourceBuilder.query(QueryBuilders.matchQuery("license_number","赣G99J90"));
        //会覆盖上面的
        sourceBuilder.query(QueryBuilders.matchQuery("point_id","231500625935985"));*/

        //时间范围查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("passing_time")
        .gte(beginTime)
        .lte(endTime));

        //TODO  match 和 term  对比
        //测试通过 ok
        boolQueryBuilder.must(QueryBuilders.matchQuery("point_id","3604003003360303"));
        /*matchPhraseQuery:中文分词后的精确查询   matchQuery 相关的也会展示*/
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("license_number","赣G83A84"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("license_type","VEHPLATETYPE02"));


        //TODO  must 和 filter  对比 filter快一点
        /*boolQueryBuilder.filter(QueryBuilders.matchQuery("point_id","3604003003510304"));
        boolQueryBuilder.filter(QueryBuilders.matchQuery("license_number","赣GX8933"));*/

        //bool(布尔)过滤器   用过复合过滤
        sourceBuilder.query(boolQueryBuilder);


        //分页 限制区间
        from = from <= -1 ? 0 : from;
        size = size >= 1000 ? 1000 : size;
        size = size <= 0 ? 10 : size;
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        //是否返回_source字段:查询的document 信息
        //sourceBuilder.fetchSource(false);
        sourceBuilder.fetchSource(true);

        //设置返回哪些字段 过滤一部分
        String[] includeFields = new String[]{"license_number", "passing_time", "license_type","point_id"};
        String[] excludeFields = new String[]{"veh_pic"};
        sourceBuilder.fetchSource(includeFields, excludeFields);


        //排序
        sourceBuilder.sort(new FieldSortBuilder("passing_time").order(SortOrder.DESC));

        //将请求体加入到请求中
        searchRequest.source(sourceBuilder);

        //3:发送请求
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //4:处理响应 SearchResponse(并在对象中包含查询执行的细节和符合条件的文档集合)
        RestStatus status = searchResponse.status();
        System.out.println("*****" + status);
        System.out.println("查询结果 : " + searchResponse.toString());

        //5:获取hits 以及
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();
        System.out.println("** totalHits: " + totalHits + " maxScore: " + maxScore);
        SearchHit[] searchHits = hits.getHits();
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : searchHits) {
            //元数据
            /*String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            System.out.println(" " +index+" " +type+" " +id+" " +score );*/

            //获取文档信息 String  或 HashMap
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            /*string 格式*/
            //System.out.println(sourceAsString);
            /*将map 放到list中 最后在一起转成json */
            list.add(sourceAsMap);
        }
        //6:查询结果json格式返回
        /*将查询的map放到list中  在将list转成json */
        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println(json);

    }

    public static void main(String args[]) {
        RestClientQueryTest restClientQueryTest = new RestClientQueryTest();
        query();
    }
}
