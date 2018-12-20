package com.guoshuai.mtdap.hbase.basic;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 往表里添加数据
 */
public class PutDataSample extends TableOperationImpl {

  Logger LOG = Logger.getLogger(PutDataSample.class);
  
  public PutDataSample() {
    super();
  }

  public void process() {
    /*指定表名 从配置文件中拿到表名转成TableName类型*/
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);
    /*指定列簇*/
    byte[] familyName = Bytes.toBytes(SampleConstants.CF_BASIC);
    /*指定列名 从配置表中获取到列名的属性 放到数组中管理*/
    byte[][] basicQualifiers = {Bytes.toBytes(SampleConstants.CF_BASIC_NAME),
            Bytes.toBytes(SampleConstants.CF_BASIC_GENDER),
            Bytes.toBytes(SampleConstants.CF_BASIC_AGE),
            Bytes.toBytes(SampleConstants.CF_BASIC_ADDRESS)};

    Table table = null;
    try {
      //获取表对象
      table = connection.getTable(tableName);
      List<Put> puts = new ArrayList<Put>();
      // 添加数据 Put(Bytes rowkey)
      Put put = new Put(Bytes.toBytes("A0001001"));
      put.addColumn(familyName, basicQualifiers[0], Bytes.toBytes("Zhang San"));
      put.addColumn(familyName, basicQualifiers[1], Bytes.toBytes("Male"));
      put.addColumn(familyName, basicQualifiers[2], Bytes.toBytes(new Long(19).toString()));
      put.addColumn(familyName, basicQualifiers[3], Bytes.toBytes("Shenzhen, Guangdong"));
      puts.add(put);
      //todo long及int,double类型在转换时需要先转成string
      put = new Put(Bytes.toBytes("A0001002"));
      put.addColumn(familyName, basicQualifiers[0], Bytes.toBytes("Li Wanting"));
      put.addColumn(familyName, basicQualifiers[1], Bytes.toBytes("Female"));
      put.addColumn(familyName, basicQualifiers[2], Bytes.toBytes(new Long(23).toString()));
      put.addColumn(familyName, basicQualifiers[3], Bytes.toBytes("Shijiazhuang, Hebei"));
      puts.add(put);

      put = new Put(Bytes.toBytes("B0001001"));
      put.addColumn(familyName, basicQualifiers[0], Bytes.toBytes("Wang Ming"));
      put.addColumn(familyName, basicQualifiers[1], Bytes.toBytes("Male"));
      put.addColumn(familyName, basicQualifiers[2], Bytes.toBytes(new Long(26).toString()));
      put.addColumn(familyName, basicQualifiers[3], Bytes.toBytes("Ningbo, Zhejiang"));
      puts.add(put);

      put = new Put(Bytes.toBytes("B0001002"));
      put.addColumn(familyName, basicQualifiers[0], Bytes.toBytes("Li Gang"));
      put.addColumn(familyName, basicQualifiers[1], Bytes.toBytes("Male"));
      put.addColumn(familyName, basicQualifiers[2], Bytes.toBytes(new Long(18).toString()));
      put.addColumn(familyName, basicQualifiers[3], Bytes.toBytes("Xiangyang, Hubei"));
      puts.add(put);

      put = new Put(Bytes.toBytes("C0001001"));
      put.addColumn(familyName, basicQualifiers[0], Bytes.toBytes("Zhao Enru"));
      put.addColumn(familyName, basicQualifiers[1], Bytes.toBytes("Female"));
      put.addColumn(familyName, basicQualifiers[2], Bytes.toBytes(new Long(21).toString()));
      put.addColumn(familyName, basicQualifiers[3], Bytes.toBytes("Shangrao, Jiangxi"));
      puts.add(put);

      put = new Put(Bytes.toBytes("C0001003"));
      put.addColumn(familyName, basicQualifiers[0], Bytes.toBytes("Chen Long"));
      put.addColumn(familyName, basicQualifiers[1], Bytes.toBytes("Male"));
      put.addColumn(familyName, basicQualifiers[2], Bytes.toBytes(new Long(32).toString()));
      put.addColumn(familyName, basicQualifiers[3], Bytes.toBytes("Zhuzhou, Hunan"));
      puts.add(put);

      put = new Put(Bytes.toBytes("H0001001"));
      put.addColumn(familyName, basicQualifiers[0], Bytes.toBytes("Zhou Wei"));
      put.addColumn(familyName, basicQualifiers[1], Bytes.toBytes("Female"));
      put.addColumn(familyName, basicQualifiers[2], Bytes.toBytes(new Long(29).toString()));
      put.addColumn(familyName, basicQualifiers[3], Bytes.toBytes("Nanyang, Henan"));
      puts.add(put);

      put = new Put(Bytes.toBytes("J0001001"));
      put.addColumn(familyName, basicQualifiers[0], Bytes.toBytes("Yang Yiwen"));
      put.addColumn(familyName, basicQualifiers[1], Bytes.toBytes("Female"));
      put.addColumn(familyName, basicQualifiers[2], Bytes.toBytes(new Long(30).toString()));
      put.addColumn(familyName, basicQualifiers[3], Bytes.toBytes("Kaixian, Chongqing"));
      puts.add(put);

      put = new Put(Bytes.toBytes("K0001001"));
      put.addColumn(familyName, basicQualifiers[0], Bytes.toBytes("Xu Bing"));
      put.addColumn(familyName, basicQualifiers[1], Bytes.toBytes("Male"));
      put.addColumn(familyName, basicQualifiers[2], Bytes.toBytes(new Long(26).toString()));
      put.addColumn(familyName, basicQualifiers[3], Bytes.toBytes("Weinan, Shaanxi"));
      puts.add(put);


      // Submit a put request.
      table.put(puts);
      
      LOG.info("Successfully put 9 items data into " + tableName.getNameAsString() + ".");
    } catch (IOException e) {
      LOG.error("Failed to put data into table " + tableName.getNameAsString() + ".", e);
    } finally {
      if (table != null) {
        try {
          table.close();
        } catch (IOException e) {
          table = null;
        }
      }
    }
  }

}
