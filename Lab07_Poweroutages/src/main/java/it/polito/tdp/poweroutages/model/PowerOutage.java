package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutage {
	private int nercIndex;
	private LocalDateTime start;
	private LocalDateTime stop;
	private int affected;
	private long duration;

	public PowerOutage(int nercIndex, LocalDateTime start, LocalDateTime stop, int affected) {
		this.nercIndex = nercIndex;
		this.start = start;
		this.stop = stop;
		this.affected = affected;
		duration = ChronoUnit.HOURS.between(start, stop);
	}

	public int getNercIndex() {
		return nercIndex;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getStop() {
		return stop;
	}

	public int getAffected() {
		return affected;
	}

	public long getDuration() {
		return duration;
	}

	@Override
	public String toString() {
		return String.format("%s %s - %d %d", start, stop, duration, affected);
	}

}
