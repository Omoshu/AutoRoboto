package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Point;

import com.cionik.autoroboto.model.MouseButton;
import com.cionik.autoroboto.ui.MouseClickTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class MouseClickTask extends MouseTask {

	public MouseClickTask(MouseButton button, Point point) throws AWTException {
		super(button, point);
	}

	@Override
	public TaskPanel createPanel() {
		return new MouseClickTaskPanel(getButton(), getPoint());
	}
	
	@Override
	public void run() {
		super.run();
		
		int mask = getButton().getMask();
		getRobot().mousePress(mask);
		getRobot().mouseRelease(mask);
	}

	@Override
	protected String getName() {
		return "Click";
	}

}
