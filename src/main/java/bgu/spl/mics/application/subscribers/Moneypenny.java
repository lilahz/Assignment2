package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.AnswerToM;
import main.java.bgu.spl.mics.Subscriber;
import main.java.bgu.spl.mics.application.messages.AgentAvailableEvent;
import main.java.bgu.spl.mics.application.messages.AgentReleaseEvent;
import main.java.bgu.spl.mics.application.messages.AgentSendEvent;
import main.java.bgu.spl.mics.application.messages.TerminateBroadcast;
import main.java.bgu.spl.mics.application.passiveObjects.Agent;
import main.java.bgu.spl.mics.application.passiveObjects.Squad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
    private Squad squad = Squad.getInstance();
    private int id;
    private List<String> agentNames;

    public Moneypenny(int id) {
        super("MoneyPenny" + id);
        this.id = id;
        agentNames = new ArrayList<>();
    }

    @Override
    protected void initialize() {
            subscribeEvent(AgentAvailableEvent.class, (callBack) -> {
                // Checking if the needed agents are available for the mission
                // If the agents are available
                System.out.println("MoneyPenny " + id + " - Searching for " + callBack.getAgentList().toString());
				Object[] answerToM = new Object[2];
				answerToM[0] = id;
				answerToM[1]= (AnswerToM) () -> squad.getAgents(callBack.getAgentList());
				complete(callBack, answerToM);
            });

            subscribeEvent(AgentSendEvent.class, (callBack) -> {
                System.out.println("MoneyPenny " + id + " - Sending agents " + callBack.getAgentList().toString());
                squad.sendAgents(callBack.getAgentList(), callBack.getDuration());
                agentNames = squad.getAgentsNames(callBack.getAgentList());
                complete(callBack, agentNames);
            });

            subscribeEvent(AgentReleaseEvent.class, (callBack) -> {
                System.out.println("MoneyPenny - Releasing agents");
                squad.releaseAgents(callBack.getAgentList());
                complete(callBack, true);
            });

        subscribeBroadcast(TerminateBroadcast.class, (callBack) -> {
            System.out.println(this.getName() + " Have been terminated ");
            terminate();
        });
    }

}
