package test.java.bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.bgu.spl.mics.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {
    private static final TimeUnit SECONDS = TimeUnit.SECONDS;
    Future<String> future;

    @BeforeEach
    public void setUp() {
        future = new Future<>();
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.Future#isDone()}
     * check if the mission is resolved before and after a resolve action.
     */
    @Test public void testIsDone() {
        assertFalse((future.isDone()));
        future.resolve("Mission done");
        assertTrue(future.isDone());
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.Future#resolve(Object)}
     * sets the result of the operation to a new value.
     */
    @Test
    public void resolve() {
        String result = "Mission done";
        future.resolve(result);
        assertEquals(future.get(), result);
        result = "Mission failed";
        assertNotEquals(future.get(), result);
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.Future#get()}
     * retrieves the result of the opertaion .
     */
    @Test
    public void get() {
        String result = "Mission done";
        future.resolve(result);
        assertEquals(future.get(), result);
        result = "Mission failed";
        assertNotEquals(future.get(), result);
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.Future#get(long, TimeUnit)}
     * retrieves the result of the opertaion if possible if not wait the time unit and trys again.
     */
    @Test
    public void getWithTime() {
        String result = "Mission done";
        assertNull(future.get(10, SECONDS));
        future.resolve(result);
        assertEquals(future.get(10, SECONDS), result);
        result = "Mission failed";
        future.resolve(result);
        assertNotEquals(future.get(10, SECONDS), result);
    }
}