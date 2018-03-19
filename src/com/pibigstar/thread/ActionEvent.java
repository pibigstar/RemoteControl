package com.pibigstar.thread;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 执行事件线程
 * @author pibigstar
 *
 */
public class ActionEvent extends Thread{

	private ObjectInputStream ois;

	public ActionEvent(ObjectInputStream ois) {
		this.ois = ois;
	}


	@Override
	public void run() {
		while (true) {
			try {
				Object readObject = ois.readObject();
				InputEvent inputEvent = (InputEvent) readObject;
				actionEvent(inputEvent);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("客户端退出。。。");
				break;
			}
		}

	}

	/**
	 * 执行具体的事件
	 * @param inputEvent
	 */
	private void actionEvent(InputEvent inputEvent) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		// 鼠标事件
		if (inputEvent instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)inputEvent;
			int type = e.getID();
			if(type==MouseEvent.MOUSE_PRESSED){  //按下
				robot.mousePress(2);
			}
			if(type==MouseEvent.MOUSE_RELEASED){  //放开
				robot.mouseRelease(3);
			}
			if(type==MouseEvent.MOUSE_MOVED) { //移动
				robot.mouseMove(e.getX(), e.getY());
			}
			if(type==MouseEvent.MOUSE_DRAGGED) { //拖动
				robot.mouseMove(e.getX(), e.getY());
			}
			if(type==MouseEvent.MOUSE_WHEEL) {   //滑轮滚动
				robot.mouseWheel(getMouseClick(e.getButton()));
			}

		}
		// 键盘事件
		if(inputEvent instanceof KeyEvent){
			KeyEvent e=(KeyEvent) inputEvent;
			if(e.getID()==KeyEvent.KEY_PRESSED){
				robot.keyPress(e.getKeyCode());
			}
			if(e.getID()==KeyEvent.KEY_RELEASED){
				robot.keyRelease(e.getKeyCode());
			}
		}

	}

	//根据发送事的Mouse事件对象，转变为通用的Mouse按键代码
	private int getMouseClick(int button){
		if(button==MouseEvent.BUTTON1){
			return 2;
		} 
		if(button==MouseEvent.BUTTON2){
			return 3;
		} 
		if(button==MouseEvent.BUTTON3){
			return InputEvent.BUTTON3_MASK;
		}
		return -1;
	}

}
