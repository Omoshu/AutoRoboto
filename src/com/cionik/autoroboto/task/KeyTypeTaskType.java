package com.cionik.autoroboto.task;

import com.cionik.autoroboto.ui.KeyTypeTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class KeyTypeTaskType implements TaskType {

	@Override
	public TaskPanel createPanel() {
		return new KeyTypeTaskPanel();
	}
	
	@Override
	public String toString() {
		return "Key Type";
	}

}
