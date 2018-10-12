package ch01;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceTrick6 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getName() + " get error ");
            e.printStackTrace();
        });


        Runnable task = () -> System.out.println(1 / 0);

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        //executorService.execute(() -> System.out.println(1 / 0));

        //executorService.submit(() -> System.out.println(1 / 0));


        Future<?> future = executorService.submit(task);
        future.get();

        //new Thread(task).start();

    }
}
