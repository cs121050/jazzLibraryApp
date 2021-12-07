package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import JazzLibraryClassies.VideoDatabaseFeeder;

import java.util.ArrayList;



/**
*
* @author nick
*/
public class ResourceFileCorrectionFrame extends JFrame {
    
    private JLabel resourceFileCorrectionWarning_Lb;
	
    private JCheckBox artistNameFileCorrection_Cb;
    private JCheckBox videoDBFeederFileCorrection_Cb;

    private JButton correction_Btn;

    
    public ResourceFileCorrectionFrame() {
       super();
       
       resourceFileCorrectionWarning_Lb=new JLabel("");


       artistNameFileCorrection_Cb = new JCheckBox("Artist Name File Correction");
       videoDBFeederFileCorrection_Cb = new JCheckBox("Video DB Feeder File Correction");

       correction_Btn = new JButton("FileCorrection");

    }
    
    public void prepareUI() {
        
    	if(!VideoManagerAppMain.isResourcesAtributesInputCorrect()) {
     	   resourceFileCorrectionWarning_Lb.setText("artistNameFile or videoDBFeederFile Path is not correct .. .");
     	   resourceFileCorrectionWarning_Lb.setForeground(Color.red);
        }
    	
    	JPanel chooseFileCorrectionPanel = new JPanel();
        chooseFileCorrectionPanel.setLayout(new GridLayout(4,1));
    	
        chooseFileCorrectionPanel.add(artistNameFileCorrection_Cb);
        chooseFileCorrectionPanel.add(videoDBFeederFileCorrection_Cb);
        
        chooseFileCorrectionPanel.add(correction_Btn);
        chooseFileCorrectionPanel.add(resourceFileCorrectionWarning_Lb);
    	
    	
    	this.add(chooseFileCorrectionPanel, BorderLayout.CENTER);
    	
        
        
        this.setSize(340, 120);
        this.setLocationRelativeTo(null);
        this.setTitle("Resource File Correction");
        this.setVisible(true);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        
        
        correction_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
	            	if(artistNameFileCorrection_Cb.isSelected()) {
	            		
						artistNameFileCorrection();
	            	}
	            	else if(videoDBFeederFileCorrection_Cb.isSelected()) {
	            		videoDBFeedingFileCorrection();
	            	}
	            	else if(artistNameFileCorrection_Cb.isSelected() && videoDBFeederFileCorrection_Cb.isSelected()) {
	            		artistNameFileCorrection();
	            		videoDBFeedingFileCorrection();
	            	}
	            	setInvisible();
	            	
            	} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            }
            
        });
        
        

        
        this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
            	setInvisible();       
            }
        });

        
        
        
   
    }

    
    
    
    
    
    private void artistNameFileCorrection() throws IOException {

    	
    	FileReader artistNameFR=new FileReader(VideoManagerAppMain.artistNamesFilePath);
    	BufferedReader artistNameBR = new BufferedReader(artistNameFR);
    	
    	ArrayList<String> artistNames = new ArrayList<String>();
    	
        String artistNameFileLine=artistNameBR.readLine();
        while(artistNameFileLine != null){
        	
        	artistNameFileLine = stringNormalizer(artistNameFileLine);
        	
        	artistNameFileLine = artistNameFileLine.toLowerCase();
        	
        	artistNames.add(artistNameFileLine);

        	
        	artistNameFileLine=artistNameBR.readLine();
        }
        artistNameBR.close();
        
        artistNames = RemoveArtistNameDuplication(artistNames);

        writeStringArrayListToFile(artistNames,VideoManagerAppMain.artistNamesFilePath);

	}
    
    
    
	private void videoDBFeedingFileCorrection() throws IOException {
	
	    	
	    	FileReader videoFR=new FileReader(VideoManagerAppMain.databaseFeederFilePath);
	    	BufferedReader videoBR = new BufferedReader(videoFR);
	    	
	    	ArrayList<VideoDatabaseFeeder> videoDatabaseFeederList = new ArrayList<VideoDatabaseFeeder>();
	    	
	        String videoLine=videoBR.readLine();
	        while(videoLine != null){
				System.out.println(videoLine);


	        	String[] splitedVideoLine=videoLine.split("#");
	        	
	        	String[] splitLink=splitedVideoLine[2].trim().split("=");
	    		String linkWithoutSeconds=splitLink[0]+"="+splitLink[1];
	        	
	        	VideoDatabaseFeeder videoDatabaseFeeder = new VideoDatabaseFeeder();
	        	videoDatabaseFeeder.setArtist_name(splitedVideoLine[0]);
	        	videoDatabaseFeeder.setArtist_instrument(splitedVideoLine[1]);
	        	videoDatabaseFeeder.setVideo_link(linkWithoutSeconds);
	        	videoDatabaseFeeder.setVideo_name(splitedVideoLine[3]);
	        	videoDatabaseFeeder.setVideo_duration(splitedVideoLine[4]);
	        	videoDatabaseFeeder.setVideo_type(splitedVideoLine[5]);
	        	videoDatabaseFeeder.setVideo_id(splitedVideoLine[6]);
		        	
	        	
		        if(videoDatabaseFeeder.getVideo_link().contains("watch?v=")) {
		        	videoDatabaseFeederList.add(videoDatabaseFeeder);
		        }
	        	
	        	
	        	videoLine=videoBR.readLine();
	        }
	        
	        
	        videoBR.close();
	        
	        videoDatabaseFeederList = RemoveVideoDuplication(videoDatabaseFeederList);
	
	        writeVideoArrayListToFile(videoDatabaseFeederList,VideoManagerAppMain.databaseFeederFilePath);

	}

	    
	private void writeVideoArrayListToFile(ArrayList<VideoDatabaseFeeder> videos, String databaseFeederFilePath) throws IOException {

		FileWriter fw = new FileWriter(databaseFeederFilePath);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        for(int i=0;i<videos.size();i++)
        	pw.println(videos.get(i).toString());
        
        pw.close();
	}

	private ArrayList<VideoDatabaseFeeder> RemoveVideoDuplication(ArrayList<VideoDatabaseFeeder> videoList) {
			
			ArrayList<VideoDatabaseFeeder> newVideoList = new ArrayList<VideoDatabaseFeeder>();
			boolean artistNameDublicationFound = false;
			
			for(int i=0;i<videoList.size();i++) {
				for(int j=0;j<newVideoList.size();j++)
					if(videoList.get(i).getVideo_link().equals(newVideoList.get(j).getVideo_link())) {
						artistNameDublicationFound = true;
						System.out.println("Duplication Captured");
						break;
					}

				if(artistNameDublicationFound==false)	
					newVideoList.add(videoList.get(i));	
				else
					artistNameDublicationFound = false;
				
			}	
			
			return newVideoList;
		}

	
	
	
	
	
	
	
	
	

	
	
	private void writeStringArrayListToFile(ArrayList<String> list, String databaseFeederFilePath) throws IOException {
    	
    	FileWriter fw = new FileWriter(databaseFeederFilePath);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        for(int i=0;i<list.size();i++)
        	pw.println(list.get(i).toString());
        
        
        pw.close();
  
    }
	
	
	
	
	
	
	
	
	
	private ArrayList<String> RemoveArtistNameDuplication(ArrayList<String> artistNames) {
		
		ArrayList<String> newArtistNames = new ArrayList<String>();
		boolean artistNameDublicationFound = false;
		
		for(int i=0;i<artistNames.size();i++) {
			for(int j=0;j<newArtistNames.size();j++)
				if(artistNames.get(i).equals(newArtistNames.get(j))) {
					artistNameDublicationFound = true;
					break;
				}
			if(artistNameDublicationFound==false)	
				newArtistNames.add(artistNames.get(i));	
		}	
		
		return artistNames;
	}

	










    

	private String stringNormalizer(String videoLine) {

    	 String normalized = Normalizer.normalize(videoLine, Normalizer.Form.NFD);
    	 String accentRemoved = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    	
    	 return accentRemoved;
	}
	
	
	
	

	private void setInvisible() {
            this.setVisible(false);
    }
    
    
    
    
    
}
