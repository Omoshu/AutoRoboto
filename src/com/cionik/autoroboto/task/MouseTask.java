package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;

import com.cionik.autoroboto.model.MouseButton;

public class MouseTask implements Task {
	
	private Robot robot;
	
	private MouseButton button;
	
	private Point point;
	
	private int eventType;
	
	public MouseTask(MouseButton button, Point point, int eventType) throws AWTException {
		if (eventType != MouseEvent.MOUSE_CLICKED &&
			eventType != MouseEvent.MOUSE_PRESSED &&
			eventType != MouseEvent.MOUSE_RELEASED) {
			throw new IllegalArgumentException("invalid eventType");
		}
		
		this.button = button;
		this.point = point;
		this.eventType = eventType;
		
		robot = new Robot();
	}

	@Override
	public void execute() {
		if (point != null) {
			robot.mouseMove(point.x, point.y);
		}
		
		if (eventType == MouseEvent.MOUSE_CLICKED || eventType == MouseEvent.MOUSE_PRESSED) {
			robot.mousePress(button.getMask());
		}
		
		if (eventType == MouseEvent.MOUSE_CLICKED || eventType == MouseEvent.MOUSE_RELEASED) {
			robot.mouseRelease(button.getMask());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Mouse ");
		if (eventType == MouseEvent.MOUSE_CLICKED) {
			sb.append("Click");
		} else if (eventType == MouseEvent.MOUSE_PRESSED) {
			sb.append("Press");
		} else if (eventType == MouseEvent.MOUSE_RELEASED) {
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