
package videoManager;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JTextField;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;


/**
*
* @author nick
*/
public class DatabasePopulator extends JFrame {
    
    private JComboBox databaseNames_Cb;

    private JLabel username_Lb;
    private JLabel password_Lb;    
    
    private JTextField username_Tf;
    private JTextField password_Tf;
    
    private JButton establishConnection_Btn;
    
    Separator separator;
    
    private JLabel connectionIndicator_Lb;    
    private JLabel databaseName_Lb;  
    
    private JButton populate_Btn;



    
    public DatabasePopulator() {
       super();
       
       databaseNames_Cb = new JComboBox(VideoManagerApp.databaseNames); 


       username_Lb=new JLabel("Server username: ");
       password_Lb=new JLabel("Server password: ");
       
       username_Tf = new JTextField(10);
       password_Tf = new JTextField(10);
       password_Tf.setVisible(false);
       
       establishConnection_Btn = new JButton("Establish Connection");

       
       
       
       separator = new Separator();
       
       
       
       connectionIndicator_Lb=new JLabel("you are not conected to Database ");
       connectionIndicator_Lb.setForeground(Color.red);
       
       connectionIndicator_Lb=new JLabel("you are conected to Database ");
       connectionIndicator_Lb.setForeground(Color.green);
       databaseName_Lb=new JLabel(VideoManagerApp.databaseName);   //perneis to index kai travas to item apo thn thesh tou public String pinaka
       databaseName_Lb.setForeground(Color.blue);
       
       populate_Btn = new JButton("Populate");



    }
    
    public void prepareUI() {
        
    	if(VideoManagerApp.isLogedInToServer==false) {
    		
    		loginToServerLayout();
    		
    	}
    	else {
    		
    		populateToDatabaseLayout();
    		
    	}
    	
    	
    	
    	
    	
       


        


        
        this.setSize(400, 220);
        this.setLocationRelativeTo(null);
        this.setTitle("Database Populator");
        this.setVisible(true);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        
        
        
        
        
       

        establishConnection_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	if(inputInspection()==true)  // an einai keno , an kanei thn sundesh , kataxorei 
	            	populateToDatabaseLayout();
            	else
            		
            	
            }
        });

        
    }
    
    
    private void loginToServerLayout(){
    	 
    	 JPanel topPanel1 = new JPanel();
         topPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
         topPanel1.add(username_Lb);
         topPanel1.add(username_Tf);

         
         JPanel topPanel2 = new JPanel();
         topPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
         topPanel2.add(password_Lb);
         topPanel2.add(password_Tf);
    
         
         JPanel bottomPanel = new JPanel();
         bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
         bottomPanel.add(establishConnection_Btn);
         
         this.add(topPanel1, BorderLayout.NORTH);
         this.add(topPanel2, BorderLayout.NORTH);
         
         this.add(bottomPanel, BorderLayout.NORTH);

        
    }
    
    
    private void populateToDatabaseLayout(){
    	
    	JPanel topPanel1 = new JPanel();
        topPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel1.add(connectionIndicator_Lb);
        topPanel1.add(databaseName_Lb);
   
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(establishConnection_Btn);
    	
        this.add(topPanel1, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.NORTH);
        
    }
    
    
    
    

    private boolean inputInspection() {
        
        String usedArtistNamesFilePath=usedArtistNamesFilePath_Tf.getText().trim();
        String artistNamesFilePath=artistNamesFilePath_Tf.getText().trim();
        String databaseFeederFilePath=databaseFeederFilePath_Tf.getText().trim();
        String videoReceivingFolderPath=videoReceivingFolderPath_Tf.getText().trim();
        
        
        
        
    

        if(usedArtistNamesFilePath.equals("")||artistNamesFilePath.equals("")||
        		databaseFeederFilePath.equals("")||videoReceivingFolderPath.equals("")==true){
            JOptionPane.showMessageDialog(DatabasePopulator.this, "there are blank fields  ", "Warning",
            JOptionPane.WARNING_MESSAGE);
            return false;
        }    
            
            
            
        //phoneNo
            if(phoneNo.length()!=9  || isNumeric(phoneNo)==false){
                JOptionPane.showMessageDialog(DatabasePopulator.this, "plz , enther a corect phone no", "Warning",
            JOptionPane.WARNING_MESSAGE);
                return false;
            }
        //ticketId
            if(ticketId.length()!=3  || isNumeric(ticketId)==false){
                JOptionPane.showMessageDialog(DatabasePopulator.this, "plz , enther a corect ticket Id (up to 3 digits) ", "Warning",
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
                JOptionPane.showMessageDialog(DatabasePopulator.this, "plz , enther a corect issueDate(like 00/00/00) ", "Warning",
            JOptionPane.WARNING_MESSAGE);
                return false;
            }
        //seatId
            if(isNumeric(seatId)==false){//elenxo an einai arithmitiko
                JOptionPane.showMessageDialog(DatabasePopulator.this, "plz , enter a corect seatId \n(must be an integer) ", "Warning",
            JOptionPane.WARNING_MESSAGE);
                return false;
            }
        //cost
            String[] splitedcost=cost.split(".") ;
            //prota elenxo an einai arithmitiko me thn sinartish pou eftia3a ..meta to kano split sto "," an exei 2 stixia o pinakas paei
            //na pei oti to string exei koma ,, an to splitedcost[1].length()!=2 einai 2 paei na pei oti exo 2 dekadika psifia
            if(splitedcost.length!=2||isNumeric(splitedcost[0])==false || isNumeric(splitedcost[1])==false||splitedcost[1].length()!=2){
                JOptionPane.showMessageDialog(DatabasePopulator.this, "plz , cost must be numeric \n with 2 decimal points ", "Warning",
            JOptionPane.WARNING_MESSAGE);
                return false;
            }
        
       
        

        return true;
    }
    
    
    
    private void setInvisible() {
        int i = JOptionPane.showConfirmDialog(DatabasePopulator.this, "Do you want to exit the Ticket Creation?"
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
