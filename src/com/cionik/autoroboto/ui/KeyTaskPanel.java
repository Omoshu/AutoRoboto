package com.cionik.autoroboto.ui;

import java.awt.AWTException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cionik.autoroboto.task.KeyTask;
import com.cionik.autoroboto.util.JKeyCodeTextField;
import com.cionik.autoroboto.util.Listener;
import com.cionik.autoroboto.util.SwingUtils;

import net.miginfocom.swing.MigLayout;

public class KeyTaskPanel extends JPanel implements TaskPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JKeyCodeTextField keyTextField = new JKeyCodeTextField(5);
	private int eventId;
	
	public KeyTaskPanel(int eventId) {
		super();
		
		SwingUtils.checkKeyEventId(eventId);
		
		this.eventId = eventId;
		
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
			return new KeyTask(keyTextField.getKeyCode(), eventId);
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
