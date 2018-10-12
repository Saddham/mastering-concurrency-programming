package ch01;

public class Threads {
    public static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
