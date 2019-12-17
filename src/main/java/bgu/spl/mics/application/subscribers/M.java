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
	MessageBrokerImpl messageBroker = (MessageBrokerImpl) MessageBrokerImpl.getInstance();
	private SimplePublisher simplePublisher = getSimplePublisher();

	public M(int id) {
		super("M" + id);
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		Callback<MissionReceivedEvent> callback = new Callback<MissionReceivedEvent>() {
			@Override
			public void call(MissionReceivedEvent c) {
				/**
				 * Send message to find out if there are any available agents
				 */
				Event<AgentAvailableEvent> agentAvailableEventEvent = new AgentAvailableEvent();
				Future<AgentAvailableEvent> future = simplePublisher.sendEvent(agentAvailableEventEvent);


			}
		};
		this.subscribeEvent(MissionReceivedEvent.class, callback);
	}


}
