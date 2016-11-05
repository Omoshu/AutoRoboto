package com.cionik.autoroboto.util;

import java.awt.Component;

public class DoubleClickEvent<S extends Component, T> {
	
	private S source;
	private T item;
	
	public DoubleClickEvent(S source, T item) {
		this.source = source;
		this.item = item;
	}
	
	public S getSource() {
		return source;
	}
	
	public T getItem() {
		return item;
	}
	
}