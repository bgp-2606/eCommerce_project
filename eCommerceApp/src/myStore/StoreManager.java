/*
    myStore.StoreManager.java
    Bren-Gelyn Padlan
    101148482
 */
package myStore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains the main components of the store namely
 * the inventory and the shopping cart, and their interaction with each other.
 * It also manages the checkout.
 *
 * @author Bren-Gelyn Padlan
 * @version 5.0
 */
public class StoreManager {
    /**
     * Inventory of the store
     */
    private Inventory inventory;
    /**
     * Shopping cart of a user
     */
    private ArrayList<ShoppingCart> shoppingCarts ;
    /**
     * Cart id of each user's cart
     */
    private int cartIdCounter;

    /**
     * This is the constructor for store.StoreManager initializing an inventory.
     */
    public StoreManager() {
        this.inventory = new Inventory();
        this.shoppingCarts = new ArrayList<>();
        this.cartIdCounter = 0;
    }

    /**
     * This method provides the number of stock of a specific book.
     */
    public int getProductStock(Product product) {
        return this.inventory.getProductQuantity(product);
    }

    /**
     * This method increments the cart id by 1
     * @return an integer that represents the incremented cart id
     */
    public int assignNewCartId() {
        int cartId = cartIdCounter++;
        this.shoppingCarts.add(new ShoppingCart(cartId));
        return cartId;
    }

    /**
     * This method adds an item to the user's cart. At the same time, it also
     * decreases the quantity of that item in the inventory.
     * @param cartId an integer that represents the id of the current user's cart
     * @param product a Product that represents the chosen item
     * @param quantity an integer that represents the requested number of items to add to cart
     */
    public void addToCart(int cartId, Product product, int quantity) {
        int availableStock = this.inventory.getProductQuantity(product);
        // If requested quantity is greater than what is available
        if (quantity > availableStock) {
            this.shoppingCarts.get(cartId).addProductQuantity(product, availableStock);
            this.inventory.removeProductQuantity(product, availableStock);
        }
        // If requested quantity is less than or equal to the available stock
        else {
            this.shoppingCarts.get(cartId).addProductQuantity(product, quantity);
            this.inventory.removeProductQuantity(product, quantity);
        }
    }

    /**
     * This method removes an item to the user's cart. At the same time, it also
     * returns that item to the inventory stock.
     * @param cartId an integer that represents the id of the current user's cart
     * @param product a Product that represents the chosen item
     * @param quantity an integer that represents the requested number of items to remove to cart
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeFromCart(int cartId, Product product, int quantity) {
        ShoppingCart sc = this.shoppingCarts.get(cartId);
        // If cart is not empty
        if (sc.getNumOfProducts() != 0) {
            int itemQuantity = sc.getProductQuantity(product); // Quantity of a certain item in the cart

            // If quantity requested is greater than the number of that item in cart
            if (quantity > itemQuantity) {
                this.shoppingCarts.get(cartId).removeProductQuantity(product, itemQuantity);
                this.inventory.addProductQuantity(product, itemQuantity);
            }
            // If quantity is less than or equal to the number of that item in cart
            else {
                this.shoppingCarts.get(cartId).removeProductQuantity(product, quantity);
                this.inventory.addProductQuantity(product, quantity);
            }
            return true;
        }
        return false;
    }

    /**
     * This method calculates the total price in the user's cart.
     * @param cartId an integer that represents the id of the current user's cart
     */
    public double checkout(int cartId) {
        double total = 0.00;
        for (Product pC : getCartContents(cartId).keySet()) {
            for (Product pI : getAvailableProducts()) {
                if (pC.getId() == pI.getId()) {
                    total += pI.getPrice() * getCartContents(cartId).get(pC);
                }
            }
        }
        return total;
    }

    /**
     * This method returns the ArrayList of Products in the inventory.
     * @return an ArrayList of Products in the inventory
     */
    public ArrayList<Product> getAvailableProducts() {
        return this.inventory.getProductList();
    }

    /**
     * This method provides the cart of the user.
     * @return a cart that represents the shopping cart of the user
     */
    public HashMap<Product, Integer> getCartContents(int cartId) {
        return this.shoppingCarts.get(cartId).getCart();
    }

    /**
     * This method resets the cart content.
     * @param cartId an integer that represents the id of the current user's cart
     */
    public void resetCart(int cartId) {
        this.shoppingCarts.get(cartId).clearCart();
    }
}
