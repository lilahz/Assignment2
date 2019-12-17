package main.java.bgu.spl.mics.application.publishers;

import main.java.bgu.spl.mics.Publisher;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {
	int duration;

	public TimeService(int duration) {
		super("Time");
		this.duration = duration;
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		
	}

	@Override
	public void run() {
		// TODO Implement this
	}

}
