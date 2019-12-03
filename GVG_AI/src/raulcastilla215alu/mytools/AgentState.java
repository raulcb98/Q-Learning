package raulcastilla215alu.mytools;

import java.util.ArrayList;
import core.game.StateObservation;
import tools.Vector2d;

public class AgentState extends State {

	private Vector2d agentPos;
	
	public AgentState(StateObservation stateObs) {
		update(stateObs);
	}
	
	public void update(StateObservation stateObs) {
		agentPos = stateObs.getAvatarPosition();		
		
	}
	
}
