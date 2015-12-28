package com.cionik.autoroboto.task;

public class MultiplexTask implements Task {
	
	private Task[] tasks;
	
	public MultiplexTask(Task[] tasks) {
		this.tasks = tasks;
	}

	@Override
	public void execute() {
		for (Task t : tasks) {
			t.execute();
		}
	}
	
}