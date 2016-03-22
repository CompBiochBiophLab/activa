package com.o2hlink.activa.data.program;

/**
 * @author Adrian Rejas<P>
 * 
 * This interface defines a set of functions to be implemented by each sensor class.
 * 
 */
public abstract class Program {
	
	/**
	 * Name of the program.
	 */
	public String name;
	
	/**
	 * Description of the program.
	 */
	public String description;

	/**
	 * Icon R.id used for the program
	 */
	public int icon;
	
	/**
	 * Id of the program.
	 */
	public int id;
	
	/**
	 * Type of the program (Foreground or background)
	 */
	public int type;
	
	/**
	 * State of the working of the program (0 for inactive, i>0 for active at state i);
	 */
	public int state;
	
	/**
	 * Start the working of the program.
	 */
	public abstract void startProgram();
	
	/**
	 * Finish the working of the program.
	 */
	public abstract void finishProgram();
	
	/**
	 * Go to next step of the program.
	 */
	public abstract void nextStep();
	
	/**
	 * Show the progress at the working of the program (it background).
	 */
	public abstract void showProgress();
	
}
