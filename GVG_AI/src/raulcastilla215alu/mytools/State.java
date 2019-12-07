package raulcastilla215alu.mytools;

import java.util.ArrayList;

/**
 * Defines a Q-Table state.
 * 
 * @author Ricardo Manuel Ruiz D�az
 * @author Ra�l Castilla Bravo
 */
public class State {
	
	/**
	 * Private attributes
	 */
	private boolean frontBlock;
	private boolean backBlock;
	private boolean leftBlock;
	private boolean rightBlock;
	
	private boolean frontDanger;
	private boolean backDanger;
	private boolean leftDanger;
	private boolean rightDanger;
	
	private int compass;
	
	public static final int POSFRONTBLOCK = 0;
	public static final int POSBACKBLOCK = 1;
	public static final int POSLEFTBLOCK = 2;
	public static final int POSRIGHTBLOCK = 3;
	public static final int POSFRONTDANGER = 4;
	public static final int POSBACKDANGER = 5;
	public static final int POSLEFTDANGER = 6;
	public static final int POSRIGHTDANGER = 7;
	public static final int POSCOMPASS = 8;
	
	public static final int NORTH = 0;
	public static final int SOUTH = 1; 
	public static final int EAST = 2;
	public static final int WEST = 3;
	
	/**
	 * Default constructor.
	 */
	public State() {
		
	}
	
	/**
	 * Constructor. 
	 * @param array Private attributes values expressed with integers.
	 */
	public State(ArrayList<Integer> array) {
		update(array);		
	}
	
	/**
	 * Updates private attributes values.
	 * @param array Updates private attributes values.
	 */
	protected void update(ArrayList<Integer> array) {
		frontBlock = (array.get(POSFRONTBLOCK) == 0 ? false : true);
		backBlock = (array.get(POSBACKBLOCK) == 0 ? false : true);
		leftBlock = (array.get(POSLEFTBLOCK) == 0 ? false : true);
		rightBlock = (array.get(POSRIGHTBLOCK) == 0 ? false : true);
		
		frontDanger = (array.get(POSFRONTDANGER) == 0 ? false : true);
		backDanger = (array.get(POSBACKDANGER) == 0 ? false : true);	
		leftDanger = (array.get(POSLEFTDANGER) == 0 ? false : true);
		rightDanger = (array.get(POSRIGHTDANGER) == 0 ? false : true);	
		
		compass = array.get(POSCOMPASS);
	}
	
	/**
	 * Returns true if the attributes are exactly the same.
	 */
	@Override
	public boolean equals(Object obj) {
		State aux = (State) obj;
		return ( this.frontBlock == aux.frontBlock && 
				 this.backBlock == aux.backBlock && 
				 this.leftBlock == aux.leftBlock &&
				 this.rightBlock == aux.rightBlock &&
				 this.frontDanger == aux.frontDanger &&
				 this.backDanger == aux.backDanger &&
				 this.leftDanger == aux.leftDanger &&
				 this.rightDanger == aux.rightDanger &&
				 this.compass == aux.compass);
	}

	/**
	 * Returns a String with the information of the object.
	 */
	@Override
	public String toString() {
		String str = "";
		
		str = "frontBlock = " + Boolean.toString(frontBlock) + "\n" + 
				"backBlock = " + Boolean.toString(backBlock) + "\n" + 
				"leftBlock = " + Boolean.toString(leftBlock) + "\n" + 
				"rightBlock = " + Boolean.toString(rightBlock) + "\n" + 
				"frontDanger = " + Boolean.toString(frontDanger) + "\n" + 
				"backDanger = " + Boolean.toString(backDanger) + "\n" + 
				"leftDanger = " + Boolean.toString(leftDanger) + "\n" + 
				"rigthDanger = " + Boolean.toString(rightDanger) + "\n" + 
				"compass = ";
		
		switch (compass) {
			case NORTH: str += "North"; break;
			case SOUTH: str += "South"; break;
			case EAST: str += "East"; break;
			case WEST: str += "West"; break;
		}

		return str;
	}
}
