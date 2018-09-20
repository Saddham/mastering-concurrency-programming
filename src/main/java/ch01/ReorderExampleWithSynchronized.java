package ch01;

public class ReorderExampleWithSynchronized {
    private int a = 1;
    private boolean flg = true;

    public synchronized void method1 () {
        flg = false;
        a = 2;
    }

    public synchronized void method2 () {
        if (flg) {
            System.out.println("a = " + a);
        }
    }

    public static void main (String[] args) {

        for (int i = 0; i < Integer.MAX_VALUE; i++) {

            ReorderExampleWithSynchronized reorderExample =
                    new ReorderExampleWithSynchronized();

            Thread thread1 = new Thread(() -> {
                reorderExample.method1();
            });

            Thread thread2 = new Thread(() -> {
                reorderExample.method2();
            });

            thread1.start();
            thread2.start();
        }
    }
}
