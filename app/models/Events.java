 package models;


 import java.util.ArrayList;

 public class Events{

    public String name;
    private String link;
    private String Category;

    private String Organizer;
	private String Coordinates;
	public String Street;
	 public String lng;
	 public String lat;

	public static ArrayList<Match>EventRestDistance=new ArrayList<Match>();// very Events has its matching Array with Restaurants

    public Events(String Title,String link,String Category, String Organizer,String Coordinates,String Street){

        this.name=Title;
        this.link=link;
        this.Category=Category;
		this.Street=Street;
        this.Organizer = Organizer;
		if(Coordinates.contains(" ")){					// syntax for google maps
			this.Coordinates=Coordinates.replace(" ",",");


		}


    }
     public String getTitle() {
		return name;
	}

	public void setTitle(String title) {
		name = title;
	}

	public String getStreet(){
		return Street;
	}

	public String getCoordinates() {
		return Coordinates;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	 public void setStreet(String street) {
		 this.Street = street;
	 }

	public String getOrganizer() {
		return Organizer;

	}

	public void setOraganizer(String organizer) {
		Organizer = organizer;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	 public void setLng(String lng){
		 lng=lng;
	 }
	 public void setLat(String lat){
		 lat=lat;
	 }
	public String getLng(){
		return lng;
	}
	 public String getLat(){
		 return lat;
	 }
}
