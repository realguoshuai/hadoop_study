package com.guoshuai.mtdap.hbase;

/**
 * Created on 2018/12/20.
 * Constants for sample.
 * 样板常量类
 */
public class SampleConstants {

  /*命名空间*/
  public static final String NAMESPACE = "TEST";
  /*表名*/
  public static final String TABLE = "TEST:T1";
  /*MOB 是HBase的压缩分区策略*/
  public static final String TABLE_MOB = "TEST:Table_mob";
  /*CF: Column Family 列簇*/
  public static final String CF_BASIC = "basic";
  public static final String CF_BASIC_NAME = "name";
  public static final String CF_BASIC_GENDER = "gender";
  public static final String CF_BASIC_AGE = "age";
  public static final String CF_BASIC_ADDRESS = "address";
  public static final String CF_JOB = "job";
  public static final String CF_JOB_TIME = "time";
  public static final String CF_JOB_COMPANY = "company";
  /*索引*/
  public static final String INDEX = "sample_index";
  /*用户*/
  public static final String USER = "huawei";
  /*权限*/
  public static final String PERMISSIONS = "RW";
}
