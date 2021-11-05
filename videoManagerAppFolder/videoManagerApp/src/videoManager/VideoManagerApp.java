/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoManager;



import java.io.IOException;

/**
 *
 * @author nick
 */
public class VideoManagerApp {
	
	public static String usedArtistNamesFilePath;
	public static String artistNamesFilePath;
	public static String databaseFeederFilePath;
	public static String videoReceiverFolderPath;
	
	public static String serverUsername;
	public static String serverPassword;
	
	public static String[] databaseNames;


	//onigei t0 filo! kai pernei tis global metablites //METHOD
	//to database names ,,, einai mia gramh me onomata   xorismeno me #  ,, kanta split valta ston pinaka  databaseNames

	
	public static boolean isLogedInToServer;


    public static void main(String[] args){

        MainFrame mainFrame = new MainFrame();
        mainFrame.prepareUI();

    }
    
}
