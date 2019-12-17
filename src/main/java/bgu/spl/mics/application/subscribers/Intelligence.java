package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.Subscriber;
import main.java.bgu.spl.mics.application.passiveObjects.MissionInfo;

/**
 * A Publisher\Subscriber.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {
	private MissionInfo[] missions;

	public Intelligence(int id) {
		super("Intelligence" + id);
	}

	@Override
	protected void initialize() {
		// TODO Implement this
	}

	public void load(MissionInfo[] missions) {
		this.missions = missions;
	}

	public MissionInfo[] getMissions() {
		return missions;
	}
}
