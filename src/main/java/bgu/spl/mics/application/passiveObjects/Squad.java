package main.java.bgu.spl.mics.application.passiveObjects;
import main.java.bgu.spl.mics.MessageBrokerImpl;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {
	private Map<String, Agent> agents;

	private static class singleHolder2{
		private static Squad instance = new Squad();
	}
	private Squad(){
		//initialize
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Squad getInstance() {
		return singleHolder2.instance;
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param agents 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public void load (Agent[] agents) {
		this.agents = new ConcurrentHashMap<String, Agent>();
		for (int i=0; i<agents.length; i++){
			this.agents.put(agents[i].getSerialNumber(), agents[i]);
		}
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials){
		for (int i = 0; i < serials.size(); i++) {
			agents.get(serials.get(i)).release();
		}
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   time ticks to sleep
	 */
	public void sendAgents(List<String> serials, int time){
		// TODO: find out how to convert time ticks to milliseconds
	}

	/**
	 * acquires an agent, i.e. holds the agent until the caller is done with it
	 * @param serials   the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
	 */
	public synchronized boolean getAgents(List<String> serials){
		for (int i = 0; i < serials.size(); i++) {
			if (!agents.containsKey(serials.get(i))) {
				return false;
			}
			else if (agents.get(serials.get(i)).isAvailable()) {
				agents.get(serials.get(i)).acquire();
			}
			else {
				while (!agents.get(serials.get(i)).isAvailable()) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				agents.get(serials.get(i)).acquire();
			}
		}
		return true;
	}

    /**
     * gets the agents names
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials){
		ArrayList<String> names = new ArrayList<>();
		for (int i = 0; i < serials.size(); i++) {
			names.add(agents.get(serials.get(i)).getName());
		}
		return names;
    }
}
