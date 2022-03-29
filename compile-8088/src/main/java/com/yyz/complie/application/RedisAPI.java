package com.yyz.complie.application;

/**
 * @author yangyizhou
 * @create 2022/3/8 15:25
 */

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis操作接口
 */
public class RedisAPI {
    private static JedisPool pool = null;

    public static void set(String key, String value){
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        if (pool == null){
             pool = new JedisPool(poolConfig, "127.0.0.1", 6379);
        }
        try (Jedis jedis = pool.getResource()) {
            //从连接池获取jedis对象,执行操作
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("无法连接redis");
        }

    }

    public static String get(String key){
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        if (pool == null){
            pool = new JedisPool(poolConfig, "127.0.0.1", 6379);
        }
        try (Jedis jedis = pool.getResource()) {
            //从连接池获取jedis对象,执行操作
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("无法连接redis");
        }
    }



}
