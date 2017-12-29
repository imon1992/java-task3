package test;

import java.net.InetAddress;

public class PingAddress {

	public Boolean pingAddress(String adress) {
		try {
			InetAddress inet = InetAddress.getByName(adress);
			return inet.isReachable(5000);
		} catch (Exception e) {
			return false;
		}
	}
}