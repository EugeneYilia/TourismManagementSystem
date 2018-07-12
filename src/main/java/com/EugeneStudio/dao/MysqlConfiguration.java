package com.EugeneStudio.dao;

import com.EugeneStudio.bootstrap.Bootstrap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class MysqlConfiguration {
    public static JDBCClient jdbcClient;
    public static void configure(){
        jdbcClient = JDBCClient.createShared(Bootstrap.getVertx(), new JsonObject()//配置数据库连接
                .put("url", "jdbc:mysql://localhost:3306/TourismManagement?useUnicode=true&characterEncoding=utf-8&useSSL=true")
                .put("driver", "com.mysql.jdbc.Driver")
                .put("user", "root")
                .put("password", "liuyichen")
        );
        //        testJDBCConnection(jdbcClient);//success
    }

    public static void closeJDBCConnection() {
        jdbcClient.close(handler -> {
            if (handler.succeeded()) {
                System.out.println("mysql数据库关闭成功");
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

    public static JDBCClient getJdbcClient() {
        return jdbcClient;
    }
}
