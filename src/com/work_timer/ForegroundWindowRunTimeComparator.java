package com.work_timer;

import java.util.ArrayList;

public class ForegroundWindowRunTimeComparator extends ForegroundWindow implements Runnable {
	private ArrayList<ProgramTimer> observedProgramTimers = new ArrayList<>();
	private String nameOfCurrentWindow = null;
	private boolean isActiveOneOfObserveredProgram = false;
	private SystTray systTray = null;

	public ForegroundWindowRunTimeComparator(ArrayList<ProgramTimer> observedProgramTimers,  SystTray systTray) {
		this.observedProgramTimers = observedProgramTimers;
		this.systTray = systTray;
	}

	public void run() {

		while (true) {

			try {
				Thread.sleep(100);
				nameOfCurrentWindow = ForegroundWindow.getNameOfCurrentWindow();
			} catch (Exception e) {
				e.printStackTrace();
			}
			isActiveOneOfObserveredProgram = false;
			observedProgramTimers.stream().forEach(observedProgramTimer -> {
				if (nameOfCurrentWindow.contains(observedProgramTimer.programName)) {
					isActiveOneOfObserveredProgram = true;
					if (observedProgramTimer.timerPause) {
						observedProgramTimer.resumeTimer();
					}
				} else {
					observedProgramTimer.pauseTimer();
				}
			});
			if (isActiveOneOfObserveredProgram) {
				systTray.setWorkIconColor();
			} else {
				systTray.setIdleIconColor();
			}
		}
	}

	public boolean isActiveOneOfObserveredProgram() {
		return isActiveOneOfObserveredProgram;
	}

}
