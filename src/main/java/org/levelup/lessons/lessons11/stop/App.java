package org.levelup.lessons.lessons11.stop;

import lombok.SneakyThrows;
import org.w3c.dom.ls.LSOutput;

import java.beans.PropertyDescriptor;

public class App {

    //Thread states
    // NEW - поток создан, но еще не запущен
    // RUNNABLE - Поток запуще, готов выполниться
    // RUNNING - Поток запущен, выполняется
    // BLOCKED - Поток прерван, ожидает доступа к ресурсу (ожидает монитор)
    // WAITING - поток прерван, ожидает события которое разбудит этот поток
    //TIMED_WAITING - поток прерван, котороое разбудит этот поток, ждет ограниченное время
    // TERMINATED - Поток завершен

    @SneakyThrows
    public static void main(String[] args) throws InterruptedException {
        ProducerThread producerThread = new ProducerThread();
        System.out.println("До вызова метода start() " + producerThread.getState().name());
        producerThread.start();

        Thread.sleep(100);
        System.out.println("Просим поток остановиться");
        producerThread.interrupt(); //shutdown = true

        Thread.sleep(2000);
        System.out.println("Просим поток остановиться во второй раз!");
        producerThread.interrupt(); //shutdown = true
    }

}
