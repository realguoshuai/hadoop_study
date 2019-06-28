package com.mtdap.keyveh;

import com.guoshuai.mtdap.common.HiveJdbc;
import com.guoshuai.mtdap.common.InitPropertiesUtil;
import com.mtdap.common.PhoenixJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * od 线路信息
 */
public class ODPathinfoJdbc {
    private static final Logger logger = LoggerFactory.getLogger(ODPathinfoJdbc.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    String path_id = null;
    String path_detail = null;

    private int j = 0;
    long start = System.currentTimeMillis();


    private void doWork(String sql) throws SQLException {
        HiveJdbc hiveJdbc = new HiveJdbc();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        Connection connection = hiveJdbc.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //phoenix
        PhoenixJdbc phoenixJdbc = new PhoenixJdbc();
        //String pSql = "UPSERT INTO mtdap.od_depart values (?,?)";
        String pSql = "UPSERT into mtdap.od_pathinfo values(?, ?)";
        Connection pConnection = null;
        PreparedStatement preStat = null;

        try {
            statement = connection.prepareStatement(sql);
            if ("true".equals(InitPropertiesUtil.initHiveProp().getProperty("queenTrigger"))) {
                statement.execute("set mapreduce.job.queuename = guoshuai");
            }
            resultSet = statement.executeQuery();
            //phoenix
            pConnection = phoenixJdbc.getConnection();
            preStat = pConnection.prepareStatement(pSql);
            pConnection.setAutoCommit(false);
            long startTmp = System.currentTimeMillis();
            while (resultSet.next()) {
                j++;

                path_id = resultSet.getString("path_id");
                path_detail = resultSet.getString("path_detail");

                preStat.setString(1, path_id);
                preStat.setString(2, path_detail);
                preStat.addBatch();


                if (resultSet.getRow() % 10000 == 0) {
                    long endTmp = System.currentTimeMillis();
                    preStat.executeBatch();
                    pConnection.commit();
                    /*System.out.println("从hive中拿到" + HiveConfig.batchRows + "条");
                    System.out.println("该批次数据插入花费时间time/s --------------" + (endTmp - startTmp) / 1000.0);
                    startTmp = System.currentTimeMillis();*/
                }
            }

            preStat.executeBatch();
            pConnection.commit();
            long end = System.currentTimeMillis();
            logger.info("数据全部插入完毕time/min --------------" + (end - start) / 60000);
            logger.info("已经插入 " + j + " 条数据，mtdap.od_pathinfo数据全部插入完毕，共耗时:" + (end - start) / 60000 + " min");

        } catch (Exception e) {
            e.printStackTrace();
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
            if (preStat != null) {
                preStat.close();
            }
            if (pConnection != null) {
                pConnection.close();
            }
        }


    }

    public void executeDataSynch(String sql) {
        try {
            doWork(sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        ODPathinfoJdbc firstIntoVeh = new ODPathinfoJdbc();
        String date = args[0].toString();
        String sql = "select * from  dws.od_pathinfo where  year = '" + date.substring(0, 4) + "' and month = '" + date.substring(5, 7) + "' and day = '" + date.substring(8, 10) + "'";
        //String sql ="select * from dws.od_depart  where year = '2019' and month = '05' and day = '27'";
        System.out.println("********sql: " + sql);
        firstIntoVeh.executeDataSynch(sql);
    }
}
