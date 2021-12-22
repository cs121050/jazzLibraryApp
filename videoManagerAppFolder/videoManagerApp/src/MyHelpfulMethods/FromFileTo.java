package MyHelpfulMethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import JazzLibraryClassies.VideoDatabaseFeeder;

public class FromFileTo {

	

    public static String fromFileToString(String filePath) throws IOException {
    	//travaei to periexomeno enos file, kai to kanei string!
    	FileReader fr=new FileReader(filePath);
		BufferedReader br = new BufferedReader(fr);
    			
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("");
		
		String line=br.readLine();
        while(line != null){

        	stringBuffer.append(line+"\n");

        	line=br.readLine();
        }
    	return stringBuffer.toString();
	}

	

    
	  
	public static ArrayList<String> fromFileToArrayList(String filePath) throws IOException {

		FileReader fr=new FileReader(filePath);
    	BufferedReader br = new BufferedReader(fr);
    			
		ArrayList<String> fromFileToArrayList = new ArrayList<String>();
		
        String line=br.readLine();
        while(line != null){
        	        	
        	fromFileToArrayList.add(line);

            line=br.readLine();
        }
        
        br.close();
				
		return fromFileToArrayList;
	}


	
   
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}



