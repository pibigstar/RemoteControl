package com.pibigstar.ui;

import java.awt.Container;
import java.awt.HeadlessException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MyJFrame extends JFrame{
	
	private String title;
	private static JLabel screen;
	public MyJFrame(String title) {
		this.title = title;
		try {
			setTitle(title);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			setSize(1000,800);
			enableInputMethods(false);
			Container con = getContentPane();
			JPanel panel = new JPanel();
			screen = new JLabel();
			panel.add(screen);
			JScrollPane scrollPane = new JScrollPane(panel);
			con.add(scrollPane);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MyJFrame() throws HeadlessException {
		super();
	}


	public void changeImage(ImageIcon icon) {
		screen.setIcon(icon);
		screen.repaint();
		this.repaint();//ÖØ»æ
	}
}
