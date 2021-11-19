/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;





/**
*
* @author nick
*/
class AboutFrame  extends JFrame {
    
	
    private JLabel conectedToServerCondition_Lb;
    private JLabel conectedToServerConditionValue_Lb;

    private JLabel appVersion_Lb;
    private JLabel appVersionValue_Lb;


    public AboutFrame() {
       super();
       
       conectedToServerCondition_Lb=new JLabel("Conected To Server Condition: ");
       determineConectionCondition();
        
       appVersion_Lb=new JLabel("App Version: ");
       appVersionValue_Lb=new JLabel("v1");
       appVersionValue_Lb.setForeground(Color.blue);
        
    }
    
    
    public void prepareUI() {

        

        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(conectedToServerCondition_Lb);
        topPanel.add(conectedToServerConditionValue_Lb);
        topPanel.add(appVersion_Lb);
        topPanel.add(appVersionValue_Lb);
        
        this.add(topPanel, BorderLayout.PAGE_START);


  
                
       this.setSize(300, 300);
       this.setLocationRelativeTo(null);
       this.setTitle("About The App");
       this.setVisible(true);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
        
        
    
    
    
     private void determineConectionCondition() {

    	 if (VideoManagerApp.isLogedInToServer == true) {
    		 conectedToServerConditionValue_Lb=new JLabel("Online!");
    	     appVersionValue_Lb.setForeground(Color.green);

    	 }
    	 else {
    		 conectedToServerConditionValue_Lb=new JLabel("Offline..");
    	     appVersionValue_Lb.setForeground(Color.red);
    	 }
    	 
     }
     

}
    
    
    
    
    
    
    
    
 
