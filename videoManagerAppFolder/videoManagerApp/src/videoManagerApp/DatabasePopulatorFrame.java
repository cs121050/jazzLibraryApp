
package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.DateFormat;

import JazzLibraryClassies.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



/**
*
* @author nick
*/
public class DatabasePopulatorFrame extends JFrame {

    
    private JLabel warningMessage_Lb;

    public static StringBuffer videoDownloadProgreessOutput;  

    boolean exitDatabasePopulatorFrame = false;
    
    private static Connection conn;
     
    
    public DatabasePopulatorFrame() {
       super();
 
       
       warningMessage_Lb=new JLabel("n/a");       
       videoDownloadProgreessOutput=new StringBuffer("");
       
    }
    
    
    public void prepareUI() throws Exception {
        
    	
    	this.add(warningMessage_Lb, BorderLayout.NORTH);	    	
        
        
        this.setSize(300, 100);
        this.setLocationRelativeTo(null);
        this.setTitle("Database Populator");
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        
	    if(VideoManagerAppMain.isResourcesAtributesInputCorrect()==true && VideoManagerAppMain.isDatabaseAtributesInputCorrect()==true){
	        
	    	conn = DAO.JazzLibraryDAO.getConnection(VideoManagerAppMain.serverName, VideoManagerAppMain.databaseName, VideoManagerAppMain.databaseUsername, VideoManagerAppMain.databasePassword);
	    	
	    	
	    	warningMessage_Lb.setText("Do Not Turn Off Until Finish.. .");
	    	warningMessage_Lb.setForeground(Color.red);
	    	
	    	do{
	    		
	    	int n = JOptionPane.showOptionDialog(new JFrame(), "Database Population is Starting ,it might take over 10 minutes \n(depending of your internet connection) so be patient ", "Coded Message"
	    			, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
	    	        null, new Object[] {"Yes", "No"}, JOptionPane.YES_OPTION);
	    	        if (n == JOptionPane.NO_OPTION) {
	    	            this.setVisible(false);
	    	            break;
	    	        } else if (n == JOptionPane.CLOSED_OPTION) {
	    	            this.setVisible(false);
	    	            break;
	    	        }
	    	
	    	
	    	videoDownloadProgreessOutput.append("Loading... (step 0/4 )...  (Load Databasre Structure)"+"\n");   System.out.println(1);
	    	String databaseStructureSqlFile = databaseStructureFileToString();
	    	DAO.JazzLibraryDAO.dropDatabaseTablesAndCreateNew(databaseStructureSqlFile, conn);
	    	
	    	videoDownloadProgreessOutput.append("Loading... (step 1/4 )...  (Reading Artists)"+"\n");  System.out.println(2);
			List<Artist> artists=artistAtributesColector();

			videoDownloadProgreessOutput.append("Loading... (step 1.5/4 )...  (uploading Artists Into DataBaseTable)"+"\n");  System.out.println(3);
			for(int i=0;i<artists.size();i++)
				DAO.JazzLibraryDAO.createArtist(
						artists.get(i).getArtist_name(),
						artists.get(i).getArtist_surname(),
						artists.get(i).getInstrument_id(),
						conn 
						);        	

			
			
			
			videoDownloadProgreessOutput.append("Loading... (step 2/4 )...  (Reading Videos)"+"\n");    System.out.println(4);
			List<Video> videos=videoAtributesColector();
			
			
			videoDownloadProgreessOutput.append("Loading... (step 2.5/4 )...  (uploading Videos Into DataBaseTable)"+"\n");    System.out.println(5);
			for(int i=0;i<videos.size();i++) {
				//den tou aresei pou tou bazo apo to class .. kati den katalavainei
				DAO.JazzLibraryDAO.createVideo(
						videos.get(i).getVideo_name(),
						videos.get(i).getVideo_duration(),
						videos.get(i).getDuration_id(),
						videos.get(i).getVideo_path(),
						videos.get(i).getType_id(),
						videos.get(i).getLocation_id(),
						videos.get(i).getVideo_availability(),

						conn 
						);
			}

			
			videoDownloadProgreessOutput.append("Loading... (step 3/4 )...  (Reading Videos & Artists)"+"\n");  System.out.println(6);
			List<VideoContainsArtist> videoContainsArtist=videoContainsArtistColector();
			

			videoDownloadProgreessOutput.append("Loading... (step 3.5/4 )...  (uploading Videos & Artist Relations Into DataBaseTable)"+"\n");    System.out.println(7);
			for(int i=0;i<videoContainsArtist.size();i++)
				DAO.JazzLibraryDAO.createVideoContainsArtist(
						videoContainsArtist.get(i).getVideo_id(),
						videoContainsArtist.get(i).getArtist_id(),
						conn 
						);        	
			
			
			videoDownloadProgreessOutput.append("Loading... (step 4/? )...  (Reading Videos & Artists)"+"\n");   System.out.println(8);
			List<SplashScreenQuotes > splashScreenQuotes =SplashScreenQuotesColector();
			videoDownloadProgreessOutput.append("Loading... (step 4.5/? )...  (uploading quotes)"+"\n");   System.out.println(9);
			for(int i=0;i<splashScreenQuotes.size();i++)
				DAO.JazzLibraryDAO.createSplashScreenQuote(
						splashScreenQuotes.get(i).getQuote_text(),
						conn 
						);        	
			
			
			
			
			videoDownloadProgreessOutput.append("COMPLETE"+"\n");
	    	
	    	}while(false);

			
			
	    	conn.close();
	    	this.setVisible(false);
	    }
	    else { 
	    	
	    	warningMessage_Lb.setText("some file can't found ,or connection to database \\n can't complete, go to Settings and comfigure the Path");
	    	warningMessage_Lb.setForeground(Color.red);
	    }
	    
	    
	    
	    
	    
	    this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
            	
            	setDatabasePopulatorFrameInvisible();    
            }
        });
        

	    
	    
    }

        



    private String databaseStructureFileToString() throws IOException {

    	FileReader databaseStructure_Fr=new FileReader(VideoManagerAppMain.databaseStructureFilePath);
		BufferedReader databaseStructure_Br = new BufferedReader(databaseStructure_Fr);
    			
		StringBuffer databaseStructureStringBuffer=new StringBuffer();
		databaseStructureStringBuffer.append("");
		
		String databaseStructureLine=databaseStructure_Br.readLine();
        while(databaseStructureLine != null){

        	databaseStructureStringBuffer.append(databaseStructureLine+"\n");

        	databaseStructureLine=databaseStructure_Br.readLine();
        }
		
		
    	return databaseStructureStringBuffer.toString();
	}


	public static List<Artist> artistAtributesColector() throws IOException{
    	//anoigei kai diabazei to file me ta dedomena , ftiaxnei ena list me Object artist , kai gia kathe gramh tou pinaka
    	//ftiaxnei gia to kirio Artist tou video , ena Object me ta stoixeia tou , 
    	//sthn sunexeia elenxei mia mia tis egrafes tou list , na dei an o artist exei   idi perastei 
    	//an den iparxei idi mesa sthn lista    ,  tote to kaneis ADD	
    		FileReader dummyFeeder_fr=new FileReader(VideoManagerAppMain.databaseFeederFilePath);
    		BufferedReader dummyFeeder_Br = new BufferedReader(dummyFeeder_fr);

            String videoLine=dummyFeeder_Br.readLine();
    		
            
            
            List<Artist> passedArtists = new ArrayList<Artist>();
            while(videoLine != null){


            	
           
            	
        		Artist thisArtist=getMainArtistAtributes(videoLine);
            	
            	
            	boolean artistExists=artistExists(thisArtist,passedArtists);
            	if(artistExists==false) 
    	    		passedArtists.add(thisArtist);
            	
            	
            	videoLine=dummyFeeder_Br.readLine();
            }
            	
            dummyFeeder_Br.close();    
            
            return passedArtists;
            
            
    	}
	
	
	
	public static boolean artistExists(Artist thisArtistName,List<Artist> artistsAtributes) {
		//perneis mia lista me Objects , kai ena object ,,, an breis to object auto mesa sthn LIST
		//DOSe   true  alios dose false!		
			int found=0;
			for(int i=0;i<artistsAtributes.size();i++) {
				
				String pastArtistFullName=artistsAtributes.get(i).getArtist_name()+" "+artistsAtributes.get(i).getArtist_surname();
				
				String thisArtistFullName=thisArtistName.getArtist_name()+" "+thisArtistName.getArtist_surname();


				
				if(pastArtistFullName.equals(thisArtistFullName))
					found=1;
				
			}
				if(found==1)
					return true;
				else 
					return false;

		}
    	




	public static Artist getMainArtistAtributes(String videoLine) {

		String videosMainArtistName=getMainArtistName(videoLine);


		String[] splitedFullName=nameSpliter(videosMainArtistName);
		
		
    	int instrumentID=getArtistInstrumentId(videoLine);
		
    	
    	
		
    	Artist artist = new Artist();
    	artist.setArtist_name(splitedFullName[0]);
    	artist.setArtist_surname(splitedFullName[1]);
    	artist.setInstrument_id(instrumentID);
	
		return artist;
	}
	
	
	public static String getMainArtistName(String videoLine) {
		//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
			String[] splitContentLine=videoLine.trim().split("#");
			
			String[] eachContainedArtist=splitContentLine[0].trim().split("@");//xorizei an to video anoikei se polous artists
			
			String videosMainArtistName=eachContainedArtist[0];
			
			return videosMainArtistName;

		}
	
	public static String[] nameSpliter(String fullName) {
	    	    
			String[] artistNameSplited=fullName.trim().split(" ");
	    	StringBuffer fullFirstName = new StringBuffer(); 
	    	 for(int j=0;j<artistNameSplited.length-1;j++)
	             fullFirstName.append(artistNameSplited[j]+" ");
			
	    	String[] splitedName= {fullFirstName.toString().trim(),artistNameSplited[artistNameSplited.length-1] } ;
	    	 
	    	 
			return splitedName;
			
		}
	
	
	public static int getArtistInstrumentId(String videoLine){
		//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
			String[] splitContentLine=videoLine.trim().split("#");
			
			String instrumentName= splitContentLine[1];
			
	    	int instrumentId=DAO.JazzLibraryDAO.getInstrumentId(instrumentName,conn);
			
			
			
			return instrumentId;
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static List<Video> videoAtributesColector() throws IOException{
				
			FileReader dummyFeeder_fr=new FileReader(VideoManagerAppMain.databaseFeederFilePath);
			BufferedReader dummyFeeder_Br = new BufferedReader(dummyFeeder_fr);

	        String videoLine=dummyFeeder_Br.readLine();

	        
	        List<Video> videoList = new ArrayList<Video>();
	        while(videoLine != null){

	        	Video thisVideo=getVideoAtributes(videoLine);	        
	        
	        	
	        	
		    	videoList.add(thisVideo);
	        	
	        	
	        	
	        	videoLine=dummyFeeder_Br.readLine();
	        }
	        	
	        dummyFeeder_Br.close();    
	        
	        return videoList;
	        
	        
		}
	
	
	
	public static Video getVideoAtributes(String videoLine) {
		//perneis apo to LINE tou text, oles tis plirofories pou thes gia na siinthrseis ena VIDEO OBJECT 
		//kai oti ID pliroforia thes , thn perneis apo DAO   	
			
	    	String videoPath=getVideoPath(videoLine);
	    	
	    	String videoName=getVideoName(videoLine);

	    	int videoDurationId=getVideoDurationId(videoLine);

	    	String videoDurationName=getVideoDurationName(videoLine);
	    	
	    	
	    	int videoTypeId=getVideoTypeId(videoLine);

	    	String videoLocationId=getVideoLocationId(videoLine);
	    	
	    	String videoAvailability=getVideoAvailability(videoLine);

			

	    	
	    	
	    	
	    	Video video = new Video();
	    	video.setVideo_path(videoPath);
	    	video.setVideo_name(videoName);
	    	video.setDuration_id(videoDurationId);
	    	video.setVideo_duration(videoDurationName);
	        video.setType_id(videoTypeId);
	        video.setLocation_id(videoLocationId);
	        video.setVideo_availability(videoAvailability);

			
	        
			return video;
		}	
	
	public static String getVideoAvailability(String videoLine){
		//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
			String[] splitContentLine=videoLine.trim().split("#");
			
			String videoAvailability = splitContentLine[7];
			
			return videoAvailability;
			
		}
	
	
	public static String getVideoPath(String videoLine){
		//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
			String[] splitContentLine=videoLine.trim().split("#");
			
			String videoPath = splitContentLine[2];
			
			return videoPath;
			
		}
		
		
		
			
		public static String getVideoName(String videoLine){
		//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
			String[] splitContentLine=videoLine.trim().split("#");
			
			String videoName = splitContentLine[3];
			
			return videoName;
			
		}	
					
		public static int getVideoDurationId(String videoLine){
		//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
				String[] splitContentLine=videoLine.trim().split("#");			
				
				String videoDuration = splitContentLine[4];
				
				String durationRepresentative="5";
				if(Integer.parseInt(videoDuration.replace(":", ""))<=300){   //pernei thn gramatoseira 00:00:00 apo to contentLine , kanei removet ta ":" --> 000000 , kai meta to knei INT gia na to sigrinei me to 500 (->00:05:00)
		            durationRepresentative = "5";
		        }
		        else if(Integer.parseInt(videoDuration.replace(":", ""))<=900){
		            durationRepresentative = "5to15";
		        }
		        else if(Integer.parseInt(videoDuration.replace(":", ""))<=1800){
		            durationRepresentative = "15to30";
		        }
		        else if(Integer.parseInt(videoDuration.replace(":", ""))<=3600){
		            durationRepresentative = "30to60";
		        }
		        else if(Integer.parseInt(videoDuration.replace(":", ""))>3600){
		            durationRepresentative = "moreThan60";
		        }
				
			
		    	int videoDurationId=DAO.JazzLibraryDAO.getDurationId(durationRepresentative,conn);
		
				
				return videoDurationId;
				
		}
			
		
		
		public static String getVideoDurationName(String videoLine){
		//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
			String[] splitContentLine=videoLine.trim().split("#");		
			
			String videoDurationName = splitContentLine[4];
			
			return videoDurationName;
			
	}
					
				
					
					
				
		public static int getVideoTypeId(String videoLine){
		//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
			String[] splitContentLine=videoLine.trim().split("#");
			
			String videoType = splitContentLine[5];
			
	    	int videoTypeId=DAO.JazzLibraryDAO.getTypeId(splitContentLine[5],conn);
			
			
			
			return videoTypeId;
			
		}
			
		public static String getVideoLocationId(String videoLine){
		//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
			String[] splitContentLine=videoLine.trim().split("#");
			
			String videoLocationId = splitContentLine[6];
			
			return videoLocationId;
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
		
		
	
	
		public static List<VideoContainsArtist> videoContainsArtistColector() throws IOException{
			//diabazeis kathe gramh tou file me to DATA  kai list   me VIDEOCONTAINSARTIST OBJECTs
			//meta bash tou Video_PAth  kai tou Artuist name /surname tou kathe artist pou periexete sthn gramh 
			//kaneis mia engafh sto TABLE  VIDEOCONTAINSARTIST
				FileReader dummyFeeder_fr=new FileReader(VideoManagerAppMain.databaseFeederFilePath);
				BufferedReader dummyFeeder_Br = new BufferedReader(dummyFeeder_fr);

		        String videoLine=dummyFeeder_Br.readLine();
				
		        
		        
		        List<VideoContainsArtist> videoContainsArtistList = new ArrayList<VideoContainsArtist>();
		        while(videoLine != null){


		        	
		        	int thisVideoId=getVideoId(videoLine);	        
		        
		        	int[] containingArtistId=getAllContainedArtistsIds(videoLine);
		        	
		        	
		        	
		        	
		        	for(int i=0;i<containingArtistId.length;i++) {    		        		
		        		VideoContainsArtist videoContainsArtist = new VideoContainsArtist();
		        		videoContainsArtist.setArtist_id(containingArtistId[i]);
		        		videoContainsArtist.setVideo_id(thisVideoId);

		        		videoContainsArtistList.add(videoContainsArtist);
		        	}
		        	
		        	

		        	videoLine=dummyFeeder_Br.readLine();
		        }
		        	
		        dummyFeeder_Br.close();    
		        
		        
		        
		        return videoContainsArtistList;
		   	}
			
			
			
		public static int getVideoId(String videoLine) {
			
	    	String videoPath=getVideoPath(videoLine);
			int videoId=DAO.JazzLibraryDAO.getVideoId(videoPath,conn);
			
			
			return videoId;
		}
		
		
		public static int[] getAllContainedArtistsIds(String videoLine) {
			String[][] allContainedArtistNamesAndSurname=artistNameManager(videoLine);
			
			int[] allContainedArtistIds =new int[allContainedArtistNamesAndSurname.length];
			for(int i=0;i<allContainedArtistNamesAndSurname.length;i++)
				allContainedArtistIds[i]=DAO.JazzLibraryDAO.getArtistId(allContainedArtistNamesAndSurname[i][0],allContainedArtistNamesAndSurname[i][1],conn);
				
			return allContainedArtistIds;
		}
		
		
		public static String[][] artistNameManager(String videoLine){
		    //pernei ena line , to spaei sta genika data (me #) , meta to spaei sto onomata ton  simetexonton Artist(me @)
		    //ftiaxnei LIST me megethos [plithos simetexonton Artist][2]  ,, me 2 stiles {onoma , eponimo}
		    //kataxoro ston pinaka me ta onomateponima    ,    kai teliosa	
		    //videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
		    	String[] splitContentLine=videoLine.trim().split("#");
		    	
		    	
		    	String[] eachContainedArtist=splitContentLine[0].trim().split("@");
		    	
		    	String[][] videoConteinedArtistsNameSurname = new String[eachContainedArtist.length][2];  
		    	for(int i=0;i<eachContainedArtist.length;i++) {
		    		        	
		        	String[] splitedName =nameSpliter(eachContainedArtist[i]);
		    		
		        	videoConteinedArtistsNameSurname[i]= splitedName; 
		        	 
		    	}
		    
		        
		        return videoConteinedArtistsNameSurname;
		    }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	
		private static List<SplashScreenQuotes> SplashScreenQuotesColector() throws IOException {
			FileReader splashScreenQuotes_Fr=new FileReader(VideoManagerAppMain.splashScreenQuotes);
			BufferedReader splashScreenQuotes_Br = new BufferedReader(splashScreenQuotes_Fr);

	        String quoteLine=splashScreenQuotes_Br.readLine();
			
	        
	        
	        List<SplashScreenQuotes> splashScreenQuotesList = new ArrayList<SplashScreenQuotes>();
	        while(quoteLine != null){

	        	
	        	SplashScreenQuotes splashScreenQuote = new SplashScreenQuotes();
	        	
	        	
	        	splashScreenQuote.setQuote_text(quoteLine);
	        	
	        	
	        	splashScreenQuotesList.add(splashScreenQuote);
	        	
	        	

	        	quoteLine=splashScreenQuotes_Br.readLine();
	        }
	        	
	        splashScreenQuotes_Br.close();    
	        
	        
	        
	        return splashScreenQuotesList;
	   	}
		
		
		
	
	
	
	
	private void setDatabasePopulatorFrameInvisible() {
	    int i = JOptionPane.showConfirmDialog(this, "are you sure you want to Stop Population?");
	    if (i == JOptionPane.YES_OPTION) {
	    
	    	exitDatabasePopulatorFrame=true;    
	    	this.setVisible(false);
	    }
	}


}
