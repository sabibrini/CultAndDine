package models;
import java.util.*;
import java.io.*;
import play.db.ebean.*;
import com.avaje.ebean.Ebean;
import javax.persistence.*;

import java.util.*;

public class FilterRestaurant{

    /*
    Filter methods for the database Restaurants !!
     */

    public static List<Restaurants> filterQuarter(String searchingQuarter){
        return Restaurants.find.where().ilike("quater",searchingQuarter).findList();
    }
    public static List<Restaurants> filterCategory(String cat){
        return Restaurants.find.where().ilike("category",cat).findList();
    }
    public static List<Restaurants> filterName(String name){
        return Restaurants.find.where().ilike("name",name).findList();
    }

    public static List<Restaurants> filterCategoryList(List<Restaurants> r2,String category){
        List<Restaurants> output=new ArrayList<Restaurants>();
            for(Restaurants x: r2){
                if(x.getCategory().equals(category)){
                    output.add(x);
                }
            }
        return output;
    }
}