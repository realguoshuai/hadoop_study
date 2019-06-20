package com.guoshuai.utils

import java.util

import redis.clients.jedis.{HostAndPort, JedisCluster}

/**
  * Description: redis集群初始化类 scala版
  */
object RedisConnector {

  val redisProp = InitPropertiesUtil.initRedisProp
  val ips = redisProp.getProperty("ips_cluster").split(",")
  val ports = redisProp.getProperty("ports_cluster").split(",")

  val jedisClusterNodes = new util.HashSet[HostAndPort]()

  for (i <- 0 until ips.length) {
    for (j <- 0 until ports.length) {
      jedisClusterNodes.add(new HostAndPort(ips(i), ports(j).toInt))
    }
  }

  val clients = new JedisCluster(jedisClusterNodes, 15000)
}
