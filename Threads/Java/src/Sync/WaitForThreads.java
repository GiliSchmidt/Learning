/*
 */
package Sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CountDownLatch in Java is a type of synchronizer which allows one Thread to
 * wait for one or more Threads before it starts processing.
 *
 * In this case the CountDownLatch created waits for 3 Threads to call
 * latch.countDown(). For example, if you change the number passed by parameter
 * when instanciating the CountDownLatch to anything higher than 3 the execution
 * of the Main Thread WILL NEVER CONTINUE, because here we are instanciating
 * only 3 Threads that call latch.countDown().
 *
 * If you change the number passed by parameter when instanciating the
 * CountDownLatch to anything lower than 3, for example 2, the execution of the
 * Main Thread WILL CONTINUE AFTER ONLY 2 THREADS CALL latch.countDown(). So the
 * Main Thread won't wait for the last Thread to finish its execution.
 *
 * One object of CountDownLatch is not reusable. When it's count reaches zero
 * it's useless.
 *
 * You may use latch.await() in many classes as you want.
 *
 * @author Giliardi Schmidt
 */
public class WaitForThreads {

    public static void main(String[] args) {
        //define the number of times that latch.countDown() must be called before 
        //the Thread that needs to wait (Thread using latch.await) stop waiting
        //in this case it's 3
        CountDownLatch latch = new CountDownLatch(3);

        //create a pool of threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("Creating pool of threads");
        for (int i = 0; i < 3; i++) {
            executor.submit(new Processor(latch));
        }

        try {
            //latch.await() makes this Thread wait for the latch.countDown() to
            //be called for the number of times specified when instanciating 
            //CountDownLatch latch = new CountDownLatch(3)
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(WaitForThreads.class.getName()).log(Level.SEVERE, null, ex);
        }

        //only'll print this after the number specified when instanciating 
        //the CountDownLatch is equals to zero
        System.out.println("Completed.");
    }

}

class Processor implements Runnable {

    //no need to use volatile because CountDownLatch is thread safe =)
    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Thread Started");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(WaitForThreads.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Thread Completed");

        //decrement by one the number specified at CountDownLatch latch = new CountDownLatch(3)
        latch.countDown();

    }

}
