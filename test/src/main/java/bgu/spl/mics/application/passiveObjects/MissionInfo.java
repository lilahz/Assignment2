package bgu.spl.mics.application.passiveObjects;

import java.util.List;

/**
 * Passive data-object representing information about a mission.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class MissionInfo {

	/**
     * Retrieves the name of the mission.
     */
	public String missionName() {
		// TODO Implement this
		return null;
	}

	/**
     * Retrieves the serial agent number.
     */
	public int getSerialAgentNumber() {
		// TODO Implement this
		return 0;
	}
	
	/**
     * Retrieves the gadget name.
     */
	public String getGadget() {
		// TODO Implement this
		return null;
	}
	
	/**
     * Retrieves the time the mission was issued in milliseconds.
     */
	public int getTimeIssued() {
		// TODO Implement this
		return 0;
	}

	
	/**
     * Retrieves the time that if it that time passed the mission should be aborted.
     */
	public int getTimeExpired() {
		// TODO Implement this
		return 0;
	}
	
}
