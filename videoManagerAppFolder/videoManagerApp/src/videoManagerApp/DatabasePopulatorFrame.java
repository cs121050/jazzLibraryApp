
package videoManagerApp;

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
public class DatabasePopulatorFrame extends JFrame {
    
    private JLabel warningMessage_Lb;
    
    private ImageIcon  database_populator_icon;

    
    public DatabasePopulatorFrame() {
       super();

       ImageIcon  database_populator_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\database_populator_icon.png");
       
       warningMessage_Lb=new JLabel("Do Not Turn Off Until Population Finish.. .");
       warningMessage_Lb.setIcon(database_populator_icon);

    }
    
    public void prepareUI() {
        
        

    	JPanel databasePopulationPanel = new JPanel();
    	databasePopulationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
    	databasePopulationPanel.add(warningMessage_Lb);

    	
    	
    	this.add(databasePopulationPanel, BorderLayout.CENTER);
    	
        
        
        this.setSize(300, 140);
        this.setLocationRelativeTo(null);
        this.setTitle("Database Population");
        this.setVisible(false);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        File databaseFeederFile = new File(VideoManagerApp.databaseFeederFilePath);
	    if(databaseFeederFile.exists()){
	        
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	this.setVisible(true);
	        

	        //databasePopulation();     //opou mesa tha anigei commandLine pou tha exei to output!!!    //elenxos an exoume sidesh , ME IDIO TROPO ! 

	    	
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
        int i = JOptionPane.showConfirmDialog(this, "are you sure you want to Stop Population?");
        if (i == JOptionPane.YES_OPTION) {
        
        	this.setVisible(false);
        }
    }
    
    
}
