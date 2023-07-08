import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static List<String> configs = new ArrayList<>();
    public static boolean save = false;

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder1 = factory.newDocumentBuilder();
        Document doc = builder1.parse(new File("shop.xml"));
        Node root = doc.getDocumentElement();
        read(root);
        configs.remove(0);
        configs.remove(3);
        configs.remove(6);
        Basket basket;
        if (configs.get(0).equals("true")) {
            switch (configs.get(2)) {
                case ("json"):
                    JSONParser parser = new JSONParser();
                    try {
                        Object obj = parser.parse(new FileReader(configs.get(1)));
                        JSONObject jsonObject = (JSONObject) obj;
                        System.out.println(jsonObject);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case ("txt"):
                    basket = Basket.loadFromTxtFile(new File(configs.get(1)));
                    break;
                default:
                    break;
            }
        }
        ClientLog clientLog = new ClientLog();
        FileWriter file = new FileWriter("log.csv");
        basket = new Basket(new String[]{"Молоко", "Хлеб", "Гречневая крупа"}, new int[]{50, 14, 80});
        basket.setSave(configs.get(3));
        basket.setSaveFileName(configs.get(4));
        basket.setSaveFileType(configs.get(5));
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
        if (configs.get(6).equals("true")) {
            clientLog.exportAsCSV(new FileWriter(configs.get(7)));
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
