
package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.Desktop;


//my pakages
import ButtomColumnTableModel.*;
import JazzLibraryClassies.VideoDatabaseFeeder;
import youtubeDownloaderSyncPipe.SyncPipe;
import AutoCompleteJTextField.*;




/**
*
* @author nick
*/

public class MainFrame extends JFrame {
        
	//anoigo to file kai to kratao anoixto kathos trexei to programa
	
	public static List<VideoDatabaseFeeder> videoList = new ArrayList<VideoDatabaseFeeder>();
	public static ArrayList<String> artistNameList;   
	public static ArrayList<String> remainingArtistNames;   
	public int remainingArtistNamesCounter = 0;
	
	public static ArrayList<String> searchTags ;

	
	static MyDefaultTableModel tableModel;
	
	
    private static String importedFileName="not set";
	
    private static JTable databaseFeedingFile_jT;
	
    private JTable videoRegistration_jT;

    
	private JLabel artistName_Jl;
	private JLabel instrumentName_Jl;
	private JLabel videoLink_Jl;
	private JLabel videoType_Jl;
	private JLabel extraArtist_Jl;
	private JLabel statusMessage_Jl;
    
	static JLabel artistNameValue_Jl;
    private JTextField videoLink_Tf;
    private JTextField extraArtist_Tf;
	
    
    
    private JComboBox instrument_Cb;
    private JComboBox type_Cb;
    private String[] instruments_StringArray = { "bass", "guitar", "piano", "drums", "voice", "sax", "trumpet", "violin", "vibes","other" };
    private String[] type_StringArray = { "interview", "movie", "lecture"};
      
    private JButton loadNextArtist_Btn;
    private JButton registerAndDownload_Btn;


    private JMenuBar menuBar;
    private JMenu setingsMenu;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenuItem addArtist_Item;
    private JMenuItem databasePopulator_Item;
    private JMenuItem resorceFileCorrection_Item;
    private JMenuItem videoDownloader_Item;
    private JMenuItem configureResourcesConnections_Item;
    private JMenuItem refresh_Item;
    private JMenuItem about_Item;




    public MainFrame() throws IOException {
        super();     
        
        
        initialiseResourcesVariables();
                
    	artistName_Jl = new JLabel("artist name'"); 
    	artistName_Jl.setBorder(BorderFactory.createEtchedBorder());
    	
    	instrumentName_Jl = new JLabel("instrument name"); 
    	instrumentName_Jl.setBorder(BorderFactory.createEtchedBorder());

    	videoLink_Jl = new JLabel("video link"); 
    	videoLink_Jl.setBorder(BorderFactory.createEtchedBorder());

    	videoType_Jl = new JLabel("video type"); 
    	videoType_Jl.setBorder(BorderFactory.createEtchedBorder());

    	extraArtist_Jl = new JLabel("extra artist"); 
    	extraArtist_Jl.setBorder(BorderFactory.createEtchedBorder());

    	statusMessage_Jl = new JLabel(""); 
    	statusMessage_Jl.setFont(new Font("Serif", Font.PLAIN, 10));
    	
    	

        videoLink_Tf= new JTextField("");
        extraArtist_Tf= new JTextField(""); 
        
        instrument_Cb = new JComboBox(instruments_StringArray); 
        type_Cb = new JComboBox(type_StringArray); 
        
        loadNextArtist_Btn = new JButton("Load Next Artist");
        
        Icon edit_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\edit_icon.png");

        Icon add_artist_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\add_artist_icon.png");
        Icon  correction_resource_files_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\correction_resource_files_icon.png");
        Icon  database_populator_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\database_populator_icon.png");
        Icon download_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\download_icon.png");

        Icon settings_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\setting_icon.png");
        Icon refresh_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\refresh_icon.png");
        
        registerAndDownload_Btn = new JButton(edit_icon);
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        setingsMenu = new JMenu("setings");
        helpMenu = new JMenu("Help");

        addArtist_Item = new JMenuItem("Add Artist",add_artist_icon);
        resorceFileCorrection_Item  = new JMenuItem("Resorce File Correction",correction_resource_files_icon);
        databasePopulator_Item = new JMenuItem("Database Populator",database_populator_icon);
        videoDownloader_Item = new JMenuItem("Download Videos",download_icon);
        configureResourcesConnections_Item = new JMenuItem("Configure Resources Connections",settings_icon);
        refresh_Item = new JMenuItem("Refresh",refresh_icon);
        about_Item = new JMenuItem("about");

    }



    public void removeUI() { 
    }


