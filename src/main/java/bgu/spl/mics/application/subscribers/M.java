package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.Event;
import main.java.bgu.spl.mics.Subscriber;
import main.java.bgu.spl.mics.application.messages.missionReceivedEvent;

import java.util.Queue;

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
	protected void initialize() {

		
	}


}
