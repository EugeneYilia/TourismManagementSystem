package com.EugeneStudio.controller;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AdministratorController {

    public static void handleAuth(RoutingContext routingContext){

    }

    public static void handleLogin(RoutingContext context) {
        String param1 = context.request().getParam("param1"); // 从上下文获取请求参数，类似于从httprequest中获取parameter一样
        String param2 = context.request().getParam("param2");

        context.response()
                .putHeader("content-type", "application/json")   // 申明response类型为json格式，结束response并且输出json字符串
                .end(new JsonObject()
                        .put("identity", "administrator")
                        .put("param1", param1)
                        .put("param2", param2)
                        .encodePrettily());
    }
}
