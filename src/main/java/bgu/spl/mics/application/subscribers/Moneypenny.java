package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.Subscriber;
import main.java.bgu.spl.mics.application.messages.AgentAvailableEvent;
import main.java.bgu.spl.mics.application.passiveObjects.Agent;
import main.java.bgu.spl.mics.application.passiveObjects.Squad;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
	Squad squad = Squad.getInstance();

	public Moneypenny(int id) {
		super("M" + id);
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		subscribeEvent(AgentAvailableEvent.class, (callBack) ->{
			synchronized (squad) {
				if (squad.getAgents(callBack.getAgentList())) {
					// TODO: add more stuff: gadget available
					// to get from M if to release or to send Agents.
					//squad.sendAgents(callBack.getAgentList(), callBack.getDuration());
				}
			}
		});
		
	}

}
