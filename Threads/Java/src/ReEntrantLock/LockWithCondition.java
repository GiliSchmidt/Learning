/*
 */
package ReEntrantLock;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Variaton of ReEntrantLock using an object Condition. Similar to wait() and
 * notify().
 *
 * @author Giliardi Schmidt
 */
public class LockWithCondition {

    public static void main(String[] args) throws InterruptedException {
        Runner1 runner = new Runner1();

        Thread t1 = new Thread(() -> {
            try {
                runner.firstThread();
            } catch (InterruptedException ex) {
                Logger.getLogger(LockWithCondition.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                runner.secondThread();
            } catch (InterruptedException ex) {
                Logger.getLogger(LockWithCondition.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finish();
    }

}

class Runner1 {

    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 1000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        //locks the block of code below
        lock.lock();

        System.out.println("First Thread started...");
        System.out.println("First Thread waiting...");
        System.out.println("");

        //makes this thread wait for signal()
        //similar to wait() and notify()
        condition.await();

        System.out.println("First Thread  continued =)");
        try {
            increment();
        } catch (Exception e) {

        } finally {
            System.out.println("First Thread ended =(");

            //unlocks it
            //MUST BE CALLED ALWAYS
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();

        System.out.println("Second Thread Started");
        System.out.println("Press return key...");

        new Scanner(System.in).nextLine();

        System.out.println("Ok! Return key pressed");
        System.out.println("");

        //this won't unlock the Lock, but must be called to continue the
        //execution of the other thread
        condition.signal();
        try {
            increment();
        } catch (Exception e) {

        } finally {
            System.out.println("Second Thread ended =(");
            System.out.println("");
            lock.unlock();
        }
    }

    public void finish() {
        System.out.println("");
        //count must be 2000

        System.out.println("Done! Count must be 2000 and is :" + count);
    }
}
