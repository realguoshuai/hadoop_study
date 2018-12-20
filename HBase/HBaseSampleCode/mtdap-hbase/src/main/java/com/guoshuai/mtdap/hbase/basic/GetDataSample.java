package com.guoshuai.mtdap.hbase.basic;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created on 2016/7/11.
 * This is the sample for getting data from table.
 */
public class GetDataSample extends TableOperationImpl {

  Logger LOG = Logger.getLogger(GetDataSample.class);
  
  public GetDataSample() {
    super();
  }

  public void process() {
    //指定表名
    TableName tableName = TableName.valueOf(SampleConstants.TABLE);
    //指定列簇
    byte[] familyName = Bytes.toBytes(SampleConstants.CF_BASIC);
    //指定 column name.
    byte[][] qualifier = {Bytes.toBytes(SampleConstants.CF_BASIC_NAME),
            Bytes.toBytes(SampleConstants.CF_BASIC_ADDRESS)};
    //指定 RowKey.
    byte[] rowKey = Bytes.toBytes("C0001003");
    Table table = null;
    try {
      //获取表对象
      table = connection.getTable(tableName);

      //实例化GET对象  Get 只能获取一行
      Get get = new Get(rowKey);
      /*获取单行Get 使用addColumn 添加所有的目标列
       *获取所有列对象 addFamily
       *获取目标列的指定数据戳版本 setTimestamp
       *如果希望限制每列的返回版本号 setMaxVersions
       *添加过滤器
       */
      //设置 column family name and column name.
      get.addColumn(familyName, qualifier[0]);
      get.addColumn(familyName, qualifier[1]);

      //通过Table.get执行 查询
      Result result = table.get(get);

      //输出查询到的results
      /* rawCells 获取*/
      for (Cell cell : result.rawCells()) {
        LOG.info("result: "+ Bytes.toString(CellUtil.cloneRow(cell))
                + ":" + Bytes.toString(CellUtil.cloneFamily(cell))
                + "," + Bytes.toString(CellUtil.cloneQualifier(cell))
                + "," + Bytes.toString(CellUtil.cloneValue(cell)));
        /*C0001003  Bytes.toString(CellUtil.cloneRow(cell))
        :basic      Bytes.toString(CellUtil.cloneFamily(cell))
        ,name,      Bytes.toString(CellUtil.cloneQualifier(cell))
        Chen Long   Bytes.toString(CellUtil.cloneValue(cell))*/
        System.out.println("cell is :"+cell
                +" cell-key: "+ CellUtil.getCellKeyAsString(cell)//上下一样的返回结果
                +" CF: "+ CellUtil.cloneFamily(cell)    //CF: [B@5a37d3ed
                +" Rowkey: "+ CellUtil.cloneRow(cell)   //Rowkey: [B@26df6e3a
                +" Qualifier column: " + CellUtil.cloneQualifier(cell)   //Qualifier column: [B@4a3631f8
                +" value: " + CellUtil.cloneValue(cell)    //value: [B@6b58b9e9
        );
        System.out.println("RowKey: "+ Bytes.toString(CellUtil.cloneRow(cell))
                +" CF: "+ Bytes.toString(CellUtil.cloneFamily(cell))
                +" Qualifier: "+ Bytes.toString(CellUtil.cloneQualifier(cell))
                +" value: "+ Bytes.toString(CellUtil.cloneValue(cell))
        );


        //cell rowkey,列簇,时间戳
        /*C0001003/basic:name/1544749761634/Put/vlen=9/seqid=0
        * Cell格式的值: rowkey/列簇/时间戳*/
      }
    } catch (IOException e) {
      LOG.error("Failed to get data from " + tableName.getNameAsString() + ".", e);
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
