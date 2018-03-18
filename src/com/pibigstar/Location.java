package com.pibigstar;
import java.awt.Dimension;  
import java.awt.Frame;  
import java.awt.Rectangle;  
import java.awt.Robot;  
import java.awt.Toolkit;  
import java.awt.image.BufferedImage;  

import javax.swing.ImageIcon;  
import javax.swing.JLabel;  

/** 
 * 2015-05-17 
 * 
 */  
public class Location {  
	public static void main(String[] args) {  
		try {  
			Frame jframe=new Frame("×ÀÃæ¼à¿Ø");  
			jframe.setSize(1200,800);  
			jframe.setVisible(true);  
			jframe.setAlwaysOnTop(true);  
			Toolkit tk=Toolkit.getDefaultToolkit();  
			Dimension dm = tk.getScreenSize();  
			JLabel imagelabel=new JLabel();  
			jframe.add(imagelabel);  
			Robot robot=new Robot();  
			for(;;){  
				Rectangle rec=new Rectangle(dm.width-jframe.getWidth(),dm.height-jframe.getHeight(),dm.width,jframe.getHeight());  
				BufferedImage bufimage = robot.createScreenCapture(rec);  
				imagelabel.setIcon(new ImageIcon(bufimage));  
				Thread.sleep(50);  
			}  

		} catch (Exception e) {  
			e.printStackTrace();  
		}   
	}  
}  