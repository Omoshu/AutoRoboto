package com.cionik.autoroboto.task;

import com.cionik.autoroboto.ui.MouseReleaseTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class MouseReleaseTaskType implements TaskType {

	@Override
	public TaskPanel createPanel() {
		return new MouseReleaseTaskPanel();
	}
	
	@Override
	public String toString() {
		return "Mouse Release";
	}

}
