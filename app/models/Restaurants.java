package models;

import play.db.ebean.Model;
import javax.persistence.*;
import play.data.validation.Constraints;

@Entity
public class Restaurants extends Model {

    @Id
    public Long id;
    public String name;
    public String category;
    public String priceclass;
    public String adress;
    public String phonenumber;

    public Restaurants(Integer index,String restaurantName,String text,String priceclass){
        this.id=new Long(index);
        this.name=restaurantName;
        this.category=text;
        this.priceclass=priceclass;
    }

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



}