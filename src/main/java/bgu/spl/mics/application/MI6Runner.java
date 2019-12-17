package main.java.bgu.spl.mics.application;
import java.io.*;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;
import main.java.bgu.spl.mics.*;
import main.java.bgu.spl.mics.application.passiveObjects.Inventory;
import main.java.bgu.spl.mics.application.passiveObjects.Squad;
import main.java.bgu.spl.mics.application.publishers.TimeService;
import main.java.bgu.spl.mics.application.subscribers.Intelligence;
import main.java.bgu.spl.mics.application.subscribers.M;
import main.java.bgu.spl.mics.application.subscribers.Moneypenny;
import com.google.gson.*;
import main.java.bgu.spl.mics.application.subscribers.Q;


/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static ArrayList<Subscriber> subscribers = new ArrayList<>();
    public static TimeService timeService;

    public static void main(String[] args) {
        MessageBroker messageBroker = MessageBrokerImpl.getInstance(); // maybe wrong - check about the reference
        Inventory inventory = Inventory.getInstance();
        Squad squad = Squad.getInstance();

        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(args[0]));
            JsonHelper helper = gson.fromJson(reader, JsonHelper.class);
            inventory.load(helper.inventory);
            squad.load(helper.squad);
            loadServices(helper.services);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Helper function to load services from given JSON file
     * Services to load - M, MoneyPenny, Intelligence and Time.
     * @param services JsonObject representing the list of services
     */
    public static void loadServices(ServicesType services){
        for (int i = 0; i < services.M; i++) {
            subscribers.add(new M(i));
        }
        for (int i = 0; i < services.Moneypenny; i++) {
            subscribers.add(new Moneypenny(i));
        }
        for (int i = 0; i < services.intelligence.length; i++) {
            Intelligence tmp = new Intelligence(i);
            tmp.load(services.intelligence[i].getMissions());

            subscribers.add(tmp);
        }

        timeService = new TimeService(services.time);

        subscribers.add(new Q());
    }

}