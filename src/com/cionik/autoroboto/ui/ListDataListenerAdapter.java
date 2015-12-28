package com.cionik.autoroboto.ui;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.cionik.utils.model.Listener;

public class ListDataListenerAdapter implements ListDataListener {
	
	private Listener<Void> listener;
	
	public ListDataListenerAdapter(Listener<Void> listener) {
		this.listener = listener;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		listener.handleEvent(null);
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		listener.handleEvent(null);
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		listener.handleEvent(null);
	}
	
}