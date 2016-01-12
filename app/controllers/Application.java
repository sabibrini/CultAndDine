package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import play.data.*;
import play.data.DynamicForm;
import static play.data.Form.*;
import java.io.*;
import java.net.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import play.*;
import play.mvc.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import models.*;

public class Application extends Controller {

    /*
    Renders Home
     */
    public play.mvc.Result start() {
        return ok(start.render(new Options()));
    }

    /*
    Home application for Events
    Endpoint Connection to wien.gv.at with date input from the user
    Saving the data in events.xml and with the call readEvents we save the events from the xml in an ArrayList
    This methode renders the selected Event site
     */
    public play.mvc.Result events() throws Exception{
         DynamicForm requestData = form().bindFromRequest();
         String startdate=requestData.get("dateStart");
         String end= requestData.get("dateEnd");
        
        String site= "https://www.wien.gv.at/vadb/internet/AdvPrSrv.asp?Layout=rss-vadb_neu&Type=R&hmwd=d&vie_range-from="+startdate+"&vie_range-to="+end;
        
        
        URL url = new URL(site);
        URLConnection conn = url.openConnection();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(conn.getInputStream());

        DOMSource domSource = new DOMSource(doc);
        TransformerFactory tfactory = TransformerFactory.newInstance();
        

        FileWriter myOutput = new FileWriter("public/inputfiles/events.xml");
        Transformer xform = tfactory.newTransformer();
        xform.setOutputProperty(OutputKeys.INDENT, "yes");
        xform.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        xform.transform(domSource, new StreamResult(myOutput));

        models.Read_xml.readEvents();
        
        return ok(selectedEvents.render(models.Read_xml.event));
    }

   /* public play.mvc.Result restaurants() throws Exception{

        models.Read_xmlRest.readRestaurants();
        List<Restaurants> rest=Restaurants.find.all();

        return ok(selectedRestaurants.render(rest));
    }*/
    //not used yet
    public play.mvc.Result filterCategor(){
        List<Restaurants> r=FilterRestaurant.filterCategory("Weinbar");
        return ok(selectedRestaurants.render(r));
    }

    /*
    Home application for Restaurants
    Userinput= Dropdownbox. Defaultvalue Mariahilf
     This methode render the selected Restaurants with the Restaurant list as parameter
     */
    public play.mvc.Result filterQuatDatabase(){
    	//form request returns null
    	Form<Options> form = Form.form(Options.class).bindFromRequest();
        if (form.hasErrors()) {

        }
    	String userinput = form.field("name").value();
    	System.out.println(userinput);
    	//Mariahilf is the default value
        List<Restaurants> r=FilterRestaurant.filterQuarter("Mariahilf");
        return ok(selectedRestaurants.render(r));
    }
    /*
    Home Button on every Page
    new option for the drop down
     */
    public play.mvc.Result goHome() {
    	return ok(start.render(new Options()));
    }

    /*
    Application on selected Restaurants. Input =Clicking of the user at a  Restaurants name
    the google API needs some special syntax all spaces have to be "+"
     Here we implement the long and lat data of the restaurant maybe for later for the maps
     This methode render the event site in which the user can see all details of the Restaurants
     and can search for near Events
     */
    public play.mvc.Result calcLongLad(String name)throws Exception{
        List<Restaurants> r=FilterRestaurant.filterName(name);
        Restaurants rest=r.get(0);
        String adress=rest.getAdress();
        if(adress.contains("\n")){
            adress=adress.replace("\n"," ");
        }
        if(adress.contains(" ")){
            adress=adress.replace(" ","+");
        }
        System.out.println(adress);
        String site="https://maps.googleapis.com/maps/api/geocode/xml?address="+adress;

        URL url = new URL(site);
        URLConnection conn = url.openConnection();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(conn.getInputStream());

        DOMSource domSource = new DOMSource(doc);
        TransformerFactory tfactory = TransformerFactory.newInstance();


        FileWriter myOutput = new FileWriter("public/inputfiles/restLoc.xml");
        Transformer xform = tfactory.newTransformer();
        xform.setOutputProperty(OutputKeys.INDENT, "yes");
        xform.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        xform.transform(domSource, new StreamResult(myOutput));

        Read_xmlRest.readLongLat(rest);
        String src="https://www.google.com/maps/embed/v1/place?key=AIzaSyDVGIonPsFY9X03uVDndvXKmg0xOEuSyHk &q="+rest.getAdress()+",1060+Wien";

        return  ok(restaurant.render(rest));
    }

