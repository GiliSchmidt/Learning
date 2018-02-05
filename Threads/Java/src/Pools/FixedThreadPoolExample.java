/*
 */
package Pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Mannaging a group of reusable Threads. Please read the documentation for ExecutorServuce abd Executors.newFixedThreadPool.
 * 
 * @author Giliardi Schmidt
 */
public class FixedThreadPoolExample {

    public static void main(String[] args) {
        //creates a Thread pool of size n
        //only n threads'll be created and they'll be reusable
        //note when running the code that only 2 Processors'll be running at the same time, in this example
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executor.submit(new Processor(i));
        }

        //use this before awaitTermination(5, TimeUnit.MINUTES) to block the creation of new tasks
        executor.shutdown();
        System.out.println("---------------------");
        System.out.println("Tasks sumbitted.");
        System.out.println("---------------------");

        try {
            //use this to make this Thread (main method) wait for the taskt to complete.
            //specify the timeout
            executor.awaitTermination(5, TimeUnit.MINUTES);
            System.out.println("---------------------");
            System.out.println("Tasks executed.");
        } catch (InterruptedException ex) {
            Logger.getLogger(FixedThreadPoolExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Processor implements Runnable {

    private int id;

    public Processor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Staring Thread: " + this.id);

        try {
            //simulate a block of code
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Completed Thread: " + this.id);
    }

}
