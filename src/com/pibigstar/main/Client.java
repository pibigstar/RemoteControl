package com.pibigstar.main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;

/**
 * 客户端，接收服务端传递的信息
 * @author pibigstar
 *
 */
public class Client extends Thread{
	
	private DataInputStream dis;
	private Socket socket;
	
	public static void main(String[] args) {
		
	}
	
	private boolean connectService(String host,int port) {
		try {
			socket = new Socket(host, port);
			//得到服务端的输入流
			dis = new DataInputStream(socket.getInputStream());
			
			return true;
		} catch (IOException e) {
			System.out.println("连接"+host+"服务器端失败");
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				int len = dis.readInt();
				byte[] data = new byte[len];
				dis.readFully(data);
				
				ImageIcon imageIcon = new ImageIcon(data);
				
			} catch (Exception e) {
				System.out.println("获得输入流异常");
				e.printStackTrace();
			}
			
		}
	}

}
