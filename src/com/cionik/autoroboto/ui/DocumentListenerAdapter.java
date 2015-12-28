package com.cionik.autoroboto.ui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.cionik.utils.model.Listener;

public class DocumentListenerAdapter implements DocumentListener {
	
	private Listener<Void> listener;
	
	public DocumentListenerAdapter(Listener<Void> listener) {
		this.listener = listener;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		listener.handleEvent(null);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		listener.handleEvent(null);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		listener.handleEvent(null);
	}
	
}