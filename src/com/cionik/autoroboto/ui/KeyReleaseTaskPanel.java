package com.cionik.autoroboto.ui;

import java.awt.AWTException;

import com.cionik.autoroboto.task.KeyReleaseTask;
import com.cionik.autoroboto.task.Task;

public class KeyReleaseTaskPanel extends KeyTaskPanel {

	private static final long serialVersionUID = 1L;
	
	public KeyReleaseTaskPanel(int keyCode) {
		super(keyCode);
	}
	
	public KeyReleaseTaskPanel() {
		super();
	}

	@Override
	public Task getTask() {
		try {
			return new KeyReleaseTask(getKeyCode());
		} catch (AWTException e) {
			return null;
		}
	}

}
