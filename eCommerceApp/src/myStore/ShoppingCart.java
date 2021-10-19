/*
    myStore.ShoppingCart.java
    Bren-Gelyn Padlan
    101148482
 */
package myStore;

import java.util.HashMap;

/**
 * This class represents the user's cart containing the
 * product id and quantity of each items.
 *
 * @author Bren-Gelyn Padlan
 * @version 2.0
 */
public class ShoppingCart implements ProductStockContainer {
    /**
     * User's cart
     */
    private HashMap<Product, Integer> cart;
    /**
     * Unique cart id of a user's cart
     */
    private final int cartId;

    /**
     * This is the constructor of store.ShoppingCart object that initializes
     * the user's cart into an empty HashMap.
     */
    public ShoppingCart(int cartId) {
        this.cart = new HashMap<>();
        this.cartId = cartId;
    }

    /**
     * This method provides the cart of the current user
     * @return HashMap, the user's cart
     */
    public HashMap<Product,Integer> getCart() {
        return this.cart;
    }

    /**
     * This method provides the number of unique products in the shopping cart.
     * @return int, number of unique products in the shopping cart
     */
    @Override
    public int getNumOfProducts() {
        return this.cart.size();
    }

    /**
     * This method provides the quantity of each product in the shopping cart.
     * @param product Product, the product itself
     * @return int, quantity of each product in the shopping cart
     */
    @Override
    public int getProductQuantity(Product product) {
        return this.cart.getOrDefault(product, -1);
    }

    /**
     * This method adds an item with the specified quantity to the user's cart.
     * If the item does not exist in the cart, the new item will be added with
     * the specified quantity. If the item already exists in the cart, its
     * current quantity will be increased.
     * @param product Product, the product itself
     * @param quantity int, the requested number of items to add
     */
    @Override
    public void addProductQuantity(Product product, int quantity) {
        int currentQuantity = this.cart.getOrDefault(product, 0);
        this.cart.put(product, currentQuantity + quantity);
    }

    /**
     * This method removes specified quantity of items from the user's cart.
     * If the specified quantity is less than the current quantity of the
     * selected item, its current quantity will be decreased.
     * If the specified quantity is equal to the current quantity of the
     * selected item, the item will be removed from the cart.
     * @param product Product, the product itself
     * @param quantity int, the requested number of items to remove
     * @return a boolean, True if the removal is successful or not, False otherwise
     */
    @Override
    public boolean removeProductQuantity(Product product, int quantity) {
        int currentQuantity = this.cart.getOrDefault(product, 0);
        if (quantity < currentQuantity && currentQuantity > 0) {
            this.cart.put(product, currentQuantity - quantity);
            return true;
        }
        if (quantity == currentQuantity) {
            this.cart.remove(product);
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
