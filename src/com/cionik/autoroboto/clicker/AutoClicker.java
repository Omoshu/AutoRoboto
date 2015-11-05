package com.cionik.autoroboto.clicker;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.cionik.autoroboto.MouseButton;
import com.cionik.autoroboto.Time;
import com.cionik.utils.check.Condition;
import com.cionik.utils.model.Listenable;

public class AutoClicker extends Listenable<AutoClickerListener> {
	
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private AtomicBoolean skip = new AtomicBoolean(false);
	
	private Future<?> future;
	
	private Robot robot;
	
	private MouseButton button;
	
	private Point point;
	
	private Time initialDelay;
	
	private Time delay;
	
	private int numOfClicks;
	
	private int clicksCompleted;
	
	public AutoClicker(MouseButton button, Point point, Time initialDelay, Time delay, int numOfClicks) throws AWTException {
		Condition.checkNullArgument(button);
		Condition.checkNullArgument(delay);
		
		this.button = button;
		this.point = point;
		this.initialDelay = initialDelay;
		this.delay = delay;
		this.numOfClicks = numOfClicks;
		
		robot = new Robot();
	}
	
	public void start() {
		/*SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {

			@Override
			protected Object doInBackground() throws Exception {
				if (initialDelay != null) {
					Thread.sleep(initialDelay.getUnit().toMillis(initialDelay.getDuration()));
				}
				
				long delayMilli = delay.getUnit().toMillis(delay.getDuration());
				future = executor.scheduleAtFixedRate(new Click(),
						delayMilli + initialDelay.getUnit().toMillis(initialDelay.getDuration()),
						delayMilli,
						TimeUnit.MILLISECONDS);
				
				return null;
			}
			
		};
		worker.execute();*/
		long delayMilli = delay.getUnit().toMillis(delay.getDuration());
		future = executor.scheduleAtFixedRate(new Click(),
				delayMilli + initialDelay.getUnit().toMillis(initialDelay.getDuration()),
				delayMilli,
				TimeUnit.MILLISECONDS);
		notifyStartListeners();
	}
	
	public void stop() {
		if (!executor.isShutdown()) {
			executor.shutdown();
			notifyStopListeners();
		}
	}
	
	public void skip() {
		skip.set(true);
	}
	
	public boolean isRunning() {
		return future != null && !future.isDone();
	}
	
	private void notifyStartListeners() {
		for (AutoClickerListener l : getListeners()) {
			l.started();
		}
	}
	
	private void notifyStopListeners() {
		for (AutoClickerListener l : getListeners()) {
			l.stopped();
		}
	}
	
	private class Click implements Runnable {

		@Override
		public void run() {
			if (skip.compareAndSet(true, false)) {
				return;
			}
			
			if (point != null) {
				robot.mouseMove(point.x, point.y);
			}
			
			robot.mousePress(button.getMask());
			robot.mouseRelease(button.getMask());
			
			if (++clicksCompleted == numOfClicks) {
				executor.shutdown();
			}
		}
		
	}
	
}