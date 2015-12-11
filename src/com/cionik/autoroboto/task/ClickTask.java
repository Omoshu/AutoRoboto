package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;

import com.cionik.autoroboto.MouseButton;

public class ClickTask extends Task {
	
	private Robot robot;
	
	private Point point;
	
	private MouseButton button;
	
	private int iterations;
	
	public ClickTask(Point point, MouseButton button, int iterations) throws AWTException {
		this.point = point;
		this.button = button;
		this.iterations = iterations;
		
		robot = new Robot();
	}

	@Override
	public void execute() {
		if (point != null) {
			robot.mouseMove(point.x, point.y);
		}
		
		robot.mousePress(button.getMask());
		robot.mouseRelease(button.getMask());
		
		if (iterations != -1 && --iterations == 0) {
			stop();
		}
	}
	
}