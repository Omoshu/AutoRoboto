package com.cionik.autoroboto.task;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.cionik.autoroboto.model.Time;
import com.cionik.autoroboto.util.Listenable;

public class TaskScheduler extends Listenable<TaskListener> {
	
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private AtomicBoolean isSkipped = new AtomicBoolean();
	
	private Future<?> future;
	
	public void schedule(Task task, Time initialDelay, Time delay, int iterations) {
		if (future == null || future.isDone()) {
			notifyStartListeners();
			isSkipped.set(false);
			future = executor.scheduleWithFixedDelay(new TaskRunnable(task, iterations),
					initialDelay.convert(TimeUnit.MILLISECONDS), delay.convert(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
		}
	}
	
	public void shutdown() {
		if (isRunning()) {
			executor.shutdown();
			executor = Executors.newSingleThreadScheduledExecutor();
			notifyShutdownListeners();
		}
	}
	
	public void skip() {
		isSkipped.set(true);
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
		
		private int iterations;
		
		public TaskRunnable(Task task, int iterations) {
			this.task = task;
			this.iterations = iterations;
		}

		@Override
		public void run() {
			if (!isSkipped.getAndSet(false)) {
				task.execute();
				
				if (iterations != -1 && --iterations == 0) {
					shutdown();
				}
			}
		}
		
	}
	
}