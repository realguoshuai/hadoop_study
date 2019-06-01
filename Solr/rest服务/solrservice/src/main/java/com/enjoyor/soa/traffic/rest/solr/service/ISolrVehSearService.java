package com.enjoyor.soa.traffic.rest.solr.service;


import com.enjoyor.soa.traffic.util.pojo.Page;
import com.enjoyor.soa.traffic.util.pojo.ResultPojo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoshuai on 2019/04/12
 * @Description: 快速搜车，数据来源Solr，其中过车信息包含过车id和图片路径
 */

@Service
public interface ISolrVehSearService {

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
    //Page getPageVehicleSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId,String pointAttribute, Page page);
    ResultPojo getPageVehicleSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, String dataSource,String vehColor,String speedMin,String speedMax, Page page);

    /**
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
    ResultPojo getPageVehSear(String licenseNumber, String licenseType, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId,String dataSource, Page page);

    /**
     * Description
     * Param @param licenseNumber  号牌号码 String  可以为null，支持模糊查询
     * return com.enjoyor.soa.traffic.util.pojo.ResultPojo
     **/

    ResultPojo getLicenseNumberSuggest(String licenseNumber, Page page);


   // List<SolrPassInfoPojo> getVehSear(String id, String licenseType, String vehType, String licenseNumber, String startDay, String endDay, String pointId, String directionCode, Integer minSpeed, Integer maxSpeed) throws Exception;

    /**
     * 对过车记录进行查询并获取分页结果
     * PS 为了提高查询速度，建议尽可能输入车牌； 时间范围不确定时传null,比传具体时间范围性能更好
     * @param id          过车信息ID String  可以为null
     * @param licenseNum  号牌号码 String  可以为null，支持模糊查询
     * @param licenseType 号牌类型  String 可为null
     * @param vehType     车牌类型  String 可为null
     * @param startDay    分析起始日期  String 可为null
     * @param endDay      分析截止日期 String  可为null
     * @param pointId     分析截止日期  String[] 可以为null
     * @param directionCode 卡口方向  String 可以为null
     * @param minSpeed    速度范围下限  Integer 可以为null
     * @param maxSpeed    速度范围上限  Integer 可以为null
     * @return 包含过车信息的List (默认上限1000条数据)
     */
   // List<SolrPassInfoPojo> getVehSear(String id, String licenseType, String vehType, String licenseNum, String startDay, String endDay, String[] pointId, String directionCode, Integer minSpeed, Integer maxSpeed) throws Exception;

    /**
     * 对过车记录进行查询并获取分页结果
     * @param ids 过车信息ID的list<String>
     * @return 包含过车信息的Lists(默认上限1000条数据)
     */
    //List<SolrPassInfoPojo> getVehSear(List<String> ids) throws Exception;
}
