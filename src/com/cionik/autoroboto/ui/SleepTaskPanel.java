package com.cionik.autoroboto.ui;

import java.util.concurrent.TimeUnit;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cionik.autoroboto.model.Time;
import com.cionik.autoroboto.task.SleepTask;
import com.cionik.autoroboto.util.JNumericTextField;
import com.cionik.autoroboto.util.Listener;

public class SleepTaskPanel extends JPanel implements TaskPanel {

	private static final long serialVersionUID = 1L;
	
	private JNumericTextField delayTextField = new JNumericTextField(0, 5);
	private JComboBox<TimeUnit> timeUnitComboBox = new JComboBox<TimeUnit>(TimeUnit.values());
	
	public SleepTaskPanel() {
		initComponents();
		layoutComponents();
	}
	
	private void initComponents() {
		timeUnitComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
	}
	
	private void layoutComponents() {
		add(new JLabel("Sleep: "));
		add(delayTextField);
		add(timeUnitComboBox);
	}

	@Override
	public boolean hasValidInput() {
		return delayTextField.getValue() != null;
	}

	@Override
	public Runnable getTask() {
		return new SleepTask(new Time((TimeUnit) timeUnitComboBox.getSelectedItem(), delayTextField.getValue()));
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

	@Override
	public void addInputChangeListener(Listener<Void> listener) {
		delayTextField.getDocument().addDocumentListener(new DocumentListenerAdapter(listener));
	}

}
