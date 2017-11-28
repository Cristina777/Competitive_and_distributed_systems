package ace.ucv.thread.sha;

import java.security.MessageDigest;
//import java.util.concurrent.Callable;
import java.util.*;


public class PasswordTester extends Thread {

	String myName="";     
	int intervalStart;
	int intervalEnd;
	String hash;
	//TODO: check off all the todos
	
	/**
	 * TODO: this constructor should receive an interval where to generate passwords 
	 * and the password hash 
	 * TODO: all three parameters must be saved locally just like name
	 * 
	 * @param intervalStart int
	 * @param intervalEnd int
	 * @param hash String
	 * @param name name of the PasswordTester instance
	 * @param intervalStart 
	 * @param intervalEnd 
	 * @param hash 
	 */
	public PasswordTester(String name, int intervalStart, int intervalEnd, String hash){
		myName=name;//save name locally in this class
		this.intervalStart = intervalStart;
		this.intervalEnd = intervalEnd;
		this.hash = hash;
		// at this point , name dies!!
	}
	
	
	
	//@Override
	/*public String call() throws Exception {		
		System.out.println(myName + Thread.currentThread().getId() + ": Search for the password");
		
		//TODO: all passwords from intervalStart to intervalEnd must be hashed using sha256 
		//TODO: and checked if their sha256 hash is equal to  the locally saved hash from the constructor
		
		for(int i = intervalStart; i < intervalEnd && RunMe.found==false; i++){
			if(hash.matches(sha256(i))){
				System.out.println(myName+Thread.currentThread().getId()+": i="+i);
                                RunMe.found=true;
				//break;
			}
		}
		return myName+": I found the password!!"; 
		//return null;
	}*/
	
        
	String sha256(int possiblePassword) {
		 try{
		        MessageDigest digest = MessageDigest.getInstance("SHA-256");
		        byte[] hash = digest.digest((""+possiblePassword).getBytes("UTF-8"));
		        StringBuffer hexString = new StringBuffer();

		        for (int i = 0; i < hash.length; i++) {
		            String hex = Integer.toHexString(0xff & hash[i]);
		            if(hex.length() == 1) hexString.append('0');
		            hexString.append(hex);
		        }

		        return hexString.toString();
		    } catch(Exception ex){
		       throw new RuntimeException(ex);
		    }
	}
        

    

    @Override
    public void run(){ 
    System.out.println(myName + Thread.currentThread().getId() + ": Search for the password");
		
		//TODO: all passwords from intervalStart to intervalEnd must be hashed using sha256 
		//TODO: and checked if their sha256 hash is equal to  the locally saved hash from the constructor
		
		for(int i = intervalStart; i < intervalEnd && RunMe.found==false; i++){
			if(hash.matches(sha256(i))){
				System.out.println(myName+Thread.currentThread().getId()+": i="+i);
                                RunMe.found=true;
				}
		}
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
 