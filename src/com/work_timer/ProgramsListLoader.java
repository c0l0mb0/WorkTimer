package com.work_timer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProgramsListLoader {
	private File dataFile;
	
	public ProgramsListLoader(String path) {
		super();
		dataFile = new File(path);

		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveData(List<String> listOfObservedPrograms) {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(dataFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				ObjectOutputStream oos = null;
				try {
					oos = new ObjectOutputStream(fos);
					if (oos != null) {
						oos.writeObject(listOfObservedPrograms);
					}
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> loadData() throws Exception{

		FileInputStream fis = null;
		ArrayList<String> listOfObservedPrograms = null;
		try {
			fis = new FileInputStream(dataFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				ObjectInputStream ois = null;
				try {
					ois = new ObjectInputStream(fis);
					if (ois != null) {
						try {							
							listOfObservedPrograms = (ArrayList<String>) ois.readObject();							
						} catch (ClassNotFoundException e) {
							throw  new ClassNotFoundException();
						}
					}
					ois.close();					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listOfObservedPrograms;
	}
	

}
