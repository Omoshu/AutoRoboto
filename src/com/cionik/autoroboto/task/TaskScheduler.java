package com.cionik.autoroboto.task;

import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.KeyStroke;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import com.cionik.autoroboto.model.Time;
import com.cionik.autoroboto.util.Listenable;

public class TaskScheduler extends Listenable<TaskListener> {
	
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private AtomicBoolean skipped = new AtomicBoolean();
	private NativeMouseMotionListener skipListener;
	private NativeKeyListener stopKeyListener;
	private Future<?> future;
	
	public void schedule(Runnable task, Time initialDelay, Time delay, int iterations, boolean mouseMotionPause, KeyStroke stopKeyStroke) {
		if (!isRunning()) {
			notifyStartListeners();
			skipped.set(false);
			
			if (mouseMotionPause && GlobalScreen.isNativeHookRegistered()) {
				skipListener = new SkipListener();
				GlobalScreen.addNativeMouseMotionListener(skipListener);
			}
			
			future = executor.scheduleWithFixedDelay(new TaskTimer(task, iterations),
					initialDelay.convert(TimeUnit.MILLISECONDS), delay.convert(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
			
			if (stopKeyStroke != null && GlobalScreen.isNativeHookRegistered()) {
				stopKeyListener = new StopKeyListener(stopKeyStroke);
				GlobalScreen.addNativeKeyListener(stopKeyListener);
			}
		}
	}
	
	public void shutdown() {
		if (isRunning()) {
			GlobalScreen.removeNativeMouseMotionListener(skipListener);
			GlobalScreen.removeNativeKeyListener(stopKeyListener);
			skipListener = null;
			stopKeyListener = null;
			
			executor.shutdown();
			executor = Executors.newSingleThreadScheduledExecutor();
			notifyShutdownListeners();
		}
	}
	
	public void skip() {
		skipped.set(true);
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
	
	private class TaskTimer implements Runnable {
		
		private Runnable task;
		private int iterations;
		
		public TaskTimer(Runnable task, int iterations) {
			this.task = task;
			this.iterations = iterations;
		}

		@Override
		public void run() {
			if (!skipped.getAndSet(false)) {
				task.run();
				
				if (iterations != -1 && --iterations == 0) {
					shutdown();
				}
			}
		}
		
	}
	
	private class SkipListener implements NativeMouseMotionListener {

		@Override
		public void nativeMouseDragged(NativeMouseEvent e) {
		}

		@Override
		public void nativeMouseMoved(NativeMouseEvent e) {
			if (isRunning()) {
				skip();
			}
		}
		
	}
	
	private class StopKeyListener implements NativeKeyListener {
		
		private String keyText;
		private String modifierText;
		
		public StopKeyListener(KeyStroke keystroke) {
			keyText = KeyEvent.getKeyText(keystroke.getKeyCode());
			modifierText = KeyEvent.getModifiersExText(keystroke.getModifiers());
		}

		@Override
		public void nativeKeyPressed(NativeKeyEvent e) {
			if (keyText.equals(NativeKeyEvent.getKeyText(e.getKeyCode())) &&
				modifierText.equals(NativeKeyEvent.getModifiersText(e.getModifiers()))) {
				if (isRunning()) {
					shutdown();
				}
			}
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent e) {
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent e) {
		}
		
	}
	
}