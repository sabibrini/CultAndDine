package models;

/*
Every Restaurant has it own geographics add for Events !!!
 */
public class Geographics{
    private String lat;
    private String lnd;
    private Restaurants rest;

    public Geographics(String la,String ln, Restaurants rest) {
        this.lat=la;
        this.lnd=ln;
        this.rest=rest;

    }
    public String getLat(){
        return this.lat;
    }
    public String getLnd(){
        return this.lnd;
    }
    public Restaurants getRestaurant() {
        return this.rest;
    }
}
