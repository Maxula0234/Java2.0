package org.levelup.lessons.lessons11.stop;

import lombok.SneakyThrows;

public class ProducerThread extends Thread {

    //boolean shutdown

    //t.interrupt()
    //t.isInterrupted()
    //Thread.interrupted


    @SneakyThrows
    @Override
    public void run() {
        int counter = 0;

        while (true) {
            if (Thread.interrupted()) {
                System.out.println("Поток попросили остановиться");
                if (counter > 25) {
                    System.out.println("ПОток остановился");
                    break;
                } else {
                    System.out.println("Поток не создал 25 задач, поэтому не останавливаем.");
                }
            }
            try {
                System.out.println("Generate task #" + counter++);
                Thread.sleep(700);
            } catch (InterruptedException e) {
                interrupt();
            }
        }

//        try {
//            while (!isInterrupted()){
//                System.out.println("Generate task #" + counter++);
//                Thread.sleep(700);
//            }
//        } catch (InterruptedException e) {
//            System.out.println("Поток был разбужен");
//        }


    }
}
