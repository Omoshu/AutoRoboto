package com.cionik.autoroboto.ui;

import java.awt.AWTException;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cionik.autoroboto.task.KeyTask;
import com.cionik.autoroboto.util.JKeyCodeTextField;
import com.cionik.autoroboto.util.Listener;

import net.miginfocom.swing.MigLayout;

public class KeyTaskPanel extends JPanel implements TaskPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JKeyCodeTextField keyTextField = new JKeyCodeTextField(5);
	private int eventType;
	
	public KeyTaskPanel(int eventType) {
		if (eventType != KeyEvent.KEY_TYPED &&
			eventType != KeyEvent.KEY_PRESSED &&
			eventType != KeyEvent.KEY_RELEASED) {
			throw new IllegalArgumentException("invalid eventType");
		}
		
		this.eventType = eventType;
		
		layoutComponents();
	}
	
	private void layoutComponents() {
		setLayout(new MigLayout());
		add(new JLabel("Key: "));
		add(keyTextField);
	}

	@Override
	public boolean hasValidInput() {
		return keyTextField.getKeyCode() != -1;
	}

	@Override
	public Runnable getTask() {
		try {
			return new KeyTask(keyTextField.getKeyCode(), eventType);
		} catch (AWTException e) {
			return null;
		}
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

	@Override
	public void addInputChangeListener(Listener<Void> listener) {
		keyTextField.getDocument().addDocumentListener(new DocumentListenerAdapter(listener));
	}

}
