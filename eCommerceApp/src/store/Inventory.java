/*
    store.Inventory.java
    Bren-Gelyn Padlan
    101148482
 */
package store;

import java.util.ArrayList;

/**
 * This class represents the inventory of the store containing
 * list of product, number of stocks and types of each product.
 *
 * @author Bren-Gelyn Padlan
 * @version 2.0
 */
public class Inventory {
    /**
     * An ArrayList of store.Product containing all the books in the inventory
     */
    private ArrayList<Product> bookList;
    /**
     * An ArrayList of Integer containing the number of stocks of each book in the inventory
     */
    private ArrayList<Integer> numStock;
    /**
     * An ArrayList of String containing the type of each book in the inventory
     */
    private ArrayList<String> bookType;     //is it fiction or nonfiction?

    /**
     *  This is the constructor for inventory that initializes all
     *  the attributes by their default values.
     */
    public Inventory() {
        this.bookList = new ArrayList<Product>();
        this.numStock = new ArrayList<Integer>();
        this.bookType = new ArrayList<String>();

        //Below are for testing purposes
        Product book1 = new Product("1984", 001, 1.99);
        Product book2 = new Product("The Great Gatsby", 002, 2.45);
        Product book3 = new Product("Silent Spring", 003, 1.86);
        Product book4 = new Product("A Room of One's Own", 004, 3.75);
        Product book5 = new Product("Catcher in The Rye", 005, 2.89);

        this.bookList.add(book1);
        this.bookList.add(book2);
        this.bookList.add(book3);
        this.bookList.add(book4);
        this.bookList.add(book5);

        this.numStock.add(10);
        this.numStock.add(20);
        this.numStock.add(30);
        this.numStock.add(40);
        this.numStock.add(50);

        this.bookType.add("fiction");
        this.bookType.add("fiction");
        this.bookType.add("nonfiction");
        this.bookType.add("nonfiction");
        this.bookType.add("fiction");
    }


    /**
     * This method provides the number of stocks for a specific book.
     * @param id an integer that represents the id of a book
     * @return an integer that represents the number of stocks of the chosen book
     */
    public int getNumStock(int id) {
        int i = getIndex(id);
        // if it does not exist
        if (i == -1) {
            return i;
        }

        //if it exists
        return this.numStock.get(i);
    }

    /**
     * This method provides the type of a specific book given an id.
     * @param id an integer that represents the id of the book
     * @return a String that represents the type of the book
     */
    public String getType(int id) {
        int i = getIndex(id);
        // if it does not exist
        if (i == -1) {
            return null;
        }

        //if it exists
        return this.bookType.get(i);
    }

    /**
     * This method provides the list of books in the inventory.
     * @return an ArrayList of store.Product representing the list of books in the inventory
     */
    public ArrayList<Product> getBookList() {
        return this.bookList;
    }

    /**
     * This method increases the stock of a specific book, takes the book itself and quantity
     * If book does not exist, create new book.
     * @param book a store.Product object thar represents a book
     * @param quantity an integer that represents the requested quantity to add to the stock
     * @param type a String representing the type of the book being added
     */
    public void addStock(Product book, int quantity, String type) {
        int stock, i;
        for (Product p : this.bookList) {
            if (book.getId() == p.getId()) {
                i = getIndex(p.getId());
                stock = this.numStock.get(i);

                this.numStock.set(i, stock + quantity);
            }
        }
        if (!this.bookList.contains(book)) {
            this.bookList.add(book);
            this.numStock.add(quantity);
            this.bookType.add(type);
        }
    }


    /**
     * This method decreases the number of stock of a specific book.
     * @param id an integer that represents the id of the book
     * @param quantity an integer that represents the requested quantity to remove
     * @return a boolean true if removal of stock is successful, false otherwise
     */
    public boolean removeStock(int id, int quantity) {
        int stock, i;

        for (Product p : this.bookList) {
            if (id == p.getId()) {
                i = getIndex(id);
                stock = this.numStock.get(i);

                //checks if quantity value is invalid or if not enough stocks
                if (quantity <= 0 || stock < quantity) {
                    this.numStock.set(i, 0);
                    return true;
                }
                this.numStock.set(i, stock - quantity);
                return true;
            }
        }
        return false;
    }

    /**
     * This method provides the book given its product id.
     * @param id an integer that represents the id of the book
     * @return a store.Product object of the given id
     */
    public Product getProductInfo(int id) {
        Product targetBook = null;

        for(Product book : this.bookList) {
            if (book.getId() == id) {
                targetBook = book;
                break;
            }
        }
        return targetBook;
    }


    /**
     * This method provides the index of the book given its id.
     * @param id an integer that represents the id of a book
     * @return the index of the product given its id
     */
    private int getIndex(int id){
        Product targetBook = getProductInfo(id);
        return this.bookList.indexOf(targetBook);
    }

}