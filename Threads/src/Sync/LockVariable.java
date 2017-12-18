package Sync;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giliardi Schmidt
 */
public class LockVariable {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Sync_Example1 ex1 = new Sync_Example1();
        ex1.start();

        //it'll make the while stop
        in.nextLine();

        ex1.shutdown();
    }

}

/**
 * For some reason Java sometimes optimize the code in a way that it may not
 * always check if the value of a variable has changed. The value of the
 * attribute may be cached. See the example below. Java may not always check, in
 * the while(running), if the value of running has changed.
 *
 * To fix it, add the keyword volatile to the attribute!
 *
 * THIS'LL LOCK THE VARIABLE!
 *
 */
class Sync_Example1 extends Thread {

    //add the modifier volatile, so Java`ll always check the value of running at the while(running)!
    //locks the variable!
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("EX1: I'm running!");

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sync_Example1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }

}
