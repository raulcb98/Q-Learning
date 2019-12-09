package raulcastilla215alu.matrix;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import ontology.Types.ACTIONS;
import raulcastilla215alu.mytools.*;

public class QTable extends Matrix {

	private ArrayList<State> states;
	private ArrayList<ACTIONS> actions;
	
	public QTable(ArrayList<State> states, ArrayList<ACTIONS> actions, String path) {
		this.states = states;
		this.actions = actions;
		
		File qTableFile = new File(path);
		if (!qTableFile.exists()) {
			initializesWithRandoms();
			System.out.println("Creo con randoms");
		} else {
			readCSV(path, ',');
		}
	}
	
	public float get(State s, ACTIONS a) {
		int row = states.indexOf(s);
		int column = actions.indexOf(a);		
		return Float.parseFloat(get(row, column));
	}
	
	public void set(State s, ACTIONS a, float qValue) {
		int row = states.indexOf(s);
		int column = actions.indexOf(a);		
		set(row, column, Float.toString(qValue));
	}
	
	private ArrayList<String> createRowWithZeros(int length) {
		ArrayList<String> array = new ArrayList<>();
		
		for(int i = 0; i < length; i++) {
			array.add("0");
		}
		
		return array;
	}
	
	private void initializesWithZeros() {
		for(int i = 0; i < states.size(); i++) {
			addRow(createRowWithZeros(actions.size())); 		
		}
	}
	
	private ArrayList<String> createRowWithRandoms(int length) {
		ArrayList<String> array = new ArrayList<>();
		Random rd = new Random();
		
		for(int i = 0; i < length; i++) {
			array.add(Float.toString(rd.nextFloat()));
		}
		
		return array;
	}
	
	private void initializesWithRandoms() {
		for(int i = 0; i < states.size(); i++) {
			addRow(createRowWithRandoms(actions.size())); 		
		}
	}
	
	public float getMaxQValue(State s) {
		int indexRow = states.indexOf(s);
		if(indexRow == -1) {
			System.out.println("longitud= " + states.size());
			System.out.println("indexRow = " + indexRow);
			System.out.println("States = " + (State)s);
		}
		
		ArrayList<String> array = getRow(indexRow);
		float max = Float.parseFloat(array.get(0));
		
		for(int i = 1; i < array.size(); i++) {
			float value = Float.parseFloat(array.get(i));
			if (max < value) {
				max = value;
			}
		}
		return max;
	}
	
	public ACTIONS getBestAction(State s) {
		int indexRow = states.indexOf(s);
		ArrayList<String> array = getRow(indexRow);
		float max = Float.parseFloat(array.get(0));
		int indexMax = 0;
		
		for(int i = 1; i < array.size(); i++) {
			float value = Float.parseFloat(array.get(i));
			if (max < value) {
				max = value;
				indexMax = i;
			}
		}

		return actions.get(indexMax);
	}
	
	public ACTIONS getRandomAction() {
    	Random rd = new Random();
    	int action = rd.nextInt(actions.size());
    	
        return actions.get(action);
	}
}
