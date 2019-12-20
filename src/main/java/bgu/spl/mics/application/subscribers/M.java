package main.java.bgu.spl.mics.application.subscribers;

import main.java.bgu.spl.mics.*;
import main.java.bgu.spl.mics.application.messages.*;
import main.java.bgu.spl.mics.application.passiveObjects.Diary;
import main.java.bgu.spl.mics.application.passiveObjects.Report;
import main.java.bgu.spl.mics.application.passiveObjects.Squad;

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
    private Boolean executed;
    private Diary diary;
    private Report report;
    private Future<Object[]> moneyPennyIdFuture;
    private Future<Integer> qTimeFuture;
    private Future<List<String>> agentNamesFuture;

    private Squad squad = Squad.getInstance();

    public M(int id) {
        super("M" + id);
        this.id = id;
        this.executed = false;
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
                // Send event to moneyPenny to acquire agents
                executed = false;
                System.out.println("M" + id + " Sending AgentAvailableEvent to MoneyPenny");
                moneyPennyIdFuture = getSimplePublisher().sendEvent(new AgentAvailableEvent(callBack.getSerialAgentsNumbers(), callBack.getDuration()));

                if (moneyPennyIdFuture.get() != null) {
                    boolean acquired = ((AnswerToM) moneyPennyIdFuture.get()[1]).acquiredAgent();
                    System.out.println("M" + id + "Money penny acquired " + acquired);
                    // Check if the agents were acquired

                    if (acquired) {
                        System.out.println("M" + id + " Sending GadgetAvailableEvent to Q");
                        qTimeFuture = getSimplePublisher().sendEvent(new GadgetAvailableEvent(callBack.getGadget(), currentTick));
                        // If the gadget is available, send event to MoneyPenny to send agents

                        if (qTimeFuture.get() != null) {
                            System.out.println("M" + id + " Received callback from Q, " + callBack.getGadget() + " available");

                            if (callBack.getTimeExpired() >= currentTick + callBack.getDuration()) {
                                System.out.println("M" + id + " Sending AgentSendEvent to MoneyPenny");
                                agentNamesFuture = getSimplePublisher().sendEvent(new AgentSendEvent(callBack.getSerialAgentsNumbers(), callBack.getDuration()));

                                if (agentNamesFuture.get() != null) {
                                    System.out.println("================================================M" + id + " Mission " + callBack.getMissionName() + " done.");
                                    executed = true;
                                }
                            }else executed = false;
                        }else executed = false;
                    }else executed = false;
                }

                if (!executed) {
                    System.out.println("================================================M" + id + " Mission " + callBack.getMissionName() + " failed to execute");
                    Future<?> abortMission = getSimplePublisher().sendEvent(new AgentReleaseEvent(callBack.getSerialAgentsNumbers()));
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
    } catch(
    Exception e)

    {
        e.printStackTrace();
    }
}


}
