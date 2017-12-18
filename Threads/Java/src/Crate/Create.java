package Crate;

/**
 * There are 3 examples here.
 *
 * Create 1: extending class
 *
 * Thread Create 2: implementing interface Runnable. Note the Thread ex2 = new
 * Thread(new Create_Example2())
 *
 * Create 3: doing it directly
 *
 * @author Giliardi Schmidt
 */
public class Create {

    public static void main(String[] args) {
        Create_Example1 ex1 = new Create_Example1();
        Thread ex2 = new Thread(new Create_Example2());

        Thread ex3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("EX3: Counting... " + i);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        //should do something in here
                    }
                }
            }
        });

        Thread ex3Labda = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("EX3 Lambda: Counting... " + i);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    //should do something in here
                }
            }
        });

        //DON'T CALL THE METHOD run(). Always call start()!
        ex1.start();
        ex2.start();
        ex3.start();
        ex3Labda.start();
    }
}

class Create_Example1 extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("EX1: Counting... " + i);

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                //should do something in here
            }
        }
    }
}

class Create_Example2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("EX2: Counting... " + i);

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                //should do something in here
            }
        }
    }

}

