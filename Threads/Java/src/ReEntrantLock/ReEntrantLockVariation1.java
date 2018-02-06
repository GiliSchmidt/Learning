/*
 */
package ReEntrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock a block of code. Similar to synchronized(LOCK){} keyword.
 *
 * This is a variation from ReEntrantLock
 *
 * REMEMBER TO ALWAYS USE THE TRY-CATCH-FINALLY BLOCK!
 *
 * @author Giliardi Schmidt
 */
public class ReEntrantLockVariation1 {

    public static void main(String[] args) throws InterruptedException {
        Runner2 runner = new Runner2();

        Thread t1 = new Thread(() -> runner.firstThread());
        Thread t2 = new Thread(() -> runner.secondThread());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finish();
    }

}

class Runner2 {

    private int count = 0;
    private Lock lock = new ReentrantLock();

    private void increment() {
        lock.lock();
        try {
            //locks the block of code below
            for (int i = 0; i < 1000; i++) {
                count++;
            }
        } finally {
            //unlocks it
            lock.unlock();
        }

    }

    public void firstThread() {

        increment();

    }

    public void secondThread() {
        lock.lock();

        increment();

    }

    public void finish() {
        //count must be 2000
        System.out.println("Done! Count must be 2000 and is :" + count);
    }
}
