package jazzLibraryhelpfullmethods;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.commons.io.IOUtils;
import org.mp4parser.IsoFile;
import org.mp4parser.muxer.MemoryDataSourceImpl;



public class changeNamesDurationToVoid {


    public static void main(String[] args) throws Exception{

    		
    	//List<VideoDatabaseFeeder> feedingFileLines =renaming();
    	//writeVideoArrayListToFile(feedingFileLines,"C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\text_resorses\\databaseFeederFile.txt");
    	
    	//youtubeVideoInfoDownloader();
    	List<VideoDatabaseFeeder> feedingFileLinesWithNames =videoDataBaseFeedingFilleNameDurationAvailabilityGiver();
    	//List<VideoDatabaseFeeder> deleteUnavalableVideosFromList =deleteUnavalableVideosFromList();
    	//feedingFileLinesWithNames=videoDataBaseFeedingFilleNameDurationGiver_leftovers(feedingFileLinesWithNames);
    	writeVideoArrayListToFile(feedingFileLinesWithNames,"C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\text_resorses\\databaseFeederFile.txt");
    	

    	
    	
    }
    
    
    
    
    private static List<VideoDatabaseFeeder> deleteUnavalableVideosFromList() throws Exception {

		ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = fromVideoDatabaseFeedingFileToArrayList();

    	for(int i=0;i<videoDatabaseFeederList.size();i++){
            if(videoDatabaseFeederList.get(i).getVideo_availability().equals("0"))
            	videoDatabaseFeederList.remove(i);
            
        }

        return videoDatabaseFeederList;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static List<VideoDatabaseFeeder> videoDataBaseFeedingFilleNameDurationGiver_leftovers(List<VideoDatabaseFeeder> feedingFileLinesWithNamesList) throws Exception {

    	for(int i=0;i<feedingFileLinesWithNamesList.size();i++){

        	
            File videoInfoJsonFile = new File("F:\\jazzLibrary\\downloaderLoot\\"+feedingFileLinesWithNamesList.get(i).getVideo_id()+".mp4");           
            if( (feedingFileLinesWithNamesList.get(i).getVideo_name().equals("???") 
            		|| feedingFileLinesWithNamesList.get(i).getVideo_duration().equals("???")
            			) && videoInfoJsonFile.exists() )  {

            	
//            	String videpJsonInfoString = readFileAsString("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoRecivingFolder"+"\\"+videoDatabaseFeederList.get(i).getVideo_id()+".info.json");            	
//            	String[] spitedVideoInfoJsonString1 = videpJsonInfoString.split("\"title\": ");
//            	String[] isolateVideoTitleValueString = spitedVideoInfoJsonString1[1].split(",");
//                String videoTitleValue = isolateVideoTitleValueString[0].replaceAll("\"", "");
            	
		    	byte[] fileToByteArray = readFileToByteArray(videoInfoJsonFile);
		    	long videoDuration = getAudioLength(fileToByteArray);

                System.out.println(videoDuration);
                //feedingFileLinesWithNamesList.get(i).setVideo_name(videoTitleValue.replace("#","n."));
                feedingFileLinesWithNamesList.get(i).setVideo_duration(String.valueOf(videoDuration));
            }
           

        }

        return feedingFileLinesWithNamesList;
	}



	public static List<VideoDatabaseFeeder> renaming() throws IOException {
    	
    	FileReader feeder_Fr=new FileReader("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\text_resorses\\databaseFeederFileMASTER.txt");
		BufferedReader feeder_Br = new BufferedReader(feeder_Fr);

		List<VideoDatabaseFeeder> feederList = new ArrayList<VideoDatabaseFeeder>();
		
        String feederLine=feeder_Br.readLine();
        while(feederLine != null){
        	
        	String[] splitFeederLine=new String[8];
    		splitFeederLine=feederLine.trim().split("#");
    		
    		String[] splitLink=splitFeederLine[2].trim().split("&t=");
    		String linkWithoutSeconds=splitLink[0];

    		
    		VideoDatabaseFeeder videoDatabaseFeeder = new VideoDatabaseFeeder();
    		videoDatabaseFeeder.setArtist_name(splitFeederLine[0]);
    		videoDatabaseFeeder.setArtist_instrument(splitFeederLine[1]);
    		videoDatabaseFeeder.setVideo_link(linkWithoutSeconds);
    		videoDatabaseFeeder.setVideo_name("???");
    		videoDatabaseFeeder.setVideo_duration("???");
    		videoDatabaseFeeder.setVideo_type(splitFeederLine[5]);
    		videoDatabaseFeeder.setVideo_id(splitFeederLine[6].replace("&t",""));
    		videoDatabaseFeeder.setVideo_availability("0");

    		
    		feederList.add(videoDatabaseFeeder);

            feederLine=feeder_Br.readLine();
        }
        
        feeder_Br.close(); 
        
        
        return feederList;
	
    }
    
    
    
    
    private static void writeVideoArrayListToFile(List<VideoDatabaseFeeder> feedingFileLines, String databaseFeederFilePath) throws IOException {

		FileWriter fw = new FileWriter(databaseFeederFilePath);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        for(int i=0;i<feedingFileLines.size();i++)
        	pw.println(feedingFileLines.get(i).toString());
        
        pw.close();
	}
    
    
    private static ArrayList<VideoDatabaseFeeder> videoDataBaseFeedingFilleNameDurationAvailabilityGiver() throws Exception {
    	
    	int counter=0;
    		
		ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = fromVideoDatabaseFeedingFileToArrayList();

		
        for(int i=0;i<videoDatabaseFeederList.size();i++){

        	counter++;
        	
            File videoInfoJsonFile = new File("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoRecivingFolder\\"+videoDatabaseFeederList.get(i).getVideo_id()+".info.json");      
            
            if(videoDatabaseFeederList.get(i).getVideo_availability().equals("0")){
	            videoDatabaseFeederList.remove(i); i--;
            }
            
            
            /* if(videoInfoJsonFile.exists())  {
                
            	
            	String videpJsonInfoString = readFileAsString("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoRecivingFolder"+"\\"+videoDatabaseFeederList.get(i).getVideo_id()+".info.json");            	
            	String[] spitedVideoInfoJsonString1 = videpJsonInfoString.split("\"title\": ");
            	String[] isolateVideoTitleValueString = spitedVideoInfoJsonString1[1].split(",");
                String videoTitleValue = isolateVideoTitleValueString[0].replaceAll("\"", "").replaceAll("#","n.").replaceAll("}", "");


                String[] spitedVideoInfoJsonString2 = videpJsonInfoString.split("\"duration\": ");
            	String durationValue="0";
            	int durationNumericForm = 0;
            	for(int j=0;j<spitedVideoInfoJsonString2.length;j++) {
                	
            		String[] isolateDurationValueString = spitedVideoInfoJsonString2[j].split(",");
            		String[] isolateDurationValueStringAgain = isolateDurationValueString[0].split("}");
            		
            		if(!isolateDurationValueString[0].contains(".")) {
            			
            			durationValue = isolateDurationValueStringAgain[0];
    	            	try {
    	                    durationNumericForm = Integer.parseInt(durationValue.replace("}",""));
    	                    
    	                    break;
    	                } catch (NumberFormatException e) {
    	                	
    	                }
            		}
	

            	}
            	
            	
            	
                System.out.println(durationNumericForm);
                videoDatabaseFeederList.get(i).setVideo_name(videoTitleValue);
                videoDatabaseFeederList.get(i).setVideo_duration(String.valueOf(durationNumericForm));
                videoDatabaseFeederList.get(i).setVideo_availability("1");
            }
            
            */
            
            
        }
        

        return videoDatabaseFeederList;
	}
    
    
    
	private static ArrayList<VideoDatabaseFeeder> fromVideoDatabaseFeedingFileToArrayList() throws IOException {
			
			
			
		FileReader databaseFeederFR=new FileReader("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\text_resorses\\databaseFeederFileMASTER.txt");
	    BufferedReader databaseFeederBR = new BufferedReader(databaseFeederFR);
	    
		ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = new ArrayList<VideoDatabaseFeeder>();
		
		String videoDatabaseFeederLine=databaseFeederBR.readLine();
	    while(videoDatabaseFeederLine != null){
	
	    	String[] splitedVideoLine=videoDatabaseFeederLine.split("#");
	    	
	    	String[] splitLink=splitedVideoLine[2].split("&t=");
    		String videoLinkWithoutSeconds=splitLink[0];
	    	
	    	
	    	VideoDatabaseFeeder videoDatabaseFeeder = new VideoDatabaseFeeder();
	    	videoDatabaseFeeder.setArtist_name(splitedVideoLine[0]);
	    	videoDatabaseFeeder.setArtist_instrument(splitedVideoLine[1]);
	    	videoDatabaseFeeder.setVideo_link(videoLinkWithoutSeconds);
	    	videoDatabaseFeeder.setVideo_name(splitedVideoLine[3]);
	    	videoDatabaseFeeder.setVideo_duration(splitedVideoLine[4]);
	    	videoDatabaseFeeder.setVideo_type(splitedVideoLine[5]);
	    	videoDatabaseFeeder.setVideo_id(splitedVideoLine[6]);
	    	videoDatabaseFeeder.setVideo_availability(splitedVideoLine[7]);

	        	
	
	    	videoDatabaseFeederList.add(videoDatabaseFeeder);
	    	
	    	
	    	videoDatabaseFeederLine=databaseFeederBR.readLine();
	    }
	    
	    return videoDatabaseFeederList;
	}
	
	
	
	
	public static String readFileAsString(String file)throws Exception
	{
	    return new String(Files.readAllBytes(Paths.get(file)));
	}
	
	
	private static void youtubeVideoInfoDownloader() throws IOException {
		
		int counter=0;
		
		
		ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = fromVideoDatabaseFeedingFileToArrayList();
	
		int i=0;
		while(i<videoDatabaseFeederList.size() ) {
	    	
	        
	    	String videoURL=videoDatabaseFeederList.get(i).getVideo_link().replaceAll("\\s+","_");
	
	            try {
	            	Process proc =proc = Runtime.getRuntime().exec("cmd");
	                new Thread(new SyncPipe(proc.getErrorStream(), System.err)).start();
	                new Thread(new SyncPipe(proc.getInputStream(), System.out)).start();
	                
	                OutputStream outputStream = proc.getOutputStream();
	                PrintWriter outputStreamWriter = new PrintWriter(outputStream);     
	                
	                outputStreamWriter.println("cd "+"C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoRecivingFolder");  
	                outputStreamWriter.println("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoRecivingFolder"+"\\"+"youtube-dl -o %(id)s.mp4 --write-info-json --skip-download "+videoURL);  
	
	
	                outputStreamWriter.close();  
	                
	                proc.waitFor();    
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            
	            
	            
	    	i++;
	    }
	    
	}
	
	

	public static byte[] readFileToByteArray(final File file) throws IOException {
	    InputStream in = null;
	    try {
	        in = openInputStream(file);
	        return IOUtils.toByteArray(in); // Do NOT use file.length() - see IO-453
	    } finally {
	        IOUtils.closeQuietly(in);
	    }
	}
	
	public static long getAudioLength(byte[] content) throws Exception {
	    IsoFile isoFile = new IsoFile((ReadableByteChannel) new MemoryDataSourceImpl(content));
	    double lengthInSeconds = (double)isoFile.getMovieBox().getMovieHeaderBox().getDuration() / isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
	    return (long)lengthInSeconds;
	}
	public static FileInputStream openInputStream(final File file) throws IOException {
	    if (file.exists()) {
	        if (file.isDirectory()) {
	            throw new IOException("File '" + file + "' exists but is a directory");
	        }
	        if (file.canRead() == false) {
	            throw new IOException("File '" + file + "' cannot be read");
	        }
	    } else {
	        throw new FileNotFoundException("File '" + file + "' does not exist");
	    }
	    return new FileInputStream(file);
	}



}
