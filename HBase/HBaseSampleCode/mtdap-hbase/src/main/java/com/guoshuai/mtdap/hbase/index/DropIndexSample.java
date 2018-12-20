package com.guoshuai.mtdap.hbase.index;

import com.guoshuai.mtdap.hbase.SampleConstants;
import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.index.client.IndexAdmin;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/7/11.
 * This is the sample for dropping index from table.
 */
public class DropIndexSample extends TableOperationImpl {

  Logger LOG = Logger.getLogger(DropIndexSample.class);
  
  public DropIndexSample() {
    super();
  }
  
  public void process() {
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);
    IndexAdmin iAdmin = null;
    try {

      iAdmin = new IndexAdmin(conf);
      //批量删除 单独删除
      //iAdmin.dropIndex(tableName, SampleConstants.INDEX);
      List<String> list = new ArrayList<>();
      list.add(SampleConstants.INDEX);
      list.add(SampleConstants.CF_BASIC_NAME);
      iAdmin.dropIndexes(tableName,list);
      LOG.info("Successfully droped index from table " + tableName.getNameAsString() + ".");
    } catch (IOException e) {
      LOG.error("Failed to drop index from table " + tableName.getNameAsString() + ".", e);
    } finally {
      if (iAdmin != null) {
        try {
          // Close Secondary Index
          iAdmin.close();
        } catch (IOException e) {
          iAdmin = null;
        }
      }
    }
  }
  
}
