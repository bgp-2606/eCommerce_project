/*
    ShoppingCart.java
    SYSC 2004 Project M2

    Bren-Gelyn Padlan
    101148482
 */

import java.util.HashMap;

/**
 * This class represents the user's cart.
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
     * This is the constructor of ShoppingCart object that initializes
     * the user's cart into an empty HashMap.
     */
    public ShoppingCart() {
        this.cart = new HashMap<Integer, Integer>();
    }

    public HashMap<Integer,Integer> getCartH() {
        return this.cart;
    }

    /**
     * This method provides contents of the user's cart in a 2-D array.
     * @return a 2-D array of integers containing the product id and
     * product quantity
     */
    public int[][] getCartA() {
        int cartSize = this.cart.size();
        int[][] tempCart = new int[cartSize][cartSize];

        int i = 0;
        for (Integer id : this.cart.keySet()) {
            tempCart[i][0] = id;
            i++;
        }
        i = 0;
        for (Integer quantity : this.cart.values()) {
            tempCart[i][1] = quantity;
            i++;
        }

        return tempCart;
    }

    /**
     * This method adds an item with the specified quantity to the user's cart.
     * If the item does not exist in the cart, the new item will be added with
     * the specified quantity. If the item already exists in the cart, its
     * current quantity will be increased.
     * @param bookId
     * @param quantity
     */
    public void addItem(Integer bookId, Integer quantity) {
        int currentQuantity = this.cart.getOrDefault(bookId, 0);
        this.cart.put(bookId, currentQuantity + quantity);
    }

    /**
     * This method removes specified quantity of items from the user's cart.
     * If the specified quantity is less than the current quantity of the
     * selected item, its current quantity will be decreased.
     * If the specified quantity is equal to the current quantity of the
     * selected item, the item will be removed from the cart.
     * @param bookId
     * @param quantity
     * @return
     */
    public boolean removeItem(Integer bookId, Integer quantity) {
        int currentQuantity = this.cart.getOrDefault(bookId, 0);
        System.out.println("currQ = " + currentQuantity);
        if (currentQuantity > 0 && quantity < currentQuantity) {
            this.cart.put(bookId, currentQuantity - quantity);
            return true;
        }
        if (quantity == currentQuantity) {
            this.cart.remove(bookId);
            return true;
        }
        return false;
    }

}
