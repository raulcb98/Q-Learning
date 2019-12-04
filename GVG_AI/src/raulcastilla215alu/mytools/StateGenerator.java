package raulcastilla215alu.mytools;

import java.util.ArrayList;

public class StateGenerator {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<State> generate() {
		int[] values = new int[2];
		values[0] = 0;
		values[1] = 1;
		
		// Generation and filtering states without compass
		ArrayList<ArrayList<Integer>> combStates = combnk(8, values);
		filterStates(combStates);
		
		int[] compassValues = {State.NORTH, State.SOUTH, State.EAST, State.WEST};
		
		ArrayList<State> output = new ArrayList<State>();
		ArrayList<Integer> aux;
		
		for(int indexCompass = 0; indexCompass < compassValues.length; indexCompass++) {
			for(int indexArray = 0; indexArray < combStates.size(); indexArray++) {
				aux = (ArrayList<Integer>) combStates.get(indexArray).clone();
				aux.add(compassValues[indexCompass]);
				output.add(new State(aux));
			}
		}
		
//		for(int indexArray = 0; indexArray < combStates.size(); indexArray++) {
//			output.add(new State(combStates.get(indexArray)));
//		}
		
		return output;			
	}
	
	private static ArrayList<ArrayList<Integer>> combnk(int length, int[] values) {
		return combnkRec(length-1, values, new ArrayList<ArrayList<Integer>>());
	}
	
	@SuppressWarnings("unchecked")
	private static ArrayList<ArrayList<Integer>> combnkRec(int length, int[] values, ArrayList<ArrayList<Integer>> array){
		if(length == 0) {
			for(int indexValues = 0; indexValues < values.length; indexValues++) {
				array.add(new ArrayList<Integer>());
				array.get(indexValues).add(values[indexValues]);
			}
			return array;
		}
		ArrayList<ArrayList<Integer>> aux = new ArrayList<ArrayList<Integer>>();
		aux = combnkRec(length - 1, values, array);
				
		ArrayList<ArrayList<Integer>> output = new ArrayList<ArrayList<Integer>>();
		for(int indexValue = 0; indexValue < values.length; indexValue++) {
			for(int indexArray = 0; indexArray < aux.size(); indexArray++) {
				ArrayList<Integer> subArray = (ArrayList<Integer>) aux.get(indexArray).clone();	
				subArray.add(values[indexValue]);
				output.add(subArray);
			}
		}
		return output;				
	}
	
	private static void filterStates(ArrayList<ArrayList<Integer>> combStates){
		for(int i = 0; i < combStates.size(); i++ ) {
			if(!isValid(combStates.get(i))) {
				combStates.remove(i);
				i--;
			}
		}
	}

	private static boolean isValid(ArrayList<Integer> comb) {
		boolean frontBlock = (comb.get(State.POSFRONTBLOCK) == 0 ? false : true);
		boolean backBlock = (comb.get(State.POSBACKBLOCK) == 0 ? false : true);
		boolean leftBlock = (comb.get(State.POSLEFTBLOCK) == 0 ? false : true);
		boolean rightBlock = (comb.get(State.POSRIGHTBLOCK) == 0 ? false : true);
		
		boolean frontDanger = (comb.get(State.POSFRONTDANGER) == 0 ? false : true);
		boolean backDanger = (comb.get(State.POSBACKDANGER) == 0 ? false : true);
		boolean leftDanger = (comb.get(State.POSLEFTDANGER) == 0 ? false : true);
		boolean rightDanger = (comb.get(State.POSRIGHTDANGER) == 0 ? false : true);
		
		if(frontBlock && backBlock && leftBlock && rightBlock) return false;
		if(leftDanger && rightDanger) return false;
		if((leftDanger || rightDanger) && (leftBlock || rightBlock)) return false;
		if(frontDanger && frontBlock) return false;
		if(backDanger && backBlock) return false;
		
		return true;	
	}
	
	public static void main(String[] args) {
		
		ArrayList<State> test = generate();
		System.out.println("Tama�o del vector: " + test.size());
		for(int i = 0; i < test.size(); i++) {
			System.out.println(test.get(i) + "*");
			
		}
		
	}
	
}