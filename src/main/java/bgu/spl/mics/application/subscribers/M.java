package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.*;
import main.java.bgu.spl.mics.application.messages.MissionReceivedEvent;
import main.java.bgu.spl.mics.application.messages.AgentAvailableEvent;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	public M(int id) {
		super("M" + id);
		// TODO Implement this
	}

	@Override
	/**
	 * Subscribes to A
	 */
	protected void initialize() {
		Callback<MissionReceivedEvent> callback = c -> {
			/**
			 * Send message to find out if there are any available agents
			 */
			Event<AgentAvailableEvent> agentAvailableEventEvent =
					new AgentAvailableEvent(c.getSerialAgentsNumbers(), c.getDuration());
			Future<AgentAvailableEvent> future = getSimplePublisher().sendEvent(agentAvailableEventEvent);
		};
		// TODO do we need to add antoher callback for gadget avilable event
		this.subscribeEvent(MissionReceivedEvent.class, callback);
	}


}
