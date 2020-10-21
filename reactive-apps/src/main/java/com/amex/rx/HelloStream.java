package com.amex.rx;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;

public class HelloStream {
    public static void createStream() {
        //create stream ;publisher
        //create is operator is used to create data stream.
        Observable<String> stream = Observable.create(observer -> {
            //push data .
            observer.onNext("hello");
            observer.onNext("hai");
            observer.onComplete();

        });

        //subscriber
        stream.subscribe(data -> {
            System.out.println(data);
        }, err -> {
            System.out.println(err);
        }, () -> {
            System.out.println("completed");
        });
        //sending data via events
    }

    //
    public static void justOperator() {
        //data source ; can be data sets- sequence of primitives or objects
        // list ,array,Future
        Observable<Integer> stream = Observable.just(1, 2, 3, 4, 5, 7, 8, 10)
                .filter(item -> item % 2 != 0)
                .map(item -> item * 2);
        stream.subscribe(data -> {
            System.out.println(data);
        }, err -> {
            System.out.println(err);
        }, () -> {
            System.out.println("completed");
        });
    }

    public static void listOpeator() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 8);
        Observable<Integer> stream = Observable.fromIterable(list)
                .map(i -> {
                    System.out.println("First  map is running on  " + Thread.currentThread().getName());
                    return i;
                })
                .observeOn(Schedulers.io())
                .filter(item -> {
                    System.out.println("Filter is running on  " + Thread.currentThread().getName());
                    return item % 2 != 0;
                })
                .observeOn(Schedulers.newThread())
                .map(i -> {
                    System.out.println("Second  map is running on  " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribeOn(Schedulers.computation());
        stream.subscribe(data -> {
            System.out.println(data);
        }, err -> {
            System.out.println(err);
        }, () -> {
            System.out.println("completed");
        });
    }

    public static void main(String[] args) throws InterruptedException {
        justOperator();
        listOpeator();
        Thread.sleep(1000);
    }

}
