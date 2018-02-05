package Sync;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giliardi Schmidt
 */
public class LockBlockOfCode {

    public static void main(String[] args) {
        new Worker().main();
    }

}

/**
 * Please read the description of the class (Example) LockClass, the principle is the
 * same. The difference is that using the word synchronized in the method
 * signature it'll lock the entire class, so one Thread wouldn't be able to run
 * stageTwo() if another Thread was running stageOne(). One Thread would need to
 * wait the other finish. In the end, the total time take would be 4000 (in this
 * example). When using the word synchronized for a block of code, one Thread is
 * able to run stageOne() whilhe another is running stageTwo() because there'll
 * be different locks for each block of code.
 *
 * You could use the word volatile for each list, but it's recommended to
 * declare and use different locks.
 *
 * THIS LOCKS A BLOCK OF CODE!
 *
 * @author Giliardi Schmidt
 */
class Worker{

    //locks that`ll be checked inside synchronized(LOCK){}
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private Random random;
    private List<Integer> list1;
    private List<Integer> list2;

    Worker() {
        this.list2 = new ArrayList();
        this.list1 = new ArrayList();
        this.random = new Random();
    }

    /**
     * Adds a number to the list1.
     */
    public void stageOne() {
        //locking the block of code!
        //note the different lock when compared to method stageTwo()
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.list1.add(random.nextInt(100));
        }
    }

    /**
     * Adds a number to the list2.
     */
    public void stageTwo() {
        //locking the block of code!
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.list2.add(random.nextInt(100));
        }
    }

    public void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void main() {
        System.out.println("Starting...");
        Thread t1 = new Thread(() -> process());
        Thread t2 = new Thread(() -> process());

        long start = System.currentTimeMillis();
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }

        //because of the Thread.sleep(), the time taken should be around 2000ms
        //should be the same time taken by only one Thread calling process
        System.out.println("Finished:\n"
                + "Time taken: " + (System.currentTimeMillis() - start) + "\n"
                + "List1 " + list1.size() + " List2: " + list2.size());

    }
}
