package com.pibigstar.util;

import java.net.InetAddress;

/**
 * ·µ»ØIPv4µØÖ·
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
}
