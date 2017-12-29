package test;

import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;

class Processor extends Thread {
	private static Integer MINUTES = 1;
	private static Integer ITERATION_INTERVAL_IN_SECONDS = 5;

	private CountDownLatch latch;
	private String threadName;
	private transient String addressToPing;

	public Processor(CountDownLatch latch) {
		this.latch = latch;
	}

	public void run() {
		int iterationCount = (MINUTES * 60) / ITERATION_INTERVAL_IN_SECONDS;
		PingAddress pingAddress = new PingAddress();

		for (int i = 0; i < iterationCount; i++) {
			try {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				addressToPing = deleteProtocol(addressToPing);
				Boolean pingResult = pingAddress.pingAddress(addressToPing);
				String result = threadName + " --- " + "TimeStamp: " + timestamp + " --- Status: "
						+ pingResult.toString() + "--- Host " + addressToPing;
				System.out.println(result);
				Thread.sleep(ITERATION_INTERVAL_IN_SECONDS * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		latch.countDown();
	}

	private String deleteProtocol(String siteAddress) {
		return siteAddress.replaceAll("^(\\w+):\\/\\/", "");
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public void setPingAddress(String siteAddress) {
		this.addressToPing = siteAddress;
	}
}