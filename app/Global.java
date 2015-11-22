import java.io.BufferedReader;
import java.util.List;

import com.google.gson.stream.*;
import models.Restaurants;             //import your Class
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import java.io.*;


import play.db.ebean.Model;

import play.*;
import play.mvc.*;
import java.util.List;
/*import play.api.libs.json.Json;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;*/

//import gson.stream.*;

//import org.eclipse.persistence.jaxb.JAXBContextFactory;


import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        if (Restaurants.find.all().isEmpty()) {  
            List<Restaurants> r=Restaurants.find.all();

            String jsonFile ="/CultAndDine/public/inputfiles/restaurantAPI.json";
            
            BufferedReader br = null;
            
            try {
            	br = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile), "UTF-8"));
            } catch (FileNotFoundException fnfe) {
            	System.out.println("Could nort open input file" + jsonFile + ", quitting.");
            } catch (UnsupportedEncodingException uee) {
            	System.out.println("Problems with encoding");
            	uee.printStackTrace();
            }
            
            JsonReader reader = new JsonReader(br);
            reader.setLenient(true);
            
            reader.beginArray();
            while(reader.hasNext()) {
            	Restaurants rest = new Restaurants();
            	reader.beginObject();
            	while (reader.hasNext()) {
            		String current = reader.nextName();
            		if (current.equals("id") && reader.peek() != JsonToken.NULL) {
            			rest.id(reader.nextLong());
            		}
            		else if (current.equals("restaurantName") && reader.peek() != JsonToken.NULL) {
            			rest.name(reader.nextString());
            		}
            		else if (current.equals("text") && reader.peek() != JsonToken.NULL) {
            			rest.category(reader.nextString());
            		}
            		else if (current.equals("priceclass") && reader.peek() != JsonToken.NULL) {
            			rest.priceclass(reader.nextString());
            		}
            		else if (current.equals("adress") && reader.peek() != JsonToken.NULL) {
            			rest.adress(reader.nextString());
            		}
            		else if (current.equals("phoneNr") && reader.peek() != JsonToken.NULL) {
            			rest.phonenumber(reader.nextString());
            		}
            		else {
            			reader.skipValue();
            		}
            	}
            	reader.endObject();
            	r.add(rest);
            }
            reader.endArray();
            reader.close();
            
            try {
            	br.close();
            }
            catch (IOException ioe) {
            	ioe.printStackTrace();
            }
            
            
            /*JAXBContext jc = JAXBContext.newInstance(DatabaseInventory.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            File jsonObj = new File(jsonFile);

            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
            DatabaseInventory activity = (DatabaseInventory) unmarshaller.unmarshal(jsonObj);
            Marshaller xmlM = jc.createMarshaller();
            xmlM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            String xmlFile = "json2xml/samples/convertedFile.xml";
            FileOutputStream fos = new FileOutputStream(new File(workspace + xmlFile));
            xmlM.marshal(activity, fos);
            fos.close();*/



            /*JsArray a = (JsArray) parser.parse(new FileReader("/CultAndDine/public/inputfiles/restaurantAPI.json"));
            for(Object o: a){
                JSONObject restaurants = (JSONObject) o;
                String restId=(String) restaurants.get("id");
                String restaurantName=(String) restaurants.get("restaurantName");
                String restaurantCata=(String) restaurants.get("text");
                String restaurantPriceclass=(String) restaurants.get("priceclass");
                String restaurantAdress=(String) restaurants.get("adress");
                String restaurantPhone=(String) restaurants.get("phoneNr");
            
                Restaurants rest = new Restaurants();
                rest.id=restId;
                rest.name=restaurantName;
                rest.category=restaurantCata;
                rest.priceclass=restaurantPriceclass;
                rest.adress=restaurantAdress;
                rest.phone=restaurantPhone;
                r.add(rest);
                Ebean.save(rest);
            }*/
            //Ebean.save((List<?>) Yaml.load("initial-data.yml"));
        }
    }
}
