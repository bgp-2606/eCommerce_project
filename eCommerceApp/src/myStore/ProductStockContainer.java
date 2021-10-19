/*
    myStore.ProductStockContainer.java
    Bren-Gelyn Padlan
    101148482
 */
package myStore;

/**
 * This is an interface for Inventory and ShoppingCart.
 *
 * @author Bren-Gelyn Padlan
 * @version 1.0
 */
public interface ProductStockContainer {
    /**
     * This method should provide the quantity of the product in a specific container.
     * @param product Product, the product itself
     * @return int, quantity
     */
    int getProductQuantity(Product product);

    /**
     * This method should increment the quantity of a product.
     * @param product Product, the product itself
     * @param quantity int, the requested quantity to add
     */
    void addProductQuantity(Product product, int quantity);

    /**
     * This method should decrement the quantity of a product.
     * @param product Product, the product itself
     * @param quantity int, the requested quantity to remove
     * @return a boolean true if product quantity is decremented, false otherwise
     */
    boolean removeProductQuantity(Product product, int quantity);

    /**
     * This method should provide the number of unique products in a container.
     * @return int, number of unique products
     */
    int getNumOfProducts();
}
