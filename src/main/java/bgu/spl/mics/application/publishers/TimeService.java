package main.java.bgu.spl.mics.application.publishers;

import main.java.bgu.spl.mics.MessageBroker;
import main.java.bgu.spl.mics.Publisher;
import main.java.bgu.spl.mics.SimplePublisher;
import main.java.bgu.spl.mics.application.messages.TickBroadcast;

import java.util.Timer;
import java.util.TimerTask;

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
	private int duration;
	private int currentTime;
	private Timer timer;
	private SimplePublisher simplePublisher = getSimplePublisher();

	public TimeService(int duration) {
		super("Time");
		this.duration = duration;
		this.currentTime = 0;
		timer = new Timer();

	}

	@Override
	protected void initialize() {
	}

	@Override
	public void run() {
		while (currentTime <= duration) {
			TimerTask task = new TimerTask() {
				public void run() {
					simplePublisher.sendBroadcast(new TickBroadcast(currentTime));
					currentTime++;
				}
			};
			timer.schedule(task, 100);
		}

	}

}
