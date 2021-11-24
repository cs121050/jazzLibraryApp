/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoManagerApp;



import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import DAO.JazzLibraryDAO;


/**
 *
 * @author nick
 */
public class VideoManagerApp extends JFrame  {
	
	
	public static String resourcesConnections="C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\connectionsOKAA.txt";

	
	public static String artistNamesFilePath="";
	public static String databaseFeederFilePath="";
	public static String videoReceiverFolderPath="";
	public static String searchTagsFilePath="";
	public static String databaseStructureFile="";

	
	public static String serverName="";
	public static String databaseName="";
	public static String databaseUsername="";
	public static String databasePassword="";
	
	public static Connection jazzLibraryDBConnection=null;



	
	public static boolean isLogedInToServer=false;


    public static void main(String[] args) throws IOException{

    		
    	
    	connectionsInitialisation();
    	
    	
    	
    	
    	
    	

    	try {
			if (isDatabaseAtributesInputCorrect()==true){
				DAO.JazzLibraryDAO getConnection = new DAO.JazzLibraryDAO();
				jazzLibraryDBConnection =JazzLibraryDAO.getConnection(serverName,databaseName,databaseUsername,databasePassword);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
    	
    	
    	
    	
        MainFrame mainFrame = new MainFrame();
        mainFrame.prepareUI();

    }
 
	
    
    
    
    
    
    
    
    
    
    public static void connectionsInitialisation() throws IOException {
        	
        	FileReader flConnectionsLine=new FileReader(resourcesConnections);
    		BufferedReader brConnectionsLine = new BufferedReader(flConnectionsLine);

    		ArrayList<String[]> splitedLineToNameAndPath = new ArrayList<String[]>();
    		
            String connectionsLine=brConnectionsLine.readLine();
            while(connectionsLine != null){
            	
        		String[] splitConnectionsLine=connectionsLine.trim().split("#");
        		splitedLineToNameAndPath.add(splitConnectionsLine);

                connectionsLine=brConnectionsLine.readLine();
            }
            
            brConnectionsLine.close(); 

            
            // den ta fortonei kala
            for(int i=0;i<splitedLineToNameAndPath.size();i++) {

    			if(splitedLineToNameAndPath.get(i)[0].equals("artistNamesFilePath"))
            		artistNamesFilePath=splitedLineToNameAndPath.get(i)[1];
            	
            	else if(splitedLineToNameAndPath.get(i)[0].equals("databaseFeederFilePath"))
            		databaseFeederFilePath=splitedLineToNameAndPath.get(i)[1];
            	
            	else if(splitedLineToNameAndPath.get(i)[0].equals("videoReceiverFolderPath"))
            		videoReceiverFolderPath=splitedLineToNameAndPath.get(i)[1];
            	
            	else if(splitedLineToNameAndPath.get(i)[0].equals("searchTagsPath"))
            		searchTagsFilePath=splitedLineToNameAndPath.get(i)[1];
    			
            	else if(splitedLineToNameAndPath.get(i)[0].equals("databaseStructureFile"))
            		databaseStructureFile=splitedLineToNameAndPath.get(i)[1];
           
    			
            	
            	else if(splitedLineToNameAndPath.get(i)[0].equals("serverName"))
            		serverName=splitedLineToNameAndPath.get(i)[1];
            	
            	else if(splitedLineToNameAndPath.get(i)[0].equals("databaseUsername"))
            		databaseUsername=splitedLineToNameAndPath.get(i)[1];
            	
            	else if(splitedLineToNameAndPath.get(i)[0].equals("databasePassword"))
            		databasePassword=splitedLineToNameAndPath.get(i)[1];
            	
            	else if(splitedLineToNameAndPath.get(i)[0].equals("databaseName"))
            		databaseName=splitedLineToNameAndPath.get(i)[1];
            	
            	
             }
            
        	
        }


    
    
    
    
    
    
    


    		    
    public static boolean isResourcesAtributesInputCorrect() {
    	
    	
    	String artistNamesFilePath=VideoManagerApp.artistNamesFilePath;
        String databaseFeederFilePath=VideoManagerApp.databaseFeederFilePath;
        String searchTagsFilePath=VideoManagerApp.searchTagsFilePath;
        String videoReceiverFolderPath=VideoManagerApp.videoReceiverFolderPath;
    	

        File artistNamesFile = new File(VideoManagerApp.artistNamesFilePath);
        File databaseFeederFile = new File(VideoManagerApp.databaseFeederFilePath);
        File searchTagsFile = new File(VideoManagerApp.searchTagsFilePath);
        File videoReceiverFolder = new File(VideoManagerApp.videoReceiverFolderPath);

        
        

        if(artistNamesFilePath.equals("") || artistNamesFilePath== null||
        		databaseFeederFilePath.equals("")|| databaseFeederFilePath== null||
        				videoReceiverFolderPath.equals("")|| videoReceiverFolderPath== null||
        				searchTagsFilePath.equals("")|| searchTagsFilePath== null){
        	
        	
    	    	return false;
        }    
        else if(!artistNamesFile.exists() || !databaseFeederFile.exists() || !searchTagsFile.exists() ||
        		!videoReceiverFolder.exists() || !videoReceiverFolder.isDirectory()){
    	    	
        		
    	    	
        		return false;        	
        }   
        else if(!artistNamesFilePath.contains("\\") || !databaseFeederFilePath.contains("\\") ||
        		!searchTagsFilePath.contains("\\") || !videoReceiverFolderPath.contains("\\") ){
        	
    	    	
        	
        	
    	    	return false;     
    	
        
        
        }  
        else
        	return true;

    }




    public static boolean isDatabaseAtributesInputCorrect() throws SQLException {
    	
    	String serverName=VideoManagerApp.serverName;
    	String databaseName=VideoManagerApp.databaseName;
        String databaseUsername=VideoManagerApp.databaseUsername;
        String databasePassword=VideoManagerApp.databasePassword;
        
        DAO.JazzLibraryDAO enstablishConnectionTest = new DAO.JazzLibraryDAO();
        boolean connectionContition =JazzLibraryDAO.enstablishConnectionTest(serverName,databaseName,databaseUsername,databasePassword);

        if(databaseName.equals("") || databaseName== null||
        		databaseUsername.equals("")|| databaseUsername== null||
        		databasePassword.equals("")|| databasePassword== null){

    	
        	return false;
        }    
        
        
        if(databaseName.equals("") || databaseName== null||
        		databaseUsername.equals("")|| databaseUsername== null||
        				databasePassword.equals("")|| databasePassword== null) {
        	
    	    	
    	    	return false;
        }    
        else if(connectionContition==false){
        	
        	
        	return false;
        }    
        else{
        	
        	return true;
        }    	
    }

       
		    
    
    
}
