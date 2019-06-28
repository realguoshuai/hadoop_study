package com.guoshuai.soa.traffic.rest.solr.dao;


import com.guoshuai.soa.traffic.util.pojo.Page;
import com.guoshuai.soa.traffic.util.pojo.ResultPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 快速搜车，数据来源Solr
 */
public interface ISolrVehSearDao {

    /**
     * 对过车记录进行查询并获取分页结果
     * @return 包含过车信息的page
     */
    //Page getPageVehicleSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId,String pointAttribute, Page page);
    ResultPojo getPageVehicleSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, String dataSource,String vehColor,String speedMin,String speedMax, Page page);

    /**
     * 对过车记录进行查询并获取分页结果
     * @return 包含过车信息的page
     */
    ResultPojo getPageVehSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId,String dataSource, Page page);

    /**
     * @param licenseNumber 车辆号牌智能提醒
     * @param page
     * @return
     */
    ResultPojo getLicenseNumberSuggest(String licenseNumber, Page page);


    /**
     * 对过车记录进行查询并获取List
     * @return 包含过车信息的List<pojo>
     */
    // List<SolrPassInfoPojo> getVehSear(String id, String licenseType, String licenseNumber,  String vehType, String pointId, String startTime, String endTime) throws Exception;

    /**
     * 对过车记录进行查询并获取分页结果
     *
     * @param ids 过车信息ID的list
     * @return 包含过车信息的List(默认上限10000条数据)
     */
    // List<SolrPassInfoPojo> getVehSear(List<String> ids) throws Exception;

    //List<SolrPassInfoPojo> getlist(String token, String id, String license_num, String license_type, List<String> pointId, String startTime, String endTime, Page page);

}

