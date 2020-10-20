package com.amex.vertx.core.fs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.example.util.Runner;


public class FileSytemVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FileSytemVerticle.class);
  }

  public Future<Buffer> readFile() {
    Promise<Buffer> fileContent = Promise.promise();
    FileSystem fs = vertx.fileSystem();
    fs.readFile("assets/info.txt", fileHander -> {
      if (fileHander.succeeded()) {
        //System.out.println(fileHander.result().toString());
        fileContent.complete(fileHander.result());
      } else {
        fileContent.fail(fileHander.cause());
        // System.out.println(fileHander.cause());
      }
    });
    return fileContent.future();
  }

  public Future<String> readJSONFile() {
    Promise<String> fileContent = Promise.promise();
    FileSystem fs = vertx.fileSystem();
    fs.readFile("assets/users.json", fileHander -> {
      if (fileHander.succeeded()) {
        fileContent.complete(fileHander.result().toString());
      } else {
        fileContent.fail(fileHander.cause());
        // System.out.println(fileHander.cause());
      }
    });
    return fileContent.future();
  }

  public Future<String> write() {
    Promise<String> fileContent = Promise.promise();
    FileSystem fs = vertx.fileSystem();
    Buffer data = Buffer.buffer();
    data.appendString("Hello!!!!");
    fs.writeFile("assets/greetings.txt", data, fileHander -> {
     if(fileHander.succeeded()){
       System.out.println("written");
     }else{
       System.out.println(fileHander.cause());

     }
    });
    return fileContent.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("start");
    readFile().onSuccess(response -> {
      System.out.println(response.toString());
    });
    readJSONFile().onSuccess(response -> {
      System.out.println(response);
    });
    write();
    System.out.println("end");

  }
}
