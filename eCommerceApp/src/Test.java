public class Test {
    public static void main(String[] args) {

        //Inventory i1 = new Inventory();

        //i1.addStock(book1, 20);
        //i1.addStock(book2, 30);

        //System.out.println("Book 1 quantity: " + i1.getNumStock(book1.getId()));
        //System.out.println("Book 2 quantity: " + i1.getNumStock(book2.getId()));

        //i1.removeStock(book1, 2);
        //i1.addStock(book2, 6);

        //System.out.println();
        //System.out.println("Book 1 quantity: " + i1.getNumStock(book1.getId()));
        //System.out.println("Book 2 quantity: " + i1.getNumStock(book2.getId()));

        Product book1 = new Product("book1", 001, 1.99);
        Product book2 = new Product("book2", 002, 2.45);
        Product book4 = new Product("book4", 004, 1.05);


        int[][] items = new int[2][2];

        items[0][0] = 001;
        items[0][1] = 7;
        items[1][0] = 002;
        items[1][1] = 20;

        StoreManager sM = new StoreManager();
        double total = 0.0;

        System.out.println(sM.getStock(book1));
        System.out.println(sM.getStock(book2));

        sM.checkout();

        System.out.println(total);

        System.out.println(sM.getStock(book1));
        System.out.println(sM.getStock(book2));
        System.out.println(sM.getStock(book4));

    }
}
