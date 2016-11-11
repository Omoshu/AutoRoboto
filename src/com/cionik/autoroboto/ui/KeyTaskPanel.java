package com.cionik.autoroboto.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cionik.autoroboto.util.JKeyCodeTextField;
import com.cionik.autoroboto.util.Listener;

import net.miginfocom.swing.MigLayout;

public abstract class KeyTaskPanel extends JPanel implements TaskPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JKeyCodeTextField keyTextField = new JKeyCodeTextField(5);
	
	public KeyTaskPanel(int keyCode) {
		super();
		
		if (keyCode != -1) {
			keyTextField.setKeyCode(keyCode);
		}
		layoutComponents();
	}
	
	public KeyTaskPanel() {
		this(-1);
	}
	
	private void layoutComponents() {
		setLayout(new MigLayout());
		add(new JLabel("Key: "));
		add(keyTextField);
	}
	
	public int getKeyCode() {
		return keyTextField.getKeyCode();
	}

	@Override
	public boolean hasValidInput() {
		return keyTextField.getKeyCode() != -1;
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
