package com.amex.vertx.fp.lambda.functionasliteral;

//function as parameter

@FunctionalInterface
interface Handler {
    void handle();
}
//function as parameter with input

@FunctionalInterface
interface HttpHandler {
    void handle(Object response);
}

//return and parameters

@FunctionalInterface
interface DatabaseHandler {
    boolean handle(Object response);
}

class SocketHandler {
    public void requestHandler(Handler handler) {
        handler.handle();
    }
}

class HttpServer {
    public void requestHandler(HttpHandler httpHandler) {
        String response = "Hello,I am handler";
        httpHandler.handle(response);
    }
}

class DatabaseServer {
    public void connect(DatabaseHandler databaseHandler) {

        boolean status = databaseHandler.handle("You got Response");
        String dbStatus = status ? "Database server is Ready" : "Database server is down";
        System.out.println(dbStatus);

    }
}

public class FunctionAsParameter {
    public static void main(String[] args) {
        SocketHandler socketHandler = new SocketHandler();
        //old style : inner class style
        socketHandler.requestHandler(new Handler() {
            @Override
            public void handle() {
                System.out.println("Socket Handler");
            }
        });
        //new style ; functional style ; lambda style ; inline function
        socketHandler.requestHandler(() -> {
            System.out.println("Socket Handler");
        });
        socketHandler.requestHandler(() -> System.out.println("Socket Handler"));
        //create function and pass the reference
        Handler handlerRef = () -> System.out.println("Socket Handler");
        socketHandler.requestHandler(handlerRef);
        //////////////////////////////////////////////////////////////////////////////////////////
        HttpServer httpServer = new HttpServer();

        httpServer.requestHandler((Object response) -> System.out.println(response));
        httpServer.requestHandler(response -> System.out.println(response));
        //function reference syntax;
        HttpHandler httpHandler = response -> System.out.println(response);
        httpServer.requestHandler(httpHandler);
        /////////////////////////////////////////////////////////////////////////////////////////
        DatabaseServer databaseServer = new DatabaseServer();
        databaseServer.connect(response -> {
            System.out.println(response);
            return true;
        });
        DatabaseHandler dbHandler = response -> {
            System.out.println(response);
            return true;
        };
        databaseServer.connect(dbHandler);

    }
}
