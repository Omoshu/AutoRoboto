package com.cionik.autoroboto.ui;

import java.util.concurrent.TimeUnit;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cionik.autoroboto.model.Time;
import com.cionik.autoroboto.task.DelayTask;
import com.cionik.autoroboto.task.Task;
import com.cionik.utils.model.Listener;
import com.cionik.utils.swing.JNumericTextField;

public class DelayTaskPanel extends JPanel implements TaskPanel {

	private static final long serialVersionUID = 1L;
	
	private JNumericTextField delayTextField = new JNumericTextField(0, 5);
	
	private JComboBox<TimeUnit> timeUnitComboBox = new JComboBox<TimeUnit>(TimeUnit.values());
	
	public DelayTaskPanel() {
		initComponents();
		layoutComponents();
	}
	
	private void initComponents() {
		timeUnitComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
	}
	
	private void layoutComponents() {
		add(new JLabel("Delay: "));
		add(delayTextField);
		add(timeUnitComboBox);
	}

	@Override
	public boolean hasValidInput() {
		return delayTextField.getValue() != null;
	}

	@Override
	public Task getTask() {
		return new DelayTask(new Time(delayTextField.getValue(), (TimeUnit) timeUnitComboBox.getSelectedItem()));
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
