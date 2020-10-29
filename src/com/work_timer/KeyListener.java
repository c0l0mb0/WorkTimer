package com.work_timer;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;

public class KeyListener implements Runnable {
	WorkTimeCalculator workTimeCalculatorction = null;

	public KeyListener(WorkTimeCalculator workTimeCalculatorction) {
		super();
		this.workTimeCalculatorction = workTimeCalculatorction;
	}

	@Override
	public void run() {

		User32 user32 = User32.INSTANCE;

		if (!user32.RegisterHotKey(null, 1, User32.MOD_ALT | User32.MOD_CONTROL, 0x53)) {// s key
			System.out.println("Error");
			return;
		}

		WinUser.MSG msg = new WinUser.MSG();

		while (true) {
			while (user32.PeekMessage(msg, null, 0, 0, 1)) {
				if (msg.message == WinUser.WM_HOTKEY) {
//					System.out.println("Yattaaaa. Hotkey with id " + WinUser.WM_HOTKEY);
					workTimeCalculatorction.startProgramsObservation();
					user32.UnregisterHotKey(null, 1);
				}

				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}