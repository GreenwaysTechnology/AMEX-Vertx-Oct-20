package com.amex.vertx.web.jdbc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class JDBCVerticle extends AbstractVerticle {
  private static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS products(id INT IDENTITY, name VARCHAR(255), price FLOAT, weight INT)";
  private static String INITAL_TABLE_DATA = "INSERT INTO products (name, price, weight) VALUES ('Egg Whisk', 3.99, 150), ('Tea Cosy', 5.99, 100), ('Spatula', 1.00, 80)";
  private static String GET_ALL_PRODUCTS = "SELECT id, name, price, weight FROM products";

  private JDBCClient jdbcClient;

  public static void main(String[] args) {
    Runner.runExample(JDBCVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //connection string
    JsonObject jdbcConfig = new JsonObject()
      .put("url", "jdbc:hsqldb:mem:test?shutdown=true")
      .put("driver_class", "org.hsqldb.jdbcDriver");
    //connect database
    //create jdbc bridge layer
    jdbcClient = JDBCClient.createShared(vertx, jdbcConfig);

    setUpInitialData(ready -> {

      Router router = Router.router(vertx);
      ////Middleware injection /filter /interceptor ; // to json string from client and convert
      //      //into json object : body parsers
      router.route().handler(BodyHandler.create());

      //filter
      router.route("/greet").handler(routingContext -> {
        System.out.println(routingContext.request().uri() + " ");
        routingContext.put("message","Hello");
        routingContext.next();
      });
      router.get("/greet").handler(routingContext -> {

        routingContext.response().end("greeting" + routingContext.get("message"));
      });
      /////////////////////////////////////////////////////////////////////////////////////
      //filter for products url
      router.route("/products*").handler(routingContext ->
        jdbcClient.getConnection(res -> {
          if (res.failed()) {
            routingContext.fail(res.cause());
          } else {
            SQLConnection conn = res.result();
            // save the connection on the context
            routingContext.put("conn", conn);
            // we need to return the connection back to the jdbc pool. In order to do that we need to close it, to keep
            // the remaining code readable one can add a headers end handler to close the connection.
            routingContext.addHeadersEndHandler(done -> conn.close(v -> {
              System.out.println(v.succeeded() ? "Connection Closed" : "Connection not closed");
            }));
            routingContext.next();
          }
        })).failureHandler(routingContext -> {
        SQLConnection conn = routingContext.get("conn");
        if (conn != null) {
          conn.close(v -> {
            System.out.println(v.succeeded() ? "Connection Closed" : "Connection not closed");
          });
        }
      });

      router.get("/products").handler(this::handleListProducts);


      vertx.createHttpServer().requestHandler(router).listen(3000, httpServerAsyncResult -> {
        if (httpServerAsyncResult.succeeded()) {
          System.out.println(httpServerAsyncResult.result().actualPort());
        }
      });

    });


  }

  private void handleListProducts(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    SQLConnection conn = routingContext.get("conn");
    conn.query(GET_ALL_PRODUCTS, query -> {
      if (query.failed()) {
        sendError(500, response);
      } else {
        JsonArray arr = new JsonArray();
        query.result().getRows().forEach(arr::add);
        routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily());
      }
    });
  }

  private void sendError(int statusCode, HttpServerResponse response) {
    response.setStatusCode(statusCode).end();
  }

  private void setUpInitialData(Handler<Void> done) {

    jdbcClient.getConnection(res -> {
      if (res.failed()) {
        throw new RuntimeException(res.cause());
      }
      final SQLConnection conn = res.result();

      //execute to create table
      conn.execute(CREATE_TABLE, ddl -> {
        if (ddl.failed()) {
          throw new RuntimeException(ddl.cause());
        }

        //insert inital rows
        conn.execute(INITAL_TABLE_DATA, fixtures -> {
          if (fixtures.failed()) {
            throw new RuntimeException(fixtures.cause());
          }
          done.handle(null);
        });
      });
    });

  }
}
