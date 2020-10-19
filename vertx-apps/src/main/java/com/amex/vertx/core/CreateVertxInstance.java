package com.amex.vertx.core;

import io.vertx.core.Vertx;

public class CreateVertxInstance {
  public static void main(String[] args) {
    //vertx instance creation
    Vertx myVertxInstance = Vertx.vertx();
    System.out.println(myVertxInstance.getClass().getName());
  }
}
