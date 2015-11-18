import java.util.List;

import models.Restaurants;             //import your Class
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import play.db.ebean.Model;

import play.*;
import play.mvc.*;
import java.util.List;
import play.api.libs.json.Json;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

import org.eclipse.persistence.jaxb.JAXBContextFactory;


import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) throws Exception {
        if (Restaurants.find.all().isEmpty()) {  
            List<Restaurants> r=Restaurants.find.all();

            String jsonFile ="/CultAndDine/public/inputfiles/restaurantAPI.json";
            JAXBContext jc = JAXBContext.newInstance(DatabaseInventory.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            File jsonObj = new File(jsonFile);

            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
            DatabaseInventory activity = (DatabaseInventory) unmarshaller.unmarshal(jsonObj);
            Marshaller xmlM = jc.createMarshaller();
            xmlM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            String xmlFile = "json2xml/samples/convertedFile.xml";
            FileOutputStream fos = new FileOutputStream(new File(workspace + xmlFile));
            xmlM.marshal(activity, fos);
            fos.close();



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
