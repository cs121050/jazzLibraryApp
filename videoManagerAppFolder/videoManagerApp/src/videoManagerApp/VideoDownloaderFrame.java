
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
import java.text.DateFormat;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

import youtubeDownloaderSyncPipe.*;


/**
*
* @author nick
*/
public class VideoDownloaderFrame extends JFrame {
    
    private JLabel warningMessage_Lb;
    
    private Component  download_icon_large;

    
    public VideoDownloaderFrame() {
       super();

       ImageIcon  download_icon_large = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\download_icon_large.png");
       
       warningMessage_Lb=new JLabel("");
       warningMessage_Lb.setIcon(download_icon_large);

    }
    
    public void prepareUI() throws IOException {
        
        

    	JPanel addAristNamePanel = new JPanel();
    	addAristNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
    	addAristNamePanel.add(warningMessage_Lb);

    	
    	
    	this.add(addAristNamePanel, BorderLayout.CENTER);
    	
        
        
        this.setSize(300, 120);
        this.setLocationRelativeTo(null);
        this.setTitle("Video Downloader");
        this.setVisible(false);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        
        
        
	    if(VideoManagerApp.isResourcesAtributesInputCorrect()==true){
	        
	    	warningMessage_Lb.setText("Do Not Turn Off Until Finish.. .");
	    	warningMessage_Lb.setForeground(Color.green);
	    	
			ArrayList<VideoDatabaseFeeder> updatedVideoDatabaseFeeder = downloadVideosAndUpdateFeedingFile();  

			writeVideoArrayListToFile(updatedVideoDatabaseFeeder,VideoManagerApp.databaseFeederFilePath);
	    }
	    else { 
	    	
	    	warningMessage_Lb.setText("Databade Feading file can't found , go to Settings and comfigure the Path");
	    	warningMessage_Lb.setForeground(Color.red);
	    	
	    }

        

        
        
	    
	    
	    
        
        this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
            	setInvisible();       
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
    
    
    private ArrayList<VideoDatabaseFeeder> downloadVideosAndUpdateFeedingFile() throws IOException {

    	
    	
        
        String[] cmd = { "cmd"};


        
        
        
        
        
		ArrayList<VideoDatabaseFeeder> videoDatabaseFeeder = fromVideoDatabaseFeedingFileToArrayList();

		
		ArrayList<VideoDatabaseFeeder> updatedVideoDatabaseFeeder = fromVideoDatabaseFeedingFileToArrayList();

                 
        
        for(int i=0;i<videoDatabaseFeeder.size();i++){
        	
        	
        	
        	
        	   
            
            
            
        	String videoURL=videoDatabaseFeeder.get(i).getVideo_link().replaceAll("\\s+","_");

            
                try {
                	Process proc =proc = Runtime.getRuntime().exec(cmd);
                    new Thread(new SyncPipe(proc.getErrorStream(), System.err)).start();
                    new Thread(new SyncPipe(proc.getInputStream(), System.out)).start();
                    
                    OutputStream outputStream = proc.getOutputStream();
                    PrintWriter outputStreamWriter = new PrintWriter(outputStream);     
                    
                    InputStream inputStream = proc.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    
                    outputStreamWriter.println(VideoManagerApp.videoReceiverFolderPath+"\\"+"youtube-dl -o %(id)s --write-info-json "+videoURL);  

                    
                    
                    if(videoDatabaseFeeder.get(i).getVideo_name().equals("???") || videoDatabaseFeeder.get(i).getVideo_duration().equals("???")  )  {
	                    
                    	
	                    
	                    
                    	//dibase to json arxeio ,, me onoma to Video_Id  .info.json   ,,,,,,,  kanto split , kai pare to onoma kai to duration

	                    //videoDatabaseFeeder.get(i).setVideo_name(videosTitle);
	                    //videoDatabaseFeeder.get(i).setVideo_duration(videosDuration);
	                    
	                    updatedVideoDatabaseFeeder.add(videoDatabaseFeeder.get(i));
                    }
                    else {
	                    updatedVideoDatabaseFeeder.add(videoDatabaseFeeder.get(i));
                    }
                    
                    
                    
                    

                    
 
                	

                    outputStreamWriter.close();  

                    
                    proc.waitFor();
                    
                    

                    
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

            
                             
            
        }
    	
    	
        
        
        
        return updatedVideoDatabaseFeeder;
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

	
	
	
	
	private void setInvisible() {
        int i = JOptionPane.showConfirmDialog(this, "are you sure you want to Stop Downloading?");
        if (i == JOptionPane.YES_OPTION) {
        
        	this.setVisible(false);
        }
    }
    
    
}
