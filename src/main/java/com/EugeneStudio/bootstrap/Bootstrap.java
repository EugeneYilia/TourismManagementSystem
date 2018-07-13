package com.EugeneStudio.bootstrap;

import com.EugeneStudio.core.CLIBootstrap;
import com.EugeneStudio.dao.MysqlConfiguration;
import com.EugeneStudio.dao.RedisConfiguration;
import com.EugeneStudio.verticle.CLIVerticle;
import com.EugeneStudio.verticle.ServerVerticle;
import com.EugeneStudio.worker.CLIWorker;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.WorkerExecutor;
import io.vertx.ext.web.sstore.SessionStore;

public class Bootstrap {

    private static SessionStore sessionStore;
    private static Vertx vertx;
    private static WorkerExecutor workerExecutor;

    public static void main(String[] args) {
        vertx = Vertx.vertx(new VertxOptions()
                .setWorkerPoolSize(40)
        );// 获取Vertx基类

        RedisConfiguration.configure();
        MysqlConfiguration.configure();
        vertx.deployVerticle(new ServerVerticle()); // 部署发布rest服务
        new Thread(() -> CLIBootstrap.start()).start();
//        vertx.deployVerticle(new CLIVerticle());
        //int poolSize = 10;
//        long maxExecuteTime = 1000*1000*1000*20;
        //workerExecutor = vertx.createSharedWorkerExecutor("EugeneWorkerPool", poolSize);//50个worker   每个worker时长为30分钟

        //new CLIWorker().startWork();
    }

    public static Vertx getVertx() {
        return vertx;
    }

    public static SessionStore getSessionStore() {
        return sessionStore;
    }

    public static WorkerExecutor getWorkerExecutor() {
        return workerExecutor;
    }
}
