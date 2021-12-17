
package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import JazzLibraryClassies.VideoDatabaseFeeder;

import java.util.ArrayList;

import youtubeDownloaderSyncPipe.*;
 


public class VideoDownloaderFrame extends JFrame {

        
	    private JLabel warningMessage_Lb;

	    public static StringBuffer videoDownloadProgreessOutput;  

	    boolean exitVideoDownloadFrame = false;
	    
	    
	    public VideoDownloaderFrame() {
	       super();

	       
	       warningMessage_Lb=new JLabel("n/a");
	       
	       
	       videoDownloadProgreessOutput=new StringBuffer("");
	       
	    }
	    
	    
	    public void prepareUI() throws Exception {
	        
	    	
	    	this.add(warningMessage_Lb, BorderLayout.NORTH);	    	
	        
	        
	        this.setSize(300, 80);
	        this.setLocationRelativeTo(null);
	        this.setTitle("Video Downloader");
	        this.setVisible(true);
	        this.setResizable(false);
	        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);



	        
	        
		    if(VideoManagerAppMain.isResourcesAtributesInputCorrect()==true){
		        
		    	warningMessage_Lb.setText("Do Not Turn Off Until Finish.. .");
		    	warningMessage_Lb.setForeground(Color.red);
		    	
		    	do{

		    		int n = JOptionPane.showOptionDialog(new JFrame(),
			    			"Video Downloading is Starting ,it might take over 10 hours \n(depending of your internet connection) so be patient ,\n if you close it next time it will continue where it stoped", "Coded Message",
			    			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
			    	        null, new Object[] {"Yes", "No"}, JOptionPane.YES_OPTION);
			    	        if (n == JOptionPane.NO_OPTION) {
			    	            this.setVisible(false);
			    	            break;
			    	        } else if (n == JOptionPane.CLOSED_OPTION) {
			    	            this.setVisible(false);
			    	            break;
			    	        }
			    	
			    	
			    	
			    	videoDownloadProgreessOutput.append("~Video Downloading~  (1/2)"+"\n");
			    	youtubeVideoDownloader();  
	
	
			    	videoDownloadProgreessOutput.append("~Video Namegiving~  (2/2)"+"\n");
					ArrayList<VideoDatabaseFeeder> updatedVideoDatabaseFeeder = videoDataBaseFeedingFilleNameDurationAvailabilityGiver();
					writeVideoArrayListToFile(updatedVideoDatabaseFeeder,VideoManagerAppMain.databaseFeederFilePath);
	
	
			    	videoDownloadProgreessOutput.append("~Downloading Complited Successfully"+"\n");
			    	
		    	}while(false);
		    	
		        this.setVisible(false);
		    }
		    else { 
		    	
		    	warningMessage_Lb.setText("Databade Feading file can't found ,\n go to Settings and comfigure the Path");
		    	warningMessage_Lb.setForeground(Color.red);
		    	
		    }
		    
		    
		    
		    
		    
