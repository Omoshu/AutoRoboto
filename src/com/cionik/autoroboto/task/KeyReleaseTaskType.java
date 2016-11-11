package com.cionik.autoroboto.task;

import com.cionik.autoroboto.ui.KeyReleaseTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class KeyReleaseTaskType implements TaskType {

	@Override
	public TaskPanel createPanel() {
		return new KeyReleaseTaskPanel();
	}
	
	@Override
	public String toString() {
		return "Key Release";
	}

}
