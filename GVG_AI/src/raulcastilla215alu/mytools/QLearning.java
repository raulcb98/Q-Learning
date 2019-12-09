package raulcastilla215alu.mytools;

import ontology.Types.ACTIONS;
import raulcastilla215alu.matrix.QTable;

public class QLearning {

	private QTable table;
	
	public QLearning(QTable table) {
		this.table = table;
	}
	
	public void saveQTable(String path) {
		table.toCSV(path);
	}
	
	public ACTIONS learn(AgentState previousState, ACTIONS lastAction, AgentState currentState) {
		
		return ACTIONS.ACTION_NIL;
	}
}
