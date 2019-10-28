package com.guoshuai.mtdap.es.dao;




import com.guoshuai.mtdap.common.page.Page;
import com.guoshuai.mtdap.common.helper.ResultPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 快速搜车，数据来源ES
 */
public interface IEsVehSearDao {

    /**
     * 对过车记录进行查询并获取分页结果
     * @return 包含过车信息的page
     */
    ResultPojo getPageVehicleSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, ArrayList dataSource, String vehColor, String speedMin, String speedMax,
                                  String laneNumber, String srRightShield, String srRightBelt, String srRightCallup, String srPendant, String srYellowLabel, String srChemicals, String srBrand, String srSubBrand, List<String> regionIds, Page page);
    /**
     * 对过车记录进行聚合查询并获取分页结果
     * @return 包含过车信息的page
     */
    ResultPojo getAggVeh(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, ArrayList dataSource, String vehColor, String speedMin, String speedMax,
                         String laneNumber, String srRightShield, String srRightBelt, String srRightCallup, String srPendant, String srYellowLabel, String srChemicals, String srBrand, String srSubBrand, List<String> regionIds, Page page);


}

