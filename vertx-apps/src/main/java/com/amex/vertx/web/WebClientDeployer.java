package com.amex.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

public class WebClientDeployer extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(WebClientDeployer.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //how to ensure deployment successfull.
    //verticle deployment itself async.
    //after deployment, vertx assigns unique identifier called deploymentId.
    vertx.deployVerticle(new SimpleREST(), handler -> {
      if (handler.succeeded()) {
        System.out.println("SIMPLE REST Verticle has been deployed  id : " + handler.result());
      } else {
        System.out.println(handler.cause().getMessage());
      }
    });
    vertx.deployVerticle(new WebClientVerticle(), handler -> {
      if (handler.succeeded()) {
        System.out.println("WebClientVerticle  Verticle has been deployed  id : " + handler.result());
      } else {
        System.out.println(handler.cause().getMessage());
      }
    });
  }
}
