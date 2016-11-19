package com.cionik.autoroboto.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.cionik.autoroboto.task.Task;
import com.cionik.autoroboto.task.TaskType;
import com.cionik.autoroboto.util.Listener;

import net.miginfocom.swing.MigLayout;

public class TaskDialog {
	
	private JDialog dialog = new JDialog((Frame) null, true);
	private JButton finishButton = new JButton("Finish");
	private JButton cancelButton = new JButton("Cancel");
	private TaskPanel taskPanel;
	private Task task;
	
	public TaskDialog(TaskType type) {
		taskPanel = type.createPanel();
		
		addListeners();
		initComponents(type);
		layoutComponents();
	}
	
	private void addListeners() {
		taskPanel.addInputChangeListener(new CheckInputListener());
		cancelButton.addActionListener(new CancelActionListener());
		finishButton.addActionListener(new FinishActionListener());
	}
	
	private void initComponents(TaskType type) {
		dialog.setTitle(type.toString());
		checkFinishButton();
	}
	
	private void layoutComponents() {
		dialog.setLayout(new MigLayout());
		dialog.add(taskPanel.getPanel(), "span, growx, wrap");
		dialog.add(cancelButton, "split 2, growx, align center");
		dialog.add(finishButton, "growx, align center");
		
		dialog.pack();
		dialog.setLocationByPlatform(true);
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	public Task getTask() {
		return task;
	}
	
	private void checkFinishButton() {
		finishButton.setEnabled(taskPanel.hasValidInput());
	}
	
	private class CheckInputListener implements Listener<Void> {

		@Override
		public void handleEvent(Void t) {
			checkFinishButton();
		}
		
	}
	
	private class CancelActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}
		
	}
	
	private class FinishActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			task = taskPanel.getTask();
			dialog.dispose();
		}
		
	}
	
}