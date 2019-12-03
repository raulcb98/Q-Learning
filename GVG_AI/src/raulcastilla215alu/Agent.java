package raulcastilla215alu;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import tools.ElapsedCpuTimer;

/**
 * Created with IntelliJ IDEA.
 * User: ssamot
 * Date: 14/11/13
 * Time: 21:45
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Agent extends AbstractPlayer {
    /**
     * Random generator for the agent.
     */
    protected Random randomGenerator;
    /**
     * List of available actions for the agent
     */
    protected ArrayList<Types.ACTIONS> actions;


    /**
     * Public constructor with state observation and time due.
     * @param so state observation of the current game.
     * @param elapsedTimer Timer for the controller creation.
     */
    public Agent(StateObservation so, ElapsedCpuTimer elapsedTimer)
    {
        randomGenerator = new Random();
        actions = so.getAvailableActions();
        
//        for(ArrayList<Observation> v: so.getImmovablePositions())
//        	System.out.println(v);
        so.getObservationGrid();
        so.getAvatarOrientation();
        
        ArrayList<Observation>[] observations = so.getMovablePositions();
        System.out.println(observations[0].get(0));
    }


    /**
     * Picks an action. This function is called every game step to request an
     * action from the player.
     * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
     * @return An action for the current state
     */
    public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
        
    	for(int i = 0; i < 100000; i++) {
    		System.out.println("");
    	}
    	
    	int index = randomGenerator.nextInt(actions.size());
        
//        stateObs.getPortalsPositions(); // Posici�n de salida. (inmovil).
//        stateObs.getAvatarPosition();
//        stateObs.getAvatarPosition();
//        
//        for(ArrayList<Observation> v: stateObs.getMovablePositions())
//        	System.out.println(v);
//        
//        stateObs.getBlockSize();
//        stateObs.getWorldDimension();
        
        // Cuadricular dividiendo tama�o real entre getBlockSize() esto hacerlo en el constructor
        // hacemos nuestro propio mapa de ese juego. 

        return actions.get(index);
    }

}
