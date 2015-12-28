package com.cionik.autoroboto.model;

import java.util.concurrent.TimeUnit;

public class Time {
	
	private long duration;
	
	private TimeUnit unit;
	
	public Time(long duration, TimeUnit unit) {
		this.duration = duration;
		this.unit = unit;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public TimeUnit getUnit() {
		return unit;
	}
	
	public long convert(TimeUnit newUnit) {
		return unit.convert(duration, newUnit);
	}
	
}