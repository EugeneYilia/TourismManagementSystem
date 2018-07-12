package com.EugeneStudio.verticle;

import com.EugeneStudio.controller.AdministratorController;
import com.EugeneStudio.controller.UserController;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class ServerVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx); // 实例化一个路由器出来，用来路由不同的rest接口
        router.route().handler(BodyHandler.create()); // 增加一个处理器，将请求的上下文信息，放到RoutingContext中
        router.get("/administrator/login/:param1/:param2").handler(AdministratorController::handleLogin);// 处理一个get方法的rest接口
        router.get("/user/login/:param1/:param2").handler(UserController::handleLogin);// 处理一个get方法的rest接口
        vertx.createHttpServer().requestHandler(router::accept).listen(80);// 创建一个httpserver，监听80端口，并交由路由器分发处理用户请求
    }
}
