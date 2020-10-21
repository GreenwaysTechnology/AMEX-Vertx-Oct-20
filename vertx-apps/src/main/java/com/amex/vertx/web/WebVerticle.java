package com.amex.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;

class MessageController extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    //Server
    HttpServer httpServer = vertx.createHttpServer();

    //Routers
    Router router = Router.router(vertx);
    //Router has collection of routes.
    router.get("/").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      HttpServerRequest request = routingContext.request();
      response
        .setStatusCode(200)
        .putHeader("content-type", "text/html")
        .end("<h1>Home Page</h1>");
    });
    router.get("/page/hello").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      HttpServerRequest request = routingContext.request();
      response
        .setStatusCode(200)
        .putHeader("content-type", "text/html")
        .end("<h1>Hello Vertx Web </h1>");
    });
    router.get("/page/hai").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      HttpServerRequest request = routingContext.request();
      response
        .setStatusCode(200)
        .putHeader("content-type", "text/html")
        .end("<h1>Hi Vertx Web </h1>");
    });

    httpServer.requestHandler(router);


    httpServer.listen(3000, serverHandler -> {
      if (serverHandler.succeeded()) {
        System.out.println("Server is ready at " + serverHandler.result().actualPort());
      } else {
        System.out.println(serverHandler.cause());
      }
    });

  }
}


public class WebVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(WebVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new MessageController());
  }
}
