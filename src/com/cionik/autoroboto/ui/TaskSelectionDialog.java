package com.cionik.autoroboto.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cionik.autoroboto.task.TaskType;
import com.cionik.autoroboto.util.DoubleClickEvent;
import com.cionik.autoroboto.util.Listener;
import com.cionik.autoroboto.util.SwingUtils;

import net.miginfocom.swing.MigLayout;

public class TaskSelectionDialog {
	
	private JDialog dialog = new JDialog((Frame) null, true);
	private JList<TaskType> taskList = new JList<TaskType>();
	private JScrollPane taskListScrollPane = new JScrollPane(taskList);
	private JButton cancelButton = new JButton("Cancel");
	private JButton nextButton = new JButton("Next");
	private TaskType taskType;
	
	public TaskSelectionDialog(TaskType[] taskTypes) {
		addListeners();
		initComponents(taskTypes);
		layoutComponents();
	}
	
	private void addListeners() {
		SwingUtils.addDoubleClickListener(taskList, new TaskDoubleClickListener());
		taskList.addListSelectionListener(new TaskSelectionListener());
		cancelButton.addActionListener(new CancelActionListener());
		nextButton.addActionListener(new NextActionListener());
	}
	
	private void initComponents(TaskType[] taskTypes) {
		dialog.setTitle("New Task");
		taskList.setListData(taskTypes);
		
		checkNextButton();
	}
	
	private void layoutComponents() {
		dialog.setLayout(new MigLayout());
		dialog.add(taskListScrollPane, "span, grow, wrap");
		dialog.add(cancelButton, "split 2, growx, align center");
		dialog.add(nextButton, "growx, align center");
		
		dialog.pack();
		dialog.setLocationByPlatform(true);
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	public TaskType getTaskType() {
		return taskType;
	}
	
	private void checkNextButton() {
		nextButton.setEnabled(taskList.getSelectedIndex() != -1);
	}
	
	private void select(TaskType taskType) {
		this.taskType = taskType;
		if (taskType != null) {
			dialog.dispose();
		}
	}
	
	private class TaskDoubleClickListener implements Listener<DoubleClickEvent<JList<TaskType>, TaskType>> {

		@Override
		public void handleEvent(DoubleClickEvent<JList<TaskType>, TaskType> e) {
			select(e.getItem());
		}
		
	}
	
	private class TaskSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			checkNextButton();
		}
		
	}
	
	private class NextActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			select(taskList.getSelectedValue());
		}
		
	}
	
	private class CancelActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}
		
	}
	
}