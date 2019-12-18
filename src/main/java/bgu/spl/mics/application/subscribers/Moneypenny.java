package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.Subscriber;
import main.java.bgu.spl.mics.application.messages.AgentAvailableEvent;
import main.java.bgu.spl.mics.application.messages.AgentReleaseEvent;
import main.java.bgu.spl.mics.application.messages.AgentSendEvent;
import main.java.bgu.spl.mics.application.passiveObjects.Agent;
import main.java.bgu.spl.mics.application.passiveObjects.Squad;

import java.util.ArrayList;
import java.util.List;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
	private Squad squad = Squad.getInstance();
	private int id;
	private List<String> agentNames;

	public Moneypenny(int id) {
		super("M" + id);
		this.id = id;
		agentNames = new ArrayList<>();
	}

	@Override
	protected void initialize() {
		subscribeEvent(AgentAvailableEvent.class, (callBack) ->{
			synchronized (squad) {
				// Checking if the needed agents are available for the mission
				// If the agents are available
				if (squad.getAgents(callBack.getAgentList())) {
					complete(callBack, id);
				}
			}
		});

		subscribeEvent(AgentSendEvent.class, (callBack) ->{
			synchronized (squad) {
				squad.sendAgents(callBack.getAgentList(), callBack.getDuration());
				agentNames = squad.getAgentsNames(callBack.getAgentList());
				complete(callBack, agentNames);
			}
		});

		subscribeEvent(AgentReleaseEvent.class, (callBack) ->{
			synchronized (squad) {
				squad.releaseAgents(callBack.getAgentList());
				complete(callBack, null); // TODO: whats the result to send
			}
		});
		
	}

}
