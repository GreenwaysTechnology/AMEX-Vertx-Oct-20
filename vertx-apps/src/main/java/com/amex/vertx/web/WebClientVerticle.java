package com.amex.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class WebClientVerticle extends AbstractVerticle {
  WebClient webClient;

//  public static void main(String[] args) {
//    Runner.runExample(WebClientVerticle.class);
//  }

  @Override
  public void start() throws Exception {
    super.start();
    Router router = Router.router(vertx);
    webClient = WebClient.create(vertx);
    router.get("/api/viewproducts").handler(routingContext -> {
      Integer port = 3000;
      String host = "localhost";
      String url = "/products";
      //
      webClient.get(port, host, url).send(httpResponseAsyncResult -> {
        if (httpResponseAsyncResult.succeeded()) {
          HttpResponse<Buffer> response = httpResponseAsyncResult.result();
          JsonObject jsonObject = new JsonObject(response.body());
          routingContext
            .response()
            .setStatusCode(200)
            .end(response.bodyAsString());
        } else {
          routingContext.response().setStatusCode(400).end();
        }
      });

    });
    vertx.createHttpServer().requestHandler(router).listen(3001, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("Web Client Server is running on " + httpServerAsyncResult.result().actualPort());
      }
    });
  }
}
