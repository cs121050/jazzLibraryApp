
package videoManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import java.net.URI;

import DAO.JazzLibraryDAO;

/**
*
* @author nick
*/
public class ConfigureResourcesConnectionsFrame extends JFrame {
    
    private JLabel serverName_Lb;
    private JLabel databaseName_Lb;
	private JLabel databaseUsername_Lb;
    private JLabel databasePassword_Lb;
    
    private JLabel artistNamesFilePath_Lb;
    private JLabel databaseFeederFilePath_Lb;
    private JLabel videoReceivingFolderPath_Lb;
    private JLabel searchTagsPath_Lb;
    
    private JLabel databaseWarning_Lb;
    private JLabel resourcesWarning_Lb;
    
    
    private JTextField serverName_Tf;
    private JTextField databaseName_Tf;
    private JTextField databaseUsername_Tf;
    private JTextField databasePassword_Tf;
    
    private JTextField artistNamesFilePath_Tf;
    private JTextField databaseFeederFilePath_Tf;
    private JTextField videoReceivingFolderPath_Tf;
    private JTextField searchTagsPath_Tf;

    
    private JButton databaseConnection_Btn;
 
    private JButton artistNamesFilePath_browse_Btn;
    private JButton databaseFeederFilePath_browse_Btn;    
    private JButton videoReceivingFolder_browse_Btn;
    private JButton searchTagsPath_browse_Btn;
    private JButton resourcesLink_Btn;
    
	JFileChooser fileChouser = new JFileChooser();

    
    public ConfigureResourcesConnectionsFrame() {
       super();

       serverName_Lb=new JLabel("Server Name: ");
       databaseName_Lb=new JLabel("Database Name: ");
       databaseUsername_Lb=new JLabel("Username: ");
       databasePassword_Lb=new JLabel("Password: ");
       
       artistNamesFilePath_Lb=new JLabel("Artist Names File: ");
       databaseFeederFilePath_Lb=new JLabel("Database Feeder File: ");
       videoReceivingFolderPath_Lb=new JLabel("Video Receiving Folder: ");
       searchTagsPath_Lb=new JLabel("Search Tags Path: ");
       
       
       
       
       
       databaseWarning_Lb=new JLabel("");
       resourcesWarning_Lb=new JLabel("");
        
       
       serverName_Tf = new JTextField(10);
       serverName_Tf.setText(VideoManagerApp.serverName);
       databaseName_Tf = new JTextField(10);
       databaseName_Tf.setText(VideoManagerApp.databaseName);
       databaseUsername_Tf = new JTextField(10);
       databaseUsername_Tf.setText(VideoManagerApp.databaseUsername);
       databasePassword_Tf = new JTextField(10);
       databasePassword_Tf.setText(VideoManagerApp.databasePassword);

       
       artistNamesFilePath_Tf = new JTextField(10);
       artistNamesFilePath_Tf.setText(VideoManagerApp.artistNamesFilePath);
       databaseFeederFilePath_Tf = new JTextField(10);
       databaseFeederFilePath_Tf.setText(VideoManagerApp.databaseFeederFilePath);
       videoReceivingFolderPath_Tf = new JTextField(10);
       videoReceivingFolderPath_Tf.setText(VideoManagerApp.videoReceiverFolderPath);
       searchTagsPath_Tf = new JTextField(10);
       searchTagsPath_Tf.setText(VideoManagerApp.searchTagsFilePath);

       
       databaseConnection_Btn = new JButton("Connect");
       
       Icon browse_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\browse_icon.png");
       artistNamesFilePath_browse_Btn = new JButton(browse_icon);
       databaseFeederFilePath_browse_Btn = new JButton(browse_icon);
       videoReceivingFolder_browse_Btn = new JButton(browse_icon);
       searchTagsPath_browse_Btn = new JButton(browse_icon);
       resourcesLink_Btn = new JButton("LINK");

    }
    
