import java.io.*;

public class Basket implements java.io.Serializable {

    private String[] products;
    private int[] price;
    private int[] cart;

    public Basket(String[] products, int[] price) {
        this.products = products;
        this.price = price;
        cart = new int[price.length];
    }

    public void addToCart(int productNum, int amount) {
        cart[productNum] += amount;
    }

    public void setCart(int[] cart) {
        this.cart = cart;
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < cart.length; i++) {
            if (cart[i] != 0) {
                System.out.println(products[i] + " " + cart[i] + " шт " + price[i] + " руб/шт " + (cart[i] * price[i]) + " руб в сумме");
            }
        }
    }

    public void saveBin(File binFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(binFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            Basket basket = new Basket(products, price);
            basket.setCart(cart);
            oos.writeObject(basket);
        }
    }

    public static Basket loadFromBinFile(File binFile) throws IOException {
        Basket basket;
        try (FileInputStream fis = new FileInputStream(binFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

}
