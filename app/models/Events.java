 package models;


 import java.util.ArrayList;

 public class Events{

    private String Title;
    private String link;
    private String Category;

    private String Organizer;
	private String Coordinates;
	private String Street;
	public static ArrayList<Match>EventRestDistance=new ArrayList<Match>();// very Events has its matching Array with Restaurants

    public Events(String Title,String link,String Category, String Organizer,String Coordinates,String Street){

        this.Title=Title;
        this.link=link;
        this.Category=Category;
		this.Street=Street;
        this.Organizer = Organizer;
		if(Coordinates.contains(" ")){					// syntax for google maps
			this.Coordinates=Coordinates.replace(" ",",");
			

		}


    }
     public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
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

}
