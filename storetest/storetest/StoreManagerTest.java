/*
    storetest.StoreManagerTest.java
    Bren-Gelyn Padlan
    101148482
 */
package storetest;

import org.junit.jupiter.api.*;
import store.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a test class for StoreManager class. It tests all of
 * StoreManager's methods.
 *
 * @author Bren-Gelyn Padlan
 * @version 1.0
 */
public class StoreManagerTest {
    /**
     * A test instance for StoreManager object.
     */
    private static StoreManager sm;

    /**
     * This method sets up the initial stage of the test by initializing the StoreManager sm.
     */
    @BeforeAll
    public static void init() {
        sm = new StoreManager();
    }

    /**
     * This method tests getStock().
     */
    @Test
    public void testGetStock() {
        // The inventory already contains these products.
        Product p1 = new Product("1984", 001, 1.99);
        Product p2 = new Product("The Great Gatsby", 002, 2.45);
        Product p3 = new Product("Silent Spring", 003, 1.86);
        Product p4 = new Product("A Room of One's Own", 004, 3.75);
        Product p5 = new Product("Catcher in The Rye", 005, 2.89);

        assertAll(
                ()-> assertEquals(10, sm.getStock(p1), "The method getStock is returning incorrect stock number for p1."),
                ()-> assertEquals(20, sm.getStock(p2), "The method getStock is returning incorrect stock number for p2."),
                ()-> assertEquals(30, sm.getStock(p3), "The method getStock is returning incorrect stock number for p3."),
                ()-> assertEquals(40, sm.getStock(p4), "The method getStock is returning incorrect stock number for p4."),
                ()-> assertEquals(50, sm.getStock(p5), "The method getStock is returning incorrect stock number for p5.")
        );
    }

    /**
     * This method tests assignNewCartId() if cartId is incremented properly and if the
     * size of the list of shopping carts is increased.
     */
    @Test
    public void testAssignNewCartId() {
        assertEquals(0, sm.assignNewCartId(), "The cart number is incorrect.");
        assertEquals(1, sm.getShoppingCart().size(), "The shopping cart is not properly added.");

        assertEquals(1, sm.assignNewCartId(), "The cart number is incorrect.");
        assertEquals(2, sm.getShoppingCart().size(), "The shopping cart is not properly added.");

        assertEquals(2, sm.assignNewCartId(), "The cart number is incorrect.");
        assertEquals(3, sm.getShoppingCart().size(), "The shopping cart is not properly added.");
    }

    /**
     * This method tests checkout() using two test cases.
     */
    @Test
    public void testCheckout() {
        // The method checkout() only calculates the total of all the items in the cart.
        // It does not really care where the cart comes from that is why I am using add() method for ArrayList
        sm.getShoppingCart().add(new ShoppingCart()); // List of ShoppingCart now has a size of 1
        sm.getShoppingCart().add(new ShoppingCart()); // List of ShoppingCart now has a size of 2

        // Test Case 1: If cart contains items
        sm.getShoppingCart().get(0).getCart().put(001, 2);
        assertEquals(3.98, sm.checkout(0), "The total price for the cart 0 is incorrect.");
        sm.getShoppingCart().get(0).getCart().put(002, 3);
        assertEquals(11.33, sm.checkout(0), "The total price for the cart 0 is incorrect.");

        // Test Case 2: If cart contains no item
        // Cart 1 does not contain any items
        assertEquals(0.00, sm.checkout(1), "The total price for the cart 1 is incorrect.");

        // Test Case 3: If cart does not exists
        /* This cannot happen because the parameter of the checkout method, namely cartId, is connected to the
         size of the list of shopping carts. Everytime the cartId is incremented, a new shopping cart is added
         to the list.
         */
    }

