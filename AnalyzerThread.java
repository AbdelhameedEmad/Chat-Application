import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class AnalyzerThread {
	
	static Socket connectionSocket ;
	
	ArrayList<ClientThread> Clients;

	ArrayList<String> names ;
	
	String myClientName;
	
	ClientThread myClient;
	
	Socket otherServerSocket;
	
	DataOutputStream outToOtherServer;
	
	BufferedReader fromOtherServer;
	
	public AnalyzerThread(ArrayList<ClientThread> Clients, ArrayList<String> names, Socket otherServerSocket) {
		
		this.names = names ;
		
		this.Clients = Clients;
		
		this.otherServerSocket = otherServerSocket;
	}

	public AnalyzerThread(ArrayList<ClientThread> Clients, ArrayList<String> names) {
				
		this.names = names ;
		
		this.Clients = Clients;
		
	}
	
	public void Analyze(String s) throws IOException {
	
		if(s.length() >= 5 && s.contains("(")){
			
		if(s.substring(0,4).equals("Chat") || s.substring(0,4).equals("chat") || s.substring(0,5).equals(" chat")
				|| s.substring(0,5).equals(" Chat")){
			
			String M = s.substring(s.indexOf("(")+1);
			
			String[] parts = M.split(", ");
			
			String SourceName = parts[0] ;
			
			String destinationName = parts[1] ;
			
			int TTL = Integer.parseInt(parts[2]);
			
			String message = parts[3].substring(0,parts[3].indexOf(")"));
			
			if(TTL <= 0){
				
				outToOtherServer = new DataOutputStream(otherServerSocket.getOutputStream());
				
				outToOtherServer.writeBytes( "Chat (" + "Server" +", " + SourceName +", "+
			"3" +", " + "There is nobody with that name online" +")" + "\n");
				
			}
			
			else if(names.contains(destinationName))
			{
			int realdestination = names.indexOf(destinationName);
			
			Clients.get(realdestination).Send("From " + SourceName + ": " + message);
		
			}
			else{
				TTL = TTL - 1;
				
				outToOtherServer = new DataOutputStream(otherServerSocket.getOutputStream());
				
				outToOtherServer.writeBytes( "Chat (" + SourceName +", " + destinationName +", "+ TTL +", " + message +")" + "\n");
				
			}
		}
		
		else if(s.substring(0,13).equals("GetMemberList")){
						
						
			String members ="Online: " + names.get(0);
			
			for (int i = 1; i < names.size(); i++) {
				
				members = members + " " + names.get(i) ;
			
			}
						
			outToOtherServer = new DataOutputStream(otherServerSocket.getOutputStream());
						
			outToOtherServer.writeBytes("GetMemberList" + "\n");

			fromOtherServer = new  BufferedReader
	        		(new InputStreamReader(otherServerSocket.getInputStream()));
			
			myClient.Send(members + fromOtherServer.readLine());
			
			}
						
						
		}
		else
			myClient.Send("There is no similar request");
		}
		
	
	
	public void setMyClient(ClientThread myClient) {
		this.myClient = myClient;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}
	
	public void setMyClientName(String myClientName) {
		this.myClientName = myClientName;
	}

	public void setClients(ArrayList<ClientThread> clients) {
		Clients = clients;
	}

	public void setOtherServer(Socket otherServer) {
		this.otherServerSocket = otherServer;
	}
	
}
