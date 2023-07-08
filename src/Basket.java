import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Basket implements java.io.Serializable {

    private String[] products;
    private int[] price;
    private int[] cart;
    private String save;
    private String saveFileName;
    private String saveFileType;

    public Basket(String[] products, int[] price) {
        this.products = products;
        this.price = price;
        cart = new int[price.length];
    }

    public void addToCart(int productNum, int amount) throws IOException {
        cart[productNum] += amount;
        if (save.equals("true")) {
            switch (saveFileType) {
                case ("json"):
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    try (FileWriter file2 = new FileWriter(saveFileName)) {
                        file2.write(gson.toJson(this));
                        file2.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case ("txt"):
                    saveTxt(new File(saveFileName));
                    break;
                default:
                    break;
            }
        }
    }

    public void setCart(int[] cart) {
        this.cart = cart;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public void setSaveFileType(String saveFileType) {
        this.saveFileType = saveFileType;
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

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String e : products) {
                out.print(e + "_");
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
            String[] products = line.split("_");
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
