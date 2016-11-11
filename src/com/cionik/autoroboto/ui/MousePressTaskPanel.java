package com.cionik.autoroboto.ui;

import java.awt.AWTException;
import java.awt.Point;

import com.cionik.autoroboto.model.MouseButton;
import com.cionik.autoroboto.task.MousePressTask;
import com.cionik.autoroboto.task.Task;

public class MousePressTaskPanel extends MouseTaskPanel {

	private static final long serialVersionUID = 1L;

	public MousePressTaskPanel(MouseButton button, Point point) {
		super(button, point);
	}
	
	public MousePressTaskPanel() {
		super();
	}

	@Override
	public Task getTask() {
		try {
			return new MousePressTask(getMouseButton(), isCurrentMouseLocation() ? null : getPoint());
		} catch (AWTException e) {
			return null;
		}
	}

}
