package com.cionik.autoroboto.ui;

import java.awt.AWTException;
import java.awt.Point;

import com.cionik.autoroboto.model.MouseButton;
import com.cionik.autoroboto.task.MouseReleaseTask;
import com.cionik.autoroboto.task.Task;

public class MouseReleaseTaskPanel extends MouseTaskPanel {

	private static final long serialVersionUID = 1L;

	public MouseReleaseTaskPanel(MouseButton button, Point point) {
		super(button, point);
	}
	
	public MouseReleaseTaskPanel() {
		super();
	}

	@Override
	public Task getTask() {
		try {
			return new MouseReleaseTask(getMouseButton(), isCurrentMouseLocation() ? null : getPoint());
		} catch (AWTException e) {
			return null;
		}
	}

}
