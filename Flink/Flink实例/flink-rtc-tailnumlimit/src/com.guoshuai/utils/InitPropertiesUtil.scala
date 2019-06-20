package com.enjoyor.mtdap3.utils

import java.io.{BufferedInputStream, InputStream}
import java.util.Properties

/**
  * Description: 配置参数初始化公共类
  * Demo:
  * lazy val pro: Properties = InitPropertiesUtil.initKafkaPro
  * val topicsSet = pro.getProperty("topic").split(",").toSet
  * pro.getProperty("bootstrap_servers")
  */
object InitPropertiesUtil extends Serializable {

  /**
    * get business's properties
    *
    * @return java.util.Properties
    */
  def initBusinessProp: Properties = {
    val prop: Properties = new Properties
    val in: InputStream = getClass.getResourceAsStream("/business.properties")
    if (in == null) {
      println("ERROR : business's properties init failed in is null")
    }
    prop.load(new BufferedInputStream(in))
    println("success : business's properties init ok")
    prop
  }

  /**
    * get kafka's properties
    *
    * @return java.util.Properties
    */
  def initKafkaProp: Properties = {
    val prop: Properties = new Properties
    val in: InputStream = getClass.getResourceAsStream("/kafka.properties")
    if (in == null) {
      println("ERROR : kafka's properties init failed in is null")
    }
    prop.load(new BufferedInputStream(in))
    println("success : kafka's properties init ok")
    prop
  }

  /**
    * get redis's properties
    *
    * @return java.util.Properties
    */
  def initRedisProp: Properties = {
    val prop: Properties = new Properties
    val in: InputStream = getClass.getResourceAsStream("/redis.properties")
    if (in == null) {
      println("ERROR : redis's properties init failed in is null")
    }
    prop.load(new BufferedInputStream(in))
    println("success : redis's properties init ok")
    prop
  }

  /**
    * get phoenix's properties
    *
    * @return java.util.Properties
    */
  def initPhoenixProp: Properties = {
    val prop: Properties = new Properties
    val in: InputStream = getClass.getResourceAsStream("/phoenix.properties")
    if (in == null) {
      println("ERROR : phoenix's properties init failed in is null")
    }
    prop.load(new BufferedInputStream(in))
    prop
  }

  /**
    * get basic's properties
    *
    * @return java.util.Properties
    */
  def initBasicProp: Properties = {
    val prop: Properties = new Properties
    val in: InputStream = getClass.getResourceAsStream("/basic.properties")
    if (in == null) {
      println("ERROR : basic's properties init failed in is null")
    }
    prop.load(new BufferedInputStream(in))
    prop
  }
}
