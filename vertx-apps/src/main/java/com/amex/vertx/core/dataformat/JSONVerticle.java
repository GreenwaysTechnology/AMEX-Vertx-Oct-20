package com.amex.vertx.core.dataformat;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

import java.util.ArrayList;
import java.util.List;

class Address {
  private String city;

  public Address() {
    this.city = "Coimbaotre";
  }

  public String getCity() {
    return city;
  }

  @Override
  public String toString() {
    return "Address{" +
      "city='" + city + '\'' +
      '}';
  }

  public void setCity(String city) {
    this.city = city;
  }

}

class User {
  private int id;
  private String name;

  private List<String> skills = new ArrayList<>();

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  private Address address;

  public User() {
    this.id = 1;
    this.name = "default";
    this.skills.add("java");
    this.skills.add("vertx");
    this.address = new Address();
  }

  public List<String> getSkills() {
    return skills;
  }

  public void setSkills(List<String> skills) {
    this.skills = skills;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", skills=" + skills +
      ", address=" + address +
      '}';
  }
}

public class JSONVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(JSONVerticle.class);
  }

  //create simple json object
  public void createSimpleJSON() {
    JsonObject user = new JsonObject();
    user.put("id", 1);
    user.put("name", "Subramanian");
    user.put("city", "Coimbatore");
    user.put("state", "Tamil Nadu");
    user.put("status", true);
    System.out.println(user.getString("name") + " " + user.getBoolean("status"));
    System.out.println(user.encodePrettily());
  }

  public void createJSONUsingFluentPattern() {
    JsonObject user = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("city", "Coimbatore")
      .put("state", "Tamil Nadu")
      .put("status", true);

    System.out.println(user.getString("name") + " " + user.getBoolean("status"));
    System.out.println(user.encodePrettily());
  }

  //nested json
  public void createNestedJson() {
    JsonObject user = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true)
      .put("address", new JsonObject()
        .put("city", "Coimbatore")
        .put("state", "Tamil Nadu"));

    System.out.println(user.getString("name") + " " + user.getBoolean("status"));
    System.out.println(user.encodePrettily());

  }

  //list of json object; JsonArray
  public void createListofJson() {
    JsonObject user = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true)
      .put("address", new JsonObject()
        .put("city", "Coimbatore")
        .put("state", "Tamil Nadu"));

    JsonArray users = new JsonArray()
      .add(user)
      .add(new JsonObject()
        .put("id", 1)
        .put("name", "Subramanian")
        .put("status", true)
        .put("address", new JsonObject()
          .put("city", "Coimbatore")
          .put("state", "Tamil Nadu")))
      .add(new JsonObject()
        .put("id", 1)
        .put("name", "Subramanian")
        .put("status", true)
        .put("address", new JsonObject()
          .put("city", "Coimbatore")
          .put("state", "Tamil Nadu")));
    System.out.println(users.encodePrettily());

  }

  public void createConfig(JsonObject myconfig) {
    JsonObject config = new JsonObject()
      .put("appName", "CMS")
      .put("version", "1.0.0")
      //  .put("http.port",myconfig.getInteger("http.port"));
      .mergeIn(myconfig);
    System.out.println(config.encodePrettily());
  }

  public void parse() {
    String jsonString = Json.encode(new User());
    System.out.println(jsonString);
    JsonObject jsonObject = new JsonObject(jsonString);
    System.out.println(jsonObject.encodePrettily());
    //to object
    User userObject = Json.decodeValue(jsonString,User.class);
    System.out.println(userObject);
  }


  @Override
  public void start() throws Exception {
    super.start();
    createSimpleJSON();
    createJSONUsingFluentPattern();
    createNestedJson();
    createListofJson();
    createConfig(new JsonObject().put("http.port", 8080).put("host", "amex.com"));
    parse();
  }
}
