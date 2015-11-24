import java.io.BufferedReader;
import java.util.List;


//import com.google.gson.stream.*;
import models.Restaurants;             //import your Class
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import java.io.*;


import play.db.ebean.Model;

import play.*;
import play.mvc.*;
import java.util.List;
import models.*;

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


import com.avaje.ebean.Ebean;
import javax.persistence.OptimisticLockException;


import com.avaje.ebean.Ebean;
//asdfsdfsd
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app)  {
        if (Restaurants.find.all().isEmpty()) {
            try {
                String site = "https://www.kimonolabs.com/api/6z5dt1mo?apikey=mBBMysBxfIxQJbVAINmoonSlIhen0foo&authorization=n3G3laDyjIYIzXw3gFVgTc7k56mcaiy5";
                URL endpoint = new URL(site);
                String endpoint_content = IOUtils.toString(endpoint, "UTF-8");


                JsonObject fson = Json.parse(endpoint_content).asObject();
                JsonArray items = fson.get("results").asObject().get("collection1").asArray();
                String name;
                String text = null;
                String quater1;
                String price;
                String phoneNr;
                String adress1;
                Integer index1;

                for (JsonValue item : items) {
                    Restaurants restau = new Restaurants();
                    name = item.asObject().getString("restaurantName", "Unknown Item");
                    JsonValue cata = item.asObject().get("category");
                    if (cata.isArray()) {
                        for (JsonValue c : cata.asArray()) {
                            text = c.asObject().getString("text", "Unknown Item");
                        }
                    } else {
                        text = cata.asObject().getString("text", "Unknown Item");
                    }
                    price = item.asObject().getString("priceclass", "Unknown Item");
                    quater1 = item.asObject().getString("quarter", "Unknown Item");
                    adress1 = item.asObject().getString("adress", "Unknown Item");
                    phoneNr = item.asObject().getString("phoneNr", "Unknown Item");
                    index1 = item.asObject().getInt("index", -1);

                    restau.id = new Long(index1);
                    restau.name = name;
                    restau.category = text;
                    restau.priceclass = price;
                    restau.adress = adress1;
                    restau.phonenumber = phoneNr;
                    restau.quater = quater1;

                    try {
                        restau.save();
                    } catch (OptimisticLockException e) {
                        throw new RuntimeException("cannot save value");
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }


    }
 }
