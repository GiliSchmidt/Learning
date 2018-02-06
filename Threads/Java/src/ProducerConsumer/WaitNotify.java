/*
 */
package ProducerConsumer;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example using the methods wait and notify when using synchronized(lock){}.
 * When a thread calls the method wait() it'll lose the lock and will wait until
 * another thread calls the method notify(). A thread that calls wait()'ll lose
 * the control of the lock immediately.
 *
 * The method notify() tells to the thread that called the method wait() that
 * they can continue their job. Calling the method notify() doesn't mean that
 * the thread'll lose the lock. See the example below.,
 *
 * REMEMBER TO USE THE SAME LOCK OBJECT!
 *
 * @author Giliardi Schmidt
 */
public class WaitNotify {
    
    public static void main(String[] args) throws InterruptedException {
        Processor processor = new Processor();
        
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

class Processor {
    
    public void produce() throws InterruptedException {
        //the lock must be the same used in consume()
        synchronized (this) {
            System.out.println("Producing...");
            Thread.sleep(2000);
            
            System.out.println("Now i'll wait =)");
            
            System.out.println("");
            
            //lose the control of the lock and wait for another thread to call
            //the method notify()
            //it's better to define a timeout using wait(TIMEOUT)
            wait();
            
            System.out.println("Resumed!");
        }
        
    }
    
    public void consume() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        Thread.sleep(2000);

        //the lock must be the same used in produce()
        synchronized (this) {
            System.out.println("Press return key...");
            input.nextLine();
            System.out.println("Return key pressed!");

            //notify the other thread that it can continue it's jog
            //but don't lose the control of the lock
            notify();
            
            System.out.println("Still locked =(");
            Thread.sleep(2000);
            
            System.out.println("");
            System.out.println("Not locked anymore!");
        }
    }
}
