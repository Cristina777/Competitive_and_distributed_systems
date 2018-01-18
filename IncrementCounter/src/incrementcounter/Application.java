package incrementcounter;
import java.util.concurrent.locks.ReentrantLock;
import java.io.IOException;
import java.lang.Thread;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Application {

    public static int counter = 0;
    public final static int x = 97;
   
    
    public static void main(String[] args) {
       /* Thread one = new Thread(new IncrementCounter(), "One");
        Thread two = new Thread(new IncrementCounter(), "Two");
        Thread three = new Thread(new IncrementCounter(), "Three");
        Thread four = new Thread(new IncrementCounter(), "Four");*/
       Thread[] threads = new Thread[4];
       
       for(int i = 0;i< 4;i++){
           threads[i] = new Thread(new Incrementer(i));
           threads[i].start();
       }      
        for(int i = 0;i<4;i++){
           try {
               threads[i].join();
           } catch (InterruptedException ex) {
               Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }
}  

