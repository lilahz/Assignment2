package main.java.bgu.spl.mics.application.messages;

import main.java.bgu.spl.mics.Event;
import main.java.bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.List;

public class MissionReceivedEvent implements Event {
    private List<String> serialAgentsNumbers;
    private int duration;
    private String gadget;
    private String missionName;
    private int timeExpired;
    private int timeIssued;

    public MissionReceivedEvent(MissionInfo missionInfo) {
        serialAgentsNumbers = missionInfo.getSerialAgentsNumbers();
        duration = missionInfo.getDuration();
        gadget = missionInfo.getGadget();
        missionName = missionInfo.getMissionName();
        timeExpired = missionInfo.getTimeExpired();
        timeIssued = missionInfo.getTimeIssued();
    }

    public List<String> getSerialAgentsNumbers() {
        return serialAgentsNumbers;
    }

    public int getDuration() {
        return duration;
    }

    public String getGadget() {
        return gadget;
    }

    public String getMissionName() {
        return missionName;
    }

    public int getTimeExpired() {
        return timeExpired;
    }

    public int getTimeIssued() {
        return timeIssued;
    }
}
