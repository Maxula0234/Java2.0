package org.levelup.lessons.lesson10;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class CounterRunnable implements Runnable {

    private final Counter counter;

    @SneakyThrows
    @Override
    public void run(){
        for (int i = 0; i < 20 ; i++) {
            counter.increase();
            Thread.sleep(200);
        }
    }
}

