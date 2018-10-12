package ch01;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;


public class WaitTimeMonitoringExecutorService implements ExecutorService {

    private final ExecutorService target;

    public WaitTimeMonitoringExecutorService(ExecutorService target) {
        this.target = target;
    }


    @Override
    public <T> Future<T> submit(Callable<T> task) {
        final long startTime = System.currentTimeMillis();
        return target.submit(() -> {
                    final long queueDuration = System.currentTimeMillis() - startTime;
                    System.out.println("Task " + task + " spent " + queueDuration + "ms in queue");
                    return task.call();
                }
        );
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return submit(() -> {
            task.run();
            return result;
        });
    }

    @Override
    public Future<?> submit(Runnable task) {
        return submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                task.run();
                return null;
            }
        });
    }


    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(Runnable command) {

    }

    //...

}
