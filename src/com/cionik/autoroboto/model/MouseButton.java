package com.cionik.autoroboto.model;

import java.awt.event.InputEvent;

public enum MouseButton {
	
	LEFT(InputEvent.BUTTON1_DOWN_MASK),
	RIGHT(InputEvent.BUTTON2_DOWN_MASK),
	MIDDLE(InputEvent.BUTTON3_DOWN_MASK);
	
	private final int mask;
	
	private MouseButton(int mask) {
		this.mask = mask;
	}
	
	public int getMask() {
		return mask;
	}
	
}