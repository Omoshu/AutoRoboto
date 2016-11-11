package com.cionik.autoroboto.task;

import com.cionik.autoroboto.ui.TaskPanel;

public class TaskComposite implements Task {
	
	private Task[] tasks;
	
	public TaskComposite(Task[] tasks) {
		if (tasks == null) {
			throw new IllegalArgumentException("tasks cannot be null");
		}
		
		this.tasks = tasks;
	}

	@Override
	public void run() {
		for (Task t : tasks) {
			t.run();
		}
	}

	@Override
	public TaskPanel createPanel() {
		return null;
	}
	
}