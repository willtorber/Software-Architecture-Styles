package ufps.architecture.blackboard;

import java.util.ArrayList;
import java.util.TreeMap;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import ufps.architecture.util.Datos;

public class Blackboard {
	
	private ArrayList<String> agents = new ArrayList<>();
	private ArrayList<String> agentsOnline = new ArrayList<>();
	private TreeMap<String, Double> data = new TreeMap<>();
	private Double probability = 0d;
		
	public Blackboard(String hostname, int port) {
		
		/*
		 * Se registran los agentes que podrán acceder a la pizarra
		 */		
		agents.add("Analist");
		agents.add("Instability");
		agents.add("Temperature");
		agents.add("Wet");
		Configuration config = this.configure(hostname, port);
		final SocketIOServer server = this.createrServer(config);
		this.runServer(server);
	}
	
	public Configuration configure(String hostname, int port) {
		System.out.println(hostname + " address");
		Configuration config = new Configuration();
	    config.setHostname(hostname);
	    config.setPort(port);
	    return config;
	}
	
	public SocketIOServer createrServer(Configuration config) {
	    final SocketIOServer server = new SocketIOServer(config);
	    server.addConnectListener(new ConnectListener() {
	    	
			@Override
			public void onConnect(SocketIOClient arg0) {
				// TODO Auto-generated method stub
				System.out.println("Connect Listener");
			}
	    });
	    	    	    	  	    
	    server.addDisconnectListener(new DisconnectListener() {
	        @Override
	        public void onDisconnect(SocketIOClient client) {
	            System.out.println("onDisconnected");
	        }
	    });
	    
	    server.addEventListener("add", Datos.class, new DataListener<Datos>() {

			@Override
			public void onData(SocketIOClient arg0, Datos arg1, AckRequest arg2) throws Exception {
				// TODO Auto-generated method stub				
				if (isRegistrer(arg1.getAgent_name())) {
					agentsOnline.add(arg1.getAgent_name());
				}	            		
				
				if (agentsOnline.size() == agents.size()) {
					System.out.println("Se procede a llamar a los agentes");
					server.getBroadcastOperations().sendEvent("get data");
				}				
			}	    	
	    });
	    
	    
	    server.addEventListener("set data", Datos.class, new DataListener<Datos>() {

			@Override
			public void onData(SocketIOClient arg0, Datos arg1, AckRequest arg2) throws Exception {
				if (isOnline(arg1.getAgent_name())) {					
					data.put(arg1.getAgent_name(), Double.parseDouble(arg1.getIndicator()));
				}	            			
				
				if(areDataComplete()) {
					System.out.println("Registro de datos completo, inicia el llamado a analista");
					server.getBroadcastOperations().sendEvent("query analist",
							new Datos(String.valueOf((data.get("Wet") 
									+ data.get("Temperature") 
									+ data.get("Instability"))),""));					
					data.clear();
				}
			}	    	
	    });
	    
	    server.addEventListener("rest analist", Datos.class, new DataListener<Datos>() {

			@Override
			public void onData(SocketIOClient arg0, Datos arg1, AckRequest arg2) throws Exception {
				// TODO Auto-generated method stub           
				System.out.println("Procentaje de probabilidad de un tornado: " + arg1.getIndicator());
				probability = Double.parseDouble(arg1.getIndicator());
				
				if(isAlertness()) {
					server.getBroadcastOperations().sendEvent("alertness", new Datos(String.valueOf(probability), ""));
				} else {
					server.getBroadcastOperations().sendEvent("get data");
				}							
			}	    	
	    });	    	   
	    	    
	    return server;
	}
	
	public void runServer(SocketIOServer server) {
		System.out.println("Starting server...");
	    server.start();
	    System.out.println("Server started");
	}
	
	private Boolean isRegistrer(String arg) {
		return this.agents.contains(arg);
	}
	
	
	private Boolean isOnline(String arg) {
		return this.agentsOnline.contains(arg);
	}
	
	private Boolean areDataComplete() {		
		return (this.data.size() == 3);
	}
	
	private Boolean isAlertness() {
		return (this.probability > 55);
	}

}
