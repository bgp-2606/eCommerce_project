/*
    myStore.StoreView.java
    Bren-Gelyn Padlan
    101148482
 */
package myStore;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class has the store view that a user can interact with
 * while shopping.
 *
 * @author Bren-Gelyn Padlan
 * @version 5.0
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
     * The JFrame of the GUI
     */
    private final JFrame frame;
    /**
     * The three JPanels inside the frame
     */
    private JPanel mainPanel, headerPanel, footerPanel;
    /**
     * A JTabbedPane that contains the browse and cart tabs
     */
    private JTabbedPane bodyPanel;
    /**
     * The two JPanels that are contained in the bodyPanel
     */
    private JPanel browseTab, cartTab;
    /**
     * An ArrayList of JButtons that adds item to cart
     */
    private ArrayList<JButton> addButtons;
    /**
     * An ArrayList of JButtons that removes items from cart
     */
    private ArrayList<JButton> removeButtons;
    /**
     * The center panel inside cartTab
     */
    private JPanel centerCartPanel;
    /**
     * The items in the cart with their corresponding JLabels
     */
    private HashMap<Integer, JLabel> cartItems;

    /**
     * This is a constructor for store.StoreView object that initializes the
     * store manager and cart id.
     * @param sm a store.StoreManager object that represents the store itself
     * @param cartId an integer that represents unique id of the cart
     */
    public StoreView(StoreManager sm, int cartId) {
        this.sm = sm;
        this.cartId = cartId;

        this.frame = new JFrame("Paper & Quill Bookstore");
        this.frame.setPreferredSize(new Dimension(900, 700));
        this.frame.setMinimumSize(new Dimension(900, 700));
        this.mainPanel = new JPanel(new BorderLayout());
        this.headerPanel = new JPanel(new BorderLayout());
        this.bodyPanel = new JTabbedPane();
        this.footerPanel = new JPanel();
        this.browseTab = new JPanel(new BorderLayout());
        this.cartTab = new JPanel(new BorderLayout());

        this.addButtons = new ArrayList<JButton>();
        this.removeButtons = new ArrayList<JButton>();
        for (int i = 0; i < 9; i++) {
            this.addButtons.add(new JButton("+"));
            this.removeButtons.add(new JButton("-"));
            // Initially disable each remove buttons
            this.removeButtons.get(i).setEnabled(false);
        }

        this.cartItems = new HashMap<Integer, JLabel>();
    }

    /**
     * This method adds item to the current user's cart.
     */
    private void addToCart(int option) {
        int quantity = 1;
        int i = 0;
        for (Product p : this.sm.getAvailableProducts()) {
            if (i == option){
                // Check if chosen item is out of stock
                if (this.sm.getProductStock(p) == 0) {
                    System.out.println("Sorry '" + p.getName() + "' is out of stock.\n");
                    return;
                }
                // Actually add the chosen item in the cart
                this.sm.addToCart(this.cartId, p, quantity);
                // Update GUI
                itemAddToCartTab(p.getId());
            }
            i++;
        }
    }

    /**
     * This method removes the item from the current user's cart.
     */
    private void removeFromCart(int option) {
        int quantity = 1;

        HashMap<Product, Integer> cart = this.sm.getCartContents(this.cartId);
        // If cart is empty, exit this subroutine.
        if (cart.isEmpty()) {
            System.out.println("Your cart is currently empty.\n");
            return;
        }

        for (Product p : cart.keySet()) {
            if (p.getId() == option) {
                // Actually remove the item from cart
                this.sm.removeFromCart(this.cartId, p, quantity);
                // Update GUI
                itemRemoveInCartTab(p.getId());
                break;
            }
        }
    }

    /**
     * This method puts the user in the checkout interface where the summary
     * and total price of the items in the cart are displayed.
     */
    private void userCheckout() {
        if (this.sm.getCartContents(this.cartId).isEmpty()) {
            JOptionPane.showMessageDialog(frame,"You currently have no items in your cart. To checkout, your cart must not be empty.");
        } else {
            StringBuilder stringCheckout = new StringBuilder();
            stringCheckout.append("<html><table>");
            // Place each item in the cart in the stringCheckout
            stringCheckout.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", "Quantity", "Product Name", "Unit Price", "Price"));
            HashMap<Product, Integer> cart = this.sm.getCartContents(this.cartId);
            for (Product p : cart.keySet()) {
                stringCheckout.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                                cart.get(p), p.getName(), p.getPrice(), p.getPrice() * cart.get(p)));

            }

            // Call checkout method in store.StoreManager
            double totalPrice = this.sm.checkout(this.cartId);
            totalPrice = Math.round(totalPrice * 100.0) / 100.0;
            String totalPriceString = Double.toString(totalPrice);
            stringCheckout.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                    "----------", "------------------------------", "---------------", "---------------"));
            stringCheckout.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", " ", " ", "Total : $ ", totalPriceString));
            stringCheckout.append(String.format("<tr><td>%s</td><td>%s</td></tr>", "", "You've been checked out."));
            stringCheckout.append(String.format("<tr><td>%s</td><td>%s</td></tr>", "", "Thank you for shopping!"));
            stringCheckout.append("</table></html>");

            Object[] options = {"Keep Shopping", "Leave Store"};
            // Show the option dialog box with the stringCheckout (which is the receipt) displayed.
            int userClick = JOptionPane.showOptionDialog(frame, stringCheckout.toString(), "Checking out...",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            // YES option is when the user still wants to keep shopping
            // NO option is when the user wants to quit
            if (userClick == JOptionPane.YES_OPTION || userClick == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(frame, "Cart will reset when you go back to Browse Store tab. Enjoy shopping!");
                this.sm.resetCart(this.cartId);
                this.cartItems.clear();
                this.centerCartPanel.removeAll();

                JLabel cartHeader = new JLabel();
                StringBuilder header = new StringBuilder();
                header.append("<html><table>");
                header.append(String.format("<tr><td style='width: 80px'>%s</td><td style='width: 150px'>%s</td><td style='width: 100px'>%s</td><td style='width: 100px'>%s</td></tr>",
                        "Quantity", "Product Name", "Unit Price", "Price"));
                header.append("</table></html>");
                cartHeader.setText(header.toString());
                cartHeader.setFont(new Font("Verdana", Font.BOLD, 12));

                this.centerCartPanel.add(cartHeader);

                int i = 1;
                for (Product p : this.sm.getAvailableProducts()) {
                    if (this.sm.getProductStock(p) == 0) {
                        this.removeButtons.get(i).setEnabled(false);
                    }
                    i++;
                }

            } else if (userClick == JOptionPane.NO_OPTION) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    this.frame.setVisible(false);
                    this.frame.dispose();
                    System.exit(0);
                }
            }
        }
    }

    /**
     * This method adds the JLabel of a newly-added item inside the cartTab. If the item is
     * already in the cart, the JLabel is updated.
     * @param itemId int, the product id of the item to be added to cart
     */
    private void itemAddToCartTab(int itemId) {
        boolean addItemToExisting = false;
        int itemQuantity = 0;
        double itemPrice = 0.0;
        for (Product p : this.sm.getCartContents(this.cartId).keySet()) {
            if (p.getId() == itemId) {
                itemQuantity = this.sm.getCartContents(this.cartId).get(p);
                itemPrice = p.getPrice();
                itemPrice = Math.round(itemPrice * 100.0)/100.0;
                StringBuilder item = new StringBuilder();
                item.append("<html><table>");
                item.append(String.format("<tr><td style='width: 80px'>%s</td><td style='width: 150px'>%s</td><td style='width: 100px'>%s</td><td style='width: 100px'>%s</td></tr>",
                        itemQuantity, p.getName(), itemPrice, itemPrice * itemQuantity));
                item.append("</table></html>");

                for (int id : this.cartItems.keySet()) {
                    if (id == itemId) {
                        addItemToExisting = true;
                        break;
                    }
                }

                if (addItemToExisting) {
                    this.cartItems.get(itemId).setText(item.toString());
                } else {
                    JLabel label = new JLabel(item.toString());
                    label.setFont(new Font("Verdana", Font.ROMAN_BASELINE, 12));
                    this.cartItems.put(itemId, label);

                    // Add the JLabel of the newly-added item
                    this.centerCartPanel.add(this.cartItems.get(itemId));
                }

                break;
            }
        }
    }

    /**
     * This method removes the JLabel of the chosen item inside the cartTab if and only if the
     * the item does not exist in the cart anymore. If the item to be removed exists in the cart
     * and still has quantity greater than 1, then update its corresponding JLabel.
     * @param itemId int, the product id of the item to be removed from cart
     */
    private void itemRemoveInCartTab(int itemId) {
        for (Product p : this.sm.getCartContents(this.cartId).keySet()) {
            if (p.getId() == itemId) {
                int itemQuantity = this.sm.getCartContents(this.cartId).get(p);
                double itemPrice = p.getPrice();
                itemPrice = Math.round(itemPrice * 100.0)/100.0;

                StringBuilder item = new StringBuilder();
                item.append("<html><table>");
                item.append(String.format("<tr><td style='width: 80px'>%s</td><td style='width: 150px'>%s</td><td style='width: 100px'>%s</td><td style='width: 100px'>%s</td></tr>",
                        itemQuantity, p.getName(), itemPrice, itemPrice * itemQuantity));
                this.cartItems.get(itemId).setText(item.toString());
                item.append("</table></html>");
                return;
            }
        }
        this.centerCartPanel.remove(this.cartItems.get(itemId));
        this.cartItems.remove(itemId);
    }

    /**
     * This method adds an action listener to an add button.
     * @param productToAdd int, the id of the product to be added to cart
     * @param stock JLabel, the number of available stocks shown in the GUI
     */
    private void addButtonClick(int productToAdd, JLabel stock) {
        this.addButtons.get(productToAdd).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (Product p : sm.getAvailableProducts()) {
                    if (p.getId() == productToAdd + 1) {
                        // Add item clicked by the user to the cart
                        addToCart(productToAdd);
                        // Update the displayed stock
                        stock.setText("Stock: " + Integer.toString(sm.getProductStock(p)));
                        // If no more stocks, disable button
                        if (sm.getProductStock(p) < 1) {
                            addButtons.get(productToAdd).setEnabled(false);
                        }
                        if (sm.getCartContents(cartId).get(p) >= 1) {
                            removeButtons.get(productToAdd).setEnabled(true);
                        }
                        break;
                    }
                }
            }
        });
    }

    /**
     * This method adds an action listener to a remove button.
     * @param productToRem int, the id of the product to be removed from cart
     * @param stock Jlabel, the number of avaiable stocks shown in the GUI
     */
    private void removeButtonClick(int productToRem, JLabel stock) {
        this.removeButtons.get(productToRem).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (Product p : sm.getAvailableProducts()) {
                    if (p.getId() == productToRem + 1) {
                        if (sm.getCartContents(cartId).get(p) == 1) {
                            removeButtons.get(productToRem).setEnabled(false);
                        }
                        // Remove item clicked by the user from the cart
                        removeFromCart(productToRem + 1);
                        // Update the displayed stock
                        stock.setText("Stock: " + Integer.toString(sm.getProductStock(p)));
                        // If the stocks of that item is greater than 0, enable add button
                        if (sm.getProductStock(p) > 0) {
                            addButtons.get(productToRem).setEnabled(true);
                        }
                        break;
                    }
                }
            }
        });
    }

    /**
     * Sets up the browse tab inside the body panel.
     */
    private void setupBrowseTab() {
        JPanel northPanel = new JPanel(new GridLayout(4, 3));
        JLabel l1 = new JLabel("BROWSE ITEMS IN OUR SHOP!");
        l1.setFont(new Font("Cambria Math", Font.BOLD, 20));
        northPanel.add(l1);
        this.browseTab.add(northPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(500, 800));
        centerPanel.setBorder(new LineBorder(Color.WHITE, 3));

        String[] names = {"1984", "The Great Gatsby", "Silent Spring", "A Room of One's Own",
                "Catcher in the Rye", "The Code Breaker", "Crime and Punishment", "Moby Dick", "The Sixth Extinction"};
        ArrayList<JPanel> productPanels = new ArrayList<JPanel>();
        ArrayList<JPanel> productInfo = new ArrayList<JPanel>();
        ArrayList<JLabel> productImages = new ArrayList<JLabel>();
        ArrayList<JLabel> productNames = new ArrayList<JLabel>();
        ArrayList<JLabel> productPrice = new ArrayList<JLabel>();
        ArrayList<JLabel> productStocks = new ArrayList<JLabel>();
        ArrayList<JPanel> buttonPanels = new ArrayList<JPanel>();


        for (int i = 0; i < 9; i++) {
            // Setup each product panel
            productPanels.add(new JPanel(new BorderLayout()));
            productPanels.get(i).setBorder(new LineBorder(Color.WHITE, 10));


            // Setup the image panels
            productImages.add(new JLabel());
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("src/myStore/book_covers/" + names[i] + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image dimg = img.getScaledInstance(120, 200, Image.SCALE_SMOOTH);
            productImages.get(i).setIcon(new ImageIcon(dimg));
            productPanels.get(i).add(productImages.get(i), BorderLayout.WEST);


            // Setup product info: name, price, available stock
            productInfo.add(new JPanel(new GridLayout(4, 1)));
            productNames.add(new JLabel(names[i]));
            for (Product p : this.sm.getAvailableProducts()) {
                if (p.getId() == i + 1) {
                    productStocks.add(new JLabel("Stock: "+ Integer.toString(this.sm.getProductStock(p))));
                    productPrice.add(new JLabel(String.format("$ %.2f", p.getPrice())));
                    break;
                }
            }

            productNames.get(i).setFont(new Font("Times New Roman", Font.BOLD, 14));
            productStocks.get(i).setFont(new Font("Times New Roman", Font.BOLD, 14));
            productPrice.get(i).setFont(new Font("Times New Roman", Font.BOLD, 14));

            productInfo.get(i).add(productNames.get(i));
            productInfo.get(i).add(productStocks.get(i));
            productInfo.get(i).add(productPrice.get(i));


            // Setup button panels
            buttonPanels.add(new JPanel());
            addButtonClick(i, productStocks.get(i));
            removeButtonClick(i, productStocks.get(i));

            buttonPanels.get(i).add(this.addButtons.get(i));
            buttonPanels.get(i).add(this.removeButtons.get(i));


            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                    productInfo.get(i), buttonPanels.get(i));
            productPanels.get(i).add(splitPane, BorderLayout.EAST);
            centerPanel.add(productPanels.get(i));
        }

        JScrollPane browse = new JScrollPane(centerPanel);
        browse.setPreferredSize(new Dimension(500, 500));

        this.browseTab.add(browse, BorderLayout.CENTER);
    }

    /**
     * Sets up the cart tab inside the body panel
     */
    private void setupCartTab() {
        JPanel northPanel = new JPanel(new FlowLayout(1));
        JLabel l1 = new JLabel("HERE'S WHAT IS IN YOUR CART");
        l1.setFont(new Font("Cambria Math", Font.BOLD, 20));
        northPanel.add(l1);
        this.cartTab.add(northPanel, BorderLayout.NORTH);

        this.centerCartPanel = new JPanel();
        this.centerCartPanel.setLayout(new BoxLayout(this.centerCartPanel, BoxLayout.Y_AXIS));
        this.centerCartPanel.setPreferredSize(new Dimension(500, 1000));
        this.centerCartPanel.setBorder(new LineBorder(Color.DARK_GRAY, 5));

        JLabel cartHeader = new JLabel();
        StringBuilder header = new StringBuilder();
        header.append("<html><table>");
        header.append(String.format("<tr><td style='width: 80px'>%s</td><td style='width: 150px'>%s</td><td style='width: 100px'>%s</td><td style='width: 100px'>%s</td></tr>",
                                        "Quantity", "Product Name", "Unit Price", "Price"));
        header.append("</table></html>");
        cartHeader.setText(header.toString());
        cartHeader.setFont(new Font("Verdana", Font.BOLD, 12));

        this.centerCartPanel.add(cartHeader);
        this.cartTab.add(this.centerCartPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 15));
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userCheckout();
            }
        });
        southPanel.add(checkoutButton);
        this.cartTab.add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * This method displays the GUI for the book store.
     * @return a boolean true if the user enters 'quit' or 'checkout'
     */
    public void displayGUI() {
        this.frame.setTitle("Paper & Quill Bookstore");

        // Setup header panel
        JLabel storeTitle = new JLabel("Paper & Quill");
        storeTitle.setFont(new Font("Monotype Corsiva", Font.BOLD, 40));
        this.headerPanel.add(storeTitle, BorderLayout.CENTER);
        JLabel userId = new JLabel("User: " + this.cartId);
        userId.setFont(new Font("Arial",Font.BOLD, 18));
        this.headerPanel.add(userId, BorderLayout.EAST);

        // Setup body panel
        setupBrowseTab();
        setupCartTab();
        this.bodyPanel.add("Browse Store", this.browseTab);
        this.bodyPanel.add("Shopping Cart", this.cartTab);

        // Add sub-panels to the main panel
        this.mainPanel.add(this.headerPanel, BorderLayout.NORTH);
        this.mainPanel.add(this.bodyPanel, BorderLayout.CENTER);
        this.mainPanel.add(this.footerPanel, BorderLayout.SOUTH);

        // Add main panel to frame and pack it
        this.frame.add(this.mainPanel);
        this.frame.pack();

        // Set WindowListener when user clicks close button
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });

        // Make frame visible
        this.frame.setVisible(true);
    }

    /**
     * This is main method for the store where the user can interact with.
     * @param args
     */
    public static void main(String[] args) {
        StoreManager sm = new StoreManager();
        StoreView sv1 = new StoreView(sm,sm.assignNewCartId());
        sv1.displayGUI();
    }
}