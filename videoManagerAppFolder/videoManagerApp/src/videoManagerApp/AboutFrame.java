package videoManagerApp;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
*
* @author nick
*/
class AboutFrame  extends JFrame {
    
    TextArea textArea = new TextArea("Hello im the developer of the application.\n"
    							  + " This apps is a jazzLibraryApp maintenance tool\n"
    							  + "it's purpose is to manage the the following things:\n\n"
    							  + "1) Automatic search of videos in Youtube through\n"
    							  + "   web browser (Mozila) ,and registration of new\n"
    							  + "   videos on local txt Database FeedingFile\n"
    							  + "2) Database FeedingFile correction mechanisms\n"
    							  + "3) Automatic DatabasePopulation\n\n"
    							  + "The Developer Nickos Sarantopoulos ID:121050");    

    public void prepareUI() {

        

 
        this.add(textArea, BorderLayout.PAGE_START);


  
                
       this.setSize(320, 210);
       this.setLocationRelativeTo(null);
       this.setTitle("About The App");
       this.setResizable(false);
       this.setVisible(true);
       this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    
       
       
       
       
       
       
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
    
    
    
    
    
    
    
    
 
