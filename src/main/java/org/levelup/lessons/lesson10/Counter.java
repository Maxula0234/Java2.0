package org.levelup.lessons.lesson10;

//ThreadSage(потокобезопасность)
// Критическая секция - это участок кода, который может выполнять только один поток в единый момент времени!
// synchronized - это описание блока кода
// synchronized method - здесь блокировка берется по this.
// synchronized block

public class Counter {
    private int counter; //Счетчик каких-то вычислений или вызовов

    public void increase() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " значение в методе increase: " + counter);
            counter++;
            System.out.println(Thread.currentThread().getName()+", значение счетчика: " + counter);

        }
    }

    public synchronized int getCounter() {
        return counter;
    }
}
