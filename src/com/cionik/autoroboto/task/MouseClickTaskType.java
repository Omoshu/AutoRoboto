package com.cionik.autoroboto.task;

import com.cionik.autoroboto.ui.MouseClickTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class MouseClickTaskType implements TaskType {

	@Override
	public TaskPanel createPanel() {
		return new MouseClickTaskPanel();
	}
	
	@Override
	public String toString() {
		return "Mouse Click";
	}

}
