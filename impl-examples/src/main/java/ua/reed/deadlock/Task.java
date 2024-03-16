package ua.reed.deadlock;

import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

    private final Object lockA;
    private final Object lockB;
    private final boolean deadlock;

    public Task(final Object lockA, final Object lockB, final boolean deadlock) {
        this.lockA = lockA;
        this.lockB = lockB;
        this.deadlock = deadlock;
    }

    @Override
    public void run() {
        if (deadlock) {
            System.out.printf("Deadlock mode is on for %s%n", Thread.currentThread().getName());
            synchronized (lockB) {
                System.out.printf("%s has taken lockB%n", Thread.currentThread().getName());
                synchronized (lockA) {
                    System.out.printf("%s has taken lockA%n", Thread.currentThread().getName());

                }
            }
        } else {
            System.out.printf("Deadlock mode is on for %s%n", Thread.currentThread().getName());
            synchronized (lockA) {
                System.out.printf("%s has taken lockA%n", Thread.currentThread().getName());
                synchronized (lockB) {
                    System.out.printf("%s has taken lockB%n", Thread.currentThread().getName());
                    doWork();
                }
            }
        }
    }

    private void doWork() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
