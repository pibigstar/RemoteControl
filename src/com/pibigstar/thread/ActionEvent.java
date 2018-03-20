package com.pibigstar.thread;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
		try {
			Robot robot = new Robot();
			while (true) {
				Object readObject = ois.readObject();
				InputEvent inputEvent = (InputEvent) readObject;
				actionEvent(robot,inputEvent);
			}
		} catch (Exception e) {
			System.out.println("客户端退出。。。");
			return;
		}
	}


	/**
	 * 执行具体的事件
	 * @param inputEvent
	 */
	private void actionEvent(Robot robot,InputEvent inputEvent) {
		int mousebuttonmask = -100; //鼠标按键 
		// 鼠标事件
		if (inputEvent instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)inputEvent;
			int type = e.getID();
			if(type==MouseEvent.MOUSE_PRESSED){  //按下
				robot.mouseMove( e.getX() , e.getY() );  
				mousebuttonmask = getMouseClick(e.getButton() );
				if(mousebuttonmask != -100)
					robot.mousePress(mousebuttonmask);
			}
			if(type==MouseEvent.MOUSE_RELEASED){  //放开
				robot.mouseMove( e.getX() , e.getY() );  
				mousebuttonmask = getMouseClick( e.getButton() );//取得鼠标按键  
				if(mousebuttonmask != -100)  
					robot.mouseRelease( mousebuttonmask ); 
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
	private static int getMouseClick(int button) { 
		//取得鼠标按键 
		if (button == MouseEvent.BUTTON1) //左键 ,中间键为BUTTON2  
			return InputEvent.BUTTON1_MASK; 
		if (button == MouseEvent.BUTTON3) //右键  
			return InputEvent.BUTTON3_MASK; 
		return -100; 

	} 
}
