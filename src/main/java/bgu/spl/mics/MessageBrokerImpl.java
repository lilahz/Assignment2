package main.java.bgu.spl.mics;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {
	// TODO: check if needed to create queue for broadcast messages

	// Map messagesQ for each subscriber.
	private Map<RunnableSubPub, LinkedBlockingQueue<Message>> subsMap;
	// Map of types of events and broadcasts and Q of subscribers that are subscribed to that type.
	private Map<Class<? extends Message>, ConcurrentLinkedQueue<RunnableSubPub>> eventsMap;
	// Map of subscribers and what type of broadcast they are subscribed to.
	private Map<Class<? extends Message>, ConcurrentLinkedQueue<RunnableSubPub>> broadcastsMap;
	//Map of Future objects and the events it represents.
	private Map<Event<?>, Future<?>> futureMap;
	/**
	 * Initializes new Message Broker instance
	 */
	private static class singleHolder{
		private static MessageBrokerImpl instance = new MessageBrokerImpl();
	}

	/**
	 * Constructor
	 */
	private MessageBrokerImpl(){
		eventsMap = new ConcurrentHashMap<>();
		broadcastsMap = new ConcurrentHashMap<>();
		subsMap = new ConcurrentHashMap<>();
		futureMap = new ConcurrentHashMap<>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static MessageBroker getInstance() {
		return singleHolder.instance;
	}

	@Override
	public synchronized <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
		// if the type of event does not exist add the type to the map and create Q with m.
		if (!eventsMap.containsKey(type)) {
			ConcurrentLinkedQueue tmp = new ConcurrentLinkedQueue();
			tmp.add(m);
			// TODO: maybe change to putIfAbscent? and then remove the condition
			eventsMap.put(type, tmp);
		}
		// Else, the type exist and add m to the Q.
		else {
			eventsMap.get(type).add(m);
		}
	}

	@Override
	public synchronized void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		// if the type of broadcast does not exist add the type to the map and create Q with m.
		if (!broadcastsMap.containsKey(type)) {
			ConcurrentLinkedQueue tmp = new ConcurrentLinkedQueue();
			tmp.add(m);
			broadcastsMap.put(type, tmp);
		}
		// Else, the type exist and add m to the Q.
		else {
			broadcastsMap.get(type).add(m);
		}
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO: check what needs to be done to implement thread-safe
		Future<T> future = (Future<T>) futureMap.get(e);
		if (future != null)
			future.resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if (broadcastsMap.containsKey(b.getClass())) {
			ConcurrentLinkedQueue tmp = broadcastsMap.get(b.getClass());
			for (Object x : tmp) {
				subsMap.get(x).add(b);
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		//we will add to the messageQ of the first subscriber in the Type of event in eventsmap
		//and we will take him out of the Q and insert him again(round robin).
		Future<T> output = new Future<>();
		if (eventsMap.containsKey(e)){
			RunnableSubPub temp = eventsMap.get(e.getClass()).poll();
			if (temp != null){
				subsMap.get(temp).add(e);
				eventsMap.get(e.getClass()).add(temp);
			}
			else return null;

		}
		futureMap.put(e,output);
		return output;
	}

	/**
	 * Allocates a message-queue for the {@link Subscriber} {@code m}.
	 * <p>
	 * @param m the Subscriber to create a queue for.
	 */
	@Override
	public void register(Subscriber m) {
		if (!subsMap.containsKey(m)){
			LinkedBlockingQueue tmp = new LinkedBlockingQueue();
			subsMap.put(m, tmp);
		}
	}

	/**
	 * Removes the message queue allocated to {@code m} via the call to
	 * {@link #register(Subscriber)} and cleans all references
	 * related to {@code m} in this MessageBroker. If {@code m} was not
	 * registered, nothing should happen.
	 * <p>
	 * @param m the Subscriber to unregister.
	 */
	@Override
	public void unregister(Subscriber m) {
		subsMap.remove(m, subsMap.get(m));
		for (int i = 0; i < eventsMap.size(); i++) {
			if (eventsMap.get(i).contains(m))
				synchronized (eventsMap.get(i)) {
					eventsMap.get(i).remove(m);
				}
		}
		for (int j = 0; j < broadcastsMap.size(); j++) {
			if (broadcastsMap.get(j).contains(m))
				synchronized (broadcastsMap.get(j)) {
					broadcastsMap.get(j).remove(m);
				}
		}
	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
		LinkedBlockingQueue tmp = subsMap.get(m);
		Message message = (Message) tmp.take();
		return message;
	}

	

}
