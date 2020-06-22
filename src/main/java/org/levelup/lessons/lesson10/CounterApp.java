package org.levelup.lessons.lesson10;


import lombok.SneakyThrows;

public class CounterApp {

    @SneakyThrows
    public static void main(String[] args) {
        Counter c1 = new Counter();
        Counter c2 = new Counter();

        Thread t1 = new Thread(new CounterRunnable(c1), "Thread 1");
        Thread t2 = new Thread(new CounterRunnable(c2), "Thread 2");
        Thread t3 = new Thread(new CounterRunnable(c2), "Thread 3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("c1.value: " + c1.getCounter());
        System.out.println("c2.value: " + c2.getCounter());

        int  sum =  c1.getCounter() + c2.getCounter();
        System.out.println("value: " +  sum);
    }
}
