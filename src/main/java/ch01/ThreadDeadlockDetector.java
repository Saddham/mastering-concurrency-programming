package ch01;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 从JDK1.5开始java.lang.management包提供了ThreadMXBean类，该类可用获取关于线程的各种各样的信息，
 * 包括探测线程的死锁。findMonitorDeadlockedThreads()方法返回long[]，这些long[]表示发生死锁的线程的id。
 * 如果long[]数组等于null则表示没有发现死锁的线程。
 * 但这个方法只是监控object monitor的死锁，对于使用java.util.concurrent包的ownable synchronizer则无能为力。
 * 为此JDK1.6引入了一个新的方法findDeadlockedThreads()除了监控object monitor死锁外，同时还监控ownable synchronizer的死锁。
 * <p>
 * 通过建立一个定期任务，让它使用ThreadMXBean定期核查是否存在死锁的线程，就可以解决线程死锁探测的问题
 */
public class ThreadDeadlockDetector {

    //建立定期任务的调度器
    private final Timer threadCheck = new Timer("ThreadDeadLockDector", true);

    private final Collection<Listener> listeners = new CopyOnWriteArraySet<Listener>();

    private final ThreadMXBean mbean = ManagementFactory.getThreadMXBean();

    private static final int DEFAULT_DEADLOCK_CHECK_PERIOD = 10000;

    public ThreadDeadlockDetector() {
        this(DEFAULT_DEADLOCK_CHECK_PERIOD);
    }

    public ThreadDeadlockDetector(int deadlockCheckPeriod) {
        //建立一个定期核查的任务
        threadCheck.schedule(new TimerTask() {
            @Override
            public void run() {
                checkForDeadlocks();
            }

        }, 10, deadlockCheckPeriod);
    }

    //一旦返现有死锁，就发出死锁报警
    private void checkForDeadlocks() {
        long[] ids = findDeadlockedThreads();
        if (ids != null && ids.length > 0) {
            Thread[] threads = new Thread[ids.length];
            for (int i = 0; i < threads.length; i++) {
                threads[i] = findMatchingThread(mbean.getThreadInfo(ids[i]));
            }
            fireDeadlockDetected(threads);
        }

    }

    //核查是否存在死锁
    private long[] findDeadlockedThreads() {
        if (mbean.isSynchronizerUsageSupported()) {
            return mbean.findDeadlockedThreads();
        } else {
            return mbean.findMonitorDeadlockedThreads();
        }
    }

    private Thread findMatchingThread(ThreadInfo inf) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getId() == inf.getThreadId()) {
                return thread;
            }
        }

        throw new IllegalStateException("Deadlocked Thread not found");

    }

    public boolean addListener(Listener listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(Listener listener) {
        return listeners.remove(listener);
    }

    private void fireDeadlockDetected(Thread[] threads) {
        for (Listener listener : listeners) {
            listener.deallockDetected(threads);
        }
    }

    public interface Listener {
        void deallockDetected(Thread[] deadlockedThreads);
    }

}