    public void prepareUI() throws SQLException {
        
    	JPanel databaseConnectionAtributes = new JPanel();
    	databaseConnectionAtributes.setLayout(new GridLayout(4,2));
    	databaseConnectionAtributes.add(serverName_Lb);
    	databaseConnectionAtributes.add(serverName_Tf);
    	databaseConnectionAtributes.add(databaseName_Lb);
    	databaseConnectionAtributes.add(databaseName_Tf);
    	databaseConnectionAtributes.add(databaseUsername_Lb);
    	databaseConnectionAtributes.add(databaseUsername_Tf);
    	databaseConnectionAtributes.add(databasePassword_Lb);
    	databaseConnectionAtributes.add(databasePassword_Tf);
    	
    	JPanel databaseConnectionButton = new JPanel();
    	databaseConnectionButton.setLayout(new GridLayout(2,1));
    	databaseConnectionButton.add(databaseConnection_Btn);
    	databaseConnectionButton.add(databaseWarning_Lb);
    	
    	JPanel databaseConnection_tabbedPane_Tab = new JPanel();
    	databaseConnection_tabbedPane_Tab.setLayout(new BorderLayout());
    	databaseConnection_tabbedPane_Tab.add(databaseConnectionAtributes, BorderLayout.NORTH);
    	databaseConnection_tabbedPane_Tab.add(databaseConnectionButton, BorderLayout.CENTER);

 
    	
    	
    	JPanel fileResourcesAtributes = new JPanel();
    	fileResourcesAtributes.setLayout(new GridLayout(4,3));
    	fileResourcesAtributes.add(artistNamesFilePath_Lb);
    	fileResourcesAtributes.add(artistNamesFilePath_Tf);
    	fileResourcesAtributes.add(artistNamesFilePath_browse_Btn);
    	fileResourcesAtributes.add(databaseFeederFilePath_Lb);
    	fileResourcesAtributes.add(databaseFeederFilePath_Tf);
    	fileResourcesAtributes.add(databaseFeederFilePath_browse_Btn);
    	fileResourcesAtributes.add(videoReceivingFolderPath_Lb);
    	fileResourcesAtributes.add(videoReceivingFolderPath_Tf);
    	fileResourcesAtributes.add(videoReceivingFolder_browse_Btn);
    	fileResourcesAtributes.add(searchTagsPath_Lb);
    	fileResourcesAtributes.add(searchTagsPath_Tf);
    	fileResourcesAtributes.add(searchTagsPath_browse_Btn);
    	
    	JPanel fileResourcesButton = new JPanel();
    	fileResourcesButton.setLayout(new GridLayout(2,1));
    	fileResourcesButton.add(resourcesLink_Btn);
    	fileResourcesButton.add(resourcesWarning_Lb);
    	
    	JPanel fileResources_tabbedPane_Tab = new JPanel();
    	fileResources_tabbedPane_Tab.setLayout(new BorderLayout());
    	fileResources_tabbedPane_Tab.add(fileResourcesAtributes, BorderLayout.NORTH);
    	fileResources_tabbedPane_Tab.add(fileResourcesButton, BorderLayout.CENTER);
    	
    	
    
    	JTabbedPane tabbedPane = new JTabbedPane();
    	tabbedPane.add(databaseConnection_tabbedPane_Tab,"SQL Database Connection");
    	tabbedPane.add(fileResources_tabbedPane_Tab,"File Resources");
    	
    	this.add(tabbedPane, BorderLayout.CENTER);
    	

    	isDatabaseTFieldAtributesInputCorrect();
    	isResourcesTFieldAtributesInputCorrect();   	
    	
        
        this.setSize(400, 240);
        this.setLocationRelativeTo(null);
        this.setTitle("Configure Resources Connections");
        this.setVisible(true);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

 
         
        artistNamesFilePath_browse_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                            	
            	int fileChouserResponse = fileChouser.showOpenDialog(null);
            	if(fileChouserResponse == JFileChooser.APPROVE_OPTION) {
            		File artistNamesFile = new File(fileChouser.getSelectedFile().getAbsolutePath());
            		
                	artistNamesFilePath_Tf.setText(artistNamesFile.getAbsolutePath());
            	}
            	
            }
        });
        
        databaseFeederFilePath_browse_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	int fileChouserResponse = fileChouser.showOpenDialog(null);
            	if(fileChouserResponse == JFileChooser.APPROVE_OPTION) {
            		File databaseFeederFile = new File(fileChouser.getSelectedFile().getAbsolutePath());
            		
                	databaseFeederFilePath_Tf.setText(databaseFeederFile.getAbsolutePath());
            	}

            }
        });
        
        videoReceivingFolder_browse_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	fileChouser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            	int fileChouserResponse = fileChouser.showOpenDialog(null);
            	if(fileChouserResponse == JFileChooser.APPROVE_OPTION) {
            		File videoReceivingFolder = new File(fileChouser.getSelectedFile().getAbsolutePath());
            		
                	videoReceivingFolderPath_Tf.setText(videoReceivingFolder.getAbsolutePath());
            	}
            }
        });
       
        resourcesLink_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	
            	
                if (isResourcesTFieldAtributesInputCorrect()==true){
                  
                	//grapse arxeio
                	
                	
                	
                	
                	
                	
                	try {
						MainFrame.artistNameList =  MainFrame.readArtistNamesFileToList();
					
                	MainFrame.remainingArtistNames = MainFrame.continiuFromWhereYouStoped();
                
                	MainFrame.fileNameInitialisation();

                	MainFrame.jTableOfFileDisplayInitialisation();
        	        
                	MainFrame.artistNameValue_Jl = new JLabel(MainFrame.remainingArtistNames.get(0)); 
                	
                	} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	
                	
                	
                	
                }
            }
        });

        databaseConnection_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	String serverName=serverName_Tf.getText().trim();
        		String databaseName=databaseName_Tf.getText().trim();
        	    String databaseUsername=databaseUsername_Tf.getText().trim();
        	    String databasePassword=databasePassword_Tf.getText().trim();
            	
        	    URI resourcesConnectionsURI;
				try {
					resourcesConnectionsURI = new URI(VideoManagerApp.resourcesConnections);
				
             
					if (isDatabaseTFieldAtributesInputCorrect()==true){
						
						replaceLineFromFile(6,"serverName#"+serverName            ,resourcesConnectionsURI);
						replaceLineFromFile(7,"databaseName#"+databaseName        ,resourcesConnectionsURI);
						replaceLineFromFile(8,"databaseUsername#"+databaseUsername,resourcesConnectionsURI);
						replaceLineFromFile(9,"databasePassword#"+databasePassword,resourcesConnectionsURI);

						
						
						getConnection();
									    
					}
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
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
    
    
    
    private void getConnection() {
    	
    	String serverName=serverName_Tf.getText().trim();
		String databaseName=databaseName_Tf.getText().trim();
	    String databaseUsername=databaseUsername_Tf.getText().trim();
	    String databasePassword=databasePassword_Tf.getText().trim();
	    
	    DAO.JazzLibraryDAO getConnection = new DAO.JazzLibraryDAO();
	    Connection con =JazzLibraryDAO.getConnection(serverName,databaseName,databaseUsername,databasePassword);		
    	
	    VideoManagerApp.jazzLibraryDBConnection=con;
    }

    
    
    
    private boolean isResourcesTFieldAtributesInputCorrect() {
    	
        
		String artistNamesFilePath=artistNamesFilePath_Tf.getText().trim();;
	    String databaseFeederFilePath=databaseFeederFilePath_Tf.getText().trim();;
	    String searchTagsFilePath=searchTagsPath_Tf.getText().trim();;
	    String videoReceiverFolderPath=videoReceivingFolderPath_Tf.getText().trim();;
		
	
	    File artistNamesFile = new File(artistNamesFilePath);
	    File databaseFeederFile = new File(databaseFeederFilePath);
	    File searchTagsFile = new File(searchTagsFilePath);
	    File videoReceiverFolder = new File(videoReceiverFolderPath);
	
	    
	    
	
	    if(artistNamesFilePath.equals("") || artistNamesFilePath== null||
	    		databaseFeederFilePath.equals("")|| databaseFeederFilePath== null||
	    				videoReceiverFolderPath.equals("")|| videoReceiverFolderPath== null||
	    				searchTagsFilePath.equals("")|| searchTagsFilePath== null){
	    	
		    	if(artistNamesFilePath.equals("") || artistNamesFilePath== null) {
		    		resourcesWarning_Lb.setText("plz determine an artistNamesFilePath.. .");
		    		resourcesWarning_Lb.setForeground(Color.red);
		    		
		    	}
		    	else if(databaseFeederFilePath.equals("")|| databaseFeederFilePath== null) {
		    		resourcesWarning_Lb.setText("plz determine an databaseFeederFilePath.. .");
		    		resourcesWarning_Lb.setForeground(Color.red);
		    		
		    	}
		    	else if(videoReceiverFolderPath.equals("")|| videoReceiverFolderPath== null) {
		    		resourcesWarning_Lb.setText("plz determine an videoReceiverFolderPath.. .");
		    		resourcesWarning_Lb.setForeground(Color.red);
		    		
		    	}
		    	else if(searchTagsFilePath.equals("")|| searchTagsFilePath== null) {
		    		resourcesWarning_Lb.setText("plz determine an searchTagsFilePath.. .");
		    		resourcesWarning_Lb.setForeground(Color.red);
		    		
		    	}
		    	else  {
		    		resourcesWarning_Lb.setText(".. .");
		    		resourcesWarning_Lb.setForeground(Color.red);
		    		
		    	}
	    	
		    	return false;
	    }    
	    else if(artistNamesFile.exists() == false || databaseFeederFile.exists() == false  
	    		|| searchTagsFile.exists() == false  || videoReceiverFolder.exists() == false){
		    	
	    		if(artistNamesFile.exists()== false) {
	    			resourcesWarning_Lb.setText("artistNamesFile does not exist, set a new path and try again");
		    		resourcesWarning_Lb.setForeground(Color.red);
	    			
		    	}
		    	else if(databaseFeederFile.exists()== false) {
		    		resourcesWarning_Lb.setText("databaseFeederFile does not exist, set a new path and try again");
		    		resourcesWarning_Lb.setForeground(Color.red);
	    			
		    	}
		    	else if(searchTagsFile.exists()== false) {
		    		resourcesWarning_Lb.setText("searchTagsFile does not exist, set a new path and try again");
		    		resourcesWarning_Lb.setForeground(Color.red);
	    			
		    	}
		    	else if(videoReceiverFolder.exists()== false) {
		    		resourcesWarning_Lb.setText("videoReceiverFolder does not exist, set a new path and try again");
		    		resourcesWarning_Lb.setForeground(Color.red);
	    			
		    	}
		    	else if(videoReceiverFolder.isDirectory()== false) {
		    		resourcesWarning_Lb.setText("videoReceiverFolder path must be a directory , not a file.. .");
		    		resourcesWarning_Lb.setForeground(Color.red);
	    			
		    	}
		    	else  {
		    		resourcesWarning_Lb.setText(".. .");
		    		resourcesWarning_Lb.setForeground(Color.red);
		    		
		    	}
		    	
	    		return false;        	
	    }   
	    else if(!artistNamesFilePath.contains("\\") || !databaseFeederFilePath.contains("\\") ||
	    		!searchTagsFilePath.contains("\\") || !videoReceiverFolderPath.contains("\\") ){
	    	
		    	if(!artistNamesFilePath.contains("\\")) {
		    		resourcesWarning_Lb.setText("artistNamesFilePath is not a path");
		    		resourcesWarning_Lb.setForeground(Color.red);
	    			
		    	}
		    	else if(!databaseFeederFilePath.contains("\\")) {
		    		resourcesWarning_Lb.setText("databaseFeederFilePath is not a path");
		    		resourcesWarning_Lb.setForeground(Color.red);
	    			
		    	}
		    	else if(!searchTagsFilePath.contains("\\")) {
		    		resourcesWarning_Lb.setText( "searchTagsFilePath is not a path");
		    		resourcesWarning_Lb.setForeground(Color.red);
	    			
		    	}
		    	else if(!videoReceiverFolderPath.contains("\\")) {
		    		resourcesWarning_Lb.setText("videoReceiverFolderPath is not a path");
		    		resourcesWarning_Lb.setForeground(Color.red);
	    			
		    	}
		    	else  {
		    		resourcesWarning_Lb.setText(".. .");
		    		resourcesWarning_Lb.setForeground(Color.red);
		    		
		    	}
		    	
	    		return false;     
		
	    
	    
	    }  
	    else {
	    	resourcesWarning_Lb.setText("resources linkage enstablished");
    		resourcesWarning_Lb.setForeground(Color.green);
	    	return true;
	    	
	    }
    
	}
	
	
	
	
	private boolean isDatabaseTFieldAtributesInputCorrect() throws SQLException {
		
		String serverName=serverName_Tf.getText().trim();
		String databaseName=databaseName_Tf.getText().trim();
	    String databaseUsername=databaseUsername_Tf.getText().trim();
	    String databasePassword=databasePassword_Tf.getText().trim();
	    
	    DAO.JazzLibraryDAO enstablishConnectionTest = new DAO.JazzLibraryDAO();
	    boolean connectionContition =JazzLibraryDAO.enstablishConnectionTest(serverName,databaseName,databaseUsername,databasePassword);

	    if(serverName.equals("") || serverName== null||
		    	databaseName.equals("") || databaseName== null||
		    		databaseUsername.equals("")|| databaseUsername== null||
		    				databasePassword.equals("")|| databasePassword== null) {
	    	
		    	if(databaseName.equals("") || databaseName== null) {
		    		databaseWarning_Lb.setText("plz determine an databaseName.. .");
			    	databaseWarning_Lb.setForeground(Color.red);
		    		
		    	}
		    	else if(databaseUsername.equals("")|| databaseUsername== null) {
		    		databaseWarning_Lb.setText("plz determine an databaseUsername.. .");
			    	databaseWarning_Lb.setForeground(Color.red);
		    		
		    	}
		    	else if(databasePassword.equals("")|| databasePassword== null) {
		    		databaseWarning_Lb.setText("plz determine an databasePassword.. .");
			    	databaseWarning_Lb.setForeground(Color.red);
		    		
		    	}
		    	return false;
	    }    
	    
	    else if(connectionContition==false){
	    	databaseWarning_Lb.setText("Connection failed.. check your input");
	    	databaseWarning_Lb.setForeground(Color.red);
	    	return false;
	    }    
	    else{
	    	databaseWarning_Lb.setText("Connection established successfully.");
	    	databaseWarning_Lb.setForeground(Color.green);
	    	return true;
	    }    	
	    
	    

	}
	
	
	
	
	
	
	
	public int findLineIndexContains(String string, String filePath) throws IOException{
		    
		FileReader flConnectionsLine=new FileReader(filePath);
		BufferedReader brConnectionsLine = new BufferedReader(flConnectionsLine);
		 
		int lineIndex = 0;
		String fileLine=brConnectionsLine.readLine();
	    while(fileLine != null){
	    	
	    	if(fileLine.contains(string)) {
	    		
	    		
	            brConnectionsLine.close();
	    		return lineIndex;
	    	}
	    	
	    	lineIndex++;
	        fileLine=brConnectionsLine.readLine();
	    }
	    
        brConnectionsLine.close();
	    return -1;
	
	}
	
	
	
	
	
	
	
	
	
	    
    
	public static void replaceLineFromFile(int lineNumber, String data,URI fileURI) throws IOException {
	    
	    Path filePath = Paths.get(fileURI);
		List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
	    lines.set(lineNumber, data);
	    Files.write(filePath, lines, StandardCharsets.UTF_8);
	}
    

    

    
    
    private void setInvisible() {
            this.setVisible(false);
    }
    
    
    
    
    
    
    
    
}
