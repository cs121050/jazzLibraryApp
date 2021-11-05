import java.awt.Desktop;
import java.util.Scanner;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;



public class WebSearcher2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        
        
       
                
        
        
        
        String inputFileName;
        
        
        
        
        SystemMessages(1);
        
        
        

        fileCompareTool();
        
    }
    
    
    
    
 

    public static void SystemPause(){

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    public static void SystemMessages(int choice){
        
        if(choice==1){
            System.out.println("~  How The Bloody Thing Works ~\n"
                             + ""
                             + ""
                             + ""
                             + "         \n");
        }
        else if(choice==2){
            
        }
        else if(choice==3){
            
        }
        
    }    
     
    
    
    
    
    
    
    
    public static void fileCompareTool() throws IOException, InterruptedException{

        FileReader frName,frInstru;
        BufferedReader brName,brInstru;
        String nameLine;
        String instruLine;
        
        
        String instrument;
        
        
        
        
        FileWriter fw = new FileWriter("C:\\Users\\NICK\\Desktop\\artistPlaysInstrumentt.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
            pw.println("#instrument#");
        
        
        
        frName=new FileReader("C:\\Users\\NICK\\Desktop\\jazzNamesMOD.txt");
        brName = new BufferedReader(frName);
        
        
        frInstru=new FileReader("C:\\Users\\NICK\\Desktop\\ArtistByInstrument3.txt");
        brInstru = new BufferedReader(frInstru);
        brInstru.mark(22220000);
        
        instruLine=brInstru.readLine();
        
        
        
        nameLine=brName.readLine();//h proth gramh einai to name
        instrument=instruLine;  //h proth gramh einai to proto organo

        
        
        
        
        
        
        while(nameLine != null){
            pw.print(nameLine +"#instrument#");
            System.out.println("instru= "+instrument);

            
            
            

            while(true){
                 
                if (instruLine.toLowerCase().contains(nameLine)) {
                    
                    pw.print(instrument + " ");//  sigrish an to onoma periexete sthn gramh me ta onomata kathe organou
                    System.out.println(nameLine+"  !!!!!!!   "+instrument);
                    brInstru.reset();
                    brInstru.mark(22220000);
                    instruLine=brInstru.readLine();
                    instrument=instruLine;  //h proth gramh einai to proto organo
                    break;
                    
                }
                
                
                if (instruLine.contains("=")){

                    instrument=brInstru.readLine();
                    System.out.println("instru= "+instrument);

                }
                
                instruLine=brInstru.readLine();
               
                if (instruLine.contains("@")){
                                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    brInstru.reset();
                    brInstru.mark(22220000);
                    instruLine=brInstru.readLine();
                    instrument=instruLine;  //h proth gramh einai to proto organo
                    break;
                }
                
            }
                                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            //SystemPause(); //pagonei , kai dinei minima sthn othonh

            pw.println("");
            nameLine=brName.readLine(); 



        }

        pw.close();
        brName.close();
    }
    
}

    
    
    
    
    
    
    
    


    