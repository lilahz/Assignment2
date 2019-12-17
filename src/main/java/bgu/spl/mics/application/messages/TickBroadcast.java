package main.java.bgu.spl.mics.application.messages;

import main.java.bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {
    private int currentTick;

    public TickBroadcast(int currentTick) {
        this.currentTick = currentTick;
    }

    public int getCurrentTick() {
        return currentTick;
    }
}
