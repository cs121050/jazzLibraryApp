/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoManagerApp;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.JazzLibraryDAO;


/**
 *
 * @author nick
 */
public class VideoManagerAppMain{
	
	
	public static String resourcesConnections="C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\connectionsOKAA.txt";
	public static String resourcesFolder="C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\text_resorses";
	
	public static String databaseFeederFileMASTERPath="";
	public static String artistNamesFilePath="";
	public static String databaseFeederFilePath="";
	public static String videoReceiverFolderPath="";
	public static String searchTagsFilePath="";
	public static String databaseStructureFilePath="";
	public static String splashScreenQuotes="";
	
	public static String serverName="";
	public static String databaseName="";
	public static String databaseUsername="";
	public static String databasePassword="";
	  

	
	public static boolean isLogedInToServer=false;


    public static void main(String[] args) throws IOException{

    		
    	
    	connectionsInitialisation();
    	
    	
    	
    	
    	
    	
    	
    	
    	
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
    			
            	else if(splitedLineToNameAndPath.get(i)[0].equals("databaseStructureFilePath"))
            		databaseStructureFilePath=splitedLineToNameAndPath.get(i)[1];
           
            	else if(splitedLineToNameAndPath.get(i)[0].equals("databaseFeederFileMASTERPath"))
            		databaseFeederFileMASTERPath=splitedLineToNameAndPath.get(i)[1];
    			
            	else if(splitedLineToNameAndPath.get(i)[0].equals("splashScreenQuotes"))
            		splashScreenQuotes=splitedLineToNameAndPath.get(i)[1];
    			

            	
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


    
    
    public static void replaceOldResourceAtributesFromFile(String[] resourceAtributes, String resourcesConnectionPath) {
	    
		try {

	        BufferedReader resourcesConnectionFile = new BufferedReader(new FileReader(resourcesConnectionPath));
	        StringBuffer inputBuffer = new StringBuffer();
	        String resourcesConnectionLine;

	        while ((resourcesConnectionLine = resourcesConnectionFile.readLine()) != null) {
	            
	        	String[] splitedResourcesConnectionLine = resourcesConnectionLine.split("#");
	        	
	        	if(splitedResourcesConnectionLine[0].equals("artistNamesFilePath")) 
	        		inputBuffer.append("artistNamesFilePath#"+resourceAtributes[0]+"\n");
	        	
	        	else if(splitedResourcesConnectionLine[0].equals("databaseFeederFilePath")) 
	        		inputBuffer.append("databaseFeederFilePath#"+resourceAtributes[1]+"\n");
	        	
	        	else if(splitedResourcesConnectionLine[0].equals("videoReceiverFolderPath")) 
	        		inputBuffer.append("videoReceiverFolderPath#"+resourceAtributes[2]+"\n");
	        	
	        	else if(splitedResourcesConnectionLine[0].equals("searchTagsPath")) 
	        		inputBuffer.append("searchTagsPath#"+resourceAtributes[3]+"\n");
	        	
	        	else if(splitedResourcesConnectionLine[0].equals("searchTagsPath")) 
	        		inputBuffer.append("databaseStructureFilePath#"+resourceAtributes[4]+"\n");
	        	
	        	else 
	        		inputBuffer.append(resourcesConnectionLine+"\n"); //to afineis opos einai

	            
	        }
	        resourcesConnectionFile.close();

	        // write the new string with the replaced line OVER the same file
	        FileOutputStream fileOut = new FileOutputStream(resourcesConnectionPath);
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();

	    } catch (Exception e) {
	        System.out.println("[resourcesAtributes]Problem replacing resourcesConnectionfile line.");
	    }
	    
	}
    
    
    
    


    		    
    public static boolean isResourcesAtributesInputCorrect() {
    	
    	
    	String artistNamesFilePath=VideoManagerAppMain.artistNamesFilePath;
        String databaseFeederFilePath=VideoManagerAppMain.databaseFeederFilePath;
        String searchTagsFilePath=VideoManagerAppMain.searchTagsFilePath;
        String videoReceiverFolderPath=VideoManagerAppMain.videoReceiverFolderPath;
    	

        File artistNamesFile = new File(VideoManagerAppMain.artistNamesFilePath);
        File databaseFeederFile = new File(VideoManagerAppMain.databaseFeederFilePath);
        File searchTagsFile = new File(VideoManagerAppMain.searchTagsFilePath);
        File videoReceiverFolder = new File(VideoManagerAppMain.videoReceiverFolderPath);

        
        

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
    	
    	String serverName=VideoManagerAppMain.serverName;
    	String databaseName=VideoManagerAppMain.databaseName;
        String databaseUsername=VideoManagerAppMain.databaseUsername;
        String databasePassword=VideoManagerAppMain.databasePassword;
        
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
