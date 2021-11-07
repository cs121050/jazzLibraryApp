
package videoManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;


/**
*
* @author nick
*/
public class ConfigureResourcesConnections extends JFrame {
    
    private JLabel usedArtistNamesFilePath_Lb;
    private JLabel artistNamesFilePath_Lb;
    private JLabel databaseFeederFilePath_Lb;
    private JLabel videoReceivingFolderPath_Lb;
    
    
    private JTextField usedArtistNamesFilePath_Tf;
    private JTextField artistNamesFilePath_Tf;
    private JTextField databaseFeederFilePath_Tf;
    private JTextField videoReceivingFolderPath_Tf;
    
    private JButton usedArtistNamesFilePath_browseBtn;
    private JButton artistNamesFilePath_browseBtn;
    private JButton databaseFeederFilePath_browseBtn;
    private JButton videoReceivingFolderPath_browseBtn;
    
    private JButton link_Btn;
    

    
    public ConfigureResourcesConnections() {
       super();

       usedArtistNamesFilePath_Lb=new JLabel("UsedArtistNames File Path_Lb: ");
       artistNamesFilePath_Lb=new JLabel("ArtistNames File Path_Lb: ");
       databaseFeederFilePath_Lb=new JLabel("DatabaseFeeder File Path_Lb: ");
       videoReceivingFolderPath_Lb=new JLabel("videoReceiveing Folder Path:");
        
       usedArtistNamesFilePath_Tf = new JTextField(100);
       artistNamesFilePath_Tf = new JTextField(100);
       databaseFeederFilePath_Tf = new JTextField(100);
       videoReceivingFolderPath_Tf = new JTextField(100);
        
       Icon browse_icon = new ImageIcon("browse_icon.png");
       
       usedArtistNamesFilePath_browseBtn = new JButton(browse_icon);
       artistNamesFilePath_browseBtn = new JButton(browse_icon);
       databaseFeederFilePath_browseBtn = new JButton(browse_icon);
       videoReceivingFolderPath_browseBtn = new JButton(browse_icon);


       link_Btn = new JButton("LINK");

    }
    
