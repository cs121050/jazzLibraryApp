package JazzLibraryClassies;
/**
*
* @author nick
*/
public class VideoDatabaseFeeder {

    private String artist_name;
    private String artist_instrument;
    private String video_link;
    private String video_name;
    private String video_duration;
    private String video_type;
    private String video_id;
    private String video_availability;

	
    public String getArtist_name() {
		return artist_name;
	}
	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}
	public String getArtist_instrument() {
		return artist_instrument;
	}
	public void setArtist_instrument(String artist_instrument) {
		this.artist_instrument = artist_instrument;
	}
	public String getVideo_link() {
		return video_link;
	}
	public void setVideo_link(String video_link) {
		this.video_link = video_link;
	}
	public String getVideo_name() {
		return video_name;
	}
	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}
	public String getVideo_duration() {
		return video_duration;
	}
	public void setVideo_duration(String video_duration) {
		this.video_duration = video_duration;
	}
	public String getVideo_type() {
		return video_type;
	}
	public void setVideo_type(String video_type) {
		this.video_type = video_type;
	}
	public String getVideo_id() {
		return video_id;
	}
	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}
	public String getVideo_availability() {
		return video_availability;
	}
	public void setVideo_availability(String video_availability) {
		this.video_availability = video_availability;
	}
	
	
	
    //output pou thelei to , CustomJTable gia na emfanisei thn string pliroforia sta pedia tou !!
	public String[] toStringArray() {
		
		String[] toStringArray={artist_name,artist_instrument,video_link,video_name,video_duration,video_type,video_id,video_availability,"browse","save","delete"};
		return  toStringArray;
	}
	
	//pali output , gia na grafei apo class , sto CustomJTable ,, thimizo oti ta teleutea 3 pedia einai ta custom koumpia!! 
	//o kodikas tous einai sto ButtonColumnTableModel package
	public Object[] toObject() {

		Object[] toObject = new Object[] {artist_name,artist_instrument,video_link,video_name,video_duration,video_type,video_id,video_availability,"browse","save","delete"};
		
		return  toObject;
		
	}
	
	//output  gia na grafei to class , sto arxeio Feeder!!
	public String toString() {

		String toString=artist_name+"#"+artist_instrument+"#"+video_link+"#"+video_name+"#"+video_duration+"#"+video_type+"#"+video_id+"#"+video_availability;
		return  toString;
		
	}
	
	
	
}