package com.amex.vertx.core.vertcles;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;


public class DeployerVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(DeployerVerticle.class);
    //programgramtic deployment
//    Vertx vertxInstace = Vertx.vertx();
//    vertxInstace.deployVerticle(new DeployerVerticle());
//    vertxInstace.deployVerticle(new HelloVerticle());
//    //verticles are deployed using factory api.
//    vertxInstace.deployVerticle(HelloVerticle.class.getName());
//    vertxInstace.deployVerticle("com.amex.vertx.core.vertcles.HelloVerticle");

  }

  @Override
  public void start() throws Exception {
    super.start();

    vertx.deployVerticle(new HelloVerticle());
    //verticles are deployed using factory api.
    vertx.deployVerticle(HelloVerticle.class.getName());
    vertx.deployVerticle("com.amex.vertx.core.vertcles.HelloVerticle");
  }
}
