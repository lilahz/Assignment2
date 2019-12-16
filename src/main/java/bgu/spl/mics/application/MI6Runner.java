package main.java.bgu.spl.mics.application;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import main.java.bgu.spl.mics.MessageBroker;
import main.java.bgu.spl.mics.MessageBrokerImpl;
import main.java.bgu.spl.mics.application.passiveObjects.Agent;
import main.java.bgu.spl.mics.application.passiveObjects.Inventory;
import main.java.bgu.spl.mics.application.passiveObjects.Squad;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import com.google.gson.*;


/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args) {
        MessageBroker messageBroker = MessageBrokerImpl.getInstance(); // maybe wrong - check about the reference
        Inventory inventory = Inventory.getInstance();
        Squad squad = Squad.getInstance();

        try {
            Object j = new JSONParser().parse(new FileReader(args[0]));
            JSONObject jo = (JSONObject) j;
//            inventory.load(getGadgets(jo));
            squad.load(getAgents(jo));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        //TODO: change to GSON if nessecary else delete all the lines
//        Gson gson = new Gson();
//        try {
//            JsonReader reader = new JsonReader(new FileReader(args[0]));
//            JsonParser parser = gson.fromJson(reader, JsonParser.class);
//
//            String[] gadgets=  gson.fromJson(reader, String[].class);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Helper function to read the gadgets from the JSON file
     *
     * @param j JSONObject to read the gadgets from
     * @return array of strings representing the gadgets
     */
    public static String[] getGadgets(JSONObject j) {
        JSONArray gadgets2 = (JSONArray) j.get("inventory");
        String[] gadgets = new String[gadgets2.size()];
        for (int i = 0; i < gadgets2.size(); i++) {
            gadgets[i] = (String) gadgets2.get(i);
        }
        return gadgets;
    }

    public static Agent[] getAgents(JSONObject j) {
        JSONArray squad = (JSONArray) j.get("squad");
        Agent[] agents = new Agent[squad.size()];
        for (int i = 0; i < squad.size(); i++) {
            Map tmp = (Map) squad.get(i);
            String[] tmpArr = new String[tmp.size()];
            Agent agent = new Agent();
        }
        return agents;
    }
}