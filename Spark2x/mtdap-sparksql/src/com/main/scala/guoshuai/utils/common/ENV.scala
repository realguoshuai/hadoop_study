package com.main.scala.guoshuai.utils.common

/**
  * created by guoshuai on 2019/07/30
  */
trait ENV {
    val prop = InitPropertiesUtil.initMysqlProp
    val driver_name: String = prop.getProperty("driver_name")
    val url: String = prop.getProperty("url")
    val phoenixUrl: String = prop.getProperty("phoenixUrl")
    val name: String = prop.getProperty("name")
    val ps: String = prop.getProperty("ps")

//早晚高峰
  val am_start:String = "7:00"
  val am_end:String = "9:00"
  val pm_start:String = "16:30"
  val pm_end:String = "18:30"
}
