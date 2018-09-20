package ch01;

public class CompositeActionWithSynchronized {
    static int c;

    public synchronized static void increase(){
        c++;
    }
    public static void main (String[] args) throws InterruptedException {

        for (int t = 0; t < 10; t++) {
            c = 0;

            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    increase();
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    increase();
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("counter value = " + c);
        }

    }
}
