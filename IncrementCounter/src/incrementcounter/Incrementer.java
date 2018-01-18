package incrementcounter;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import static incrementcounter.Application.counter;
import static incrementcounter.Application.x;


public class Incrementer implements Runnable{
    private int threadId ;
    public static Semaphore semaphore = new Semaphore(1,true);

    public Incrementer(int i) {
        threadId = i;
    }
    @Override
    public void run() {     
            while(counter <= x) {
                try {
                    semaphore.acquire();
                    if(counter <= x){
                        System.out.println("Thread with number " +threadId+ " has increased to : " + counter);
                        counter++;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    semaphore.release();
                }
                
            }
        }
}
