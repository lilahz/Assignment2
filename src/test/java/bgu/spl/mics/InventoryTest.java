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
    private String[] gadgets;
    @BeforeEach
    public void setUp(){
        inventory = Inventory.getInstance();
        // Load string array for test.
        gadgets = new String[]{"Sky Hook", "Explosive Pen", "Geiger counter", "X-ray glasses",
                "Dagger shoe"};
        inventory.load(gadgets);
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
        assertFalse(inventory.getItem("Dagger shoe"));
    }

    //==============================================getItem Tests=======================================================

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#getItem(String)}
     */
    @Test public void testExistItem(){
        String gadget = "Sky Hook";
        assertTrue(inventory.getItem(gadget));
    }


    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#getItem(String)}
     * Negative test.
     */
    @Test public void testNonExistItem() {
        String gadget = "gadget";
        assertFalse(inventory.getItem(gadget));
    }

    /**
     * Test method for {@link main.java.bgu.spl.mics.application.passiveObjects.Inventory#getItem(String)}
     * Make Sure item is removed after one call for getItem.
     * Negative test.
     */
    @Test public void testTwiceGetItem() {
        String gadget = "Sky Hook";
        inventory.getItem(gadget);
        assertFalse(inventory.getItem(gadget));
    }
}
