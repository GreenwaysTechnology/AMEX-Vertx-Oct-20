package com.amex.vertx.core.vertcles;

import io.vertx.core.AbstractVerticle;

public class HelloVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("HelloVerticle");
  }
}
