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

public class Client6 {
    private static Socket socket; 
    private static Socket client1, client3;
    static boolean c1=false, c3=false;
    static int seq_num=-1;
    static ServerSocket c4Socket, c5Socket;
    static Socket socket4, socket5;
    static boolean k1=false, k2=false, k3=false;
    static int request=0,reply=0, release=0, enquire=0, failed=0;
	static boolean runServer = true; 
    
    public static void main(String args[]) throws UnknownHostException, IOException, InterruptedException{
    	List<Thread> tList = new ArrayList<Thread>();
    	Thread t1 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				c4Socket = new ServerSocket(28009);
    				c4Socket.setSoTimeout(100000);
    				System.out.println("c4socket 28009");
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
    				c5Socket = new ServerSocket(28013);
    				c5Socket.setSoTimeout(100000);
    				System.out.println("c5socket 28013");
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
    				Thread.sleep(50000);
    				System.out.println("client sockets 28014 28015");
    				client1 = new Socket("10.176.66.54",28014);
    				client1.setSoTimeout(100000);
    		    	client3 = new Socket("10.176.66.56",28015);
    				client3.setSoTimeout(100000);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			k3 = true;
			System.out.println(k3);
    		}
    		
    	});
    	t3.start();
    	tList.add(t3);
    	
    	//ServerSocket 
    	for(Thread temp : tList){
    		temp.join();
    	}
    	//ServerSocket c5Socket = new ServerSocket(28013);
	   	
    	List<Long> queue = new ArrayList<Long>();
    	List<Integer> queueId = new ArrayList<Integer>();
    	Map<Integer, Long> queueMap = new HashMap<Integer, Long>();
    	
    	int no=50;
	//Thread.sleep(90000);
	System.out.println("above maekawa logic");

    	// this will send request to other client to access the critical section
    	if(k1 && k2 && k3){
		//while(true)
			{
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
    	            //Thread.sleep(10000);
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
			request++;
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
        Thread ts5 = new Thread(new Runnable() {           
            public void run() { 
		int id=0;
                //do stuff here
            	try {
			while(runServer){
			socket5 = c5Socket.accept();
            		InputStream issc5 = socket5.getInputStream();
    	            InputStreamReader isrsc5 = new InputStreamReader(issc5);
    	            BufferedReader brsc5 = new BufferedReader(isrsc5);
    	            OutputStream ossc5 = socket5.getOutputStream(); 
    	            OutputStreamWriter oswsc5 = new OutputStreamWriter(ossc5); 
    	            BufferedWriter bwsc5 = new BufferedWriter(oswsc5);
			String str;
    	            //Thread.sleep(10000);
		            if((str = brsc5.readLine())!=null){
					String[] s= new String[2];
		           		s = str.split("-");
				    id=Integer.parseInt(s[0]);
				    long timestamp = Long.parseLong(s[1]);
				    queue.add(timestamp);
				    queueId.add(id);
				    queueMap.put(id, timestamp);
				
		    	   
    	            if(id==queueId.get(0)){
    	            	bwsc5.write("1"+"\n");
    		            bwsc5.flush();
			reply++;
    	            }
    	            else{
    	            	bwsc5.write("0"+"\n");
    		            bwsc5.flush();
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

        ts5.start();
       // ts4.join();
	//ts5.join();

		}
		//int num=50;
	    	while((no--)>0){
	    		Thread tc1 = new Thread(new Runnable() {           
	    	        public void run() { 
	    	            //do stuff here
	    	        	
	    				try {
	    					InputStream isc1 = client1.getInputStream();		
	    		            InputStreamReader isrc1 = new InputStreamReader(isc1);
	    		            BufferedReader brc1 = new BufferedReader(isrc1);
	    		            OutputStream osc1 = client1.getOutputStream(); 
	    		            OutputStreamWriter oswc1 = new OutputStreamWriter(osc1); 
	    		            BufferedWriter bwc1 = new BufferedWriter(oswc1);
	    		            Date d = new Date();
	    		            String str = "6-"+d.getTime();
	    		            bwc1.write(str+"\n");
	    		            bwc1.flush();
	    		            String string;
//Thread.sleep(10000);
				    if((string=brc1.readLine())!=null){
					int result =Integer.parseInt(string);
	    		            if(result == 1){
	    		            	c1=true;
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
	    	    tc1.start();
	    	    Thread tc3 = new Thread(new Runnable() {           
	    	        public void run() { 
	    	            //do stuff here
	    	        	
	    				try {
	    					InputStream isc3 = client3.getInputStream();		
	    		            InputStreamReader isrc3 = new InputStreamReader(isc3);
	    		            BufferedReader brc3 = new BufferedReader(isrc3);
	    		            OutputStream osc3 = client3.getOutputStream(); 
	    		            OutputStreamWriter oswc3 = new OutputStreamWriter(osc3); 
	    		            BufferedWriter bwc3 = new BufferedWriter(oswc3);
	    		            Date d = new Date();
	    		            String str = "6-"+d.getTime();
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
			request++;
			tc1.join();
			tc3.join();
			//tc1.stop();
			//tc3.stop();
			try{     
    		Thread.sleep(50);
		}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     
  
	    	
    	
    	
    	//servers
    	
      //--------------------------------------------------------------------------------------------------------------------------------------------------------	
    	//logic for write operation
       if(c1 && c3)
{
    	    try 
    	    { 	c1=false;c3= false;
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
    			//socket = new Socket("127.0.0.0",port);
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
    	        String cInfo = "<6, "+seq_num+" ,Client6> ";
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
		File log = new File("./c6.txt");
                FileWriter fileWriter = new FileWriter(log,true);
		fileWriter.write(st+"\n"); 
                fileWriter.flush();
}
}
