 package models;


public class Events{
    private String Title;
    private String link;
    private String Description;
    private String Category;
    
    public Events(String Title,String link,String Description,String Category){
        this.Title=Title;
        this.link=link;
        this.Description=Description;
        this.Category=Category;
    }
     public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

}
