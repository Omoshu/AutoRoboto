package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Point;

import com.cionik.autoroboto.model.MouseButton;
import com.cionik.autoroboto.ui.MousePressTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class MousePressTask extends MouseTask {

	public MousePressTask(MouseButton button, Point point) throws AWTException {
		super(button, point);
	}

	@Override
	public TaskPanel createPanel() {
		return new MousePressTaskPanel(getButton(), getPoint());
	}
	
	@Override
	public void run() {
		super.run();
		
		getRobot().mousePress(getButton().getMask());
	}

	@Override
	protected String getName() {
		return "Press";
	}

}
