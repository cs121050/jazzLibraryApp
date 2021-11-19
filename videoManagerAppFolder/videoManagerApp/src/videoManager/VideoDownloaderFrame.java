
package videoManager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;


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
       
       warningMessage_Lb=new JLabel("Do Not Turn Off Until Finish.. .");
       warningMessage_Lb.setIcon(download_icon_large);

    }
    
    public void prepareUI() {
        
        

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


        File databaseFeederFile = new File(VideoManagerApp.databaseFeederFilePath);
	    if(databaseFeederFile.exists()){
	        
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	this.setVisible(true);
	        

	        //downloadVideos();   //opou mesa tha anigei commandLine pou tha exei to output!!!

	    	
	        this.setVisible(false);
	        
	    }
	    else { 
	    	JOptionPane.showMessageDialog(this, "Databade Feading file can't found , go to Settings and comfigure the Path", "Warning",
                    JOptionPane.WARNING_MESSAGE);
	    }

        

        
        
	    
	    
	    
        
        this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
            	setInvisibleVideoDownloaderframe();       
            }
        });

        
   
    }



    
    
    private void setInvisibleVideoDownloaderframe() {
        int i = JOptionPane.showConfirmDialog(this, "are you sure you want to Stop Downloading?");
        if (i == JOptionPane.YES_OPTION) {
        
        	this.setVisible(false);
        }
    }
    
    
}
