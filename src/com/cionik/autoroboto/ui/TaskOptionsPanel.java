package com.cionik.autoroboto.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.DocumentListener;

import com.cionik.autoroboto.model.Time;
import com.cionik.autoroboto.task.Task;
import com.cionik.autoroboto.task.TaskListener;
import com.cionik.autoroboto.task.TaskScheduler;
import com.cionik.autoroboto.util.JKeyStrokeTextField;
import com.cionik.autoroboto.util.JNumericTextField;
import com.cionik.autoroboto.util.Listener;

import net.miginfocom.swing.MigLayout;

public class TaskOptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JCheckBox pauseCheckBox = new JCheckBox("Pause on Mouse Move");
	private JCheckBox infiniteCheckBox = new JCheckBox("Infinite");
	private JNumericTextField delayTextField = new JNumericTextField(1000);
	private JNumericTextField initialDelayTextField = new JNumericTextField(0);
	private JNumericTextField iterationsTextField = new JNumericTextField(0);
	private JComboBox<TimeUnit> delayComboBox = new JComboBox<TimeUnit>(TimeUnit.values());
	private JComboBox<TimeUnit> initialDelayComboBox = new JComboBox<TimeUnit>(TimeUnit.values());
	private JKeyStrokeTextField stopShortcutTextField = new JKeyStrokeTextField();
	private JButton clearButton = new JButton("Clear");
	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");
	private TaskScheduler scheduler = new TaskScheduler();
	private TaskPanel taskPanel;
	
	public TaskOptionsPanel(TaskPanel taskPanel) {
		super();
		
		this.taskPanel = taskPanel;
		
		addListeners();
		initComponents();
		layoutComponents();
	}
	
	private void addListeners() {
		Listener<Void> listener = new CheckInputListener();
		ActionListener actionListener = new ActionListenerAdapter(listener);
		DocumentListener documentListener = new DocumentListenerAdapter(listener);
		taskPanel.addInputChangeListener(listener);
		infiniteCheckBox.addActionListener(actionListener);
		delayTextField.getDocument().addDocumentListener(documentListener);
		initialDelayTextField.getDocument().addDocumentListener(documentListener);
		iterationsTextField.getDocument().addDocumentListener(documentListener);
		
		infiniteCheckBox.addActionListener(new InfiniteCheckBoxActionListener());
		clearButton.addActionListener(new ClearButtonActionListener());
		startButton.addActionListener(new StartButtonActionListener());
		stopButton.addActionListener(new StopButtonActionListener());
		
		scheduler.addListener(new TaskListenerImpl());
	}
	
	private void initComponents() {
		delayTextField.setColumns(7);
		initialDelayTextField.setColumns(7);
		iterationsTextField.setColumns(7);
		stopShortcutTextField.setColumns(7);
		
		delayComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
		initialDelayComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
		
		startButton.setEnabled(hasValidInput());
		stopButton.setEnabled(scheduler.isRunning());
	}
	
	private void layoutComponents() {
		setLayout(new MigLayout());
		add(taskPanel.getPanel(), "span, pushx, growx, wrap");
		add(new JSeparator(), "span, growx, wrap");
		add(pauseCheckBox, "span");
		add(new JLabel("Initial Delay: "));
		add(initialDelayTextField);
		add(initialDelayComboBox, "wrap");
		add(new JLabel("Delay: "));
		add(delayTextField);
		add(delayComboBox, "wrap");
		add(new JSeparator(), "span, growx, wrap");
		add(new JLabel("Iterations: "));
		add(iterationsTextField);
		add(infiniteCheckBox, "wrap");
		add(new JSeparator(), "span, growx, wrap");
		add(new JLabel("Stop Shortcut: "));
		add(stopShortcutTextField);
		add(clearButton, "wrap");
		add(new JSeparator(), "span, growx, wrap");
		add(startButton, "span, split, pushy, alignx center, aligny bottom");
		add(stopButton, "alignx center, aligny bottom");
	}
	
	private void start() {
		Task task = taskPanel.getTask();
		if (task != null) {
			scheduler.schedule(task,
					new Time((TimeUnit) initialDelayComboBox.getSelectedItem(), initialDelayTextField.getValue(0)),
					new Time((TimeUnit) delayComboBox.getSelectedItem(), delayTextField.getValue(0)),
					infiniteCheckBox.isSelected() ? -1 : iterationsTextField.getValue(),
					pauseCheckBox.isSelected(),
					stopShortcutTextField.getKeyStroke());
		}
	}
	
	private boolean hasValidInput() {
		return taskPanel.hasValidInput() &&
				(delayTextField.getValue() != null && delayTextField.getValue() > 0) &&
				((iterationsTextField.getValue() != null && iterationsTextField.getValue() > 0) || infiniteCheckBox.isSelected());
	}
	
	private class CheckInputListener implements Listener<Void> {

		@Override
		public void handleEvent(Void t) {
			startButton.setEnabled(hasValidInput());
		}
		
	}
	
	private class InfiniteCheckBoxActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			iterationsTextField.setEnabled(!infiniteCheckBox.isSelected());
		}
		
	}
	
	private class ClearButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			stopShortcutTextField.clear();
		}
		
	}
	
	private class StartButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hasValidInput()) {
				start();
			}
		}
		
	}
	
	private class StopButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			scheduler.shutdown();
		}
		
	}
	
	private class TaskListenerImpl implements TaskListener {

		@Override
		public void beforeStart() {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
		}

		@Override
		public void afterShutdown() {
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
		}
		
	}
	
}