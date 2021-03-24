/*
    store.StoreManager.java
    Bren-Gelyn Padlan
    101148482
 */
package store;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains the main components of the store namely
 * the inventory and the shopping cart, and their interaction with each other.
 * It also manages the checkout.
 *
 * @author Bren-Gelyn Padlan
 * @version 4.0
 */
public class StoreManager {
    /**
     * store.Inventory of the store
     */
    private Inventory inventory;
    /**
     * Shopping cart of a user
     */
    private ArrayList<ShoppingCart> shoppingCarts ;
    /**
     * Cart id of each user's cart
     */
    private static int cartId = 0;

    /**
     * This is the constructor for store.StoreManager initializing an inventory.
     */
    public StoreManager() {
        this.inventory = new Inventory();
        this.shoppingCarts = new ArrayList<ShoppingCart>();
    }

    /**
     * This method provides the inventory of the store
     * @return an store.Inventory object that represents the inventory of the store
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     *
     * @return
     */
    public ArrayList<Product> getBooks() {
        return this.inventory.getBookList();
    }

    /**
     *
     * @param id
     * @return
     */
    public Product toProduct(int id) {
        return this.inventory.getProductInfo(id);
    }

    /**
     * This method provides the shopping cart of the user
     * @return a store.ShoppingCart object that represents the shopping cart of the user
     */
    public HashMap<Integer, Integer> getCart(int cartId) {
        return this.shoppingCarts.get(cartId).getCart();
    }

    /**
     * This method provides the number of stock of a specific book.
     */
    public int getStock(Product product) {
        return this.inventory.getNumStock(product.getId());
    }

    /**
     * This method increments the cart id by 1
     * @return an integer that represents the incremented cart id
     */
    public int assignNewCartId() {
        this.shoppingCarts.add(new ShoppingCart());
        return cartId++;
    }

    /**
     * This method resets the cart content.
     * @param cartId an integer that represents the id of the current user's cart
     */
    public void resetCart(int cartId) {
        this.shoppingCarts.get(cartId).clearCart();
    }

    /**
     * This method calculates the total price in the user's cart.
     * @param cartId an integer that represents the id of the current user's cart
     */
    public double checkout(int cartId) {
        double total = 0.00;
        for (Integer productId : getCart(cartId).keySet()) {
            for (Product p : getBooks()) {
                if (productId == p.getId()) {
                    total += p.getPrice() * getCart(cartId).get(productId);
                }
            }
        }
        return total;
    }

    /**
     * This method adds an item to the user's cart. At the same time, it also
     * decreases the quantity of that item in the inventory.
     * @param cartId an integer that represents the id of the current user's cart
     * @param productId an integer that represents the id of the chosen item
     * @param quantity an integer that represents the requested number of items to add to cart
     */
    public void addToCart(int cartId, int productId, int quantity) {
        int availableStock = this.inventory.getNumStock(productId);
        // If requested quantity is greater than what is available
        if (quantity > availableStock) {
            this.shoppingCarts.get(cartId).addItem(productId, availableStock);
            this.inventory.removeStock(productId, availableStock);
        }
        // If requested quantity is less than or equal to the available stock
        else {
            this.shoppingCarts.get(cartId).addItem(productId, quantity);
            this.inventory.removeStock(productId, quantity);
        }
    }

    /**
     * This method removes an item to the user's cart. At the same time, it also
     * returns that item to the inventory stock.
     * @param cartId an integer that represents the id of the current user's cart
     * @param productId an integer that represents the id of the chosen item
     * @param quantity an integer that represents the requested number of items to remove to cart
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeFromCart(int cartId, int productId, int quantity) {
        Product item = this.inventory.getProductInfo(productId);
        // If item is in inventory
        if (item != null) {
            String type = this.inventory.getType(productId);
            HashMap<Integer, Integer> cart = this.shoppingCarts.get(cartId).getCart();

            // If cart exists and is not empty
            if (cart != null && cart.size() != 0) {
                int itemQuantity = cart.get(productId); // Quantity of a certain item in the cart

                // If quantity requested is greater than the number of that item in cart
                if (quantity > itemQuantity) {
                    this.shoppingCarts.get(cartId).removeItem(productId, itemQuantity);
                    this.inventory.addStock(item, itemQuantity, type);
                }
                // If quantity is less than or equal to the number of that item in cart
                else {
                    this.shoppingCarts.get(cartId).removeItem(productId, quantity);
                    this.inventory.addStock(item, quantity, type);
                }
                return true;
            }
        }
        return false;
    }

}
