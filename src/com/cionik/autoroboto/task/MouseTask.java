package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;

import com.cionik.autoroboto.model.MouseButton;
import com.cionik.autoroboto.util.SwingUtils;

public class MouseTask implements Runnable {
	
	private Robot robot;
	private MouseButton button;
	private Point point;
	private int eventId;
	
	public MouseTask(MouseButton button, Point point, int eventId) throws AWTException {
		if (button == null) {
			throw new IllegalArgumentException("button cannot be null");
		}
		SwingUtils.checkMouseEventId(eventId);
		
		this.button = button;
		this.point = point;
		this.eventId = eventId;
		
		robot = new Robot();
	}

	@Override
	public void run() {
		if (point != null) {
			robot.mouseMove(point.x, point.y);
		}
		
		if (eventId == MouseEvent.MOUSE_CLICKED || eventId == MouseEvent.MOUSE_PRESSED) {
			robot.mousePress(button.getMask());
		}
		
		if (eventId == MouseEvent.MOUSE_CLICKED || eventId == MouseEvent.MOUSE_RELEASED) {
			robot.mouseRelease(button.getMask());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Mouse ");
		if (eventId == MouseEvent.MOUSE_CLICKED) {
			sb.append("Click");
		} else if (eventId == MouseEvent.MOUSE_PRESSED) {
			sb.append("Press");
		} else if (eventId == MouseEvent.MOUSE_RELEASED) {
			sb.append("Release");
		}
		sb.append(": Point[");
		if (point == null) {
			sb.append("Mouse Location");
		} else {
			sb.append("X:");
			sb.append(point.x);
			sb.append(", Y:");
			sb.append(point.y);
		}
		sb.append("], Button[");
		sb.append(button);
		sb.append("]");
		return sb.toString();
	}
	
}