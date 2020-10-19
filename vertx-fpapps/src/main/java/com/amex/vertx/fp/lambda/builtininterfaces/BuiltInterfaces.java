package com.amex.vertx.fp.lambda.builtininterfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BuiltInterfaces {
    public static void main(String[] args) {
        Consumer<String> consumer = null;
        consumer = name -> System.out.println(name);
        consumer.accept("Subramanain");
        consumer = System.out::println;
        consumer.accept("Subramanain");
        List<String> list = Arrays.asList("ram", "subu", "karthik");
        list.forEach(name -> System.out.println(name));
        list.forEach(System.out::println);
        list.forEach(consumer);

        //return value
        Supplier<Integer> supplier = () -> 100;
        System.out.println(supplier.get());

        //boolean use case
        Predicate<Integer> isEven = number -> number % 2 == 0;
        System.out.println(isEven.test(10));
        System.out.println(isEven.test(11));

        //transformation usecase
        Function<Integer, Integer> function = num -> num * 2;
        System.out.println(function.apply(10));

    }
}
