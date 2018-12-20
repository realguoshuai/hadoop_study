package com.guoshuai.mtdap.hbase.index;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.index.ColumnQualifier;
import org.apache.hadoop.hbase.index.IndexSpecification;
import org.apache.hadoop.hbase.index.client.IndexAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created on 2016/7/11.
 * This is the sample for creating index for table.
 *
 * 创建索引 使用admin api
 */
public class CreateIndexSample extends TableOperationImpl {

  Logger LOG = Logger.getLogger(CreateIndexSample.class);
  
  public CreateIndexSample() {
    super();
  }

  public void process() {
   
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);

    byte[][] qualifier ={Bytes.toBytes(SampleConstants.CF_BASIC),
            Bytes.toBytes(SampleConstants.CF_BASIC_NAME),
            Bytes.toBytes(SampleConstants.CF_BASIC_AGE),
            Bytes.toBytes(SampleConstants.CF_BASIC_GENDER),
            Bytes.toBytes(SampleConstants.CF_BASIC_ADDRESS)
    };

    // Create index instance 创建一个索引 索引名跟原名称相同,且不能重名
    /*IndexSpecification indexSpec = new IndexSpecification(SampleConstants.INDEX);*/
    IndexSpecification indexSpec = new IndexSpecification(SampleConstants.CF_BASIC_ADDRESS);
    /*indexSpec.addIndexColumn(new HColumnDescriptor(SampleConstants.CF_BASIC),
            SampleConstants.CF_BASIC_NAME, ColumnQualifier.ValueType.String);

    IndexSpecification specification = new IndexSpecification(SampleConstants.INDEX);
    specification.addIndexColumn(new HColumnDescriptor(SampleConstants.CF_BASIC),
            SampleConstants.CF_BASIC_NAME,ColumnQualifier.ValueType.String);*/
    //给姓名的列创建索引 命名为sample_index 类似于其他关系型数据库的索引
    indexSpec.addIndexColumn(new HColumnDescriptor(qualifier[0]),SampleConstants.CF_BASIC_ADDRESS,
            ColumnQualifier.ValueType.String);

    IndexAdmin iAdmin = null;

    try {
      //初始化 IndexAdmin  具体的执行者
      /*client包下的 IndexAdmin类 继承自HBaseAdmin*/
      iAdmin = new IndexAdmin(conf);

      //创建二级索引
      iAdmin.addIndex(tableName, indexSpec);
      LOG.info("Successfully add index for table " + tableName.getNameAsString() + ".");
    } catch (IOException e) {
      LOG.error("Successfully add index for table " + tableName.getNameAsString() + ".", e);
    }
  }
  
}
