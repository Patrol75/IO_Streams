import java.io.*;

public class Basket {

    private String[] products;
    private int[] price;
    private int[] cart;

    public Basket(String[] products, int[] price) {
        this.products = products;
        this.price = price;
        cart = new int[price.length];
    }

    public String[] getProducts() {
        return products;
    }

    public void addToCart(int productNum, int amount) {
        cart[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < cart.length; i++) {
            if (cart[i] != 0) {
                System.out.println(products[i] + " " + cart[i] + " шт " + price[i] + " руб/шт " + (cart[i] * price[i]) + " руб в сумме");
            }
        }
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String e : products) {
                out.print(e + " ");
            }
            out.println();
            for (int e : price) {
                out.print(e + " ");
            }
            out.println();
            for (int e : cart)
                out.print(e + " ");
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        Basket basket;
        try (FileReader fr = new FileReader(textFile)) {
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            String[] products = line.split(" ");
            line = reader.readLine();
            String[] stringPrice = line.split(" ");
            int[] price = new int[stringPrice.length];
            for (int i = 0; i < stringPrice.length; i++) {
                price[i] = Integer.parseInt(stringPrice[i]);
            }
            line = reader.readLine();
            String[] stringCart = line.split(" ");
            int[] cart = new int[stringCart.length];
            for (int i = 0; i < stringCart.length; i++) {
                cart[i] = Integer.parseInt(stringCart[i]);
            }
            basket = new Basket(products, price);
            for (int i = 0; i < products.length; i++) {
                basket.addToCart(i, cart[i]);
            }
        }
        return basket;
    }

}
