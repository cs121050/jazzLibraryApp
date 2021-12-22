package JazzLibraryClassies;

/**
*
* @author nick
*/
public class Video {


    private int video_id;
    private int duration_id;
    private String video_name;
    private String video_duration;
    private String video_link;
    private int type_id;
    private String video_uniqueId;
    private String video_availability;


    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public int getDuration_id() {
        return duration_id;
    }

    public void setDuration_id(int duration_id) {
        this.duration_id = duration_id;
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

    public String getVideo_path() {
        return video_link;
    }

    public void setVideo_path(String video_path) {
        this.video_link = video_path;
    }

	public String getLocation_id() {
		return video_uniqueId;
	}

	public void setLocation_id(String location_id) {
		this.video_uniqueId = location_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getVideo_availability() {
		return video_availability;
	}

	public void setVideo_availability(String video_availability) {
		this.video_availability = video_availability;
	}

}
