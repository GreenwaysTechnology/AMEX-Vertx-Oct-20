package com.amex.vertx.core.asynwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class CallbackVerticle extends AbstractVerticle {

  //get User ---->login---->showPage---->result

  public Future<String> getUser() {
    Promise promise = Promise.promise();
    String userName = "admin";
    if (userName != null) {
      //output
      promise.complete(userName);
    } else {
      promise.fail(new RuntimeException("User not found"));
    }
    return promise.future();
  }

  public Future<String> login(String userName) {
    Promise promise = Promise.promise();
    if (userName.equals("admin")) {
      //output
      promise.complete("login success");
    } else {
      promise.fail(new RuntimeException("login failed"));
    }
    return promise.future();
  }

  public Future<String> showPage(String status) {
    Promise promise = Promise.promise();
    if (status.equals("login success")) {
      //output
      promise.complete("Welcome to Admin page");
    } else {
      promise.fail(new RuntimeException("You are inside Guest page"));
    }
    return promise.future();
  }

  public void callbackHell() {
    getUser().onComplete(userAsyncResult -> {
      if (userAsyncResult.succeeded()) {
        System.out.println("Get User is called");
        //call login method
        login(userAsyncResult.result()).onComplete(loginAsyncResult -> {
          if (loginAsyncResult.succeeded()) {
            System.out.println("login  is called");
            showPage(loginAsyncResult.result()).onComplete(pageAsyncResult -> {
              if (pageAsyncResult.succeeded()) {
                System.out.println("show page is called");
                System.out.println(pageAsyncResult.result());
              } else {
                System.out.println(pageAsyncResult.cause().getMessage());
              }
            });
          } else {
            System.out.println(loginAsyncResult.cause());
          }
        });
      } else {
        System.out.println(userAsyncResult.cause());
      }
    });
  }

  public void composeFuture() {
    getUser().compose(user -> {
      System.out.println("login  is called");
      return login(user);
    }).compose(status -> {
      System.out.println("show page is called");
      return showPage(status);
    }).onSuccess(System.out::println)
      .onFailure(System.out::println);
    //if dont have logs
    getUser()
      .compose(user -> login(user))
      .compose(status -> showPage(status))
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
    //using method reference
    getUser()
      .compose(this::login)
      .compose(this::showPage)
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
  }

  @Override
  public void start() throws Exception {
    super.start();
    composeFuture();

  }
}

public class CallbackMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(CallbackMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new CallbackVerticle());
  }
}
