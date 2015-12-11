package com.cionik.autoroboto.task;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cionik.autoroboto.Time;
import com.cionik.utils.model.Listenable;

public class TaskScheduler extends Listenable<TaskListener> {
	
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private Future<?> future;
	
	public void schedule(Task task, Time initialDelay, Time delay) {
		if (future == null || future.isDone()) {
			notifyStartListeners();
			future = executor.scheduleAtFixedRate(new TaskRunnable(task),
					initialDelay.convert(TimeUnit.MILLISECONDS), delay.convert(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
		}
	}
	
	public void shutdown() {
		executor.shutdown();
		executor = Executors.newSingleThreadScheduledExecutor();
		notifyShutdownListeners();
	}
	
	public boolean isRunning() {
		return future != null && !future.isDone();
	}
	
	private void notifyStartListeners() {
		for (TaskListener l : getListeners()) {
			l.beforeStart();
		}
	}
	
	private void notifyShutdownListeners() {
		for (TaskListener l : getListeners()) {
			l.afterShutdown();
		}
	}
	
	private class TaskRunnable implements Runnable {
		
		private Task task;
		
		public TaskRunnable(Task task) {
			this.task = task;
		}

		@Override
		public void run() {
			if (!task.isStopped() && !task.getAndResetSkipped()) {
				task.execute();
			}
		}
		
	}
	
}