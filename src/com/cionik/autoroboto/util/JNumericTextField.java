package com.cionik.autoroboto.util;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class JNumericTextField extends JTextField {
	
	private static final long serialVersionUID = 1L;
	
	public JNumericTextField() {
		super();
	}
	
	public JNumericTextField(int value, int columns) {
		super(columns);
		
		setValue(value);
	}
	
	public JNumericTextField(int value) {
		super();
		
		setValue(value);
	}
	
	public Integer getValue() {
		return super.getText().equals("") ? null : Integer.parseInt(super.getText());
	}
	
	public int getValue(int defaultValue) {
		Integer value = getValue();
		return value == null ? defaultValue : value;
	}
	
	public void setValue(int value) {
		if (value < 0) {
			throw new IllegalArgumentException("value cannot be negative");
		}
		super.setText(Integer.toString(value));
	}
	
	@Override
	public String getText() {
		throw new UnsupportedOperationException("Use getValue() instead");
	}
	
	@Override
	public void setText(String t) {
		throw new UnsupportedOperationException("Use setValue() instead");
	}

	@Override
	protected Document createDefaultModel() {
		return new NumericDocument();
	}
	
	private class NumericDocument extends PlainDocument {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str.matches("^[0-9]+$")) {
				super.insertString(offs, str, a);
			}
		}
		
	}
	
}