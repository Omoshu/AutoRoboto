package com.cionik.autoroboto.ui;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import com.cionik.autoroboto.MouseButton;
import com.cionik.autoroboto.Time;
import com.cionik.autoroboto.clicker.AutoClicker;
import com.cionik.autoroboto.clicker.AutoClickerListener;
import com.cionik.utils.swing.ScreenPointSelectDialog;

public class ClickerPanelController extends ClickerPanel {

	private static final long serialVersionUID = 1L;
	
	private AutoClicker clicker;
	
	private NativeKeyListener stopKeyListener = new NativeKeyListener() {

		@Override
		public void nativeKeyPressed(NativeKeyEvent e) {
			String key = stopKeyTextField.getText();
			if (clicker != null && clicker.isRunning() && key != null) {
				if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals(key)) {
						clicker.stop();
				}
			}
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent e) {
		}
			
		@Override
		public void nativeKeyTyped(NativeKeyEvent e) {
		}
			
	};
	
	private NativeMouseMotionListener mouseListener = new NativeMouseMotionListener() {

		@Override
		public void nativeMouseDragged(NativeMouseEvent e) {
		}

		@Override
		public void nativeMouseMoved(NativeMouseEvent e) {
			if (clicker != null && clicker.isRunning() && onlyMovingCheckBox.isSelected()) {
				clicker.skip();
			}
		}
		
	};
	
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
		ActionListener toggleStart = e -> startButton.setEnabled(canStart());
		DocumentListener toggleStartDoc = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				toggleStart.actionPerformed(null);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				toggleStart.actionPerformed(null);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				toggleStart.actionPerformed(null);
			}
			
		};
		
		mouseLocationCheckBox.addActionListener(toggleStart);
		xTextField.getDocument().addDocumentListener(toggleStartDoc);
		yTextField.getDocument().addDocumentListener(toggleStartDoc);
		delayTextField.getDocument().addDocumentListener(toggleStartDoc);
		numOfClicksTextField.getDocument().addDocumentListener(toggleStartDoc);
		clickInfinitelyCheckBox.addActionListener(toggleStart);
		
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
		
		startButton.addActionListener(e -> {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			
			start();
		});
		
		stopButton.addActionListener(e -> stop());
	}
	
	private void start() {
		try {
			clicker = new AutoClicker((MouseButton) mouseButtonComboBox.getSelectedItem(),
				mouseLocationCheckBox.isSelected() ? null : new Point(xTextField.getValue(), yTextField.getValue()),
				initialDelayTextField.getValue() == null ? null : new Time(initialDelayTextField.getValue(), (TimeUnit) initialDelayComboBox.getSelectedItem()),
				new Time(delayTextField.getValue(), (TimeUnit) delayComboBox.getSelectedItem()),
				clickInfinitelyCheckBox.isSelected() ? -1 : numOfClicksTextField.getValue());
			clicker.addListener(new AutoClickerListenerImpl());
			clicker.start();
			
			if (GlobalScreen.isNativeHookRegistered()) {
				if (!stopKeyTextField.getText().equals("")) {
					GlobalScreen.addNativeKeyListener(stopKeyListener);
				}
				
				if (onlyMovingCheckBox.isSelected()) {
					GlobalScreen.addNativeMouseMotionListener(mouseListener);
				}
			}
		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}
	
	private void stop() {
		clicker.stop();
	}
	
	private boolean canStart() {
		return (mouseLocationCheckBox.isSelected() || (xTextField.getValue() != null && yTextField.getValue() != null)) &&
				(delayTextField.getValue() != null && delayTextField.getValue() > 0) &&
				((numOfClicksTextField.getValue() != null && numOfClicksTextField.getValue() > 0) || clickInfinitelyCheckBox.isSelected());
	}
	
	private class AutoClickerListenerImpl implements AutoClickerListener {

		@Override
		public void started() {
			
		}

		@Override
		public void stopped() {
			GlobalScreen.removeNativeKeyListener(stopKeyListener);
			GlobalScreen.removeNativeMouseMotionListener(mouseListener);
			
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
		}
		
	}
	
}