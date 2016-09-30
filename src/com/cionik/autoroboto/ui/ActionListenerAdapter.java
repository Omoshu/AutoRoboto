package com.cionik.autoroboto.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cionik.autoroboto.util.Listener;

public class ActionListenerAdapter implements ActionListener {
	
	private Listener<Void> listener;
	
	public ActionListenerAdapter(Listener<Void> listener) {
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		listener.handleEvent(null);
	}
	
}