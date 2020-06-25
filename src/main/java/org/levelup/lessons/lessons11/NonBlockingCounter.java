package org.levelup.lessons.lessons11;

import java.util.concurrent.atomic.AtomicInteger;

//Блокирующие алгоритмы - алгоритмы, в которыз используется блокировка
// Неблокирующие алгоритмы
public class NonBlockingCounter {

    private int counter;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public void increase(){
        //CAS - compare and set
        atomicInteger.incrementAndGet();
    }
}
