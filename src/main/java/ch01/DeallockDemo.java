package ch01;

public class DeallockDemo {
    public static void main(String[] args) {

        new ThreadDeadlockDetector(5).addListener(deadlockedThreads -> {
            for (Thread thread : deadlockedThreads) {
                System.out.println(thread.getName() + " deadlocked.");
            }
            if (deadlockedThreads.length > 0) {
                System.exit(1);
            }
        });

        TreeNode parent = new TreeNode();
        TreeNode child = new TreeNode();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            System.out.println(i+" iterator");
            Thread thread1 = new Thread(() -> parent.addChild(child));
            thread1.setName("parent addChild");
            Thread thread2 = new Thread(() -> child.setParent(parent));
            thread2.setName("child setParent");
            thread1.start();
            thread2.start();
        }


    }
}
