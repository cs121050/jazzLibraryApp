package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import JazzLibraryClassies.VideoDatabaseFeeder;

public class VideoDownloaderProgressFrame {

	public static void main(String[] args) {

        
	    private JLabel warningMessage_Lb;

	    private JScrollPane progress_Sp;  
	    public static JTextArea progress_Ta;  

	    
	    
	    public VideoDownloaderFrame() {
	       super();

	       
	       warningMessage_Lb=new JLabel("n/a");
	       
	       
	       progress_Ta=new JTextArea("");
	       progress_Ta.setBounds(0,0,300,100);
	       progress_Sp = new JScrollPane();
	       progress_Sp.setBackground(Color.GREEN);
	       progress_Sp.add(progress_Ta);
	       
	    }
	    
	    
	    public void prepareUI() throws Exception {
	        
	    	
	    	this.add(warningMessage_Lb, BorderLayout.NORTH);
	    	this.add(progress_Sp, BorderLayout.CENTER);
	    	
	        
	        
	        this.setSize(300, 300);
	        this.setLocationRelativeTo(null);
	        this.setTitle("Video Downloader");
	        this.setVisible(true);
	        this.setResizable(false);
	        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


	        
	        
	        
		    if(VideoManagerApp.isResourcesAtributesInputCorrect()==true){
		        
		    	warningMessage_Lb.setText("Do Not Turn Off Until Finish.. .");
		    	warningMessage_Lb.setForeground(Color.red);
		    	
		    	
		    	
		    	
		          
		    	
		    	progress_Ta.append("~Video Downloading~  (1/2)"+"\n");
			    	JOptionPane.showMessageDialog(this,
			                "Video Downloading is Starting ,it will take a long time , be patient", "Coded Message",
			                JOptionPane.INFORMATION_MESSAGE);
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
	            	setInvisible();       
	            }
	        });

	        
	   
	    }

}
