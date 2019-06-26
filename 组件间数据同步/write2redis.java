package com.guoshuai.mtdap.other;

import com.guoshuai.mtdap.common.JedisClusterUtil;
import com.guoshuai.mtdap.common.PhoenixJdbc;
import com.guoshuai.mtdap.phoenix2redis.FlowParamsSynch;
import net.minidev.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description  模拟数据存到redis中 要求json格式
 * Created with guoshuai
 * date 2019/6/26 11:39
 */
public class write2redis {
    private static Logger logger = Logger.getLogger(FlowParamsSynch.class.getName());

    public void synchBusLineInfo1(String key) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("road_name","公交专用道1");
        jsonObject.put("point_id","360107103404");
        jsonObject.put("lane_number","1");
        jsonObject.put("road_id","101");
        JedisClusterUtil.writeHash(key, "360107103404_101", jsonObject.toJSONString());
        System.out.println("*****************update mtdap3_keyroad value in redis.");

    }
    public void synchBusLineInfo2(String key) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("road_name","公交专用道1");
        jsonObject.put("point_id","360111102002");
        jsonObject.put("lane_number","2");
        jsonObject.put("road_id","101");
        JedisClusterUtil.writeHash(key, "360111102002_101", jsonObject.toJSONString());
        System.out.println("*****************update mtdap3_keyroad value in redis.");

    }
    public void synchBusLineInfo3(String key) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("road_name","公交专用道1");
        jsonObject.put("point_id","360104101701");
        jsonObject.put("lane_number","3");
        jsonObject.put("road_id","101");
        JedisClusterUtil.writeHash(key, "360104101701_101", jsonObject.toJSONString());
        System.out.println("*****************update mtdap3_keyroad value in redis.");

    }

    public static void main(String args[]) {
        write2redis redis = new write2redis();
        redis.synchBusLineInfo1("mtdap3_busline");
        redis.synchBusLineInfo2("mtdap3_busline");
        redis.synchBusLineInfo3("mtdap3_busline");
    }
}
