package Sync;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giliardi Schmidt
 */
public class LockClass {

    public static void main(String[] args) {
        new Sync_Example2().doSomething();
    }
}

/**
 * Critical zone. For example, the Thread t1 may load the value of count
 * (count'll be = 10). But then it'll lost the control of the CPU. Afer that,
 * Thread t2'll load the value of count(count'll be = 10), add 1 and store it,
 * so count'll be 11. But, when t1 gets the CPU back, for it count'll be 10,
 * then it'll add 1 and store 11. See the problem? Count'll be 11, but should be
 * 12!
 *
 * To fix it, add the keyword synchronized to the method!
 *
 * THIS'LL LOCK THE CLASS!
 *
 */
class Sync_Example2 {

    private int count;

    public void doSomething() {
        Runnable r = () -> {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();

        try {
            //Using join() is going to make the Thread running doSomething() wait for t1 and t2 to finish before continuing it's operation.
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Sync_Example2.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("EX2: Count should be 200 and it`s: " + count);
    }

    /**
     * Every object in Java has some kind of Mutex. Using the keyword
     * synchronized will make Java check if some thread is already using this
     * method.
     *
     * SYNCHRONIZED LOCKS THE CLASS!
     */
    public synchronized void increment() {
        this.count++;
    }
}
