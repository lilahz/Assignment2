package test.java.bgu.spl.mics;

import main.java.bgu.spl.mics.application.passiveObjects.Agent;
import main.java.bgu.spl.mics.application.passiveObjects.Squad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a Unit Test for the {@link main.java.bgu.spl.mics.application.passiveObjects.Squad} class.
 */
public class SquadTest {
    private Squad squad;
    Agent[] agents;

    @BeforeEach
    public void setUp(){
        squad = Squad.getInstance();
        // TODO: check if this is repeating code, if some test needs a different setup copy to each function and delete
        Agent a = new Agent();
        a.setName("James Bond");
        a.setSerialNumber("007");
        Agent b = new Agent();
        b.setName("Bill Fairbanks");
        b.setSerialNumber("002");
        agents = new Agent[] {a, b};
        squad.load(agents);
    }

    //=============================================getInstance Tests====================================================
    /**
     * Test method for {@link Squad#getInstance()}
     * Make sure that if we call getInstance(), we get the same Squad.
     */
    @Test public void testSameSquad() {
        Squad squad2 = Squad.getInstance();
        assertEquals(squad, squad2);
    }

    //================================================load Tests========================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#load(Agent[])}
     * Check if after load, all the agents exist in the squad.
     */
    @Test public void testLoadInventory() {
//        Agent a = new Agent();
//        Agent b = new Agent();
//        agents = new Agent[] {a, b};
        List<String> serials = new ArrayList<>();
        serials.add("007");
        serials.add("002");
        assertTrue(squad.getAgents(serials));
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#load(Agent[])}
     * Check if after load, non-existing object does not exist in the inventory.
     * Negative test.
     */
    @Test public void testNegativeLoad() {
//        Agent a = new Agent();
//        Agent b = new Agent();
//        agents = new Agent[] {a, b};
        List<String> serials = new ArrayList<>();
        serials.add("0012");
        assertFalse(squad.getAgents(serials));
    }

    //=============================================releaseAgents Tests==================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#releaseAgents(List)}
     * Release Agents and make sure getAgents return true after release.
     */
    @Test public void testRealease() {
        List<String> serials = new ArrayList<>();
        serials.add("007");
        serials.add("002");
        squad.releaseAgents(serials);
        for (int i = 0; i < agents.length; i++) {
            assertTrue(agents[i].isAvailable());
        }
    }

    //===============================================sendAgents Tests===================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#releaseAgents(List)}
     * Send Agents to a mission, make sure they are all available when function is finished.
     */
    @Test public void testSendAgents() {
        List<String> serials = new ArrayList<>();
        serials.add("007");
        serials.add("002");
        squad.sendAgents(serials, 100);
        for (int i = 0; i < agents.length; i++) {
            assertTrue(agents[i].isAvailable());
        }
    }

    //================================================getAgents Tests===================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#getAgents(List)}
     * Make sure the function returns true for a list of available agents.
     */
    @Test public void testGetAgents() {
        List<String> serials = new ArrayList<>();
        serials.add("007");
        serials.add("002");
        assertTrue(squad.getAgents(serials));
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#getAgents(List)}
     * Make sure the function returns true for a list of agents, even if one agent is acquired.
     */
    @Test public void testGetAcquired() {
        List<String> serials = new ArrayList<>();
        serials.add("007");
        serials.add("002");
        agents[0].acquire();
        assertTrue(squad.getAgents(serials));
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#getAgents(List)}
     * Make sure the function returns false for a list of agents, with one agent missing.
     * Negative test.
     */
    @Test public void testGetNegative() {
        List<String> serials = new ArrayList<>();
        serials.add("007");
        serials.add("002");
        serials.add("0012");
        assertFalse(squad.getAgents(serials));
    }

    //============================================getAgentsNames Tests==================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#getAgentsNames(List)}
     * Compare returned agents names.
     * Negative test.
     */
    @Test public void testGetNames() {
        List<String> serials = new ArrayList<>();
        serials.add("007");
        serials.add("002");
        List<String> names = new ArrayList<>();
        names.add("James Bond");
        names.add("Bill Fairbanks");
        List<String> returnedNames = squad.getAgentsNames(serials);
        for (int i = 0; i < names.size(); i++) {
            assertEquals(names.get(i), returnedNames.get(i));
        }
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#getAgentsNames(List)}
     * Compare returned agents names.
     */
    @Test public void testGetNamesNegative() {
        List<String> serials = new ArrayList<>();
        serials.add("007");
        serials.add("002");
        List<String> names = new ArrayList<>();
        names.add("Bond");
        names.add("Johnston");
        List<String> returnedNames = squad.getAgentsNames(serials);
        for (int i = 0; i < names.size(); i++) {
            assertNotEquals(names.get(i), returnedNames.get(i));
        }
    }

}
