package com.guoshuai.utils

import java.math.BigDecimal
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.{Calendar, Date, Properties, TimeZone}

import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting

object TimeHelper {

  lazy val dateTimeFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  lazy val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
  lazy val businessProperties: Properties = InitPropertiesUtil.initBusinessProp

  /**
    * 判断当前时间处于哪个时段：早高峰、晚高峰、其他
    *
    * @param timeStr
    * @return 0：早高峰，1：晚高峰，9：其他
    */
  def judgeMorningEveningRush(timeStr: String): Int = {
    val morningRushStart = businessProperties.getProperty("morning_rush_start").replace(":", "").toInt
    val morningRushEnd = businessProperties.getProperty("morning_rush_end").replace(":", "").toInt
    val eveningRushStart = businessProperties.getProperty("evening_rush_start").replace(":", "").toInt
    val eveningRushEnd = businessProperties.getProperty("evening_rush_end").replace(":", "").toInt

    val currentTime = timeStr.substring(11, 16).replace(":", "").toInt

    if (currentTime >= morningRushStart && currentTime < morningRushEnd)
      0
    else if (currentTime >= eveningRushStart && currentTime < eveningRushEnd)
      1
    else
      9
  }

  /**
    * 对日期时间字符串加(减)若干秒
    *
    * @param timeString
    * @param seconds
    * @return
    */
  def addSeconds(timeString: String, seconds: Int): String = {
    val milliSeconds = dateTimeFormat.parse(timeString).getTime
    val tsmp = new Timestamp(milliSeconds + (seconds * 1000))
    dateTimeFormat.format(tsmp)
  }

  /**
    * 对日期时间字符串加(减)若干分钟
    *
    * @param timeString
    * @param minutes
    * @return
    */
  def addMinutes(timeString: String, minutes: Int): String = {
    val milliSeconds = dateTimeFormat.parse(timeString).getTime
    val tsmp = new Timestamp(milliSeconds + (minutes * 60 * 1000))
    dateTimeFormat.format(tsmp)
  }

  /**
    * 对日期时间字符串加(减)若干小时
    *
    * @param timeString
    * @param hours
    * @return
    */
  def addHours(timeString: String, hours: Int): String = {
    val milliSeconds = dateTimeFormat.parse(timeString).getTime
    val tsmp = new Timestamp(milliSeconds + (hours * 60 * 60 * 1000))
    dateTimeFormat.format(tsmp)
  }

  /**
    * 时间为String类型转为TimeStamp类型
    *
    * @param timeString 时间字符串，必须为" yyyy-MM-dd HH:mm:ss"的形式
    * @return Timestamp
    */
  def stringToTimestamp(timeString: String): Timestamp = {
    try {
      new Timestamp(dateTimeFormat.parse(timeString).getTime)
    } catch {
      case e: Exception => println("timestampToString error : " + e.getMessage)
        null
    }

    //val milliSeconds = dateTimeFormat.parse(timeString).getTime
    //这里加上8小时是为了避免写入数据时减少8小时
    //new Timestamp(milliSeconds + 8 * 60 * 60 * 1000)
  }

  /**
    * 将一个形如"yyyy-MM-dd"的字符串转换成对应的java.sql.Date对象
    *
    * @param dateString
    * @return
    */
  def getSQLDate(dateString: String): java.sql.Date = {
    new java.sql.Date(dateFormat.parse(dateString).getTime)
  }

  /**
    * 另一种方式的String转Timestamp
    *
    * @param tsStr
    * @return
    */
  def getSQLTimestamp(tsStr: String): Timestamp = {
    dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"))
    val utilDate = dateTimeFormat.parse(tsStr)
    new Timestamp(utilDate.getTime())
  }

  /**
    * 返回指定日期时间字符串的UNIX时间戳
    *
    * @param dateString
    * @return
    */
  def getTimeMillis(dateString: String): Long = {
    AnyRef.synchronized {
      val date = dateTimeFormat.parse(dateString)
      date.getTime
    }
  }

  /**
    * 返回小数点后指定位数的数值
    *
    * @param decimal
    * @param scale
    */
  def getDecimalScale(decimal: Double, scale: Int): Double = {
    new BigDecimal(decimal).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue()
  }

  /**
    * 将一个Timestamp类型的变量转换成String
    *
    * @param timestamp
    * @return
    */
  def timestampToString(timestamp: Timestamp): String = {
    try {
      dateTimeFormat.format(timestamp)
    } catch {
      case e: Exception => println("timestampToString error : " + e.getMessage)
        "0000-00-00"
    }
  }

  /**
    * 将UNIX时间戳转换为对应的Timestamp对象
    *
    * @param milliSecond
    * @return
    */
  def milliSecondToTimestamp(milliSecond: Long): Timestamp = {
    val date = dateTimeFormat.format(new Date(milliSecond))
    Timestamp.valueOf(date)
  }

