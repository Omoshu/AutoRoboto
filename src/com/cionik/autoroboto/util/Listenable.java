package com.cionik.autoroboto.util;

import java.util.ArrayList;
import java.util.List;

public class Listenable<T> {
	
	private List<T> listeners = new ArrayList<T>();
	
	public void addListener(T t) {
		listeners.add(t);
	}
	
	public boolean removeListener(T t) {
		return listeners.remove(t);
	}
	
	protected List<T> getListeners() {
		return listeners;
	}
	
}