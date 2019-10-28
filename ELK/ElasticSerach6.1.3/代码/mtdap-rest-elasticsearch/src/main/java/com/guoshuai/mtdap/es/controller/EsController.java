package com.guoshuai.mtdap.es.controller;

import com.guoshuai.mtdap.common.page.Page;
import com.guoshuai.mtdap.es.service.impl.EsVehSearService;
import com.guoshuai.mtdap.common.helper.ResultPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/es")
@Api(value = "EsController", description = "es")
public class EsController {
	@Autowired
	private EsVehSearService solrVehSearService;

    @ApiOperation(value = "search-veh")
    @GetMapping(value = "/search-veh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "licenseNumber", value = "号牌号码", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "licenseType", value = "号牌类型", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "pointIds", value = "过车卡口 多个逗号隔开", required = false, paramType = "query", dataType = "List<String>", defaultValue = ""),
            @ApiImplicitParam(name = "directionCode", value = "点位方向", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "startTime", value = "开始日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "endTime", value = "结束日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "deptId", value = "部门id 多个逗号隔开", required = false, paramType = "query", dataType = "ArrayList", defaultValue = ""),
            @ApiImplicitParam(name = "dataSource", value = "卡口属性 多个逗号隔开", required = false, paramType = "query", dataType = "ArrayList", defaultValue = ""),
            @ApiImplicitParam(name = "vehColor", value = "车身颜色", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "speedMin", value = "行车速度查询下限", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "speedMax", value = "行车速度查询上限", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "laneNumber", value = "车道", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srRightShield", value = "遮阳板", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srRightBelt", value = "安全带", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srRightCallup", value = "打电话", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srPendant", value = "有挂件", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srYellowLabel", value = "黄标车", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srChemicals", value = "危化车", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srBrand", value = "车辆品牌", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srSubBrand", value = "车辆子品牌", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "regionIds", value = "区域id 多个逗号隔开", required = false, paramType = "query", dataType = "List<String>", defaultValue = ""),
    })
    public ResultPojo getVehSear(@RequestParam(required = false) String licenseNumber,
                                 @RequestParam(required = false) String licenseType,
                                 @RequestParam(required = false) List<String> pointIds,
                                 @RequestParam(required = false) String directionCode,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime,
                                 @RequestParam(required = false) ArrayList deptId,
                                 @RequestParam(required = false) ArrayList dataSource,
                                 @RequestParam(required = false) String vehColor,
                                 @RequestParam(required = false) String speedMin,
                                 @RequestParam(required = false) String speedMax,
                                 @RequestParam(required = false) String laneNumber,
                                 @RequestParam(required = false) String srRightShield,
                                 @RequestParam(required = false) String srRightBelt,
                                 @RequestParam(required = false) String srRightCallup,
                                 @RequestParam(required = false) String srPendant,
                                 @RequestParam(required = false) String srYellowLabel,
                                 @RequestParam(required = false) String srChemicals,
                                 @RequestParam(required = false) String srBrand,
                                 @RequestParam(required = false) String srSubBrand,
                                 @RequestParam(required = false) List<String> regionIds,
                                 Page page) {
        return solrVehSearService.getPageVehicleSear(licenseNumber,licenseType,  pointIds,directionCode, startTime, endTime,deptId,dataSource,vehColor,speedMin,speedMax,
                laneNumber,srRightShield,srRightBelt,srRightCallup,srPendant,srYellowLabel,srChemicals,srBrand,srSubBrand,regionIds,page);
    }



    @ApiOperation(value = "aggs-veh")
    @GetMapping(value = "/aggs-veh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "licenseNumber", value = "号牌号码", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "licenseType", value = "号牌类型", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "pointIds", value = "过车卡口 多个逗号隔开", required = false, paramType = "query", dataType = "List<String>", defaultValue = ""),
            @ApiImplicitParam(name = "directionCode", value = "点位方向", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "startTime", value = "开始日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "endTime", value = "结束日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "vehColor", value = "车身颜色", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "speedMin", value = "行车速度查询下限", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "speedMax", value = "行车速度查询上限", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "laneNumber", value = "车道", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srRightBelt", value = "安全带", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srRightCallup", value = "打电话", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srYellowLabel", value = "黄标车", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "srChemicals", value = "危化车", required = false, paramType = "query", dataType = "String", defaultValue = ""),
    })
    public ResultPojo AggVehSear(@RequestParam(required = false) String licenseNumber,
                                 @RequestParam(required = false) String licenseType,
                                 @RequestParam(required = false) List<String> pointIds,
                                 @RequestParam(required = false) String directionCode,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime,
                                 @RequestParam(required = false) ArrayList deptId,
                                 @RequestParam(required = false) ArrayList dataSource,
                                 @RequestParam(required = false) String vehColor,
                                 @RequestParam(required = false) String speedMin,
                                 @RequestParam(required = false) String speedMax,
                                 @RequestParam(required = false) String laneNumber,
                                 @RequestParam(required = false) String srRightShield,
                                 @RequestParam(required = false) String srRightBelt,
                                 @RequestParam(required = false) String srRightCallup,
                                 @RequestParam(required = false) String srPendant,
                                 @RequestParam(required = false) String srYellowLabel,
                                 @RequestParam(required = false) String srChemicals,
                                 @RequestParam(required = false) String srBrand,
                                 @RequestParam(required = false) String srSubBrand,
                                 @RequestParam(required = false) List<String> regionIds,
                                 Page page) {
        return solrVehSearService.getAggVeh(licenseNumber,licenseType,  pointIds,directionCode, startTime, endTime,deptId,dataSource,vehColor,speedMin,speedMax,
                laneNumber,srRightShield,srRightBelt,srRightCallup,srPendant,srYellowLabel,srChemicals,srBrand,srSubBrand,regionIds,page);
    }
}
