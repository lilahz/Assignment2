package main.java.bgu.spl.mics.example;

import main.java.bgu.spl.mics.Subscriber;

public interface Creator {
    Subscriber create(String name, String[] args);
}
