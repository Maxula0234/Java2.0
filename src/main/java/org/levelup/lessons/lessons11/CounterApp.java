package org.levelup.lessons.lessons11;

public class
CounterApp {
    public static void main(String[] args) {
        ReentrantCounter counter = new ReentrantCounter();

        for (int k = 0; k < 3; k++) {
            new Thread(() -> {
                for (int i = 0; i < 20; i++) {
                    System.err.println(Thread.currentThread().getName() + " " + counter.increaseAndGetReentrant());

                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException exc){
                        throw new RuntimeException();
                    }
                }
            }).start();
        }
    }
}
