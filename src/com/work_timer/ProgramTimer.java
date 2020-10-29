package com.work_timer;

import java.time.LocalTime;

public class ProgramTimer extends Timer {
	String programName;

	public ProgramTimer(String programName) {
		super();
		this.programName = programName;
	}

	public LocalTime getProgramWorkTime() {
		return LocalTime.ofSecondOfDay(this.getElapsedTime() / 1000);
	}

	public String getProgramName() {
		return programName;
	}
}
