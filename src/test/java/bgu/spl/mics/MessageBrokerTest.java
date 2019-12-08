package test.java.bgu.spl.mics;

import main.java.bgu.spl.mics.*;
import main.java.bgu.spl.mics.example.messages.ExampleBroadcast;
import main.java.bgu.spl.mics.example.messages.ExampleEvent;
import main.java.bgu.spl.mics.application.subscribers.M;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a Unit Test for the {@link main.java.bgu.spl.mics.MessageBrokerImpl} class.
 */
public class MessageBrokerTest {
    private MessageBroker messageBroker;
    private ExampleEvent event;
    private ExampleBroadcast broadcast;
    private Subscriber notM;
    private Subscriber M;

    @BeforeEach
    public void setUp(){
        messageBroker = MessageBrokerImpl.getInstance();
        event = new ExampleEvent("M");
        broadcast = new ExampleBroadcast("notM");
        notM = new M();
        M = new M();
    }

    /** Test method for {@link MessageBrokerImpl#getInstance()}
     * Test for the thread-safe singltone.
     */
    @Test public void testGetInstance(){
        MessageBroker messageBroker2 = MessageBrokerImpl.getInstance();
        assertEquals(messageBroker2, messageBroker);
    }

    /** Test method for {@link MessageBrokerImpl#subscribeEvent(Class, Subscriber)} ()}
     * Checks if the subscriber who called this method is now subscribe for the type of event.
     */
    @Test public void testSubEvent(){
        messageBroker.subscribeEvent(event.getClass(), notM);
        messageBroker.sendEvent(event);
        try {
            assertEquals(messageBroker.awaitMessage(notM), event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Test method for {@link MessageBrokerImpl#subscribeBroadcast(Class, Subscriber)}
     * Checks if the subscriber who called this method is now subscribe for the type of event.
     */
    @Test public void testSubBroad(){
        messageBroker.subscribeBroadcast(broadcast.getClass(), notM);
        messageBroker.sendBroadcast(broadcast);
        try {
            assertEquals(messageBroker.awaitMessage(notM), broadcast);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link MessageBrokerImpl#complete(Event, Object)}
     * Check if event status is changed to complete
     */
    @Test public void testComplete() {
        Future<String> future = messageBroker.sendEvent(event);
        assertFalse(future.isDone());
        messageBroker.complete(event, "COMPLETE");
        assertTrue(future.isDone());
    }

    /**
     * Test method for {@link MessageBrokerImpl#sendBroadcast(Broadcast)}
     * Check if the sent broadcast was received.
     */
    @Test public void testSendBroad() {
        messageBroker.subscribeBroadcast(broadcast.getClass(), notM);
        messageBroker.sendBroadcast(broadcast);
        try {
            assertEquals(messageBroker.awaitMessage(notM), broadcast);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link MessageBrokerImpl#sendEvent(Event)}
     * Check if the send event was received.
     */
    @Test public void testSendEvent() {
        Future<String> future = messageBroker.sendEvent(event);
        assertNull(future);
        messageBroker.subscribeEvent(event.getClass(), notM);
        messageBroker.complete(event, "COMPLETE");
        try {
            assertEquals(messageBroker.awaitMessage(notM), event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link MessageBrokerImpl#register(Subscriber)}
     * Make sure a queue of messages was created for the given subscriber
     */
    @Test public void testRegister() {
        messageBroker.register(notM);
        messageBroker.subscribeEvent(event.getClass(), M);
        messageBroker.sendEvent(event);
        try {
            messageBroker.awaitMessage(notM);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link MessageBrokerImpl#unregister(Subscriber)}
     * Make sure a queue of messages was created for the given subscriber
     * Negative test.
     */
    @Test public void testUnregister() {
        messageBroker.register(notM);
        messageBroker.subscribeEvent(event.getClass(), M);
        messageBroker.sendEvent(event);
        try {
            messageBroker.awaitMessage(notM);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        messageBroker.unregister(notM);
        messageBroker.sendEvent(event);
        try {
            messageBroker.awaitMessage(notM);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link MessageBrokerImpl#awaitMessage(Subscriber)}
     * Check the subscriber got his message from his queue.
     */
    @Test public void testAwaitMessage() {
        messageBroker.register(notM);
        messageBroker.subscribeEvent(event.getClass(), M);
        messageBroker.sendEvent(event);
        try {
            messageBroker.awaitMessage(notM);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
