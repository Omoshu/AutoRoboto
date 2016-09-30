package com.cionik.autoroboto.util;

public interface Listener<T> {
	
	void handleEvent(T t);
	
}