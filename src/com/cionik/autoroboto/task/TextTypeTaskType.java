package com.cionik.autoroboto.task;

import com.cionik.autoroboto.ui.TaskPanel;
import com.cionik.autoroboto.ui.TextTypeTaskPanel;

public class TextTypeTaskType implements TaskType {

	@Override
	public TaskPanel createPanel() {
		return new TextTypeTaskPanel();
	}
	
	@Override
	public String toString() {
		return "Text Type";
	}

}
