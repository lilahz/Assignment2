package main.java.bgu.spl.mics.application.passiveObjects;

import main.java.bgu.spl.mics.MessageBrokerImpl;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {
    private Map<String, Agent> agents;

    private static class singleHolder2 {
        private static Squad instance = new Squad();
    }

    private Squad() {
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
     *
     * @param agents Data structure containing all data necessary for initialization
     *               of the squad.
     */
    public void load(Agent[] agents) {
        this.agents = new ConcurrentHashMap<String, Agent>();
        for (int i = 0; i < agents.length; i++) {
            this.agents.put(agents[i].getSerialNumber(), agents[i]);
        }
    }

    /**
     * Releases agents.
     */
    public void releaseAgents(List<String> serials) {
        synchronized (agents) {
            for (int i = 0; i < serials.size(); i++) {
                Agent agent = agents.get(serials.get(i));
                agents.get(serials.get(i)).release();
//                System.out.println("MoneyPenny - releasing " + agent.getSerialNumber());
                agents.notifyAll();
            }

        }
        System.out.println("Released all agents");
    }

    /**
     * simulates executing a mission by calling sleep.
     *
     * @param time time ticks to sleep
     */
    // TODO : move the synch from the title to sycnh(agents) . employee: ofeer.
    public void sendAgents(List<String> serials, int time) {
        synchronized(agents) {
            try {
                System.out.println("Sending agents to mission");
                Thread.currentThread().sleep(time * 100);
                releaseAgents(serials);
                System.out.println("released agents");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * acquires an agent, i.e. holds the agent until the caller is done with it
     *
     * @param serials the serial numbers of the agents
     * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
     */
    public boolean getAgents(List<String> serials) {
        serials.sort((a, b) -> Integer.parseInt(a) - Integer.parseInt(b));
        System.out.println("Getting agents " + serials.toString());
        for (int i = 0; i < serials.size(); i++) {
            // If one of the given serials doesn't exist in the sqaud, return false
            if (!agents.containsKey(serials.get(i))) {
                return false;
            }
        }

        synchronized (agents) {
            try {
                for (int j = 0; j < serials.size(); j++) {
                    if (!agents.get(serials.get(j)).isAvailable()) {
//                        System.out.println("MoneyPenny - waiting for agent " + agents.get(serials.get(j)).getSerialNumber());
                        agents.wait();
                    }
                }
                for (int k = 0; k < serials.size(); k++) {
                    agents.get(serials.get(k)).acquire();
//                    System.out.println("Acquired" + serials.get(k));

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    /**
     * gets the agents names
     *
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials) {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < serials.size(); i++) {
            names.add(agents.get(serials.get(i)).getName());
        }
        return names;
    }
}
