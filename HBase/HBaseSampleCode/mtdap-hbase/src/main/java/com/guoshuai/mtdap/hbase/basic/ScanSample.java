package com.guoshuai.mtdap.hbase.basic;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created on 2016/7/11.
 * This is the sample for scanning data from table.
 * 通过Scan 从表中读数据 (用的最多)
 */
public class ScanSample extends TableOperationImpl {

  Logger LOG = Logger.getLogger(ScanSample.class);
  
  public ScanSample() {
    super();
  }
  
  public void process() {
    // Specify the table name.
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);

    byte[][] qualifier ={Bytes.toBytes(SampleConstants.CF_BASIC_NAME)
            , Bytes.toBytes(SampleConstants.CF_BASIC_ADDRESS)
            , Bytes.toBytes(SampleConstants.CF_BASIC_AGE)
            , Bytes.toBytes(SampleConstants.CF_BASIC_GENDER)};
    Table table = null;
    // Instantiate a ResultScanner object.
    ResultScanner rScanner = null;
    try {
      // Create the SampleConfiguration instance.
      table = connection.getTable(tableName);
      
      // Instantiate a Get object.
      Scan scan = new Scan();
      /* scan.addColumn(byte [] family, byte [] qualifier) */
      scan.addColumn(Bytes.toBytes(SampleConstants.CF_BASIC), qualifier[0]);
      //todo 可以添加多个  添加多个能获取到对应的值
      scan.addColumn(Bytes.toBytes(SampleConstants.CF_BASIC),qualifier[1]);
      /* ... 应为addColumn的重载只有一个 所以需要查那几个列 目前只能这样指定
      * Todo 怎样将Phoenix 的sql转换成HBase的api实现
      * */
      // Set the cache size.
      scan.setCaching(1000);
      
      // Submit a scan request.
      rScanner = table.getScanner(scan);
      
      // Print query results.
      for (Result r = rScanner.next(); r != null; r = rScanner.next()) {
        for (Cell cell : r.rawCells()) {
          LOG.info(Bytes.toString(CellUtil.cloneRow(cell)) + ":"
              + Bytes.toString(CellUtil.cloneFamily(cell)) + ","
              + Bytes.toString(CellUtil.cloneQualifier(cell)) + ","
              + Bytes.toString(CellUtil.cloneValue(cell)));
        }
      }
     /*A0001001:basic,name,Zhang San
       A0001002:basic,name,Li Wanting
       B0001001:basic,name,Wang Ming
       B0001002:basic,name,Li Gang
       C0001001:basic,name,Zhao Enru
       C0001003:basic,name,Chen Long
       H0001001:basic,name,Zhou Wei
       J0001001:basic,name,Yang Yiwen
       K0001001:basic,name,Xu Bing   */
      LOG.info("Scan data successfully.");
    } catch (IOException e) {
      LOG.error("Failed to scan data from " + tableName.getNameAsString() + ".", e);
    } finally {
      if (rScanner != null) {
        // Close the scanner object.
        rScanner.close();
      }
      if (table != null) {
        try {
          // Close the HTable object.
          table.close();
        } catch (IOException e) {
          table = null;
        }
      }
    }
  }
  
}
