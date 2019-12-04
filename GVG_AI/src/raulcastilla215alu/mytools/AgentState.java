package raulcastilla215alu.mytools;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import tools.Vector2d;

public class AgentState extends State {

	private Vector2d agentPos;
	private int blockSize;
	private int dangerDistance;
	
	public static final int IMMOVABLE = 4;
	public static final int MOVABLE = 6;
	public static final int RIGHTCAR1 = 7;
	public static final int RIGHTCAR2 = 8;
	public static final int LEFTCAR1 = 10;
	public static final int LEFTCAR2 = 11;
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int NONORIENTATION = 2;
	
	public AgentState(StateObservation stateObs) {
		blockSize = stateObs.getBlockSize();
		dangerDistance = 2;
		update(stateObs);
	}
	
	public void update(StateObservation stateObs) {
		agentPos = calculateCell(stateObs.getAvatarPosition(), stateObs.getBlockSize());		
		
		ArrayList<Observation>[][] grid = stateObs.getObservationGrid();
		
		int x = (int) agentPos.x;
		int y = (int) agentPos.y;
		
		int[] stateValues = new int[9];
		
		stateValues[POSFRONTBLOCK] = (isThisCategory(grid[x][y-1], IMMOVABLE) ? 1 : 0);
		stateValues[POSBACKBLOCK] = (isThisCategory(grid[x][y+1], IMMOVABLE) ? 1 : 0);
		stateValues[POSLEFTBLOCK] = (isThisCategory(grid[x-1][y], IMMOVABLE) ? 1 : 0);
		stateValues[POSRIGHTBLOCK] = (isThisCategory(grid[x+1][y], IMMOVABLE) ? 1 : 0);

		Vector2d frontAgentPos = new Vector2d();
		frontAgentPos.set(agentPos.x, agentPos.y-1);
		
		Vector2d backAgentPos = new Vector2d();
		backAgentPos.set(agentPos.x, agentPos.y+1);
		
		stateValues[POSFRONTDANGER] = (inDanger(grid, frontAgentPos) ? 1 : 0);
		stateValues[POSBACKDANGER] = (inDanger(grid, backAgentPos) ? 1 : 0);
		stateValues[POSLEFTDANGER] = (inDanger(grid, agentPos, LEFT) ? 1 : 0);
		stateValues[POSRIGHTDANGER] = (inDanger(grid, agentPos, RIGHT) ? 1 : 0);
		
		ArrayList<Observation>[] arrayObs = stateObs.getPortalsPositions();
		if(arrayObs != null) {
			stateValues[POSCOMPASS] = compassDirection(arrayObs[0].get(0).position, agentPos);
		} else {
			stateValues[POSCOMPASS] = NORTH;
		}
		
		ArrayList<Integer> arrayStateValues = new ArrayList<>();
		for(int i = 0; i < stateValues.length; i++) {
			arrayStateValues.add(stateValues[i]);
		}
		
		super.update(arrayStateValues);
	}
	
	
	public static Vector2d calculateCell(Vector2d agentPos, int blockSize) {
		Vector2d cellCoords = new Vector2d();
		
		int x = (int) (agentPos.x/blockSize);
		int y = (int) (agentPos.y/blockSize);
		
		cellCoords.set(x, y);
		
		return cellCoords;
	}
	
	private boolean isThisCategory(ArrayList<Observation> obs, int category) {
		if(obs.isEmpty()) return false;
		return obs.get(0).category == category;
	}
	
	private boolean inDanger(ArrayList<Observation>[][] grid, Vector2d pos) {
		
		int highwayOrientation = getHighwayOrientation(grid, pos);
		
		if(highwayOrientation == LEFT) return inDanger(grid, pos, RIGHT); 
		if(highwayOrientation == RIGHT) return inDanger(grid, pos, LEFT); // Si los coches van hacia la derecha, yo miro a la izquierda.
		
		return false;
	}
	
	private boolean inDanger(ArrayList<Observation>[][] grid, Vector2d agentPos, int orientation) {
		ArrayList<Observation> observationRow = new ArrayList<>();
		int carType1 = -1; 
		int carType2 = -1;
		int initialValue = -1;
		int endValue = -1;
		
		
		if(orientation == LEFT) {
			initialValue = 1;
			endValue = (int)agentPos.x;
			
			carType1 = RIGHTCAR1;
			carType2 = RIGHTCAR2;
			
		} else {
			initialValue = (int)agentPos.x;
			endValue = grid.length-1;
			
			carType1 = LEFTCAR1;
			carType2 = LEFTCAR2;
		}
		

		for(int i = initialValue; i <= endValue; i++) {
			if(!grid[i][(int) agentPos.y].isEmpty()) {
				observationRow.add(grid[i][(int)agentPos.y].get(0));
			}
		}
		
		
		for(int i = 0; i < observationRow.size(); i++) {
			Observation obs = observationRow.get(i);
			if(obs.category == IMMOVABLE) {
				observationRow.remove(i);
				i--;
			} else {
				if(obs.itype != carType1 && obs.itype != carType2) {
					observationRow.remove(i);
					i--;
				} else {
					if(!isNear(obs, agentPos, dangerDistance)) {
						observationRow.remove(i);
						i--;
					}
				}
			}
		}
			
		return observationRow.size() != 0;
	}
	
	private boolean isNear(Observation obs, Vector2d agentPos, int blockDistance) {
		Vector2d obsBlocks = calculateCell(obs.position, blockSize);
		
		int dif = (int) Math.abs(obsBlocks.x-agentPos.x);
		return dif <= blockDistance;
	}
	
	private int compassDirection(Vector2d portalPos, Vector2d agentPos) {
		Vector2d portalBlockPos = calculateCell(portalPos, blockSize);
		
		int modX = (int) (portalBlockPos.x - agentPos.x);
		int modY = (int) (portalBlockPos.y - agentPos.y);
		
		if(Math.abs(modY) > Math.abs(modX)) {
			if(modY < 0) {
				return NORTH;
			} else {
				return SOUTH;
			}
		} else {
			if(modX < 0) {
				return EAST;
			} else {
				return WEST;
			}
		}
	}

	private int getHighwayOrientation(ArrayList<Observation>[][] grid, Vector2d pos) {
		ArrayList<Observation> cars = new ArrayList<>();
		
		for(int i = 1; i < grid.length-1; i++) {
			ArrayList<Observation> obs = grid[i][(int)pos.y];
			if(!obs.isEmpty() && obs.get(0).category == MOVABLE) {
				cars.add(obs.get(0));
			}
		}
		
		if(cars.isEmpty()) return NONORIENTATION;
		
		if(cars.get(0).itype == RIGHTCAR1 || cars.get(0).itype == RIGHTCAR2) return RIGHT;
		
		return LEFT;
	}

}