	public void prepareUI() { 
		
        artistAutoCompleteJTextFieldExtra();

        
        
    	JScrollPane tableScrollPane = new JScrollPane();
    	// Force the scrollbars to always be displayed
    	tableScrollPane.setVerticalScrollBarPolicy(
    	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	tableScrollPane.setHorizontalScrollBarPolicy(
        	    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	tableScrollPane.setPreferredSize(new java.awt.Dimension(1,250));
    	tableScrollPane.setBorder(BorderFactory.createTitledBorder(importedFileName));
    	tableScrollPane.getViewport ().add (databaseFeedingFile_jT);

    	
    	
    

        JPanel videoRegistrationPanel = new JPanel();
        videoRegistrationPanel.setLayout(new GridLayout(2,6,10,10));
        videoRegistrationPanel.setBorder(BorderFactory.createTitledBorder("Video Registration"));
                
        videoRegistrationPanel.add(artistName_Jl);
        videoRegistrationPanel.add(instrumentName_Jl);
        videoRegistrationPanel.add(videoLink_Jl);
        videoRegistrationPanel.add(videoType_Jl);
        videoRegistrationPanel.add(extraArtist_Jl);
        videoRegistrationPanel.add(statusMessage_Jl);
                
        videoRegistrationPanel.add(artistNameValue_Jl);
        videoRegistrationPanel.add(instrument_Cb);
        videoRegistrationPanel.add(videoLink_Tf);
        videoRegistrationPanel.add(type_Cb);
        videoRegistrationPanel.add(extraArtist_Tf);
        videoRegistrationPanel.add(registerAndDownload_Btn);


        
        
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	buttonPanel.add(loadNextArtist_Btn);
        
    	
    	
        this.add(tableScrollPane, BorderLayout.NORTH);
        this.add(videoRegistrationPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        fileMenu.add(addArtist_Item);
        fileMenu.addSeparator();
        fileMenu.add(resorceFileCorrection_Item);
        fileMenu.addSeparator();
        fileMenu.add(videoDownloader_Item);
        fileMenu.add(databasePopulator_Item);   

        setingsMenu.add(refresh_Item);
        setingsMenu.addSeparator();
        setingsMenu.add(configureResourcesConnections_Item);
        
        helpMenu.add(about_Item);
        
        menuBar.add(fileMenu);
        menuBar.add(setingsMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);
        
        
        this.setSize(1000, 430);      
        this.setLocationRelativeTo(null);
        this.setTitle("Video Manager");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("videoManager_icon.jpg")));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        
        
        
      
        this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
            	
            	exitApp();       
            }
        });
        

        
        registerAndDownload_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	
            	
            	String artistName=mainAndExtraArtistMerger();

            	String videoLink=secontIndicatorTrimmer();
            	
            	String videoId=getYoutubeVideoId();
            	
            	
            	
            	
            	VideoDatabaseFeeder video = new VideoDatabaseFeeder();
            	video.setArtist_name(artistName);
        		video.setArtist_instrument(String.valueOf(instrument_Cb.getSelectedItem()));
        		video.setVideo_link(videoLink);
        		video.setVideo_type(String.valueOf(type_Cb.getSelectedItem()));
        		video.setVideo_name("???");
        		video.setVideo_duration("???");
        		video.setVideo_id(videoId);
        		videoList.add(0,video);
        		
        		videoLink_Tf.setText("");
        	    extraArtist_Tf.setText("");
        		
        		
				try {
					
					writeVideoListToFile(videoList,VideoManagerApp.databaseFeederFilePath);
					
					tableModel.insertRow(0, video.toObject());
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
            }

			private String mainAndExtraArtistMerger() {

				String artistName;
            	if(!extraArtist_Tf.getText().equals(""))
                	artistName = ""+ artistNameValue_Jl.getText() + "@" +extraArtist_Tf.getText().trim();
            	else
                	artistName=artistNameValue_Jl.getText();            	
				return artistName;
			}

			private String getYoutubeVideoId() {
				
				String[] splitedVideoLink=videoLink_Tf.getText().split("=");
            	String videoId=splitedVideoLink[1];
            	
            	
				return videoId;
			}

			private String secontIndicatorTrimmer() {
				//an exei 2 fores = , paei na pei oti to video den 3ekina apo thn arxh
            	String[] splitedVideoLink=videoLink_Tf.getText().split("=");
            	String videoLink;
            	if(splitedVideoLink.length>2) 
            		videoLink=splitedVideoLink[0]+"="+splitedVideoLink[1];
            	else
            		videoLink=videoLink_Tf.getText();
            	
				return videoLink;
			}
        });
        
        

        loadNextArtist_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                artistNameValue_Jl.setText(remainingArtistNames.get(remainingArtistNamesCounter));
            	
            	loadSearchResultsToBrowser();
   
            }
        });

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        addArtist_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	AddArtistFrame addArtistFrame = new AddArtistFrame();
            	addArtistFrame.prepareUI();
                
            }
        });
        
        
        
        resorceFileCorrection_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	
            	ResourceFileCorrectionFrame resourceFileCorrectionFrame = new ResourceFileCorrectionFrame();
            	resourceFileCorrectionFrame.prepareUI();
            	
            }
        });
        
        
        
        
        
        
        
    	videoDownloader_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	
            	try {
            		
                	VideoDownloaderFrame videoDownloaderFrame = new VideoDownloaderFrame();
					videoDownloaderFrame.prepareUI();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            }
        });
    	
    	
        databasePopulator_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //	
            //	
             //   
            }
        });

        
        
        refresh_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	try {
            		
					VideoManagerApp.connectionsInitialisation();
					initialiseResourcesVariables();
			    	

					tableScrollPane.getViewport().add(databaseFeedingFile_jT);

			    	videoRegistrationPanel.removeAll();
				        videoRegistrationPanel.add(artistName_Jl);
				        videoRegistrationPanel.add(instrumentName_Jl);
				        videoRegistrationPanel.add(videoLink_Jl);
				        videoRegistrationPanel.add(videoType_Jl);
				        videoRegistrationPanel.add(extraArtist_Jl);
				        videoRegistrationPanel.add(statusMessage_Jl);
					        videoRegistrationPanel.add(artistNameValue_Jl);
					        videoRegistrationPanel.add(instrument_Cb);
					        videoRegistrationPanel.add(videoLink_Tf);
					        videoRegistrationPanel.add(type_Cb);
					        videoRegistrationPanel.add(extraArtist_Tf);
					        videoRegistrationPanel.add(registerAndDownload_Btn);
			    	
			        revalidate();
			        repaint();
			
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
            }
        });
        configureResourcesConnections_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	ConfigureResourcesConnectionsFrame configureResourcesConnectionsFrame = new ConfigureResourcesConnectionsFrame();
            	try {
					configureResourcesConnectionsFrame.prepareUI();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
            }
        });
        about_Item.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		
