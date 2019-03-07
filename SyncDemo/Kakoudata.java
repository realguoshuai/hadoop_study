package com.enjoyor.mtdap.datasynch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.enjoyor.mtdap.config.HiveConfig;
import com.enjoyor.mtdap.jdbc.HiveJdbc;
import com.enjoyor.mtdap.jdbc.PhoenixJdbc;
import com.enjoyor.mtdap.pojo.Kkdata;
import com.enjoyor.mtdap.util.DateTimeUtil;

public class Kakoudata {

    long record_id = 0;
    String pass_time = null;
    String point_id = null;
    String license_type = null;
    String license_color = null;
    String license_num = null;
    int lane_number = 0;
    int speed = 0;
    String veh_color = null;
    String brand = null;


    //3000条提交一次
    int j = 0;
    long start = System.currentTimeMillis();


    private void readFromHive(String sql) throws SQLException {
        HiveJdbc hiveJdbc = new HiveJdbc();

        Connection connection = hiveJdbc.getConnection();

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Kkdata kkdata = null;
        ArrayList<Kkdata> batchData = null;

        try {
            // 执行HQL
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            batchData = new ArrayList<Kkdata>();

            // 输出查询结果到控制台
            while (resultSet.next()) {
                //System.out.println("查到数据了");

                if (resultSet.getString("record_id") != null)
                    record_id = Long.parseLong(resultSet.getString("record_id"));
               // System.out.println("--------- hive parse later record_id : " + record_id);
                if (resultSet.getString("point_id") != null) point_id = resultSet.getString("point_id");
                if (resultSet.getString("license_type") != null) license_type = resultSet.getString("license_type");
                if (resultSet.getString("license_color") != null) license_color = resultSet.getString("license_color");
                if (resultSet.getString("lane_number") != null) lane_number = (int)Float.parseFloat(resultSet.getString("lane_number"));
                if (resultSet.getFloat("speed") != 0.0) speed = (resultSet.getInt("speed"));
                if (resultSet.getString("license_number") != null)
                    license_num = resultSet.getString("license_number");
                //hive 中 string  Phoenix Date
                if (resultSet.getString("passing_time") != null)
                    pass_time = resultSet.getString("passing_time");
                if (resultSet.getString("veh_color") != null) veh_color = resultSet.getString("veh_color");
                if (resultSet.getString("brand") != null) brand = resultSet.getString("brand");


               /* System.out.println(
                        "----" + record_id + " " + point_id + " " + license_type + " " + license_color + "" +
                                " " +  " " + lane_number + " " + speed + " " +
                                " " + license_num + " " + pass_time + " " +
                                " " + veh_color + " " + " " + brand);   */

                kkdata = new Kkdata();

                kkdata.setRECORD_ID(record_id);
                kkdata.setPOINT_ID(point_id);
                kkdata.setLICENSE_TYPE(license_type);
                kkdata.setLICENSE_COLOR(license_color);
                kkdata.setLANE_NUMBER(lane_number);
                kkdata.setSPEED(speed);
                kkdata.setLICENSE_NUMBER(license_num);
                kkdata.setPASSING_TIME(pass_time);
                kkdata.setVEH_COLOR(veh_color);
                kkdata.setBRAND(brand);


                batchData.add(kkdata);
                long start = System.currentTimeMillis();
                if (resultSet.getRow() % HiveConfig.batchRows == 0) {
                    System.out.println("从hive中拿到" + HiveConfig.batchRows + "条");
                    writeToPhoenix(batchData);
                    batchData.clear();
                }
            }
            writeToPhoenix(batchData);

        } finally {
            if (null != resultSet) {
                resultSet.close();
            }
            if (null != statement) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    private void writeToPhoenix(ArrayList<Kkdata> batchData) throws SQLException {
        PhoenixJdbc phoenixJdbc = new PhoenixJdbc();

        //String sql = "upsert into MTDAP2.BASE_KKDATA_7DAY_COMMON values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //String sql = "UPSERT INTO MTDAP2.BASE_KKDATA_7DAY_COMMON (RECORD_ID,POINT_ID,REGION_ID,DEPT_ID,LICENSE_TYPE,LICENSE_COLOR,VEH_TYPE,LANE_NUMBER,SPEED,VEH_LENGTH,RECORD_TYPE,VIO_CODE,LICENSE_NUMBER,DATA_SOURCE,PASSING_TIME,SPEED_LIMIT_MAX,SPEED_LIMIT_MIN,VEH_COLOR,SURVEIL_TYPE,BRAND,RECEIVE_TIME,INSERT_TIME,PIC_DIRECTORY,VEH_PIC,CHARACTER_PIC,STANDBY_PIC,VIDEO_PATH,SR_SUBMIT_TIME,SR_RECEIVE_TIME,SR_LICENSE_NUMBER,SR_LICENSE_NUMBER_CONF,SR_LICENSE_TYPE,SR_LICENSE_TYPE_CONF,SR_LICENSE_COLOR,SR_LICENSE_COLOR_CONF,SR_VEH_TYPE,SR_VEH_TYPE_CONF,SR_VEH_COLOR,SR_VEH_COLOR_CONF,SR_BRAND,SR_BRAND_CONF,SR_SUBBRAND,SR_SUBBRAND_CONF,SR_RIGHT_BELT,SR_RIGHT_BELT_CONF,SR_LEFT_BELT,SR_LEFT_BELT_CONF,SR_RIGHT_SHIELD,SR_RIGHT_SHIELD_CONF,SR_LEFT_SHIELD,SR_LEFT_SHIELD_CONF,SR_RIGHT_CALLUP,SR_RIGHT_CALLUP_CONF,SR_LEFT_CALLUP,SR_LEFT_CALLUP_CONF,SR_PENDANT,SR_PENDANT_CONF,SR_YELLOW_LABEL,SR_YELLOW_LABEL_CONF,SR_CHEMICALS,SR_CHEMICALS_CONF,SR_DAMAGE,SR_DAMAGE_CONF,SR_FACE_PIC_PATH,SR_LICENSE_PIC_PATH) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String sql = "upsert into MTDAP2.BASE_KKDATA_7DAY_STANDARD(RECORD_ID,PASS_TIME,POINT_ID," +
                "LICENSE_TYPE,LICENSE_COLOR,LICENSE_NUM,LANE_NUMBER,SPEED,VEH_COLOR,BRAND) values (?, to_date(?), ?, ?, ?, ?, ?,?,?,?)";
        Connection connection = null;
        PreparedStatement preStat = null;

        try {
            connection = phoenixJdbc.getConnection();
            preStat = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            for (Kkdata kkdata : batchData) {
                preStat.setLong(1, kkdata.getRECORD_ID());
                preStat.setString(2, kkdata.getPASSING_TIME());

                preStat.setString(3, kkdata.getPOINT_ID());
                preStat.setString(4, kkdata.getLICENSE_TYPE());
                preStat.setString(5, kkdata.getLICENSE_COLOR());

                preStat.setString(6, kkdata.getLICENSE_NUMBER());
                preStat.setInt(7, kkdata.getLANE_NUMBER());
                preStat.setInt(8, kkdata.getSPEED());
                preStat.setString(9, kkdata.getVEH_COLOR());
                preStat.setString(10, kkdata.getBRAND());
                preStat.addBatch();
            }
            preStat.executeBatch();
            connection.commit();
            System.out.println("向Phoenix中写入" + batchData.size() + "条");
        } finally {
            if (preStat != null) {
                preStat.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void executeDataSynch(String sql) {
        try {
            readFromHive(sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub
        Kakoudata firstIntoVeh = new Kakoudata();

        //String sql = "select pass_day, veh_type, license_num, is_local, into_point, into_time from first_into_veh";

        //firstIntoVeh.executeDataSynch(sql);
        String sql = "select * from dwd.base_kkdata where passing_time >= '2019-01-23' and passing_time < '2019-01-28'";
        firstIntoVeh.executeDataSynch(sql);
    }
}
