package com.cionik.autoroboto.task;

import java.util.concurrent.TimeUnit;

import com.cionik.autoroboto.model.Time;
import com.cionik.autoroboto.ui.SleepTaskPanel;
import com.cionik.autoroboto.ui.TaskPanel;

public class SleepTask implements Task {
	
	private Time duration;
	private long durationMillis;
	
	public SleepTask(Time duration) {
		this.duration = duration;
		durationMillis = duration.convert(TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(durationMillis);
		} catch (InterruptedException e) {
			//Ignore
		}
	}
	
	@Override
	public TaskPanel createPanel() {
		return new SleepTaskPanel(duration);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Sleep: ");
		sb.append(duration.getDuration());
		sb.append(" ");
		sb.append(duration.getUnit());
		return sb.toString();
	}
	
}