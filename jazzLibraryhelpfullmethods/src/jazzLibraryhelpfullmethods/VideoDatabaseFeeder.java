package jazzLibraryhelpfullmethods;

public class VideoDatabaseFeeder {

    private String artist_name;
    private String artist_instrument;
    private String video_link;
    private String video_name;
    private String video_duration;
    private String video_type;
    private String video_id;
	
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


	public String[] toStringArray() {
		
		String[] toStringArray={artist_name,artist_instrument,video_link,video_name,video_duration,video_type,video_id,"save","delete"};
		return  toStringArray;
	}
	
	public Object[] toObject() {

		Object[] toObject = new Object[] {artist_name,artist_instrument,video_link,video_name,video_duration,video_type,video_id,"save","delete"};
		
		return  toObject;
		
	}
	
	
	public String toString() {

		String toString=artist_name+"#"+artist_instrument+"#"+video_link+"#"+video_name+"#"+video_duration+"#"+video_type+"#"+video_id;
		return  toString;
		
	}
	
	
}