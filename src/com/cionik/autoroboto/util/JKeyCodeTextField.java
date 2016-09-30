package com.cionik.autoroboto.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JKeyCodeTextField extends JKeyInputTextField {

	private static final long serialVersionUID = 1L;
	
	private int keyCode = -1;
	
	public JKeyCodeTextField(int columns) {
		super(columns);
	}
	
	public JKeyCodeTextField() {
		super();
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	@Override
	public void clear() {
		super.clear();
		keyCode = -1;
	}

	@Override
	protected KeyListener createKeyListener() {
		return new KeyCodeListener();
	}
	
	private class KeyCodeListener extends KeyAdapter {
		
		@Override
		public void keyReleased(KeyEvent e) {
			keyCode = e.getKeyCode();
			setText(KeyEvent.getKeyText(keyCode));
			getParent().requestFocusInWindow();
		}
		
	}
	
}