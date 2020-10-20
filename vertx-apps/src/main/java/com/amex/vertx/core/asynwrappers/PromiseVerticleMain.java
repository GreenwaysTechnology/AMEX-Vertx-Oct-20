package com.amex.vertx.core.asynwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class PromiseVerticle extends AbstractVerticle {

  //Promise
  public Promise<String> getSuccessPromise() {
    Promise<String> promise = Promise.promise();
    promise.complete("Hello");
    return promise;
  }

  public Future<String> getSuccessPromiseV1() {
    Promise<String> promise = Promise.promise();
    promise.complete("Hello");
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    getSuccessPromise().future().onComplete(asyncResult -> {
      System.out.println(asyncResult.result());
    });
    getSuccessPromiseV1().onComplete(asyncResult -> {
      System.out.println(asyncResult.result());
    });
  }
}


public class PromiseVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(PromiseVerticleMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new PromiseVerticle());
  }
}
