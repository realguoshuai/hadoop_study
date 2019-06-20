package com.guoshuai.utils

import java.sql.{Connection, DriverManager}
import java.util.{LinkedList, Properties}


/**
  * phoenix连接池工具类
  */
object PhoenixConnPool {

  lazy val properties: Properties = InitPropertiesUtil.initPhoenixProp
  private val maxConnection = Integer.parseInt(properties.getProperty("jdbc.max_connection"))
  private val connectionNum = properties.getProperty("jdbc.connection_num")
  private var currentNum = 0
  private var pools: LinkedList[Connection] = null //连接池
  private val url = properties.getProperty("jdbc.url")

  /**
    * 获得连接
    */
  def initConnection(): Connection = {
    val connection = DriverManager.getConnection(url)
    connection.setAutoCommit(false)
    connection
  }


  /**
    * 初始化连接池
    */
  private def initConnectionPool(): LinkedList[Connection] = {
    AnyRef.synchronized({
      pools = new LinkedList[Connection]()
      if (pools.isEmpty()) {
        for (i <- 1 to connectionNum.toInt) {
          pools.push(initConnection())
          currentNum += 1
        }
      }
      pools
    })
  }

  /**
    * 获得连接
    */
  def getConnection(): Connection = {
    if (pools == null)
      initConnectionPool()
    if (pools.size() == 0)
      initConnection()
    else
      AnyRef.synchronized({
        pools.poll()
      })
  }

  /**
    * 释放连接
    */
  def releaseConnection(connection: Connection) {
    pools.push(connection)
  }
}
