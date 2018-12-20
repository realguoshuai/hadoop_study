package com.guoshuai.mtdap.hbase.basic;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created on 2016/7/11.
 * This is the sample for deleting data from table.
 */
public class DeletaDataSample extends TableOperationImpl {

  Logger LOG = Logger.getLogger(DeletaDataSample.class);

  public  void process(){
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);

    byte[][] qualifier = {Bytes.toBytes(SampleConstants.CF_BASIC),
      Bytes.toBytes(SampleConstants.CF_BASIC_NAME),
      Bytes.toBytes(SampleConstants.CF_BASIC_AGE),
      Bytes.toBytes(SampleConstants.CF_BASIC_GENDER),
      Bytes.toBytes(SampleConstants.CF_BASIC_ADDRESS)};

    //rowkey  当key不存在时  程序正常运行
    byte[] row = Bytes.toBytes("C0001003121212eqw");
    Table table = null;
    try {
      //指定需要删除的row   get put 都需要指定具体的key
      Delete delete = new Delete(row);
      delete.addColumns(qualifier[0],qualifier[1]);
      delete.addColumns(qualifier[0],qualifier[2]);
      delete.addColumns(qualifier[0],qualifier[3]);
      delete.addColumns(qualifier[0],qualifier[4]);
      //get  table object
      table = connection.getTable(tableName);
      //commit
      table.delete(delete);
      LOG.info("delete successful "+tableName.getNameAsString());
    } catch (IOException e) {
      e.printStackTrace();
      LOG.info("delete faild "+tableName.getNameAsString());
    }finally{
      if(null!=table){
        try {
          table.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void process1() {
    /*指定rowkey 指定列簇 指定列值
     *table.delete(delte)
     * */
    // Specify the table name.
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);

    //指定byte数组
    byte[] [] qualifier ={
            Bytes.toBytes(SampleConstants.CF_BASIC),
            Bytes.toBytes(SampleConstants.CF_BASIC_NAME),
            Bytes.toBytes(SampleConstants.CF_BASIC_GENDER),
            Bytes.toBytes(SampleConstants.CF_BASIC_AGE),
            Bytes.toBytes(SampleConstants.CF_BASIC_ADDRESS)
    };
    byte[] rowKey = Bytes.toBytes("C0001003");

    Table table = null;
    try {
      table = connection.getTable(tableName);
      Delete delete = new Delete(rowKey);
      delete.addColumns(Bytes.toBytes(SampleConstants.CF_BASIC),
              Bytes.toBytes(SampleConstants.CF_BASIC_ADDRESS));
      // Submit a get request.
      table.delete(delete);
      LOG.info("Successfully delete data from table " + tableName.getNameAsString() + ".");
    } catch (IOException e) {
      LOG.error("Failed to delete data from table " + tableName.getNameAsString() + ".");
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