		    this.addWindowListener(new WindowAdapter() {
	            @Override                               
	            public void windowClosing(WindowEvent e) {
	            	
	            	setVideoDownloadFrameInvisible();    
	            }
	        });
	        

		    
		    
	    }

	        



    private void writeVideoArrayListToFile(ArrayList<VideoDatabaseFeeder> videos, String databaseFeederFilePath) throws IOException {

		FileWriter fw = new FileWriter(databaseFeederFilePath);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        for(int i=0;i<videos.size();i++)
        	pw.println(videos.get(i).toString());
        
        pw.close();
	}
    
    
    
    
    private ArrayList<VideoDatabaseFeeder> videoDataBaseFeedingFilleNameDurationAvailabilityGiver() throws Exception {
	
    	int counter=0;
    		
		ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = fromVideoDatabaseFeedingFileToArrayList();

		
        for(int i=0;i<videoDatabaseFeederList.size();i++){

        	counter++;
	    	videoDownloadProgreessOutput.append(""+counter+"\\"+videoDatabaseFeederList.size()+"\n");
        	
            File videoInfoJsonFile = new File(VideoManagerAppMain.videoReceiverFolderPath+"\\"+videoDatabaseFeederList.get(i).getVideo_id()+".info.json");           
            if( (videoDatabaseFeederList.get(i).getVideo_name().equals("???") 
            		|| videoDatabaseFeederList.get(i).getVideo_duration().equals("???")
            			) && videoInfoJsonFile.exists() )  {
                
            	videoDatabaseFeederList.get(i).setVideo_availability("1");
            	
            	String videpJsonInfoString = readFileAsString(VideoManagerAppMain.videoReceiverFolderPath+"\\"+videoDatabaseFeederList.get(i).getVideo_id()+".info.json");            	
            	String[] spitedVideoInfoJsonString1 = videpJsonInfoString.split("\"title\": ");
            	String[] isolateVideoTitleValueString = spitedVideoInfoJsonString1[1].split(",");
                String videoTitleValue = isolateVideoTitleValueString[0].replaceAll("\"", "");

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
                videoDatabaseFeederList.get(i).setVideo_name(videoTitleValue.replace("#","n."));
                videoDatabaseFeederList.get(i).setVideo_duration(String.valueOf(durationNumericForm));
            }
            else {
            	
            	videoDatabaseFeederList.remove(i); //an to video den einai avalable delete it
            }
           

        }

        
        cleanFolderOfJSONFiles(VideoManagerAppMain.videoReceiverFolderPath);
        
        
        return videoDatabaseFeederList;
	}
    
    
    public static void cleanFolderOfJSONFiles(String folderPath)
	{
        File folder = new File(folderPath);
    	
	    for (final File fileEntry : folder.listFiles()) 
	        if (fileEntry.getName().endsWith(".json")) {
	            File thisJSONFile = new File(folder.getAbsolutePath()+"\\"+fileEntry.getName());
	            thisJSONFile.delete();
	    }
	}
    
    
    
    
    
	public static String readFileAsString(String file)throws Exception
	{
	    return new String(Files.readAllBytes(Paths.get(file)));
	}
    
    
    
    private void youtubeVideoDownloader() throws IOException {
    	
    	int counter=0;
    	
    	
		ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = fromVideoDatabaseFeedingFileToArrayList();

		exitVideoDownloadFrame = false;
		int i=0;
		while(i<videoDatabaseFeederList.size() || exitVideoDownloadFrame==true) {
        	
        	counter++;
	    	videoDownloadProgreessOutput.append(""+counter+"\\"+videoDatabaseFeederList.size()+"\n");
            
        	String videoURL=videoDatabaseFeederList.get(i).getVideo_link().replaceAll("\\s+","_");

                try {
                	Process proc =proc = Runtime.getRuntime().exec("cmd");
                    new Thread(new SyncPipe(proc.getErrorStream(), System.err)).start();
                    new Thread(new SyncPipe(proc.getInputStream(), System.out)).start();
                    
                    OutputStream outputStream = proc.getOutputStream();
                    PrintWriter outputStreamWriter = new PrintWriter(outputStream);     
                    
                    outputStreamWriter.println("cd "+VideoManagerAppMain.videoReceiverFolderPath);  
                    outputStreamWriter.println(VideoManagerAppMain.videoReceiverFolderPath+"\\"+"youtube-dl -o %(id)s.mp4 --write-info-json "+videoURL);  


                    outputStreamWriter.close();  
                    
                    proc.waitFor();    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                
                
        	i++;
        }
        
    }

    
    
	static void youtubeVideoInfoDownloader() throws IOException {
		
		int counter=0;
		
		
		ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = (ArrayList<VideoDatabaseFeeder>) MainFrame.videoDataGlobalList;
	
		int i=0;
		while(i<videoDatabaseFeederList.size() ) {
	    	
	        
	    	String videoURL=videoDatabaseFeederList.get(i).getVideo_link().replaceAll("\\s+","_");
	
	            try {
	            	Process proc =proc = Runtime.getRuntime().exec("cmd");
	                new Thread(new SyncPipe(proc.getErrorStream(), System.err)).start();
	                new Thread(new SyncPipe(proc.getInputStream(), System.out)).start();
	                
	                OutputStream outputStream = proc.getOutputStream();
	                PrintWriter outputStreamWriter = new PrintWriter(outputStream);     
	                
	                outputStreamWriter.println(VideoManagerAppMain.videoReceiverFolderPath+"\\"+"youtube-dl -o %(id)s.mp4 --write-info-json --skip-download "+videoURL);  
	
	
	                outputStreamWriter.close();  
	                
	                proc.waitFor();    
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            
	            
	            
	    	i++;
	    }
	    
	}
    
    
    
    
    
    
    
	private ArrayList<VideoDatabaseFeeder> fromVideoDatabaseFeedingFileToArrayList() throws IOException {
		
		
		
		FileReader databaseFeederFR=new FileReader(VideoManagerAppMain.databaseFeederFilePath);
        BufferedReader databaseFeederBR = new BufferedReader(databaseFeederFR);
        
    	ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = new ArrayList<VideoDatabaseFeeder>();
    	
    	String videoDatabaseFeederLine=databaseFeederBR.readLine();
        while(videoDatabaseFeederLine != null){

        	String[] splitedVideoLine=videoDatabaseFeederLine.split("#");
        	
        	VideoDatabaseFeeder videoDatabaseFeeder = new VideoDatabaseFeeder();
        	videoDatabaseFeeder.setArtist_name(splitedVideoLine[0]);
        	videoDatabaseFeeder.setArtist_instrument(splitedVideoLine[1]);
        	videoDatabaseFeeder.setVideo_link(splitedVideoLine[2]);
        	videoDatabaseFeeder.setVideo_name(splitedVideoLine[3]);
        	videoDatabaseFeeder.setVideo_duration(splitedVideoLine[4]);
        	videoDatabaseFeeder.setVideo_type(splitedVideoLine[5]);
        	videoDatabaseFeeder.setVideo_id(splitedVideoLine[6]);
	        	

        	videoDatabaseFeederList.add(videoDatabaseFeeder);
        	
        	
        	videoDatabaseFeederLine=databaseFeederBR.readLine();
        }
        
        return videoDatabaseFeederList;
	}

	
	

	
	private void setVideoDownloadFrameInvisible() {
        int i = JOptionPane.showConfirmDialog(this, "are you sure you want to Stop Downloading?");
        if (i == JOptionPane.YES_OPTION) {
        
        	exitVideoDownloadFrame=true;    
        	this.setVisible(false);
        }
    }
    
    
}