  /**
    * 将UNIX时间戳转换为对应的Timestamp字符串
    *
    * @param milliSecond
    * @return
    */
  def milliSecondToTimestampString(milliSecond: Long): String = {
    AnyRef.synchronized {
      dateTimeFormat.format(new Date(milliSecond))
    }
  }

  /**
    * 将一个Timestamp对象转换成对应的UNIX时间戳
    *
    * @param timestamp
    * @return
    */
  def timestamptoMilliSecond(timestamp: Timestamp): Long = {
    timestamp.getTime
  }

  /**
    * 返回两个时间点之间的差值
    *
    * @param sTime 时间字符串，必须为" yyyy-MM-dd HH:mm:ss"的形式
    * @param eTime 时间字符串，必须为" yyyy-MM-dd HH:mm:ss"的形式
    * @return long，时间差（分钟）
    */
  def timesDiffMinute(sTime: String, eTime: String): Double = {
    try {
      val begin: Date = dateTimeFormat.parse(sTime)
      val end: Date = dateTimeFormat.parse(eTime)
      val decimal = new BigDecimal((end.getTime - begin.getTime) / 60000.0) //转化成分钟
      decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
    } catch {
      case e: Exception => println("timesDiff error : " + e.getMessage)
        0
    }
  }

  /**
    * 返回两个时间点之间的差值
    *
    * @param sTime 时间字符串，必须为" yyyy-MM-dd HH:mm:ss"的形式
    * @param eTime 时间字符串，必须为" yyyy-MM-dd HH:mm:ss"的形式
    * @return long，时间差（秒）
    */
  def timesDiffSecond(sTime: String, eTime: String): Double = {
    try {
      val begin: Date = dateTimeFormat.parse(sTime)
      val end: Date = dateTimeFormat.parse(eTime)
      val diff = (end.getTime - begin.getTime) / 1000.0 //转化为秒
      diff
    } catch {
      case e: Exception => println("timesDiff error : " + e.getMessage)
        0
    }
  }

  /**
    * 返回给定日期午夜时分的时间戳
    *
    * @param date 一个Date对象
    * @return 精确到毫秒的时间戳
    */
  def timestampOfMidnight(date: Date): Long = {
    val dateStr = dateTimeFormat.format(date).substring(0, 10) + " 23:59:59"
    val dateMilli = getTimeMillis(dateStr)
    dateMilli
  }

  /**
    * 返回给定日期午夜时分的时间戳
    *
    * @param date 一个String类型的日期对象
    * @return 精确到毫秒的时间戳
    */
  def timestampOfMidnight(date: String): Long = {
    val dateStr = date + " 23:59:59"
    val dateMilli = getTimeMillis(dateStr)
    dateMilli
  }

  /**
    * 对一个以时间为前缀的字符串数组进行排序并返回排序后的结果
    *
    * @param prefixTimeStr ，以时间开头的字符串数组，如
    *                      Array("2018-03-31 20:26:29_360112028004",
    *                      "2018-03-31 20:41:33_360107003001",
    *                      "2018-03-31 20:26:33_360111000102",
    *                      "2018-03-31 20:28:34_360107008002",
    *                      "2018-03-31 20:26:01_360104000503",
    *                      "2018-03-31 20:26:01_360104000210",
    *                      "2018-03-31 20:25:57_360106013001",
    *                      "2018-03-31 20:31:47_360122000302")
    * @return
    */
  def sortPrefixTime(prefixTimeStr: Array[String]): Array[String] = {
    Sorting.quickSort(prefixTimeStr)
    prefixTimeStr
  }

  /**
    * 对带有时间前缀的数组元素进行过滤，把数组中忽略时间前缀之后的元素去重，保留首次出现的元素
    *
    * @param prefixTimeStr
    * @return
    */
  def distinctSortPrefixTime(prefixTimeStr: Array[String]): Array[String] = {
    AnyRef.synchronized {
      Sorting.quickSort(prefixTimeStr)
      val afterDistinct = new ArrayBuffer[String]()

      for (element <- prefixTimeStr) {
        if (afterDistinct.length != 0) {
          if (!afterDistinct.last.split("_")(1).equals(element.split("_")(1))) {
            afterDistinct += element
          }
        } else {
          afterDistinct += element
        }
      }

      afterDistinct.toArray
    }
  }

  /**
    * 对一个日期时间字符串数组中的元素进行差值计算，结果以分钟为单位返回
    *
    * @param timeStr 日期时间字符串数组
    * @return 一个以分钟为单位的Int数组
    */
  def calArrayDiffMinute(timeStr: Array[String]): Array[Double] = {
    AnyRef.synchronized {
      //val timeBuff = ArrayBuffer[String]()
      val timeDiffBuff = ArrayBuffer[Double](0)
      /*for (elem <- prefixTimeStr) {
      timeBuff += elem.split("_")(0)
    }*/
      for (i <- 0 until timeStr.length - 1) {
        timeDiffBuff += timesDiffMinute(timeStr(i), timeStr(i + 1))
      }

      timeDiffBuff.toArray
    }
  }

