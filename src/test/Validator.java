package test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

public class Validator {
	public Boolean validateIp(String ip) {
		Pattern patern = Pattern.compile(
		        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
		    return patern.matcher(ip).matches();
	}
	
	public boolean validateUrl(String url) {  
	    URL u = null;
	    
	    try {  
	        u = new URL(url);  
	    } catch (MalformedURLException e) {  
	        return false;  
	    }

	    try {  
	        u.toURI();  
	    } catch (URISyntaxException e) {  
	        return false;  
	    }  

	    return true;  
	}
}
