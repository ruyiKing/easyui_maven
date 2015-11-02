package com.yrw.test.redis;
import java.util.List;

import redis.clients.jedis.Jedis;
public class RedisListJava {
   private static Jedis jedis;

public static void main(String[] args) {
      jedis = new Jedis("localhost");
      System.out.println("Connection to server sucessfully");
      //存储数据到列表中
      jedis.lpush("tutorial-list", "Redis");
      jedis.lpush("tutorial-list", "Mongodb");
      jedis.lpush("tutorial-list", "Mysql");
     // 获取存储的数据并输出
     List<String> list = jedis.lrange("tutorial-list", 0 ,5);
     for(int i=0; i<list.size(); i++) {
       System.out.println("Stored string in redis:: "+list.get(i));
     }
 }
}