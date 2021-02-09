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
        return numStock.get(getIndex(id));
    }

    /**
     * Gets the book type given its id.
     */
    public String getType(int id){
        return bookType.get(getIndex(id));
    }

    /**
     * Gets the list of books in the inventory.
     */
    public ArrayList<Product> getBookList() {
        return bookList;
    }

    /**
     *  Increases the stock of a specific book, takes the book itself and quantity
     *  If book does not exist, create new book.
     */
    public void addStock(Product book, int quantity, String type) {
        if (bookList.contains(book)) {
            int i = bookList.indexOf(book);
            int stock = numStock.get(i);

            numStock.set(i, stock + quantity);

        } else {
            bookList.add(book);
            numStock.add(quantity);
            bookType.add(type);
        }
    }


    /**
     *  Decreases the number of stock of a specific book.
     */
    public void removeStock(Product book, int quantity) {
        int i = bookList.indexOf(book);
        int stock = numStock.get(i);

        //checks if quantity value is invalid
        if (quantity <= 0) {
            System.out.println("Please enter a valid quantity.");

        } else {

            if (stock < quantity) {
                System.out.println("There is not enough stocks available.");

            } else {
                numStock.set(i, stock - quantity);
            }
        }
    }

    /**
     * Gets the book given its product id.
     */
    public Product getProductInfo(int productId) {
        Product targetBook = null;

        for(Product book : bookList) {
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
        return bookList.indexOf(targetBook);
    }

}
