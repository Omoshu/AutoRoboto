package com.cionik.autoroboto.ui;

import javax.swing.JPanel;

import com.cionik.autoroboto.task.Task;
import com.cionik.utils.model.Listener;

public interface TaskPanel {
	
	boolean hasValidInput();
	
	Task getTask();
	
	JPanel getPanel();
	
	void addInputChangeListener(Listener<Void> listener);
	
}