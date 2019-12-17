package main.java.bgu.spl.mics.application.messages;

import main.java.bgu.spl.mics.Event;
import main.java.bgu.spl.mics.Message;
import main.java.bgu.spl.mics.application.passiveObjects.Agent;

import java.util.List;
import java.util.Map;

public class AgentAvailableEvent implements Event {
    private List<String> agentList;
    private int duration;

    public AgentAvailableEvent(List<String> agentsList, int duration) {
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
