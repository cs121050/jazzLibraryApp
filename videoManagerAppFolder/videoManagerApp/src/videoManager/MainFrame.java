
package videoManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.geom.Dimension2D;
import java.beans.Transient;


/**
*
* @author nick
*/

public class MainFrame extends JFrame {
        
    private JLabel importedFileName;
	
    private JTable databaseFeedingFileTable;
	
    private JLabel spacing_Jl;
	
	private JTextField artistName_Tf;
    private JTextField videoLink_Tf;
    private JTextField extraArtist_Tf;
	
    
    
    private JComboBox instrument_Cb;
    private JComboBox type_Cb;
    private String[] instruments_StringArray = { "bass", "guitar", "piano", "drums", "voice", "sax", "trumpet", "violin", "vibes","other" };
    private String[] type_StringArray = { "interview", "movie", "lecture"};
      
    private JButton loadNextArtist_Btn;
    private JButton registerAndDownload_Btn;


    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenuItem databasePopulator_Item;
    private JMenuItem configureResourcesConnections_Item;
    private JMenuItem about_Item;




    public MainFrame() throws IOException {
        super();     
        importedFileName=new JLabel("no file imported");

        jTableInitialisation();
        
        spacing_Jl=new JLabel("~#    %    #~");
        
        artistName_Tf = new JTextField("artist name");  artistName_Tf.setMinimumSize(new java.awt.Dimension(10,10));
        videoLink_Tf= new JTextField("video link"); new Dimension(txtMessage.getWidth(), height)
        extraArtist_Tf= new JTextField("extra artist"); 
        
        instrument_Cb = new JComboBox(instruments_StringArray); 
        type_Cb = new JComboBox(type_StringArray); 
        
        loadNextArtist_Btn = new JButton("Load Next Artist"); 
        registerAndDownload_Btn = new JButton("Register & Download");
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        databasePopulator_Item = new JMenuItem("Database Populator");
        configureResourcesConnections_Item = new JMenuItem("Configure Resources Connections");
        about_Item = new JMenuItem("about");

    }


//	JPanel tablePanel = new JPanel();
//	tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//	tablePanel.add(databaseFeedingFileTable);
//
//	
//	JScrollPane tableScrollPane = new JScrollPane(tablePanel);
//	// Force the scrollbars to always be displayed
//	tableScrollPane.setVerticalScrollBarPolicy(
//	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//	tableScrollPane.setHorizontalScrollBarPolicy(
//    	    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//	tableScrollPane.setPreferredSize(new java.awt.Dimension(500,250));
//	//tableScrollPane.getColumnHeader().setVisible();
//	//tableScrollPane.revalidate();
//	
//	JPanel spacingPanel = new JPanel();
//	spacingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//	spacingPanel.add(spacing_Jl);
//	
//	
//    JPanel topPanel = new JPanel();
//    topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//    topPanel.add(artistName_Tf);
//    topPanel.add(instrument_Cb);
//    topPanel.add(videoLink_Tf);
//    topPanel.add(type_Cb);
//    topPanel.add(extraArtist_Tf);
//    topPanel.add(registerAndDownload_Btn);
//
//
//
//    
//    
//    setLayout(new java.awt.GridLayout(5,1));
//    add(tableScrollPane);
//    add(topPanel);
//    add(loadNextArtist_Btn);        
//
//
//
//    fileMenu.add(databasePopulator_Item);   
//    fileMenu.add(configureResourcesConnections_Item);
//    
//    helpMenu.add(about_Item);
//    
//    menuBar.add(fileMenu);
//    menuBar.add(helpMenu);
//
//    this.setJMenuBar(menuBar);
//    
//    
//    
//    this.setSize(700, 400);      
//    this.setLocationRelativeTo(null);
//    this.setTitle("Video Manager");
//    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("videoManager_icon.jpg")));
//    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//    this.setVisible(true);

