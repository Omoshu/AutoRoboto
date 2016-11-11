package com.cionik.autoroboto.ui;

import java.awt.AWTException;
import java.awt.Point;

import com.cionik.autoroboto.model.MouseButton;
import com.cionik.autoroboto.task.MouseClickTask;
import com.cionik.autoroboto.task.Task;

public class MouseClickTaskPanel extends MouseTaskPanel {

	private static final long serialVersionUID = 1L;

	public MouseClickTaskPanel(MouseButton button, Point point) {
		super(button, point);
	}
	
	public MouseClickTaskPanel() {
		super();
	}

	@Override
	public Task getTask() {
		try {
			return new MouseClickTask(getMouseButton(), isCurrentMouseLocation() ? null : getPoint());
		} catch (AWTException e) {
			return null;
		}
	}

}
