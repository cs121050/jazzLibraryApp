public class Artist {

    private int artist_id;
    private String artist_name;
    private String artist_surname;
    private int instrument_id;


    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }
    
    public String getArtist_surname() {
        return artist_surname;
    }

    public void setArtist_surname(String artist_surname) {
        this.artist_surname = artist_surname;
    }

	public int getInstrument_id() {
		return instrument_id;
	}

	public void setInstrument_id(int instrument_id) {
		this.instrument_id = instrument_id;
	}
}