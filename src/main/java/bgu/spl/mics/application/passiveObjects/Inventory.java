package main.java.bgu.spl.mics.application.passiveObjects;

import main.java.bgu.spl.mics.MessageBrokerImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
	private List<String> gadgets;
	/**
	 * Initializes new Message Broker instance
	 */
	private static class singleHolder{
		private static Inventory instance = new Inventory();
	}

	/**
	 * Constructor
	 */
	private Inventory(){
	}
	/**
     * Retrieves the single instance of this class.
     */
	public static Inventory getInstance() {
		return singleHolder.instance;
	}

	/**
     * Initializes the inventory. This method adds all the items given to the gadget
     * inventory.
     * <p>
     * @param inventory 	Data structure containing all data necessary for initialization
     * 						of the inventory.
     */
	public void load (String[] inventory) {
		gadgets = Collections.synchronizedList(new ArrayList<>());
		for (int i=0; i<inventory.length;i++)
		{
			gadgets.add(inventory[i]);
		}
	}
	
	/**
     * acquires a gadget and returns 'true' if it exists.
     * <p>
     * @param gadget 		Name of the gadget to check if available
     * @return 	‘false’ if the gadget is missing, and ‘true’ otherwise
     */
	public boolean getItem(String gadget){
		//TODO: Implement this
		return true;
	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<String> which is a
	 * list of all the of the gadgeds.
	 * This method is called by the main method in order to generate the output.
	 */
	public void printToFile(String filename){
		//TODO: Implement this
	}
}
