package com.cionik.autoroboto.model;

import java.util.concurrent.TimeUnit;

public class Time {
	
	private TimeUnit unit;
	private long duration;
	
	public Time(TimeUnit unit, long duration) {
		if (unit == null) {
			throw new IllegalArgumentException("unit cannot be null");
		} else if (duration < 0) {
			throw new IllegalArgumentException("duration cannot be negative");
		}
			
		this.unit = unit;
		this.duration = duration;
	}
	
	public TimeUnit getUnit() {
		return unit;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public long convert(TimeUnit newUnit) {
		return newUnit.convert(duration, unit);
	}
	
}