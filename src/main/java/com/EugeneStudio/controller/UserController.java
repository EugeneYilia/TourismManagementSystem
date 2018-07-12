package com.EugeneStudio.controller;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserController {
    public static void handleLogin(RoutingContext context) {
        String param1 = context.request().getParam("param1");
        String param2 = context.request().getParam("param2");

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
