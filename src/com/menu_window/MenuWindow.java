package com.menu_window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import com.work_timer.WorkTimeCalculator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuWindow {

	private JFrame frmMenu;
	private JTextField textField;
	WorkTimeCalculator workTimeCalculator;

	/**
	 * Create the application.
	 */
	public MenuWindow(WorkTimeCalculator workTimeCalculator) {
		this.workTimeCalculator = workTimeCalculator;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMenu = new JFrame();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(frmMenu);
		frmMenu.pack();

		frmMenu.setTitle("Otions");
		frmMenu.setResizable(false);
		frmMenu.setBounds(100, 100, 263, 280);
//		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMenu.getContentPane().setLayout(null);

		JButton btnSaveListOf = new JButton("Save watch list changes");

		btnSaveListOf.setBounds(41, 22, 171, 23);
		frmMenu.getContentPane().add(btnSaveListOf);

		JLabel lblNewLabel = new JLabel("Watch list:");
		lblNewLabel.setBounds(102, 122, 55, 14);
		frmMenu.getContentPane().add(lblNewLabel);

		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		list.setBounds(41, 147, 171, 74);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		frmMenu.getContentPane().add(list);

		JButton btnAdd = new JButton("Add");

		btnAdd.setBounds(41, 56, 65, 23);
		frmMenu.getContentPane().add(btnAdd);

		textField = new JTextField();
		textField.setBounds(116, 56, 96, 20);
		frmMenu.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnDeleteSelected = new JButton("Delete selected");

		btnDeleteSelected.setBounds(41, 88, 171, 23);
		frmMenu.getContentPane().add(btnDeleteSelected);

		btnDeleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1) {
					listModel.remove(index);
				}
			}
		});

		btnSaveListOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> listOfObservedPrograms = new ArrayList<String>();
				if (!listModel.isEmpty()) {
					for (int i = 0; i < list.getModel().getSize(); i++) {
						Object item = list.getModel().getElementAt(i);
						listOfObservedPrograms.add( String.valueOf(item));
					}
					workTimeCalculator.saveObservedProgramNames(listOfObservedPrograms);
				}
			}
		});

		frmMenu.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				if (!workTimeCalculator.isEmptyListOfObservedPrograms()) {
					ArrayList<String> listOfObservedPrograms = (ArrayList<String>) workTimeCalculator.getObservedProgramNames();
					listOfObservedPrograms.stream().forEach(observedProgName -> listModel.addElement(observedProgName));
				}

			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText() != "") {
					listModel.addElement(textField.getText());
					textField.setText("");
				}

			}
		});

		frmMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frmMenu.setVisible(true);
	}
}
