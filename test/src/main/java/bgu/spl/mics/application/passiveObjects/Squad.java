package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Future;

import java.util.Map;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Squad {

	private Map<String, Agent> agents;

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Inventory getInstance() {
		//TODO: Implement this
		return null;
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param inventory 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public void load (Agent[] inventory) {

	}

	/**
     * Searches an agent
     * <p>
     * @return Future object resolved to 'true' if Agent of serialNumber 'serial' is available, 'false' otherwise.
     */
	public Future<Boolean> search(String serial) {
		// TODO Implement this
		return null;
	}

	/**
     * Retrieves the name of the agent.
     * <p>
     * @return the name of the agent.
     */
	public String getName() {
		// TODO Implement this
		return null;
	}

	/**
     * Retrieves if the agent is available.
     * <p>
     * @return if the agent is available.
     */
	public boolean isAvailable() {
		// TODO Implement this
		return false;
	}

	/**
	 * Acquires an agent.
	 */
	public void acquire(){
		// TODO Implement this
	}

	/**
	 * Releases an agent.
	 */
	public void release(){
		// TODO Implement this
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   milliseconds to sleep
	 */
	public void send(int time){
		// TODO Implement this
	}
}
