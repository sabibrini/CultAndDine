package models;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class Read_xml {

	public static ArrayList<Events> event = new ArrayList<Events>();
	public static ArrayList<Restaurants> res = new ArrayList<Restaurants>();

	public static void readEvents() {

		try {

			File fXmlFile = new File("CultAndDine2/public/inputfiles/events.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);


			doc.getDocumentElement().normalize();


			NodeList nList = doc.getElementsByTagName("item");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					event.add(new Events(eElement.getElementsByTagName("title").item(0).getTextContent(), eElement.getElementsByTagName("link").item(0).getTextContent(), eElement.getElementsByTagName("description").item(0).getTextContent(), eElement.getElementsByTagName("category").item(0).getTextContent()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readRestaurants() {

		try {

			File fXmlFile = new File("CultAndDine2/public/inputfiles/restaurantsAPI.xml");
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
					res.add(new Restaurants(eElement.getElementsByTagName("index").item(0).getTextContent(), eElement.getElementsByTagName("restaurantName").item(0).getTextContent(), eElement.getElementsByTagName("text").item(0).getTextContent(), eElement.getElementsByTagName("priceclass").item(0).getTextContent()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

