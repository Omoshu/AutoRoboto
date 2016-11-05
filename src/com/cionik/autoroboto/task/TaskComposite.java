package com.cionik.autoroboto.task;

public class TaskComposite implements Runnable {
	
	private Runnable[] tasks;
	
	public TaskComposite(Runnable[] tasks) {
		if (tasks == null) {
			throw new IllegalArgumentException("tasks cannot be null");
		}
		
		this.tasks = tasks;
	}

	@Override
	public void run() {
		for (Runnable r : tasks) {
			r.run();
		}
	}
	
}