package com.amex.vertx.core.asynwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.example.util.Runner;


/**
 * Steps:
 * 1.Create future object
 * 2.encapsulate data - success/failure
 * 3.register handler - setHandler,onComplete,onSuccess,OnFailure,succeeded
 * 4.Process result using AsyncHandler apis , result,cause,succeed /failed
 */


class FutureVerticle extends AbstractVerticle {

  //create future object with empty result
  public Future<Void> getEmptyFuture() {
    // 1.Create future object
    Future<Void> future = Future.future();
    // 2.encasulate data , empty data
    future.complete();
    return future;
  }

  //create future object with data - data type could be anything- prmitive or object
  public Future<String> getSuccessFuture() {
    Future<String> future = Future.future();
    // 2.encasulate data , empty data
    future.complete("Hello Future!");
    return future;
  }

  //create future object with error
  public Future<String> getFailureFuture() {
    Future<String> future = Future.future();
    // 2.encasulate data , empty data
    future.fail("something went wrong!");
    return future;
  }

  //biz logic and data
  public Future<String> login(String userName, String password) {
    Future<String> future = Future.future();
    //biz logic
    if (userName.equals("admin") && password.equals("admin")) {
      //send success data
      future.complete("login successe");
    } else {
      //send failure data
      future.fail("login failed");
    }

    return future;
  }

  //return future with help of Static factory apis
  public Future<String> validate(String userName, String password) {
    if (userName.equals("admin") && password.equals("admin")) {
      //send success data
      return Future.succeededFuture("validation ok");
    } else {
      //send failure data
      return Future.failedFuture("validation failed");
    }
  }

  //function as parameter
  public void findAllHandler(Handler<AsyncResult<String>> aHandler) {
    boolean isFound = false;
    if (isFound) {
      aHandler.handle(Future.succeededFuture("found"));
    } else {
      aHandler.handle(Future.failedFuture("Not found"));
    }

  }


  @Override
  public void start() throws Exception {
    super.start();
    //register handler - setHandler,onComplete,onSuccess,OnFailure,succeeded
    //empty result
    if (getEmptyFuture().succeeded()) {
      System.out.println("Future is completed");
    }
    //Registeraion.
    getSuccessFuture().setHandler(new Handler<AsyncResult<String>>() {
      @Override
      public void handle(AsyncResult<String> asyncResult) {
        if (asyncResult.succeeded()) {
          System.out.println(asyncResult.result());
        } else {
          System.out.println(asyncResult.cause().getMessage());
        }
      }
    });
    getSuccessFuture().setHandler(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause().getMessage());
      }
    });
    getSuccessFuture().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause().getMessage());
      }
    });
    //failures
    getFailureFuture().onComplete(asyncResult -> {
      if (asyncResult.failed()) {
        System.out.println(asyncResult.cause());
      } else {
        System.out.println(asyncResult.result());
      }
    });
    //simple api
    getSuccessFuture().onSuccess(response -> {
      System.out.println(response);
    });
    getFailureFuture().onFailure(error -> {
      System.out.println(error);
    });
    //using method reference
    getSuccessFuture().onSuccess(System.out::println);
    getFailureFuture().onFailure(System.out::println);
    /////////////////////////
    login("admin", "admin").onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    login("foo", "admin").onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    //"hello".trim().touppercase.tolower()
    login("admin", "admin")
      .onSuccess(System.out::println)
      .onFailure(System.out::println);

    ////
    validate("admin", "admin")
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
    ////////////////////////////////////////////////////
    //function as parameter
    findAllHandler(handler -> {
      if (handler.succeeded()) {
        System.out.println(handler.result());
      } else {
        System.out.println(handler.cause());
      }
    });


  }
}


public class FutureMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FutureMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new FutureVerticle());
  }
}
