import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread{
	
	Socket ServerTalkingTo ;
			 
	AnalyzerThread Sender;
	
	BufferedReader inFromServerTalkingTo;
	 
	DataOutputStream  outToServerTalkingTo;
	
	String clientSentenceToSend;


		public ServerThread(Socket connectionSocket) throws IOException
		{
			ServerTalkingTo = connectionSocket;
			
			inFromServerTalkingTo = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			outToServerTalkingTo = new DataOutputStream(connectionSocket.getOutputStream());
		
		}
		
		public void run(){
			
		      while(true) {
					try {
		 	         
						
						if(ServerTalkingTo.isClosed())
		 	        	 break;
		 	         
						if(ServerTalkingTo.equals(null))
		 	        	 break;
						
		 	         clientSentenceToSend = inFromServerTalkingTo.readLine();
					
		 	         if(clientSentenceToSend.substring(0,5).equals("Check"))
		 	         {
		 	        	 String name = clientSentenceToSend.substring(6);
		 	        	 
		 	        	 if(Sender.names.contains(name))
		 	        		 outToServerTalkingTo.writeBytes("Exists"+"\n");
		 	        	 else
		 	        		outToServerTalkingTo.writeBytes("Doesn't Exist"+"\n");
		 	        	 
		 	         }
		 	         else if(clientSentenceToSend.equals("GetMemberList")){
		 	        	
		 	        	 if(Sender.names.isEmpty() != true){
		 	        	 
		 	        		 String members = Sender.names.get(0);
		 				
		 				for (int i = 1; i < Sender.names.size(); i++) {
		 					
		 					members = members + " " + Sender.names.get(i) ;
		 				
		 				}
		 				
		 	        	 outToServerTalkingTo.writeBytes(" " + members + "\n");
		 	        	}
		 	        	 else
		 	        	outToServerTalkingTo.writeBytes(" " + "\n");
		 	         }
		 	         else
		 	         Sender.Analyze(clientSentenceToSend);
		 	         
		      } 
		      
	      catch (Exception e) {System.out.println("exception");
	      break;
	      }
	}
		      }

		public void setSender(AnalyzerThread sender) {
		
			Sender = sender;
		
		}
		
}