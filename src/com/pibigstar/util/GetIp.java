package com.pibigstar.util;

import java.net.InetAddress;

/**
 * 返回IPv4地址和主机名
 * @author pibigstar
 *
 */
public class GetIp {
	@SuppressWarnings("static-access")
	public static String getHostIp() {
		InetAddress ia=null;
		String localip = null;
		try {
			ia=ia.getLocalHost();
			localip =ia.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localip;
	}
	public static String getHostName() {
		InetAddress ia=null;
		String name = null;
		try {
			ia=ia.getLocalHost();
			name =ia.getHostName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public static void main(String[] args) {
		System.out.println(getHostName());
	}
}
