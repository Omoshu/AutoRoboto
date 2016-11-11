package com.cionik.autoroboto.task;

import java.awt.AWTException;

import com.cionik.autoroboto.ui.KeyTypeTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class KeyTypeTask extends KeyTask {

	public KeyTypeTask(int keyCode) throws AWTException {
		super(keyCode);
	}

	@Override
	public TaskPanel createPanel() {
		return new KeyTypeTaskPanel(getKeyCode());
	}

	@Override
	public void run() {
		getRobot().keyPress(getKeyCode());
		getRobot().keyRelease(getKeyCode());
	}

	@Override
	protected String getName() {
		return "Type";
	}

}
