
package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

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
	
	public static List<VideoDatabaseFeeder> videoDataGlobalList = new ArrayList<VideoDatabaseFeeder>();
	public static ArrayList<String> artistNameList;   
	public static ArrayList<String> remainingArtistNames;   
	public int remainingArtistNamesCounter = 0;
	
	public static ArrayList<String> searchTags ;

	
	static MyDefaultTableModel tableModel;
	
	
    private static String importedFileName="not set";
	
    private static JTable databaseFeedingFile_jT;
	
    
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
    private JButton register_Btn;


    private JMenuBar menuBar;
    private JMenu setingsMenu;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenuItem newFeedingFile_Item;
    private JMenuItem mergeFiles_Item;
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
        
        Icon edit_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\edit_icon.png");

        Icon new_feeding_file_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\new_feeding_file_icon.png");
        Icon merge_files_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\merge_files_icon.png");
        Icon add_artist_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\add_artist_icon.png");
        Icon  correction_resource_files_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\correction_resource_files_icon.png");
        Icon  database_populator_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\database_populator_icon.png");
        Icon download_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\download_icon.png");

        Icon settings_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\setting_icon.png");
        Icon refresh_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManagerApp\\refresh_icon.png");
        
        register_Btn = new JButton(edit_icon);
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        setingsMenu = new JMenu("setings");
        helpMenu = new JMenu("Help");

        newFeedingFile_Item = new JMenuItem("New feeding File",new_feeding_file_icon);
        mergeFiles_Item  = new JMenuItem("Merge Files",merge_files_icon);
        addArtist_Item = new JMenuItem("Add Artist",add_artist_icon);
        resorceFileCorrection_Item  = new JMenuItem("Resorce File Correction",correction_resource_files_icon);
        databasePopulator_Item = new JMenuItem("Database Populator",database_populator_icon);
        videoDownloader_Item = new JMenuItem("Download Videos",download_icon);
        configureResourcesConnections_Item = new JMenuItem("Configure Resources Connections",settings_icon);
        refresh_Item = new JMenuItem("Refresh",refresh_icon);
        about_Item = new JMenuItem("about");

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
        videoRegistrationPanel.add(register_Btn);


        
        
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	buttonPanel.add(loadNextArtist_Btn);
        
    	
    	
        this.add(tableScrollPane, BorderLayout.NORTH);
        this.add(videoRegistrationPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
         
        fileMenu.add(newFeedingFile_Item);
        fileMenu.add(addArtist_Item);
        fileMenu.addSeparator();
        fileMenu.add(mergeFiles_Item);
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
        

        
        register_Btn.addActionListener(new ActionListener() {
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
        		video.setVideo_availability("0");
        		videoDataGlobalList.add(0,video);
        		
        		videoLink_Tf.setText("");
        	    extraArtist_Tf.setText("");
        		
        		
				try {
					
					writeVideoListToFile(videoDataGlobalList,VideoManagerAppMain.databaseFeederFilePath);
					
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
            
            	String[] splitLink=videoId.split("&t=");
	    		String videoIdWithoutSeconds=splitedVideoLink[0];
	    		
	    		
	    		
				return videoIdWithoutSeconds;
			}

			private String secontIndicatorTrimmer() {
				//an exei 2 fores = , paei na pei oti to video den 3ekina apo thn arxh

            	String[] splitLink=videoLink_Tf.getText().split("&t=");
	    		String videoLinkWithoutSeconds=splitLink[0];
            	
            	
				return videoLinkWithoutSeconds;
			}
        });
        
        

        loadNextArtist_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                artistNameValue_Jl.setText(remainingArtistNames.get(remainingArtistNamesCounter));
            	
                artistBrowserSearchResults();
   
            }
        });

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        newFeedingFile_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	try {
					newFeedingFileCreation();
					JOptionPane.showMessageDialog(MainFrame.this, "feeding file has sincronized");
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
 
            	
            }
        });
        
        
        mergeFiles_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	try {
					mergeFeedingFilesToMaster();
					JOptionPane.showMessageDialog(MainFrame.this, "new feeding file has merged to the Master ");
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
            	
            }

			private void mergeFeedingFilesToMaster() throws IOException {
				
				//blepei an iparxei to MASTER , an den iparxei to dimiourgei ,, an iparxei prosthetei se auto kathe gramh tou onGoingFeedingFile xoris na elenxei tipota
				
				//writeVideoListToFile(videoList,VideoManagerAppMain.databaseFeederFileMASTERPath);
				
				File databaseFeederFileMASTER = new File(VideoManagerAppMain.databaseFeederFileMASTERPath);
				if(!databaseFeederFileMASTER.exists()) { 
					File f = new File(VideoManagerAppMain.databaseFeederFileMASTERPath);
					f.getParentFile().mkdirs(); 
					f.createNewFile();
				}
				
				FileWriter fw = new FileWriter(VideoManagerAppMain.databaseFeederFileMASTERPath,true);
		        BufferedWriter bw = new BufferedWriter(fw);
		        PrintWriter pw = new PrintWriter(bw);
		        
		        for(int i=0;i<videoDataGlobalList.size();i++)
		        	pw.println(videoDataGlobalList.get(i).toString());
		        
		        pw.close();
				
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
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            }
        });
    	
    	
        databasePopulator_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            

            	try {
            		
                	DatabasePopulatorFrame databasePopulatorFrame = new DatabasePopulatorFrame();
                	databasePopulatorFrame.prepareUI();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        
        
        refresh_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	try {
            		
					VideoManagerAppMain.connectionsInitialisation();
					initialiseResourcesVariables();
			    	
					tableScrollPane.getViewport().add(databaseFeedingFile_jT);

					fileNameInitialisation();
					tableScrollPane.setBorder(BorderFactory.createTitledBorder(importedFileName));
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
					        videoRegistrationPanel.add(register_Btn);
			    	
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
    
    
	  private void newFeedingFileCreation() throws IOException {
			
			File newDatabaseFeedingFile = new File(VideoManagerAppMain.resourcesFolder + "\\onGoingDatabaseFeederFile.txt" );
			if(!newDatabaseFeedingFile.exists()) {
				newDatabaseFeedingFile.getParentFile().mkdirs(); 
				newDatabaseFeedingFile.createNewFile();
			}
			VideoManagerAppMain.databaseFeederFilePath = VideoManagerAppMain.resourcesFolder + "\\onGoingDatabaseFeederFile.txt";
			
			String[] resourcesAtributes = new String[5];
        	
			//na ftiaxtei global pinakas sto MAIN .. pou na min xriazete na diloneis kathe glamh ,, allla mono mia kathe fora
        	resourcesAtributes[0]=VideoManagerAppMain.artistNamesFilePath;
        	resourcesAtributes[1]=VideoManagerAppMain.resourcesFolder + "\\onGoingDatabaseFeederFile.txt";
			resourcesAtributes[2]=VideoManagerAppMain.videoReceiverFolderPath;
			resourcesAtributes[3]=VideoManagerAppMain.searchTagsFilePath;
			resourcesAtributes[4]=VideoManagerAppMain.databaseStructureFilePath;
			
        	VideoManagerAppMain.replaceOldResourceAtributesFromFile(resourcesAtributes,VideoManagerAppMain.resourcesConnections);
			
	  }



	private ArrayList<String> readSearchTags() throws IOException {

		FileReader flSearchTagsFileLine=new FileReader(VideoManagerAppMain.searchTagsFilePath);
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
		if(VideoManagerAppMain.isResourcesAtributesInputCorrect()) {
	        
        	searchTags = readSearchTags();
	        artistNameList =  readArtistNamesFileToList();
	    	remainingArtistNames = continiueFromWhereYouStoped();
        
	        fileNameInitialisation();

	    	videoDataGlobalList = readDatabaseFeederFileToVideoObjects();
	        
	        jTableOfFileDisplayInitialisation();
	        
	        artistNameValue_Jl = new JLabel(remainingArtistNames.get(0)); 

        }else{
	        artistNameValue_Jl = new JLabel(""); 
        }
        
		
	}


	

	
	
	private void artistBrowserSearchResults() {

		
		
		String url = "https://www.youtube.com/results?search_query=";

		String[] splitedArtistName=remainingArtistNames.get(remainingArtistNamesCounter).split(" ");

		
		
		for(int i=0 ;i<splitedArtistName.length;i++)
            url = url +"+"+ splitedArtistName[i];

        
		for(int i=0 ;i<searchTags.size();i++)    
			browserURL(url+"+"+ searchTags.get(i));

		
		
		remainingArtistNamesCounter++;
	}

	
	
	private static void browserURL(String uriString) {
		
		Desktop desktop;
		Runtime runtime;
		
		if(Desktop.isDesktopSupported()){
            desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(uriString));
            }catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else{
            runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + uriString);
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

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
    	
		File tempFile = new File(VideoManagerAppMain.databaseFeederFilePath);
		boolean exists = tempFile.exists();
		
		
		String databaseFeederFilePathChanged=VideoManagerAppMain.databaseFeederFilePath.replace('\\','#');
		
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
    	
        String[] columnNames= {"artist name" , "instrument", "video link", "video name", "video duration","video type","video ID","video availability","","",""};
    	
    	String[][] videoData = new String[videoDataGlobalList.size()][];
    	for(int i=0;i<videoDataGlobalList.size();i++) 
    		videoData[i]=videoDataGlobalList.get(i).toStringArray();
    	
    	tableModel = new MyDefaultTableModel(videoData,columnNames);
        databaseFeedingFile_jT=new JTable(tableModel);

        
        
        AbstractAction browse = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                
                String thisVideoLink = videoDataGlobalList.get(modelRow).getVideo_link();
                
    			browserURL(thisVideoLink);


            }
        };
        
        AbstractAction save = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                VideoDatabaseFeeder updatedVideo = ((MyDefaultTableModel)table.getModel()).getVideoAtRow(modelRow);

                videoDataGlobalList.remove(modelRow);
                videoDataGlobalList.add(modelRow,updatedVideo); //bazei to video sthn thesh , pou patithike to koubi

                
                try {
                	
					writeVideoListToFile(videoDataGlobalList,VideoManagerAppMain.databaseFeederFilePath);
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
                
                videoDataGlobalList.remove(modelRow);
                
                
                
                
                
                try {
                	
					writeVideoListToFile(videoDataGlobalList,VideoManagerAppMain.databaseFeederFilePath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        };

      
        
        
        ButtonColumn browseButtonColumn = new ButtonColumn(databaseFeedingFile_jT, browse, 8);
        browseButtonColumn.setMnemonic(KeyEvent.VK_D);

        
        ButtonColumn saveButtonColumn = new ButtonColumn(databaseFeedingFile_jT, save, 9);
        saveButtonColumn.setMnemonic(KeyEvent.VK_D);
        
        
        ButtonColumn deleteButtonColumn = new ButtonColumn(databaseFeedingFile_jT, delete, 10);
        deleteButtonColumn.setMnemonic(KeyEvent.VK_D);
        
    }
    
    
    
    
    



	static ArrayList<String> continiueFromWhereYouStoped() throws IOException {

		
		ArrayList<String> remainingArtistNames = new ArrayList<String>();

		
		FileReader flDatabaseFeederFileLine=new FileReader(VideoManagerAppMain.databaseFeederFilePath);
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
    		
    		FileReader flArtistNamesFileLine=new FileReader(VideoManagerAppMain.artistNamesFilePath);
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
    	
    	FileReader flDatabaseFeederFileLine=new FileReader(VideoManagerAppMain.databaseFeederFilePath);
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
    		video.setVideo_availability(splitConnectionsLine[7]);

    		
    		videoList.add(video);
    		
            connectionsLine=brDatabaseFeederFileLine.readLine();
        }
        
		brDatabaseFeederFileLine.close();
        
        return videoList;
    }
    
    
static ArrayList<String> readArtistNamesFileToList() throws IOException {
    	
    	FileReader flArtistNamesFileLine=new FileReader(VideoManagerAppMain.artistNamesFilePath);
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
    

