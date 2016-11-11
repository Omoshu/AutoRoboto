package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public abstract class KeyTask implements Task {
	
	private Robot robot;
	private int keyCode;
	
	public KeyTask(int keyCode) throws AWTException {
		this.keyCode = keyCode;
		
		robot = new Robot();
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	protected Robot getRobot() {
		return robot;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Key ");
		sb.append(getName());
		sb.append(": Key[");
		sb.append(KeyEvent.getKeyText(keyCode));
		sb.append(" (");
		sb.append(keyCode);
		sb.append(")]");
		return sb.toString();
	}
	
	protected abstract String getName();
	
}