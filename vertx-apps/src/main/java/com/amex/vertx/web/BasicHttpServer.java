package com.amex.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;

public class BasicHttpServer extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BasicHttpServer.class);
  }

  public void startServer() {
    //create HttpServer
    HttpServer httpServer = vertx.createHttpServer();

    //client request handling.
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      response.end("Hello,Vertx Web");
    });

    //start httpServer
    httpServer.listen(3001, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("Server is Ready " + httpServerAsyncResult.result().actualPort());
      }
    });
  }

  public void startFluentHttpServer() {
    vertx.createHttpServer()
      .requestHandler(request -> {
        request
          .response()
          .end("Hello! Vertx Web");
      })
      .listen(3001, httpServerAsyncResult -> {
        if (httpServerAsyncResult.succeeded()) {
          System.out.println("Server is Ready " + httpServerAsyncResult.result().actualPort());
        }
      });
  }

  public void sendHtml() {
    vertx.createHttpServer()
      .requestHandler(request -> request
        .response()
        .putHeader("content-type", "text/html")
        .end("<html><body><h1>Hello from vert.x!</h1></body></html>"))
      .listen(3001, serverHandler -> {
        if (serverHandler.succeeded()) {
          System.out.println("Server is ready at " + serverHandler.result().actualPort());
        } else {
          System.out.println(serverHandler.cause());
        }
      });
  }

  @Override
  public void start() throws Exception {
    super.start();
    //startFluentHttpServer();
    sendHtml();

  }
}
