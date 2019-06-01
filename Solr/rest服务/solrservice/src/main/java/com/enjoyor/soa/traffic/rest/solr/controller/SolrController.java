package com.enjoyor.soa.traffic.rest.solr.controller;

import com.enjoyor.soa.traffic.rest.solr.query.SolrQueryTest;
import com.enjoyor.soa.traffic.rest.solr.service.impl.SolrVehSearService;
import com.enjoyor.soa.traffic.util.pojo.Page;
import com.enjoyor.soa.traffic.util.pojo.ResultPojo;
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
@RequestMapping(value = "/solr")
@Api(value = "SolrController", description = "solr")
public class SolrController {
	@Autowired
	private SolrQueryTest solrQueryTest;
	@Autowired
	private SolrVehSearService solrVehSearService;


	@ApiOperation(value = "searveh")
	@GetMapping(value = "/searveh")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "licenseNumber", value = "号牌号码", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "licenseType", value = "号牌类型", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "pointIds", value = "过车卡口 多个逗号隔开", required = false, paramType = "query", dataType = "List<String>", defaultValue = ""),
			@ApiImplicitParam(name = "directionCode", value = "点位方向", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "startTime", value = "开始日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "endTime", value = "结束日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "deptId", value = "部门id", required = false, paramType = "query", dataType = "ArrayList", defaultValue = ""),
            @ApiImplicitParam(name = "dataSource", value = "卡口属性", required = false, paramType = "query", dataType = "String", defaultValue = ""),
			@ApiImplicitParam(name = "vehColor", value = "车身颜色", required = false, paramType = "query", dataType = "String", defaultValue = ""),
			@ApiImplicitParam(name = "speedMin", value = "行车速度查询下限", required = false, paramType = "query", dataType = "String", defaultValue = ""),
			@ApiImplicitParam(name = "speedMax", value = "行车速度查询上限", required = false, paramType = "query", dataType = "String", defaultValue = ""),
	})
	public ResultPojo getVehSear(@RequestParam(required = false) String licenseNumber,
						  @RequestParam(required = false) String licenseType,
						  @RequestParam(required = false) List<String> pointIds,
						  @RequestParam(required = false) String directionCode,
						  @RequestParam(required = false) String startTime,
						  @RequestParam(required = false) String endTime,
						  @RequestParam(required = false) ArrayList deptId,
						  @RequestParam(required = false) String dataSource,
						  @RequestParam(required = false) String vehColor,
						  @RequestParam(required = false) String speedMin,
						  @RequestParam(required = false) String speedMax,
						  Page page) {
		return solrVehSearService.getPageVehicleSear(licenseNumber,licenseType,  pointIds,directionCode, startTime, endTime,deptId,dataSource,vehColor,speedMin,speedMax,page);
	}

	//直连到九江单机测试
	@ApiOperation(value = "search")
	@GetMapping(value = "/search")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "licenseNumber", value = "号牌号码", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "licenseType", value = "号牌类型", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "pointIds", value = "过车卡口 多个逗号隔开", required = false, paramType = "query", dataType = "List<String>", defaultValue = ""),
			@ApiImplicitParam(name = "directionCode", value = "点位方向", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "startTime", value = "开始日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "endTime", value = "结束日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "deptId", value = "部门id", required = false, paramType = "query", dataType = "ArrayList", defaultValue = ""),
            @ApiImplicitParam(name = "dataSource", value = "卡口属性", required = false, paramType = "query", dataType = "String", defaultValue = ""),
	})
	public ResultPojo testVehSear(@RequestParam(required = false) String licenseNumber,
						  @RequestParam(required = false) String licenseType,
						  @RequestParam(required = false) List<String> pointIds,
						  @RequestParam(required = false) String directionCode,
						  @RequestParam(required = false) String startTime,
						  @RequestParam(required = false) String endTime,
						  @RequestParam(required = false) ArrayList deptId,
                          @RequestParam(required = false) String dataSource,
						  Page page) {
		return solrVehSearService.getPageVehSear(licenseNumber,licenseType,  pointIds,directionCode, startTime, endTime,deptId,dataSource,page);
	}

    //车牌模糊查询
    @ApiOperation(value = "suggest")
    @GetMapping(value = "/suggest")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "licenseNumber", value = "号牌号码", required = false, paramType = "query", dataType = "string", defaultValue = "")
    })
    public ResultPojo testVehSear(@RequestParam(required = false) String licenseNumber,
                                  Page page) {
        return solrVehSearService.getLicenseNumberSuggest(licenseNumber,page);
    }

 /*测试json
	@GetMapping(value = "/test-json")
	@ApiImplicitParams({
		@ApiImplicitParam(name="passing_time",value = "过车时间",required = false,paramType = "query",dataType = "string",defaultValue = "")
	})
	public List testJson(){
		ArrayList<String> list = new ArrayList<>();
		for(int i=0;i<10;i++){
			list.add(String.valueOf(i));
		}
		return list;
	}*/


/*    @ApiOperation(value = "getsolr")
    @GetMapping(value = "/getsolr")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "令牌", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "id", value = "过车id", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "license_num", value = "号牌号码", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "license_type", value = "号牌类型", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "pointIds", value = "过车卡口 多个逗号隔开", required = false, paramType = "query", dataType = "List<String>", defaultValue = ""),
            @ApiImplicitParam(name = "startTime", value = "开始日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "endTime", value = "结束日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
    })
    public ResultPojo getsolr(@RequestParam(required = false) String token,
                              @RequestParam(required = false) String id,
                              @RequestParam(required = false) String license_num,
                              @RequestParam(required = false) String license_type,
                              @RequestParam(required = false) List<String> pointIds,
                              @RequestParam(required = false) String startTime,
                              @RequestParam(required = false) String endTime,
                              Page page) {
        return solrQueryTest.getlist(token, id, license_num, license_type, pointIds, startTime, endTime, page);
    }*/


    /* bak
    @ApiOperation(value = "searveh")
    @GetMapping(value = "/searveh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "licenseNumber", value = "号牌号码", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "licenseType", value = "号牌类型", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "pointIds", value = "过车卡口 多个逗号隔开", required = false, paramType = "query", dataType = "List<String>", defaultValue = ""),
            @ApiImplicitParam(name = "directionCode", value = "点位方向", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "startTime", value = "开始日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "endTime", value = "结束日期 yyyy-MM-dd HH:mm:ss", required = false, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "deptId", value = "部门id", required = false, paramType = "query", dataType = "ArrayList", defaultValue = ""),
    })
    public Page getVehSear(@RequestParam(required = false) String licenseNumber,
                           @RequestParam(required = false) String licenseType,
                           @RequestParam(required = false) List<String> pointIds,
                           @RequestParam(required = false) String directionCode,
                           @RequestParam(required = false) String startTime,
                           @RequestParam(required = false) String endTime,
                           @RequestParam(required = false) ArrayList deptId,
                           Page page) {
        return solrVehSearService.getPageVehicleSear(licenseNumber,licenseType,  pointIds,directionCode, startTime, endTime,deptId,page);
    }*/
}