  /**
    * 对一个日期时间字符串数组中的元素进行差值计算，结果以秒为单位返回
    *
    * @param timeStr 日期时间字符串数组
    * @return 一个以秒为单位的Int数组
    */
  def calArrayDiffSecond(timeStr: Array[String]): Array[Double] = {
    AnyRef.synchronized {
      //val timeBuff = ArrayBuffer[String]()
      val timeDiffBuff = ArrayBuffer[Double](0)
      /*for (elem <- prefixTimeStr) {
      timeBuff += elem.split("_")(0)
    }*/

      for (i <- 0 until timeStr.length - 1) {
        timeDiffBuff += timesDiffSecond(timeStr(i), timeStr(i + 1))
      }

      timeDiffBuff.toArray
    }
  }

  /**
    * 将数组中各元素前缀剥离后返回
    *
    * @param prefixStr 元素带有前缀的数组
    * @param splitStr  数组元素前后缀分隔符
    * @return
    */
  def stripPrefix(prefixStr: Array[String], splitStr: String): Array[String] = {
    AnyRef.synchronized {
      val suffix = prefixStr.toList.unzip(x => (x.split(splitStr)(0), x.split(splitStr)(1)))._2
      suffix.toArray
    }

    /*val otherBuff = ArrayBuffer[String]()
    for (elem <- prefixStr) {
      otherBuff += elem.split(splitStr)(1)
    }
    otherBuff.toArray*/

  }

  /**
    * 将数组中各元素后缀剥离后返回
    *
    * @param suffixStr 元素带有后缀的数组
    * @param splitStr  数组元素前后缀分隔符
    * @return
    */
  def stripSuffix(suffixStr: Array[String], splitStr: String): Array[String] = {
    AnyRef.synchronized {
      val prefix = suffixStr.toList.unzip(x => (x.split(splitStr)(0), x.split(splitStr)(1)))._1
      prefix.toArray
    }

    /*val otherBuff = ArrayBuffer[String]()
    for (elem <- suffixStr) {
      otherBuff += elem.split(splitStr)(0)
    }
    otherBuff.toArray*/
    //
  }

  /**
    * 对日期时间字符串中的秒部分按照传入的offset取整
    *
    * @param ts    “”“”      日期时间格式字符串："yyyy-MM-dd HH:mm:ss"
    * @param scale 秒数偏移量：10,15,30,60等
    * @return
    */
  def timestampSecondsRoundOff(ts: String, scale: Int): String = {
    val seconds = ts.substring(17).toInt
    if (seconds == 0) {
      ts
    } else {
      var remainder = (seconds - seconds % scale).toString
      if (remainder == "0") {
        remainder = "00"
      }
      ts.substring(0, 17) + remainder
    }
  }

  /**
    * 对日期时间字符串中的分钟部分按照传入的offset取整
    *
    * @param ts    “”“”      日期时间格式字符串："yyyy-MM-dd HH:mm:ss"
    * @param scale 分钟数偏移量：10,15,30,60等
    * @return
    */
  def timestampMinutesRoundOff(ts: String, scale: Int): String = {
    val minutes = ts.substring(14, 16).toInt
    if (minutes == 0) {
      ts.substring(0, 17) + "00"
    } else {
      var remainder = (minutes - minutes % scale).toString
      if (remainder == "0") {
        remainder = "00"
      }
      ts.substring(0, 14) + remainder + ":00"
    }
  }

  /**
    * 返回删除秒部分之后的时间日期字符串
    *
    * @param milliSecond
    * @return
    */
  def truncSeconds(milliSecond: Long): String = {
    milliSecondToTimestampString(milliSecond).substring(0, 17) + "00"
  }


  /** 判断传入的日期是否是工作日(周六周日)
    * @param date
    * return  true or flase
    */

