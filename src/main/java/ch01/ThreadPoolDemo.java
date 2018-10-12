package ch01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
        }

        ExecutorService threadPool1 = Executors.newFixedThreadPool(10);
        ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
        while (!Thread.interrupted()) {
            Runnable task = () -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            threadPool1.submit(task);
            threadPool2.submit(task);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadPool1.shutdown();
        threadPool2.shutdown();


    }

}
