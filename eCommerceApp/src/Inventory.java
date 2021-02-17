/*
    Inventory.java
    SYSC 2004 Project M1

    Bren-Gelyn Padlan
    101148482
 */

import java.util.ArrayList;

public class Inventory {
    private ArrayList<String> bookType;     //is it fiction or nonfiction?
    private ArrayList<Integer> numStock;
    private ArrayList<Product> bookList;

    /**
     *  Constructor for inventory.
     */
    public Inventory() {
        bookList = new ArrayList<>();
        numStock = new ArrayList<>();
        bookType = new ArrayList<>();

        //Below are for testing purposes
        Product book1 = new Product("book1", 001, 1.99);
        Product book2 = new Product("book2", 002, 2.45);
        Product book3 = new Product("book3", 003, 1.50);

        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        numStock.add(50);
        numStock.add(50);
        numStock.add(50);

        bookType.add("fiction");
        bookType.add("fiction");
        bookType.add("nonfiction");
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
    public void addStock(Product book, int quantity, String type) {
        if (this.bookList.contains(book)) {
            int i = this.bookList.indexOf(book);
            int stock = this.numStock.get(i);

            this.numStock.set(i, stock + quantity);

        } else {
            this.bookList.add(book);
            this.numStock.add(quantity);
            this.bookType.add(type);
        }
    }


    /**
     *  Decreases the number of stock of a specific book.
     */
    public boolean removeStock(int id, int quantity) {
        Product target = getProductInfo(id);
        int stock, i;
        if (this.bookList.contains(target)) {
            i = getIndex(id);
            stock = this.numStock.get(i);

            //checks if quantity value is invalid or if not enough stocks
            if (quantity <= 0 || stock < quantity) {
                return false;
            }

            this.numStock.set(i, stock - quantity);
            return true;
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