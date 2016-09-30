package com.cionik.autoroboto.task;

public class MultiplexTask implements Runnable {
	
	private Runnable[] tasks;
	
	public MultiplexTask(Runnable[] tasks) {
		this.tasks = tasks;
	}

	@Override
	public void run() {
		for (Runnable r : tasks) {
			r.run();
		}
	}
	
}