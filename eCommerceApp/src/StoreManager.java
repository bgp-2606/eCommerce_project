/*
    StoreManager.java
    SYSC 2004 Project M1

    Bren-Gelyn Padlan
    101148482
 */

public class StoreManager {
    private Inventory inventory;

    /**
     * Constructor for StoreManager initializing an inventory.
     */
    public StoreManager() {
        this.inventory = new Inventory();
    }

    /**
     * Gets the number of stock of a specific book.
     */
    public int getStock(Product product) {
        return this.inventory.getNumStock(product.getId());
    }

    /**
     * Calculates the total price in the user's cart.
     */
    public double processTransaction(int[][] items) {
        //Assuming that the 2D array is arranged like this:
        // [[prodId1, quan1], [prodId2, quan2], [prodId3, quan3]]

        double totalPrice = 0.0;
        boolean success = false;
        int id, quantity;
        Product target;

        for (int i = 0; i < items.length; i++) {

            id = items[i][0];
            quantity = items[i][1];
            target = this.inventory.getProductInfo(id);

            if (this.inventory.getBookList().contains(target)) {

                // if the stock of target book is > quantity
                if (this.inventory.getNumStock(id) > quantity) {
                    success = this.inventory.removeStock(id, quantity);
                    totalPrice += target.getPrice() * quantity;

                } else {
                    return -1.0;
                }
            }
        }
        return totalPrice;
    }

}
