package com.amex.vertx.fp.methodreference;


import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Printer {
    void print(String message);
}

@FunctionalInterface
interface UpperCase {
    String convertToUpper(String message);
}

class MicroTask {
    public static void startMicroTaskStatic() {
        System.out.println(Thread.currentThread().getName());
    }

    public void startMicroTask() {
        System.out.println(Thread.currentThread().getName());
    }
}


class Task {

    private void startMicroTask() {
        System.out.println(Thread.currentThread().getName());
    }

    public void startTask() {
        Thread thread = null;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread.start();
        thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        thread.start();
        //isloate thread logic
        thread = new Thread(() -> this.startMicroTask());
        thread.start();
        //method reference syntax; if i dont want lambda itself
        thread = new Thread(this::startMicroTask);
        thread.start();
        //using lambda
        MicroTask microTask = new MicroTask();
        thread = new Thread(() -> microTask.startMicroTask());
        thread.start();
        thread = new Thread(microTask::startMicroTask);
        thread.start();
        thread = new Thread(MicroTask::startMicroTaskStatic);
        thread.start();

    }
}


public class MethodReferenceMain {
    public static void main(String[] args) {
        Task task = new Task();
        task.startTask();
        //printer and method reference
        Printer printer = null;

        printer = message -> System.out.println(message);
        printer.print("hello");
        printer = System.out::println;
        printer.print("hello");

        UpperCase upperCase = null;
        upperCase = name -> name.toUpperCase();
        System.out.println(upperCase.convertToUpper("subramanian"));
        upperCase = String::toUpperCase;
        System.out.println(upperCase.convertToUpper("subramanian"));

        List<String> list = Arrays.asList("ram", "subu", "karthik");
        list.forEach(name -> System.out.println(name));
        list.forEach(System.out::println);

    }
}
