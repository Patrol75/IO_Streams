import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Main {
    static List<String> configs = new ArrayList<>();

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory. newInstance();
        DocumentBuilder builder1 = factory.newDocumentBuilder();
        Document doc = builder1.parse( new File("shop.xml"));
        Node root = doc.getDocumentElement();
        read(root);
        configs.remove(0);
        configs.remove(3);
        configs.remove(6);
        System.out.println(configs);
        if (configs.get(0) == "true"){

        }


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

    private static void read(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                Element element = (Element) node_;
                NodeList nodeList1 = element.getChildNodes();
                Node node1 = (Node) nodeList1.item(0);
                configs.add(node1.getNodeValue());
                read(node_);
            }
        }
    }
}
