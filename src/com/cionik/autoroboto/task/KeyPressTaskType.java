package com.cionik.autoroboto.task;

import com.cionik.autoroboto.ui.KeyPressTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class KeyPressTaskType implements TaskType {

	@Override
	public TaskPanel createPanel() {
		return new KeyPressTaskPanel();
	}
	
	@Override
	public String toString() {
		return "Key Press";
	}

}
