package com.guoshuai.utils

import java.util

import net.minidev.json.JSONObject

import scala.collection.JavaConversions


/**
  * Description: redis集群操作工具类scala版
  */
object JedisClusterUtil {
  val clients = RedisConnector.clients

  /**
    *
    * @param key
    * @param value
    * @return
    */
  def set(key: String, value: String): Unit = {
    clients.set(key, value)
  }

  /**
    *
    * @param key
    * @return
    */
  def get(key: String): Option[String] = {
    val value = clients.get(key)
    if (value == null)
      None
    else
      Some(value)
  }


  /**
    *
    * @param key
    */
  def del(key: String): Unit = {
    clients.del(key)
  }

  /**
    *
    * @param hkey
    * @param key
    * @param value
    * @return
    */
  def hset(hkey: String, key: String, value: String): Boolean = {
    clients.hset(hkey, key, value) == 1
  }

  /**
    *
    * //@param hkey
    * //@param key
    * @return
    */
  def hget(hkey: String, key: String): Option[String] = {
    if (key == null) {
      Some(JSONObject.toJSONString(clients.hgetAll(hkey)))
      // clients.hgetAll(hkey)
    } else {
      val value = clients.hget(hkey, key)
      if (value == null)
        None
      else
        Some(value)
    }
  }

  /**
    * 返回一个包含所有字段的迭代器
    * @param hkey
    * @return
    */
  def hkeys(hkey: String): util.Iterator[String] = {
    clients.hkeys(hkey).iterator()
  }

  /**
    * 获取指定hash表中字段的数量
    * @param hkey
    * @return
    */
  def hlen(hkey: String): Long = {
    clients.hlen(hkey)
  }

  /**
    *
    * @param hkey
    * @param key
    * @return
    */
  def hdel(hkey: String, key: String): Option[Long] = {
    Some(clients.hdel(hkey, key))
  }

  /**
    *
    * @param hkey
    * @param map
    */
  def hmset(hkey: String, map: Map[String, String]): Unit = {
    clients.hmset(hkey, JavaConversions.mapAsJavaMap(map))
  }

  /**
    *
    * @param key
    * @param value
    * @return
    */
  def rpush(key: String, value: String): Option[Long] = {
    Some(clients.rpush(key, value))
  }

  /**
    *
    * @param key
    * @return
    */
  def lpop(key: String): Option[String] = {

    val value = clients.lpop(key)
    if (value == null)
      None
    else
      Some(value)
  }

  /**
    *
    * @param key
    * @return
    */
  def lhead(key: String): Option[String] = {
    val head = clients.lindex(key, 0)
    if (head == null)
      None
    else
      Some(head)
  }

  /**
    *
    * @param key
    * @return
    */
  def incr(key: String): Option[Long] = {
    val inc = clients.incr(key)
    if (inc == null)
      None
    else
      Some(inc)
  }

  /**
    *
    * @param key
    * @param time 秒
    * @return
    */
  def expire(key: String, time: Int) = {
    clients.expire(key, time)
  }

  /**
    *
    * @param key
    * @param time 毫秒
    * @return
    */
  def pexpire(key: String, time: Long) = {
    clients.pexpire(key, time)
  }

  /**
    * 设置key过期时间（时间戳）
    *
    * @param key
    * @param time unixTime
    * @return
    */
  def expireAt(key: String, time: Long) = {
    clients.expireAt(key, time)
  }

  /**
    * 设置key过期时间（UNIX毫秒时间戳）
    * @param key
    * @param time UNIX毫秒时间戳
    * @return
    */
  def pexpireAt(key: String, time: Long) = {
    println("*************** set " + key + " expire at " + time)
    clients.pexpireAt(key, time)
  }

  /**
    *
    * @param key
    * @return
    */
  def ttl(key: String): Option[Long] = {
    Some(clients.ttl(key))
  }

  /**
    *
    * @param key
    * @return
    */
  def exists(key: String): Boolean = {
    clients.exists(key)
  }

  /**
    * 向Set添加一个或多个成员
    *
    * @param key     Set的key
    * @param members 不定个数的value
    * @return
    */
  def sAdd(key: String, members: String*) = {
    for (member <- members)
      clients.sadd(key, member)
  }

  /**
    * 获取Set的成员数
    *
    * @param key
    * @return
    */
  def sCard(key: String) = {
    clients.scard(key)
  }

  /**
    * 判断member是否是Set的成员
    *
    * @param key
    * @param member
    * @return
    */
  def isSetMember(key: String, member: String): Boolean = {
    clients.sismember(key, member)
  }

  /**
    * 返回集合中的所有成员
    *
    * @param key
    * @return
    */
  def getSetMembers(key: String): util.Iterator[String] = {
    clients.smembers(key).iterator()
  }

  def main(args: Array[String]): Unit = {
    //val result = hgetall("road_peakflow")
    //println(hget("road_peakflow", null))
    //println(hget("road_peakflow", "104289520768"))
    //println(hget("road_peakflow", "104289520760").getOrElse(0))
    //val array = (1, 2, 3, 4)
    //JedisClusterUtil.hset("mtdap2_vehicle_track", "2浙A1234", "*****34593457*****")
    //println(clients.exists("mtdap2_vehicle_track"))
    /*for (i <- 1 to 10) {
      JedisClusterUtil.hset("mtdap2_vehicle_track", "2浙A1234", "*****34593457*****")
      clients.expire("mtdap2_vehicle_track", 1)
      println(clients.exists("mtdap2_vehicle_track"))
      println(JedisClusterUtil.hget("mtdap2_vehicle_track", "2浙A1234"))
    }*/
    //JedisClusterUtil.hset("mtdap2_vehicle_track", "2浙A1234", "*****34593457*****")
    clients.expire("mtdap2_vehicle_track", 1)
    //println(JedisClusterUtil.hget("mtdap2_vehicle_track", "2浙A1234").get)
    //println(clients.exists("mtdap2_vehicle_track"))
      //println(JedisClusterUtil.ttl("mtdap2_flow_keyroad_peak_30sec").get + 1)

    //if (JedisClusterUtil.ttl("mtdap2_flow_keyroad_peak_30sec").get == -1) {
      //println("true")
    //} else {
      //println("false")
    //}

  }
}
