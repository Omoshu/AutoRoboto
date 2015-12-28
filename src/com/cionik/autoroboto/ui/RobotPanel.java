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

import com.cionik.autoroboto.task.MultiplexTask;
import com.cionik.autoroboto.task.Task;
import com.cionik.utils.model.Listener;

import net.miginfocom.swing.MigLayout;

public class RobotPanel extends JPanel implements TaskPanel {

	private static final long serialVersionUID = 1L;
	
	private JList<Task> taskList = new JList<Task>(new DefaultListModel<Task>());
	
	private JScrollPane taskListScrollPane = new JScrollPane(taskList);
	
	private JButton addButton = new JButton("Add...");
	
	private JButton removeButton = new JButton("Remove");
	
	public RobotPanel() {
		super();
		
		initComponents();
		addListeners();
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
		return new MultiplexTask(tasks);
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
		checkRemoveButton();
	}
	
	private void addListeners() {
		addButton.addActionListener(new AddActionListener());
		removeButton.addActionListener(new RemoveActionListener());
		taskList.addListSelectionListener(new RemoveSelectionListener());
	}
	
	private void layoutComponents() {
		setLayout(new MigLayout());
		add(taskListScrollPane, "span, push, grow, wrap");
		add(addButton, "span, split 2, align center");
		add(removeButton, "align center, wrap");
	}
	
	private void checkRemoveButton() {
		removeButton.setEnabled(taskList.getSelectedIndex() != -1);
	}
	
	private class AddActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			TaskSelectionDialog dialog = new TaskSelectionDialog();
			dialog.show();
			Task task = dialog.getTask();
			if (task != null) {
				((DefaultListModel<Task>) taskList.getModel()).addElement(task);
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
	
	private class RemoveSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			checkRemoveButton();
		}
		
	}
	
}