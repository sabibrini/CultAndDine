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

    public play.mvc.Result start() {
        return ok(start.render(new Options()));
    }

//    public play.mvc.Result selectedRestaurant() {
//        return ok(restaurant.render());
//    }

    //add according functionality to give information about a certain selected event
   public play.mvc.Result selectedEvent(String title) {
       Events e=Read_xml.getEventByname(title);
	   return ok(event.render(e));
   }

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
    
    public play.mvc.Result restaurants() throws Exception{

        models.Read_xmlRest.readRestaurants();
        List<Restaurants> rest=Restaurants.find.all();

        return ok(selectedRestaurants.render(rest));
    }

    public play.mvc.Result filterCategor(){
        List<Restaurants> r=FilterRestaurant.filterCategory("Weinbar");
        return ok(selectedRestaurants.render(r));
    }

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
    
    public play.mvc.Result goHome() {
    	return ok(start.render(new Options()));
    }

    public play.mvc.Result calcLongLad(String name)throws Exception{
        // lies das Restaurant das mit dem Button gewählt wurde aus und speicher in String
    	//name is sent when clicking on the hyperlink, it should be filtered (when its not a nullpointer)
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

        return  ok(restaurant.render(rest));
    }
    public play.mvc.Result matchRestEvent(String name) throws Exception{
        List<Restaurants> r=FilterRestaurant.filterName(name);
        Restaurants rest=r.get(0);
        //Geographics g=Read_xmlRest.geo.get(0);

        String add=rest.adress;
        add=add.replaceAll("\\r\\n|\\r|\\n", "+");
        //byte ptext1[] = add.getBytes("ISO-8859-1");
        //add = new String(ptext1, "UTF-8");
        if (add.contains(" ")) {
            add = add.replace(" ", "+");
        }
       /* DynamicForm requestData = form().bindFromRequest();
        String startdate=requestData.get("dateStart");
        String end= requestData.get("dateEnd");
*/
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

        //String la=Float.toString(g.getLat());
        //String ln=Float.toString(g.getLnd());

        //System.out.println(la+" ehwriohweoiehoihrgpihreghreähgiperhgüeqhü "+ln);
        for(Events x: models.Read_xml.event) {
            String st = x.getStreet();

            byte ptext[] = st.getBytes("ISO-8859-1");
            st = new String(ptext, "UTF-8");
            if (st.contains(" ")) {
                st = st.replace(" ", "+");
            }
            // filter bei TOP 5 z.b gibs einen fehler Tops wegfiltern !!!!

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
        //restEventDistance liste an finalRestaurant �bergeben und auslesen
        return  ok(finalRestaurant.render(rest,Restaurants.restEventDistance));
    }

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
            //System.out.println("ADRESSE "+str);
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
    return  ok(event.render(e));


}


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
