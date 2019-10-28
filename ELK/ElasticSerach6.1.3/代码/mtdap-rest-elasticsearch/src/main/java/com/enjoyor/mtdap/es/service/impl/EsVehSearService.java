package com.enjoyor.mtdap.es.service.impl;



import com.enjoyor.mtdap.common.page.Page;
import com.enjoyor.mtdap.es.dao.impl.EsVehSearDao;
import com.enjoyor.mtdap.es.service.IEsVehSearService;
import com.enjoyor.mtdap.common.helper.ResultPojo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 快速搜车，数据来源Es
 */

@Service
public class EsVehSearService implements IEsVehSearService {
    private EsVehSearDao dao =new EsVehSearDao();

    @Override
    public ResultPojo getPageVehicleSear(String licenseType, String licenseNumber, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, ArrayList dataSource, String vehColor, String speedMin, String speedMax,
                                         String laneNumber, String srRightShield, String srRightBelt, String srRightCallup, String srPendant, String srYellowLabel, String srChemicals, String srBrand, String srSubBrand, List<String> regionIds, Page page) {
        return dao.getPageVehicleSear(licenseType,licenseNumber,pointId,directionCode,startTime,endTime,deptId,dataSource,vehColor,speedMin,speedMax,
                laneNumber,srRightShield,srRightBelt,srRightCallup,srPendant,srYellowLabel,srChemicals,srBrand,srSubBrand,regionIds,page);
    }

    /**
     * 对过车记录进行聚合查询并获取分页结果
     *
     * @param licenseNumber 号牌号码 String  可以为null，支持模糊查询
     * @param licenseType   号牌类型  String 可为null
     * @param pointId       分析截止日期  String[] 可以为null
     * @param directionCode 卡口方向  String 可以为null
     * @param startTime     分析起始日期  String 可为null
     * @param endTime       分析截止日期 String  可为null
     * @param deptId        部门id Array 可以为null
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
     * @param page          分页 String 可以为null
     * @return 包含过车信息和错误信息的page (默认上限2w条数据)
     */
    @Override
    public ResultPojo getAggVeh(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, ArrayList dataSource, String vehColor, String speedMin, String speedMax, String laneNumber, String srRightShield, String srRightBelt, String srRightCallup, String srPendant, String srYellowLabel, String srChemicals, String srBrand, String srSubBrand, List<String> regionIds, Page page) {
        return dao.getAggVeh(licenseType,licenseNumber,pointId,directionCode,startTime,endTime,deptId,dataSource,vehColor,speedMin,speedMax,
                laneNumber,srRightShield,srRightBelt,srRightCallup,srPendant,srYellowLabel,srChemicals,srBrand,srSubBrand,regionIds,page);
    }

}
