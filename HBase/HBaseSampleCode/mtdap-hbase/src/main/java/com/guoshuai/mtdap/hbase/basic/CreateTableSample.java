package com.guoshuai.mtdap.hbase.basic;

import com.guoshuai.mtdap.hbase.impl.TableOperationImpl;
import com.guoshuai.mtdap.hbase.SampleConstants;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * 使用Admin  创建 HBase 表
 */
public class CreateTableSample extends TableOperationImpl {

    Logger LOG = Logger.getLogger(CreateTableSample.class);

    public CreateTableSample() {
        super();
    }

    public void process() {
        TableName tableName = TableName.valueOf(SampleConstants.TABLE);
        /* HTableDescriptor:负责HBase表结构的修改*/
        HTableDescriptor htd = new HTableDescriptor(tableName);
        /* HColumnDescriptor：描述column family的信息*/
        HColumnDescriptor hcd = new HColumnDescriptor(SampleConstants.CF_BASIC);
        //将列簇名字添加
        htd.addFamily(hcd);

        /*预分区:splitKey byte[]
         *创建表是如果不指定默认分区,默认一个region,不仅会产生热点访问问题
         *同时当数据量变大时,region会自动split分割成两个region
         *split操作会占用大量的集群I/O
         * */
        byte[][] splitKeys = {Bytes.toBytes("A"), Bytes.toBytes("B"), Bytes.toBytes("C"), Bytes.toBytes("D"),
                Bytes.toBytes("H"), Bytes.toBytes("J"), Bytes.toBytes("K")};

        try {
            //默认不存在 置为false
            boolean isNameSpaceExist = false;
            //list所有namespace
            NamespaceDescriptor[] nsd = admin.listNamespaceDescriptors();
            //遍历 ,比较
            for (NamespaceDescriptor namespaceDescriptor : nsd) {
                System.out.println("namespaceDescriptor  " + namespaceDescriptor);
                /*admin.listNamespaceDescriptors() 查看HBase中所有的命名空间  return NamespaceDescriptor[]
                 * namespaceDescriptor  {NAME => 'default'}
                 * namespaceDescriptor  {NAME => 'hbase'}
                 * namespaceDescriptor  {NAME => 'sampleNameSpace'}
                 * namespaceDescriptor  {NAME => 'testNameSpace'}
                 * */
                if (SampleConstants.NAMESPACE.equals(namespaceDescriptor.getName())) {
                    //如果有 置为真
                    isNameSpaceExist = true;
                    break;
                }
            }


            if (!isNameSpaceExist) {
                LOG.info("name space " + SampleConstants.NAMESPACE + " not exist, will create first.");
                LOG.info(SampleConstants.NAMESPACE + "不存在");
                //创建命名空间
                admin.createNamespace(NamespaceDescriptor.create(SampleConstants.NAMESPACE).build());
            }

            if (!admin.tableExists(tableName)) {
                //用重载的第二个api  表,预分区
                /*admin.createTable(HTableDescriptor desc, byte[][] splitKeys)*/
                admin.createTable(htd, splitKeys);
                LOG.info("Create table " + tableName.getNameAsString() + " successful!");
            } else {
                LOG.warn("Table already exists!");
            }
        } catch (IOException e) {
            LOG.error("Failed to create table " + tableName.getNameAsString() + ".", e);
        }

    }

}
