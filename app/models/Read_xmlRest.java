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


public class Read_xmlRest {

    public static void readRestaurants() throws Exception{
        /*String site="https://www.kimonolabs.com/api/6z5dt1mo?apikey=mBBMysBxfIxQJbVAINmoonSlIhen0foo&authorization=n3G3laDyjIYIzXw3gFVgTc7k56mcaiy5";
        URL endpoint =new URL(site);
        String endpoint_content = IOUtils.toString(endpoint,"UTF-8");


        JsonObject fson= Json.parse(endpoint_content).asObject();
        JsonArray items=fson.get("results").asObject().get("collection1").asArray();
        String name;
        String text=null;
        String quater1;
        String price;
        String phoneNr;
        String adress1;
        Integer index1;
        Restaurants restau=new Restaurants();
        for (JsonValue item : items) {
            name = item.asObject().getString("restaurantName", "Unknown Item");
            JsonValue cata=item.asObject().get("category");
            if (cata.isArray()) {
                for(JsonValue c: cata.asArray()){
                    text=c.asObject().getString("text", "Unknown Item");
                }
            }
            else {
                text=cata.asObject().getString("text", "Unknown Item");
            }
            price = item.asObject().getString("priceclass", "Unknown Item");
            quater1=item.asObject().getString("quarter", "Unknown Item");
            adress1=item.asObject().getString("adress", "Unknown Item");
            phoneNr=item.asObject().getString("phoneNr", "Unknown Item");
            index1=item.asObject().getInt("index", -1);

            restau.id= new Long(index1);
            restau.name=name;
            restau.category=text;
            restau.priceclass=price;
            restau.adress=adress1;
            restau.phonenumber=phoneNr;
            restau.quater=quater1;

            try {
                Ebean.save(restau);
            } catch (OptimisticLockException e){
                throw new RuntimeException("cannot save value");
            }
        }

*/
    }
}

