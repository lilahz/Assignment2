package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.*;
import main.java.bgu.spl.mics.application.messages.*;
import main.java.bgu.spl.mics.application.passiveObjects.Diary;
import main.java.bgu.spl.mics.application.passiveObjects.Report;

import java.util.List;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private int currentTick;
	private int id;
	private Boolean executed = false;
	private Diary diary;
	private Report report;
	private Future<Integer> moneyPennyIdFuture;
	private Future<Integer> qTimeFuture;
	private Future<List<String>> agentNamesFuture;

	public M(int id) {
		super("M" + id);
		this.id = id;
		diary = Diary.getInstance();
	}

	@Override
	/**
	 * Subscribes to A
	 */
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, (callBack) -> {
			currentTick = callBack.getCurrentTick();
		});

		subscribeEvent(MissionReceivedEvent.class, (callBack) -> {
			// Send event received by MoneyPenny - questioning if the agents are available
			moneyPennyIdFuture = getSimplePublisher().sendEvent(
					new AgentAvailableEvent(callBack.getSerialAgentsNumbers(), callBack.getDuration()));
			// If the agents are available
			if (moneyPennyIdFuture.isDone()) {
				// Check if the gadget is also available for the mission
				qTimeFuture = getSimplePublisher().sendEvent(
						new GadgetAvailableEvent(callBack.getGadget(), currentTick));
				if (qTimeFuture.isDone()) {
					// Check if the time hasn't expired
					if (callBack.getTimeExpired() >= currentTick + callBack.getDuration()) {
						agentNamesFuture = getSimplePublisher().sendEvent(
								new AgentSendEvent(callBack.getSerialAgentsNumbers(), callBack.getDuration())
						);
						executed = true;
					}
					// If time expired abort mission and release agents
					else {
						Future<Boolean> abortMission = getSimplePublisher().sendEvent(
								new AgentReleaseEvent(callBack.getSerialAgentsNumbers())
						);
					}
				}
				// If gadget is not available abort mission and release agents
				else {
					Future<Boolean> abortMission = getSimplePublisher().sendEvent(
							new AgentReleaseEvent(callBack.getSerialAgentsNumbers())
					);
				}
			}
			diary.incrementTotal();

			// The report of the current mission, only if executed
			if (executed) {
				report = new Report();
				report.setMissionName(callBack.getMissionName());
				report.setM(this.id);
				report.setMoneypenny(moneyPennyIdFuture.get());
				report.setAgentsSerialNumbers(callBack.getSerialAgentsNumbers());
				report.setAgentsNames(agentNamesFuture.get());
				report.setGadgetName(callBack.getGadget());
				report.setQTime(qTimeFuture.get());
				report.setTimeIssued(callBack.getTimeSent());
				report.setTimeCreated(currentTick);

				diary.addReport(report);
			}

		});
	}


}
