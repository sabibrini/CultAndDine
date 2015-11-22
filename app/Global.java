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

			try {
            reader.beginArray();
            while(reader.hasNext()) {
            	Restaurants rest = new Restaurants();
            	reader.beginObject();
            	while (reader.hasNext()) {
            		String current = reader.nextName();
            		if (current.equals("id") && reader.peek() != JsonToken.NULL) {
            			rest.setId(reader.nextLong());
            		}
            		else if (current.equals("restaurantName") && reader.peek() != JsonToken.NULL) {
            			rest.setName(reader.nextString());
            		}
            		else if (current.equals("text") && reader.peek() != JsonToken.NULL) {
            			rest.setCategory(reader.nextString());
            		}
            		else if (current.equals("priceclass") && reader.peek() != JsonToken.NULL) {
            			rest.setPriceClass(reader.nextString());
            		}
            		else if (current.equals("adress") && reader.peek() != JsonToken.NULL) {
            			rest.setAdress(reader.nextString());
            		}
            		else if (current.equals("phoneNr") && reader.peek() != JsonToken.NULL) {
            			rest.setPhoneNumber(reader.nextString());
            		}
            		else {
            			reader.skipValue();
            		}
            	}
            	reader.endObject();
            	r.add(rest);
                Ebean.save(rest);
            }
            reader.endArray();
            reader.close();
			}
			catch (Exception e){
				e.printStackTrace();
			}
            
            try {
            	br.close();
            }
            catch (IOException ioe) {
            	ioe.printStackTrace();
            }
            
            //Ebean.save((List<?>) Yaml.load("initial-data.yml"));
        }
    }
}
