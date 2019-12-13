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
	private int deadCounter;
	private QTable qTable;
	
	public Brain(StateObservation stateObs, String savePath) {
		this.savePath = savePath;
        currentState = new AgentState(stateObs);
        previousState = new AgentState(stateObs);
        lastAction = stateObs.getAvatarLastAction();
        
        ArrayList<State> states = StateGenerator.generate();
        ArrayList<ACTIONS> actions = stateObs.getAvailableActions(true);
		qTable = new QTable(states , actions, savePath);
		qLearning = new QLearning(qTable);
		
		deadCounter = 0;
	}
	
	public ACTIONS learn(StateObservation stateObs) {
		previousState = new AgentState(currentState);
		currentState.perceive(stateObs);
		lastAction = stateObs.getAvatarLastAction();
		
		if(currentState.isAgentDead()) {
			deadCounter++;
		} else {
			deadCounter = 0;
		}
		
		if(deadCounter > 1 || !previousState.portalExist()) {
			return ACTIONS.ACTION_NIL;
		} else {
			return qLearning.learn(previousState, lastAction, currentState);
		}
	}
	
	public ACTIONS act(StateObservation stateObs) {
		
		currentState.perceive(stateObs);;
        int ticks = stateObs.getGameTick();
        IOModule.write("./History.txt", ticks + "\n" + currentState.toString(), true);
		
		currentState.perceive(stateObs);
		if(currentState.portalExist())
			return qTable.getBestAction(currentState);
		else
			return ACTIONS.ACTION_NIL; 
	}
	
	public void saveQTable() {
		qLearning.saveQTable(savePath);
	}

}
