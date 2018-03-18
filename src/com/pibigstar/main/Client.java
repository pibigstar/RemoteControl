package com.pibigstar.main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.pibigstar.ui.MyJFrame;

/**
 * 客户端，接收服务端传递的信息
 * @author pibigstar
 *
 */
public class Client extends Thread{
	
	private DataInputStream dis;
	private static Socket socket;
	private MyJFrame jFrame;
	
	public static void main(String[] args) {
		Client client = new Client();
		if (client.connectService()) {
			client.showUi();
			client.start();
		}
	}
	
	private boolean connectService(){
		do {
			//String link = "192.168.56.1:9090";
			String link = JOptionPane.showInputDialog("请输入主机名和端口号");
			
			if (link.lastIndexOf(":")==-1) {
				JOptionPane.showMessageDialog(null,"主机号和端口之前请用:分割");
				break;
			}
			String host = link.substring(0, link.lastIndexOf(":"));
			String port = link.substring(link.lastIndexOf(":")+1);
			try {
				 socket = new Socket(host,Integer.parseInt(port));
				 dis = new DataInputStream(socket.getInputStream());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,"未知端口号");
				break;
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null,"未知主机号");
				break;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"网络异常连接错误");
				break;
			}
			return true;
		} while (false);
		return false;
	}
	
	private void showUi() {
		jFrame = new MyJFrame("java 远程监控");
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				int len = dis.readInt();
				byte[] data = new byte[len];
				dis.readFully(data);
				ImageIcon imageIcon = new ImageIcon(data);
				jFrame.changeImage(imageIcon);
			} catch (Exception e) {
				System.out.println("获得输入流异常");
				e.printStackTrace();
				break;
			}
		}
	}
}
