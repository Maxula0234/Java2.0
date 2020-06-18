package org.levelup.lessons.lesson9;

import lombok.SneakyThrows;

public class Threads {
    //class extends THread
    // class implements Runnable
    // since  5 java - classic implements Callable


    public static void main(String[] args) throws InterruptedException {
        Thread background = new Thread(new SleepingRunnable());
        background.setDaemon(true);
        background.start();

        SoutThread t = new SoutThread();
//        t.run(); // простой вызов метода run не создает поток, а просто запускает метод run в текущем потоке
        t.start();// запуск нового потока
        t.join();


        //t.stop -- никогда не останавливать так
        System.out.println(Thread.currentThread().getName() + ": In main method");
    }

    //main thread - поток в котором запускается метод main(String[] args)
    static class SoutThread extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": I love cincurrency...");
            Thread.sleep(5000);
            System.out.println("the end");
        }
    }

    static class SleepingRunnable implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " wake up");
                Thread.sleep(1000);
            }
        }
    }


}

