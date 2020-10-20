package com.amex.vertx.core.async.timers;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

import java.util.Date;

class TimersVerticle extends AbstractVerticle {

  //non blocking api ; timer
  public void delay(long timeout) {
    //any non blocking api
    vertx.setTimer(timeout, handler -> {
      System.out.println("i am delayed message");
    });
  }

  //encaspulate data after async opertion is completed
  public Future<String> delayWithData(long timeout) {
    Promise<String> promise = Promise.promise();
    //any non blocking api
    vertx.setTimer(timeout, handler -> {
      promise.complete("I am delayed but encapulated inside promise");
    });
    return promise.future();
  }

  public void delayWithDataV2(long timeout, Handler<AsyncResult<String>> aHandler) {
    //any non blocking api
    vertx.setTimer(timeout, handler -> {
      aHandler.handle(Future.succeededFuture("I am delayed with callback but encapulated inside promise"));
    });
  }

  public Future<String> trace(long timeout, Handler<AsyncResult<String>> aHandler) {
    Promise<String> promise = Promise.promise();
    long timerId = vertx.setPeriodic(timeout, handler -> {
      aHandler.handle(Future.succeededFuture(new Date().toString()));
    });

    //cancel timer after some time
    vertx.setTimer(10000, l -> {
      System.out.println("Stopping timer...");
      vertx.cancelTimer(timerId);
    });


    return promise.future();
  }

  public Future<JsonObject> delayWithJSON(long timeout) {
    Promise<JsonObject> promise = Promise.promise();
    //any non blocking api
    vertx.setTimer(timeout, handler -> {
      promise.complete(new JsonObject().put("id", 1).put("name", "Subramanian"));
    });
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("start");
    delay(1000);
    System.out.println("end");

    delayWithData(5000).onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    delayWithDataV2(3000, response -> {
      if (response.succeeded()) {
        System.out.println(response.result());
      }
    });
    trace(1000, dateHandler -> {
      if (dateHandler.succeeded()) {
        System.out.println(dateHandler.result());
      } else {
        System.out.println(dateHandler.cause());
      }
    });
    delayWithJSON(4000).onSuccess(user -> {
      System.out.println(user.encodePrettily());
    });
  }
}


public class TimersMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(TimersMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new TimersVerticle());
  }
}
