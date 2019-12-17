package main.java.bgu.spl.mics.application.messages;

import main.java.bgu.spl.mics.Event;
import main.java.bgu.spl.mics.Message;
import main.java.bgu.spl.mics.application.passiveObjects.Agent;

public class AgentAvailableEvent implements Event {
    private String serialNumber;
    private String name;
    private boolean isAvailable;

    public AgentAvailableEvent() {

    }
}
