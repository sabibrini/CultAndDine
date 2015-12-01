 package models;


public class Events{
    private String Title;
   // private String Description;
    private String Category;
    private String Organizer;
    
    public Events(String Title, String Category,/* String Description,*/ String Organizer){
        this.Title=Title;
       // this.Description=Description;
        this.Category=Category;
        this.Organizer = Organizer;
    }
     public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}


//	public String getDescription() {
//		return Description;
//	}
//
//	public void setDescription(String description) {
//		Description = description;
//	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}
	
	public String getOrganizer() {
		return Organizer;
	}
	
	public void setOrganizer(String organizer) {
		Organizer = organizer;
	}

}
