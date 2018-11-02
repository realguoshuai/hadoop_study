package com.enjoyor.mtdap.common;

import org.apache.log4j.Logger;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class RedisUtility {

    private static Logger logger = Logger.getLogger(RedisUtility.class.getName());
    private JedisCluster client;

    public RedisUtility() {
        Set<HostAndPort> hosts = new HashSet<HostAndPort>();
        hosts.add(new HostAndPort(RedisConst.IP_1, RedisConst.PORT_1));
        hosts.add(new HostAndPort(RedisConst.IP_2, RedisConst.PORT_2));
        hosts.add(new HostAndPort(RedisConst.IP_3, RedisConst.PORT_3));
        hosts.add(new HostAndPort(RedisConst.IP_4, RedisConst.PORT_4));
        hosts.add(new HostAndPort(RedisConst.IP_5, RedisConst.PORT_5));
        hosts.add(new HostAndPort(RedisConst.IP_6, RedisConst.PORT_6));

        // socket timeout(connect, read), unit: ms
        int timeout = 5000;
        client = new JedisCluster(hosts, timeout);
        logger.info("********** Initialize Redis Utility.");
    }

    /**
     * 判断某个key是否存在
     * @param key
     * @return
     */
    public boolean existsKey(String key) {
        return client.exists(key);
    }

    /**
     * 设置一个key-value
     * @param key
     * @param value
     * @return
     */
    public void set(String key, String value) {
        client.set(key, value);
    }

    /**
     * 根据key返回value
     * @param key
     * @return
     */
    public String get(String key) {
        String value = client.get(key);
        if (value != null) {
            return value;
        } else {
            return "";
        }
    }
}
