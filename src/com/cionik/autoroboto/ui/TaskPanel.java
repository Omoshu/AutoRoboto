package com.cionik.autoroboto.ui;

import javax.swing.JPanel;

import com.cionik.autoroboto.util.Listener;

public interface TaskPanel {
	
	boolean hasValidInput();
	
	Runnable getTask();
	
	JPanel getPanel();
	
	void addInputChangeListener(Listener<Void> listener);
	
}