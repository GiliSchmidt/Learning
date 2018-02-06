/*
 */
package ReEntrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock a block of code. Similar to synchronized(LOCK){} keyword.
 *
 * REMEMBER TO ALWAYS USE THE TRY-CATCH-FINALLY BLOCK!
 *
 * @author Giliardi Schmidt
 */
public class ReEntrantLock {

    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();

        Thread t1 = new Thread(() -> runner.firstThread());
        Thread t2 = new Thread(() -> runner.secondThread());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finish();
    }

}

class Runner {

    private int count = 0;
    private Lock lock = new ReentrantLock();

    private void increment() {
        for (int i = 0; i < 1000; i++) {
            count++;
        }
    }

    public void firstThread() {
        //locks the block of code below
        lock.lock();

        try {
            increment();
        } catch (Exception e) {

        } finally {
            //unlocks it
            lock.unlock();
        }
    }

    public void secondThread() {
        lock.lock();

        try {
            increment();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public void finish() {
        //count must be 2000
        System.out.println("Done! Count must be 2000 and is :" + count);
    }
}
