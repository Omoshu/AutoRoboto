package com.cionik.autoroboto.util;

import java.awt.Component;

public class DoubleClickEvent<C extends Component, T> {
	
	private C source;
	
	private T item;
	
	public DoubleClickEvent(C source, T item) {
		this.source = source;
		this.item = item;
	}
	
	public C getSource() {
		return source;
	}
	
	public T getItem() {
		return item;
	}
	
}