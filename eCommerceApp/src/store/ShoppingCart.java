/*
    store.ShoppingCart.java
    Bren-Gelyn Padlan
    101148482
 */
package store;

import java.util.HashMap;

/**
 * This class represents the user's cart containing the
 * product id and quantity of each items.
 *
 * @author Bren-Gelyn Padlan
 * @version 1.0
 */
public class ShoppingCart {
    /**
     * User's cart
     */
    private HashMap<Integer, Integer> cart;

    /**
     * This is the constructor of store.ShoppingCart object that initializes
     * the user's cart into an empty HashMap.
     */
    public ShoppingCart() {
        this.cart = new HashMap<Integer, Integer>();
    }

    /**
     * This method provides the cart of the current user
     * @return a HashMap of Integer keys and Integer values that represents the user's cart
     */
    public HashMap<Integer,Integer> getCart() {
        return this.cart;
    }


    /**
     * This method adds an item with the specified quantity to the user's cart.
     * If the item does not exist in the cart, the new item will be added with
     * the specified quantity. If the item already exists in the cart, its
     * current quantity will be increased.
     * @param itemId an integer that represents the id of a book
     * @param quantity an integer that represents the requested number of items to add
     */
    public void addItem(Integer itemId, Integer quantity) {
        int currentQuantity = this.cart.getOrDefault(itemId, 0);
        this.cart.put(itemId, currentQuantity + quantity);
    }

    /**
     * This method removes specified quantity of items from the user's cart.
     * If the specified quantity is less than the current quantity of the
     * selected item, its current quantity will be decreased.
     * If the specified quantity is equal to the current quantity of the
     * selected item, the item will be removed from the cart.
     * @param itemId an integer that represents the id of a book
     * @param quantity an integer that represents the requested number of items to remove
     * @return a boolean, True if the removal is successful or not, False otherwise
     */
    public boolean removeItem(Integer itemId, Integer quantity) {
        int currentQuantity = this.cart.getOrDefault(itemId, 0);
        if (quantity < currentQuantity && currentQuantity > 0) {
            this.cart.put(itemId, currentQuantity - quantity);
            return true;
        }
        if (quantity == currentQuantity) {
            this.cart.remove(itemId);
            return true;
        }
        return false;
    }

    /**
     * This method resets the contents of the cart.
     */
    public void clearCart() {
        this.cart.clear();
    }

}
