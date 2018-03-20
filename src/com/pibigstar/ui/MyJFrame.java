package com.pibigstar.ui;

import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MyJFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private String title;
	public static JLabel screen;
	public static JPanel panel;
	public MyJFrame(String title) {
		this.title = title;
		try {
			setTitle(title);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			setSize(1000,800);
			enableInputMethods(false);
			Container con = getContentPane();
			panel = new JPanel();
			screen = new JLabel();
			panel.add(screen);
			JScrollPane scrollPane = new JScrollPane(panel);
			con.add(scrollPane);			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeImage(ImageIcon icon) {
		screen.setIcon(icon);
		screen.repaint();
		this.repaint();//ÖØ»æ
	}
}
