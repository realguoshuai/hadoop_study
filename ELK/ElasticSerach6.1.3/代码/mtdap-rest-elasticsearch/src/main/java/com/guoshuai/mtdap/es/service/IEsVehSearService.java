package com.guoshuai.mtdap.es.service;




import com.guoshuai.mtdap.common.helper.ResultPojo;
import org.springframework.stereotype.Service;
import com.guoshuai.mtdap.common.page.Page;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoshuai on 2019/10/09
 * @Description: 快速搜车，数据来源Es，其中过车信息包含过车id和图片路径
 */

@Service
public interface IEsVehSearService {

    /**
     * 对过车记录进行查询并获取分页结果
     * @param licenseType 号牌类型  String 可为null
     * @param licenseNumber  号牌号码 String  可以为null，支持模糊查询
     * @param startTime    分析起始日期  String 可为null
     * @param endTime      分析截止日期 String  可为null
     * @param pointId     分析截止日期  String[] 可以为null
     * @param directionCode 卡口方向  String 可以为null
     * @param deptId      部门id Array 可以为null
     * @param page        分页 String 可以为null
     * @return 包含过车信息和错误信息的page (默认上限2w条数据)
     */

    ResultPojo getPageVehicleSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, ArrayList dataSource, String vehColor, String speedMin, String speedMax,
                                  String laneNumber, String srRightShield, String srRightBelt, String srRightCallup, String srPendant, String srYellowLabel, String srChemicals, String srBrand, String srSubBrand, List<String> regionIds, Page page);


    /**
     * 对过车记录进行聚合查询并获取分页结果
     * @param licenseType 号牌类型  String 可为null
     * @param licenseNumber  号牌号码 String  可以为null，支持模糊查询
     * @param startTime    分析起始日期  String 可为null
     * @param endTime      分析截止日期 String  可为null
     * @param pointId     分析截止日期  String[] 可以为null
     * @param directionCode 卡口方向  String 可以为null
     * @param deptId      部门id Array 可以为null
     * @param page        分页 String 可以为null
     * @return 包含过车信息和错误信息的page (默认上限2w条数据)
     */

    ResultPojo getAggVeh(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, ArrayList dataSource, String vehColor, String speedMin, String speedMax,
                                  String laneNumber, String srRightShield, String srRightBelt, String srRightCallup, String srPendant, String srYellowLabel, String srChemicals, String srBrand, String srSubBrand, List<String> regionIds, Page page);


}
