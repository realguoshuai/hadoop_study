package com.huawei.bigdata.spark.examples;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.*;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.*;
import org.apache.spark.*;

import com.huawei.hadoop.security.LoginUtil;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.hive.HiveContext;

/**
 * calculate data from hive/hbase,then update to hbase
 */
public class SparkHivetoHbase {

  public static void main(String[] args) throws Exception {
    String userPrincipal = "enjoyor";
    String userKeytabPath = "/opt/sh/all_data/user.keytab";
    String krb5ConfPath = "/opt/sh/all_data/krb5.conf";
    Configuration hadoopConf = new Configuration();
    LoginUtil.login(userPrincipal, userKeytabPath, krb5ConfPath, hadoopConf);

    // Obtain the data in the table through the Spark interface.
    SparkConf conf = new SparkConf().setAppName("SparkHivetoHbase");
    JavaSparkContext jsc = new JavaSparkContext(conf);
    HiveContext sqlContext = new org.apache.spark.sql.hive.HiveContext(jsc);
    Dataset<Row> dataFrame = sqlContext.sql(" insert into dwd.tibet_veh partition\n" +
            "  (year = '2019', month = '05', day = '30')\n" +
            " select b.record_id ,\n" +
            "        b.point_id ,\n" +
            "        b.region_id,\n" +
            "        b.dept_id,\n" +
            "b.license_type ,\n" +
            "b.license_color ,\n" +
            "b.veh_type ,\n" +
            "b.lane_number,\n" +
            "b.speed ,\n" +
            "b.veh_length ,\n" +
            "b.record_type ,\n" +
            "b.license_number ,\n" +
            "b.license_region,\n" +
            "b.data_source ,\n" +
            "b.passing_time ,\n" +
            "b.speed_limit_max ,\n" +
            "b.speed_limit_min ,\n" +
            "b.veh_color ,\n" +
            "b.surveil_type ,\n" +
            "b.brand ,\n" +
            "b.receive_time ,\n" +
            "b.insert_time ,\n" +
            "b.pic_directory ,\n" +
            "b.veh_pic ,\n" +
            "b.character_pic ,\n" +
            "b.standby_pic ,\n" +
            "b.video_path ,\n" +
            "b.sr_submit_time ,\n" +
            "b.sr_receive_time ,\n" +
            "b.sr_license_number ,\n" +
            "b.sr_license_number_conf ,\n" +
            "b.sr_license_type ,\n" +
            "b.sr_license_type_conf ,\n" +
            "b.sr_license_color ,\n" +
            "b.sr_license_color_conf ,\n" +
            "b.sr_veh_type ,\n" +
            "b.sr_veh_type_conf ,\n" +
            "b.sr_veh_color ,\n" +
            "b.sr_veh_color_conf ,\n" +
            "b.sr_brand ,\n" +
            "b.sr_brand_conf ,\n" +
            "b.sr_subbrand ,\n" +
            "b.sr_subbrand_conf ,\n" +
            "b.sr_right_belt ,\n" +
            "b.sr_right_belt_conf ,\n" +
            "b.sr_left_belt ,\n" +
            "b.sr_left_belt_conf ,\n" +
            "b.sr_right_shield ,\n" +
            "b.sr_right_shield_conf ,\n" +
            "b.sr_left_shield ,\n" +
            "b.sr_left_shield_conf ,\n" +
            "b.sr_right_callup ,\n" +
            "b.sr_right_callup_conf ,\n" +
            "b.sr_left_callup ,\n" +
            "b.sr_left_callup_conf ,\n" +
            "b.sr_pendant ,\n" +
            "b.sr_pendant_conf ,\n" +
            "b.sr_yellow_label ,\n" +
            "b.sr_yellow_label_conf ,\n" +
            "b.sr_chemicals ,\n" +
            "b.sr_chemicals_conf ,\n" +
            "b.sr_damage ,\n" +
            "b.sr_damage_conf ,\n" +
            "b.sr_face_pic_path ,\n" +
            "b.sr_license_pic_path ,\n" +
            "b.vio_code,\n" +
            "                case\n" +
            "           when e.license_num is null then\n" +
            "            '0'\n" +
            "           else\n" +
            "            '1'\n" +
            "         end first_into_city\n" +
            "        from(\n" +
            " SELECT record_id ,\n" +
            "        point_id ,\n" +
            "        region_id,\n" +
            "        dept_id,\n" +
            "        license_type ,\n" +
            "        license_color ,\n" +
            "        veh_type ,\n" +
            "        lane_number,\n" +
            "        speed ,\n" +
            "        veh_length ,\n" +
            "        record_type ,\n" +
            "        license_number ,\n" +
            "        license_region,\n" +
            "        data_source ,\n" +
            "        passing_time ,\n" +
            "        speed_limit_max ,\n" +
            "        speed_limit_min ,\n" +
            "        veh_color ,\n" +
            "        surveil_type ,\n" +
            "        brand ,\n" +
            "        receive_time ,\n" +
            "        insert_time ,\n" +
            "        pic_directory ,\n" +
            "        veh_pic ,\n" +
            "        character_pic ,\n" +
            "        standby_pic ,\n" +
            "        video_path ,\n" +
            "        sr_submit_time ,\n" +
            "        sr_receive_time ,\n" +
            "        sr_license_number ,\n" +
            "        sr_license_number_conf ,\n" +
            "        sr_license_type ,\n" +
            "        sr_license_type_conf ,\n" +
            "        sr_license_color ,\n" +
            "        sr_license_color_conf ,\n" +
            "        sr_veh_type ,\n" +
            "        sr_veh_type_conf ,\n" +
            "        sr_veh_color ,\n" +
            "        sr_veh_color_conf ,\n" +
            "        sr_brand ,\n" +
            "        sr_brand_conf ,\n" +
            "        sr_subbrand ,\n" +
            "        sr_subbrand_conf ,\n" +
            "        sr_right_belt ,\n" +
            "        sr_right_belt_conf ,\n" +
            "        sr_left_belt ,\n" +
            "        sr_left_belt_conf ,\n" +
            "        sr_right_shield ,\n" +
            "        sr_right_shield_conf ,\n" +
            "        sr_left_shield ,\n" +
            "        sr_left_shield_conf ,\n" +
            "        sr_right_callup ,\n" +
            "        sr_right_callup_conf ,\n" +
            "        sr_left_callup ,\n" +
            "        sr_left_callup_conf ,\n" +
            "        sr_pendant ,\n" +
            "        sr_pendant_conf ,\n" +
            "        sr_yellow_label ,\n" +
            "        sr_yellow_label_conf ,\n" +
            "        sr_chemicals ,\n" +
            "        sr_chemicals_conf ,\n" +
            "        sr_damage ,\n" +
            "        sr_damage_conf ,\n" +
            "        sr_face_pic_path ,\n" +
            "        sr_license_pic_path ,\n" +
            "        vio_code\n" +
            "FROM \n" +
            "  (SELECT \n" +
            "        record_id ,\n" +
            "        point_id,\n" +
            "        region_id,\n" +
            "        dept_id ,\n" +
            "        license_type ,\n" +
            "        license_color ,\n" +
            "        veh_type ,\n" +
            "        lane_number ,\n" +
            "        speed ,\n" +
            "        veh_length ,\n" +
            "        record_type ,\n" +
            "        license_number ,\n" +
            "        data_source ,\n" +
            "        passing_time ,\n" +
            "        speed_limit_max ,\n" +
            "        speed_limit_min ,\n" +
            "        veh_color ,\n" +
            "        surveil_type ,\n" +
            "        brand ,\n" +
            "        receive_time ,\n" +
            "        insert_time ,\n" +
            "        pic_directory ,\n" +
            "        veh_pic ,\n" +
            "        character_pic ,\n" +
            "        standby_pic ,\n" +
            "        video_path ,\n" +
            "        sr_submit_time ,\n" +
            "        sr_receive_time ,\n" +
            "        sr_license_number ,\n" +
            "        sr_license_number_conf ,\n" +
            "        sr_license_type ,\n" +
            "        sr_license_type_conf ,\n" +
            "        sr_license_color ,\n" +
            "        sr_license_color_conf ,\n" +
            "        sr_veh_type ,\n" +
            "        sr_veh_type_conf ,\n" +
            "        sr_veh_color ,\n" +
            "        sr_veh_color_conf ,\n" +
            "        sr_brand ,\n" +
            "        sr_brand_conf ,\n" +
            "        sr_subbrand ,\n" +
            "        sr_subbrand_conf ,\n" +
            "        sr_right_belt ,\n" +
            "        sr_right_belt_conf ,\n" +
            "        sr_left_belt ,\n" +
            "        sr_left_belt_conf ,\n" +
            "        sr_right_shield ,\n" +
            "        sr_right_shield_conf ,\n" +
            "        sr_left_shield ,\n" +
            "        sr_left_shield_conf ,\n" +
            "        sr_right_callup ,\n" +
            "        sr_right_callup_conf ,\n" +
            "        sr_left_callup ,\n" +
            "        sr_left_callup_conf ,\n" +
            "        sr_pendant ,\n" +
            "        sr_pendant_conf ,\n" +
            "        sr_yellow_label ,\n" +
            "        sr_yellow_label_conf ,\n" +
            "        sr_chemicals ,\n" +
            "        sr_chemicals_conf ,\n" +
            "        sr_damage ,\n" +
            "        sr_damage_conf ,\n" +
            "        sr_face_pic_path ,\n" +
            "        sr_license_pic_path ,\n" +
            "        vio_code,\n" +
            "         substr(point_id,\n" +
            "         1,\n" +
            "         length(point_id)-2) point_id_nodrict,\n" +
            "         lead(substr(point_id,\n" +
            "         1,\n" +
            "         length(point_id)-2)) over(partition by license_number,\n" +
            "         license_type\n" +
            "  ORDER BY passing_time) AS next_point_id, lag(substr(point_id, 1, length(point_id)-2)) over(partition by license_number, license_type\n" +
            "  ORDER BY passing_time) AS last_point_id, substr(point_id,-2) direction, substr(license_number, 0, 2) license_region, to_unix_timestamp(passing_time) pass_time_timestamp, lead(to_unix_timestamp(passing_time)) over(partition by license_number, license_type\n" +
            "  ORDER BY passing_time) next_pass_time, lag(to_unix_timestamp(passing_time)) over(partition by license_number, license_type\n" +
            "  ORDER BY passing_time) last_pass_time\n" +
            "  FROM ods.ext_kkdata\n" +
            "  WHERE concat_ws('-', year, month, day) = '2019-05-30'\n" +
            "          AND default.isvalidlicense(license_number) = true\n" +
            "          AND point_id!=''\n" +
            "          AND (substr(license_number, 0, 1)='新'\n" +
            "          OR substr(license_number, 0, 1)='藏') ) a\n" +
            "WHERE (nvl(next_pass_time, 1893427200) - pass_time_timestamp >= 120)\n" +
            "        OR (pass_time_timestamp - nvl(last_pass_time, 0) >= 120)\n" +
            "        OR point_id_nodrict <> nvl(next_point_id, 0)\n" +
            "        OR point_id_nodrict <> nvl(last_point_id, 0))b\n" +
            "        \n" +
            "                  left join dws.first_into_city_new e\n" +
            "      on b.license_number = e.license_num ");
//dataFrame.collect();
    // Traverse every Partition in the hive table and update the hbase table
    // If less data, you can use rdd.foreach()
   /*dataFrame.toJavaRDD().foreachPartition(
      new VoidFunction<Iterator<Row>>() {
        public void call(Iterator<Row> iterator) throws Exception {
       //   hBaseWriter(iterator);
         // System.out.println();
          while (iterator.hasNext()) {
            Row item = iterator.next();
            System.out.println(item);
          }
        }
      }
    );*/

    jsc.stop();
  }

