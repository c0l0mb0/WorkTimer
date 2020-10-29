package com.work_timer;

public class Timer implements Runnable {

	 boolean timerState = false;
	 boolean timerPause = false;
	 long startTime = 0;
	 long elapsedTime = 0;

	public  long getElapsedTime() {
		return elapsedTime;
	}

	@Override
	public void run() {

		if (elapsedTime > 0) {
			startTime = System.currentTimeMillis() - elapsedTime;
		} else {
			startTime = System.currentTimeMillis();
		}
		timerState = true;
		while (timerState) {
			try {
				Thread.sleep(800);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!timerPause) {
				elapsedTime = System.currentTimeMillis() - startTime;
			}		
		}
	}

	public void restartTimer() {
		this.elapsedTime = 0;
	}

	public void pauseTimer() {
		this.timerPause = true;
	}

	public void resumeTimer() {
		this.timerPause = false;
		this.startTime = System.currentTimeMillis() - elapsedTime;
	}

}
