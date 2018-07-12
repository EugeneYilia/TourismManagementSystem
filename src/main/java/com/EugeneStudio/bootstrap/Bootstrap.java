package com.EugeneStudio.bootstrap;

import com.EugeneStudio.verticle.ServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class Bootstrap {
    public static JDBCClient jdbcClient;
    public static RedisClient redisClient;
    public static SessionStore sessionStore;
    public static Vertx vertx;

    public static void main(String[] args) {
        vertx = Vertx.vertx(new VertxOptions()
                .setWorkerPoolSize(40));// 获取Vertx基类
        jdbcClient = JDBCClient.createShared(vertx, new JsonObject()//配置数据库连接
                .put("url", "jdbc:mysql://localhost:3306/TourismManagement?useUnicode=true&characterEncoding=utf-8&useSSL=true")
                .put("driver", "com.mysql.jdbc.Driver")
                .put("user", "root")
                .put("password", "liuyichen")
        );
//        testJDBCConnection(jdbcClient);//success
        redisClient = RedisClient.create(vertx, new RedisOptions()
                .setHost("127.0.0.1")
                .setPort(6379)
                .setEncoding("UTF-8")
        );

        //        testRedisConnection(redisClient);//success
        vertx.deployVerticle(new ServerVerticle()); // 部署发布rest服务
    }

    public static Vertx getVertx() {
        return vertx;
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

    public static void closeJDBCConnection() {
        jdbcClient.close(handler -> {
            if (handler.succeeded()) {
                System.out.println("mysql数据库关闭成功");
            } else {
                System.out.println("mysql数据库关闭失败");
            }
        });
    }

    public static void closeRedisConnection() {
        redisClient.close(handler -> {
            if (handler.succeeded()) {
                System.out.println("redis服务器关闭成功");
            } else {
                System.out.println("mysql数据库关闭失败");
            }
        });
    }

    public static void testJDBCConnection(JDBCClient jdbcClient) {
        jdbcClient.getConnection(response -> {
            if (response.failed()) {
                System.out.println("获取连接失败");
                throw new RuntimeException(response.cause());
            }
            System.out.println("获取连接成功");
            final SQLConnection sqlConnection = response.result();
            sqlConnection.execute("insert into user values('1','1')", handler -> {
                if (handler.failed()) {
                    throw new RuntimeException(handler.cause());
                } else {
                    System.out.println("插入用户数据成功");
                }
            });
        });
    }

    public static RedisClient getRedisClient() {
        return redisClient;
    }

    public static JDBCClient getJdbcClient() {
        return jdbcClient;
    }
}
