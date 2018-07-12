package com.EugeneStudio.controller;

import com.EugeneStudio.dao.MysqlConfiguration;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;

import java.util.List;

public class UserController {
    public static void handleAuth(RoutingContext routingContext){

    }

    public static void handleLogin(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id"); // 从上下文获取请求参数，类似于从httprequest中获取parameter一样
        String password = routingContext.request().getParam("password");

        Session session = routingContext.session();

        MysqlConfiguration.getJdbcClient().getConnection(connectionHandler -> {
            if (connectionHandler.succeeded()) {
                final SQLConnection sqlConnection = connectionHandler.result();
                sqlConnection.query("select * from user where id = '" + id + "' and password = '" + password + "'", queryHandler -> {
                    if (queryHandler.succeeded()) {
                        List<JsonObject> list = queryHandler.result().getRows();
                        if (list == null || list.isEmpty()) {//用户不存在
                            routingContext.response()
                                    .putHeader("content-type", "application/json")   // 申明response类型为json格式，结束response并且输出json字符串
                                    .end(new JsonObject()
                                            .put("identity", "user")
                                            .put("login", false)
                                            .encodePrettily());
                            return;
                        } else {
                            session.put("authority", "01");//创建session并分配给其对应的权限，其有效时间为之前设置的30分钟
                            routingContext.response()
                                    .putHeader("content-type", "application/json")   // 申明response类型为json格式，结束response并且输出json字符串
                                    .end(new JsonObject()
                                            .put("identity", "user")
                                            .put("login", true)
                                            .encodePrettily());
                            return;
                        }
                    } else {
                        System.out.println("查找数据失败");
                    }
                });
            } else {
                System.out.println("数据库连接失败");
            }
        });
    }
}
