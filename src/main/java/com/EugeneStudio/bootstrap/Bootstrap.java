package com.EugeneStudio.bootstrap;

import com.EugeneStudio.verticle.ServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Bootstrap {
    public static void main(String[] args) {
        Vertx.vertx(// 获取vertx基类
                new VertxOptions()
                        .setWorkerPoolSize(40))
                        .deployVerticle(new ServerVerticle()); // 部署发布rest服务
    }
}
