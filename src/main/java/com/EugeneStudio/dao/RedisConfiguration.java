package com.EugeneStudio.dao;

import com.EugeneStudio.bootstrap.Bootstrap;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class RedisConfiguration {
    public static RedisClient redisClient;

    public static void configure() {

        redisClient = RedisClient.create(Bootstrap.getVertx(), new RedisOptions()
                .setHost("127.0.0.1")
                .setPort(6379)
                .setEncoding("UTF-8")
        );

        //        testRedisConnection(redisClient);//success

    }

    public static void testRedisConnection(RedisClient redisClient) {
        redisClient.set("ccc", "111", handler -> {
            if (handler.succeeded()) {
                System.out.println("redis设置键值成功");
               /* redisClient.subscribe("ccc", s -> {
                    System.out.println(s.result());
                });*/
            } else {
                System.out.println("redis设置键值失败");
            }
        });

        redisClient.get("ccc", s -> {
            if (s.succeeded()) {
                System.out.println(s.result());
                System.out.println("redis键对应的值获取成功");
            } else {
                System.out.println("redis键对应的值获取失败");
            }
        });



/*        redisClient.subscribe("ccc", s -> {
            if (s.succeeded()) {
                System.out.println(s.result());
                System.out.println("redis键对应的值获取成功");
            } else {
                System.out.println("redis键对应的值获取失败");
            }
        });*/

        //System.out.println("############################################");

        redisClient.set("bbb", "333", r -> {
            if (r.succeeded()) {
                System.out.println("redis设置键值成功");
                redisClient.get("bbb", s -> {
                    System.out.println(s.result());
                });
            } else {
                System.out.println("Connection or Operation Failed " + r.cause());
            }
        });
    }


   /* public static void testRedisConnection2(RedisClient redisClient) {
        redisClient.set("ccc", new Recorder(new Date(),"1","1",1), handler -> {
            if (handler.succeeded()) {
                System.out.println("redis设置键值成功");
               *//* redisClient.subscribe("ccc", s -> {
                    System.out.println(s.result());
                });*//*
            } else {
                System.out.println("redis设置键值失败");
            }
        });

        redisClient.get("ccc", s -> {
            if (s.succeeded()) {
                System.out.println(s.result());
                System.out.println("redis键对应的值获取成功");
            } else {
                System.out.println("redis键对应的值获取失败");
            }
        });
    }*/


    public static void closeRedisConnection() {
        redisClient.close(handler -> {
            if (handler.succeeded()) {
                System.out.println("redis服务器关闭成功");
            } else {
                System.out.println("mysql数据库关闭失败");
            }
        });
    }


    public static RedisClient getRedisClient() {
        return redisClient;
    }


}
