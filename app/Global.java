import java.util.List;

import models.restaurants;             //import your Class
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import play.db.ebean.Model;

import play.*;
import play.mvc.*;
import java.util.List;
import play.api.libs.json.Json;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        if (restaurants.find.all().isEmpty()) {  
            List<restaurants> r=restaurants.find.all();
             JsArray a = (JsArray) parser.parse(new FileReader("c:\\kimonoData.json")); 
            for(Object o: a){
                JSONObject restaurants = (JSONObject) o;
                String restId=(String) restaurants.get("id");
                String restaurantName=(String) restaurants.get("restaurantName");
                String restaurantCata=(String) restaurants.get("text");
                String restaurantPriceclass=(String) restaurants.get("priceclass");
                String restaurantAdress=(String) restaurants.get("adress");
                String restaurantPhone=(String) restaurants.get("phoneNr");
            
                restaurants rest = new restaurants();
                rest.id=restId;
                rest.name=restaurantName;
                rest.category=restaurantCata;
                rest.priceclass=restaurantPriceclass;
                rest.adress=restaurantAdress;
                rest.phone=restaurantPhone;
                r.add(rest);
                Ebean.save(rest);
            }
            //Ebean.save((List<?>) Yaml.load("initial-data.yml"));
        }
    }
}
