/*
    StoreView.java
    SYSC 2004 Project M2

    Bren-Gelyn Padlan
    101148482
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * This class has the store view that a user can interact with
 * while shopping.
 *
 * @author Bren-Gelyn Padlan
 * @version 1.0
 */
public class StoreView {
    private StoreManager sm;
    private int cartId;

    public StoreView () {
        this(new StoreManager(),new StoreManager().getCartId());
    }

    public StoreView(StoreManager sm, int cartId) {
        this.sm = sm;
        this.cartId = cartId;
    }

    public void browse() {
        System.out.println(
                "|--------------------------THE BOOK STORE--------------------------| \n" +
                "\\-----------------------------BROWSE-------------------------------/"
        );
        System.out.println("Type 'help' for a list of commands.\n");

        System.out.printf(
                "%-8s | %-30s | %-10s | \n", "Stock", "Product Name", "Unit Price"
        );

        Inventory inventory = this.sm.getInventory();
        // Loop through the bookList and print stock, product name, and unit price
        for (Product p : inventory.getBookList()) {
            System.out.printf(
                    "%-8d | %-30s | %-10.2f | \n",
                    inventory.getNumStock(p.getId()), p.getName(), p.getPrice()
            );
        }
        System.out.println();
    }

    public void viewcart() {
        System.out.println(
                "|--------------------------THE BOOK STORE--------------------------| \n" +
                "\\------------------------------CART--------------------------------/"
        );
        System.out.println("Type 'help' for a list of commands.\n");

        System.out.printf(
                "%-8s | %-30s | %-10s | \n", "Quantity", "Product Name", "Unit Price"
        );

        Inventory inventory = this.sm.getInventory();
        HashMap<Integer, Integer> cart = this.sm.getShoppingCart().getCartH();

        for (Integer id : cart.keySet()) {
            for (Product p : inventory.getBookList()) {
                if (id == p.getId()) {
                    System.out.printf(
                            "%-8d | %-30s | %-10.2f | \n",
                            cart.get(id), p.getName(), p.getPrice()
                    );

                }
            }
        }
        System.out.println("________________________________________________________");
        System.out.println();
    }

    public void addtocart() {
        System.out.println(
                "|--------------------------THE BOOK STORE--------------------------| \n" +
                "\\-------------------------------ADD--------------------------------/"
        );
        System.out.println("Type 'help' for a list of commands.\n");

        System.out.printf(
                "%-8s | %-30s | %-10s | %-8s |\n", "Stock", "Product Name", "Unit Price", "Option"
        );

        Inventory inventory = this.sm.getInventory();
        ArrayList<Product> bookList = inventory.getBookList();

        //Option, I call it index because it is actually the index of the product in the book list.
        int index = 0;
        for (Product p : bookList) {
            System.out.printf(
                    "%-8d | %-30s | %-10.2f | %-8s | \n",
                    inventory.getNumStock(p.getId()), p.getName(), p.getPrice(), index
            );
            index++;
        }

        int option, quantity;
        String input = "y";
        Scanner sc = new Scanner(System.in);

        while (input.equalsIgnoreCase("y")) {

            System.out.print("\nENTER OPTION NUMBER: >>> ");
            option = sc.nextInt();
            // If user input for option is invalid
            if (option > bookList.size()-1) {
                System.out.println("Option invalid. Please enter an option from the list above.");
                continue;
            }

            System.out.print("ENTER AMOUNT TO ADD: >>> ");
            quantity = sc.nextInt();
            // If user input for quantity is invalid
            if (quantity <= 0) {
                System.out.println("Please enter a valid quantity.");
                continue;
            }

            // Get the product id of the chosen item
            int itemId = bookList.get(option).getId();
            // Actually add the chosen item in the cart
            sm.addToCart(itemId, quantity);

            //Prompt user if s/he wants to add more items to cart
            System.out.print("ANYTHING MORE TO ADD? (y/n): >>> ");
            input = sc.next();
        }
        System.out.println();
    }

    public void removefromcart() {
        System.out.println(
                "|--------------------------THE BOOK STORE--------------------------| \n" +
                "\\------------------------------REMOVE------------------------------/"
        );
        System.out.println("Type 'help' for a list of commands.\n");

        System.out.printf(
                "%-8s | %-30s | %-10s | %-8s | \n", "Quantity", "Product Name", "Unit Price", "Option"
        );

        Inventory inventory = this.sm.getInventory();
        ArrayList<Product> bookList = inventory.getBookList();
        HashMap<Integer, Integer> cart = this.sm.getShoppingCart().getCartH();

        int index = 0;
        for (Integer id : cart.keySet()) {
            for (Product p : bookList) {
                if (id == p.getId()) {
                    System.out.printf(
                            "%-8d | %-30s | %-10.2f | %-8s | \n",
                            cart.get(id), p.getName(), p.getPrice(), index
                    );
                }
            }
            index++;
        }

        // If cart is empty, exit this subroutine.
        if (cart.isEmpty()) {
            System.out.println("Your cart is currently empty. To add items, enter 'addtocart' command.\n");
            return;
        }

        int option, quantity;
        String input = "y";
        Scanner sc = new Scanner(System.in);

        while (input.equalsIgnoreCase("y")) {

            System.out.print("\nENTER THE OPTION NUMBER: >>> ");
            option = sc.nextInt();
            // If user input for option is invalid
            if (option >= cart.size()) {
                System.out.println("Option invalid. Please enter an option from the list above.");
                continue;
            }

            System.out.print("ENTER AMOUNT TO REMOVE: >>> ");
            quantity = sc.nextInt();
            // If user input for quantity is invalid
            if (quantity <= 0) {
                System.out.println("Please enter a valid quantity.");
                continue;
            }

            int itemId = 0;
            int j = 0;
            // Find the product id of the chosen item
            for (Integer id : cart.keySet()) {
                if (j == option) {
                    itemId = id;
                    break;
                }
                j++;
            }

            // Actually remove the item from cart
            boolean remove = sm.removeFromCart(itemId, quantity);
            System.out.println(remove);

            //Prompt user if s/he wants keep removing items from cart
            System.out.print("ANYTHING MORE TO REMOVE? (y/n): >>> ");
            input = sc.next();
        }
        System.out.println();
    }

