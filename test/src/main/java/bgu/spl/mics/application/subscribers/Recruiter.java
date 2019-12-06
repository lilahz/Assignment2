package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;

/**
 * Handles MissionReceivedEvents, and finally sends a ReadyEvent.
 * Each Recruiter has its unique name, which it sends to M in the ReadyEvent (along with more information).
 * This class may not hold references for objects which it is not responsible for:
 * {@link bgu.spl.mics.application.passiveObjects.Diary}, {@link bgu.spl.mics.application.passiveObjects.Squad},
 * {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Recruiter extends Subscriber {

	public Recruiter() {
		super("Change_This_Name");
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		
	}

}
