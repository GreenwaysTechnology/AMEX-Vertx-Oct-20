package com.amex.vertx.fp.lambdarules;

//functional interface
@FunctionalInterface
interface Greeter {
    static void doSomthingElse() {
        System.out.println("static method");
    }

    //abstract method
    void sayGreet();

    //void sayHai();
    default void doSomething() {
        System.out.println("default method");
    }
}

public class LambdaRulesMain {
    public static void main(String[] args) {
        Greeter greeter = null;

        //inner class syntax
//        greeter = new Greeter() {
//            @Override
//            public void sayGreet() {
//                System.out.println("greet - inner class");
//            }
//        };
//        greeter.sayGreet();
        //lambda version
        greeter = () -> {
            System.out.println("greet - lambda version");
        };
        greeter.sayGreet();
        greeter.doSomething();
        Greeter.doSomthingElse();


    }
}
