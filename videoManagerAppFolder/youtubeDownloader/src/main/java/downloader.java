import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


//ContentJazzNames

public class downloader {
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
            
		
                 
                
                String urlLine;    
                String[] splitedLineToWords;
                
                String download_path="D:";                   //to directory pou tha apothikeutei to video
                String[] command ={"cmd"}; //gia to youtubeDownloader kodika
                Process p;
                
                Scanner keyboard = new Scanner(System.in);
                   
                
                
                
                
                
                System.out.println("Wich file Content File you want to Draw URLs from??! ");
                String ContentFileName=keyboard.next(); 
        
                //iparxei to file?
                File f=new File("D:\\" + ContentFileName +".txt");
                if (f.exists() == false ){
                    System.out.println("file "+ContentFileName+" does not exists ,plz make it and run the program again");
                    System.exit(0);
                }
                // anoi3eto gia diavasma
                FileReader fr=new FileReader("D:\\" + ContentFileName +".txt");
                BufferedReader br = new BufferedReader(fr);

               
                
                
                
                

                         
                urlLine=br.readLine();  //diavase thn gramh kai spastein sta ":" thw oste na deis poia 3ekinoun me https
                splitedLineToWords=urlLine.split(":");// kai etsi na 3ereis poia einai LINKS
                
                new File("D:\\").mkdirs(); //ftia3e fakelo tade , gia na valeiw ekei ta downloads
                while(urlLine != null){
                    urlLine=urlLine.replaceAll("\\s+","_");
                    
                    if(!splitedLineToWords[0].equals("https") && !splitedLineToWords[0].equals("=") && !splitedLineToWords[0].equals(" ")){
                        new File("D:\\"+urlLine).mkdirs(); //kathe onoma tou Content , tha exei kai apo ena fakelo
                        download_path="D:\\"+urlLine;  //alaxe thn dieuthinsh proorismou , oste na mpei ston
                                                                                            //katalilo fakelo
                        TimeUnit.MICROSECONDS.sleep(1); //gia na mhn mpourdouklothei ,kai mou faei fakelous 
                    }
                    
                    
                    
                    
                    if(splitedLineToWords[0].equals("https")){
                        try {
                            p = Runtime.getRuntime().exec(command); 
                            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
                            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
                            PrintWriter stdin = new PrintWriter(p.getOutputStream());
                            stdin.println("cd \""+download_path+"\"");     //pigene sto directoruy poy einai to youtube-dl 
                            stdin.println("D:\\"+"youtube-dl "+urlLine);  //gia na ginei to download xrisimopio to etoimo programa youtube-dl 
                            stdin.close();                                   //kai edo leme pou tha to brei to programa
                            p.waitFor();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    
                    
                    System.out.println(urlLine);
                    urlLine=br.readLine();
                    splitedLineToWords=urlLine.split(":");                               //den to xorizei h malakia !!                    
                    
                }
 
  
}
