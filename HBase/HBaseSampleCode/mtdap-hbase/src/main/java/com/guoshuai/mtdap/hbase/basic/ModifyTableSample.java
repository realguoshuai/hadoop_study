package com.guoshuai.mtdap.hbase.basic;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * 修改表
 */
public class ModifyTableSample extends TableOperationImpl {
  Logger LOG = Logger.getLogger(ModifyTableSample.class);
  
  public ModifyTableSample() {
    super();
  }

  public void process() {
    /*获取表名*/
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);
    byte[] familyName = Bytes.toBytes(SampleConstants.CF_JOB);

    try {
      HTableDescriptor htd = admin.getTableDescriptor(tableName);

      if(!htd.hasFamily(familyName)){

        HColumnDescriptor hcd = new HColumnDescriptor(familyName);
        htd.addFamily(hcd);

        //modify
       /* 禁用表
        * 一般用在修改表设置或者删除表时,必须先disable
        * */
        admin.disableTable(tableName);
       /*
        * 修改表
        * */
        admin.modifyTable(tableName, htd);
        /*开启*/
        admin.enableTable(tableName);

        LOG.info("Modify table " + tableName.getNameAsString() + " successfully.");
      }
    } catch (IOException e) {
      LOG.error("Failed to modify table " + tableName.getNameAsString() + ".", e);
    }
  }
}
