package com.cionik.autoroboto.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicArrowButton;

import com.cionik.autoroboto.task.Task;
import com.cionik.autoroboto.task.TaskComposite;
import com.cionik.autoroboto.task.TaskType;
import com.cionik.autoroboto.util.Listener;

import net.miginfocom.swing.MigLayout;

public class RobotPanel extends JPanel implements TaskPanel {

	private static final long serialVersionUID = 1L;
	
	private JList<Task> taskList = new JList<Task>(new DefaultListModel<Task>());
	private JScrollPane taskListScrollPane = new JScrollPane(taskList);
	private JButton moveUpButton = new BasicArrowButton(BasicArrowButton.NORTH);
	private JButton moveDownButton = new BasicArrowButton(BasicArrowButton.SOUTH);
	private JButton addButton = new JButton("Add...");
	private JButton editButton = new JButton("Edit...");
	private JButton removeButton = new JButton("Remove");
	
	private TaskType[] taskTypes;
	
	public RobotPanel(TaskType[] taskTypes) {
		super();
		
		this.taskTypes = taskTypes;
		
		addListeners();
		initComponents();
		layoutComponents();
	}
	
	@Override
	public boolean hasValidInput() {
		return taskList.getModel().getSize() > 0;
	}

	@Override
	public Task getTask() {
		ListModel<Task> model = taskList.getModel();
		Task[] tasks = new Task[model.getSize()];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = model.getElementAt(i);
		}
		return new TaskComposite(tasks);
	}

	@Override
	public JPanel getPanel() {
		return this;
	}
	
	@Override
	public void addInputChangeListener(Listener<Void> listener) {
		taskList.getModel().addListDataListener(new ListDataListenerAdapter(listener));
	}
	
	private void initComponents() {
		checkButtons();
	}
	
	private void addListeners() {
		taskList.addListSelectionListener(new TaskListSelectionListener());
		moveUpButton.addActionListener(new MoveUpActionListener());
		moveDownButton.addActionListener(new MoveDownActionListener());
		addButton.addActionListener(new AddActionListener());
		editButton.addActionListener(new EditActionListener());
		removeButton.addActionListener(new RemoveActionListener());
	}
	
	private void layoutComponents() {
		setLayout(new MigLayout());
		add(taskListScrollPane, "span 1 2, push, grow");
		add(moveUpButton, "pushy, aligny bottom, wrap");
		add(moveDownButton, "pushy, aligny top, wrap");
		add(addButton, "span, split 3, align center");
		add(editButton);
		add(removeButton);
	}
	
	private void checkButtons() {
		int index = taskList.getSelectedIndex();
		boolean enabled = index != -1;
		editButton.setEnabled(enabled);
		removeButton.setEnabled(enabled);
		moveUpButton.setEnabled(enabled && index != 0);
		moveDownButton.setEnabled(enabled && index != taskList.getModel().getSize() - 1);
	}
	
	private class TaskListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			checkButtons();
		}
		
	}
	
	private class MoveUpActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = taskList.getSelectedIndex();
			if (index > 0) {
				DefaultListModel<Task> model = (DefaultListModel<Task>) taskList.getModel();
				Task task = model.remove(index);
				model.add(index - 1, task);
				taskList.setSelectedIndex(index - 1);
			}
		}
		
	}
	
	private class MoveDownActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultListModel<Task> model = (DefaultListModel<Task>) taskList.getModel();
			int index = taskList.getSelectedIndex();
			if (index < model.getSize() - 1) {
				Task task = model.remove(index);
				model.add(index + 1, task);
				taskList.setSelectedIndex(index + 1);
			}
		}
		
	}
	
	private class AddActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			TaskSelectionDialog selectionDialog = new TaskSelectionDialog(taskTypes);
			selectionDialog.show();
			TaskType type = selectionDialog.getTaskType();
			if (type != null) {
				TaskDialog taskDialog = new TaskDialog(type);
				taskDialog.show();
				Task task = taskDialog.getTask();
				if (task != null) {
					((DefaultListModel<Task>) taskList.getModel()).addElement(task);
				}
			}
		}
		
	}
	
	private class EditActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = taskList.getSelectedIndex();
			if (index != -1) {
				DefaultListModel<Task> model = (DefaultListModel<Task>) taskList.getModel();
				TaskDialog dialog = new TaskDialog(model.get(index));
				dialog.show();
				Task task = dialog.getTask();
				if (task != null) {
					model.set(index, task);
				}
			}
		}
		
	}
	
	private class RemoveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = taskList.getSelectedIndex();
			if (index != -1) {
				((DefaultListModel<Task>) taskList.getModel()).remove(index);
			}
		}
		
	}
	
}