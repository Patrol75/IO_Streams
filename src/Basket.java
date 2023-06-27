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

    public void saveBin(File binFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(binFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(products);
            oos.writeObject(price);
            oos.writeObject(cart);
        }
    }

    public static Basket loadFromBinFile(File binFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(binFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            String[] products = (String[]) ois.readObject();
            int[] price = (int[]) ois.readObject();
            int[] cart = (int[]) ois.readObject();
            System.out.println("Ваша корзина:");
            for (int i = 0; i < cart.length; i++) {
                if (cart[i] != 0) {
                    System.out.println(products[i] + " " + cart[i] + " шт " + price[i] + " руб/шт " + (cart[i] * price[i]) + " руб в сумме");
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
