package models;

import play.db.ebean.Model;
import javax.persistence.*;
import play.data.validation.Constraints;

import java.lang.reflect.Array;
import java.util.*;


@Entity
public class Restaurants extends Model {

    @Id
    @GeneratedValue     // to fill more data in the database
    public Long id;
    public String name;
    public String category;
    public String priceclass;
    public String adress;
    public String phonenumber;
    public String quater;
    public String lng;
    public String lat;
    public static ArrayList<Match> restEventDistance=new ArrayList<Match>();
    public ArrayList<String> addressOfEvents=new ArrayList<String>();


    public static Finder<Long, Restaurants> find = new Finder<>(Restaurants.class);

    public void setId (Long id) {
    	this.id = id;
    }
    
    public void setName (String name) {
    	this.name = name;
    }
    
    public void setCategory (String category) {
    	this.category = category;
    }
    
    public void setPriceClass (String priceclass) {
    	this.priceclass = priceclass;
    }
    
    public void setPhoneNumber (String phonenumber) {
    	this.phonenumber = phonenumber;
    }

    public void setAdress (String adress) {
        this.adress = adress;
    }

    public void setLng(String lng){
        this.lng=lng;
    }

    public void setLat(String lat){
        this.lat=lat;
    }

    public Long getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public String getCategory () {
        return category;
    }

    public String getPriceClass () {
        return priceclass;
    }

    public String getPhoneNumber () {
        return phonenumber;
    }

    public String getAdress () {
        return adress;
    }

    public String getLng(){
        return lng;
    }
    public String getLat(){
        return lat;
    }
}