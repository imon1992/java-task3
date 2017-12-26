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

//    	app.getDataFromConsole();
    	while(true) {
    		app.runApp(app.getDataFromConsole());
    	}
    	
//        CountDownLatch latch = new CountDownLatch(THREADS_COUNT); // coundown from 3 to 0
//
//        ExecutorService executor = Executors.newFixedThreadPool(THREADS_COUNT); // 3 Threads in pool
//
//        for(int i=0; i < THREADS_COUNT; i++) {
//        	Processor test = new Processor(latch);
//        	test.setThreadName(test.getName());
//            executor.submit(test); // ref to latch. each time call new Processes latch will count down by 1
//        }
//
//        try {
//            latch.await();  // wait until latch counted down to 0
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Completed.");
    }

    private void runApp(ArrayList<HashMap> infoAboutPingSites) {
        CountDownLatch latch = new CountDownLatch(THREADS_COUNT); // coundown from 3 to 0

        ExecutorService executor = Executors.newFixedThreadPool(THREADS_COUNT); // 3 Threads in pool

        for(int i=0; i < THREADS_COUNT; i++) {
			HashMap siteInfo = infoAboutPingSites.get(i);
        	Processor test = new Processor(latch);
        	test.setThreadName(test.getName());
        	String siteAddress = (String) siteInfo.get("siteIpOrAddress");
//System.out.println(siteInfo.get(siteAddress));
        	test.setPingAddress(siteAddress);
            executor.submit(test); // ref to latch. each time call new Processes latch will count down by 1
        }

        try {
            latch.await();  // wait until latch counted down to 0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed.");
    }
    
    private ArrayList<HashMap> getDataFromConsole() {
        ArrayList<HashMap> infoAboutPingSites = new ArrayList<HashMap>();
    	for(int i=0; i < THREADS_COUNT; i++) {
    		HashMap<String,String> siteInfo = new HashMap<String,String>();
    		
        	Scanner in = new Scanner(System.in);
            System.out.print("¬ведите адрес или IP сайта: ");
            String name = in.nextLine();
            System.out.print("¬ведите приоритет дл€ данного сайта: ");
            String priority = in.nextLine();
            siteInfo.put("siteIpOrAddress", name);
            siteInfo.put("threadPriority", priority);
            infoAboutPingSites.add(siteInfo);
    	}
    	return infoAboutPingSites;
    }
}
