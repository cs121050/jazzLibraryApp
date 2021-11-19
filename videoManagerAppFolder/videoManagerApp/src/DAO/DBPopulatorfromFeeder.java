package DAO;
import java.awt.Desktop;
import java.util.Scanner;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class DBPopulatorfromFeeder {
	
	public String[]  pastArtistNames;
    public int artistID;
	
	

    
    
	private static void DBPopulatorTool() throws IOException, SQLException {
    //step 1 , diavazei olo to file pou periexei thn sunolikh pliroforeia tou kathe pediou , kai ftiaxnei
	//		   antikimena Artists  (ginete elenxos kathe artist na iparxei mono mia fora)
	//step 1.5 , meta , enan enan tous fortonei sthn bash
	//step 2 , diavazei apo thn arxh to ido File , alla authn thn fora  ftiaxnei adikimena Video
	//step 2.5 sthn sunexeia  fortosta sthn BASH
	//step 3   trekse pali to file ... pernontas to path tou video ,, kai tou kathe artist pou parousiazete sthn gramh
	//         bres ta ID tou kathe artist , kai to Id tou  paron binteo ,, kai ftiaxe ena object  videoContainsArtist
	//         gia katheartist pou brikes
	//step3.5  valta sto table san egrafes	
		
		
		System.out.println("Loading... (step 1/4 )...  (Reading Artists)");
		List<Artist> artists=artistAtributesColector();

		System.out.println("Loading... (step 1.5/4 )...  (uploading Artists Into DataBaseTable)");
		for(int i=0;i<artists.size();i++)
			JazzLibraryDAO.createArtist(
					artists.get(i).getArtist_name(),
					artists.get(i).getArtist_surname(),
					artists.get(i).getInstrument_id() 
					);        	

		
		
		
		System.out.println("Loading... (step 2/4 )...  (Reading Videos)");
		List<Video> videos=videoAtributesColector();
		
		
		System.out.println("Loading... (step 2.5/4 )...  (uploading Videos Into DataBaseTable)");
		for(int i=0;i<videos.size();i++) {
			//den tou aresei pou tou bazo apo to class .. kati den katalavainei
			JazzLibraryDAO.createVideo(
					videos.get(i).getVideo_name(),
					videos.get(i).getVideo_duration(),
					videos.get(i).getDuration_id(),
					videos.get(i).getVideo_path(),
					videos.get(i).getType_id(),
					videos.get(i).getLocation_id()
					);
		}

		
		System.out.println("Loading... (step 3/4 )...  (Reading Videos & Artists)");
		List<VideoContainsArtist> videoContainsArtist=videoContainsArtistColector();
		

		System.out.println("Loading... (step 3.5/4 )...  (uploading Videos & Artist Relations Into DataBaseTable)");
		for(int i=0;i<videoContainsArtist.size();i++)
			JazzLibraryDAO.createVideoContainsArtist(
					videoContainsArtist.get(i).getVideo_id(),
					videoContainsArtist.get(i).getArtist_id()
					);        	
		
		
		System.out.println("Loading... (step 4/? )...  (Reading Videos & Artists)");
		List<SplashScreenQuotes > splashScreenQuotes =SplashScreenQuotesColector();
		System.out.println("Loading... (step 4.5/? )...  (uploading quotes)");
		for(int i=0;i<splashScreenQuotes.size();i++)
			JazzLibraryDAO.createSplashScreenQuote(
					splashScreenQuotes.get(i).getQuote_text()
					);        	
		
		
		
		
		System.out.println("COMPLETE");

	}
	
	
	
	
	
	
	
	
	
	private static List<SplashScreenQuotes> SplashScreenQuotesColector() throws IOException {
		FileReader frSplashScreenQuotesLine=new FileReader("C:\\Users\\n.sarantopoulos\\Desktop\\splashScreenQuotes.txt");
		BufferedReader brSplashScreenQuotesLine = new BufferedReader(frSplashScreenQuotesLine);

        String quoteLine=brSplashScreenQuotesLine.readLine();
		
        
        
        List<SplashScreenQuotes> splashScreenQuotesList = new ArrayList<SplashScreenQuotes>();
        while(quoteLine != null){

        	
        	SplashScreenQuotes splashScreenQuote = new SplashScreenQuotes();
        	
        	
        	splashScreenQuote.setQuote_text(quoteLine);
        	
        	
        	splashScreenQuotesList.add(splashScreenQuote);
        	
        	

        	quoteLine=brSplashScreenQuotesLine.readLine();
        }
        	
        brSplashScreenQuotesLine.close();    
        
        
        
        return splashScreenQuotesList;
   	}



	public static List<Artist> artistAtributesColector() throws IOException{
	//anoigei kai diabazei to file me ta dedomena , ftiaxnei ena list me Object artist , kai gia kathe gramh tou pinaka
	//ftiaxnei gia to kirio Artist tou video , ena Object me ta stoixeia tou , 
	//sthn sunexeia elenxei mia mia tis egrafes tou list , na dei an o artist exei   idi perastei 
	//an den iparxei idi mesa sthn lista    ,  tote to kaneis ADD	
		FileReader frDummyFeederLine=new FileReader("C:\\Users\\n.sarantopoulos\\Desktop\\dummyDBFeeder2.txt");
		BufferedReader brDummyFeederLine = new BufferedReader(frDummyFeederLine);

        String videoLine=brDummyFeederLine.readLine();
		
        
        
        List<Artist> passedArtists = new ArrayList<Artist>();
        while(videoLine != null){


        	
       
        	
    		Artist thisArtist=getMainArtistAtributes(videoLine);
        	
        	
        	boolean artistExists=artistExists(thisArtist,passedArtists);
        	if(artistExists==false) 
	    		passedArtists.add(thisArtist);
        	
        	
        	videoLine=brDummyFeederLine.readLine();
        }
        	
        brDummyFeederLine.close();    
        
        return passedArtists;
        
        
	}
	
	
	

	public static List<VideoContainsArtist> videoContainsArtistColector() throws IOException{
	//diabazeis kathe gramh tou file me to DATA  kai list   me VIDEOCONTAINSARTIST OBJECTs
	//meta bash tou Video_PAth  kai tou Artuist name /surname tou kathe artist pou periexete sthn gramh 
	//kaneis mia engafh sto TABLE  VIDEOCONTAINSARTIST
		FileReader frDummyFeederLine=new FileReader("C:\\Users\\n.sarantopoulos\\Desktop\\dummyDBFeeder2.txt");
		BufferedReader brDummyFeederLine = new BufferedReader(frDummyFeederLine);

        String videoLine=brDummyFeederLine.readLine();
		
        
        
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
        	
        	

        	videoLine=brDummyFeederLine.readLine();
        }
        	
        brDummyFeederLine.close();    
        
        
        
        return videoContainsArtistList;
   	}
	
	
	
	
	
	

	
	public static List<Video> videoAtributesColector() throws IOException{
	//anoigei kai diabazei to file me ta dedomena , ftiaxnei ena list me Object Video , kai gia kathe gramh tou pinaka
	//ftiaxnei gia to  Video , ena Object me ta stoixeia tou , 
	//sthn sunexeia elenxei mia mia tis egrafes tou list , na dei an o Video exei   idi perastei 
	//an den iparxei idi mesa sthn lista    ,  tote to kaneis ADD		
		FileReader frDummyFeederLine=new FileReader("C:\\Users\\n.sarantopoulos\\Desktop\\dummyDBFeeder2.txt");
		BufferedReader brDummyFeederLine = new BufferedReader(frDummyFeederLine);

        String videoLine=brDummyFeederLine.readLine();
		
        
        
        List<Video> passedVideos = new ArrayList<Video>();
        while(videoLine != null){


        	
        	Video thisVideo=getVideoAtributes(videoLine);	        
        
        	
        	boolean videoExists=videoExists(thisVideo,passedVideos);
        	if(videoExists==false) 
	    		passedVideos.add(thisVideo);
        	
        	
        	
        	videoLine=brDummyFeederLine.readLine();
        }
        	
        brDummyFeederLine.close();    
        
        return passedVideos;
        
        
	}
	
	
	
	
	
	
	

	
	
	public static Artist getMainArtistAtributes(String videoLine) {
	//pernei to Line apo to file me ola ta DATA kai me tis methodous apo kato prota, 
	//briskei to onoma tou Main artist , meta to spaei se firstName SecondName	
    //perneis kai to instrument ID tou artist ,,
	//me oles autes tis plirofories ftiaxneis ena Object kai OUTPUT it	
		String videosMainArtistName=getMainArtistName(videoLine);


		String[] splitedFullName=nameSpliter(videosMainArtistName);
		
		
    	int instrumentID=getArtistInstrumentId(videoLine);
		
    	
    	
		
    	Artist artist = new Artist();
    	artist.setArtist_name(splitedFullName[0]);
    	artist.setArtist_surname(splitedFullName[1]);
    	artist.setInstrument_id(instrumentID);
	
		return artist;
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
		

    	
    	
    	
    	Video video = new Video();
    	video.setVideo_path(videoPath);
    	video.setVideo_name(videoName);
    	video.setDuration_id(videoDurationId);
    	video.setVideo_duration(videoDurationName);
        video.setType_id(videoTypeId);
        video.setLocation_id(videoLocationId);
		
        
		return video;
	}	
	
	

	
	
	public static boolean videoExists(Video thisVideo,List<Video> passedVideos){
	//perneis mia lista me Objects , kai ena object ,,, an breis to object auto mesa sthn LIST
	//DOSe   true  aluios dose false!	
		int found=0;
		for(int i=0;i<passedVideos.size();i++) {
			
			String passedVideoPath=passedVideos.get(i).getVideo_path();
			
			
			if(passedVideoPath.equals(thisVideo.getVideo_path()))
				found=1;
			
		}
			if(found==1)
				return true;
			else 
				return false;
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
		

		
		
	public String[] getAllContainedArtistNames(String videoLine) {
	//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
		String[] splitContentLine=videoLine.trim().split("#");

		String[] eachContainedArtist=splitContentLine[0].trim().split("@");//xorizei an to video anoikei se polous artists
		
		return eachContainedArtist;
	}

	
	public static int[] getAllContainedArtistsIds(String videoLine) {
		String[][] allContainedArtistNamesAndSurname=artistNameManager(videoLine);
		
		int[] allContainedArtistIds =new int[allContainedArtistNamesAndSurname.length];
		for(int i=0;i<allContainedArtistNamesAndSurname.length;i++)
			allContainedArtistIds[i]=JazzLibraryDAO.getArtistId(allContainedArtistNamesAndSurname[i][0],allContainedArtistNamesAndSurname[i][1]);
			
		return allContainedArtistIds;
	}
	
	
	
	
	
	
	

	
	
	public static int getVideoId(String videoLine) {
		
    	String videoPath=getVideoPath(videoLine);
		int videoId=JazzLibraryDAO.getVideoId(videoPath);
		
		
		return videoId;
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
			if(Integer.parseInt(videoDuration.replace(":", ""))<=500){   //pernei thn gramatoseira 00:00:00 apo to contentLine , kanei removet ta ":" --> 000000 , kai meta to knei INT gia na to sigrinei me to 500 (->00:05:00)
	            durationRepresentative = "5";
	        }
	        else if(Integer.parseInt(videoDuration.replace(":", ""))<=1500){
	            durationRepresentative = "5to15";
	        }
	        else if(Integer.parseInt(videoDuration.replace(":", ""))<=3000){
	            durationRepresentative = "15to30";
	        }
	        else if(Integer.parseInt(videoDuration.replace(":", ""))<=6000){
	            durationRepresentative = "30to60";
	        }
	        else if(Integer.parseInt(videoDuration.replace(":", ""))>6000){
	            durationRepresentative = "moreThan60";
	        }
			
		
	    	int videoDurationId=JazzLibraryDAO.getDurationId(durationRepresentative);
	
			
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
		
    	int videoTypeId=JazzLibraryDAO.getTypeId(splitContentLine[5]);
		
		
		
		return videoTypeId;
		
	}
		
	public static String getVideoLocationId(String videoLine){
	//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
		String[] splitContentLine=videoLine.trim().split("#");
		
		String videoLocationId = splitContentLine[6];
		
		return videoLocationId;
		
	}
		

	public static String getMainArtistName(String videoLine) {
	//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
		String[] splitContentLine=videoLine.trim().split("#");
		
		String[] eachContainedArtist=splitContentLine[0].trim().split("@");//xorizei an to video anoikei se polous artists
		
		String videosMainArtistName=eachContainedArtist[0];
		
		return videosMainArtistName;

	}
	
	
	

	
	
	public static int getArtistInstrumentId(String videoLine){
	//videoLine form :nikos sar@ kiriakos sar@ gianis kokor# piano# videolink# videoName# videoDuration#videoType# videoID
		String[] splitContentLine=videoLine.trim().split("#");
		
		String instrumentName= splitContentLine[1];
		
    	int instrumentId=JazzLibraryDAO.getInstrumentId(instrumentName);
		
		
		
		return instrumentId;
		
	}
	
	


  
	public static String[] nameSpliter(String fullName) {
    //gia na spaso to First name apo to Second Name : gia to First name --> kano tosa apend ston StringBuffer 
    //osa mia le3i prin to telos     .   gia to second name apla perno to teleuteo stixio tou pinaka !! 
    //kai sto telos kano trim( gia na e3afaniso to extra keno		    
		String[] artistNameSplited=fullName.trim().split(" ");
    	StringBuffer fullFirstName = new StringBuffer(); 
    	 for(int j=0;j<artistNameSplited.length-1;j++)
             fullFirstName.append(artistNameSplited[j]+" ");
		
    	String[] splitedName= {fullFirstName.toString().trim(),artistNameSplited[artistNameSplited.length-1] } ;
    	 
    	 
		return splitedName;
		
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

	
        
        
        
        
        
	public static void SystemPause(){

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

	
	
}
    
 