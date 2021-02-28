/*
    Product.java
    SYSC 2004 Project M1

    Bren-Gelyn Padlan
    101148482
 */
public class Product {
    private final String name;
    private final int id;
    private final double price;

    public Product(String name, int id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getName() { return this.name; }

    public int getId() {
        return this.id;
    }

    public double getPrice() {
        return this.price;
    }

}
