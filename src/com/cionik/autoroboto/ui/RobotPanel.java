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

import com.cionik.autoroboto.task.TaskComposite;
import com.cionik.autoroboto.util.Listener;

import net.miginfocom.swing.MigLayout;

public class RobotPanel extends JPanel implements TaskPanel {

	private static final long serialVersionUID = 1L;
	
	private JList<Runnable> taskList = new JList<Runnable>(new DefaultListModel<Runnable>());
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
	public Runnable getTask() {
		ListModel<Runnable> model = taskList.getModel();
		Runnable[] tasks = new Runnable[model.getSize()];
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
		checkRemoveButton();
	}
	
	private void addListeners() {
		taskList.addListSelectionListener(new RemoveSelectionListener());
		addButton.addActionListener(new AddActionListener());
		removeButton.addActionListener(new RemoveActionListener());
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
	
	private class RemoveSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			checkRemoveButton();
		}
		
	}
	
	private class AddActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			TaskSelectionDialog dialog = new TaskSelectionDialog();
			dialog.show();
			Runnable task = dialog.getTask();
			if (task != null) {
				((DefaultListModel<Runnable>) taskList.getModel()).addElement(task);
			}
		}
		
	}
	
	private class RemoveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = taskList.getSelectedIndex();
			if (index != -1) {
				((DefaultListModel<Runnable>) taskList.getModel()).remove(index);
			}
		}
		
	}
	
}