package com.work_timer;

import java.util.ArrayList;
import java.util.List;

public class WorkTimeCalculator {

	private SystTray systTray = null;
	private ProgramsListLoader programsListLoader = null;
	private ArrayList<String> observedProgramNames = new ArrayList<>();
	private ArrayList<ProgramTimer> observedProgramTimers = new ArrayList<>();
	private boolean isObserverRunning = false;

	public boolean isObserverRunning() {
		return isObserverRunning;
	}

	public List<ProgramTimer> getObservedProgramTimers() {
		return observedProgramTimers;
	}

	public List<String> getObservedProgramNames() {
		try {
			this.observedProgramNames = programsListLoader.loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return observedProgramNames;
	}

	public void go() {
		this.systTray = new SystTray(this);

		KeyListener keyListener = new KeyListener(this);
		Thread thread = new Thread(keyListener);
		thread.start();

		programsListLoader = new ProgramsListLoader("src/data/data.ser");
		try {
			this.observedProgramNames = programsListLoader.loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void startProgramsObservation() {
		if (isEmptyListOfObservedPrograms() || isObserverRunning) {
			return;
		}

		this.observedProgramNames.stream()
				.forEach(observedProgName -> observedProgramTimers.add(new ProgramTimer(observedProgName)));
		this.observedProgramTimers.stream().forEach(observedProgramTimer -> {
			Thread timerThread = new Thread(observedProgramTimer);
			timerThread.start();
		});
		ForegroundWindowRunTimeComparator foregroundWindowRunTimeComparator = new ForegroundWindowRunTimeComparator(
				observedProgramTimers, systTray);
		Thread mainThread = new Thread(foregroundWindowRunTimeComparator);
		mainThread.start();

		isObserverRunning = true;
	}

	public boolean isEmptyListOfObservedPrograms() {
		if (observedProgramNames == null || observedProgramNames.isEmpty()) 
			return true;		
		return false;
	}

	public boolean saveObservedProgramNames(List<String> observedProgramNames) {
		if (observedProgramNames != null)
			programsListLoader.saveData(observedProgramNames);
		return true;
	}

}