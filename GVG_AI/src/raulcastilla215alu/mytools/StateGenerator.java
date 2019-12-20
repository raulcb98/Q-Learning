package raulcastilla215alu.mytools;

import java.util.ArrayList;

/**
 * Generates all possible and logical agent state.
 * 
 * @author Ricardo Manuel Ruiz D�az.
 * @author Ra�l Castilla Bravo.
 */
public class StateGenerator {
	
	/**
	 * Generates all possible and logical agent state.
	 * @return An array of states.
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<State> generate() {
		int[] values = new int[2];
		values[0] = 0;
		values[1] = 1;
		
		// Generation and filtering states without compass
		ArrayList<ArrayList<Integer>> combStates = combnk(8, values);
		filterStates(combStates);
		
		int[] compassValues = {State.NORTH, State.SOUTH};
		
		ArrayList<State> output = new ArrayList<State>();
		ArrayList<Integer> aux;
		
		for(int indexCompass = 0; indexCompass < compassValues.length; indexCompass++) {
			for(int indexArray = 0; indexArray < combStates.size(); indexArray++) {
				aux = (ArrayList<Integer>) combStates.get(indexArray).clone();
				aux.add(compassValues[indexCompass]);
				output.add(new State(aux));
			}
		}
		
		aux = zeros(8);
		aux.add(State.EAST);
		output.add(new State(aux));
		
		aux = zeros(8);
		aux.add(State.WEST);
		output.add(new State(aux));
		
		return output;			
	}
	
	private static ArrayList<Integer> zeros(int length){
		ArrayList<Integer> aux = new ArrayList<>();
		
		for(int i =0;i < length;i++) {
			aux.add(0);
		}
		
		return aux;
	}
	
	/**
	 * Generates all possible combinations using the values and length specified.
	 * @param length size of combination.
	 * @param values possible values for each positions.
	 * @return an array of arrays of integers.
	 */
	private static ArrayList<ArrayList<Integer>> combnk(int length, int[] values) {
		return combnkRec(length-1, values, new ArrayList<ArrayList<Integer>>());
	}
	
	/**
	 * Recursive call for combnk
	 * @param length size of combination.
	 * @param values possible values for each positions.
	 * @param array memory space to save middle values.
	 * @return an array of arrays of integers.
	 */
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
	
	/**
	 * Removes illogical states.
	 * @param combStates an array of arrays of integers with all possible combinations.
	 */
	private static void filterStates(ArrayList<ArrayList<Integer>> combStates){
		for(int i = 0; i < combStates.size(); i++ ) {
			if(!isValid(combStates.get(i))) {
				combStates.remove(i);
				i--;
			}
		}
	}

	/**
	 * Applies restrictions to filter a state.
	 * @param comb array of integers with one combination of values.
	 * @return true if overcomes all restrictions.
	 */
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
		//if(leftDanger && rightDanger) return false;
		//if((leftDanger || rightDanger) && (leftBlock || rightBlock)) return false;
		if(frontDanger && frontBlock) return false;
		if(backDanger && backBlock) return false;
		
		return true;	
	}
	
	public static void main(String[] args) {
		System.out.println("Longitud = " + StateGenerator.generate().size());
	}
	
}
