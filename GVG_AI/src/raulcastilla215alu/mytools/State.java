package raulcastilla215alu.mytools;

import java.util.ArrayList;

public class State {
	
	/**
	 * Private attributes
	 */
	private boolean frontBlock;
	private boolean backBlock;
	private boolean leftBlock;
	private boolean rightBlock;
	private boolean leftDanger;
	private boolean rightDanger;
	
	public static final int POSFRONTBLOCK = 0;
	public static final int POSBACKBLOCK = 1;
	public static final int POSLEFTBLOCK = 2;
	public static final int POSRIGHTBLOCK = 3;
	public static final int POSLEFTDANGER = 4;
	public static final int POSRIGHTDANGER = 5;
	
	public State() {
		
	}
	
	public State(boolean frontBlock, boolean backBlock, boolean leftBlock, boolean rightBlock, boolean leftDanger, boolean rightDanger){
		this.frontBlock = frontBlock;
		this.backBlock = backBlock;
		this.leftBlock = leftBlock;
		this.rightBlock = rightBlock;
		this.leftDanger = leftDanger;
		this.rightDanger = rightDanger;
	}
		
	public State(ArrayList<Integer> array) {
		update(array);		
	}
	
	public void update(ArrayList<Integer> array) {
		frontBlock = (array.get(POSFRONTBLOCK) == 0 ? false : true);
		backBlock = (array.get(POSBACKBLOCK) == 0 ? false : true);
		leftBlock = (array.get(POSLEFTBLOCK) == 0 ? false : true);
		rightBlock = (array.get(POSRIGHTBLOCK) == 0 ? false : true);
		leftDanger = (array.get(POSLEFTDANGER) == 0 ? false : true);
		rightDanger = (array.get(POSRIGHTDANGER) == 0 ? false : true);		
	}
	
	@Override
	public boolean equals(Object obj) {
		State aux = (State) obj;
		return ( this.frontBlock == aux.frontBlock && 
				 this.backBlock == aux.backBlock && 
				 this.leftBlock == aux.leftBlock &&
				 this.rightBlock == aux.rightBlock &&
				 this.leftDanger == aux.leftDanger &&
				 this.rightDanger == aux.rightDanger);
	}

}
