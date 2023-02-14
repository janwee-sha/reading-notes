package chapter3;

import java.util.concurrent.TimeUnit;

/**
 * VM Args: -server
 * 当对非 volatile 变量进行读写的时候，两个线程先从内存拷贝变量到CPU缓存中。
 * 如果计算机有多个CPU，每个线程可能在不同的CPU上被处理，这意味着每个线程可以拷贝到不同的 CPU cache 中。
 * 而声明asleep为 volatile 变量时，JVM 保证了每次读 asleep 变量都从内存中读，跳过 CPU cache 这一步。
 */
public class SleepyPerson {
    private volatile boolean asleep;

    public static void main(String[] args) {
        SleepyPerson person = new SleepyPerson();
        Thread countingSheepThread = new Thread(() -> {
            while (!person.asleep) {
                countSomeSheep();
            }
        });
        Thread sleepThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            person.asleep = true;
        });
        sleepThread.start();
        countingSheepThread.start();
    }

    private static void countSomeSheep() {
        System.out.println("Sheep...");
    }
}
