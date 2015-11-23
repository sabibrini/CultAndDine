package models;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import com.eclipsesource.json.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.*;

public class Read_xmlRest {

    public static ArrayList<Restaurants> res = new ArrayList<Restaurants>();

    public static void readRestaurants() throws Exception{
        String site="https://www.kimonolabs.com/api/6z5dt1mo?apikey=mBBMysBxfIxQJbVAINmoonSlIhen0foo&authorization=n3G3laDyjIYIzXw3gFVgTc7k56mcaiy5";
        URL endpoint =new URL(site);
        String endpoint_content = IOUtils.toString(endpoint,"UTF-8");
       //JsonValue value = Json.parse("{\"status\": \"OK\",\"origin_addresses\": [ \"Vancouver, BC, Canada\", \"Seattle, État de Washington, États-Unis\" ],\"destination_addresses\": [ \"San Francisco, Californie, États-Unis\", \"Victoria, BC, Canada\" ],\"rows\": [ {\"elements\": [ {\"status\": \"OK\",\"duration\": {\"value\": 340110,\"text\": \"3 jours 22 heures\"},\"distance\": {\"value\": 1734542,\"text\": \"1 735 km\"}}, {\"status\": \"OK\",\"duration\": {\"value\": 24487,\"text\": \"6 heures 48 minutes\"},\"distance\": {\"value\": 129324,\"text\": \"129 km\"}} ]}, {\"elements\": [ {\"status\": \"OK\",\"duration\": {\"value\": 288834,\"text\": \"3 jours 8 heures\"},\"distance\": {\"value\": 1489604,\"text\": \"1 490 km\"}}, {\"status\": \"OK\",\"duration\": {\"value\": 14388,\"text\": \"4 heures 0 minutes\"},\"distance\": {\"value\": 135822,\"text\": \"136 km\"}} ]} ]}");
        JsonObject fson= Json.parse(endpoint_content).asObject();
        //go throw the object and get the things out
      //  JsonArray items= Json.parse(jsonData).asObject().get("collection1").asArray();
        /*if (value.isString()) {
            String string = value.asString();
            System.out.println("ALLALALALALALALALALAL");
        }
        if (value.isArray()) {
            JsonArray array = value.asArray();
            //for (JsonValue val : array) {
                String name=array.get(0).asString();
                System.out.println(name);
                String bla=array.get(1).asString();
                System.out.println("ALLALALALALALALALALAL");
            //}
        }*/
        System.out.println("ALLALALALALALALALALAL");
        /*try {

            File fXmlFile = new File("public/inputfiles/restaurantsAPI.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);


            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("collection1");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    res.add(new Restaurants(eElement.getElementsByTagName("restaurantName").item(0).getTextContent(), eElement.getElementsByTagName("text").item(0).getTextContent(), eElement.getElementsByTagName("priceclass").item(0).getTextContent(), eElement.getElementsByTagName("index").item(0).getTextContent()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}

