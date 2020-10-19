package com.amex.vertx.core.vertcles;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

public class GreeterVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(GreeterVerticle.class);
  }
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Verticle is Ready!!");
  }
}
