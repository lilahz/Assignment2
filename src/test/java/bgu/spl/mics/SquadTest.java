package test.java.bgu.spl.mics;

import main.java.bgu.spl.mics.application.passiveObjects.Agent;
import main.java.bgu.spl.mics.application.passiveObjects.Inventory;
import main.java.bgu.spl.mics.application.passiveObjects.Squad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This is a Unit Test for the {@link main.java.bgu.spl.mics.application.passiveObjects.Squad} class.
 */
public class SquadTest {
    private Squad squad;
    private Map<String, Agent> agents;
    private Agent[] listOfAgents;
    @BeforeEach
    public void setUp() {
        squad = Squad.getInstance();
        agents = Map.of("007", new Agent(), "002", new Agent());
        listOfAgents = new Agent[]{new Agent(), new Agent()};
        squad.load(listOfAgents);
    }

    //=============================================getAgent Tests=======================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Squad#getAgents(List)}
     *
     * @return false if one of the agents is missing
     */
    @Test public void testExistAgents(){
        List<String> serials = new ArrayList<>();
        serials.add("007"); serials.add("001");
        assertFalse(squad.getAgents(serials));
    }

    @Test
    public void test(){
        //TODO: change this test and add more tests :)
        fail("Not a good test");
    }
}
