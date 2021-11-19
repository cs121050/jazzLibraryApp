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

import JazzLibraryMethods.*;
/**
*
* @author nick
*/
public class ResourceFileCorrectionFrame extends JFrame {
    
    private JLabel warningMessage_Lb;
    
    private Component  correction_resource_files_icon;

    
    public ResourceFileCorrectionFrame() {
       super();

       ImageIcon  correction_resource_files_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\correction_resource_files_icon.png");
       
       warningMessage_Lb=new JLabel("Do Not Turn Off Until Correction Finish.. .");
       warningMessage_Lb.setIcon(correction_resource_files_icon);

    }
    
    public void prepareUI() {
        
        

    	JPanel addCorrectionPanel = new JPanel();
    	addCorrectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
    	addCorrectionPanel.add(warningMessage_Lb);

    	
    	
    	this.add(addCorrectionPanel, BorderLayout.CENTER);
    	
        
        
        this.setSize(340, 120);
        this.setLocationRelativeTo(null);
        this.setTitle("Resource File Correction");
        this.setVisible(true);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        
        
        
        
        JazzLibraryMethods.FilePathCheck d =new JazzLibraryMethods.FilePathCheck();
     
	    if(d.isDatabaseAtributesInputCorrect()==true){
	        
	    	
	    	
	    	
	    	
	        
	
	    	
	    	this.setVisible(true);
	        

	    	//artistNameFileCorrection();  //opou mesa tha anigei commandLine pou tha exei to output!!!
	    	//databaseFeederFileCorrection();
	    	
	    	
	        this.setVisible(false);
	        
	    }
	    
        
        

        

        
        
        this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
            	setInvisibleVideoDownloaderframe();       
            }
        });

        
   
    }

    
    


    
    
    private void setInvisibleVideoDownloaderframe() {
        int i = JOptionPane.showConfirmDialog(this, "are you sure you want to Stop Correction?");
        if (i == JOptionPane.YES_OPTION) {
        
        	this.setVisible(false);
        }
    }
    
    
}
