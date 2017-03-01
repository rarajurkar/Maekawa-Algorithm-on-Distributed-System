import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Server2 {        
        public static void main(String args[]){
            Socket sSocket, c1Socket, c2Socket;
            int serverPort = 26000;
            try{
               //client to server
               ServerSocket serverSocket = new ServerSocket(serverPort);
               System.out.println("Server Started and listening to the port "+serverPort); 
               //Server is always running. This is done using this while(true) loop  
           while(true){
               sSocket = serverSocket.accept();
               InputStream is = sSocket.getInputStream();
               InputStreamReader isr = new InputStreamReader(is);
               BufferedReader br = new BufferedReader(isr);
               OutputStream os = sSocket.getOutputStream(); 
               OutputStreamWriter osw = new OutputStreamWriter(os); 
               BufferedWriter bw = new BufferedWriter(osw); 
               int clientPort =  br.read(); 
               
               if(clientPort ==serverPort){ 
                  //read the option
                  c1Socket = new Socket("10.176.66.51", 25000);
                  System.out.println("port 26000 in use");
                  c2Socket = new Socket("10.176.66.53", 27000);
                  System.out.println("port 27000 in use");
                  // this code is to connect with other server 
                  InputStream is1 = c1Socket.getInputStream();
                  InputStreamReader isr1 = new InputStreamReader(is1);
                  BufferedReader br1 = new BufferedReader(isr1);
                  OutputStream os1 = c1Socket.getOutputStream(); 
                  OutputStreamWriter osw1 = new OutputStreamWriter(os1); 
                  BufferedWriter bw1 = new BufferedWriter(osw1);  
                                      
                  InputStream is2 = c2Socket.getInputStream();
                  InputStreamReader isr2 = new InputStreamReader(is2);
                  BufferedReader br2 = new BufferedReader(isr2);
                  OutputStream os2 = c2Socket.getOutputStream(); 
                  OutputStreamWriter osw2 = new OutputStreamWriter(os2); 
                  BufferedWriter bw2 = new BufferedWriter(osw2);
                  
                  File f = new File("./s2.txt");
                  FileWriter fileWriter = new FileWriter(f,true);
                  
                  String st;
                  st = br.readLine();
                  System.out.println(st); 
                  fileWriter.write(st+"\n"); 
                  fileWriter.flush();

			
			bw1.write(clientPort);
		        bw1.flush();
			bw2.write(clientPort);
		        bw2.flush();
			
                  bw1.write(st+"\n");
                  bw1.flush();
                  bw2.write(st+"\n");
                  bw2.flush();
                  bw.write(f.getName()+"--Written successfully.. \n");
                  bw.flush();
                  System.out.println("done with writing");
                  fileWriter.close();
                  bw1.close();
                  bw2.close();
                  bw.close();
               }              
               else{
                            //write the file
            	   File f = new File("./s2.txt");
                   FileWriter fileWriter = new FileWriter(f,true);
                   String st=br.readLine();
                   //while(!((st = br.readLine()).equalsIgnoreCase("q"))){//((st=br.readLine())!=null){
                   System.out.println(st); 
                   fileWriter.write(st+"\n"); 
                   fileWriter.flush();
                   //}
                   fileWriter.close();
                 }
              }
            }catch(Exception e){
        	   e.printStackTrace();
          }
    }   
}
