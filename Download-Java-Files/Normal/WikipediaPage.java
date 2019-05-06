package wikipedia.model;


/**
 * https://github.com/martinffx/wikipedia-data-parser
 * 
 */
public class WikipediaPage {


	private String id;
	private String title;
    private boolean isRedirecting = false;

    public WikipediaPage() {
        
    }
    
    public void setRedirecting(boolean redirecting) {
    	this.isRedirecting = redirecting;
    }
    
    public boolean isRedirecting() {
    	return isRedirecting;
    }
    
    public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
    public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
}