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
        inventory = Inventory.getInstance();
        // Load string array for test.
        gadges = new String[]{"Sky Hook", "Explosive Pen", "Geiger counter", "X-ray glasses",
                "Dagger shoe"};
        inventory.load(gadges);
    }

    //=============================================getInstance Tests====================================================
    /**
     * Test method for {@link Inventory#getInstance()}
     * Make sure that if we call getInstance(), we get the same Inventory.
     */
    @Test public void testSameInventory() {
        Inventory inventory2 = Inventory.getInstance();
        assertEquals(inventory, inventory2);
    }

    //================================================load Tests========================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#load(String[])}
     * Check if after load, all the items exist in the inventory.
     */
    @Test public void testLoadInventory() {
        gadges = new String[] {"Sky Hook", "Explosive Pen", "Geiger counter", "X-ray glasses"};
        inventory.load(gadges);
        assertTrue(inventory.getItem("Sky Hook"));
        assertTrue(inventory.getItem("Explosive Pen"));
        assertTrue(inventory.getItem("X-ray glasses"));
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#load(String[])}
     * Check if after load, non-existing object does not exist in the inventory.
     * Negative test.
     */
    @Test public void testNegativeLoad() {
        gadges = new String[] {"Sky Hook", "Explosive Pen", "Geiger counter", "X-ray glasses"};
        inventory.load(gadges);
        assertFalse(inventory.getItem("Dagger shoe"));
    }

    //==============================================getItem Tests=======================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#getItem(String)}
     */
    @Test public void testExistItem(){
        gadges = new String[] {"Sky Hook", "Explosive Pen", "Geiger counter", "X-ray glasses"};
        inventory.load(gadges);
        String gadget = "Sky Hook";
        assertTrue(inventory.getItem(gadget));
    }


    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#getItem(String)}
     * Negative test.
     */
    @Test public void testNonExistItem() {
        gadges = new String[] {"Sky Hook", "Explosive Pen", "Geiger counter", "X-ray glasses"};
        inventory.load(gadges);
        String gadget = "gadget";
        assertFalse(inventory.getItem(gadget));
    }
}
