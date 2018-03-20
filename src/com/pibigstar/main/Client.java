package com.pibigstar.main;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
	private ObjectOutputStream oos;
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
				oos = new ObjectOutputStream(socket.getOutputStream());
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

	/**
	 * 显示界面并添加监控
	 */
	private void showUi() {
		jFrame = new MyJFrame("java 远程监控");
	
		//鼠标监听(注意：这里对JFrame监听不起作用，不知道为啥，坑。。。）
		jFrame.panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				sendEvent(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				sendEvent(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sendEvent(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				sendEvent(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				sendEvent(e);
			}
		});
		// 鼠标滚动
		jFrame.panel.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				sendEvent(e);
			}
		});

		// 键盘监听
		jFrame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				sendEvent(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				sendEvent(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				sendEvent(e);
			}
		});
	}

	/**
	 * 将事件发送到被控制端
	 * @param e
	 */
	private void sendEvent(InputEvent e) {
		try {
			oos.writeObject(e);
			oos.flush();
			System.out.println("发送事件："+e);
		} catch (IOException e1) {
			System.out.println("发送事件失败");
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
				jFrame.changeImage(imageIcon);
			} catch (Exception e) {
				System.out.println("获得输入流异常");
				break;
			}
		}
	}
}
