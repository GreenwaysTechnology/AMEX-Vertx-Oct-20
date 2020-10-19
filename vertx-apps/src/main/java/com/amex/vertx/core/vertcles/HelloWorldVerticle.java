package com.amex.vertx.core.vertcles;

import io.vertx.core.AbstractVerticle;

public class HelloWorldVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Verticle is Ready!");
    System.out.println(this.getClass().getName() + " " + Thread.currentThread().getName());
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    System.out.println("Verticle is going off");

  }
}
