package com.guoshuai.utils

import java.util.regex.Pattern

/**
  * 定义针对车牌的正则表达式匹配规则
  * 用于对车牌进行正确识别与否的判断
  */
object RegularExpressUtil {

  /**
    * 判断当前传入的号牌是否被正确识别
    * @param str 包含号牌号码的字符串
    * @return
    */
  def isValid(str: String): Boolean = {
    //民用（包括新能源）
    val pattern1 = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{5,6}$")
    //警察
    val pattern2 = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{4}[\\u4e00-\\u9fa5]{1}$")
    //武警
    val pattern3 = Pattern.compile("^WJ[\u4e00-\u9fa5]{0,1}[A-Z0-9]{5}$")
    //军队
    val pattern4 = Pattern.compile("^[A-Z]{1}[A-Z]{1}[0-9]{5}$")

    val matcher1 = pattern1.matcher(str)
    val matcher2 = pattern2.matcher(str)
    val matcher3 = pattern3.matcher(str)
    val matcher4 = pattern4.matcher(str)
    if (matcher1.matches || matcher2.matches || matcher3.matches || matcher4.matches) true
    else false
  }
}
