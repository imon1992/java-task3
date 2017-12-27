package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
	
	private static Integer THREADS_COUNT = 3;
	
    public static void main(String[] args) {
    	App app = new App();
		app.runApp(app.getDataFromConsole());
    }

    private void runApp(ArrayList<HashMap> infoAboutPingSites) {
        CountDownLatch latch = new CountDownLatch(THREADS_COUNT);
        ExecutorService executor = Executors.newFixedThreadPool(THREADS_COUNT);

        for(int i=0; i < THREADS_COUNT; i++) {
			HashMap<String, String> siteInfo = infoAboutPingSites.get(i);
        	Processor test = new Processor(latch);
        	test.setThreadName(test.getName());
        	String siteAddress = (String) siteInfo.get("siteIpOrAddress");
        	Integer priority = Integer.parseInt(siteInfo.get("threadPriority"));
        	test.setPriority(priority);
        	test.setPingAddress(siteAddress);
            executor.submit(test);
        }

        try {
            latch.await(); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed.");
    }
    
    private ArrayList<HashMap> getDataFromConsole() {
        ArrayList<HashMap> infoAboutPingSites = new ArrayList<HashMap>();
        Validator validator = new Validator();
    	for(int i=0; i < THREADS_COUNT; i++) {
    		HashMap<String,String> siteInfo = new HashMap<String,String>();
    		
        	Scanner in = new Scanner(System.in);
            System.out.print("¬ведите адрес или IP сайта "+ i + ": ");
            String siteAddressOrIp = in.nextLine();
            System.out.print("¬ведите приоритет дл€ данного сайта: ");
            Integer priority = Integer.parseInt(in.nextLine());
            if(!validator.validateIp(siteAddressOrIp)) {
            	if(!validator.validateUrl(siteAddressOrIp)) {
            		System.out.println("Wrong address or ip format");
            		runApp(getDataFromConsole());
            	}
            }
            if(priority>10) {
            	priority = 1;
            }
            siteInfo.put("siteIpOrAddress", siteAddressOrIp);
            siteInfo.put("threadPriority", priority.toString());
            infoAboutPingSites.add(siteInfo);
    	}
    	return infoAboutPingSites;
    }
}
