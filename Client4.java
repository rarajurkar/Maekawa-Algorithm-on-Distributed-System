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

public class Client4 {
	private static Socket socket; 
    private static Socket client5, client6, client7;
    static boolean c5=false, c6=false, c7=false;
    static int seq_num=-1;
    static ServerSocket c1Socket, c2Socket, c7Socket;
    static Socket socket1, socket2, socket7;
    static boolean k1=false, k2=false, k3=false, k4=false;
    static int request=0,reply=0, release=0, enquire=0, failed=0; 
static boolean runServer = true;
    
    public static void main(String args[]) throws UnknownHostException, InterruptedException, IOException{
    	   	List<Thread> tList = new ArrayList<Thread>();
    	   	Thread t1 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				c1Socket = new ServerSocket(28002);
				c1Socket.setSoTimeout(100000);
    				System.out.println("waiiting 28002");
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			k1 = true;
			System.out.println(k1);
    		}
    		
    	});
    	t1.start();
    	tList.add(t1);
    	Thread t2 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				c2Socket = new ServerSocket(28004);
    				c2Socket.setSoTimeout(100000);
    				System.out.println("waiting 28004");
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
    				c7Socket = new ServerSocket(28017);
    				c7Socket.setSoTimeout(100000);
    				System.out.println("waiting 28017");
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
    				Thread.sleep(60000);
    				System.out.println("client sockets 28008, 28009, 28010");
    				client5 = new Socket("10.176.66.58",28008);
    				client5.setSoTimeout(100000);
    				client6 = new Socket("10.176.66.59",28009);
    				client6.setSoTimeout(100000);
    				client7 = new Socket("10.176.66.60",28010);
    				client7.setSoTimeout(100000);
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
    	}
    	
	
    	
    	List<Long> queue = new ArrayList<Long>();
    	List<Integer> queueId = new ArrayList<Integer>();
    	Map<Integer, Long> queueMap = new HashMap<Integer, Long>();
    	
    	int no=50;
    	// this will send request to other client to access the critical section
	System.out.println("above maekawa logic");

	//Thread.sleep(90000);
    	if(k1 && k2 && k3 && k4){
		//while(true)
{
			Thread ts1 = new Thread(new Runnable() {           
            public void run() { 
		int id=0;
                //do stuff here
            	try {
			while(runServer){
				socket1 = c1Socket.accept();
            		InputStream issc1 = socket1.getInputStream();
    	            InputStreamReader isrsc1 = new InputStreamReader(issc1);
    	            BufferedReader brsc1 = new BufferedReader(isrsc1);
    	            OutputStream ossc1 = socket1.getOutputStream(); 
    	            OutputStreamWriter oswsc1 = new OutputStreamWriter(ossc1); 
    	            BufferedWriter bwsc1 = new BufferedWriter(oswsc1);
			//Thread.sleep(10000);
			String str;
		            if((str = brsc1.readLine())!=null){
					String[] s= new String[2];
		           		s = str.split("-");
				    id=Integer.parseInt(s[0]);
				    long timestamp = Long.parseLong(s[1]);
				    queue.add(timestamp);
				    queueId.add(id);
				    queueMap.put(id, timestamp);
				
		    	   
    	            if(id==queueId.get(0)){
    	            	bwsc1.write("1"+"\n");
    		            bwsc1.flush();
			    reply++;
    	            }
    	            else{
    	            	bwsc1.write("0"+"\n");
    		            bwsc1.flush();
			   enquire++;
    	            }}
			}
            		
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}//catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
            } 
        });
        ts1.start();
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
    			}//catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
            } 
        });
        ts2.start();
        
        Thread ts7 = new Thread(new Runnable() {           
            public void run() { 
		int id=0;
                //do stuff here
            	try {
			while(runServer){
				socket7 = c7Socket.accept();
            		InputStream issc7 = socket7.getInputStream();
    	            InputStreamReader isrsc7 = new InputStreamReader(issc7);
    	            BufferedReader brsc7 = new BufferedReader(isrsc7);
    	            OutputStream ossc7 = socket7.getOutputStream(); 
    	            OutputStreamWriter oswsc7 = new OutputStreamWriter(ossc7); 
    	            BufferedWriter bwsc7 = new BufferedWriter(oswsc7);
		    //Thread.sleep(10000);
			String str;
		            if((str = brsc7.readLine())!=null){
					String[] s= new String[2];
		           		s = str.split("-");
				    id=Integer.parseInt(s[0]);
				    long timestamp = Long.parseLong(s[1]);
				    queue.add(timestamp);
				    queueId.add(id);
				    queueMap.put(id, timestamp);
				
		    	   
    	            if(id==queueId.get(0)){
    	            	bwsc7.write("1"+"\n");
    		            bwsc7.flush();
			reply++;
    	            }
    	            else{
    	            	bwsc7.write("0"+"\n");
    		            bwsc7.flush();
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
        ts7.start();
	


		}
		//num=50;
	    	while((no--)>0){
	    		Thread tc5 = new Thread(new Runnable() {           
	    	        public void run() { 
	    	            //do stuff here
	    	        	
	    				try {
	    					InputStream isc5 = client5.getInputStream();		
	    		            InputStreamReader isrc5 = new InputStreamReader(isc5);
	    		            BufferedReader brc5 = new BufferedReader(isrc5);
	    		            OutputStream osc5 = client5.getOutputStream(); 
	    		            OutputStreamWriter oswc5 = new OutputStreamWriter(osc5); 
	    		            BufferedWriter bwc5 = new BufferedWriter(oswc5);
	    		            Date d = new Date();
	    		            String str = "4-"+d.getTime();
	    		            bwc5.write(str+"\n");
	    		            bwc5.flush();
	    		            String string;
					//Thread.sleep(10000);
				    if((string=brc5.readLine())!=null){
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
	    	    tc5.start();
	    	    Thread tc6 = new Thread(new Runnable() {           
	    	        public void run() { 
	    	            //do stuff here
	    	        	
	    				try {
	    		            InputStream isc6 = client6.getInputStream();
	    		            InputStreamReader isrc6 = new InputStreamReader(isc6);
	    		            BufferedReader brc6 = new BufferedReader(isrc6);
	    		            OutputStream osc6 = client6.getOutputStream(); 
	    		            OutputStreamWriter oswc6 = new OutputStreamWriter(osc6); 
	    		            BufferedWriter bwc6 = new BufferedWriter(oswc6);
	    		            Date d = new Date();
	    		            String str = "4-"+d.getTime();
	    		            bwc6.write(str+"\n");
	    		            bwc6.flush();
	    		            String string;
				//Thread.sleep(10000);	
				    if((string=brc6.readLine())!=null){
					int result =Integer.parseInt(string);
	    		            if(result == 1){
	    		            	c6=true;
	    		            }
				     }
					
	    				} catch (IOException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}//catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
	    	        } 
	    	    });
	    	    tc6.start();
	    	    Thread tc7 = new Thread(new Runnable() {           
	    	        public void run() { 
	    	            //do stuff here
	    	        	
	    				try {
	    		            InputStream isc7 = client7.getInputStream();
	    		            InputStreamReader isrc7 = new InputStreamReader(isc7);
	    		            BufferedReader brc7 = new BufferedReader(isrc7);
	    		            OutputStream osc7 = client7.getOutputStream(); 
	    		            OutputStreamWriter oswc7 = new OutputStreamWriter(osc7); 
	    		            BufferedWriter bwc7 = new BufferedWriter(oswc7);
	    		            Date d = new Date();
	    		            String str = "4-"+d.getTime();
	    		            bwc7.write(str+"\n");
	    		            bwc7.flush();
	    		            String string;
//Thread.sleep(10000);
				    if((string=brc7.readLine())!=null){
int result =Integer.parseInt(string);
	    		            if(result == 1){
	    		            	c7=true;
	    		            }
					
				     }
					
	    				} catch (IOException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}     //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
	    	        } 
	    	    });
	    	    tc7.start();
		tc5.join();tc6.join();tc7.join();	
			//tc5.stop();
			//tc6.stop();
			//tc7.stop();
		request++;
	    	try{     
    		Thread.sleep(50);
		}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     
  
    	
    	
    	//server 
    	
      //--------------------------------------------------------------------------------------------------------------------------------------------------------	
    	//logic for write operation
       if(c5 && c6 && c7)
	{
    	    try 
    	    { 	c5=false;c6=false;c7=false;
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
    	        String cInfo = "<4, "+seq_num+" ,Client4> ";
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
		File log = new File("./c4.txt");
                FileWriter fileWriter = new FileWriter(log,true);
		fileWriter.write(st+"\n"); 
                fileWriter.flush();
}}
