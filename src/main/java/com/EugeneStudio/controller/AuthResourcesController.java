package com.EugeneStudio.controller;

import com.EugeneStudio.bootstrap.Bootstrap;
import com.EugeneStudio.verticle.ServerVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.redis.RedisClient;

import java.io.File;

public class AuthResourcesController {//01普通用户  10管理员

    public static void handleAuthResource(RoutingContext routingContext) {//静态资源的路由及权限配置
        String param1 = routingContext.request().getParam("param1");//请求的页面资源
       /* if (param1 == null) {
            return;
        }*/
        System.out.println("收到请求" + param1);
        File file = new File("src/main/resources/static/AuthResources/" + param1);
        if (!file.exists()) {
            routingContext.request().response().putHeader("content-type", "text/html;charset=utf-8").sendFile("src/main/resources/static/NormalResources/404.html").end();
        } else if (!file.getName().equals(param1)) {//文件名有大小写的差别造成的文件不一样，此时也返回文件不存在
            routingContext.request().response().putHeader("content-type", "text/html;charset=utf-8").sendFile("src/main/resources/static/NormalResources/404.html").end();
        }
        //RedisClient redisClient = Bootstrap.getRedisClient();
        if (param1.equals("AdministratorDashboard.html")) {//验证cookie
            //Cookie cookie = routingContext.getCookie("authority");
            //String value = cookie.getValue();
            //vertx自动帮我们找到需要的session
            Session session = routingContext.session();
//            routingContext.request().;
            if (testSessionIsNull(session, routingContext)) {
                return;
            }
            String permission = session.get("authority");
            if (permission == null) {
                System.out.println("用户验证已过期");
                routingContext.request().response().putHeader("content-type", "text/html;charset=utf-8").sendFile("src/main/resources/static/NormalResources/Expiration.html").end();
            } else if (permission.equals("10")) {
                routingContext.request().response().putHeader("content-type", "text/html;charset=utf-8").sendFile("src/main/resources/static/AuthResources/AdministratorDashboard.html").end();
            } else {
                routingContext.request().response().putHeader("content-type", "text/html;charset=utf-8").sendFile("src/main/resources/static/NormalResources/Expiration.html").end();
            }
//            router.route().handler(AdministratorController::handleAuth);
        } else if (param1.equals("UserDashboard.html")) {//验证cookie
            /*Cookie cookie = routingContext.getCookie("UserID");
            String value = cookie.getValue();//如果Session达到了timeout已经过期之后，那么直接得不到对应的session
            boolean isPermitted = false;*/
            Session session = routingContext.session();
            if (testSessionIsNull(session, routingContext)) {
                return;
            }
            String permission = session.get("authority");
            if (permission == null) {
                System.out.println("用户验证已过期");
                routingContext.request().response().putHeader("content-type", "text/html;charset=utf-8").sendFile("src/main/resources/static/NormalResources/Expiration.html").end();
            } else if (permission.equals("10") || permission.equals("01")) {//可以访问特定的页面
                routingContext.request().response().putHeader("content-type", "text/html;charset=utf-8").sendFile("src/main/resources/static/AuthResources/UserDashboard.html").end();
            } else {//没有访问权限，进行重定向
                routingContext.request().response().putHeader("content-type", "text/html;charset=utf-8").sendFile("src/main/resources/static/NormalResources/Expiration.html").end();
            }
        }
    }

    private static boolean testSessionIsNull(Session session, RoutingContext routingContext) {
        if (session == null) {
            System.out.println("session is null");
            routingContext.request().response().sendFile("src/main/resources/static/NormalResources/Expiration.html").end();
            return true;
        } else {
            System.out.println("session is not null");
            return false;
        }
    }
}
