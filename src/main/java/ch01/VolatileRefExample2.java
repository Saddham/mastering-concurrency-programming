package ch01;

import java.util.concurrent.TimeUnit;

public class VolatileRefExample2 {
    private static Data data = new Data(-1, -1);

    private static class Data {
        private int a;
        private int b;

        public Data(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public synchronized void setValues(int a, int b) {
            this.a = a;
            try {
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.b = b;
        }

        public synchronized int getA() {
            return a;
        }

        public synchronized int getB() {
            return b;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            int a = i;
            int b = i;

            //writer
            Thread writerThread = new Thread(() -> {data.setValues(a, b);});

            //reader
            Thread readerThread = new Thread(() -> {
                int x = data.getA();
                int y = data.getB();

                if (x != y) {
                    System.out.printf("a = %s, b = %s%n", x, y);
                }
            });
            writerThread.start();
            readerThread.start();
            writerThread.join();
            readerThread.join();
        }
        System.out.println("finished");
    }
}
