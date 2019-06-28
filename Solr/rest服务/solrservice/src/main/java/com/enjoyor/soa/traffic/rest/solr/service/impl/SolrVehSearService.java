package com.guoshuai.soa.traffic.rest.solr.service.impl;


import com.guoshuai.soa.traffic.rest.solr.dao.impl.SolrVehSearDao;
import com.guoshuai.soa.traffic.rest.solr.service.ISolrVehSearService;
import com.guoshuai.soa.traffic.util.pojo.Page;
import com.guoshuai.soa.traffic.util.pojo.ResultPojo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 快速搜车，数据来源Solr
 */

@Service
public class SolrVehSearService implements ISolrVehSearService {
    private SolrVehSearDao dao =new SolrVehSearDao();

    @Override
    public ResultPojo getPageVehicleSear(String licenseType, String licenseNumber, List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId, String dataSource,String vehColor,String speedMin,String speedMax, Page page) {
        return dao.getPageVehicleSear(licenseType,licenseNumber,pointId,directionCode,startTime,endTime,deptId,dataSource,vehColor,speedMin,speedMax,page);
    }
    /*直连九江测试*/
    @Override
    public ResultPojo getPageVehSear( String licenseType, String licenseNumber,List<String> pointId, String directionCode, String startTime, String endTime, ArrayList deptId,String dataSource, Page page) {
        return dao.getPageVehSear(licenseType,licenseNumber,pointId,directionCode,startTime,endTime,deptId,dataSource,page);
    }

    /*车辆智能提醒*/
    @Override
    public ResultPojo getLicenseNumberSuggest(String licenseNumber, Page page) {
        return dao.getLicenseNumberSuggest(licenseNumber,page);
    }

   /* @Override
    public Page getPageVehSear(String id, String licenseType, String vehType, String licenseNum, String startDay, String endDay, List<String> pointId, Page page) throws Exception {
        return dao.getlist(id, licenseType, vehType, licenseNum,pointId, startDay, endDay, page);
    }

    @Override
    public Page getPageVehSear(String token, String id, String licenseNumber, String licenseType, String vehType, List<String> pointId, String startTime, String endTime, Page page) throws Exception {
        try {
            String points = null;
            if(pointId != null && pointId.size() > 0){
                StringBuilder sb = new StringBuilder();
                sb.append("( ");
                for (int i = 0; i < pointId.size() ; i++) {
                    if(i == (pointId.size()-1))
                        sb.append(pointId.get(i)).append(")");
                    else
                        sb.append(pointId.get(i)).append(" or ");
                }
                points = sb.toString();
            }

        return dao.getlist(token,id,licenseNumber,licenseType,vehType,points,startTime,endTime,page);
    }*/

   /* @Override
    public List<SolrPassInfoPojo> getVehSear(String id, String licenseType, String vehType, String licenseNumber, String beginTime, String endTime, String[] pointId, String directionCode, Integer minSpeed, Integer maxSpeed) throws Exception {
        try {
            String points = null;
            if(pointId != null && pointId.length > 0){
                StringBuilder sb = new StringBuilder();
                sb.append("( ");
                for (int i = 0; i < pointId.length ; i++) {
                    if(i == (pointId.length-1))
                        sb.append(pointId[i]).append(")");
                    else
                        sb.append(pointId[i]).append(" or ");
                }
                points = sb.toString();
            }

            return dao.getlist(id, licenseType, vehType, licenseNumber, beginTime, endTime, points, directionCode, minSpeed, maxSpeed);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<SolrPassInfoPojo> getVehSear(List<String> ids) throws Exception {
        try {
            return dao.getVehSear(ids);
        } catch (Exception e) {
            throw e;
        }
    }*/

}
