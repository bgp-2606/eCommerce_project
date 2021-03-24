/*
    store.StoreView.java
    Bren-Gelyn Padlan
    101148482
 */
package store;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class has the store view that a user can interact with
 * while shopping.
 *
 * @author Bren-Gelyn Padlan
 * @version 3.0
 */
public class StoreView {
    /**
     * Store manager that contains the inventory and shopping cart
     */
    private final StoreManager sm;
    /**
     * Unique id of the cart
     */
    private final int cartId;
    /**
     *
     */
    private JFrame frame;

    /**
     * This is a constructor for store.StoreView object that initializes the
     * store manager and cart id.
     * @param sm a store.StoreManager object that represents the store itself
     * @param cartId an integer that represents unique id of the cart
     */
    public StoreView(StoreManager sm, int cartId) {
        this.sm = sm;
        this.cartId = cartId;
        this.frame = new JFrame();
    }

    /**
     * This method displays the summary of the items being sold in the store.
     */
    private void browse() {
        System.out.println(
                "|--------------------------THE BOOK STORE--------------------------| \n" +
                "\\-----------------------------BROWSE-------------------------------/");
        System.out.println("Type 'help' for a list of commands.\n");

        System.out.printf("%-8s | %-30s | %-10s | \n", "Stock", "Product Name", "Unit Price");

        // Loop through the bookList and print stock, product name, and unit price
        for (Product p : this.sm.getBooks()) {
            System.out.printf("%-8d | %-30s | %-10.2f | \n",
                    this.sm.getStock(p), p.getName(), p.getPrice());
        }
        System.out.println();
    }

    /**
     * This method allows the user to view his/her cart.
     */
    private void viewCart() {
        System.out.println(
                "|--------------------------THE BOOK STORE--------------------------| \n" +
                "\\------------------------------CART--------------------------------/");
        System.out.println("Type 'help' for a list of commands.\n");

        System.out.printf("%-8s | %-30s | %-10s | \n", "Quantity", "Product Name", "Unit Price");

        HashMap<Integer, Integer> cart = this.sm.getCart(this.cartId);

        for (Integer id : cart.keySet()) {
            for (Product p : this.sm.getBooks()) {
                if (id == p.getId()) {
                    System.out.printf("%-8d | %-30s | %-10.2f | \n",
                            cart.get(id), p.getName(), p.getPrice());
                }
            }
        }
        System.out.println("________________________________________________________");
        System.out.println();
    }

    /**
     * This method adds item to the current user's cart.
     */
    private void addToCart() {
        int option, quantity;
        String input = "y";
        Scanner sc = new Scanner(System.in);

        while (input.equalsIgnoreCase("y")) {
            System.out.println(
                    "|--------------------------THE BOOK STORE--------------------------| \n" +
                    "\\-------------------------------ADD--------------------------------/");
            System.out.println("Type 'help' for a list of commands.\n");

            System.out.printf("%-8s | %-30s | %-10s | %-8s |\n", "Stock", "Product Name", "Unit Price", "Option");

            ArrayList<Product> bookList = this.sm.getBooks();

            // Option, I call it index because it is actually the index of the product in the book list.
            int index = 0;
            for (Product p : bookList) {
                System.out.printf(
                        "%-8d | %-30s | %-10.2f | %-8s | \n",
                        this.sm.getStock(p), p.getName(), p.getPrice(), index
                );
                index++;
            }

            try {
                System.out.print("\nENTER OPTION NUMBER: >>> ");
                option = sc.nextInt();
                // If user inputs option that is not in the list
                if (option > bookList.size() - 1) {
                    System.out.printf("ERROR > INVALID OPTION > ENTER OPTION FROM RANGE [0, %d]\n\n", index-1);
                    continue;
                }
            } catch (InputMismatchException ime) {
                System.out.println("ERROR > INVALID OPTION > OPTION ENTERED MUST BE A NUMBER\n");
                sc.next();
                continue;
            }

            // Get the product id of the chosen item
            int itemId = bookList.get(option).getId();
            // Get book itself
            Product book = sm.toProduct(itemId);
            // Check if chosen item is out of stock
            if (this.sm.getStock(book) == 0) {
                System.out.println("Sorry '" + book.getName() + "' is out of stock.\n");

            } else {
                // Loop until user enters a valid input for quantity
                while (true) {
                    try {
                        System.out.print("ENTER AMOUNT TO ADD: >>> ");
                        quantity = sc.nextInt();
                        // If user inputs a negative quantity
                        if (quantity <= 0) {
                            System.out.println("ERROR > INVALID QUANTITY > QUANTITY MUST BE A POSITIVE NUMBER");
                            System.out.println("Please re-enter a valid quantity.\n");
                            continue;
                        }
                        // If user inputs quantity greater than what is available
                        if (quantity > this.sm.getStock(book)) {
                            System.out.println("ERROR > INVALID QUANTITY > NOT ENOUGH STOCKS FOR THE REQUESTED QUANTITY");
                            System.out.println("Please re-enter a valid quantity.\n");
                            continue;
                        }
                        break;

                    } catch (InputMismatchException ime) {
                        System.out.println("ERROR > INVALID QUANTITY > QUANTITY ENTERED IS NOT AN INTEGER");
                        System.out.println("Please re-enter a valid quantity.\n");
                        sc.next();
                    }
                }
                // Actually add the chosen item in the cart
                sm.addToCart(this.cartId, itemId, quantity);
            }

            //Prompt user if s/he wants to add more items to cart
            System.out.print("ANYTHING MORE TO ADD? (y): >>> ");
            input = sc.next();
            System.out.println();
        }
    }

