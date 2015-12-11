package com.cionik.autoroboto.ui;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import com.cionik.autoroboto.MouseButton;
import com.cionik.autoroboto.Time;
import com.cionik.autoroboto.task.ClickTask;
import com.cionik.autoroboto.task.Task;
import com.cionik.autoroboto.task.TaskListener;
import com.cionik.autoroboto.task.TaskScheduler;
import com.cionik.utils.swing.ScreenPointSelectDialog;

public class ClickerPanelController extends ClickerPanel {

	private static final long serialVersionUID = 1L;
	
	private TaskScheduler scheduler = new TaskScheduler();
	
	private NativeMouseMotionListener clickerSkipListener;
	
	private Task clickTask;
	
	public ClickerPanelController() {
		super();
		
		initComponents();
		addListeners();
	}
	
	private void initComponents() {
		startButton.setEnabled(canStart());
		stopButton.setEnabled(false);
	}
	
	private void addListeners() {
		ActionListener checkStart = e -> startButton.setEnabled(canStart());
		DocumentListener checkStartDoc = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				checkStart.actionPerformed(null);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkStart.actionPerformed(null);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkStart.actionPerformed(null);
			}
			
		};
		
		mouseLocationCheckBox.addActionListener(checkStart);
		clickInfinitelyCheckBox.addActionListener(checkStart);
		xTextField.getDocument().addDocumentListener(checkStartDoc);
		yTextField.getDocument().addDocumentListener(checkStartDoc);
		delayTextField.getDocument().addDocumentListener(checkStartDoc);
		numOfClicksTextField.getDocument().addDocumentListener(checkStartDoc);
		
		mouseLocationCheckBox.addActionListener(e -> {
			boolean selected = !mouseLocationCheckBox.isSelected();
			xTextField.setEnabled(selected);
			yTextField.setEnabled(selected);
			setPointButton.setEnabled(selected);
		});
		
		clickInfinitelyCheckBox.addActionListener(e ->  numOfClicksTextField.setEnabled(!clickInfinitelyCheckBox.isSelected()));
		
		setPointButton.addActionListener(e -> {
			Point p = ScreenPointSelectDialog.getPoint();
			xTextField.setValue(p.x);
			yTextField.setValue(p.y);
		});
		
		startButton.addActionListener(e -> start());
		stopButton.addActionListener(e -> stop());
		
		scheduler.addListener(new ClickTaskListener());
		if (GlobalScreen.isNativeHookRegistered()) {
			GlobalScreen.addNativeKeyListener(new HotkeyListener());
		}
	}
	
	private void start() {
		try {
			Point point = mouseLocationCheckBox.isSelected() ? null : new Point(xTextField.getValue(), yTextField.getValue());
			MouseButton button = (MouseButton) mouseButtonComboBox.getSelectedItem();
			int iterations = clickInfinitelyCheckBox.isSelected() ? -1 : numOfClicksTextField.getValue();
			clickTask = new ClickTask(point, button, iterations);
			
			Time initialDelay = new Time(initialDelayTextField.getValue(0), (TimeUnit) initialDelayComboBox.getSelectedItem());
			Time delay = new Time(delayTextField.getValue(0), (TimeUnit) delayComboBox.getSelectedItem());
			scheduler.schedule(clickTask, initialDelay, delay);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	private void stop() {
		scheduler.shutdown();
	}
	
	private boolean canStart() {
		return (mouseLocationCheckBox.isSelected() || (xTextField.getValue() != null && yTextField.getValue() != null)) &&
				(delayTextField.getValue() != null && delayTextField.getValue() > 0) &&
				((numOfClicksTextField.getValue() != null && numOfClicksTextField.getValue() > 0) || clickInfinitelyCheckBox.isSelected());
	}
	
	private class HotkeyListener implements NativeKeyListener {

		@Override
		public void nativeKeyPressed(NativeKeyEvent e) {
			KeyStroke stroke = stopKeyTextField.getKeyStroke();
			if (stroke != null && stroke.getKeyCode() == e.getKeyCode() && stroke.getModifiers() == e.getModifiers()) {
				if (scheduler.isRunning()) {
					if (scheduler.isRunning()) {
						stop();
					}
				} else {
					if (canStart()) {
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
	
	private class ClickerSkipListener implements NativeMouseMotionListener {

		@Override
		public void nativeMouseDragged(NativeMouseEvent e) {
		}

		@Override
		public void nativeMouseMoved(NativeMouseEvent e) {
			if (clickTask != null && scheduler.isRunning()) {
				clickTask.skip();
			}
		}
		
	}
	
	private class ClickTaskListener implements TaskListener {

		@Override
		public void beforeStart() {
			if (GlobalScreen.isNativeHookRegistered() && onlyMovingCheckBox.isSelected()) {
				clickerSkipListener = new ClickerSkipListener();
				GlobalScreen.addNativeMouseMotionListener(clickerSkipListener);
			}
			
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
		}

		@Override
		public void afterShutdown() {
			if (clickerSkipListener != null) {
				GlobalScreen.removeNativeMouseMotionListener(clickerSkipListener);
				clickerSkipListener = null;
			}
			
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
		}
		
	}
	
}