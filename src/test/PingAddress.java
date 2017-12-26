package test;

import java.net.InetAddress;

public class PingAddress {
	  
	  public Boolean pingAddress(String adress) {
		    try {
			      InetAddress inet = InetAddress.getByName(adress);
			      return inet.isReachable(5000);
//			      if (inet.isReachable(5000)){
//			        return true;
//			      }
			    } catch ( Exception e ) {
			    	return false;
			    }
//		    return false;
	  }
}


