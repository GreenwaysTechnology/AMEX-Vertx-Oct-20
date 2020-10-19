package com.amex.vertx.core.vertcles;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

class TaskVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Task : " + Thread.currentThread().getName());
  }
}


public class EventLopThreadForEachVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(EventLopThreadForEachVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println(" Main " +Thread.currentThread().getName());

    for (int i = 0; i <= 25; i++) {
      vertx.deployVerticle(new TaskVerticle());
    }
  }
}
