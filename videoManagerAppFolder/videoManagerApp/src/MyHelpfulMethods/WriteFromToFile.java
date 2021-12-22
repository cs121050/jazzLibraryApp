package MyHelpfulMethods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import JazzLibraryClassies.VideoDatabaseFeeder;

public class WriteFromToFile {

	
	 public static void writeVideoListToFile(List<VideoDatabaseFeeder> videos, String databaseFeederFilePath) throws IOException {

			FileWriter fw = new FileWriter(databaseFeederFilePath);
	        BufferedWriter bw = new BufferedWriter(fw);
	        PrintWriter pw = new PrintWriter(bw);
	        
	        for(int i=0;i<videos.size();i++)
	        	pw.println(videos.get(i).toString());
	        
	        pw.close();
		}
		
	 
	 
	 public static void writeStringArrayListToFile(ArrayList<String> list, String databaseFeederFilePath) throws IOException {
	    	
	    	FileWriter fw = new FileWriter(databaseFeederFilePath);
	        BufferedWriter bw = new BufferedWriter(fw);
	        PrintWriter pw = new PrintWriter(bw);
	        
	        for(int i=0;i<list.size();i++)
	        	pw.println(list.get(i).toString());
	        
	        
	        pw.close();
	  
	    }
		
	
	
}