    public void prepareUI() {
        
        

        
        JPanel topPanel1 = new JPanel();
        topPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel1.add(usedArtistNamesFilePath_Lb);
        topPanel1.add(usedArtistNamesFilePath_Tf);
        topPanel1.add(usedArtistNamesFilePath_browseBtn);

        
        JPanel topPanel2 = new JPanel();
        topPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel2.add(artistNamesFilePath_Lb);
        topPanel2.add(artistNamesFilePath_Tf);
        topPanel2.add(artistNamesFilePath_browseBtn);

        
        JPanel topPanel3 = new JPanel();
        topPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel3.add(databaseFeederFilePath_Lb);
        topPanel3.add(databaseFeederFilePath_Tf);
        topPanel3.add(databaseFeederFilePath_browseBtn);

        
        JPanel topPanel4 = new JPanel();
        topPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel4.add(videoReceivingFolderPath_Lb);
        topPanel4.add(videoReceivingFolderPath_Tf);
        topPanel4.add(videoReceivingFolderPath_browseBtn);

        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(link_Btn);


        this.add(topPanel1, BorderLayout.NORTH);
        this.add(topPanel2, BorderLayout.NORTH);
        this.add(topPanel3, BorderLayout.NORTH);
        this.add(topPanel4, BorderLayout.NORTH);

       
        this.add(bottomPanel, BorderLayout.SOUTH);


        
        this.setSize(400, 220);
        this.setLocationRelativeTo(null);
        this.setTitle("Configure Resources Connections");
        this.setVisible(true);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        
        
        
        
        
       

        usedArtistNamesFilePath_browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	
            }
        });

        artistNamesFilePath_browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            	
            }
        });
        
        databaseFeederFilePath_browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            	
            }
        });
        
        videoReceivingFolderPath_browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	
            }
        });
       
        link_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputInspection()==true){
                  
//                	public static JLabel artistNamesFilePath;
//                	public static JLabel databaseFeederFilePath;
//                	public static JLabel videoReceiverFolderPath;
                    	
                    
                }
            }
        });
   
    }
    
    

    private boolean inputInspection() {
        
        String usedArtistNamesFilePath=usedArtistNamesFilePath_Tf.getText().trim();
        String artistNamesFilePath=artistNamesFilePath_Tf.getText().trim();
        String databaseFeederFilePath=databaseFeederFilePath_Tf.getText().trim();
        String videoReceivingFolderPath=videoReceivingFolderPath_Tf.getText().trim();
        
        
        
        
    

        if(usedArtistNamesFilePath.equals("")||artistNamesFilePath.equals("")||
        		databaseFeederFilePath.equals("")||videoReceivingFolderPath.equals("")==true){
            JOptionPane.showMessageDialog(ConfigureResourcesConnections.this, "there are blank fields  ", "Warning",
            JOptionPane.WARNING_MESSAGE);
            return false;
        }    
            
            
            
        //phoneNo
            if(phoneNo.length()!=9  || isNumeric(phoneNo)==false){
                JOptionPane.showMessageDialog(ConfigureResourcesConnections.this, "plz , enther a corect phone no", "Warning",
            JOptionPane.WARNING_MESSAGE);
                return false;
            }
        //ticketId
            if(ticketId.length()!=3  || isNumeric(ticketId)==false){
                JOptionPane.showMessageDialog(ConfigureResourcesConnections.this, "plz , enther a corect ticket Id (up to 3 digits) ", "Warning",
            JOptionPane.WARNING_MESSAGE);
                return false;
            }
        //issueDate
            String[] splitedIssueDate=issueDate.split("/") ;
            //prota to kano split sta "\" an xoristei sta 3 paei na pei oti exo 2 "\"
            //meta gia kathe tou pinaka tsekaro, an einai arithmoi anamesa ton "\" 
            //meta blepo posoi xaraktires exei to kathna ,, prepei na exei 2 !
            if(splitedIssueDate.length!=3  || isNumeric(splitedIssueDate[0])==false
                            || isNumeric(splitedIssueDate[1])==false|| isNumeric(splitedIssueDate[2])==false
                    || splitedIssueDate[0].length()!=2|| splitedIssueDate[1].length()!=2|| splitedIssueDate[2].length()!=2){
                JOptionPane.showMessageDialog(ConfigureResourcesConnections.this, "plz , enther a corect issueDate(like 00/00/00) ", "Warning",
            JOptionPane.WARNING_MESSAGE);
                return false;
            }
        //seatId
            if(isNumeric(seatId)==false){//elenxo an einai arithmitiko
                JOptionPane.showMessageDialog(ConfigureResourcesConnections.this, "plz , enter a corect seatId \n(must be an integer) ", "Warning",
            JOptionPane.WARNING_MESSAGE);
                return false;
            }
        //cost
            String[] splitedcost=cost.split(".") ;
            //prota elenxo an einai arithmitiko me thn sinartish pou eftia3a ..meta to kano split sto "," an exei 2 stixia o pinakas paei
            //na pei oti to string exei koma ,, an to splitedcost[1].length()!=2 einai 2 paei na pei oti exo 2 dekadika psifia
            if(splitedcost.length!=2||isNumeric(splitedcost[0])==false || isNumeric(splitedcost[1])==false||splitedcost[1].length()!=2){
                JOptionPane.showMessageDialog(ConfigureResourcesConnections.this, "plz , cost must be numeric \n with 2 decimal points ", "Warning",
            JOptionPane.WARNING_MESSAGE);
                return false;
            }
        
       
        

        return true;
    }
    
    
    
    private void setInvisible() {
        int i = JOptionPane.showConfirmDialog(ConfigureResourcesConnections.this, "Do you want to exit the Ticket Creation?"
                                            + "\nall tha atributes you just edited will be lost !");
        if (i == JOptionPane.YES_OPTION) {
            this.setVisible(false);
        }
    }
    
    
    public boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {   //AMA einai otidipote alo apo arithmos ,, totetha peta3ei exeption
        double d = Double.parseDouble(strNum); //kai tha thosei  FALSE
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}
    
    
    
    
    
    
    
}
