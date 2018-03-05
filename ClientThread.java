import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientThread extends Thread{
	
	Socket connectionSocket ;
	
	AnalyzerThread Analyzer ;
	
	String clientSentenceToSend;
	 
	BufferedReader inFromClient;
	 
	DataOutputStream  outToClient;	
	
		public ClientThread(Socket connectionSocket) throws IOException
		{
			this.connectionSocket = connectionSocket;
						
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		}
		

		public void run(){
			
			      while(true) {
						try {
							
			 	         if(connectionSocket.isClosed())
			 	        	 break;
			 	         
			 	         if(inFromClient.equals(null))
			 	        	 break;
			 	         
							clientSentenceToSend = inFromClient.readLine();
			 	         
							Analyzer.Analyze(clientSentenceToSend);
			 	         
			      } 
			      
		      catch (Exception e) {
		    	  System.out.println("exception");
		      
		    	  if(connectionSocket.isClosed() != true)
		    	  this.start();
		    	  else
		    		  break;
		    	  
		      }
		}
			      }
		
		public void setAnalyzer(AnalyzerThread analyzer) {
			Analyzer = analyzer;
		}

		public void Send(String clientSentenceToRecieve) throws IOException{

	         outToClient.writeBytes(clientSentenceToRecieve+"\n");

		}
	}