    /**
     * Put the user in the checkout interface where the summary
     * and total price of the items in the cart are displayed.
     */
    public void checkout() {
        Scanner sc = new Scanner(System.in);
        double totalPrice;
        System.out.println(
                "|--------------------------THE BOOK STORE--------------------------| \n" +
                        "\\------------------------------CHECKOUT----------------------------/"
        );

        System.out.printf(
                "%-8s | %-30s | %-10s | %-10s \n", "Quantity", "Product Name", "Unit Price", "Price"
        );

        // Call checkout method in StoreManager
        totalPrice = this.sm.checkout();

        System.out.println("__________________________________________________________________");
        System.out.printf("%60s%.2f\n", "Total: $", totalPrice);

        System.out.println("Thank you for shopping.\n");
    }

    /**
     * Prints out the list of valid commands the user can use to
     * shop in the store.
     */
    public void help() {
        System.out.println(
                "List of Commands: \n"
                + "\tbrowse - displays list of products\n"
                + "\tviewcart - displays cart summary\n"
                + "\taddtocart - go to the interface where you can add items to cart\n"
                + "\tremovefromcart - go to the checkout interface with options to remove items\n"
                + "\tcheckout - when you want to checkout\n"
                + "\tquit - to get out\n"
        );
    }

    public void quit() {

    }

    /**
     * main method for StoreView
     * @param args
     */
    public static void main(String[] args) {
        StoreManager sm = new StoreManager();
        StoreView sv1 = new StoreView(sm, sm.assignNewCartId());
        StoreView sv2 = new StoreView(sm,sm.assignNewCartId());
        StoreView sv3 = new StoreView(sm,sm.assignNewCartId());
        StoreView[] users = {sv1, sv2, sv3};
        int activeSV = users.length;

        Scanner sc = new Scanner(System.in);

        while (activeSV > 0) {
            System.out.println("Hello. Welcome to THE BOOK STORE!");
            System.out.print("CHOOSE YOUR STOREVIEW >>> ");
            int choice = sc.nextInt();
            StoreView currUser = users[choice];

            if (choice < users.length && choice >= 0) {
                if (currUser != null) {         //If the user is active
                    String chooseAnother = "";
                    while (!chooseAnother.equalsIgnoreCase("y")) {
                        boolean stillShopping = true;
                        while (stillShopping) {
                            int cartNum = currUser.cartId;
                            System.out.println("CART >>> " + cartNum);
                            System.out.println("Enter a command...");
                            String command = sc.next();

                            // Execute command by entering subroutines
                            switch (command) {
                                case ("browse"):
                                    currUser.browse();
                                    break;
                                case ("viewcart"):
                                    currUser.viewcart();
                                    break;
                                case ("addtocart"):
                                    currUser.addtocart();
                                    break;
                                case ("removefromcart"):
                                    currUser.removefromcart();
                                    break;
                                case ("checkout"):
                                    currUser.checkout();
                                    break;
                                case ("help"):
                                    currUser.help();
                                    break;
                                case ("quit"):
                                    stillShopping = false;
                                    break;
                                default:
                                    System.out.println("ERROR > PLEASE ENTER A VALID COMMAND.");
                                    System.out.println("TYPE 'help' FOR LIST OF COMMANDS.\n");
                            }

                            if (command.equals("checkout")) {
                                break;
                            }
                        }
                        System.out.print("GO TO ANOTHER STOREVIEW? (y/n) >>> ");
                        chooseAnother = sc.next();

                        if (chooseAnother.equalsIgnoreCase("n")) {
                            System.out.println("Going offline..");
                            exit(0);
                        }

                        users[choice] = null;      // Deactivate current user
                        activeSV--;
                    }
                } else {
                    System.out.println("MAIN > ERROR > BAD CHOICE\nTHAT STOREVIEW WAS DEACTIVATED\n");
                }
            } else {
                System.out.printf("MAIN > ERROR > BAD CHOICE\nPLEASE CHOOSE IN RANGE [%d, %d]\n\n",
                        0, users.length - 1);
            }
        }
    }

}
