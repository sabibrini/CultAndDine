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

import java.util.LinkedList;
import java.util.List;
import models.*;

public class Application extends Controller {

    public play.mvc.Result start() {
        return ok(start.render());
    }

    public play.mvc.Result selectedRestaurant() {
        return ok(restaurant.render());
    }

    public play.mvc.Result selectedEvent() {
        return ok(event.render());
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
        DynamicForm requestData = form().bindFromRequest();
    	String userinput = requestData.get("selection");
    	System.out.println(userinput);
        List<Restaurants> r=FilterRestaurant.filterQuarter("Mariahilf");
        return ok(selectedRestaurants.render(r));
    }
    
    public play.mvc.Result goHome() {
    	return ok(start.render());
    }

    public play.mvc.Result calcLongLad()throws Exception{
        // lies das Restaurant das mit dem Button gewählt wurde aus und speicher in String
        List<Restaurants> r=FilterRestaurant.filterName("Martin");
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

        return  ok(start.render());
    }
    public play.mvc.Result matchRestEvent() throws Exception{
        Geographics g=Read_xmlRest.geo.get(0);

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

        for(Events x: Read_xml.event) {
            // filter von lat und lnd!!!!!

           // String site = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=" g.getLat(),g.getLnd()"&destinations="San Francisco"&mode=walking&language=en-EN";
        }
        return  ok(start.render());
    }
        
}
