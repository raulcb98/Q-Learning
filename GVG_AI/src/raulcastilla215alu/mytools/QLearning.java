package raulcastilla215alu.mytools;

import java.util.Random;

import ontology.Types.ACTIONS;
import raulcastilla215alu.matrix.QTable;

public class QLearning {

	private QTable qTable;
	private float gamma;
	private float alpha;
	private static double time = 0;
	private float epsilon;
	
	private final float CONSTANT = 40000;
	private final float WINREWARD = 10000f;
	private final float DEADREWARD = -100f;
	private final float STOPREWARD = -10f;
	private final float DISTANCEFACTOR = 200f;
	
	public QLearning(QTable qTable) {
		this.qTable = qTable;
		gamma = 0.5f;
		alpha = 0.8f;
		epsilon = 0.8f;
	}
	
	public void saveQTable(String path) {
		qTable.toCSV(path);
	}
	
	public ACTIONS learn(AgentState previousState, ACTIONS lastAction, AgentState currentState) {
		
		float sample = reward(previousState, lastAction, currentState) + gamma * qTable.getMaxQValue(currentState);
		float newQValue = (1-alpha)*qTable.get(previousState, lastAction) + alpha*sample;
		qTable.set(previousState, lastAction, newQValue);
		
		updateConstants();
		
		return nextAction(currentState);
	}
	
	private float reward(AgentState previousState, ACTIONS lastAction, AgentState currentState) {
		
		float finalReward = 0;
		
		// Distance reward
		float currentDistance = currentState.getDistanceToPortal();
		float previousDistance = previousState.getDistanceToPortal();
		float distanceReward = 0;
		
		//System.out.println("Current distance = " + currentDistance + " Previous Distance = " + previousDistance);
		
		if(currentDistance < previousDistance) {
			distanceReward += previousDistance/currentDistance * DISTANCEFACTOR;
		} else {
			distanceReward += -currentDistance/previousDistance;
		}
		 
		
		finalReward += distanceReward;
		
		// Dead reward
		if(currentState.isAgentDead()) {
			finalReward += DEADREWARD;
		}
		
		// Win reward
		if(currentState.isAgentWin()) {
			finalReward += WINREWARD;
		}
		
		// Stop reward
		if(previousDistance == currentDistance) {
			finalReward += STOPREWARD;
		}
		
		return finalReward;
	}
	
	private ACTIONS nextAction(AgentState currentState) {
		Random rd = new Random();
		float randomNumber = Math.abs(rd.nextFloat());
		
		return qTable.getBestAction(currentState);
//		if(randomNumber < epsilon) {
//			return qTable.getRandomAction();
//		} else {
//			return qTable.getBestAction(currentState);
//		}
	}
	
	private void updateConstants() {
		alpha = (float) (CONSTANT/(CONSTANT + time));
		epsilon = (float) (CONSTANT/(CONSTANT + time));
		
		time++;
		System.out.println("Time = " + time + " Alpha = " + alpha);
	}
}
