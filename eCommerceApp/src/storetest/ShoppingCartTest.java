/*
    storetest.ShoppingCartTest.java
    Bren-Gelyn Padlan
    101148482
 */
package storetest;

import org.junit.jupiter.api.*;
import store.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a test class for ShoppingCart class. It tests ShoppingCart's three
 * methods namely addItem, removeItem and clearCart.
 *
 * @author Bren-Gelyn Padlan
 * @version 1.0
 */
public class ShoppingCartTest {
    /**
     * A test ShoppingCart instance.
     */
    private static ShoppingCart sCart;

    /**
     * This method sets up the initial stage of the test by initializing the test ShoppingCart instance.
     */
    @BeforeAll
    public static void init() {
        sCart = new ShoppingCart();
    }

    /**
     * This method tests the addItem() method if an item is being added correctly to
     * the cart.
     */
    @Test
    public void testAddItem() {
        // Test Case 1: Adding to an empty cart
        sCart.addItem(001, 2);
        sCart.addItem(002, 4);

        assertEquals(2, sCart.getCart().size(), "The size of the cart was not updated.");
        assertAll(()-> assertEquals(2, sCart.getCart().get(001), "The quantity for item 001 is incorrect."),
                  ()-> assertEquals(4, sCart.getCart().get(002), "The quantity for item 002 is incorrect."));

        // Test Case 2: Adding to a non-empty cart (Adding an item that already exists/ Increasing quantity)
        sCart.addItem(001, 2);
        sCart.addItem(002, 4);

        assertEquals(2, sCart.getCart().size(), "The size of the cart was not updated.");
        assertAll(()-> assertEquals(4, sCart.getCart().get(001), "The quantity for item 001 is incorrect."),
                ()-> assertEquals(8, sCart.getCart().get(002), "The quantity for item 002 is incorrect."));


        // Test Case 3: Adding to a non-empty cart (Adding a new item)
        sCart.addItem(003, 6);
        sCart.addItem(004, 8);
        assertEquals(4, sCart.getCart().size(), "The size of the cart was not updated.");
        assertAll(()-> assertEquals(6, sCart.getCart().get(003), "The quantity for item 003 is incorrect."),
                ()-> assertEquals(8, sCart.getCart().get(004), "The quantity for item 004 is incorrect."));
    }

    /**
     * This method tests the removeItem() method if an item is removed correctly
     * from the cart.
     */
    @Test
    public void testRemoveItem() {
        // Test Case 1: Removing from an empty cart
        assertFalse(sCart.removeItem(001, 1));

        // Test Case 2: Removing from a non-empty cart (Decreasing quantity)
        sCart.getCart().put(001, 2);
        sCart.getCart().put(002, 4);
        sCart.getCart().put(003, 6);

        assertTrue(sCart.removeItem(001, 1), "The method did not successfully removed the item.");
        assertTrue(sCart.removeItem(002, 1), "The method did not successfully removed the item.");
        assertTrue(sCart.removeItem(003, 2), "The method did not successfully removed the item.");

        assertEquals(1, sCart.getCart().get(001), "The remaining quantity for item 001 is incorrect.");
        assertEquals(3, sCart.getCart().get(002), "The remaining quantity for item 002 is incorrect.");
        assertEquals(4, sCart.getCart().get(003), "The remaining quantity for item 003 is incorrect.");

        // Test Case 3: Removing from a non-empty cart (Actually removing the item)
        assertTrue(sCart.removeItem(003, 4), "The method did not successfully removed the item.");
        assertEquals(null, sCart.getCart().get(003), "The remaining quantity for item 003 must be null.");
        assertEquals(2, sCart.getCart().size(), "The number of unique items in the cart was not updated after removal.");

        // Test Case 4: Requesting more quantity than what is available
        assertFalse(sCart.removeItem(001, 2), "The method removed the item when it should not remove it.");
        assertEquals(1, sCart.getCart().get(001), "The removedItem must not remove an item because requested quantity is greater than available stock.");
        assertEquals(2, sCart.getCart().size(), "The number of unique items in the cart must be the same as removal is not executed.");
    }

    /**
     * This method tests if the contents of the cart is properly cleared.
     */
    @Test
    public void clearCart(){
        sCart.getCart().put(001, 2);
        sCart.getCart().put(002, 4);

        sCart.clearCart();
        assertEquals(0, sCart.getCart().size());
    }
}
