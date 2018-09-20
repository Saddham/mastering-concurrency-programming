package ch01;

public class CompositeActionWithVolatile {
    static volatile int c;

    public static void main(String[] args) throws InterruptedException {

        for (int t = 0; t < Integer.MAX_VALUE; t++) {
            c = 0;

            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    c++;
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    c++;
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("counter value = " + c);
            if (c != 2000) {
                System.exit(0);
            }
        }

    }
}
