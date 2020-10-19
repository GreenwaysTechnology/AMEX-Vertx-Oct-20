package com.amex.vertx.fp.lambda.refactoring;

@FunctionalInterface
interface Greeter {
    void sayGreet();
}

//parameters and args
@FunctionalInterface
interface Weclome {
    void sayHello(String name);
}

//multi parameter
@FunctionalInterface
interface Calculator {
    void add(int a, int b);
}

//return
@FunctionalInterface
interface Stock {
    int getStockValue();
}

@FunctionalInterface
interface Inventory {
    double update(double value);
}


public class LambdaRefactoring {
    public static void main(String[] args) {
        Greeter greeter = null;
        greeter = () -> {
            System.out.println("greet");
        };
        greeter.sayGreet();
        //if function has single line of body; you can remove {}
        greeter = () -> System.out.println("greet");
        greeter.sayGreet();
        /////////////////////////////////////////////////////////////////////////
        Weclome weclome = null;

        //name is args
        weclome = (String name) -> System.out.println("Name " + name);
        //Subramanian is parameter
        weclome.sayHello("Subramanain");
        //code refactoring ; parameters and args : Remove data type in args; Type inference ;
        //type of variable is understood by default.
        weclome = (name) -> System.out.println("Name " + name);
        //Subramanian is parameter
        weclome.sayHello("Subramanain");
        //if function takes single parameter; you can remove ()
        weclome = name -> System.out.println("Name " + name);
        //Subramanian is parameter
        weclome.sayHello("Subramanain");
        ////////////////////////////////////////////////////////
        Calculator calculator = null;
        calculator = (int a, int b) -> System.out.println(a + b);
        calculator.add(10, 10);
        calculator = (a, b) -> System.out.println(a + b);
        calculator.add(10, 10);
        //////////////////////////////////////////////////////////////////////////////////////
        Stock stock = null;
        stock = () -> {
            return 1000;
        };
        System.out.println(stock.getStockValue());
        //return and refactoring; if body has only return statement; remove {} and return statement
        stock = () -> 1000;
        System.out.println(stock.getStockValue());
        ///////////////////////////////////////////////////////////////////////////////////////////
        //return + args
        Inventory inventory = null;
        inventory = value -> value;
        System.out.println(inventory.update(187.89));


    }
}

