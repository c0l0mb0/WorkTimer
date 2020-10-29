package com.work_timer;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.menu_window.MenuWindow;

public class SystTray {
	TrayIcon trayIcon = null;
	Image image = null;
	WorkTimeCalculator workTimeCalculator = null;

	public SystTray(WorkTimeCalculator workTimeCalculator) {
		this.workTimeCalculator = workTimeCalculator;

		// checking for support
		if (!SystemTray.isSupported()) {
			return;
		}
		// get the systemTray of the system
		SystemTray systemTray = SystemTray.getSystemTray();
		this.image = Toolkit.getDefaultToolkit().getImage("src/img/idle.png");
		// popupmenu
		PopupMenu trayPopupMenu = new PopupMenu();

		MenuItem startTimer = new MenuItem("Start (Alt+Ctrl+s)");

		startTimer.addActionListener(e -> {
			if (!workTimeCalculator.isEmptyListOfObservedPrograms()) {
				workTimeCalculator.startProgramsObservation();
				//
			} else {
				JOptionPane.showMessageDialog(null,
						"The list of observated programs is empty. Please check the options menu.", "Error",
						JOptionPane.OK_OPTION);
			}

		});
		trayPopupMenu.add(startTimer);

		MenuItem openOptions = new MenuItem("Options");

		openOptions.addActionListener(e -> 
			new MenuWindow(this.workTimeCalculator)
		);
		trayPopupMenu.add(openOptions);

		MenuItem statistic = new MenuItem("Statist");
		statistic.addActionListener(e -> {
			if (!workTimeCalculator.isObserverRunning()) {
				JOptionPane.showMessageDialog(null, "Please press start before statistic", "Error",
						JOptionPane.OK_OPTION);
				return;
			}
			showStatistics();
		});
		trayPopupMenu.add(statistic);

		MenuItem about = new MenuItem("About");

		about.addActionListener(e -> 
			JOptionPane.showMessageDialog(null, "Dev Belov Ivan. bis80@bk.ru. https://github.com/c0l0mb0 ", "About",
					JOptionPane.OK_OPTION)
		);
		trayPopupMenu.add(about);

		MenuItem close = new MenuItem("Close");

		close.addActionListener(e -> {
			if (workTimeCalculator.isObserverRunning())
				showStatistics();
			System.exit(0);
		});
		trayPopupMenu.add(close);

		// setting tray icon
		this.trayIcon = new TrayIcon(this.image, "BookTimer", trayPopupMenu);
		this.trayIcon.setImageAutoSize(true);

		try {
			systemTray.add(this.trayIcon);
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}
	}

	public void setWorkIconColor() {
		this.image = Toolkit.getDefaultToolkit().getImage("src/img/work.png");
		this.trayIcon.setImage(this.image);
	}

	public void setIdleIconColor() {
		this.image = Toolkit.getDefaultToolkit().getImage("src/img/idle.png");
		this.trayIcon.setImage(this.image);
	}

	public void showStatistics() {

		ArrayList<ProgramTimer>  observedProgramTimers = (ArrayList<ProgramTimer>) workTimeCalculator.getObservedProgramTimers();
		StringBuilder resultMessage = new StringBuilder();
		observedProgramTimers.stream()
				.forEach(observedProgramTimer -> resultMessage.append(observedProgramTimer.getProgramName()
						+ " running time is - " + observedProgramTimer.getProgramWorkTime().getMinute() + " minuties "
						+ observedProgramTimer.getProgramWorkTime().getSecond() + " seconds " + "\r\n"));
		JOptionPane.showMessageDialog(null, resultMessage, "Statistic", JOptionPane.OK_OPTION);
	}
}
