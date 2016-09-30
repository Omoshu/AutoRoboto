package com.cionik.autoroboto.util;

public class Condition {
	
	public static void checkArgument(boolean condition, String message) {
		if (!condition) {
			IllegalArgumentException e = message == null ? new IllegalArgumentException() : new IllegalArgumentException(message);
			throw e;
		}
	}
	
	public static void checkArgument(boolean condition) {
		checkArgument(condition, null);
	}
	
	public static void checkNullArgument(Object o, String message) {
		checkArgument(o != null, message);
	}
	
	public static void checkNullArgument(Object o) {
		checkNullArgument(o, null);
	}
	
}