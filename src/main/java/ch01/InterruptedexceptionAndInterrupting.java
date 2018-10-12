package ch01;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InterruptedexceptionAndInterrupting {
    static class Cleaner implements Runnable {

        Cleaner() {
            final Thread cleanerThread = new Thread(this, "Cleaner");
            cleanerThread.start();
        }

        @Override
        public void run() {
            while(true) {
                cleanUp();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private void cleanUp() {
            //...

            System.out.println("cleanup");
        }
    }


    public static void main(String[] args) {
        Executors.newSingleThreadScheduledExecutor().schedule(new Cleaner(),1,TimeUnit.SECONDS);
    }

}
