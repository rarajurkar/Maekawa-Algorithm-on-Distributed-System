import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Client7 {
	private static Socket socket; 
    private static Socket client3, client4, client5;
    static boolean c3=false, c4=false, c5=false;
    static int seq_num=-1;
    static ServerSocket c2Socket, c3Socket, c4Socket;
    static Socket socket2, socket3, socket4;
    static boolean k1=false, k2=false, k3=false, k4=false;
    static int request=0,reply=0, release=0, enquire=0, failed=0; 
static boolean runServer = true;
    
    public static void main(String args[]) throws UnknownHostException, IOException, InterruptedException{
    	List<Thread> tList = new ArrayList<Thread>();
    	Thread t1 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				c2Socket = new ServerSocket(28005);
    				c2Socket.setSoTimeout(100000);
    				System.out.println("waiting 28005");
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			k1=true;
			System.out.println(k1);
    		}
    		
    	});
    	t1.start();
    	tList.add(t1);
    	Thread t2 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				c3Socket = new ServerSocket(28007);
    				c3Socket.setSoTimeout(100000);
    				System.out.println("waiting 28007");
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			k2=true;
			System.out.println(k2);
    		}
    		
    	});
    	t2.start();
    	tList.add(t2);
    	Thread t3 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				c4Socket = new ServerSocket(28010);
    				c4Socket.setSoTimeout(100000);
    				System.out.println("waiting 28010");
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			k3=true;
			System.out.println(k3);
    		}
    		
    	});
    	t3.start();
    	tList.add(t3);
    	Thread t4 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				Thread.sleep(50000);
    				System.out.println("client sockets 28016, 28017, 28018");    				
    				client3 = new Socket("10.176.66.56",28016);
    				client3.setSoTimeout(100000);
    		    	client4 = new Socket("10.176.66.57",28017);
    				client4.setSoTimeout(100000);
    		    	client5 = new Socket("10.176.66.58",28018);
    				client5.setSoTimeout(100000);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			k4=true;
			System.out.println(k4);
    		}
    		
    	});
    	t4.start();
    	tList.add(t4);
    	
    	for(Thread temp : tList){
    		temp.join();
    	}// wait to get all boolean values
       	//ServerSocket c3Socket = new ServerSocket(28007);
    	
    	//ServerSocket c4Socket = new ServerSocket(28010);
    	
    	List<Long> queue = new ArrayList<Long>();
    	List<Integer> queueId = new ArrayList<Integer>();
    	Map<Integer, Long> queueMap = new HashMap<Integer, Long>();
    	
    	//Thread.sleep(90000);
    	int no=50;
    	// this will send request to other client to access the critical section
    	if(k1 && k2 && k3 && k4){
		//while(true)
{
			Thread ts2 = new Thread(new Runnable() {           
            public void run() {
	int id=0; 
                //do stuff here
            	try {
			while(runServer){
				socket2 = c2Socket.accept();
            		InputStream issc2 = socket2.getInputStream();
    	            InputStreamReader isrsc2 = new InputStreamReader(issc2);
    	            BufferedReader brsc2 = new BufferedReader(isrsc2);
    	            OutputStream ossc2 = socket2.getOutputStream(); 
    	            OutputStreamWriter oswsc2 = new OutputStreamWriter(ossc2); 
    	            BufferedWriter bwsc2 = new BufferedWriter(oswsc2); 
			String str;
    	            //Thread.sleep(10000);
		            if((str = brsc2.readLine())!=null){
					String[] s= new String[2];
		           		s = str.split("-");
				    id=Integer.parseInt(s[0]);
				    long timestamp = Long.parseLong(s[1]);
				    queue.add(timestamp);
				    queueId.add(id);
				    queueMap.put(id, timestamp);
				
		    	   
    	            if(id==queueId.get(0)){
    	            	bwsc2.write("1"+"\n");
    		            bwsc2.flush();
			    reply++;
    	            }
    	            else{
    	            	bwsc2.write("0"+"\n");
    		            bwsc2.flush();
			    enquire++;
    	            }}

			}
            		
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
            } 
        });
        ts2.start();
        Thread ts3 = new Thread(new Runnable() {           
            public void run() { 
		int id=0;
                //do stuff here
            	try {
			while(runServer){
				socket3 = c3Socket.accept();
            		InputStream issc3 = socket3.getInputStream();
    	            InputStreamReader isrsc3 = new InputStreamReader(issc3);
    	            BufferedReader brsc3 = new BufferedReader(isrsc3);
    	            OutputStream ossc3 = socket3.getOutputStream(); 
    	            OutputStreamWriter oswsc3 = new OutputStreamWriter(ossc3); 
    	            BufferedWriter bwsc3 = new BufferedWriter(oswsc3);
			String str;
    	            //Thread.sleep(10000);
		            if((str = brsc3.readLine())!=null){
					String[] s= new String[2];
		           		s = str.split("-");
				    id=Integer.parseInt(s[0]);
				    long timestamp = Long.parseLong(s[1]);
				    queue.add(timestamp);
				    queueId.add(id);
				    queueMap.put(id, timestamp);
				
		    	   
    	            if(id==queueId.get(0)){
    	            	bwsc3.write("1"+"\n");
    		            bwsc3.flush();
			    reply++;
    	            }
    	            else{
    	            	bwsc3.write("0"+"\n");
    		            bwsc3.flush();
			    enquire++;
    	            }}

			}
            		
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
            } 
        });
        ts3.start();
    	
    	Thread ts4 = new Thread(new Runnable() {           
            public void run() { 
		int id=0;
                //do stuff here
            	try {
			while(runServer){
            		socket4 = c4Socket.accept();
            		InputStream issc4 = socket4.getInputStream();
    	            InputStreamReader isrsc4 = new InputStreamReader(issc4);
    	            BufferedReader brsc4 = new BufferedReader(isrsc4);
    	            OutputStream ossc4 = socket4.getOutputStream(); 
    	            OutputStreamWriter oswsc4 = new OutputStreamWriter(ossc4); 
    	            BufferedWriter bwsc4 = new BufferedWriter(oswsc4);
			String str;
    	           // Thread.sleep(10000);
		            if((str = brsc4.readLine())!=null){
					String[] s= new String[2];
		           		s = str.split("-");
				    id=Integer.parseInt(s[0]);
				    long timestamp = Long.parseLong(s[1]);
				    queue.add(timestamp);
				    queueId.add(id);
				    queueMap.put(id, timestamp);
				
		    	   
    	            if(id==queueId.get(0)){
    	            	bwsc4.write("1"+"\n");
    		            bwsc4.flush();
			reply++;
    	            }
    	            else{
    	            	bwsc4.write("0"+"\n");
    		            bwsc4.flush();
			enquire++;
    	            }}

			
			}
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
            } 
        });
        ts4.start();


		}
		//int no = 50;
	    	while((no--)>0){
	    		Thread tc3 = new Thread(new Runnable() {           
	    	        public void run() { 
	    	            //do stuff here
	    	        	
	    				try {
					System.out.println("inside tc3");
	    				InputStream isc3 = client3.getInputStream();		
	    		            InputStreamReader isrc3 = new InputStreamReader(isc3);
	    		            BufferedReader brc3 = new BufferedReader(isrc3);
	    		            OutputStream osc3 = client3.getOutputStream(); 
	    		            OutputStreamWriter oswc3 = new OutputStreamWriter(osc3); 
	    		            BufferedWriter bwc3 = new BufferedWriter(oswc3);
	    		            Date d = new Date();
	    		            String str = "7-"+d.getTime();
				    System.out.println(str);
	    		            bwc3.write(str+"\n");
	    		            bwc3.flush();
	    		            String string;
//Thread.sleep(10000);
				    if((string=brc3.readLine())!=null){
					int result =Integer.parseInt(string);

	    		            if(result == 1){
	    		            	c3=true;
	    		            }
					
				     }

	    		            
	    		   			} catch (IOException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}    
	    	        } 
	    	    });
	    	    tc3.start();
	    	    Thread tc4 = new Thread(new Runnable() {           
	    	        public void run() { 
	    	            //do stuff here
	    	        	
	    				try {
					System.out.println("Inside tc4");
	    		            InputStream isc4 = client4.getInputStream();
	    		            InputStreamReader isrc4 = new InputStreamReader(isc4);
	    		            BufferedReader brc4 = new BufferedReader(isrc4);
	    		            OutputStream osc4 = client4.getOutputStream(); 
	    		            OutputStreamWriter oswc4 = new OutputStreamWriter(osc4); 
	    		            BufferedWriter bwc4 = new BufferedWriter(oswc4);
	    		            Date d = new Date();
	    		            String str = "7-"+d.getTime();
				    System.out.println(str);
	    		            bwc4.write(str+"\n");
	    		            bwc4.flush();
	    		            String string;
				//Thread.sleep(10000);
				    if((string=brc4.readLine())!=null){
					
				int result =Integer.parseInt(string);
	    		            if(result == 1){
	    		            	c4=true;
	    		            }
				     }

	    		            
	    				} catch (IOException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}     
	    	        } 
	    	    });
	    	    tc4.start();
	
	    	    Thread tc5 = new Thread(new Runnable() {           
	    	        public void run() { 
	    	            //do stuff here
	    	        	
	    				try {
						System.out.println("Inside tc5");
	    					InputStream isc5 = client5.getInputStream();		
	    		            InputStreamReader isrc5 = new InputStreamReader(isc5);
	    		            BufferedReader brc5 = new BufferedReader(isrc5);
	    		            OutputStream osc5 = client5.getOutputStream(); 
	    		            OutputStreamWriter oswc5 = new OutputStreamWriter(osc5); 
	    		            BufferedWriter bwc5 = new BufferedWriter(oswc5);
	    		            Date d = new Date();
	    		            String str = "7-"+d.getTime();
					System.out.println(str);
	    		            bwc5.write(str+"\n");
	    		            bwc5.flush();
				    String string;
					//Thread.sleep(10000);
				    if((string=brc5.readLine())==null){
					int result =Integer.parseInt(string);
	    		            if(result == 1){
	    		            	c5=true;
	    		            }
				     }
	    		            
	    		            
	    		   			} catch (IOException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}     
	    	        } 
	    	    });
			request++;
	    	    tc5.start();  
			tc3.join();
			tc4.join();
			tc5.join();  
			//tc3.stop();
			//tc4.stop();
			//tc5.stop();	
	    	try{     
    		Thread.sleep(50);
		}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     
    	
    	
    	//servers
    	
        
      //--------------------------------------------------------------------------------------------------------------------------------------------------------	
    	//logic for write operation
        if(c3 && c4 && c5)
	 {
    	    try 
    	    { 	c3=false;c4=false;c5=false;
    	        boolean doAgain = true;
    	        Map<String,Integer> ip = new HashMap<String,Integer>();
    	        ip.put("10.176.66.51", 25000);
    	        ip.put("10.176.66.52", 26000);
    	        ip.put("10.176.66.53", 27000);
    	        String[] array = {"10.176.66.51","10.176.66.52","10.176.66.53"};
    		//while(doAgain)
    		{
    	        String randomIp = (array[new Random().nextInt(array.length)]); //randomly select the server
    	        int port = ip.get(randomIp); 
    			if(port == 25000)
    			{
    				System.out.println("Server 1 ::");
    			}else if(port == 26000)
    			{
    				System.out.println("Server 2 ::");
    			}else if(port == 27000)
    			{
    				System.out.println("Server 3 ::");
    			}
    	        socket = new Socket(randomIp, port);  
    	        Scanner sc = new Scanner(System.in); 
    	        OutputStream os = socket.getOutputStream(); 
    	        OutputStreamWriter osw = new OutputStreamWriter(os); 
    	        BufferedWriter bw = new BufferedWriter(osw);
    	        InputStream is = socket.getInputStream(); 
    	        InputStreamReader isr = new InputStreamReader(is); 
    	        BufferedReader br = new BufferedReader(isr); 
    	        String successMessage;
    	        bw.write(port);
    	        bw.flush(); 
    	        seq_num++;
    	        String cInfo = "<7, "+seq_num+" ,Client7> ";
    	        //bw.write(cInfo);
    	        //bw.flush(); 
    		Thread.sleep(10000);	
    	       //code to write a file at server
    	        System.out.println("Enter the data to be written: ");
    	        String wData; 
    	        //wData = sc.nextLine();
    	        bw.write(cInfo+"\n");//bw.write(cInfo+wData+"\n");
    	        bw.flush();
    	        successMessage = br.readLine(); 
    	        System.out.println(successMessage); 
    	        bw.close();
    	        br.close();
    	     }  
    	    } 
    	    catch (Exception exception) 
    	    { 
    	        exception.printStackTrace(); 
    	    } 
    	    finally 
    	    { 
    	        //Closing the socket 
    	        try 
    	        { 
    	            socket.close(); 
    	        } 
    	        catch(Exception e) 
    	        { 
    	            e.printStackTrace(); 
    	        } 
    	    } 
        }
}

    }
	runServer = false;
	String st ="request= "+request +" reply= "+ reply + "release= "+release +" enquire= " +enquire +" failed= "+failed;
	File log = new File("./c7.txt");
        FileWriter fileWriter = new FileWriter(log,true);
	fileWriter.write(st+"\n"); 
                fileWriter.flush();
    }
}
