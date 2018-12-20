package com.guoshuai.mtdap.hbase.index;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created on 2016/7/11.
 * This is the sample for scanning data from table which been made index.
 * 扫描索引
 */
public class ScanByIndexSample extends TableOperationImpl {

  Logger LOG = Logger.getLogger(ScanByIndexSample.class);
  
  public ScanByIndexSample() {
    super();
  }

  public void process() {

    TableName tableName = TableName.valueOf(SampleConstants.TABLE);

    Table table = null;
    ResultScanner scanner = null;
    try {
      table = connection.getTable(tableName);

      // Create a filter for indexed column.
      Filter filter = new SingleColumnValueFilter(Bytes.toBytes(SampleConstants.CF_BASIC),
          Bytes.toBytes(SampleConstants.CF_BASIC_NAME),
          CompareOp.EQUAL, Bytes.toBytes("Li Gang"));
      Scan scan = new Scan();
      scan.setFilter(filter);
      scanner = table.getScanner(scan);
      LOG.info("Scan indexed data.");

      for (Result result : scanner) {
        for (Cell cell : result.rawCells()) {
          LOG.info(Bytes.toString(CellUtil.cloneRow(cell)) + ":"
              + Bytes.toString(CellUtil.cloneFamily(cell)) + ","
              + Bytes.toString(CellUtil.cloneQualifier(cell)) + ","
              + Bytes.toString(CellUtil.cloneValue(cell)));
        }
      }
      LOG.info("Scan data by index successfully from table "+ tableName.getNameAsString() + ".");
    } catch (IOException e) {
      LOG.error("Failed to scan data by index from table "+ tableName.getNameAsString() + ".", e);
    } finally {
      if (scanner != null) {
        // Close the scanner object.
        scanner.close();
      }
      try {
        if (table != null) {
          table.close();
        }
      } catch (IOException e) {
        table = null;
      }
    }
  }
  
}
