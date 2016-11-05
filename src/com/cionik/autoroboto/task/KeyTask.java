package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.cionik.autoroboto.util.SwingUtils;

public class KeyTask implements Runnable {
	
	private Robot robot;
	private int keyCode;
	private int eventId;
	
	public KeyTask(int keyCode, int eventId) throws AWTException {
		SwingUtils.checkKeyEventId(eventId);
		
		this.keyCode = keyCode;
		this.eventId = eventId;
		
		robot = new Robot();
	}

	@Override
	public void run() {
		if (eventId == KeyEvent.KEY_TYPED || eventId == KeyEvent.KEY_PRESSED) {
			robot.keyPress(keyCode);
		}
		
		if (eventId == KeyEvent.KEY_TYPED || eventId == KeyEvent.KEY_RELEASED) {
			robot.keyRelease(keyCode);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Key ");
		if (eventId == KeyEvent.KEY_TYPED) {
			sb.append("Type");
		} else if (eventId == KeyEvent.KEY_PRESSED) {
			sb.append("Press");
		} else if (eventId == KeyEvent.KEY_RELEASED) {
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