    /**
     * This method tests addToCart() method using three test cases.
     */
    @Test
    public void testAddToCart() {
        sm.getShoppingCart().add(new ShoppingCart());

        // Test Case 1: Adding to an empty cart
        sm.addToCart(0, 001, 2);
        assertEquals(2, sm.getShoppingCart().get(0).getCart().get(001), "The method is not properly adding items to an empty cart.");
        assertEquals(8, sm.getInventory().getNumStock(001), "The method is not properly removing items from the inventory.");

        // Test Case 2: Adding to a non-empty cart
        sm.addToCart(0, 001, 2);
        assertEquals(4, sm.getShoppingCart().get(0).getCart().get(001), "The method is not properly adding items to a non-empty cart.");
        assertEquals(6, sm.getInventory().getNumStock(001), "The method is not properly removing items from the inventory.");

        // Test Case 3: Requesting more quantity than what is available
        sm.addToCart(0, 002, 21);
        assertEquals(20, sm.getShoppingCart().get(0).getCart().get(002), "The method is not properly adding items to the cart.");
        assertEquals(0, sm.getInventory().getNumStock(002), "The method is not properly removing items from the inventory.");
    }

    /**
     * This method tests removeFromCart() method using four test cases.
     */
    @Test
    public void testRemoveFromCart() {
        sm.getShoppingCart().add(new ShoppingCart());
        sm.getShoppingCart().add(new ShoppingCart());

        sm.getShoppingCart().get(0).getCart().put(001, 2);
        sm.getShoppingCart().get(0).getCart().put(002, 4);
        sm.getShoppingCart().get(0).getCart().put(003, 6);

        // Test Case 1: Removing from an empty cart
        // removeFromCart() should return false because cart 1 is empty
        assertFalse(sm.removeFromCart(1, 001, 2), "The method removed an item in cart 1 when the cart is empty.");
        assertEquals(null, sm.getShoppingCart().get(1).getCart().get(001), "The quantity of item 001 in cart 1 must be null.");
        assertEquals(0, sm.getShoppingCart().get(1).getCart().size(), "The expected size of the cart must still be 0.");
        assertEquals(10, sm.getInventory().getNumStock(001), "The method is not properly removing items from the inventory.");

        // Test Case 2: Removing from a non-empty cart (Decreasing quantity)
        assertTrue(sm.removeFromCart(0, 002, 3), "The method did not successfully removed the specified item.");
        assertEquals(1, sm.getShoppingCart().get(0).getCart().get(002), "The quantity of item 002 in cart 0 is incorrect.");
        assertEquals(23, sm.getInventory().getNumStock(002), "The method is not properly removing items from the inventory.");

        // Test Case 3: Removing from a non-empty cart (Actually removing the item)
        assertTrue(sm.removeFromCart(0, 003, 6), "The method did not successfully removed the specified item.");
        assertEquals(null, sm.getShoppingCart().get(0).getCart().get(003), "The quantity of item 003 in cart 0 must be null.");
        assertEquals(36, sm.getInventory().getNumStock(003), "The method is not properly removing items from the inventory.");

        // Test Case 4: Requesting more quantity than what is available
        assertTrue(sm.removeFromCart(0, 001, 3), "The method did not successfully removed the specified item.");
        assertEquals(null, sm.getShoppingCart().get(0).getCart().get(001), "The quantity of item 001 in cart 0 must be null.");
        assertEquals(12, sm.getInventory().getNumStock(001), "The method is not properly removing items from the inventory.");
    }

    /**
     * This method tests the resetCart() method if it actually clears/resets the chosen cart.
     */
    @Test
    public void testResetCart() {
        sm.getShoppingCart().add(new ShoppingCart());
        sm.getShoppingCart().add(new ShoppingCart());

        sm.addToCart(0, 001, 2);
        sm.addToCart(0, 002, 4);
        sm.addToCart(0, 003, 6);

        sm.resetCart(0);
        assertEquals(0, sm.getShoppingCart().get(0).getCart().size(), "The cart was not cleared.");

        sm.resetCart(1);
        assertEquals(0, sm.getShoppingCart().get(1).getCart().size(), "The cart size must still be 0.");
    }

    /**
     * This method is executed after each of the test method. It was added to reset
     * the StoreManager after each tests since it it easier to handle the tests if the list
     * of ShoppingCarts is initially empty and the Inventory is not modified.
     */
    @AfterEach
    public void afterEachTest() {
        // Reset StoreManager after every test method.
        sm = new StoreManager();
    }

}
