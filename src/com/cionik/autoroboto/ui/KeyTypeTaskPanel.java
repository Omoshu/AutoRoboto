package com.cionik.autoroboto.ui;

import java.awt.AWTException;

import com.cionik.autoroboto.task.KeyTypeTask;
import com.cionik.autoroboto.task.Task;

public class KeyTypeTaskPanel extends KeyTaskPanel {
	
	private static final long serialVersionUID = 1L;

	public KeyTypeTaskPanel(int keyCode) {
		super(keyCode);
	}
	
	public KeyTypeTaskPanel() {
		super();
	}
	
	@Override
	public Task getTask() {
		try {
			return new KeyTypeTask(getKeyCode());
		} catch (AWTException e) {
			return null;
		}
	}
	
}