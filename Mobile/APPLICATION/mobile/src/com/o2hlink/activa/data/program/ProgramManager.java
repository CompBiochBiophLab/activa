package com.o2hlink.activa.data.program;

import java.util.Hashtable;

import com.o2hlink.activa.data.calendar.Event;

public class ProgramManager {

	/**
	 * Static constants for referring to program IDs.
	 */
	public static final int PROG_WALKING = 6;
	public static final int PROG_DAILY = 3;
	public static final int PROG_PHYSICAL = 4;
	public static final int PROG_DISTANCE = 10;
	public static final int PROG_TEST = 11;
	public static final int PROG_WIIACTIVE = 12;

	/**
	 * Static constants for referring to program types.
	 */
	public static final int PROGTYPE_FOREGROUND = 0;
	public static final int PROGTYPE_BACKGROUND = 1;

	/**
	 * Instance of the Program manager.
	 */
	private static ProgramManager instance;
	
	/**
	 * List of available programs.
	 */
	public Hashtable<Integer, Program> programList;
	
	public Event eventAssociated;
	
	/**
	 * Private constructor.
	 */
	private ProgramManager() {
		this.programList = new Hashtable<Integer, Program>();
//		this.programList.put(PROG_PHYSICAL, new Physical());
		this.programList.put(PROG_WALKING, new Walking());
//		this.programList.put(PROG_TEST, new Test());
//		this.programList.put(PROG_WIIACTIVE, new WiiActive());
//		this.programList.put(PROG_DISTANCE, new Distance());
	}
	
	/**
	 * Method for getting the unique instance of the program manager it can exist.
	 * 
	 * @return The instance of ProgramManager.
	 */
	public static ProgramManager getInstance() {
		if (ProgramManager.instance == null) ProgramManager.instance = new ProgramManager();
		return ProgramManager.instance;
	}
	
	/**
	 * Method for freeing the instance of the manager.
	 */
	public static void freeInstance() {
		ProgramManager.instance = null;
	}
	
	public void startProgram(int id) {
		this.programList.get(id).startProgram();
	}
}
