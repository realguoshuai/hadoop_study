package com.guoshuai.mtdap.hbase.impl;

import com.guoshuai.mtdap.hbase.SampleConfiguration;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * 抽象类TableOperationImpl
 */
public abstract class TableOperationImpl implements TableOperation {

    Logger LOG = Logger.getLogger(TableOperationImpl.class);
    public static org.apache.hadoop.conf.Configuration conf = null;

    /*HBase Client下的类*/
    public static Connection connection = null;
    public static Admin admin = null;

    public TableOperationImpl() {
        init();
    }

    /*
     * 加properties获取conf
     * 加载conf,创建连接,获取 admin
     * 通过admin 来对Hbase表进行操作
     * */
    public void init() {
        conf = SampleConfiguration.getConfiguration();
        try {
            connection = ConnectionFactory.createConnection(conf);
            System.out.println("--" + connection);
            admin = connection.getAdmin();
        } catch (IOException e) {
            LOG.warn("Can not initialize connection or admin.");
        }
    }

    /*
     * */
    public void close() {
        if (admin != null) {
            try {
                admin.close();
            } catch (IOException e) {
                LOG.warn("Can not close admin.");
            } finally {
                admin = null;
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                LOG.warn("Can not close connection.");
            } finally {
                connection = null;
            }
        }
    }

    public void operate() {
        init();
        process();
        close();
    }
}
