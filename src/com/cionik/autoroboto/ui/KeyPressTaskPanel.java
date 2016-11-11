package com.cionik.autoroboto.ui;

import java.awt.AWTException;

import com.cionik.autoroboto.task.KeyPressTask;
import com.cionik.autoroboto.task.Task;

public class KeyPressTaskPanel extends KeyTaskPanel {

	private static final long serialVersionUID = 1L;
	
	public KeyPressTaskPanel(int keyCode) {
		super(keyCode);
	}
	
	public KeyPressTaskPanel() {
		super();
	}

	@Override
	public Task getTask() {
		try {
			return new KeyPressTask(getKeyCode());
		} catch (AWTException e) {
			return null;
		}
	}

}
