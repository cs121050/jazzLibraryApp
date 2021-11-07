import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;




public class JazzLibraryDAO {    
    
    
	
	
    
    public static Connection getConnection(){
        Connection conn = null; 
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://XM;database=jazzLibraryDB;","sa","admin");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("DB Connectrion exception " + ex);
        }
        
        return conn;
    }
    
    
//    try {
//        Class.forName("net.sourceforge.jtds.jdbc.Driver");
//       conn = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/jazzLibraryDB","nikos","admin");
//   } catch (ClassNotFoundException | SQLException ex) {
//       System.out.println("DB Connectrion exception " + ex);
//   }
    
    
    



    public static int getInstrumentId(String instrument_name) {
        
		Instrument instrument = new Instrument();         
        Connection con = JazzLibraryDAO.getConnection();  
        try {
            PreparedStatement ps = con.prepareStatement("select instrument_id   "
									            		+ "from instrument  "
									            		+ "where instrument_name=?");
            ps.setString(1,instrument_name); 
			ResultSet rs=ps.executeQuery(); 
        	
            
        	rs.next();
        	instrument.setInstrument_id(rs.getInt(1));               
              
            con.close(); 
        } catch (SQLException ex) {

        }           

        return instrument.getInstrument_id();            
    }





	public static int getDurationId(String duration_name) {
	
		
		Duration duration = new Duration();         
	    Connection con = JazzLibraryDAO.getConnection();  
	    try {
	        PreparedStatement ps = con.prepareStatement("select duration_id \r\n"
									            		+ "from duration \r\n"
									            		+ "where duration_name=?");
			ps.setString(1,duration_name); 
			ResultSet rs=ps.executeQuery(); 
			
			rs.next();
	        duration.setDuration_id(rs.getInt(1));               
	
	            
	        con.close(); 
	    } catch (SQLException ex) {
	
	    }
	    return duration.getDuration_id();            
	}
	
	
	
	public static int getTypeId(String type_name) {
	
		Type type = new Type();         
	    Connection con = JazzLibraryDAO.getConnection();  
	    try {
	        PreparedStatement ps = con.prepareStatement("select type_id \r\n"
									            		+ "from type \r\n"
									            		+ "where type_name=?");
			ps.setString(1,type_name); 
			ResultSet rs=ps.executeQuery(); 
			
			rs.next() ;
	        type.setType_id(rs.getInt(1));               
	                         
	     
				
	        con.close(); 
	    } catch (SQLException ex) {
	
	    }
	    return type.getType_id();            
	}
	
	
	
	
	public static int getArtistId(String artist_name,String artist_surname) {
	
		Artist artist = new Artist();         
	    Connection con = JazzLibraryDAO.getConnection();  
	    try {
	        PreparedStatement ps = con.prepareStatement("select artist_id \r\n"
														+ "from artist \r\n"
														+ "where artist_name=? and artist_surname=?");
			ps.setString(1,artist_name); 
			ps.setString(2,artist_surname); 
			ResultSet rs=ps.executeQuery(); 
			
			rs.next();
	        artist.setArtist_id(rs.getInt(1));               
	
				
	        con.close(); 
	    } catch (SQLException ex) {
	
	    }
	    return artist.getArtist_id();            
	}
	
	
	
	
	
	public static int getVideoId(String video_path) {
	
		Video video = new Video();         
	    Connection con = JazzLibraryDAO.getConnection();  
	    try {
	        PreparedStatement ps = con.prepareStatement("select video_id \r\n"
														+ "from video \r\n"
														+ "where video_path=?");
			ps.setString(1,video_path); 
			ResultSet rs=ps.executeQuery(); 
			
			rs.next();
			video.setVideo_id(rs.getInt(1));               
	        
				
	        con.close(); 
	    } catch (SQLException ex) {
	
	    }
	    return video.getVideo_id();            
	}



    
    
    public static int createArtist(String artist_name,String artist_surname,int instrument_id) {  
        
        int status = 0;
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Artist (artist_name,artist_surname,instrument_id) VALUES (?,?,?);");
            ps.setString(1,artist_name); 
            ps.setString(2,artist_surname); 
            ps.setInt(3,instrument_id); 

            
            status=ps.executeUpdate();                
            con.close();      
        } catch (SQLException ex) {            
        }
        return status;
    }
    
    
    public static List<Artist> retriveAllArtist() {                 //!!!!!!!!!!!!!!!!

        List<Artist> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();  
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Artist");
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){                       
                Artist artist = new Artist();         
                artist.setArtist_id(rs.getInt(1));               
                artist.setArtist_name(rs.getString(2));  
                artist.setArtist_surname(rs.getString(3));  
                list.add(artist);               
            }  
            con.close(); 
        } catch (SQLException ex) {

        }
        return list;            
    }
    
    
    
    
                                                                    public static Artist retriveArtist(String artist_name) throws InterruptedException { 

                                                                        Artist artist = new Artist();
                                                                        Connection con = JazzLibraryDAO.getConnection();  
                                                                        try {
                                                                            PreparedStatement ps = con.prepareStatement("SELECT * FROM Artist WHERE artist_name=?");

                                                                            ps.setString(1,artist_name);  
                                                                            ResultSet rs=ps.executeQuery();  

                                                                            artist.setArtist_id(rs.getInt(1));               
                                                                            artist.setArtist_name(rs.getString(2));  

                                                                            con.close(); 
                                                                        } catch (SQLException ex) {

                                                                        }
                                                                        return artist;       
                                                                    }
    
                                                                    public static Artist retriveArtist(int artist_id) { //Overfloat Method

                                                                        Artist artist = new Artist();
                                                                        Connection con = JazzLibraryDAO.getConnection();  
                                                                        try {
                                                                            PreparedStatement ps = con.prepareStatement("SELECT * FROM Artist WHERE artist_id=?");
                                                                            ps.setInt(1,artist_id);  
                                                                            ResultSet rs=ps.executeQuery();  

                                                                            artist.setArtist_id(rs.getInt(1));               
                                                                            artist.setArtist_name(rs.getString(2));  

                                                                            con.close(); 
                                                                        } catch (SQLException ex) {

                                                                        }
                                                                        return artist;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
                                                                    }


    
    
    
    
    
    
    
    
    
    public static int createInstrument(String instrument_name) {  
        
        int status = 0;
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO instrument (instrument_name) VALUES (?);");
            ps.setString(1,instrument_name); 
            
            status=ps.executeUpdate();                
            con.close();      
        } catch (SQLException ex) {            
        }
        return status;
    }
    
    
    public static List<Instrument> retriveAllInstrument() {                 //!!!!!!!!!!!!!!!!

            List<Instrument> list = new ArrayList<>();
            Connection con = JazzLibraryDAO.getConnection();  
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Instrument");
                ResultSet rs=ps.executeQuery();  
                while(rs.next()){                       
                    Instrument instrument = new Instrument();         
                    instrument.setInstrument_id(rs.getInt(1));               
                    instrument.setInstrument_name(rs.getString(2));  
                    list.add(instrument);               
                }  
                con.close(); 
            } catch (SQLException ex) {

            }
            return list;            
        }
    
    
                                                                        public static Instrument retriveInstrument(String instrument_name) { 

                                                                            Instrument instument = new Instrument();
                                                                            Connection con = JazzLibraryDAO.getConnection();  
                                                                            try {
                                                                                PreparedStatement ps = con.prepareStatement("SELECT * FROM instrument WHERE instrument_name=?");
                                                                                ps.setString(1,instrument_name);  
                                                                                ResultSet rs=ps.executeQuery();  

                                                                                instument.setInstrument_id(rs.getInt(1));               
                                                                                instument.setInstrument_name(rs.getString(2));  

                                                                                con.close(); 
                                                                            } catch (SQLException ex) {

                                                                            }
                                                                            return instument;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
                                                                        }
    
                                                                        public static Instrument retriveInstrument(int instrument_id) { 

                                                                            Instrument instument = new Instrument();
                                                                            Connection con = JazzLibraryDAO.getConnection();  
                                                                            try {
                                                                                PreparedStatement ps = con.prepareStatement("SELECT * FROM instrument WHERE instrument_id=?");
                                                                                ps.setInt(1,instrument_id);  
                                                                                ResultSet rs=ps.executeQuery();  

                                                                                instument.setInstrument_id(rs.getInt(1));               
                                                                                instument.setInstrument_name(rs.getString(2));  

                                                                                con.close(); 
                                                                            } catch (SQLException ex) {

                                                                            }
                                                                            return instument;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
                                                                        }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static int createArtistPlaysInstrument(int artist_id,int instrument_id) {  
        
        int status = 0;
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO artistplaysinstrument (artist_id,instrument_id) VALUES (?,?);");
            ps.setInt(1,artist_id); 
            ps.setInt(2,instrument_id); 
            
            status=ps.executeUpdate();                
            con.close();      
        } catch (SQLException ex) {            
        }
        return status;
    }
    
    
    
    
    
    
    public static int createVideoContainsArtist(int video_id,int artist_id) {  
        
        int status = 0;
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO videocontainsartist (video_id,artist_id) VALUES (?,?);");
            ps.setInt(1,video_id); 
            ps.setInt(2,artist_id); 
            
            status=ps.executeUpdate();                
            con.close();      
        } catch (SQLException ex) {            
        }
        return status;
    }
    
    
    
    public static List<VideoContainsArtist> retriveAllVideoContainsArtist() {                 //!!!!!!!!!!!!!!!!

            List<VideoContainsArtist> list = new ArrayList<>();
            Connection con = JazzLibraryDAO.getConnection();  
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Artist");
                ResultSet rs=ps.executeQuery();  
                while(rs.next()){                       
                    VideoContainsArtist videoContainsArtist = new VideoContainsArtist();         
                    videoContainsArtist.setVideo_id(rs.getInt(1));               
                    videoContainsArtist.setArtist_id(rs.getInt(2));  
                    list.add(videoContainsArtist);               
                }  
                con.close(); 
            } catch (SQLException ex) {

            }
            return list;            
        }
    
    
    
    
                                                                                    public static VideoContainsArtist retriveVideoContainsArtist_byArtistId(int artist_id) { 

                                                                                        VideoContainsArtist videocontainsartist = new VideoContainsArtist();
                                                                                        Connection con = JazzLibraryDAO.getConnection();  
                                                                                        try {
                                                                                            PreparedStatement ps = con.prepareStatement("SELECT * FROM videocontainsartist WHERE artist_id=?");
                                                                                            ps.setInt(1,artist_id);  
                                                                                            ResultSet rs=ps.executeQuery();  

                                                                                            videocontainsartist.setVideo_id(rs.getInt(1));               
                                                                                            videocontainsartist.setArtist_id(rs.getInt(2));  

                                                                                            con.close(); 
                                                                                        } catch (SQLException ex) {

                                                                                        }
                                                                                        return videocontainsartist;     
                                                                                    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static int createVideoIsType(int video_id,int type_id) {  
        
        int status = 0;
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO videoistype (video_id,type_id) VALUES (?,?);");
            ps.setInt(1,video_id); 
            ps.setInt(2,type_id); 
            
            status=ps.executeUpdate();                
            con.close();      
        } catch (SQLException ex) {            
        }
        return status;
    }
    
    
    
    
    
    public static int createType(int type_id,String type_name) {  
        
        int status = 0;
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO type (type_id,type_name) VALUES (?,?);");
            ps.setInt(1,type_id); 
            ps.setString(2,type_name); 
            
            status=ps.executeUpdate();                
            con.close();      
        } catch (SQLException ex) {            
        }
        return status;
    }
    
    
    
    public static List<Type> retriveAllType() {                 //!!!!!!!!!!!!!!!!

            List<Type> list = new ArrayList<>();
            Connection con = JazzLibraryDAO.getConnection();  
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Type");
                ResultSet rs=ps.executeQuery();  
                while(rs.next()){                       
                    Type type = new Type();         
                    type.setType_id(rs.getInt(1));               
                    type.setType_name(rs.getString(2));  
                    list.add(type);               
                }  
                con.close(); 
            } catch (SQLException ex) {

            }
            return list;            
        }
    
    
    
    
    
    
                                                                        public static Type retriveType(int type_id) { 

                                                                            Type type = new Type();
                                                                            Connection con = JazzLibraryDAO.getConnection();  
                                                                            try {
                                                                                PreparedStatement ps = con.prepareStatement("SELECT * FROM type WHERE type_id=?");
                                                                                ps.setInt(1,type_id);  
                                                                                ResultSet rs=ps.executeQuery();  

                                                                                type.setType_id(rs.getInt(1));               
                                                                                type.setType_name(rs.getString(2));  

                                                                                con.close(); 
                                                                            } catch (SQLException ex) {

                                                                            }
                                                                            return type;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
                                                                        }
    
                                                                                    //edo h overload method den pianei ;p !! epidh exoun int inputkai ta 2
                                                                                    public static Type retriveType(String type_name) { 

                                                                                        Type type = new Type();
                                                                                        Connection con = JazzLibraryDAO.getConnection();  
                                                                                        try {
                                                                                            PreparedStatement ps = con.prepareStatement("SELECT * FROM type WHERE type_name=?");
                                                                                            ps.setString(1,type_name);  
                                                                                            ResultSet rs=ps.executeQuery();  

                                                                                            type.setType_id(rs.getInt(1));               
                                                                                            type.setType_name(rs.getString(2));  

                                                                                            con.close(); 
                                                                                        } catch (SQLException ex) {

                                                                                        }
                                                                                        return type;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
                                                                                    }
    
    
    
    
    
    
    
    
    
    
    public static int createDuration(int duration_id,String duration_name) {  
        
        int status = 0;
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO duration (duration_id,duration_name) VALUES (?,?);");
            ps.setInt(1,duration_id); 
            ps.setString(2,duration_name); 
            
            status=ps.executeUpdate();                
            con.close();      
        } catch (SQLException ex) {            
        }
        return status;
    }
    
    public static List<Duration> retriveAllDuration() {                 //!!!!!!!!!!!!!!!!

            List<Duration> list = new ArrayList<>();
            Connection con = JazzLibraryDAO.getConnection();  
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Duration");
                ResultSet rs=ps.executeQuery();  
                while(rs.next()){                       
                    Duration duration = new Duration();         
                    duration.setDuration_id(rs.getInt(1));               
                    duration.setDuration_name(rs.getString(2));  
                    list.add(duration);               
                }  
                con.close(); 
            } catch (SQLException ex) {

            }
            return list;            
        }
    
    
    
    
    
                                                                    public static Duration retriveDuration(int duration_id) { 

                                                                        Duration duration = new Duration();
                                                                        Connection con = JazzLibraryDAO.getConnection();  
                                                                        try {
                                                                            PreparedStatement ps = con.prepareStatement("SELECT * FROM duration WHERE duration_id=?");
                                                                            ps.setInt(1,duration_id);  
                                                                            ResultSet rs=ps.executeQuery();  

                                                                            duration.setDuration_id(rs.getInt(1));               
                                                                            duration.setDuration_name(rs.getString(2));  

                                                                            con.close(); 
                                                                        } catch (SQLException ex) {

                                                                        }
                                                                        return duration;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
                                                                    }

                                                                    //edo h overload method den pianei ;p !! epidh exoun int inputkai ta 2
                                                                    public static Duration retriveDuration(String duration_name) { 

                                                                        Duration duration = new Duration();
                                                                        Connection con = JazzLibraryDAO.getConnection();  
                                                                        try {
                                                                            PreparedStatement ps = con.prepareStatement("SELECT * FROM duration WHERE duration_name=?");
                                                                            ps.setString(1,duration_name);  
                                                                            ResultSet rs=ps.executeQuery();  

                                                                            duration.setDuration_id(rs.getInt(1));               
                                                                            duration.setDuration_name(rs.getString(2));  

                                                                            con.close(); 
                                                                        } catch (SQLException ex) {

                                                                        }
                                                                        return duration;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static int createVideo(String video_name,String video_duration,int duration_id,String video_path,int type_id,String location_id) {  
        
        int status = 0;
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO video (video_name,video_duration,duration_id,video_path,type_id,location_id) VALUES (?,?,?,?,?,?);");
            ps.setString(1,video_name); 
            ps.setString(2,video_duration); 
            ps.setInt(3,duration_id); 
            ps.setString(4,video_path); 
            ps.setInt(5,type_id); 
            ps.setString(6,location_id); 
            
            status=ps.executeUpdate();                
            con.close();      
        } catch (SQLException ex) {            
        }
        return status;
    }
    
    public static List<Video> retriveAllVideo() {                 //!!!!!!!!!!!!!!!!

            List<Video> list = new ArrayList<>();
            Connection con = JazzLibraryDAO.getConnection();  
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM video");
                ResultSet rs=ps.executeQuery();  
                while(rs.next()){                       
                    Video video = new Video();         
                    video.setVideo_id(rs.getInt(1));               
                    video.setVideo_name(rs.getString(2)); 
                    video.setVideo_duration(rs.getString(3));               
                    video.setDuration_id(rs.getInt(4)); 
                    video.setVideo_path(rs.getString(5));               
                    list.add(video);               
                }  
                con.close(); 
            } catch (SQLException ex) {

            }
            return list;            
        }
    
    
                                                                            public static Video retriveVideo(String video_name) { 

                                                                                Video video = new Video();
                                                                                Connection con = JazzLibraryDAO.getConnection();  
                                                                                try {
                                                                                    PreparedStatement ps = con.prepareStatement("SELECT * FROM video WHERE video_name=?");
                                                                                    ps.setString(1,video_name);  
                                                                                    ResultSet rs=ps.executeQuery();  

                                                                                    video.setVideo_id(rs.getInt(1));               
                                                                                    video.setVideo_name(rs.getString(2));  

                                                                                    con.close(); 
                                                                                } catch (SQLException ex) {

                                                                                }
                                                                                return video;       
                                                                            }

                                                                            public static Video retriveVideo(int duration_id) { //Overfloat Method

                                                                                Video video = new Video();
                                                                                Connection con = JazzLibraryDAO.getConnection();  
                                                                                try {
                                                                                    PreparedStatement ps = con.prepareStatement("SELECT * FROM video WHERE duration_id=?");
                                                                                    ps.setInt(1,duration_id);  
                                                                                    ResultSet rs=ps.executeQuery();  

                                                                                    video.setVideo_id(rs.getInt(1));               
                                                                                    video.setVideo_name(rs.getString(2));  

                                                                                    con.close(); 
                                                                                } catch (SQLException ex) {

                                                                                }
                                                                                return video;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
                                                                            }


																			
                                                                            
                                                                            
                                                                            
    public static void createSplashScreenQuote(String quote_text) {

    	int status = 0;
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO SplashScreenQuotes  (quote_text) VALUES (?);");
            ps.setString(1,quote_text); 
       
            
            status=ps.executeUpdate();                
            con.close();      
        } catch (SQLException ex) {            
        }
    	
		}
  
                                                                            



    
    
    
}