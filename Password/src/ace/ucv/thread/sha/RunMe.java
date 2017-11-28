package ace.ucv.thread.sha;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;

public class RunMe {
    public static boolean found=false;
	
	public static void main(String[] args) {
		
		int threadCount = 97;
		int start = 10000000;
		int end = 99999999;
		double step = 89999999/97;
		String password = "17AC7E4B1DF9C33D963BBC558111601A9F65A3ED79D75DEB24D020198D8B2808".toLowerCase();

		//ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		
		Collection<PasswordTester> collection = new ArrayList<PasswordTester>( );
		System.out.println(step);
		
		for(int i=0; i< threadCount; i++){			
		   PasswordTester task = new PasswordTester("Thread"+i+"_", start, (int)(start+step), password);
		   //collection.add(task);
                   task.start();
		   start += step;
		   //end += step;
		}
                
		
		/*try {
			//this is called when one of the threads returns something other than null
			// all other threads are canceled
			String f = executor.invokeAny(collection);
			System.out.println("Finished  - " + f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		executor.shutdown();*/
	}
}