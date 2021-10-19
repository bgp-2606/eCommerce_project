/*
    myStore.Inventory.java
    Bren-Gelyn Padlan
    101148482
 */
package myStore;

import java.util.ArrayList;

/**
 * This class represents the inventory of the store containing
 * list of product, number of stocks and types of each product.
 *
 * @author Bren-Gelyn Padlan
 * @version 3.0
 */
public class Inventory implements ProductStockContainer{
    /**
     * ArrayList of Product containing all the books in the inventory
     */
    private ArrayList<Product> productList = new ArrayList<>();
    /**
     * ArrayList of Integer containing the number of stocks of each book in the inventory
     */
    private ArrayList<Integer> productQuantity = new ArrayList<>();


    /**
     *  This is the constructor for inventory that initializes all
     *  the attributes by their default values.
     */
    public Inventory() {
        initialize();
    }

    /**
     * This method provides the list of books in the inventory.
     * @return ArrayList of Product, the list of books in the inventory
     */
    public ArrayList<Product> getProductList() {
        return this.productList;
    }

    /**
     * This method provides the number of unique products in inventory.
     * @return int, number of unique products in inventory
     */
    @Override
    public int getNumOfProducts() {
        return this.productList.size();
    }

    /**
     * This method provides the number of stocks for a specific book.
     * @param product Product, the product itself
     * @return int, the number of stocks of the chosen book
     */
    @Override
    public int getProductQuantity(Product product) {
        int i = 0;
        for (Product p : this.productList) {
            if (p.getId() == product.getId()) {
                return this.productQuantity.get(i);
            }
            i++;
        }
        return -1;
    }

    /**
     * This method increases the stock of a specific book, takes the book itself and quantity
     * If book does not exist, create new book.
     * @param product Product, the product itself
     * @param quantity int, the requested quantity to add to the stock
     */
    @Override
    public void addProductQuantity(Product product, int quantity) {
        int stock;
        int i = 0;
        for (Product p : this.productList) {
            if (p.getId() == product.getId()) {
                stock = this.productQuantity.get(i);
                this.productQuantity.set(i, stock + quantity);
                break;
            }
            i++;
        }
        this.productList.add(product);
        this.productQuantity.add(quantity);
    }


    /**
     * This method decreases the number of stock of a specific book.
     * @param product Product, the product itself
     * @param quantity int, the requested quantity to remove
     * @return a boolean true if removal of stock is successful, false otherwise
     */
    @Override
    public boolean removeProductQuantity(Product product, int quantity) {
        int stock;
        int i = 0;
        for (Product p : this.productList) {
            if (p.getId() == product.getId()) {
                stock = this.productQuantity.get(i);

                //checks if quantity value is invalid or if not enough stocks
                if (quantity <= 0 || stock < quantity) {
                    this.productQuantity.set(i, 0);
                    return true;
                }
                this.productQuantity.set(i, stock - quantity);
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * This method is to initialize the inventory by default.
     */
    private void initialize() {
        //Below are for testing purposes
        this.addProductQuantity(new Product("1984",                 1, 1.99), 10);
        this.addProductQuantity(new Product("The Great Gatsby",     2, 2.45), 25);
        this.addProductQuantity(new Product("Silent Spring",        3, 1.86), 3);
        this.addProductQuantity(new Product("A Room of One's Own",  4, 3.75), 15);
        this.addProductQuantity(new Product("Catcher in The Rye",   5, 2.89), 11);
        this.addProductQuantity(new Product("The Code Breaker",     6, 2.50), 26);
        this.addProductQuantity(new Product("Crime and Punishment", 7, 2.75), 1);
        this.addProductQuantity(new Product("Moby Dick",            8, 2.50), 13);
        this.addProductQuantity(new Product("The Sixth Extinction", 9, 3.10), 10);
    }
}