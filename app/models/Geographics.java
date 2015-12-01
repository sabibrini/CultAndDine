package models;

public class Geographics{
    private Float lat;
    private Float lnd;
    private Restaurants rest;

    public Geographics(String la,String ln, Restaurants rest) {
        this.lat=new Float(la);
        this.lnd=new Float(ln);
        this.rest=rest;

    }
    public Float getLat(){
        return this.lat;
    }
    public Float getLnd(){
        return this.lnd;
    }
    public Restaurants getRestaurant() {
        return this.rest;
    }
}
