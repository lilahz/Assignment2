package main.java.bgu.spl.mics.application.publishers;

import main.java.bgu.spl.mics.MessageBroker;
import main.java.bgu.spl.mics.Publisher;
import main.java.bgu.spl.mics.SimplePublisher;
import main.java.bgu.spl.mics.application.messages.TerminateBroadcast;
import main.java.bgu.spl.mics.application.messages.TickBroadcast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {
    private int duration;
    private AtomicInteger currentTime;
    private Timer timer;
    private SimplePublisher simplePublisher = getSimplePublisher();

    public TimeService(int duration) {
        super("Time");
        this.duration = duration;
        this.currentTime = new AtomicInteger(0);
        timer = new Timer();

    }

    @Override
    protected void initialize() {
    }

    @Override
    public void run() {
        TimerTask task = new TimerTask() {
            public void run() {
                if (currentTime.intValue() <= duration) {
                    simplePublisher.sendBroadcast(new TickBroadcast(currentTime.intValue(),duration));
                    System.out.println( " -------------- TIME TICK ----------- " + currentTime);
                    int oldVal, newVal;
                    do {
                        oldVal = currentTime.intValue();
                        newVal = currentTime.intValue() + 1;
                    } while (!currentTime.compareAndSet(oldVal, newVal));
                }
                else {
                    simplePublisher.sendBroadcast(new TerminateBroadcast());
                    timer.cancel();
                    timer.purge();
                    System.out.println("Timer has been canceled");
                }
            }
        };
        timer.schedule(task, 0, 100); // todo thats the problem!?
    }
}
