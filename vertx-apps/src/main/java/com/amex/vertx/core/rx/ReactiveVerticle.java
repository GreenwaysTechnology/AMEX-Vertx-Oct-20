package com.amex.vertx.core.rx;

import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.core.http.HttpServerResponse;

//Reactive HTTP Server Verticle
class ReactiveHttpServerVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Reative");
    HttpServer httpServer = vertx.createHttpServer();
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      response.end("Hello Reactive");
    });
    httpServer.rxListen(3000).subscribe(server -> {
      System.out.println(server.actualPort());
    }, err -> {
      System.out.println(err);
    });
  }
}


public class ReactiveVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ReactiveVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.rxDeployVerticle(new ReactiveHttpServerVerticle())
      .subscribe(deploymentID -> System.out.println(deploymentID), err -> {
        System.out.println(err);
      });
  }
}
