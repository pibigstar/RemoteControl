package com.pibigstar.main;

import java.awt.AWTException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.pibigstar.thread.SendScreenThread;

/**
 * 服务端，等待控制端的接入
 * @author pibigstar
 *
 */
public class Service {

	private  DataOutputStream dos;
	private  ObjectOutputStream oos;
	private final int port = 9090; 
	public static void main(String[] args) throws IOException {
		new Service().startServer();

	}

	private void startServer(){
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			//等待客户端的连接
			Socket socket = serverSocket.accept();
			//得到客户端的输出流
			dos = new DataOutputStream(socket.getOutputStream());
			//启动发送屏幕线程，将屏幕截图发送给客户端
			SendScreenThread screenThread = new SendScreenThread(dos);
			screenThread.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
