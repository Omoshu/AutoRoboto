package com.cionik.autoroboto.task;

import java.awt.AWTException;

import com.cionik.autoroboto.ui.KeyPressTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class KeyPressTask extends KeyTask {

	public KeyPressTask(int keyCode) throws AWTException {
		super(keyCode);
	}

	@Override
	public TaskPanel createPanel() {
		return new KeyPressTaskPanel(getKeyCode());
	}

	@Override
	public void run() {
		getRobot().keyPress(getKeyCode());
	}

	@Override
	protected String getName() {
		return "Press";
	}

}
