package test.java.bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.bgu.spl.mics.application.passiveObjects.Inventory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a Unit Test for the {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory} class.
 */
public class InventoryTest {
    private Inventory inventory;
    private String[] gadges;
    @BeforeEach
    public void setUp(){
        // Inventory instance
        inventory = Inventory.getInstance();
        gadges = new String[]{"Sky Hook", "Explosive Pen", "Geiger counter", "X-ray glasses",
                "Dagger shoe"};
        inventory.load(gadges);
    }

    //=============================================getInstance Tests====================================================
    /**
     * Test method for {@link Inventory#getInstance()}
     * Make sure that if we call getInstance(), we get the same Inventory.
     *
     * @return true since Inventory is a thread-safe singleton
     */
    @Test public void testSameInventory() {
        Inventory inventory2 = Inventory.getInstance();
        assertEquals(inventory, inventory2);
    }

    //================================================load Tests========================================================


    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#load(String[])}
     * Check if after load, the size of the inventory is right.
     *
     * @reutrn true
     */
    @Test public void testLoadInventory() {
        assertTrue(gadges.length == inventory.getSize());
    }

    //==============================================getItem Tests=======================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#getItem(String)}
     *
     * @return true since the given object should exist.
     */
    @Test public void testExistItem(){
        String gadget = "Sky Hook";
        assertTrue(inventory.getItem(gadget));
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#getItem(String)}
     * Negative test.
     *
     * @return false since the given object shouldn't exist.
     */
    @Test public void testNonExistItem() {
        String gadget = "gadget";
        assertFalse(inventory.getItem(gadget));
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#getItem(String)}
     * Test if after one call, gadget is really removed from list.
     * Negative test.
     *
     * @return false since the object should be removed.
     */
    @Test public void testAfterOneGet() {
        String gadget = "Explosive Pen";
        inventory.getItem(gadget);
        assertFalse(inventory.getItem(gadget));
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#getItem(String)}
     * Test if function returns false when the list is empty
     * Negative test.
     *
     * @return false since the list is empty
     */
    @Test public void testEmptyList() {

    }
}
