package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.Subscriber;
import main.java.bgu.spl.mics.application.messages.GadgetAvailableEvent;
import main.java.bgu.spl.mics.application.messages.TerminateBroadcast;
import main.java.bgu.spl.mics.application.messages.TickBroadcast;
import main.java.bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private Inventory inventory = Inventory.getInstance();
	private int currentTick ;
	public Q() {
		super("Q");
	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, (callBack)->{
			currentTick = callBack.getCurrentTick();
		});

		subscribeEvent(GadgetAvailableEvent.class , (callBack)->{
			System.out.println("Q - Checking for available gadget");
			if (inventory.getItem(callBack.getGadget())) {
				complete(callBack, currentTick);
				System.out.println("get time : " + callBack.getTime());
				System.out.println("-----------------got it! "+callBack.getGadget());
			}
			else {
				complete(callBack, null);
				System.out.println("-----------didnt get the gadget "+callBack.getGadget());
			}
		});

		subscribeBroadcast(TerminateBroadcast.class, (callBack)->{
			System.out.println(this.getName() + " Have been terminated ");
			terminate();
		});
		
	}

}
