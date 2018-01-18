package siteparsing;

import java.util.concurrent.locks.ReentrantLock;

public class Application {
	
	private static HeadphonesFile headphones = new HeadphonesFile();
	private static int numberOfThreads = 97;
	private static String cellUrl = "http://www.cel.ro/casti/";
	private static String emagUrl = "https://www.emag.ro/casti-pc/c";
	public static boolean emagNoMorePage = false;
	public static boolean  cellNoMoreWebPage = false;

	public static void main(String[] args) {
		Thread[] threads = new Thread[numberOfThreads];
		ReentrantLock filter = new ReentrantLock();
		for(int i = 0;i< numberOfThreads;i++) {
			threads[i] = new Thread(new ParseSite(headphones, cellUrl, emagUrl,filter));
			cellUrl = nextCellPage(i + 2);
			emagUrl = nextEmagPage(i + 2);
		}
		
		for(int i = 0;i<numberOfThreads ; i++) {
			try {
                                Thread.sleep(1000);
				threads[i].start();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i = 0;i< numberOfThreads;i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Finish application");
		headphones.writeInFile();
		
		
		
	}
	
	private static String nextCellPage(int index) {
		return "http://www.cel.ro/casti/0a-" + index;
	}
	
	private static String nextEmagPage(int index) {
		return "https://www.emag.ro/casti-pc/p" + index + "/c";
	}
}
