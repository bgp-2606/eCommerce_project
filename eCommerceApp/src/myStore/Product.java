/*
    myStore.Product.java
    Bren-Gelyn Padlan
    101148482
 */
package myStore;

/**
 * This class represents a product with a name, id and price.
 *
 * @author Bren-Gelyn Padlan
 * @version 1.0
 */
public class Product {
    /**
     * store.Product's name
     */
    private final String name;
    /**
     * store.Product's id
     */
    private final int id;
    /**
     * store.Product's price
     */
    private final double price;

    /**
     * This is a constructor for store.Product object initializing the name,
     * id and price
     * @param name a String that represents the name of the product
     * @param id an integer that represents the id of the product
     * @param price a double that represents the price of the product
     */
    public Product(String name, int id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    /**
     * This method provides the name of the product
     * @return a String that represents the name of the product
     */
    public String getName() { return this.name; }

    /**
     * This method provides the id of the product
     * @return an integer that represents the id of the product
     */
    public int getId() {
        return this.id;
    }

    /**
     * This method provides the price of the product
     * @return a double that represents the price of the product
     */
    public double getPrice() {
        return this.price;
    }

}