//        		AboutFrame aboutFrame = new AboutFrame();
//        		aboutFrame.prepareUI();
        		
        	}   
        });


       
        
        
    }
    
    



	private ArrayList<String> readSearchTags() throws IOException {

		FileReader flSearchTagsFileLine=new FileReader(VideoManagerApp.searchTagsFilePath);
    	BufferedReader brSearchTagsFileLine = new BufferedReader(flSearchTagsFileLine);
    	
    	//bale elenxo an iparxoun ta files pou dia vazeis apo mesa
		
		ArrayList<String> searchTagsList = new ArrayList<String>();
		
        String searchTagsLine=brSearchTagsFileLine.readLine();
        while(searchTagsLine != null){
        	        	
        	searchTagsList.add(searchTagsLine);

            searchTagsLine=brSearchTagsFileLine.readLine();
        }
        
        brSearchTagsFileLine.close();
				
		return searchTagsList;
	}









	private void initialiseResourcesVariables() throws IOException {
		if(VideoManagerApp.isResourcesAtributesInputCorrect()) {
	        
        	searchTags = readSearchTags();
	        artistNameList =  readArtistNamesFileToList();
	    	remainingArtistNames = continiueFromWhereYouStoped();
        
	        fileNameInitialisation();

	        jTableOfFileDisplayInitialisation();
	        
	        artistNameValue_Jl = new JLabel(remainingArtistNames.get(0)); 

        }else{
	        artistNameValue_Jl = new JLabel(""); 
        }
        
		
	}




	
	
	private void loadSearchResultsToBrowser() {

		Desktop desktop;
		Runtime runtime;
		
		String url = "https://www.youtube.com/results?search_query=";

		String[] splitedArtistName=remainingArtistNames.get(remainingArtistNamesCounter).split(" ");

		System.out.println();
		
		
		for(int i=0 ;i<splitedArtistName.length;i++)
            url = url +"+"+ splitedArtistName[i];

        
		for(int i=0 ;i<searchTags.size();i++){
            


            if(Desktop.isDesktopSupported()){
                desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI(url+"+"+ searchTags.get(i)));
                }catch (IOException | URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else{
                runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open " + url);
                }catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
		
		
		remainingArtistNamesCounter++;
		
		
	}

    
    private void artistAutoCompleteJTextFieldExtra() {
		
    	AutoSuggestor autoSuggestor = new AutoSuggestor(extraArtist_Tf, this, null, Color.BLACK.brighter(), Color.WHITE, Color.RED, 0.85f) {
            @Override
			protected
            boolean wordTyped(String typedWord) {

            	
                setDictionary(artistNameList);
                //addToDictionary("bye");//adds a single word

                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
            }
        }; 
    	   	
	}
    
	
    
    
    static void fileNameInitialisation() throws IOException {
    	
		File tempFile = new File(VideoManagerApp.databaseFeederFilePath);
		boolean exists = tempFile.exists();
		
		
		String databaseFeederFilePathChanged=VideoManagerApp.databaseFeederFilePath.replace('\\','#');
		
	    String[] splitedFilePath = databaseFeederFilePathChanged.split("#");
		String fileName = splitedFilePath[splitedFilePath.length-1];
		if(exists==true) 
			importedFileName=fileName;
	
    }
    
    
    
    private static void writeVideoListToFile(List<VideoDatabaseFeeder> videoList,String filePath) throws IOException {
    	
    	FileWriter fw = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        for(int i=0;i<videoList.size();i++)
        	pw.println(videoList.get(i).toString());
        
        
        pw.close();
  
    }

    
    
    
    
    static void jTableOfFileDisplayInitialisation() throws IOException {
    	
        String[] columnNames= {"artist name" , "instrument", "video link", "video name", "video duration","video type","video ID","",""};
    	
    	videoList = readDatabaseFeederFileToVideoObjects();
    	String[][] videoData = new String[videoList.size()][];
    	for(int i=0;i<videoList.size();i++) 
    		videoData[i]=videoList.get(i).toStringArray();
    	
    	tableModel = new MyDefaultTableModel(videoData,columnNames);
        databaseFeedingFile_jT=new JTable(tableModel);

        
        AbstractAction save = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                VideoDatabaseFeeder updatedVideo = ((MyDefaultTableModel)table.getModel()).getVideoAtRow(modelRow);

                videoList.remove(modelRow);
                videoList.add(modelRow,updatedVideo); //bazei to video sthn thesh , pou patithike to koubi

                
                try {
                	
					writeVideoListToFile(videoList,VideoManagerApp.databaseFeederFilePath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        };
                
        AbstractAction delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                ((DefaultTableModel)table.getModel()).removeRow(modelRow);
                
                videoList.remove(modelRow);
                
                
                
                
                
                try {
                	
					writeVideoListToFile(videoList,VideoManagerApp.databaseFeederFilePath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        };
        
      
        

        
        ButtonColumn saveButtonColumn = new ButtonColumn(databaseFeedingFile_jT, save, 7);
        saveButtonColumn.setMnemonic(KeyEvent.VK_D);
        
       
        
        ButtonColumn deleteButtonColumn = new ButtonColumn(databaseFeedingFile_jT, delete, 8);
        deleteButtonColumn.setMnemonic(KeyEvent.VK_D);
        
        
        
    }
    
    
    
    
    



	static ArrayList<String> continiueFromWhereYouStoped() throws IOException {

		
		ArrayList<String> remainingArtistNames = new ArrayList<String>();

		
		FileReader flDatabaseFeederFileLine=new FileReader(VideoManagerApp.databaseFeederFilePath);
    	BufferedReader brDatabaseFeederFileLine = new BufferedReader(flDatabaseFeederFileLine);
    	
        String DatabaseFeederFileLine=brDatabaseFeederFileLine.readLine();
        
        
        
        if(DatabaseFeederFileLine==null) {
        	remainingArtistNames=artistNameList;

        }
        else {
        	String[] splitConnectionsLine=DatabaseFeederFileLine.trim().split("#");
    		String[] splitArtistNames=splitConnectionsLine[0].trim().split("@");
    		
    		String lastArtistYouSearched=splitArtistNames[0];

            
    		brDatabaseFeederFileLine.close();

    		
    		
    		boolean artistNameFound=false;
    		
    		FileReader flArtistNamesFileLine=new FileReader(VideoManagerApp.artistNamesFilePath);
        	BufferedReader brArtistNamesFileLine = new BufferedReader(flArtistNamesFileLine);
        	
    		
            String artistNamesLine=brArtistNamesFileLine.readLine();

            while(artistNamesLine != null){
            	        	
            	
        		if(lastArtistYouSearched.equals(artistNamesLine))
        			artistNameFound=true;
        		
        		if(artistNameFound==true) {
        			
        			remainingArtistNames.add(artistNamesLine);
        			
        		}

                artistNamesLine=brArtistNamesFileLine.readLine();
            }
            
            
            brArtistNamesFileLine.close();

            
            
            if(artistNamesLine== null && artistNameFound==false ) {
            	
            	
            	remainingArtistNames=artistNameList;
            	
            	
            }

        }
				
		
		
		
        
		
		
		return remainingArtistNames;
	}



    
    
    

    
    
    
    private static List<VideoDatabaseFeeder> readDatabaseFeederFileToVideoObjects() throws IOException {
    	
    	FileReader flDatabaseFeederFileLine=new FileReader(VideoManagerApp.databaseFeederFilePath);
    	BufferedReader brDatabaseFeederFileLine = new BufferedReader(flDatabaseFeederFileLine);
    	

		String[] splitedLineToVideoAtributes;
        List<VideoDatabaseFeeder> videoList = new ArrayList<>();

		
        String connectionsLine=brDatabaseFeederFileLine.readLine();
        while(connectionsLine != null){
        	
    		String[] splitConnectionsLine=connectionsLine.trim().split("#");
    		
    		VideoDatabaseFeeder video=new VideoDatabaseFeeder();
    		video.setArtist_name(splitConnectionsLine[0]);
    		video.setArtist_instrument(splitConnectionsLine[1]);
    		video.setVideo_link(splitConnectionsLine[2]);
    		video.setVideo_name(splitConnectionsLine[3]);
    		video.setVideo_duration(splitConnectionsLine[4]);
    		video.setVideo_type(splitConnectionsLine[5]);
    		video.setVideo_id(splitConnectionsLine[6]);
    		
    		videoList.add(video);
    		
            connectionsLine=brDatabaseFeederFileLine.readLine();
        }
        
		brDatabaseFeederFileLine.close();
        
        return videoList;
    }
    
    
static ArrayList<String> readArtistNamesFileToList() throws IOException {
    	
    	FileReader flArtistNamesFileLine=new FileReader(VideoManagerApp.artistNamesFilePath);
    	BufferedReader brArtistNamesFileLine = new BufferedReader(flArtistNamesFileLine);
    	

    	ArrayList<String> artistNameList = new ArrayList<>();

		
        String artistNamesLine=brArtistNamesFileLine.readLine();
        while(artistNamesLine != null){
        	
    		
        	artistNameList.add(artistNamesLine);
    		
            artistNamesLine=brArtistNamesFileLine.readLine();
        }
        
		brArtistNamesFileLine.close();
        
        return artistNameList;
    }
    
    
    
    
    
    
    private void videoDownloader(String downloadFolderPath,String videoUrl){


    	
    	//String[] videoNameAndDuration=null;
    	
        String[] splitedLineToWords;
        
        String[] command ={"cmd"}; //gia to youtubeDownloader kodika
        Process p;
        
        Scanner keyboard = new Scanner(System.in);
           

        

                 
        splitedLineToWords=videoUrl.split(":");// kai etsi na 3ereis poia einai LINKS
        
        
        File f=new File(downloadFolderPath+"\\downloadFolder");
        if (f.exists() == false )
            new File(downloadFolderPath+"\\downloadFolder").mkdirs(); 

        
          
            videoUrl=videoUrl.replaceAll("\\s+","_");
           
            
            
            if(splitedLineToWords[0].equals("https") && !splitedLineToWords[1].contains("channel")){
                try {
                    p = Runtime.getRuntime().exec(command); 
                    new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
                    new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
                    PrintWriter stdin = new PrintWriter(p.getOutputStream());                    
                    
                    stdin.println("cd "+downloadFolderPath);     //pigene sto directoruy poy einai to youtube-dl 
                    stdin.println("youtube-dl -f 135 "+videoUrl);  //gia na ginei to download xrisimopio to etoimo programa youtube-dl 
                    
                    stdin.println("youtube-dl --get-title "+videoUrl); 
                    stdin.write(videoUrl);
                    
                    System.out.println(p.getInputStream());
                    
                    
                    
                    
                    
                    stdin.close();                                   //kai edo leme pou tha to brei to programa
                    p.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            

    	//return videoNameAndDuration;
    }
    
    
    
    
    
    
    

	
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


    private void setInvisible() {

    	this.setVisible(false);
    }

    
    
    
    
    
    
    private void exitApp() {
        int i = JOptionPane.showConfirmDialog(MainFrame.this, "are you sure you want to Exit?");
        if (i == JOptionPane.YES_OPTION) {
        
            System.exit(0);
        }
    }
    
    
 }
    

