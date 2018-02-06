/*
 */
package ProducerConsumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread safe ArrayList. Use this when some Threads produce something and
 * others consume. For example, in this example the method produce() puts random
 * integers in the BlockingQueue<Integer> queue and the method consume takes
 * those numbers.
 *
 *
 * For more data structures that are thread safe see the JavaDoc.
 *
 * @author Giliardi Schmidt
 */
public class ProducerConsumer {

    //creates a thread safe ArrayList
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                produce();
            } catch (InterruptedException ex) {
                Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                consume();
            } catch (InterruptedException ex) {
                Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static void produce() throws InterruptedException {
        while (true) {
            //put one random integer
            //if the queue is full it'll wait for an empty space
            queue.put(random.nextInt(100));
        }

    }

    public static void consume() throws InterruptedException {
        while (true) {
            Thread.sleep(100);

            if (random.nextInt(10) == 0) {
                //take one integer
                //if the queue is empty it'll wait for something to be add to the queue
                int aux = queue.take();
                System.out.println("Taking value " + aux + ". Queue size: " + queue.size());
            }
        }
    }
}