    /*
      Matching methode 1 Restaurant all near elements.Input Restaurantname = User clicks at the restaurantname
       User can search for Events when he/she enters a date. To all events which are on that date we calculate the
       distance and save the Events which are 1km near to the Restaurant---> methode models.Matching.getDistanceData()
       This methode render to finalRestaurant which show the restaurant the google maps and the near events
       exception because of the URL connection
     */
    public play.mvc.Result matchRestEvent(String name) throws Exception{
        List<Restaurants> r=FilterRestaurant.filterName(name);
        Restaurants rest=r.get(0);
        //Geographics g=Read_xmlRest.geo.get(0);

        String add=rest.adress;
        add=add.replaceAll("\\r\\n|\\r|\\n", "+");

        if (add.contains(" ")) {
            add = add.replace(" ", "+");
        }
       String site= "https://www.wien.gv.at/vadb/internet/AdvPrSrv.asp?Layout=rss-vadb_neu&Type=R&hmwd=d&vie_range-from="+"14.06.2016"+"&vie_range-to="+"16.06.2016";


        URL url = new URL(site);
        URLConnection conn = url.openConnection();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(conn.getInputStream());

        DOMSource domSource = new DOMSource(doc);
        TransformerFactory tfactory = TransformerFactory.newInstance();


        FileWriter myOutput = new FileWriter("public/inputfiles/events.xml");
        Transformer xform = tfactory.newTransformer();
        xform.setOutputProperty(OutputKeys.INDENT, "yes");
        xform.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        xform.transform(domSource, new StreamResult(myOutput));

        models.Read_xml.readEvents();


        for(Events x: models.Read_xml.event) {
            String st = x.getStreet();

            byte ptext[] = st.getBytes("ISO-8859-1");
            st = new String(ptext, "UTF-8");
            if (st.contains(" ")) {
                st = st.replace(" ", "+");
            }

            String site2 = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins="+add+"&destinations="+st+"&mode=walking&language=en-EN";

            System.out.println(site2);
            URL url1 = new URL(site2);
            URLConnection conn1 = url1.openConnection();

            DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory1.newDocumentBuilder();
            Document doc1 = builder1.parse(conn1.getInputStream());

            DOMSource domSource1 = new DOMSource(doc1);
            TransformerFactory tfactory1 = TransformerFactory.newInstance();


            FileWriter myOutput1 = new FileWriter("public/inputfiles/distance.xml");
            Transformer xform1 = tfactory1.newTransformer();
            xform1.setOutputProperty(OutputKeys.INDENT, "yes");
            xform1.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            xform1.transform(domSource1, new StreamResult(myOutput1));

            Float distance=models.Matching.getDistanceData();
            if(distance!=null) {
                Restaurants.restEventDistance.add(new Match(x, distance));
            }

        }
        for(Match m :  Restaurants.restEventDistance){
            System.out.println(m.e.getTitle()+" "+m.getDistance());
        }
        return  ok(finalRestaurant.render(rest,Restaurants.restEventDistance));
    }

    /*
    Matching Mathode 1 Event to all near Restaurants.Input = User clicks at a event name
    With google maps api we calculate the distance to every Restaurant and save it if its 1km away
    This Methode render to itselfe with the new matching List
     */
    public play.mvc.Result matchEventRest(String title) throws Exception{
        String name=title;
        Events e=Read_xml.getEventByname(name);
        if(e!=null) {
            String str = e.getStreet();
            byte ptext[] = str.getBytes("ISO-8859-1");
            str = new String(ptext, "UTF-8");
            if (str.contains(" ")) {
                str = str.replace(" ", "+");
            }

            List<Restaurants> r = Restaurants.find.all();
            for (Restaurants rest : r) {
                String add = rest.getAdress();
                add=add.replaceAll("\\r\\n|\\r|\\n", "+");

                if (add.contains(" ")) {
                    add = add.replace(" ", "+");
                }
                //System.out.println("ADRESSE "+add);
                String site2 = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=" +str+ "&destinations="+add+"&mode=walking&language=en-EN";

                System.out.println(site2);
                URL url1 = new URL(site2);
                URLConnection conn1 = url1.openConnection();

                DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder1 = factory1.newDocumentBuilder();
                Document doc1 = builder1.parse(conn1.getInputStream());

                DOMSource domSource1 = new DOMSource(doc1);
                TransformerFactory tfactory1 = TransformerFactory.newInstance();


                FileWriter myOutput1 = new FileWriter("public/inputfiles/distance.xml");
                Transformer xform1 = tfactory1.newTransformer();
                xform1.setOutputProperty(OutputKeys.INDENT, "yes");
                xform1.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                xform1.transform(domSource1, new StreamResult(myOutput1));

                Float distance = models.Matching.getDistanceData();
                if (distance != null) {
                    Events.EventRestDistance.add(new Match(rest, distance));
                }
            }
            for (Match m : Events.EventRestDistance) {
                System.out.println(m.r.getName() + " " + m.getDistance());
            }
        }
        if(e.getStreet().contains("/")){
        	String fullStreet = e.getStreet();
        	String [] add = fullStreet.split("/");
        	String newStreet = add[0];
        	e.setStreet(newStreet);
        }        
        System.out.println("<" + e.getStreet() + ">");
        String src1="https://www.google.com/maps/embed/v1/place?key=AIzaSyDVGIonPsFY9X03uVDndvXKmg0xOEuSyHk &q="+e.getStreet()+",1060+Wien";
        System.out.println(src1);
        return  ok(event.render(e,Events.EventRestDistance));
    }


/*
From Dropdown
 */
    public static class Options {
        public static String option;

        public static String validate() {

			return null;
        }

        public static String getOption() {
        	return option;
        }
    }
}  
