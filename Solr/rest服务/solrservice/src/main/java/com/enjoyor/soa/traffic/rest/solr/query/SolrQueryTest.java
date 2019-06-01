package com.enjoyor.soa.traffic.rest.solr.query;


import com.enjoyor.soa.traffic.rest.solr.bean.SolrPassInfoPojo;
import com.enjoyor.soa.traffic.rest.solr.util.CommonUtils;
import com.enjoyor.soa.traffic.rest.solr.util.SolrClientHelper;
import com.enjoyor.soa.traffic.util.enums.EnumAppCode;
import com.enjoyor.soa.traffic.util.helper.ResultHelper;
import com.enjoyor.soa.traffic.util.helper.TimeHelper;
import com.enjoyor.soa.traffic.util.pojo.Page;
import com.enjoyor.soa.traffic.util.pojo.ResultPojo;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.enjoyor.soa.traffic.rest.solr.util.SolrConnectionPool;

@Service
public class SolrQueryTest {
	private SolrClient client = null;

	private static final Logger logger = LoggerFactory.getLogger(SolrQueryTest.class);

	private SolrQueryTest() {
		this.client = getClient();

	}

	private static SolrClient getClient() {
        /*SolrClient client = SolrClientHelper.getClient();*/
		SolrClientHelper helper = new SolrClientHelper();
		SolrClient client = helper.getClient();
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


	/**
	 * 执行 指定日期 +排序 + 封装成对象 + 分页 返回
	 * @return
	 */
	public ResultPojo getlist(String token, String id, String license_num, String license_type, List<String> pointId, String startTime, String endTime, Page page) {
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
			return ResultHelper.getResult(page);
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

	}

}
