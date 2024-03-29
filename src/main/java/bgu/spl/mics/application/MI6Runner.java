package main.java.bgu.spl.mics.application;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.stream.JsonReader;
import main.java.bgu.spl.mics.*;
import main.java.bgu.spl.mics.application.passiveObjects.Diary;
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
    public static ArrayList<Thread> threads = new ArrayList<>();
    public static TimeService timeService;

    public static void main(String[] args) {
        MessageBroker messageBroker = MessageBrokerImpl.getInstance(); // maybe wrong - check about the reference
        Inventory inventory = Inventory.getInstance();
        Squad squad = Squad.getInstance();
        Diary diary = Diary.getInstance();

        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(args[0]));
            JsonHelper helper = gson.fromJson(reader, JsonHelper.class);
            inventory.load(helper.inventory);
            squad.load(helper.squad);
            loadServices(helper.services);

            System.out.println("Starting to create threads");

            // Create and start running threads
            createThreads();

            // waiting for all threads to be done
            for (Thread t : threads) {
                t.join();
                System.out.println("waiting for " + t.getName());
            }
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }



//        ObjectOutputStream outputStream = null;
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(args[1])
//        }
        inventory.printToFile(args[1]);
        diary.printToFile(args[2]);
        System.out.println("Printing");
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

    /**
     * Create thread for each service in the subscribers array.
     * Start each thread and add it to the threads array.
     */
    public static void createThreads() {
        for (int i = 0; i < subscribers.size(); i++) {
            Thread t = new Thread((subscribers.get(i)),subscribers.get(i).getName());
            System.out.println("Starting " + subscribers.get(i).getName());
            threads.add(t);
            t.setDaemon(true);
            t.start();
        }

        Thread t = new Thread(timeService);
        System.out.println("Starting " + timeService.getName());
        t.setDaemon(true);
        t.start();
//        threads.add(t);
    }

}