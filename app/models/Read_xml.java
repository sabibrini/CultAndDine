package models;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.*;

public class Read_xml {

	public static ArrayList<Events> event = new ArrayList<Events>();

    public static String getStreetLoc(Element eElement){
        String s=eElement.getElementsByTagName("vcard:street-address").item(0).getTextContent();
        if(s.equals("")) {
            s=eElement.getElementsByTagName("vcard:street-address").item(1).getTextContent();
        }
        s = s.replaceAll("\\r\\n|\\r|\\n", "");

        return s;
    }

	public static void readEvents() {

		try {

			File fXmlFile = new File("public/inputfiles/events.xml");
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

					event.add(new Events(eElement.getElementsByTagName("title").item(0).getTextContent(),
                            eElement.getElementsByTagName("link").item(0).getTextContent(),
					eElement.getElementsByTagName("category").item(0).getTextContent(),
					eElement.getElementsByTagName("cal:organizer").item(0).getTextContent(),
					eElement.getElementsByTagName("georss:point").item(0).getTextContent(),getStreetLoc(eElement)));

				//event.add(new Events(eElement.getElementsByTagName("title").item(0).getTextContent(), eElement.getElementsByTagName("link").item(0).getTextContent(), eElement.getElementsByTagName("description").item(0).getTextContent(), eElement.getElementsByTagName("category").item(0).getTextContent(),eElement.getElementsByTagName("georss:point").item(0).getTextContent()));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

