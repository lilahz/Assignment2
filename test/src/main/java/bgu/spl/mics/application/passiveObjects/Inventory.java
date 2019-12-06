package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.Future;

import java.util.List;

/**
 *  That's where Q holds his gadget (e.g. an explosive pen was used in GoldenEye, a geiger counter in Dr. No, etc).
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Inventory {
	private List<Gadget> gadgets;
	/**
     * Retrieves the single instance of this class.
     */
	public static Inventory getInstance() {
		//TODO: Implement this
		return null;
	}

	/**
     * Initializes the inventory. This method adds all the items given to the gadget
     * inventory.
     * <p>
     * @param inventory 	Data structure containing all data necessary for initialization
     * 						of the inventory.
     */
	public void load (Gadget[] inventory) {

	}
	
	/**
     * Checks if a gadget is available.
     * <p>
     * @param gadget 		Name of the gadget to check if available
     * @return 	an {@link Future} object resolved to 'true' if 'gadget' is available , 'false' otherwise.
     */
	public Future<Boolean> isAvailable(String gadget) {
		//TODO: Implement this
		return null;
	}

	/**
     * Permanently removes a gadget from the Inventory.
     * <p>
     * @param gadget 		Name of the gadget.
     */
	public void remove(String gadget) {
		//TODO: Implement this
	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<Gadget> which is a
	 * List of all the reports in the diary.
	 * This method is called by the main method in order to generate the output.
	 */
	public void printToFile(String filename){
		//TODO: Implement this
	}
}
