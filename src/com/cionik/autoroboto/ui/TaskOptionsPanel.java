package com.cionik.autoroboto.ui;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import com.cionik.autoroboto.model.Time;
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
	private JKeyStrokeTextField startStopKeyTextField = new JKeyStrokeTextField();
	private JButton clearButton = new JButton("Clear");
	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");
	private TaskScheduler scheduler = new TaskScheduler();
	private NativeMouseMotionListener skipListener;
	private TaskPanel taskPanel;
	
	public TaskOptionsPanel(TaskPanel taskPanel) {
		super();
		
		this.taskPanel = taskPanel;
		
		initComponents();
		installListeners();
		layoutComponents();
	}
	
	private void initComponents() {
		delayTextField.setColumns(7);
		initialDelayTextField.setColumns(7);
		iterationsTextField.setColumns(7);
		startStopKeyTextField.setColumns(7);
		
		delayComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
		initialDelayComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
		
		startButton.setEnabled(hasValidInput());
		stopButton.setEnabled(scheduler.isRunning());
	}
	
	private void installListeners() {
		Listener<Void> listener = new CheckInputListener();
		ActionListener actionListener = new ActionListenerAdapter(listener);
		DocumentListener documentListener = new DocumentListenerAdapter(listener);
		taskPanel.addInputChangeListener(listener);
		infiniteCheckBox.addActionListener(actionListener);
		delayTextField.getDocument().addDocumentListener(documentListener);
		initialDelayTextField.getDocument().addDocumentListener(documentListener);
		iterationsTextField.getDocument().addDocumentListener(documentListener);
		
		infiniteCheckBox.addActionListener(e ->  iterationsTextField.setEnabled(!infiniteCheckBox.isSelected()));
		
		clearButton.addActionListener(e -> startStopKeyTextField.clear());
		
		startButton.addActionListener(e -> {
			if (hasValidInput()) {
				start();
			}
		});
		stopButton.addActionListener(e -> {
			if (scheduler.isRunning()) {
				stop();
			}
		});
		
		scheduler.addListener(new ClickTaskListener());
		if (GlobalScreen.isNativeHookRegistered()) {
			GlobalScreen.addNativeKeyListener(new HotkeyListener());
		}
	}
	
	private void layoutComponents() {
		setLayout(new MigLayout());
		add(taskPanel.getPanel(), "span, growx, wrap");
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
		add(new JLabel("Key to Start & Stop: "));
		add(startStopKeyTextField);
		add(clearButton, "wrap");
		add(new JSeparator(), "span, growx, wrap");
		add(startButton, "span, split, align center");
		add(stopButton, "align center");
	}
	
	private void start() {
		Runnable task = taskPanel.getTask();
		if (task != null) {
			Time initialDelay = new Time((TimeUnit) initialDelayComboBox.getSelectedItem(), initialDelayTextField.getValue(0));
			Time delay = new Time((TimeUnit) delayComboBox.getSelectedItem(), delayTextField.getValue(0));
			int iterations = infiniteCheckBox.isSelected() ? -1 : iterationsTextField.getValue();
			scheduler.schedule(task, initialDelay, delay, iterations);
		}
	}
	
	private void stop() {
		scheduler.shutdown();
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
	
	private class HotkeyListener implements NativeKeyListener {

		@Override
		public void nativeKeyPressed(NativeKeyEvent e) {
			KeyStroke stroke = startStopKeyTextField.getKeyStroke();
			if (stroke != null &&
				KeyEvent.getKeyText(stroke.getKeyCode()).equals(NativeKeyEvent.getKeyText(e.getKeyCode())) &&
				KeyEvent.getModifiersExText(stroke.getModifiers()).equals(NativeKeyEvent.getModifiersText(e.getModifiers()))) {
				if (scheduler.isRunning()) {
					stop();
				} else {
					if (hasValidInput()) {
						start();
					}
				}
			}
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent e) {
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent e) {
		}
		
	}
	
	private class ClickTaskListener implements TaskListener {

		@Override
		public void beforeStart() {
			if (GlobalScreen.isNativeHookRegistered() && pauseCheckBox.isSelected()) {
				skipListener = new ClickerSkipListener();
				GlobalScreen.addNativeMouseMotionListener(skipListener);
			}
			
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
		}

		@Override
		public void afterShutdown() {
			if (skipListener != null) {
				GlobalScreen.removeNativeMouseMotionListener(skipListener);
				skipListener = null;
			}
			
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
		}
		
	}
	
	private class ClickerSkipListener implements NativeMouseMotionListener {

		@Override
		public void nativeMouseDragged(NativeMouseEvent e) {
		}

		@Override
		public void nativeMouseMoved(NativeMouseEvent e) {
			if (scheduler.isRunning()) {
				scheduler.skip();
			}
		}
		
	}
	
}