  def isWeekend(date:String):Boolean={
    val format = new SimpleDateFormat("yyyy-MM-dd")
    val bdate = format.parse(date)
    val cal = Calendar.getInstance()
    cal.setTime(bdate)
    val flag = if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)true else false
    flag
  }

  /** 判断传入的日期是周几
    * @param date
    * return  true or flase
    */

  def isWeekday(date:String):Boolean={
    val format = new SimpleDateFormat("yyyy-MM-dd")
    val bdate = format.parse(date)
    val cal = Calendar.getInstance()
    cal.setTime(bdate)
    val flag = if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)true else false
    flag
  }


  /** 判断传入的日期是周几
    * @param dateStr
    * return  一周的第几天 1 周一  7周日  符合国情
    */
  def dayOfWeek(dateStr: String): Int = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.parse(dateStr)
    val cal = Calendar.getInstance()
    cal.setTime(date)
    var w = cal.get(Calendar.DAY_OF_WEEK) - 1

    //星期天 默认为0
    if (w <= 0) w = 7
    w
  }
  /**
    * 判断当前时间段是否是限行时段
    *
    * @param passingTimeLon 号牌号码
    * @return 是true  否false  根据需求添加(可能加上 设计时先搭好框架)
    */
  def isTailNumberLimitTime(passingTimeLon: Long): Boolean = {
    val passingTime = passingTimeLon
    /*获取时间范围*/
    var range1 = ""
    var range2 = ""
    var range3 = ""
    var range4 = ""
    //调用工具类  2019-06-17 09:21:56 -> 对比 小时分钟 是否在7:00-21:00(可配置)
    val timeRanges = JedisClusterUtil.get("mtdap3_limit_rule_timerange").get.split(",")
    for (i <- 0 to (timeRanges.length - 1)) {
      if(i==0){
        range1 = timeRanges(i).split("-")(0)
        range2 = timeRanges(i).split("-")(1)
      }
      if(i==1){
        range3 = timeRanges(i).split("-")(0)
        range4 = timeRanges(i).split("-")(1)
      }
    }
    //测试 ok
    /*range1="07:00"
    range2="09:00"
    range3="16:30"
    range4="18:30"*/
    if (!"".equals(range3) && !"".equals(range4)) {
      val today = dateFormat.format(new Date())
      val limit_begin1 = today + " " + range1 + ":00"
      val limit_end1 = today + " " + range2 + ":00"
      val limit_begin2 = today + " " + range3 + ":00"
      val limit_end2 = today + " " + range4 + ":00"
      //println("今天限行时间: "+limit_begin1+" "+limit_end1+" "+limit_begin2+" "+limit_end2)

      //时间转成timestamp
      val T1 = TimeHelper.getTimeMillis(limit_begin1)
      val T2 = TimeHelper.getTimeMillis(limit_end1)
      val T3 = TimeHelper.getTimeMillis(limit_begin2)
      val T4 = TimeHelper.getTimeMillis(limit_end2)
      val inTimeRanges = if ((T1 <= passingTime && passingTime <= T2) || (T3 <= passingTime && passingTime <= T4))
        true else false
      inTimeRanges
    } else {
      val today = dateFormat.format(new Date())
      val limit_begin1 = today + " " + range1 + ":00"
      val limit_end1 = today + " " + range2 + ":00"
      //println("今天限行时间: "+limit_begin1+" "+limit_end1)

      //时间转成timestamp
      val T1 = TimeHelper.getTimeMillis(limit_begin1)
      val T2 = TimeHelper.getTimeMillis(limit_end1)
      val inTimeRanges = if (T1 <= passingTime && passingTime <= T2)
        true else false
      inTimeRanges
    }
  }

  def main(args: Array[String]) = {
    val timeAndPoint = Array("2018-03-31 20:26:29_360112028004",
      "2018-03-31 20:41:33_360107003001",
      "2018-03-31 20:26:33_360107003001",
      "2018-03-31 20:28:33_360107003001",
      "2018-03-31 20:26:33_360107003001",
      "2018-03-31 20:21:33_360107003001",
      "2018-03-31 20:25:57_360106013001",
      "2018-03-31 20:31:33_360107003001",
      "2018-04-02 05:34:19_360134871001")

    /*val afterDistinct = distinctSortPrefixTime(timeAndPoint)
    println(afterDistinct.mkString(","))*/
    //7278134399000

    //val time1 = timestampOfMidnight(new Date)
    //val time1 = getTimeMillis("2018-08-20 23:59:59")
    //println(time1)

    //val begin: Date = dateFormat.parse("2018-08-20")
    //println(begin.toString)

    //val currentMillisecond = System.currentTimeMillis()
    //val lastMillisecond = currentMillisecond - 3 * 60 * 1000

    //val currentTimestamp = milliSecondToTimestamp(lastMillisecond)
    //val lastime = milliSecondToTimestampString(lastMillisecond)

    //println(milliSecondToTimestampString(currentMillisecond))
    //println(currentTimestamp.toString)
    //println(lastime)

    //println(addMinutes("2018-08-23 16:34:56", -30))
    //println(addHours("2018-08-23 16:34:56", -3))

    //val ts = "2018-12-03 11:51:28"
    //println(ts.substring(14, 16).toInt)
    //println(timestampMinutesRoundOff(ts, 30))

    println(milliSecondToTimestampString(1546395900000L))
    println(milliSecondToTimestampString(1546396080000L))

    isWeekend("2018-12-03 11:51:28")
  }


}
