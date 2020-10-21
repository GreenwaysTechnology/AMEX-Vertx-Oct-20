package com.amex.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

public class RESTEndPointVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(RESTEndPointVerticle.class);
  }

  public void createPage() {
    vertx.createHttpServer()
      .requestHandler(request -> {
        System.out.println(request.uri() + " " + request.method());
        HttpServerResponse response = request.response();
        //home page
        if (request.uri().equals("/") && request.method() == HttpMethod.GET) {
          //send a response
          response.putHeader("content-type", "text/html").end("<h1>Home</h1>");
        }
        if (request.uri().equals("/page/hello") && request.method() == HttpMethod.GET) {
          //send a response
          response.putHeader("content-type", "text/html").end("<h1>Hello Vertx Web Server</h1>");
        }
        if (request.uri().equals("/page/hai") && request.method() == HttpMethod.GET) {
          //send a response
          response.putHeader("content-type", "text/html").end("<h1>Hai Vertx Web Server</h1>");
        }


      })
      .listen(3001, httpServerAsyncResult -> {
        if (httpServerAsyncResult.succeeded()) {
          System.out.println("REST Server is running on " + httpServerAsyncResult.result().actualPort());
        }
      });

  }

  public void createREST() {
    vertx.createHttpServer()
      .requestHandler(request -> {
        System.out.println(request.uri() + " " + request.method());
        HttpServerResponse response = request.response();
        //home page
        if (request.uri().equals("/") && request.method() == HttpMethod.GET) {
          //send a response
          response.putHeader("content-type", "text/html").end("<h1>Home</h1>");
        }
        if (request.uri().equals("/api/hello") && request.method() == HttpMethod.GET) {
          //send a response
          JsonObject jsonObject = new JsonObject().put("message", "Hello Vertx Web");
          response.putHeader("content-type", "application/json").end(jsonObject.encodePrettily());
        }
        if (request.uri().equals("/api/hai") && request.method() == HttpMethod.GET) {
          //send a response
          JsonObject jsonObject = new JsonObject().put("message", "Hai Vertx Web");
          response.putHeader("content-type", "application/json").end(jsonObject.encodePrettily());
        }


      })
      .listen(3001, httpServerAsyncResult -> {
        if (httpServerAsyncResult.succeeded()) {
          System.out.println("REST Server is running on " + httpServerAsyncResult.result().actualPort());
        }
      });

  }

  @Override
  public void start() throws Exception {
    super.start();
    //createPage();
    createREST();
  }
}
