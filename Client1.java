import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.OutputStream; 
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner; 
import java.io.*;
 
public class Client1 { 
     
    public static Socket socket; 
    public static Socket client2, client4;
    static boolean c2=false;
	static boolean c4=false;
	static int seq_num=-1;
	static ServerSocket c3Socket, c6Socket;
	static Socket cSocket3, cSocket6;
	static boolean k1=false, k2=false, k3=false; 
	static int request=0,reply=0, release=0, enquire=0, failed=0; 
	static boolean runServer = true;
 
public static void main(String args[]) throws IOException, InterruptedException 
{ 
	
	//ServerSocket serverSocket = new ServerSocket(28000);
	List<Thread> tList = new ArrayList<Thread>(); 
	Thread t1 = new Thread(new Runnable() {
		public void run(){
			try {
				c3Socket = new ServerSocket(28006);
				c3Socket.setSoTimeout(100000);
				System.out.println("c1 c3 socket 28006");
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
				c6Socket = new ServerSocket(28014);
				c6Socket.setSoTimeout(100000);
				System.out.println("server scoket 28014");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			k2=true;
			System.out.println(k2);
		}
		
	});
	tList.add(t2);
	t2.start();
	
	Thread t3 = new Thread(new Runnable() {
		public void run(){
			try {
				Thread.sleep(50000);
				System.out.println("creating client sockets 28001 28002");
				client2 = new Socket("10.176.66.55",28001);
				client2.setSoTimeout(100000);
				client4 = new Socket("10.176.66.57",28002);
				client4.setSoTimeout(100000);

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
	t3.start();
	tList.add(t3);
	
	
	for(Thread temp : tList){
		temp.join();
	}
	//ServerSocket c6Socket = new ServerSocket(28014);
	
	
	List<Long> queue = new ArrayList<Long>();
	List<Integer> queueId = new ArrayList<Integer>();
	Map<Integer, Long> queueMap = new HashMap<Integer, Long>();
	
	//Maekawa algorithm
	int no=50;
	// this will send request to other client to access the critical section
	//Thread.sleep(90000);
	System.out.println("above maekawa logic");
	if(k1 && k2 && k3){
		//while(true)
{
			Thread ts3 = new Thread(new Runnable() {           
	        	public void run() { 
			int id=0;
	        	    //do stuff here
	        	try {
				while(runServer){
					System.out.println("c1 is waiting for c3");
	        		cSocket3 = c3Socket.accept();
	        		InputStream issc3 = cSocket3.getInputStream();
		            InputStreamReader isrsc3 = new InputStreamReader(issc3);
		            BufferedReader brsc3 = new BufferedReader(isrsc3);
		            OutputStream ossc3 = cSocket3.getOutputStream(); 
		            OutputStreamWriter oswsc3 = new OutputStreamWriter(ossc3); 
		            BufferedWriter bwsc3 = new BufferedWriter(oswsc3);
			    String str;
		            
				//Thread.sleep(1000);
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
		            } else{
				bwsc3.write("0"+"\n");
			            bwsc3.flush();
				enquire++;
			}
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
	    ts3.start();
	    Thread ts6 = new Thread(new Runnable() {           
	        public void run() { 
			int id=0;
	            //do stuff here
	        	try {
				while(runServer){
					cSocket6 = c6Socket.accept();
	        		System.out.println("c1 is sending request to c6");
	        		InputStream issc6 = cSocket6.getInputStream();
		            InputStreamReader isrsc6 = new InputStreamReader(issc6);
		            BufferedReader brsc6 = new BufferedReader(isrsc6);
		            OutputStream ossc6 = cSocket6.getOutputStream(); 
		            OutputStreamWriter oswsc6 = new OutputStreamWriter(ossc6); 
		            BufferedWriter bwsc6 = new BufferedWriter(oswsc6);
				String str;
			//Thread.sleep(1000);
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
		            } else{
				bwsc6.write("0"+"\n");
			            bwsc6.flush();
				enquire++;
			}
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
	    	ts6.start();
		}
		//int num=50; 
		while(no-->0){
			Thread tc2 = new Thread(new Runnable() {           
		        public void run() { 
		            //do stuff here
		        	
					try {
					System.out.println("c1 is sending request to c2");
	       			    InputStream isc2 = client2.getInputStream();		
			            InputStreamReader isrc2 = new InputStreamReader(isc2);
			            BufferedReader brc2 = new BufferedReader(isrc2);
			            OutputStream osc2 = client2.getOutputStream(); 
			            OutputStreamWriter oswc2 = new OutputStreamWriter(osc2); 
			            BufferedWriter bwc2 = new BufferedWriter(oswc2);
			            Date d = new Date();
			            String str = "1-"+d.getTime();
			            bwc2.write(str+"\n");
			            bwc2.flush();
			            String string;
				//Thread.sleep(10000);
				    if((string=brc2.readLine())!=null){
					int result =Integer.parseInt(string);
			            if(result == 1){
			            	c2=true;	
					System.out.println(c2);
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
		    tc2.start();
		    Thread tc4 = new Thread(new Runnable() {           
		        public void run() { 
		            //do stuff here
		        	
					try {
					
					System.out.println("c1 is sending request to c4");
			            InputStream isc4 = client4.getInputStream();
			            InputStreamReader isrc4 = new InputStreamReader(isc4);
			            BufferedReader brc4 = new BufferedReader(isrc4);
			            OutputStream osc4 = client4.getOutputStream(); 
			            OutputStreamWriter oswc4 = new OutputStreamWriter(osc4); 
			            BufferedWriter bwc4 = new BufferedWriter(oswc4);
			            Date d = new Date();
			            String str = "1-"+d.getTime();
			            bwc4.write(str+"\n");
			            bwc4.flush();
			            String string;
				//Thread.sleep(10000);
				    if((string=brc4.readLine())!=null){
					int result =Integer.parseInt(string);
			            if(result == 1){
			            	c4=true;
					System.out.println(c4);
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
		System.out.println("client 1 has requested..");
		request++;
		    tc2.join(); tc4.join();
		    //tc2.stop();
		    //tc4.stop();
		try{     
    		Thread.sleep(50);
		}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     
  
		

    
    //servers to receive the requests from other clients-- queue 
    

	    
	   
	
		
	//--------------------------------------------------------------------------------------------------------------------------------------------------------	
		//logic for write operation
	    if(c2 && c4){
		    try 
		    { 
		        c4=false;
			c2=false;
			System.out.println("Actual Write code...");
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
			System.out.println(port);
		        bw.write(port);
		        bw.flush(); 
		        seq_num++;
		        String cInfo = "<1, "+seq_num+" ,Client1> ";
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
		File log = new File("./c1.txt");
                FileWriter fileWriter = new FileWriter(log,true);
		fileWriter.write(st+"\n"); 
                fileWriter.flush();

	}
} 
