package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Point;

import com.cionik.autoroboto.model.MouseButton;
import com.cionik.autoroboto.ui.MouseReleaseTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class MouseReleaseTask extends MouseTask {

	public MouseReleaseTask(MouseButton button, Point point) throws AWTException {
		super(button, point);
	}

	@Override
	public TaskPanel createPanel() {
		return new MouseReleaseTaskPanel(getButton(), getPoint());
	}
	
	@Override
	public void run() {
		super.run();
		
		getRobot().mouseRelease(getButton().getMask());
	}

	@Override
	protected String getName() {
		return "Release";
	}

}
