package raulcastilla215alu.mytools;

import java.util.ArrayList;

import core.game.StateObservation;
import ontology.Types.ACTIONS;
import raulcastilla215alu.matrix.QTable;

public class Brain {
	
	private QLearning qLearning;
	private AgentState currentState;
	private AgentState previousState;
	private ACTIONS lastAction;
	private String savePath;
	
	public Brain(StateObservation stateObs) {
		savePath = "./QTable/Qtable.txt";
        currentState = new AgentState(stateObs);
        previousState = new AgentState(stateObs);
        lastAction = stateObs.getAvatarLastAction();
        
        ArrayList<State> states = StateGenerator.generate();
        ArrayList<ACTIONS> actions = stateObs.getAvailableActions(true);
		QTable qTable = new QTable(states , actions, savePath);
		qLearning = new QLearning(qTable);
	}
	
	public ACTIONS think(StateObservation stateObs) {
		previousState = new AgentState(currentState);
		currentState.perceive(stateObs);
		lastAction = stateObs.getAvatarLastAction();
		return qLearning.learn(previousState, lastAction, currentState);
	}
	
	public void saveQTable() {
		qLearning.saveQTable(savePath);
	}

}
