package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.*;
import main.java.bgu.spl.mics.application.messages.MissionReceivedEvent;
import main.java.bgu.spl.mics.application.messages.TerminateBroadcast;
import main.java.bgu.spl.mics.application.messages.TickBroadcast;
import main.java.bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A Publisher\Subscriber.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {
	private MissionInfo[] missions;
	private List<MissionInfo> sortedMissions = new ArrayList<MissionInfo>();
	private int currentTick;

	public Intelligence(int id) {
		super("Intelligence" + id);
	}

	@Override
	/**
	 * Subscribes to TickBroadcast and creates callback to send new mission
	 */
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, (callBack)->{
			currentTick = callBack.getCurrentTick();
			System.out.println("Intelligence - current time " + currentTick);
			if (!sortedMissions.isEmpty()) {
				MissionInfo tmp = sortedMissions.get(0);
				while (tmp != null && tmp.getTimeIssued() == currentTick) {
					sortedMissions.remove(0);
					System.out.println("Intelligence - Starting mission " +tmp.getMissionName());
					getSimplePublisher().sendEvent(new MissionReceivedEvent(tmp, currentTick));
					if (sortedMissions.size() > 0){
						tmp = sortedMissions.get(0);
					}
					else tmp = null;
				}
			}
		});

		subscribeBroadcast(TerminateBroadcast.class, (callBack)->{
			System.out.println(this.getName() + " Have been terminated ");
			terminate();
		});
	}

	/**
	 * Load missions from JSON and sort them by timeIssued
	 * @param missions
	 */
	public void load(MissionInfo[] missions) {
		this.missions = missions;
		sortedMissions();
	}

	/**
	 * Returns array of unsorted missions
	 * @return Array of MissionInfo
	 */
	public MissionInfo[] getMissions() {
		return missions;
	}

	/**
	 * Sort the mission in mission[] by timeIssued
	 */
	public void sortedMissions() {
		sortedMissions.add(missions[0]);
		int i = 1;
		while (i < missions.length) {
			MissionInfo tmp = missions[i];
			for (int j = 0; j < sortedMissions.size(); j++){
				if (tmp.getTimeIssued() < sortedMissions.get(j).getTimeIssued()){
					sortedMissions.add(j, tmp);
				}
				else sortedMissions.add(tmp);
				j = sortedMissions.size();
			}
			i++;
		}
	}
}
