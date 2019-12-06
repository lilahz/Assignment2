package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;

/**
 * Only this Subscriber\Publisher can access the Squad!
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class SquadManager extends Subscriber {

	public SquadManager() {
		super("Change_This_Name");
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		
	}

}
