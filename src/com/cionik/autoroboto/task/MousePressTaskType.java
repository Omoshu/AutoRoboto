package com.cionik.autoroboto.task;

import com.cionik.autoroboto.ui.MousePressTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class MousePressTaskType implements TaskType {

	@Override
	public TaskPanel createPanel() {
		return new MousePressTaskPanel();
	}
	
	@Override
	public String toString() {
		return "Mouse Press";
	}

}
