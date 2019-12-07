package raulcastilla215alu;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import raulcastilla215alu.mytools.AgentState;
import raulcastilla215alu.mytools.IOModule;
import tools.ElapsedCpuTimer;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA.
 * User: ssamot
 * Date: 14/11/13
 * Time: 21:45
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Agent extends AbstractPlayer {
	
	private int contador = 0;
    /**
     * Random generator for the agent.
     */
    protected Random randomGenerator;
    /**
     * List of available actions for the agent
     */
    protected ArrayList<Types.ACTIONS> actions;

    private AgentState currentState;

    /**
     * Public constructor with state observation and time due.
     * @param so state observation of the current game.
     * @param elapsedTimer Timer for the controller creation.
     */
    public Agent(StateObservation so, ElapsedCpuTimer elapsedTimer)
    {
        randomGenerator = new Random();
        actions = so.getAvailableActions();
        currentState = new AgentState(so);
        
//        for(ArrayList<Observation> v: so.getImmovablePositions())
//        	System.out.println(v);
//        so.getObservationGrid();
//        so.getAvatarOrientation();
        
//       ArrayList<Observation>[] observations = so.getMovablePositions(so.getAvatarPosition());
//       for(ArrayList<Observation> it: observations) {
//    	   System.out.println(it);
//       }
       
//       ArrayList<Observation>[] observations = so.getImmovablePositions(so.getAvatarPosition());
//       for(ArrayList<Observation> it: observations) {
//    	   System.out.println(it);
//       }
       
        
//       ArrayList<Observation>[][] grid = so.getObservationGrid();
//       System.out.println("NumColumns = " + grid.length);
//       System.out.println("NumRows = " + grid[0].length);
//        
//        for(int row = 0; row < grid.length; row++) {
//        	for(int column = 0; column < grid[row].length; column++) {
//        		ArrayList<Observation> obs = grid[row][column];
//        		for(Observation it : obs) {
//        			System.out.println(it);
//        		}
//        		System.out.println("***************");
//        	}
//        }
        
//        System.out.println(so.getAvatarPosition());
//        Vector2d agentPos = AgentState.calculateCell(so.getAvatarPosition(), so.getBlockSize());
//        System.out.println("Mi posición es: " + agentPos);
//       
//		System.out.println("En mi posición hay: " + grid[(int) agentPos.x][(int) agentPos.y]);
    }


    /**
     * Picks an action. This function is called every game step to request an
     * action from the player.
     * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
     * @return An action for the current state
     */
    public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
        
//    	for(int i = 0; i < 1000000; i++) {
//    		System.out.println("");
//    	}
    	
//    	if(contador == 10 || contador == 15) {
//            ArrayList<Observation>[] observations = stateObs.getMovablePositions(stateObs.getAvatarPosition());
//            for(ArrayList<Observation> it: observations) {
//         	   System.out.println(it);
//            }
//            contador++;
//            System.out.println("********************");
//    	} else {
//    		contador++;
//    	}

//        System.out.println(stateObs.getAvatarPosition());
//        Vector2d agentPos = AgentState.calculateCell(stateObs.getAvatarPosition(), stateObs.getBlockSize());
//        System.out.println("Mi posición es: " + agentPos);
    	
    	// 
        
//        stateObs.getPortalsPositions(); // Posición de salida. (inmovil).
//        stateObs.getAvatarPosition();
//        stateObs.getAvatarPosition();
//        
//        for(ArrayList<Observation> v: stateObs.getMovablePositions())
//        	System.out.println(v);
//        
//        stateObs.getBlockSize();
//        stateObs.getWorldDimension();
       
//    	if(contador == 30) {
//    		ArrayList<Observation>[] arrayObs = stateObs.getPortalsPositions();
//    		System.out.println(arrayObs[0].get(0));
//            contador++;
//            System.out.println("********************");
//            System.out.println("Longitud = " + arrayObs[0].size());
//    	} else {
//    		contador++;
//    	}
    	
//    	for(int i = 0; i < 400000; i++) {
//    		System.out.print(" ");
//    	}
    	
    	currentState.perceive(stateObs);;
    	int ticks = stateObs.getGameTick();
    	IOModule.write("./History.txt", ticks + "\n" + currentState.toString(), true);
//    	System.out.println(stateObs.getAvatarOrientation());
    	
//    	System.out.println(currentState);
//    	System.out.println("*********************************");

        // Cuadricular dividiendo tamaño real entre getBlockSize() esto hacerlo en el constructor
        // hacemos nuestro propio mapa de ese juego. 
	    int index = randomGenerator.nextInt(actions.size());
        return actions.get(index);
    }

}
