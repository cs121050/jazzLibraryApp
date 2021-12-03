
package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import JazzLibraryClassies.VideoDatabaseFeeder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import youtubeDownloaderSyncPipe.*;



public class VideoDownloaderFrame extends JFrame {

        
	    private JLabel warningMessage_Lb;

	    public static StringBuffer progress_Ta;  

	    
	    
	    public VideoDownloaderFrame() {
	       super();

	       
	       warningMessage_Lb=new JLabel("n/a");
	       
	       
	       progress_Ta=new StringBuffer("");
	       
	    }
	    
	    
	    public void prepareUI() throws Exception {
	        
	    	
	    	this.add(warningMessage_Lb, BorderLayout.NORTH);	    	
	        
	        
	        this.setSize(300, 80);
	        this.setLocationRelativeTo(null);
	        this.setTitle("Video Downloader");
	        this.setVisible(true);
	        this.setResizable(false);
	        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);



	        
	        
		    if(VideoManagerApp.isResourcesAtributesInputCorrect()==true){
		        
		    	warningMessage_Lb.setText("Do Not Turn Off Until Finish.. .");
		    	warningMessage_Lb.setForeground(Color.red);
		    	

		    	JOptionPane.showMessageDialog(this,
		                "Video Downloading is Starting ,it might take over 10 hours so be patient , if you close it next time it will continue where it stoped", "Coded Message",
		                JOptionPane.INFORMATION_MESSAGE);
		    	
		    	progress_Ta.append("~Video Downloading~  (1/2)"+"\n");
		    	youtubeVideoDownloader();  


		    	progress_Ta.append("~Video Namegiving~  (2/2)"+"\n");
				ArrayList<VideoDatabaseFeeder> updatedVideoDatabaseFeeder = videoDataBaseFeedingFilleNameDurationGiver();
				writeVideoArrayListToFile(updatedVideoDatabaseFeeder,VideoManagerApp.databaseFeederFilePath);


		    	progress_Ta.append("~Downloading Complited Successfully"+"\n");
		    	
		    	
		    }
		    else { 
		    	
		    	warningMessage_Lb.setText("Databade Feading file can't found , go to Settings and comfigure the Path");
		    	warningMessage_Lb.setForeground(Color.red);
		    	
		    }
		    
		    
		    
		    
		    
		    this.addWindowListener(new WindowAdapter() {
	            @Override                               
	            public void windowClosing(WindowEvent e) {
	            	
	            	exitApp();       
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
    
    
    
    
    private ArrayList<VideoDatabaseFeeder> videoDataBaseFeedingFilleNameDurationGiver() throws Exception {
	
    	int counter=0;
    		
		ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = fromVideoDatabaseFeedingFileToArrayList();

		JSONParser parser = new JSONParser();
		
        for(int i=0;i<videoDatabaseFeederList.size();i++){

        	counter++;
	    	progress_Ta.append(""+counter+"\\"+videoDatabaseFeederList.size()+"\n");
        	
            File videoInfoJsonFile = new File(VideoManagerApp.videoReceiverFolderPath+"\\"+videoDatabaseFeederList.get(i).getVideo_id()+".json");           
            if( (videoDatabaseFeederList.get(i).getVideo_name().equals("???") 
            		|| videoDatabaseFeederList.get(i).getVideo_duration().equals("???")
            			) && videoInfoJsonFile.exists() )  {
                
            	
            	String videpJsonInfoString = readFileAsString(VideoManagerApp.videoReceiverFolderPath+"\\"+videoDatabaseFeederList.get(i).getVideo_id()+".json");            	
            	String[] spitedVideoInfoJsonString1 = videpJsonInfoString.split("\"title\": ");
            	String[] isolateVideoTitleValueString = spitedVideoInfoJsonString1[1].split(",");
                String videoTitleValue = isolateVideoTitleValueString[0].replaceAll("\"", "");
            	
                String[] spitedVideoInfoJsonString2 = videpJsonInfoString.split("\"duration\": ");
            	String[] isolateDurationValueString = spitedVideoInfoJsonString2[1].split(",");
                String durationValue = isolateDurationValueString[0].replaceAll("\"", "");
            	

                videoDatabaseFeederList.get(i).setVideo_name(videoTitleValue);
                videoDatabaseFeederList.get(i).setVideo_duration(durationValue);
            }

        }

        return videoDatabaseFeederList;
	}
    
    
    
    
	public static String readFileAsString(String file)throws Exception
	{
	    return new String(Files.readAllBytes(Paths.get(file)));
	}
    
    
    
    private void youtubeVideoDownloader() throws IOException {
    	
    	int counter=0;
    	
    	
		ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = fromVideoDatabaseFeedingFileToArrayList();

        for(int i=0;i<videoDatabaseFeederList.size();i++){
        	
        	counter++;
	    	progress_Ta.append(""+counter+"\\"+videoDatabaseFeederList.size()+"\n");
            
        	String videoURL=videoDatabaseFeederList.get(i).getVideo_link().replaceAll("\\s+","_");

                try {
                	Process proc =proc = Runtime.getRuntime().exec("cmd");
                    new Thread(new SyncPipe(proc.getErrorStream(), System.err)).start();
                    new Thread(new SyncPipe(proc.getInputStream(), System.out)).start();
                    
                    OutputStream outputStream = proc.getOutputStream();
                    PrintWriter outputStreamWriter = new PrintWriter(outputStream);     
                    
                    outputStreamWriter.println(VideoManagerApp.videoReceiverFolderPath+"\\"+"youtube-dl -o %(id)s --write-info-json "+videoURL);  


                    outputStreamWriter.close();  
                    
                    proc.waitFor();    
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        
    }

    
    
    
    
    
    
    
    
	private ArrayList<VideoDatabaseFeeder> fromVideoDatabaseFeedingFileToArrayList() throws IOException {
		
		
		
		FileReader databaseFeederFR=new FileReader(VideoManagerApp.databaseFeederFilePath);
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

	
	
	
	private void exitApp() {
        int i = JOptionPane.showConfirmDialog(MainFrame.this, "are you sure you want to Exit?");
        if (i == JOptionPane.YES_OPTION) {
        
            System.exit(0);
        }
	
	
	private void setInvisible() {
        int i = JOptionPane.showConfirmDialog(this, "are you sure you want to Stop Downloading?");
        if (i == JOptionPane.YES_OPTION) {
        
        	this.setVisible(false);
        }
    }
    
    
}
