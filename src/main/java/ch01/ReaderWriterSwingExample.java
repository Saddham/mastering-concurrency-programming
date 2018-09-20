package ch01;

import javax.swing.*;
import java.awt.*;

public class ReaderWriterSwingExample {
    static int c;

    public static void main (String[] args) {
        JFrame frame = createFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));

        final ReaderWriterSwingExample.ProgressUi writerProgressUi = new ReaderWriterSwingExample.ProgressUi("Writer Thread");
        final ReaderWriterSwingExample.ProgressUi readerProgressUi = new ReaderWriterSwingExample.ProgressUi("Reader Thread");

        frame.add(writerProgressUi.getProgressComponent());
        frame.add(readerProgressUi.getProgressComponent());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread thread1 = new Thread(() -> {
            int temp = 0;
            while (true) {
                if (temp != c) {
                    temp = c;
                    readerProgressUi.update(temp);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                c++;
                writerProgressUi.update(c);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (i == 100) {
                    i = 1;
                    c = 0;
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    private static class ProgressUi {
        private JProgressBar progressBar = new JProgressBar();

        ProgressUi (String name) {
            progressBar.setString(name);
            progressBar.setStringPainted(true);
        }

        JComponent getProgressComponent () {
            return progressBar;
        }

        void update (int c) {
            progressBar.setValue(c);
        }
    }

    private static JFrame createFrame () {
        JFrame frame = new JFrame("Visibility Demo with Share Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 80));
        return frame;
    }
}
