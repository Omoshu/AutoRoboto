package com.cionik.autoroboto.task;

import java.awt.AWTException;

import com.cionik.autoroboto.ui.KeyReleaseTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class KeyReleaseTask extends KeyTask {

	public KeyReleaseTask(int keyCode) throws AWTException {
		super(keyCode);
	}

	@Override
	public TaskPanel createPanel() {
		return new KeyReleaseTaskPanel(getKeyCode());
	}

	@Override
	public void run() {
		getRobot().keyRelease(getKeyCode());
	}

	@Override
	protected String getName() {
		return "Release";
	}

}
