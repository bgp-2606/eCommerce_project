/*
    StoreManager.java
    SYSC 2004 Project M1

    Bren-Gelyn Padlan
    101148482
 */

import java.util.HashMap;

public class StoreManager {
    private Inventory inventory;
    private ShoppingCart shoppingCart;
    private static int cartId = 0;       //Should I keep it private?

    /**
     * Constructor for StoreManager initializing an inventory.
     */
    public StoreManager() {
        this.inventory = new Inventory();
        this.shoppingCart = new ShoppingCart();
    }

    public int assignNewCartId() {
        return cartId++;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public ShoppingCart getShoppingCart() {
        return this.shoppingCart;
    }

    /**
     * Gets the number of stock of a specific book.
     */
    public int getStock(Product product) {
        return this.inventory.getNumStock(product.getId());
    }

    public int getCartId() {
        return cartId;
    }

    /**
     * Calculates the total price in the user's cart.
     */
    public double checkout() {
        HashMap<Integer, Integer> cart = shoppingCart.getCartH();
        double total = 0.00;
        for (Integer id : cart.keySet()) {
            for (Product p : this.inventory.getBookList()) {
                if (id == p.getId()) {
                    total += p.getPrice() * cart.get(id);
                    System.out.printf(
                            "%-8d | %-30s | %-10.2f | %-10.2f \n",
                            cart.get(id), p.getName(), p.getPrice(), p.getPrice() * cart.get(id)
                    );
                }
            }
        }
        return total;
    }

    public void addToCart(int id, int quantity) {
        int availableStock = this.inventory.getNumStock(id);
        if (quantity > availableStock) {
            this.shoppingCart.addItem(id,availableStock);
            this.inventory.removeStock(id, availableStock);
        } else {
            this.shoppingCart.addItem(id, quantity);
            this.inventory.removeStock(id, quantity);
        }
    }

    public boolean removeFromCart(int id, int quantity) {
        Product item = this.inventory.getProductInfo(id);
        String type = this.inventory.getType(id);
        if (item != null) {
            boolean x = this.shoppingCart.removeItem(id, quantity);
            System.out.println(x);
            this.inventory.addStock(item, quantity, type);
            return true;
        }
        return false;
    }

}
