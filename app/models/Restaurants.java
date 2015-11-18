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

    public static Finder<Long, Restaurants> find = new Finder<>(Restaurants.class);

}