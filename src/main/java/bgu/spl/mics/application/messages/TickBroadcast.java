package main.java.bgu.spl.mics.application.messages;

import main.java.bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {
    private int currentTick;
    private int duration;

    public TickBroadcast(int currentTick, int duration) {
        this.currentTick = currentTick;
        this.duration = duration;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public int getDuration() {
        return duration;
    }
}
