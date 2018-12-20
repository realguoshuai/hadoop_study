package com.guoshuai.mtdap.hbase.impl;

/**
 * Created on 2018/12/20.
 * The interface for sample.
 * 所有操作都要实现这个接口  控制表初始化 连接 关闭
 */
public interface TableOperation {
  
  //初始化参数
  public void init();
  
  //do the operation in each function
  public void process();
  
  //the entrance for each operation
  public void operate();
  
  //关闭连接
  public void close();
}
