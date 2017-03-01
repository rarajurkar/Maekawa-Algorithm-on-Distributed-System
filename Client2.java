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

public class Client2 {

	private static Socket socket; 
    private static Socket client3, client4, client7;
    static boolean c3=false, c4=false, c7=false;
    static int seq_num=-1;
    static ServerSocket c1Socket, c5Socket;
    static Socket socket1, socket5;
    static boolean k1=false, k2=false, k3=false;
    static int request=0,reply=0, release=0, enquire=0, failed=0; 
static boolean runServer = true;
    
    public static void main(String args[]) throws IOException, InterruptedException{
    	List<Thread> tList = new ArrayList<Thread>(); 
    	Thread t1 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				c1Socket = new ServerSocket(28001);
    				c1Socket.setSoTimeout(100000);
    				System.out.println("server scokets c1socket 28001");
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			k1=true;
	System.out.println(k1);
    		}
    		
    	});
    	tList.add(t1);
    	t1.start();
    	Thread t2 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				c5Socket = new ServerSocket(28011);
    				c5Socket.setSoTimeout(100000);
    				System.out.println("server sockets c5socket 28011");
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
    				System.out.println("opening client sockets 28003, 28004, 28005");
    				client3 = new Socket("10.176.66.56",28003);
    				client3.setSoTimeout(100000);
    				client4 = new Socket("10.176.66.57",28004);
    				client4.setSoTimeout(100000);
    				client7 = new Socket("10.176.66.60",28005);
    				client7.setSoTimeout(100000);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		
    			k3=true;
			System.out.println(k3);
    		}
    		
    	});
	tList.add(t3);
    	t3.start();
       	for(Thread temp : tList){
    		temp.join();
    	}
    
    	//ServerSocket 
    	
    	List<Long> queue = new ArrayList<Long>();
    	List<Integer> queueId = new ArrayList<Integer>();
    	Map<Integer, Long> queueMap = new HashMap<Integer, Long>();
    	
    	//Maekawa algorithm
    	
    	int no=50;
	System.out.println("above maekawa logic");
	//Thread.sleep(90000);
    	// this will send request to other client to access the critical section
    	if(k1 && k2 && k3){

			//while(true)
 {
				Thread ts1 = new Thread(new Runnable() {           
            			public void run() { 
					int id=0;
                //do stuff here

            	try {
			while(runServer){
				System.out.println("c2 is waiting for c1");
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
    	            } }
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
        Thread ts5 = new Thread(new Runnable() {           
            public void run() { 
int id=0;
                //do stuff here
            	try {
			while(runServer){
				System.out.println("c2 is waiting for c5");
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
    	            } }

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
        //ts5.join(); ts1.join();

				
			}
	    		while((no--)>0){
	    			Thread tc3 = new Thread(new Runnable() {           
	    	        public void run() { 
	    	            //do stuff here
	    	        	
	    				try {
	    					System.out.println("inside client3");
	    					InputStream isc3 = client3.getInputStream();		
	    		            InputStreamReader isrc3 = new InputStreamReader(isc3);
	    		            BufferedReader brc3 = new BufferedReader(isrc3);
	    		            OutputStream osc3 = client3.getOutputStream(); 
	    		            OutputStreamWriter oswc3 = new OutputStreamWriter(osc3); 
	    		            BufferedWriter bwc3 = new BufferedWriter(oswc3);
	    		            Date d = new Date();
	    		            String str = "2-"+d.getTime();
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
	    		            InputStream isc4 = client4.getInputStream();
	    		            InputStreamReader isrc4 = new InputStreamReader(isc4);
	    		            BufferedReader brc4 = new BufferedReader(isrc4);
	    		            OutputStream osc4 = client4.getOutputStream(); 
	    		            OutputStreamWriter oswc4 = new OutputStreamWriter(osc4); 
	    		            BufferedWriter bwc4 = new BufferedWriter(oswc4);
	    		            Date d = new Date();
	    		            String str = "2-"+d.getTime();
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
	    		            String str = "2-"+d.getTime();
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
	    				} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//} 
	    	        } 
	    	    });
	    	    tc7.start();
		tc3.join(); tc7.join(); tc4.join();
		request++;
		try{     
    		Thread.sleep(50);
		}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     
  
		//tc3.stop();
		//tc7.stop();
	    	
    	
    	
    	//servers to receive the requests from other clients-- queue
    	
      //--------------------------------------------------------------------------------------------------------------------------------------------------------	
    	//logic for write operation
        if(c3 && c4 && c7)
	{
    	    try 
    	    { 	c3=false;c4=false;c7=false;
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
    	        String cInfo = "<2, "+seq_num+" ,Client2> ";
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
		File log = new File("./c2.txt");
                FileWriter fileWriter = new FileWriter(log,true);
		fileWriter.write(st+"\n"); 
                fileWriter.flush();
}}
