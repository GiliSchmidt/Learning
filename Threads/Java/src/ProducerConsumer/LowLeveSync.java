/*
 */
package ProducerConsumer;

import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example using low-level synchronization.
 *
 *
 * @author Giliardi Schmidt
 */
public class LowLeveSync {

    public static void main(String[] args) throws InterruptedException {
        Processor2 processor = new Processor2();

        Thread t1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException ex) {
                Logger.getLogger(WaitNotify.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException ex) {
                Logger.getLogger(WaitNotify.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}

class Processor2 {

    private LinkedList<Integer> list = new LinkedList();
    private final int LIMIT = 10;
    //lock to be used
    private Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;

        while (true) {
            //USE THE LOCK OBJECT!
            synchronized (lock) {
                /**
                 * use while because when the thread wakes up , the while loop
                 * wont let the code continue as long as the condition is true
                 * (continuous checking). On the other hand, the if wont even
                 * check if the codition is verified !!
                 */
                while (list.size() == LIMIT) {
                    //USE THE LOCK OBJECT!
                    lock.wait();
                }

                list.add(value++);

                //USE THE LOCK OBJECT!
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();

        while (true) {
            //USE THE LOCK OBJECT!
            synchronized (lock) {
                /**
                 * use while because when the thread wakes up , the while loop
                 * wont let the code continue as long as the condition is true
                 * (continuous checking). On the other hand, the if wont even
                 * check if the codition is verified !!
                 */
                while (list.size() == 0) {
                    //USE THE LOCK OBJECT!
                    lock.wait();
                }

                System.out.println("List size: " + list.size()
                        + "; Taking out value : " + list.removeFirst());

                //USE THE LOCK OBJECT!
                lock.notify();
            }
            Thread.sleep(random.nextInt(1000));
        }
    }
}