    /**
     * This method removes the item from the current user's cart.
     */
    private void removeFromCart() {
        int option, quantity;
        String input = "y";
        Scanner sc = new Scanner(System.in);

        while (input.equalsIgnoreCase("y")) {
            System.out.println(
                    "|--------------------------THE BOOK STORE--------------------------| \n" +
                    "\\------------------------------REMOVE------------------------------/");
            System.out.println("Type 'help' for a list of commands.\n");

            System.out.printf("%-8s | %-30s | %-10s | %-8s | \n", "Quantity", "Product Name", "Unit Price", "Option");

            ArrayList<Product> bookList = this.sm.getBooks();
            HashMap<Integer, Integer> cart = this.sm.getCart(this.cartId);

            // Option, I call it index because it is actually the index of the item in the cart.
            int index = 0;
            for (Integer id : cart.keySet()) {
                for (Product p : bookList) {
                    if (id == p.getId()) {
                        System.out.printf("%-8d | %-30s | %-10.2f | %-8s | \n",
                                cart.get(id), p.getName(), p.getPrice(), index);
                    }
                }
                index++;
            }

            // If cart is empty, exit this subroutine.
            if (cart.isEmpty()) {
                System.out.println("Your cart is currently empty.\n");
                return;
            }

            try {
                System.out.print("\nENTER OPTION NUMBER: >>> ");
                option = sc.nextInt();
                // If user inputs an option that is not in the list
                if (option >= cart.size()) {
                    System.out.printf("ERROR > INVALID OPTION > ENTER OPTION FROM RANGE [0, %d]\n\n", index-1);
                    continue;
                }
            } catch (InputMismatchException ime) {
                System.out.println("ERROR > INVALID QUANTITY > QUANTITY ENTERED IS NOT AN INTEGER\n");
                sc.next();
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
            // Loop until user enters a valid input for quantity
            while (true) {
                try {
                    System.out.print("ENTER AMOUNT TO ADD: >>> ");
                    quantity = sc.nextInt();
                    // If user inputs a negative quantity
                    if (quantity <= 0) {
                        System.out.println("ERROR > INVALID QUANTITY > QUANTITY ENTERED MUST BE COUNTABLE");
                        System.out.println("Please re-enter a valid quantity.\n");
                        continue;
                    }
                    // If user inputs quantity greater than what is available
                    if (quantity > cart.get(itemId)) {
                        System.out.println("ERROR > INVALID QUANTITY > NOT ENOUGH ITEM IN CART FOR THE REQUESTED QUANTITY");
                        System.out.println("Please re-enter a valid quantity.\n");
                        continue;
                    }
                    break;

                } catch (InputMismatchException ime) {
                    System.out.println("ERROR > INVALID QUANTITY > QUANTITY ENTERED IS NOT AN INTEGER");
                    System.out.println("Please re-enter a valid quantity.\n");
                    sc.next();
                }
            }

            // Actually remove the item from cart
            this.sm.removeFromCart(this.cartId, itemId, quantity);

            //Prompt user if s/he wants keep removing items from cart
            System.out.print("ANYTHING MORE TO REMOVE? (y): >>> ");
            input = sc.next();
            System.out.println();
        }
    }

    /**
     * This method puts the user in the checkout interface where the summary
     * and total price of the items in the cart are displayed.
     */
    private void userCheckout() {
        System.out.println(
                "|--------------------------THE BOOK STORE--------------------------| \n" +
                        "\\------------------------------CHECKOUT----------------------------/");

        System.out.printf("%-8s | %-30s | %-10s | %-10s \n", "Quantity", "Product Name", "Unit Price", "Price");

        HashMap<Integer, Integer> cart = this.sm.getCart(this.cartId);

        for (Integer productId : cart.keySet()) {
            for (Product p : this.sm.getBooks()) {
                if (productId == p.getId()) {
                    System.out.printf("%-8d | %-30s | %-10.2f | %-10.2f \n",
                            cart.get(productId), p.getName(), p.getPrice(), p.getPrice() * cart.get(productId));
                }
            }
        }

        // Call checkout method in store.StoreManager
        double totalPrice = this.sm.checkout(this.cartId);

        System.out.println("__________________________________________________________________");
        System.out.printf("%60s%.2f\n", "Total: $", totalPrice);

        System.out.println("Thank you for shopping.\n");
    }

    /**
     * This method prints out the list of valid commands the user can use to
     * shop in the store.
     */
    private void help() {
        System.out.println(
                "List of Commands: \n"
                + "\tbrowse - displays list of products\n"
                + "\tviewcart - displays cart summary\n"
                + "\taddtocart - go to the interface where you can add items to cart\n"
                + "\tremovefromcart - go to the checkout interface with options to remove items\n"
                + "\tcheckout - when you want to checkout\n"
                + "\tquit - to get out\n");
    }

    /**
     * This method returns all items in the user's cart back to the inventory stock.
     * Precondition: user's cart must not be empty
     */
    private void returnItems() {
        HashMap<Integer, Integer> cart = this.sm.getCart(this.cartId);
        int itemId = 0;
        int i = 0;
        int size = cart.size();
        while (i < size) {
            for (Integer id : cart.keySet()) {
                itemId = id;
                break;
            }
            // Removes the item based on the itemId obtained inside the for loop
            this.sm.removeFromCart(this.cartId, itemId, cart.get(itemId));
            i++;
        }
    }

    /**
     * This method displays the GUI for the book store.
     * @return a boolean true if the user enters 'quit' or 'checkout'
     */
    public boolean displayGUI() {
        Scanner sc = new Scanner(System.in);

        System.out.println("CART >>> " + this.cartId);
        System.out.println("Enter a command...");
        String command = sc.next();

        boolean isCartEmpty = this.sm.getCart(this.cartId).isEmpty();

        // Execute the command by entering the subroutines
        switch (command) {
            case ("browse"):
                browse();
                break;
            case ("viewcart"):
                viewCart();
                break;
            case ("addtocart"):
                addToCart();
                break;
            case ("removefromcart"):
                removeFromCart();
                break;
            case ("checkout"):
                // If the cart is empty, do not checkout (disconnect) user
                if (isCartEmpty) {
                    System.out.println("You do not have items in the cart.\n");
                    break;
                }
                userCheckout();
                this.sm.resetCart(this.cartId);
                return true;
            case ("help"):
                help();
                break;
            case ("quit"):
                // If current user's cart is NOT empty, put items back to inventory.
                if (!isCartEmpty) {
                    returnItems();
                }
                return true;
            default:
                System.out.println("ERROR > PLEASE ENTER A VALID COMMAND.\n" +
                                    "TYPE 'help' FOR LIST OF COMMANDS.\n");
        }
        return false;
    }

    /**
     * This is main method for the store where the user can interact with.
     * @param args
     */
    public static void main(String[] args) {
        StoreManager sm = new StoreManager();
        StoreView sv1 = new StoreView(sm,sm.assignNewCartId());
        StoreView sv2 = new StoreView(sm,sm.assignNewCartId());
        StoreView sv3 = new StoreView(sm,sm.assignNewCartId());
        StoreView[] users = {sv1, sv2, sv3};
        int activeSV = users.length;
        int choice;
        Scanner sc = new Scanner(System.in);

        while (activeSV > 0) {
            // Loop until user inputs an integer
            while (true) {
                System.out.println("\n___________________________________________________________________");
                System.out.println("Hello. Welcome to THE BOOK STORE!\n");
                System.out.print("CHOOSE YOUR STOREVIEW >>> ");
                try {
                    choice = sc.nextInt();
                    break;
                } catch (InputMismatchException ime) {
                    System.out.println("\nMAIN > ERROR > BAD CHOICE\nNO SUCH STOREVIEW");
                    sc.next();
                }
            }
            if (choice < users.length && choice >= 0) {
                StoreView currUser = users[choice];
                if (currUser != null) {         //If the user is active
                    String chooseAnother = "";
                    while (!chooseAnother.equalsIgnoreCase("y")) {
                        if (currUser.displayGUI()) {
                            users[choice] = null;      // Deactivate current user
                            activeSV--;
                            break;
                        }

                        System.out.print("GO TO ANOTHER STOREVIEW? (y) >>> ");
                        chooseAnother = sc.next();
                    }
                } else {
                    System.out.println("\nMAIN > ERROR > BAD CHOICE\nTHAT STOREVIEW WAS DEACTIVATED");
                }
            } else {
                System.out.printf("\nMAIN > ERROR > BAD CHOICE\nPLEASE CHOOSE IN RANGE [%d, %d]\n",
                        0, users.length - 1);
            }
        }
        System.out.println("\n___________________________________________________________________");
        System.out.println("No more active users available.");
        System.out.println("Store going offline...");
    }

}