package com.cionik.autoroboto.util;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public abstract class JKeyInputTextField extends JTextField {

	private static final long serialVersionUID = 1L;
	
	private Color focusBackground = Color.LIGHT_GRAY;
	
	public JKeyInputTextField(int columns) {
		super(columns);
		
		init();
	}
	
	public JKeyInputTextField() {
		this(0);
	}
	
	public void clear() {
		setText("");
	}
	
	private void init() {
		setEditable(false);
		addKeyListener(createKeyListener());
		addFocusListener(new FocusBackgroundFocusListener());
	}
	
	protected abstract KeyListener createKeyListener();
	
	private class FocusBackgroundFocusListener implements FocusListener {
		
		private Color oldBackground;

		@Override
		public void focusGained(FocusEvent e) {
			oldBackground = getBackground();
			setBackground(focusBackground);
		}

		@Override
		public void focusLost(FocusEvent e) {
			setBackground(oldBackground);
		}
		
	}
	
}