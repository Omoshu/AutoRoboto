package com.cionik.autoroboto.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.KeyStroke;

public class JKeyStrokeTextField extends JKeyInputTextField {
	
	private static final long serialVersionUID = 1L;
	
	private KeyStroke keyStroke;
	
	public JKeyStrokeTextField(int columns) {
		super(columns);
	}
	
	public JKeyStrokeTextField() {
		super();
	}
	
	public KeyStroke getKeyStroke() {
		return keyStroke;
	}
	
	@Override
	public void clear() {
		super.clear();
		keyStroke = null;
	}
	
	@Override
	protected KeyListener createKeyListener() {
		return new KeyStrokeListener();
	}
	
	private class KeyStrokeListener extends KeyAdapter {
		
		@Override
		public void keyReleased(KeyEvent e) {
			keyStroke = KeyStroke.getKeyStrokeForEvent(e);
			String modifierText = KeyEvent.getKeyModifiersText(e.getModifiers());
			if (modifierText.equals("")) {
				setText(KeyEvent.getKeyText(e.getKeyCode()));
			} else {
				setText(modifierText + "+" + KeyEvent.getKeyText(e.getKeyCode()));
			}
			getParent().requestFocusInWindow();
		}
		
	}
	
}