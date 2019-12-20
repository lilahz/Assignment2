package main.java.bgu.spl.mics.application.passiveObjects;

import com.google.gson.Gson;
import main.java.bgu.spl.mics.MessageBrokerImpl;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive object representing the diary where all reports are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Diary {
	private AtomicInteger total;
	private List<Report> reportList;

	/**
	 * Initializes new Message Broker instance
	 */
	private static class singleHolder{
		private static Diary instance = new Diary();
	}

	private Diary() {
		total =  new AtomicInteger(0);
		reportList = new ArrayList<>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Diary getInstance() {
		return singleHolder.instance;
	}

	/**
	 * returns all the reports
	 * @return ArrayList of reports
	 */
	public List<Report> getReports() {
		return reportList;
	}

	/**
	 * adds a report to the diary
	 * @param reportToAdd - the report to add
	 */
	public void addReport(Report reportToAdd){
		reportList.add(reportToAdd);
	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<Report> which is a
	 * List of all the reports in the diary.
	 * This method is called by the main method in order to generate the output.
	 */
	public void printToFile(String filename){
		try {
			FileOutputStream file =new FileOutputStream(filename);
			Gson gson = new Gson();
			file.write(("reports:").getBytes());
			for (int i = 0; i < reportList.size(); i++){
				file.write(("\n " + i + ":" +'\n').getBytes());
				file.write(("\t" + "missionName: " + reportList.get(i).getMissionName()+ "\n").getBytes());
				file.write(("\t" + "M: " + reportList.get(i).getM() + '\n').getBytes());
				file.write(("\t" + "moneypenny: " + reportList.get(i).getMoneypenny() + "\n").getBytes());
				file.write(("\t" + "agentsSerialNumbers: " + '\n').getBytes());
				for (int j = 0; j < reportList.get(i).getAgentsSerialNumbers().size(); j++){
					file.write(("\t" + "\t"  + j + ": " + reportList.get(i).getAgentsSerialNumbers().get(j)  +"\n").getBytes());
				}
				file.write(("\t" + "agentsNames: " + "\n").getBytes());
				for (int j = 0; j < reportList.get(i).getAgentsNames().size(); j++){
					file.write(("\t" + "\t" + j + ": " + reportList.get(i).getAgentsNames().get(j)  +"\n").getBytes());
				}
				file.write(("\t" + "gadgetName: " +reportList.get(i).getGadgetName() + "\n").getBytes());
				file.write(("\t" + "timeCreated: " +reportList.get(i).getTimeCreated() + "\n").getBytes());
				file.write(("\t" + "timeIssued: " +reportList.get(i).getTimeIssued() + "\n").getBytes());
				file.write(("\t" + "qTime: " +reportList.get(i).getQTime()+ "\n").getBytes());
			}
			file.write(("total: " + total).getBytes());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the total number of received missions (executed / aborted) be all the M-instances.
	 * @return the total number of received missions (executed / aborted) be all the M-instances.
	 */
	public int getTotal(){
		return total.intValue();
	}

	/**
	 * Increments the total number of received missions by 1
	 */
	public void incrementTotal(){
		int oldVal, newVal;
		do {
			oldVal = total.intValue();
			newVal = total.intValue()+1;
		} while (!total.compareAndSet(oldVal, newVal));
	}
}
