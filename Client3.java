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

public class Client3 {

	private static Socket socket; 
    private static Socket client1, client7;
    static boolean c1=false, c7=false;
    static int seq_num=-1;
    static ServerSocket c2Socket, c5Socket, c6Socket, c7Socket;
    static Socket socket2, socket5, socket6, socket7;
    static boolean k1=false, k2=false, k3=false, k4=false, k5=false;
    static int request=0,reply=0, release=0, enquire=0, failed=0; 
	static boolean runServer = true;
    
    public static void main(String args[]) throws UnknownHostException, IOException, InterruptedException{
    	List<Thread> tList = new ArrayList<Thread>(); 
    	Thread t1 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				c2Socket = new ServerSocket(28003);
				c2Socket.setSoTimeout(100000);
    				System.out.println("waiitng 28003");
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
    				c5Socket = new ServerSocket(28012);
				c5Socket.setSoTimeout(100000);
    				System.out.println("c5socket 28012");
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
    				c6Socket = new ServerSocket(28015);
    				c6Socket.setSoTimeout(100000);
    				System.out.println("c6socket 28015");
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
    				c7Socket = new ServerSocket(28016);
    				c7Socket.setSoTimeout(100000);
    				System.out.println("c7socket 28016");
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			k4=true;
			System.out.println(k4);
    		}
    		
    	});
    	t4.start();
    	tList.add(t4);
    	Thread t5 = new Thread(new Runnable() {
    		public void run(){
    			try {
    				Thread.sleep(50000);
    				System.out.println("creating client socktes 28006, 28007");
    				client1 = new Socket("10.176.66.54",28006);
    				client1.setSoTimeout(100000);
       		    		client7 = new Socket("10.176.66.60",28007);
    				client7.setSoTimeout(100000);
    	    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			k5=true;
			System.out.println(k5);
    		}
    		
    	});
    	t5.start();
    	tList.add(t5);
    	for(Thread temp : tList){
    		temp.join();
    	}
    	
    	    	
    	List<Long> queue = new ArrayList<Long>();
    	List<Integer> queueId = new ArrayList<Integer>();
    	Map<Integer, Long> queueMap = new HashMap<Integer, Long>();
    	
    	
    	int no=50;
	System.out.println("above maekawa logic");

    	// this will send request to other client to access the critical section
	//Thread.sleep(90000);
    	if(k1 && k2 && k3 && k4 && k5){
		
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
			//Thread.sleep(10000);
			String str;
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
        ts2.start();

    	Thread ts6 = new Thread(new Runnable() {           
            public void run() { 
		int id=0;
                //do stuff here
            	try {
			while(runServer){
			socket6 = c6Socket.accept();
            		InputStream issc6 = socket6.getInputStream();
    	            InputStreamReader isrsc6 = new InputStreamReader(issc6);
    	            BufferedReader brsc6 = new BufferedReader(isrsc6);
    	            OutputStream ossc6 = socket6.getOutputStream(); 
    	            OutputStreamWriter oswsc6 = new OutputStreamWriter(ossc6); 
    	            BufferedWriter bwsc6 = new BufferedWriter(oswsc6);
			//Thread.sleep(10000);
			String str;
		            if((str = brsc6.readLine())!=null){
					String[] s= new String[2];
		           		s = str.split("-");
				    id=Integer.parseInt(s[0]);
				    long timestamp = Long.parseLong(s[1]);
				    queue.add(timestamp);
				    queueId.add(id);
				    queueMap.put(id, timestamp);
				
		    	   
    	            if(id==queueId.get(0)){
    	            	bwsc6.write("1"+"\n");
    		            bwsc6.flush();
				reply++;
    	            }
    	            else{
    	            	bwsc6.write("0"+"\n");
    		            bwsc6.flush();
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
        ts6.start();
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
			//Thread.sleep(10000);
			String str;
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
        ts7.start();
	

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
	    		            String str = "3-"+d.getTime();
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
	    		            String str = "3-"+d.getTime();
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
		tc1.join(); tc7.join();
		//tc1.stop();
		//tc7.stop();
		try{     
    		Thread.sleep(50);
		}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     
  
		    	
    	
    	
    	//server 
    	
      //--------------------------------------------------------------------------------------------------------------------------------------------------------	
    	//logic for write operation
        if(c1 && c7)
	{
    	    try 
    	    { 	c1=false; c7=false;
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
    	        String cInfo = "<3, "+seq_num+" ,Client3> ";
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
		File log = new File("./c3.txt");
                FileWriter fileWriter = new FileWriter(log,true);
		fileWriter.write(st+"\n"); 
                fileWriter.flush();

}
}
