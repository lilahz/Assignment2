package main.java.bgu.spl.mics.application.messages;

import main.java.bgu.spl.mics.Event;

import java.util.List;

public class AgentSendEvent implements Event {
    private List<String> agentList;
    private int duration;

    public AgentSendEvent(List<String> agentsList, int duration) {
        this.agentList = agentsList;
        this.duration = duration;
    }

    public List<String> getAgentList() {
        return agentList;
    }

    public int getDuration() {
        return duration;
    }
}