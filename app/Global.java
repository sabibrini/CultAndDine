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


import com.avaje.ebean.Ebean;
//asdfsdfsd
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        if (Restaurants.find.all().isEmpty()) {  
            List<Restaurants> r=Restaurants.find.all();

            //Ebean.save((List<?>) Yaml.load("initial-data.yml"));
        }
    }
}
