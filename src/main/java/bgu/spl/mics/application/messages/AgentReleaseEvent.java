package main.java.bgu.spl.mics.application.messages;

import main.java.bgu.spl.mics.Event;

import java.util.List;

public class AgentReleaseEvent implements Event {
    private List<String> agentList;

    public AgentReleaseEvent(List<String> agentsList) {
        this.agentList = agentsList;
    }

    public List<String> getAgentList() {
        return agentList;
    }
}
