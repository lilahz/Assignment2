package main.java.bgu.spl.mics.application.messages;

import main.java.bgu.spl.mics.Event;

public class GadgetAvailableEvent implements Event {
    private String gadget;
    private int time;

    public GadgetAvailableEvent(String gadget, int time) {
        this.gadget = gadget;
        this.time = time;
    }

    public String getGadget() {
        return gadget;
    }

    public int getTime() {
        return time;
    }
}
