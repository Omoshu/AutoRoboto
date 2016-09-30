package com.cionik.autoroboto.task;

import java.util.concurrent.TimeUnit;

import com.cionik.autoroboto.model.Time;

public class DelayTask implements Runnable {
	
	private Time delay;
	private long delayMillis;
	
	public DelayTask(Time delay) {
		this.delay = delay;
		delayMillis = delay.convert(TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(delayMillis);
		} catch (InterruptedException e) {
			//Ignore
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Delay: ");
		sb.append(delay.getDuration());
		sb.append(" ");
		sb.append(delay.getUnit());
		return sb.toString();
	}
	
}