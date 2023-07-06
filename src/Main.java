import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ClientLog clientLog = new ClientLog();
        FileWriter file = new FileWriter("log.csv");
        Basket basket = new Basket(new String[]{"Молоко", "Хлеб", "Гречневая крупа"}, new int[]{50, 14, 80});
        basket.addToCart(0, 2);
        clientLog.log(0, 2);
        basket.addToCart(2, 1);
        clientLog.log(2, 1);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try (FileWriter file2 = new FileWriter("basket.json")) {
            file2.write(gson.toJson(basket));
            file2.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientLog.exportAsCSV(file);
    }
}