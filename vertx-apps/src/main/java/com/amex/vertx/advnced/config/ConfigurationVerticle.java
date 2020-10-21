package com.amex.vertx.advnced.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;


class ApplicationConfigFromFile extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    options.setFormat("json");
    options.setConfig(new JsonObject().put("path", "assets/config.json"));

    //////////////////////////////////////////////////////////////////////////////////////////
    //config reteriver
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(options));

    retriever.getConfig(configHandler -> {
      if (configHandler.succeeded()) {
        JsonObject fileConfig = configHandler.result();
        System.out.println(fileConfig.encodePrettily());
      } else {
        System.out.println(configHandler.cause());
      }
    });


  }
}


class ApplicationVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    JsonObject appconfig = config();
    System.out.println(appconfig.getString("message", "default Message"));
    System.out.println(appconfig.encodePrettily());

    vertx.createHttpServer()
      .requestHandler(request -> request.response().end(appconfig.getString("message")))
      .listen(config().getInteger("http.port", 8080), ar -> {
        System.out.println("Server is ready " + ar.result().actualPort());
      });
  }
}


public class ConfigurationVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ConfigurationVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    JsonObject serverConfig = new JsonObject()
      .put("http.port", 3000)
      .put("http.host", "locahost")
      .put("http.ssl", false);
    //configuration data
    JsonObject myConfig = new JsonObject()
      .put("message", "Hello")
      .mergeIn(serverConfig);

    DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(myConfig);
    vertx.deployVerticle(new ApplicationVerticle(), deploymentOptions);
    vertx.deployVerticle(new ApplicationConfigFromFile());
  }
}
