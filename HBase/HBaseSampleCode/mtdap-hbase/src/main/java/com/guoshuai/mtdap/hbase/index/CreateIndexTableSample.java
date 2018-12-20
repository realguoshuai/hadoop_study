package com.guoshuai.mtdap.hbase.index;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.index.ColumnQualifier;
import org.apache.hadoop.hbase.index.IndexSpecification;
import org.apache.hadoop.hbase.index.client.IndexAdmin;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created on 2016/7/11.
 *  This is a sample for creating Index for table but your index can't be null.
 */
public class CreateIndexTableSample extends TableOperationImpl {
  Logger LOG = Logger.getLogger(CreateIndexTableSample.class);
  
  public CreateIndexTableSample() {
    super();
  }

  public void process() {
   
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);
    String indexName = "single_index";

    // Create index instance
    IndexSpecification indexSpec = new IndexSpecification(indexName);
    indexSpec.addIndexColumn(new HColumnDescriptor(SampleConstants.CF_BASIC),
            SampleConstants.CF_BASIC_NAME, ColumnQualifier.ValueType.String);

    IndexAdmin iAdmin = null;
    try {
      // Instantiate IndexAdmin Object
      iAdmin = new IndexAdmin(conf);

      // Create Secondary Index
      iAdmin.addIndex(tableName, indexSpec);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (iAdmin != null) {
        try {
          iAdmin.close();
        } catch (IOException e) {
          iAdmin = null;
        }
      }
    }
  }
  
}
