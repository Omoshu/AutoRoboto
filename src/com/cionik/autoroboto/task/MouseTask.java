package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;

import com.cionik.autoroboto.model.MouseButton;

public abstract class MouseTask implements Task {
	
	private Robot robot;
	private MouseButton button;
	private Point point;
	
	public MouseTask(MouseButton button, Point point) throws AWTException {
		if (button == null) {
			throw new IllegalArgumentException("button cannot be null");
		}
		
		this.button = button;
		this.point = point;
		
		robot = new Robot();
	}
	
	public MouseButton getButton() {
		return button;
	}
	
	public Point getPoint() {
		return point;
	}
	
	protected Robot getRobot() {
		return robot;
	}

	@Override
	public void run() {
		if (point != null) {
			robot.mouseMove(point.x, point.y);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Mouse ");
		sb.append(getName());
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
	
	protected abstract String getName();
	
}