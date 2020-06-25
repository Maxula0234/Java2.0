package org.levelup.lessons.lessons11;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantCounter {
    //Java5
    //java.utils.concurrent.*

    private ReentrantLock lock;
    private int counter;
    public ReentrantCounter() {
        this.lock = new ReentrantLock(false);
    }
    //synchronized(this) {counter++;}

    public int increaseAndGetReentrant(){
        lock.lock();
        counter++;
        return getReentrant();
    }

    private int getReentrant(){
        try{
            return counter;
        } finally {
            lock.unlock();
        }
    }

    public void increase() {
        lock.lock(); //вход в критическую секциб (в блок, который синхронизирован)
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    public int increaseAndGet() {
        synchronized (this) {
            counter++;
            return get();
        }
    }

    public int get() {
        synchronized (this) {
            return counter;
        }
    }


}