    public void prepareUI() { 
        

    	JScrollPane tableScrollPane = new JScrollPane(databaseFeedingFileTable);
    	// Force the scrollbars to always be displayed
    	tableScrollPane.setVerticalScrollBarPolicy(
    	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	tableScrollPane.setHorizontalScrollBarPolicy(
        	    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	tableScrollPane.setPreferredSize(new java.awt.Dimension(1,250));
    	
    	JPanel tableScrollPanePanel = new JPanel();
        tableScrollPanePanel.setLayout(new java.awt.GridLayout(0,1));
        tableScrollPanePanel.add(tableScrollPane);
    	    	
        
        JPanel videoRegistrationPanel = new JPanel();
        videoRegistrationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20,10));
        videoRegistrationPanel.add(artistName_Tf);
        videoRegistrationPanel.add(instrument_Cb);
        videoRegistrationPanel.add(videoLink_Tf);
        videoRegistrationPanel.add(type_Cb);
        videoRegistrationPanel.add(extraArtist_Tf);
        videoRegistrationPanel.add(registerAndDownload_Btn);
        
        
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	buttonPanel.add(loadNextArtist_Btn);
        
    	
        this.add(tableScrollPanePanel, BorderLayout.NORTH);
        this.add(videoRegistrationPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        

        fileMenu.add(databasePopulator_Item);   
        fileMenu.add(configureResourcesConnections_Item);
        
        helpMenu.add(about_Item);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);
        
        
        
        this.setSize(700, 400);      
        this.setLocationRelativeTo(null);
        this.setTitle("Video Manager");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("videoManager_icon.jpg")));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        
        
        

        this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
                exitApp();       
            }
        });
        
        
        
        
        artistName_Tf = new JTextField("artist name"); 
        videoLink_Tf= new JTextField("video link"); 
        extraArtist_Tf= new JTextField("extra artist"); 
        
        
//        artistName_Tf.addFocusListener(new FocusAdapter() {
//            public void focusGained(FocusEvent e) {
//                JTextField source = (JTextField)e.getComponent();
//                source.setText("");
//                source.removeFocusListener(this);
//            }
//        });
//        videoLink_Tf.addFocusListener(new FocusAdapter() {
//            public void focusGained(FocusEvent e) {
//                JTextField source = (JTextField)e.getComponent();
//                source.setText("");
//                source.removeFocusListener(this);
//            }
//        });
//        extraArtist_Tf.addFocusListener(new FocusAdapter() {
//            public void focusGained(FocusEvent e) {
//                JTextField source = (JTextField)e.getComponent();
//                source.setText("");
//                source.removeFocusListener(this);
//            }
//        });
        
        
        
        
        

        

        loadNextArtist_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               
            }
        });

        registerAndDownload_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
            }
        });
        
        
        
        
        
        
        databasePopulator_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
//            	DatabasePopulator databasePopulator = new DatabasePopulator();
//                databasePopulator.prepareUI();
                
            }
        });

        configureResourcesConnections_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
//            	ConfigureResourcesConnections configureResourcesConnections = new ConfigureResourcesConnections();
//                configureResourcesConnections.prepareUI();
                
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
    
    
    
    private void jTableInitialisation() throws IOException {
    	
        String[] columnNames= {"artist" , "instrument", "link", "video name", "duration","video type","video ID"};
    	
    	List<Video> videoList = readDatabaseFeederFileToVideoObjects();
    	String[][] videoData = new String[videoList.size()][];
    	for(int i=0;i<videoList.size();i++) 
    		videoData[i]=videoList.get(i).toStringArray();
    	
        databaseFeedingFileTable=new JTable(videoData,columnNames);
    }
    
    
    
    private List<Video> readDatabaseFeederFileToVideoObjects() throws IOException {
    	
    	FileReader flDatabaseFeederFileLine=new FileReader(VideoManagerApp.databaseFeederFilePath);
		BufferedReader brDatabaseFeederFileLine = new BufferedReader(flDatabaseFeederFileLine);

		String[] splitedLineToVideoAtributes;
        List<Video> videoList = new ArrayList<>();

		
        String connectionsLine=brDatabaseFeederFileLine.readLine();
        while(connectionsLine != null){
        	
    		String[] splitConnectionsLine=connectionsLine.trim().split("#");
    		
    		Video video=new Video();
    		video.setArtist_name(splitConnectionsLine[0]);
    		video.setArtist_instrument(splitConnectionsLine[1]);
    		video.setVideo_link(splitConnectionsLine[2]);
    		video.setVideo_name(splitConnectionsLine[3]);
    		video.setVideo_duration(splitConnectionsLine[4]);
    		video.setType_id(splitConnectionsLine[5]);
    		video.setVideo_id(splitConnectionsLine[6]);
    		
    		videoList.add(video);
    		
            connectionsLine=brDatabaseFeederFileLine.readLine();
        }
        
        brDatabaseFeederFileLine.close(); 
        return videoList;
    }
    
    
    
    private void exitApp() {
        int i = JOptionPane.showConfirmDialog(MainFrame.this, "are you sure you want to Exit?");
        if (i == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    
 }
    

