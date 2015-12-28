package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyTask implements Task {
	
	private Robot robot;
	
	private int keyCode;
	
	private int eventType;
	
	public KeyTask(int keyCode, int eventType) throws AWTException {
		if (eventType != KeyEvent.KEY_TYPED &&
			eventType != KeyEvent.KEY_PRESSED &&
			eventType != KeyEvent.KEY_RELEASED) {
			throw new IllegalArgumentException("invalid eventType");
		}
		
		this.keyCode = keyCode;
		this.eventType = eventType;
		
		robot = new Robot();
	}

	@Override
	public void execute() {
		if (eventType == KeyEvent.KEY_TYPED || eventType == KeyEvent.KEY_PRESSED) {
			robot.keyPress(keyCode);
		}
		
		if (eventType == KeyEvent.KEY_TYPED || eventType == KeyEvent.KEY_RELEASED) {
			robot.keyRelease(keyCode);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Key ");
		if (eventType == KeyEvent.KEY_TYPED) {
			sb.append("Type");
		} else if (eventType == KeyEvent.KEY_PRESSED) {
			sb.append("Press");
		} else if (eventType == KeyEvent.KEY_RELEASED) {
			sb.append("Release");
		}
		sb.append(": Key[");
		sb.append(KeyEvent.getKeyText(keyCode));
		sb.append(" (");
		sb.append(keyCode);
		sb.append(")]");
		return sb.toString();
	}
	
}