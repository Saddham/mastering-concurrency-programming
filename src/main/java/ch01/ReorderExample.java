package ch01;

public class ReorderExample {
    private int a = 1;
    private boolean flg = true;

    public void method1 () {
        flg = false;
        a = 2;
    }

    public void method2 () {
        if (flg) {
            System.out.println("a = " + a);
        }
    }

    public static void main (String[] args) {

        for (int i = 0; i < Integer.MAX_VALUE; i++) {

            ReorderExample reorderExample = new ReorderExample();

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
