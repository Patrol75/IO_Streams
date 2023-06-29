import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("basket.bin");
        Basket basket = new Basket(new String[]{"Молоко", "Хлеб", "Гречневая крупа"}, new int[]{50, 14, 80});
        basket.addToCart(0, 2);
        basket.addToCart(2, 1);
        basket.printCart();
        basket.saveBin(file);
        Basket basket1 = Basket.loadFromBinFile(file);
        basket1.printCart();

    }
}