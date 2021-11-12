/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoManager;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nick
 */
public class VideoManagerApp {
	
	public static String usedArtistNamesFilePath;
	public static String artistNamesFilePath;
	public static String databaseFeederFilePath;
	public static String videoReceiverFolderPath;
	public static String searchTagsPath;
	
	public static String databaseUsername;
	public static String databasePassword;
	
	public static String databaseName;




	
	public static boolean isLogedInToServer=false;


    public static void main(String[] args) throws IOException{

    	//onigei t0 file! kai pernei tis global metablites //METHOD
    	//to database names ,,, einai mia gramh me onomata   xorismeno me #  ,, kanta split valta ston pinaka  databaseNames    	
    	
    	connectionsInitialisation();
    	
        MainFrame mainFrame = new MainFrame();
        mainFrame.prepareUI();

    }
    
public static void connectionsInitialisation() throws IOException {
    	
    	FileReader flConnectionsLine=new FileReader("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\connectionsOKAA.txt");
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

        	if(splitedLineToNameAndPath.get(i)[0].equals("usedArtistNamesFilePath"))
        		usedArtistNamesFilePath=splitedLineToNameAndPath.get(i)[1];
        	
        	else if(splitedLineToNameAndPath.get(i)[0].equals("artistNamesFilePath"))
        		artistNamesFilePath=splitedLineToNameAndPath.get(i)[1];
        	
        	else if(splitedLineToNameAndPath.get(i)[0].equals("databaseFeederFilePath"))
        		databaseFeederFilePath=splitedLineToNameAndPath.get(i)[1];
        	
        	else if(splitedLineToNameAndPath.get(i)[0].equals("videoReceiverFolderPath"))
        		videoReceiverFolderPath=splitedLineToNameAndPath.get(i)[1];
        	
        	else if(splitedLineToNameAndPath.get(i)[0].equals("searchTagsPath"))
        		searchTagsPath=splitedLineToNameAndPath.get(i)[1];
       
        	
        	
        	
        	else if(splitedLineToNameAndPath.get(i)[0].equals("databaseUsername"))
        		databaseUsername=splitedLineToNameAndPath.get(i)[1];
        	
        	else if(splitedLineToNameAndPath.get(i)[0].equals("databasePassword"))
        		databasePassword=splitedLineToNameAndPath.get(i)[1];
        	
        	else if(splitedLineToNameAndPath.get(i)[0].equals("databaseName"))
        		databaseName=splitedLineToNameAndPath.get(i)[1];
        	
        	
         }
        
    	
    }
    
    
    
    
    
    
    
}
