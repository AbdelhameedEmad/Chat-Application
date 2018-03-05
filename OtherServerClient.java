import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class OtherServerClient {
	static String sentence;
	static String modifiedSentence;
	static Socket clientSocket ;
	static String m ;
	static String x ;
	static String y ;
	
	public static void main(String argv[]) throws Exception
	    {		
	        BufferedReader inFromUser =
	                new BufferedReader(new InputStreamReader(System.in));
	        

	        while(true){
	        	try {
	        	m = inFromUser.readLine();
	        	x = m.substring(0, 4);
	        	y = m.substring(5, m.length()-1);
	        	
	        		if(x.equals("join")){
	                	
	        			clientSocket = new Socket("localhost", 9996);

	        	        DataOutputStream  outToServer = 
	        	        		new DataOutputStream(clientSocket.getOutputStream());

	        	        BufferedReader inFromServer = new BufferedReader
	        	        		(new InputStreamReader(clientSocket.getInputStream()));
	        	        
	        			outToServer.writeBytes(y + "\n");
	        	        
	        	        modifiedSentence = inFromServer.readLine();
	        	        
	        	        System.out.println(modifiedSentence);
	        	        
	        	        if(modifiedSentence.equals("Sorry this username is used"))
	        			continue;
	        	        else
	        	        	break;
	                }
				} catch (Exception e) {}
	        }
	        	
	        DataOutputStream  outToServer = 
	        		new DataOutputStream(clientSocket.getOutputStream());

	        BufferedReader inFromServer = new BufferedReader
	        		(new InputStreamReader(clientSocket.getInputStream()));


	        
	        while(true)

	        {
	        	if(inFromUser.ready()){
		        sentence = inFromUser.readLine();
		        
		        outToServer.writeBytes(sentence+"\n");
		        
		        if(sentence.equals("Quit"))
		        {
			        System.out.println("connection is closed");
			        clientSocket.close();
			        break;
		        }
	        }

	        	if(inFromServer.ready()){
	        		
		        modifiedSentence = inFromServer.readLine();
		            
		        System.out.println(modifiedSentence);
	        }
	        
	    } 
	    }
}