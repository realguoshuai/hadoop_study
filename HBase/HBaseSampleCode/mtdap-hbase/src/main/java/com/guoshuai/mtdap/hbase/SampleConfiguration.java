package com.guoshuai.mtdap.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.io.File;

/**
 * Created on 2018/12/20.
 * 为HBase Client 初始化HBase Conf
 */
public class SampleConfiguration {
  private static Configuration innerConf = null;

  public static Configuration getConfiguration() {
    if(innerConf == null) {
      init();
    }
    return innerConf;
  }

  public static void init() {
    /*SampleConfiguration */
    innerConf = HBaseConfiguration.create();
    /*System.getProperty("user.dir") 获取当前项目路径*/
    /*File.separator这个代表系统目录中的间隔符*/
    String confDir = System.getProperty("user.dir") + File.separator + "conf";
    innerConf.addResource(confDir + File.separator + "hbase-site.xml");
    innerConf.addResource(confDir + File.separator + "core-site.xml");
    innerConf.addResource(confDir + File.separator + "hdfs-site.xml");
  }

}
