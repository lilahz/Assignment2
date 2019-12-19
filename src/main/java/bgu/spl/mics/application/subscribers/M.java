package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.*;
import main.java.bgu.spl.mics.application.messages.*;
import main.java.bgu.spl.mics.application.passiveObjects.Diary;
import main.java.bgu.spl.mics.application.passiveObjects.Report;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
    private int currentTick;
    private int duration;
    private int id;
    private Boolean executed = false;
    private Diary diary;
    private Report report;
    private Future<Object[]> moneyPennyIdFuture;
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
        try {
            subscribeBroadcast(TickBroadcast.class, (callBack) -> {
                currentTick = callBack.getCurrentTick();
                duration = callBack.getDuration();
            });

            subscribeEvent(MissionReceivedEvent.class, (callBack) -> {
                // Send event received by MoneyPenny - questioning if the agents are available
                System.out.println("----------------M" + id + " start executing " + callBack.getMissionName() + "----------");
                System.out.println("M " + id + " - Sending AgentAvailableEvent to MoneyPenny for " + callBack.getMissionName());
                moneyPennyIdFuture = getSimplePublisher().sendEvent(
                        new AgentAvailableEvent(callBack.getSerialAgentsNumbers(), callBack.getDuration()));
                System.out.println("moneyPennyIdFuture is done? " + moneyPennyIdFuture.isDone());
                // If the agents are available
                if (moneyPennyIdFuture.get((duration - currentTick) * 100, TimeUnit.MILLISECONDS) != null) {
                    boolean acquired = ((AnswerToM) moneyPennyIdFuture.get()[1]).acquiredAgent();///wait if not free
                    System.out.println(acquired + "M"+ id);
                    if (acquired) {
                        // Check if the gadget is also available for the mission
                        System.out.println("M " + id + "- Sending GadgetAvailableEvent to Q for " + callBack.getMissionName());
                        qTimeFuture = getSimplePublisher().sendEvent(
                                new GadgetAvailableEvent(callBack.getGadget(), currentTick));
                        if (qTimeFuture.get((duration - currentTick) * 100, TimeUnit.MILLISECONDS) != null) {
                            // Check if the time hasn't expired
                            System.out.println("gad"+ callBack.getGadget());
                            if (callBack.getTimeExpired() >= currentTick + callBack.getDuration()) {
                                System.out.println("M " + id + " - Sending AgentSendEvent to MoneyPenny to " + callBack.getMissionName());
                                agentNamesFuture = getSimplePublisher().sendEvent(
                                        new AgentSendEvent(callBack.getSerialAgentsNumbers(), callBack.getDuration())
                                );
                                System.out.println("Mission done " + callBack.getMissionName());
                                executed = true;
                                Future<Boolean> missionDone = getSimplePublisher().sendEvent(
                                        new AgentReleaseEvent(callBack.getSerialAgentsNumbers())
                                );
                            }
                            // If time expired abort mission and release agents
                        }
                    }
                }
                // If gadget is not available abort mission and release agents
                else if (!executed) {
                    System.out.println("M " + id + "- Mission aborted");
                    Future<Boolean> abortMission = getSimplePublisher().sendEvent(
                            new AgentReleaseEvent(callBack.getSerialAgentsNumbers())
                    );
                }

                diary.incrementTotal();

                // The report of the current mission, only if executed
                if (executed) {
                    report = new Report();
                    report.setMissionName(callBack.getMissionName());
                    report.setM(this.id);
                    report.setMoneypenny((Integer) moneyPennyIdFuture.get()[0]);
                    report.setAgentsSerialNumbers(callBack.getSerialAgentsNumbers());
                    report.setAgentsNames(agentNamesFuture.get());
                    report.setGadgetName(callBack.getGadget());
                    report.setQTime(qTimeFuture.get());
                    report.setTimeIssued(callBack.getTimeSent());
                    report.setTimeCreated(currentTick);

                    diary.addReport(report);
                }

            });

            subscribeBroadcast(TerminateBroadcast.class, (callBack) -> {
                System.out.println(this.getName() + " Have been terminated ");
                terminate();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
