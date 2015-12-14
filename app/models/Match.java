package models;

public class Match{
    /*
    Every match has either a Restaurant and the distance from it or a Event and the distance
     */
    public Events e;
    public Float distance;
    public Restaurants r;

    public Match(Events e,Float dis){
        this.e=e;
        this.distance=dis;
    }
    public Match(Restaurants r,Float dis){
        this.r=r;
        this.distance=dis;
    }
    public Float getDistance(){
        return distance;
    }
}