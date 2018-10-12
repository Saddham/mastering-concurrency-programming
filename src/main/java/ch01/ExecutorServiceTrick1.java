package ch01;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTrick1 {

    //trick 1 Name pool threads
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("Orders-%d")
            .setDaemon(true)
            .build();
    final ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory);


    public static void main(String[] args) throws InterruptedException {
        ExecutorServiceTrick1 demo = new ExecutorServiceTrick1();

        int messageId = 1;
        while (!Thread.interrupted() && messageId < 100) {
            demo.process(messageId++);
            //Threads.sleep(100);
        }

        demo.shutdownGraceful();
    }

    //trick 3 Explicit and safe shutdown
    private void shutdownGraceful() throws InterruptedException {
        /*
        executorService.shutdown();
        final boolean done = executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("All message were sent so far? " + done);
        */


        final List<Runnable> rejected = executorService.shutdownNow();
        System.out.println("Rejected tasks: " + rejected.size());
    }


    // trick 2 Switch names according to context
    private void process(int messageId) {
        executorService.submit(() -> {
            final Thread currentThread = Thread.currentThread();
            final String oldName = currentThread.getName();
            currentThread.setName(oldName + "-Processing-" + messageId);
            try {
                Threads.sleep(new Random().nextInt(1000));
                //real logic here...
            } finally {
                currentThread.setName(oldName);
            }
        });
    }
}
