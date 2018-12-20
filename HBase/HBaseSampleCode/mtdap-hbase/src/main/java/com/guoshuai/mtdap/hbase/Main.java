package com.guoshuai.mtdap.hbase;

import com.guoshuai.mtdap.hbase.basic.GetDataSample;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

/**
 * Created on 2018/12/20.
 * The entrance for all code.
 */
public class Main {
    public static void main(String[] args) {
        /*PropertyConfigurator.configure  读取配置文件*/
        PropertyConfigurator.configure(System.getProperty("user.dir") +
                File.separator + "conf" + File.separator + "log4j.properties");
        //创建表
        /*TableOperation createImpl = new CreateTableSample();
        createImpl.operate();*/

        //修改表结构
        /*ModifyTableSample modifyTable = new ModifyTableSample();
        modifyTable.operate();*/

        // hbase表 手动 split
        /*MultiSplitSample multiSplit = new MultiSplitSample();
        multiSplit.operate();*/
        //添加单行数据  PUT
        /*PutDataSample putData = new PutDataSample();
        putData.operate();*/
        //获取单个rowkey下的执行column  GET
        GetDataSample getDataSample = new GetDataSample();
        getDataSample.operate();

        //获取多行   Scan (条件查询功能)
        /*ScanSample scan = new ScanSample();
        scan.operate();*/

        //获取多行  使用Filter过滤
        /*ScanWithFilterSample scanFilter = new ScanWithFilterSample();
        scanFilter.operate();*/

        //get put delete 都需要制定rowkey
        /*DeletaDataSample deletaData = new DeletaDataSample();
        deletaData.operate();*/

        //创建索引
        /*CreateIndexSample index = new CreateIndexSample();
        index.operate();*/

        //跟过滤查询一样  只不是是创建索引后  隐藏
        /*ScanByIndexSample scanIndex = new ScanByIndexSample();
        scanIndex.operate();*/

        /*DropIndexSample dropIndex = new DropIndexSample();
        dropIndex.operate();*/

        /*DropTableSample dropTable = new DropTableSample();
        dropTable.operate();*/
    }
}