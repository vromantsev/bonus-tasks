package ua.reed.deadlock;

public class TaskRunner {
    public static void main(String[] args) {
        final Object lockA = new Object();
        final Object lockB = new Object();
        var t1 = new Thread(new Task(lockA, lockB, false));
        var t2 = new Thread(new Task(lockA, lockB, true));

        t1.start();
        t2.start();
    }
}
