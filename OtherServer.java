import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class OtherServer {
		
		static int i = 0 ;  
		
		static int id = 0 ;
	    
	    static ArrayList<ClientThread> Clients = new ArrayList<ClientThread>() ;
	    
	    static ArrayList<AnalyzerThread> Analyzers = new ArrayList<AnalyzerThread>() ;
	    
	    static ArrayList<String> names = new ArrayList<String>();
	    
	    static BufferedReader inFromClient;
	    
	    static ServerThread otherServer;
	    
	    static Socket SocketToTalkToOtherServer ;
	    
		public static void main(String argv[]) throws Exception
		 {
		    @SuppressWarnings("resource")
			ServerSocket welcomeSocket = new ServerSocket(9996);
		      
			 SocketToTalkToOtherServer = new Socket("localhost", 9995);
						
			DataOutputStream outToMainServer = new DataOutputStream(SocketToTalkToOtherServer.getOutputStream());
		      
		      outToMainServer.writeBytes("I am a Server" + "\n");
			
		      while(true) {
		    	  
			      Socket connectionSocket = welcomeSocket.accept();
			      
			      inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			      
			      String FirstWords = inFromClient.readLine();

			      if(FirstWords.equals("I am a Server")){
			    	  
			    	  AnalyzerThread analyzer = new AnalyzerThread(Clients, names);
			    	  
			    	  otherServer = new ServerThread(connectionSocket);
			    	  
			    	  otherServer.setSender(analyzer);
			    	  
			    	  otherServer.start();
			    	  
			    	  Analyzers.add(analyzer);

			      }


			      else{
			    	  
				      outToMainServer.writeBytes("Check "+ FirstWords + "\n");
			    	  
			    	  BufferedReader inFromOtherServer = new BufferedReader(new InputStreamReader(SocketToTalkToOtherServer.getInputStream())); 
			    	  
			    	  String reply = inFromOtherServer.readLine();
			    	  
			    	  if(names.contains(FirstWords) || reply.equals("Exists") ){
					      
			    		  DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				      
			    		  outToClient.writeBytes("Sorry this username is used" + "\n");
			    	  
			    	  }
			    	  else{
				      AnalyzerThread analyzer = new AnalyzerThread(Clients, names);
				      
				      ClientThread ct = new ClientThread(connectionSocket);
				     
				      String name = FirstWords;
				      
				      names.add(name);
				      
				      ct.start();

				      ct.setAnalyzer(analyzer);
				      
				      analyzer.setMyClientName(name);
				      
				      analyzer.setMyClient(ct);
				      
				      if(SocketToTalkToOtherServer != null)
				      analyzer.setOtherServer(SocketToTalkToOtherServer);
				      
				      Analyzers.add(analyzer);
				      
				      Clients.add(ct) ;
				      
				      for (int i = 0; i < Analyzers.size(); i++) {
						
				    	  Analyzers.get(i).setClients(Clients);
				    	  
				    	  Analyzers.get(i).setNames(names);
				    	  
					}

				      DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				      
				      outToClient.writeBytes("From Server: Welcome ^_^ "+ names.get(id) + "\n");
				     
				      id++ ;
				    
			    	} }
		    }
	}
}