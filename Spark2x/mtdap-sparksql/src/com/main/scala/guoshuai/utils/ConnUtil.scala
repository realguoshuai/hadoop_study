package com.main.scala.guoshuai.utils

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}
import java.util.Properties

import com.huawei.hadoop.security.LoginUtil
import com.main.scala.guoshuai.utils.common.ENV
import org.apache.hadoop.conf.Configuration
import org.apache.log4j.Logger
import org.apache.spark.sql._


/**
  * Created by guoshuai on 2019/6/19
  */
object ConnUtil extends ENV{
  val logger = Logger.getLogger("ConnUtil")


  /**
    * 获取hive连接
    *
    * @param name spark job 名称
    * @return sparksession
    */
  def getHiveConn(name: String): SparkSession = {
    val sparkSession = SparkSession
      .builder()
      .appName(name)
      .enableHiveSupport()
      .getOrCreate()

    sparkSession
  }

  /**
    * 获取hive连接（重载）
    *
    * @param name         spark job 名称
    * @param partitionNum 并行度参数值
    * @return sparksession
    */
  def getHiveConn(name: String, partitionNum: Int): SparkSession = {
    //@transient
    val sparkSession = SparkSession
      .builder()
      .appName(name)
      .enableHiveSupport()
      .config("spark.sql.crossJoin.enabled",true)
      .config("spark.sql.shuffle.partitions", partitionNum)
      .getOrCreate()

    sparkSession
  }

  /**
    * huawei安全认证
    */
  def getHuawei(): Unit = {
    val userPrincipal: String = "guoshuai"
    val krb5ConfPath: String = "/opt/sh/all_data/krb5.conf"
    val userKeytabPath: String = "/opt/sh/all_data/user.keytab"
    val hadoopConf = new Configuration();
    LoginUtil.login(userPrincipal, userKeytabPath, krb5ConfPath, hadoopConf)
  }

  /**
    * huawei安全认证
    */
  def getHuawei2(): Unit = {
    val userPrincipal: String = "guoshuai@HADOOP.COM"
    val krb5ConfPath: String = "/opt/guoshuai/krb5.conf"
    val userKeytabPath: String = "/opt/guoshuai/user.keytab"
    val hadoopConf = new Configuration();
    LoginUtil.login(userPrincipal, userKeytabPath, krb5ConfPath, hadoopConf)
  }

}
