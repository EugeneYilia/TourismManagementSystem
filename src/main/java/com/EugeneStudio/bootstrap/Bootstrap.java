package com.EugeneStudio.bootstrap;

import com.EugeneStudio.dao.MysqlConfiguration;
import com.EugeneStudio.dao.RedisConfiguration;
import com.EugeneStudio.verticle.ServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.sstore.SessionStore;

public class Bootstrap {


    public static SessionStore sessionStore;
    public static Vertx vertx;

    public static void main(String[] args) {
        vertx = Vertx.vertx(new VertxOptions()
                .setWorkerPoolSize(40));// 获取Vertx基类
        vertx.deployVerticle(new ServerVerticle()); // 部署发布rest服务
        RedisConfiguration.configure();
        MysqlConfiguration.configure();
    }

    public static Vertx getVertx() {
        return vertx;
    }
}
