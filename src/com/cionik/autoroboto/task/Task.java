package com.cionik.autoroboto.task;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Task {
	
	private AtomicBoolean isStopped = new AtomicBoolean();
	
	private AtomicBoolean isSkipped = new AtomicBoolean();
	
	public void stop() {
		isStopped.set(true);
	}
	
	public boolean isStopped() {
		return isStopped.get();
	}
	
	public void skip() {
		isSkipped.set(true);
	}
	
	public boolean getAndResetSkipped() {
		return isSkipped.compareAndSet(true, false);
	}
	
	public abstract void execute();
	
}