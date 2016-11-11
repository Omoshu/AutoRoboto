package com.cionik.autoroboto.task;

import com.cionik.autoroboto.ui.SleepTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class SleepTaskType implements TaskType {

	@Override
	public TaskPanel createPanel() {
		return new SleepTaskPanel();
	}
	
	@Override
	public String toString() {
		return "Sleep";
	}

}
