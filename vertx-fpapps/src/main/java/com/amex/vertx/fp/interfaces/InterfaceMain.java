package com.amex.vertx.fp.interfaces;

interface Greeter {
    void sayGreet();
}

class GreeterImpl implements Greeter {
    @Override
    public void sayGreet() {
        System.out.println("Say Greet");
    }
}

public class InterfaceMain {
    public static void main(String[] args) {
        Greeter greeter = null;
        greeter = new GreeterImpl();
        greeter.sayGreet();
        //annonmous inner classes
        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("welcome");
            }
        };
        greeter.sayGreet();
        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("hello");
            }
        };
        greeter.sayGreet();
    }
}
