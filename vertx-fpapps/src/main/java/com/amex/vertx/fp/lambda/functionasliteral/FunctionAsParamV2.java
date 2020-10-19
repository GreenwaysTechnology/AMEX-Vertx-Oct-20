package com.amex.vertx.fp.lambda.functionasliteral;

interface Resolve {
    void resolve(Object response);
}

interface Reject {
    void reject(Throwable error);
}

class Login {
    public void validate(Resolve res, Reject rej) {
        String userName = "admin";
        String password = "admin";
        if (userName.equals("admin") && password.equals("admin")) {
            res.resolve("Login Success");
        } else {
            rej.reject(new RuntimeException("Login Failed"));
        }

    }
}


public class FunctionAsParamV2 {
    public static void main(String[] args) {
        Login login = new Login();
        login.validate(response -> {
            System.out.println(response);
        }, error -> {
            System.out.println(error.getMessage());
        });
        login.validate(response -> System.out.println(response), error -> System.out.println(error.getMessage()));

    }
}
