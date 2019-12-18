package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.Subscriber;
import main.java.bgu.spl.mics.application.messages.GadgetAvailableEvent;
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

	public Q() {
		super("Q");
	}

	@Override
	protected void initialize() {
		subscribeEvent(GadgetAvailableEvent.class , (callBack)->{
			if (inventory.getItem(callBack.getGadget()))
				complete(callBack, callBack.getTime());
		});
		
	}

}
