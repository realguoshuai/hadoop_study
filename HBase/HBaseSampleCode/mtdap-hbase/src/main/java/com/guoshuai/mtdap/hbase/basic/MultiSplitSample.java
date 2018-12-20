package com.guoshuai.mtdap.hbase.basic;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.HRegionInfo;

import org.apache.hadoop.hbase.TableName;


import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Hbase split :HBase通过split将数据分配到多个region来保证负载均衡
 * 一个table 会被分配到一个或多个region上
 */
public class MultiSplitSample extends TableOperationImpl {

  Logger LOG = Logger.getLogger(MultiSplitSample.class);

  public MultiSplitSample() {
    super();
  }

  public void process() {
    // Specify the table name.
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);
    
    try {
      List<HRegionInfo> rs = admin.getTableRegions(tableName);
      System.out.println("admin.getTableRegions(tableName):  "+rs);
      for (HRegionInfo hri : rs) {
        if(hri.containsRow(Bytes.toBytes("E0001001"))) {
          /*byte[][] splitKeys = {Bytes.toBytes("E"), Bytes.toBytes("F")};*/
          byte[][] splitKeys ={Bytes.toBytes("A1"), Bytes.toBytes("B1")};
          //TODO 分割操作只对空Region起作用
          ((HBaseAdmin) admin).multiSplitSync(hri.getRegionName(), splitKeys);
          LOG.info("Mmulti split table " + tableName.getNameAsString() + " successfully.");
        }
      }
    } catch(Exception e) {
      LOG.error("Failed to multiSplit table " + tableName.getNameAsString() + ".", e);
    }
  }

}
