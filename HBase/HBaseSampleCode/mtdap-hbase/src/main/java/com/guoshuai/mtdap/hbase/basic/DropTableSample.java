package com.guoshuai.mtdap.hbase.basic;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created on 2016/7/11.
 * This is the sample for dropping table.
 */
public class DropTableSample extends TableOperationImpl {
  Logger LOG = Logger.getLogger(DropTableSample.class);
  
  public DropTableSample() {
    super();
  }
  
  public void process() {
    // Specify the table name.
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);
    
    Admin admin = null;
    try {
      admin = connection.getAdmin();
      if (admin.tableExists(tableName)) {
        // Disable the table before deleting it.
        admin.disableTable(tableName);
        
        // Delete table.
        admin.deleteTable(tableName);
      }
      LOG.info("Drop table successfully.");
    } catch (IOException e) {
      LOG.error("Failed to drop table " + tableName.getNameAsString() + ".", e);
    } 
  }
  
}
