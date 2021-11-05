
package videoManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;



/**
*
* @author nick
*/

public class MainFrame extends JFrame {
        
	
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




    public MainFrame() {
        super();       
        
        spacing_Jl=new JLabel("~#    %    #~");
        
        artistName_Tf = new JTextField("artist name"); 
        videoLink_Tf= new JTextField("video link"); 
        extraArtist_Tf= new JTextField("extra artist"); 
        
        instrument_Cb = new JComboBox(instruments_StringArray); 
        type_Cb = new JComboBox(type_StringArray); 
        
        loadNextArtist_Btn = new JButton("Load Next Artist"); 
        registerAndDownload_Btn = new JButton("Register And Download");
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        databasePopulator_Item = new JMenuItem("Database Populator");
        configureResourcesConnections_Item = new JMenuItem("Configure Resources Connections");
        about_Item = new JMenuItem("about");

    }




    public void prepareUI() { 
        

    	JPanel spacingPanel = new JPanel();
    	spacingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	spacingPanel.add(spacing_Jl);
    	
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));
        topPanel.add(artistName_Tf);
        topPanel.add(instrument_Cb);
        topPanel.add(videoLink_Tf);
        topPanel.add(type_Cb);
        topPanel.add(extraArtist_Tf);

        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(loadNextArtist_Btn);
        bottomPanel.add(registerAndDownload_Btn);

        this.add(spacingPanel, BorderLayout.NORTH);
        this.add(topPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        fileMenu.add(databasePopulator_Item);   
        fileMenu.add(configureResourcesConnections_Item);
        
        helpMenu.add(about_Item);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);
        
        
        
        this.setSize(500, 180);      
        this.setLocationRelativeTo(null);
        this.setTitle("Video Manager");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("videoManager_icon.jpg")));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        
        
        

        this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
                exitApp();       
            }
        });
        
        

        

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
    
    
    
    private void exitApp() {
        int i = JOptionPane.showConfirmDialog(MainFrame.this, "are you sure you want to Exit?");
        if (i == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    
 }
    

