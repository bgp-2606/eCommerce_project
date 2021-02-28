/*
    Inventory.java
    SYSC 2004 Project M1

    Bren-Gelyn Padlan
    101148482
 */

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Product> bookList;
    private ArrayList<Integer> numStock;
    private ArrayList<String> bookType;     //is it fiction or nonfiction?

    /**
     *  Constructor for inventory.
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

        this.bookList.add(book1);
        this.bookList.add(book2);
        this.bookList.add(book3);
        this.bookList.add(book4);

        this.numStock.add(37);
        this.numStock.add(14);
        this.numStock.add(1);
        this.numStock.add(26);

        this.bookType.add("fiction");
        this.bookType.add("fiction");
        this.bookType.add("nonfiction");
        this.bookType.add("nonfiction");
    }


    /**
     *  Gets the number of stocks for a specific book.
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
     * Gets the type of a specific book given an id.
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
     * Gets the list of books in the inventory.
     */
    public ArrayList<Product> getBookList() {
        return this.bookList;
    }

    /**
     *  Increases the stock of a specific book, takes the book itself and quantity
     *  If book does not exist, create new book.
     */
    public void addStock(Product book, Integer quantity, String type) {
        for (Product p : this.bookList) {
            if (book.getId() == p.getId()) {
                int i = this.bookList.indexOf(book);
                int stock = this.numStock.get(i);

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
     *  Decreases the number of stock of a specific book.
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
     * Gets the book given its product id.
     */
    public Product getProductInfo(int productId) {
        Product targetBook = null;

        for(Product book : this.bookList) {
            if (book.getId() == productId) {
                targetBook = book;
            }
        }
        return targetBook;
    }


    /**
     * Gets the index of the book given its id.
     */
    private int getIndex(int id){
        Product targetBook = getProductInfo(id);
        return this.bookList.indexOf(targetBook);
    }

}