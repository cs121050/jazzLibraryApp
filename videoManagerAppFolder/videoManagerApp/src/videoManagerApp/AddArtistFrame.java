
package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
*
* @author nick
*/
public class AddArtistFrame extends JFrame {
    
    private JLabel addSingleAtistName_Lb;
    private JLabel addManyArtistNamesLb;
    private JLabel addArtistNameWarning_Lb;
    
    private JTextField addSingleAtistName_Tf;
    
    private JButton addSingleAtistName_Btn;
    private JButton browseManyArtistNames_Btn;

	JFileChooser fileChouser = new JFileChooser();

	
    public AddArtistFrame() {
       super();

       addSingleAtistName_Lb=new JLabel("Add Single Artist Name: ");
       addManyArtistNamesLb=new JLabel("Add Many Artist Name:");
       addArtistNameWarning_Lb=new JLabel("");

       addSingleAtistName_Tf = new JTextField(10);
        
       Icon browse_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\browse_icon.png");
       
       addSingleAtistName_Btn = new JButton("Go");
       browseManyArtistNames_Btn = new JButton(browse_icon);

    }
    
    public void prepareUI() {
        
       if(!VideoManagerAppMain.isResourcesAtributesInputCorrect()) {
    	   addArtistNameWarning_Lb.setText("\t artistNameFile Path is not correct .. .");
    	   addArtistNameWarning_Lb.setForeground(Color.red);
       }
        	

    	
    	JPanel addAristNamePanel = new JPanel();
    	addAristNamePanel.setLayout(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
    	
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	addAristNamePanel.add(addSingleAtistName_Lb,gbc);
    	
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	addAristNamePanel.add(addSingleAtistName_Tf,gbc);
    	
    	gbc.gridx = 2;
    	gbc.gridy = 0;
    	addAristNamePanel.add(addSingleAtistName_Btn,gbc);
    	

	    	gbc.gridx = 0;
	    	gbc.gridy = 1;
	    	addAristNamePanel.add(new JLabel("."),gbc);
	    	
	    	gbc.gridx = 1;
	    	gbc.gridy = 1;
	    	addAristNamePanel.add(new JLabel("."),gbc);
	    	

    	gbc.gridx = 0;
    	gbc.gridy = 2;
    	addAristNamePanel.add(addManyArtistNamesLb,gbc);
    	
    	gbc.gridx = 2;
    	gbc.gridy = 2;
    	addAristNamePanel.add(browseManyArtistNames_Btn,gbc);
    	
    	
    	
    	this.add(addAristNamePanel, BorderLayout.CENTER);
    	this.add(addArtistNameWarning_Lb, BorderLayout.SOUTH);
        
        
        this.setSize(420, 160);
        this.setLocationRelativeTo(null);
        this.setTitle("Add Artist");
        this.setVisible(true);
        setResizable(false); 
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


       

        addSingleAtistName_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                

            	FileWriter artistNameFileW;
				try {
					artistNameFileW = new FileWriter(VideoManagerAppMain.artistNamesFilePath, true);
					BufferedWriter artistNameBW = new BufferedWriter(artistNameFileW);
	                PrintWriter artistNamePW = new PrintWriter(artistNameBW);
					
	            	String newArtistName=addSingleAtistName_Tf.getText().trim();
	                	                
	                artistNamePW.println(newArtistName);
	                artistNamePW.close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
            	
            	
            	
            	
            	
            	setInvisible();
            }
        });

        browseManyArtistNames_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            	
            	try {
            		int fileChouserResponse = fileChouser.showOpenDialog(null);
	            	if(fileChouserResponse == JFileChooser.APPROVE_OPTION) {
	            		File artistNamesFile = new File(fileChouser.getSelectedFile().getAbsolutePath());
	                	String newArtistNamesFilePath = artistNamesFile.getAbsolutePath();
	                	
	                	
	                	FileReader newArtistNameFR=new FileReader(newArtistNamesFilePath);
	                	BufferedReader newArtistNameBR = new BufferedReader(newArtistNameFR);
	                	
	                	FileWriter artistNameFileW = new FileWriter(VideoManagerAppMain.artistNamesFilePath, true);
	 					BufferedWriter artistNameBW = new BufferedWriter(artistNameFileW);
	 	                PrintWriter artistNamePW = new PrintWriter(artistNameBW);
	 	                
	 	                
	                    String searchTagsLine=newArtistNameBR.readLine();
	                    while(searchTagsLine != null){
            	        	
	                    	artistNamePW.println(searchTagsLine);
	                    	
	                    	
		                    searchTagsLine=newArtistNameBR.readLine();
	                    }
	                	

	                    artistNamePW.close();
		            	newArtistNameBR.close();
	            	}		
	            	
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            	setInvisible();
            }
        });
        
   

        this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
            	setInvisible();
            }
        });

        
   
    }

        
        



    private void setInvisible() {

    	this.setVisible(false);
    }

    
}
