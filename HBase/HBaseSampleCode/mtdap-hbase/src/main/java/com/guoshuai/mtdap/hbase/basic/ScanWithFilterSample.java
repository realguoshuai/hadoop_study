package com.guoshuai.mtdap.hbase.basic;

import com.guoshuai.mtdap.hbase.SampleConstants;
import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueExcludeFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created on 2016/7/11.
 * This is the sample for scanning data with filter from table.
 */
public class ScanWithFilterSample extends TableOperationImpl {

  Logger LOG = Logger.getLogger(ScanWithFilterSample.class);

  public ScanWithFilterSample() {
    super();
  }

  public void process() {
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);
    byte[][] bytes = {Bytes.toBytes(SampleConstants.CF_BASIC),
            Bytes.toBytes(SampleConstants.CF_BASIC_NAME),
            Bytes.toBytes(SampleConstants.CF_BASIC_AGE),
            Bytes.toBytes(SampleConstants.CF_BASIC_ADDRESS),
            Bytes.toBytes(SampleConstants.CF_BASIC_GENDER)};
    Table table = null;
    try {
      table = connection.getTable(tableName);
      Scan scan = new Scan();
      scan.addColumn(bytes[0], bytes[1]);

      SingleColumnValueFilter filter = new SingleColumnValueFilter(
              bytes[0], bytes[1],
              CompareFilter.CompareOp.EQUAL, Bytes.toBytes("Xu Bing"));
      scan.setFilter(filter);

      ResultScanner scanner = table.getScanner(scan);

      /*遍历filter+scan的两种for循环实现*/
     // for (Result r = scanner.next(); r != null; r = scanner.next()) {
      for(Result r:scanner){
        /* keyvalues={K0001001/basic:name/1545121883432/Put/vlen=7/seqid=0} */
        System.out.println(""+r);
        for (Cell cell : r.rawCells()) {
          System.out.println(Bytes.toString(CellUtil.cloneRow(cell)) +" "+
                          Bytes.toString(CellUtil.cloneFamily(cell)) +" "+
                          Bytes.toString(CellUtil.cloneQualifier(cell)) +" "+
                          Bytes.toString(CellUtil.cloneValue(cell)));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (table != null) {
        try {
          table.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
  public void process1() {
    // Specify the table name.
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);

    byte[][] qualifier = {Bytes.toBytes(SampleConstants.CF_BASIC)
            , Bytes.toBytes(SampleConstants.CF_BASIC_NAME)
            , Bytes.toBytes(SampleConstants.CF_BASIC_GENDER)
            , Bytes.toBytes(SampleConstants.CF_BASIC_ADDRESS)
            , Bytes.toBytes(SampleConstants.CF_BASIC_AGE)};
    Table table = null;
    try {
      // Get the table.
      table = connection.getTable(tableName);

      // Instantiate a Scan object.
      Scan scan = new Scan();
      /*添加查询的列簇和查询列*/
      scan.addColumn(qualifier[0],qualifier[1]);
      scan.addColumn(qualifier[0],qualifier[2]);
      scan.addColumn(qualifier[0],qualifier[3]);
      scan.addColumn(qualifier[0],qualifier[4]);

      //Todo SingleColumnValueFilter 和 SingleColumnValueExcludeFilter 区别 ?

      //设置过滤criteria(标准)
      SingleColumnValueFilter filter = new SingleColumnValueFilter(
              Bytes.toBytes(SampleConstants.CF_BASIC), Bytes.toBytes(SampleConstants.CF_BASIC_NAME),
              CompareFilter.CompareOp.EQUAL, Bytes.toBytes("Xu Bing"));
      //使用SingleColumnValueExcludeFilter
      /*SingleColumnValueExcludeFilter excludeFilter = new SingleColumnValueExcludeFilter(
              qualifier[0],qualifier[1],CompareFilter.CompareOp.NOT_EQUAL,Bytes.toBytes("Xu Bing")
      );*/
      //通过 CompareFilter.CompareOp.EQUAL 这个参数确定指定的列簇:列名 跟最后一个参数比较的对比
      SingleColumnValueExcludeFilter excludeFilter = new SingleColumnValueExcludeFilter(
              qualifier[0],qualifier[1], CompareFilter.CompareOp.EQUAL, Bytes.toBytes("Xu Bing")
      );
      /*SingleColumnValueFilter filter1 = new SingleColumnValueFilter(qualifier[0], qualifier[2],
              CompareFilter.CompareOp.EQUAL,Bytes.toBytes("26"));*/

      //scan.setFilter(filter);
      scan.setFilter(excludeFilter);

      //todo 如果你忘记了关闭ResultScanners，会导致RegionServer出现问题 放在try-catch里面
      // Instantiate a ResultScanner object.
      ResultScanner resultScanner = null;

      // Submit a scan request.
      resultScanner = table.getScanner(scan);

      // Print query results.
      for (Result r = resultScanner.next(); r != null; r = resultScanner.next()) {
        for (Cell cell : r.rawCells()) {
          LOG.info(Bytes.toString(CellUtil.cloneRow(cell))
                  + ":" + Bytes.toString(CellUtil.cloneFamily(cell))
                  + "," + Bytes.toString(CellUtil.cloneQualifier(cell))
                  + "," + Bytes.toString(CellUtil.cloneValue(cell)));
          //K0001001:basic,name,Xu Bing
        }
      }
    } catch (IOException e) {
      LOG.error("Failed to scan data with filter." + tableName.getNameAsString() + ".", e);
    } finally {
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
