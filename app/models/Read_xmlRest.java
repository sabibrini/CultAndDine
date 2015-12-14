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
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import javax.persistence.OptimisticLockException;

//nice comment
public class Read_xmlRest {

    public static ArrayList<Geographics> geo = new ArrayList<Geographics>();

    public static void readRestaurants() throws Exception {

    }
    /*
       Calculatig the long and lat data of the Restaurants and save the Restaurant and it Geographics in an array
        */
    public static void readLongLat(Restaurants r) {
        try {

            File fXmlFile = new File("public/inputfiles/restLoc.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);


            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("result");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    geo.add(new Geographics(eElement.getElementsByTagName("lat").item(0).getTextContent(), eElement.getElementsByTagName("lng").item(0).getTextContent(),r ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Geographics x:geo){
            System.out.println(x.getLat()+" "+x.getLnd());
        }
    }

}

