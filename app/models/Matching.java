package models;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;


public class Matching{
    public static ArrayList<Float> dis=new ArrayList<Float>();

    public static void checkdis( Element e){
        Float d=Float.valueOf(e.getElementsByTagName("text").item(0).getTextContent());
        if(d<=50){
            dis.add(d);
        }
    }

    public static void getDistanceData(){

            try {

                File fXmlFile = new File("public/inputfiles/distance.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);


                doc.getDocumentElement().normalize();


                NodeList nList = doc.getElementsByTagName("row");

                System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    System.out.println("\nCurrent Element :" + nNode.getNodeName());

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        checkdis(eElement);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
