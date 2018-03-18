package com.pibigstar.thread;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 将截图图片发送到客户端（控制端）线程
 * @author pibigstar
 *
 */
public class SendScreenThread extends Thread{
	
	private DataOutputStream dos;
	private Toolkit toolkit;
	private Dimension dimension;
	private Rectangle rectangle;
	private Robot robot;
	
	public SendScreenThread(DataOutputStream dos) throws AWTException {
		this.dos = dos;
		toolkit = Toolkit.getDefaultToolkit();
		dimension = toolkit.getScreenSize();
		rectangle = new Rectangle(0, 0, (int)dimension.getWidth(), (int)dimension.getHeight());
		robot  = new Robot();
	}

	@Override
	public void run() {
		while (true) {
			byte[] data = getCapture();
			try {
				dos.writeInt(data.length);
				dos.write(data);
				dos.flush();
			} catch (IOException e) {
				break;
			}
		}
	}

	/**
	 * 得到屏幕截图数据
	 * @return
	 */
	private byte[] getCapture() {
		
		BufferedImage bufferedImage =  robot.createScreenCapture(rectangle);

		//获得一个内存输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		//将图片数据写入内存流中
		try {
			ImageIO.write(bufferedImage, "jpg", baos);
		} catch (IOException e) {
			System.out.println("截屏图片写入内存流中出现异常");
			e.printStackTrace();
		}
		
		return baos.toByteArray();
	}

}
