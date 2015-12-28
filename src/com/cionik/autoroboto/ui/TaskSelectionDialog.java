package com.cionik.autoroboto.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cionik.autoroboto.task.Task;
import com.cionik.utils.model.Listener;
import com.cionik.utils.swing.SwingUtils;

import net.miginfocom.swing.MigLayout;

public class TaskSelectionDialog {
	
	private JDialog dialog = new JDialog((Frame) null, true);
	
	private JList<String> taskList = new JList<String>();
	
	private JScrollPane taskListScrollPane = new JScrollPane(taskList);
	
	private JButton backButton = new JButton("Back");
	
	private JButton nextButton = new JButton("Next");
	
	private JButton finishButton = new JButton("Finish");
	
	private JButton cancelButton = new JButton("Cancel");
	
	private Listener<Void> checkInputListener = new CheckInputListener();
	
	private TaskPanel taskPanel;
	
	private Task task;
	
	public TaskSelectionDialog() {
		initComponents();
		installListeners();
		layoutComponents();
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	public Task getTask() {
		return task;
	}
	
	private void initComponents() {
		taskList.setListData(new String[] {
			"Mouse Click",
			"Mouse Press",
			"Mouse Release",
			"Text Type",
			"Key Type",
			"Key Press",
			"Key Release",
			"Delay"
		});
		
		SwingUtils.equatePreferredSize(backButton, nextButton);
		SwingUtils.equatePreferredSize(finishButton, cancelButton);
		
		checkNextButton();
		checkFinishButton();
	}
	
	private void installListeners() {
		taskList.addListSelectionListener(new NextSelectionListener());
		backButton.addActionListener(new BackActionListener());
		nextButton.addActionListener(new NextActionListener());
		finishButton.addActionListener(new FinishActionListener());
		cancelButton.addActionListener(new CancelActionListener());
	}
	
	private void layoutComponents() {
		dialog.setLayout(new MigLayout());
		dialog.add(taskListScrollPane, "span, grow, wrap");
		dialog.add(backButton, "split 2, align center");
		dialog.add(nextButton, "align center, wrap");
		dialog.add(finishButton, "split 2, align center");
		dialog.add(cancelButton, "align center");
		
		dialog.pack();
		dialog.setLocationByPlatform(true);
	}
	
	private TaskPanel createPanel(String name) {
		switch (name) {
			case "Mouse Click":
				return new MouseTaskPanel(MouseEvent.MOUSE_CLICKED);
			case "Mouse Press":
				return new MouseTaskPanel(MouseEvent.MOUSE_PRESSED);
			case "Mouse Release":
				return new MouseTaskPanel(MouseEvent.MOUSE_RELEASED);
			case "Text Type":
				return new TextTypeTaskPanel();
			case "Key Type":
				return new KeyTaskPanel(KeyEvent.KEY_TYPED);
			case "Key Press":
				return new KeyTaskPanel(KeyEvent.KEY_PRESSED);
			case "Key Release":
				return new KeyTaskPanel(KeyEvent.KEY_RELEASED);
			case "Delay":
				return new DelayTaskPanel();
			default:
				return null;
		}
	}
	
	private void checkNextButton() {
		nextButton.setEnabled(taskPanel == null && taskList.getSelectedIndex() != -1);
	}
	
	private void checkFinishButton() {
		finishButton.setEnabled(taskPanel != null && taskPanel.hasValidInput());
	}
	
	private class BackActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.getContentPane().remove(0);
			dialog.getContentPane().add(taskListScrollPane, "span, grow, wrap", 0);
			taskPanel = null;
			backButton.setEnabled(false);
			nextButton.setEnabled(taskList.getSelectedIndex() != -1);
			dialog.pack();
			dialog.revalidate();
			dialog.repaint();
		}
		
	}
	
	private class NextActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String taskName = taskList.getSelectedValue();
			if (taskName != null) {
				taskPanel = createPanel(taskName);
				taskPanel.addInputChangeListener(checkInputListener);
				dialog.getContentPane().remove(0);
				dialog.getContentPane().add(taskPanel.getPanel(), "span, align center, wrap", 0);
				nextButton.setEnabled(false);
				backButton.setEnabled(true);
				dialog.pack();
				dialog.revalidate();
				dialog.repaint();
			}
		}
		
	}
	
	private class FinishActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (taskPanel != null) {
				task = taskPanel.getTask();
			}
			dialog.dispose();
		}
		
	}
	
	private class CancelActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}
		
	}
	
	private class NextSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			checkNextButton();
		}
		
	}
	
	private class CheckInputListener implements Listener<Void> {

		@Override
		public void handleEvent(Void t) {
			checkFinishButton();
		}
		
	}
	
}