
package videoManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;

import javax.swing.BoxLayout;
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
public class AddArtistFrame extends JFrame {
    
    private JLabel addSingleAtistName_Lb;
    private JLabel addManyArtistNamesLb;
    
    private JTextField addSingleAtistName_Tf;
    
    private JButton addSingleAtistName_Btn;
    private JButton browseManyArtistNames_Btn;

    
    public AddArtistFrame() {
       super();

       addSingleAtistName_Lb=new JLabel("Add Single Artist Name: ");
       addManyArtistNamesLb=new JLabel("Add Many Artist Name:");

       addSingleAtistName_Tf = new JTextField(10);
        
       Icon browse_icon = new ImageIcon("C:\\Users\\n.sarantopoulos\\Desktop\\jazzLibraryApp\\videoManagerAppFolder\\videoManagerApp\\src\\videoManager\\browse_icon.png");
       
       addSingleAtistName_Btn = new JButton("Go");
       browseManyArtistNames_Btn = new JButton(browse_icon);

    }
    
    public void prepareUI() {
        
        

    	
    	JPanel addAristNamePanel = new JPanel();
    	addAristNamePanel.setLayout(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
    	
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	addAristNamePanel.add(addSingleAtistName_Lb,gbc);
    	
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	addAristNamePanel.add(addSingleAtistName_Tf,gbc);
    	
    	gbc.gridx = 2;
    	gbc.gridy = 0;
    	addAristNamePanel.add(addSingleAtistName_Btn,gbc);
    	

	    	gbc.gridx = 0;
	    	gbc.gridy = 1;
	    	addAristNamePanel.add(new JLabel("."),gbc);
	    	
	    	gbc.gridx = 1;
	    	gbc.gridy = 1;
	    	addAristNamePanel.add(new JLabel("."),gbc);
	    	

    	gbc.gridx = 0;
    	gbc.gridy = 2;
    	addAristNamePanel.add(addManyArtistNamesLb,gbc);
    	
    	gbc.gridx = 2;
    	gbc.gridy = 2;
    	addAristNamePanel.add(browseManyArtistNames_Btn,gbc);
    	
    	
    	
    	this.add(addAristNamePanel, BorderLayout.CENTER);
    	
        
        
        this.setSize(400, 140);
        this.setLocationRelativeTo(null);
        this.setTitle("Add Artist");
        this.setVisible(true);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


       

        addSingleAtistName_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	
            	setInvisible();
            }
        });

        browseManyArtistNames_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            	
            	setInvisible();
            }
        });
        
   

        this.addWindowListener(new WindowAdapter() {
            @Override                               
            public void windowClosing(WindowEvent e) {
            	setInvisible();
            }
        });

        
   
    }

        
        



    private void setInvisible() {

    	this.setVisible(false);
    }

    
}
