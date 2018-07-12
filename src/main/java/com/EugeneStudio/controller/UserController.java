package com.EugeneStudio.controller;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserController {
    public static void handleAuth(RoutingContext routingContext){

    }

    public static void handleLogin(RoutingContext context) {
        String param1 = context.request().getParam("param1");
        String param2 = context.request().getParam("param2");
        if (param1 == null || param2 == null) {
            context.response()
                    .putHeader("content-type", "application/json")
                    .end(
                            new JsonObject()
                                    .put("identity", "fail")
                                    .put("param1", param1)
                                    .put("param2", param2)
                                    .encodePrettily());
        }
        context.response()
                .putHeader("content-type", "application/json")
                .end(
                        new JsonObject()
                                .put("identity", "user")
                                .put("param1", param1)
                                .put("param2", param2)
                                .encodePrettily());
    }
}