  /**
   * write to hbase table in exetutor
   *
   * @param iterator partition data from hive table
   */
  private static void hBaseWriter(Iterator<Row> iterator) throws IOException {
    // read hbase
    String tableName = "table2";
    String columnFamily = "cf";
    Configuration conf = HBaseConfiguration.create();
    Connection connection = ConnectionFactory.createConnection(conf);
    Table table = connection.getTable(TableName.valueOf(tableName));

    try {
      connection = ConnectionFactory.createConnection(conf);
      table = connection.getTable(TableName.valueOf(tableName));

      List<Row> table1List = new ArrayList<Row>();
      List<Get> rowList = new ArrayList<Get>();
      while (iterator.hasNext()) {
        Row item = iterator.next();
        // set the put condition
        Get get = new Get(item.getString(0).getBytes());
        table1List.add(item);
        rowList.add(get);
      }

      // get data from hbase table
      Result[] resultDataBuffer = table.get(rowList);

      // set data for hbase
      List<Put> putList = new ArrayList<Put>();
      for (int i = 0; i < resultDataBuffer.length; i++) {
        // hbase row
        Result resultData = resultDataBuffer[i];
        if (!resultData.isEmpty()) {
          // get hiveValue
          int hiveValue = table1List.get(i).getInt(1);

          // get hbaseValue by column Family and colomn qualifier
          String hbaseValue = Bytes.toString(resultData.getValue(columnFamily.getBytes(), "cid".getBytes()));
          Put put = new Put(table1List.get(i).getString(0).getBytes());

          // calculate result value
          int resultValue = hiveValue + Integer.valueOf(hbaseValue);

          // set data to put
          put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("cid"), Bytes.toBytes(String.valueOf(resultValue)));
          putList.add(put);
        }
      }

      if (putList.size() > 0) {
        table.put(putList);
      }
    }catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (table != null) {
        try {
          table.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (connection != null) {
        try {
          // Close the HBase connection.
          connection.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
