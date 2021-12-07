/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

    	 if (VideoManagerAppMain.isLogedInToServer == true) {
    		 conectedToServerConditionValue_Lb=new JLabel("Online!");
    	     appVersionValue_Lb.setForeground(Color.green);

    	 }
    	 else {
    		 conectedToServerConditionValue_Lb=new JLabel("Offline..");
    	     appVersionValue_Lb.setForeground(Color.red);
    	 }
    	 
     }
     

}
    
    
    
    
    
    
    
    
 
