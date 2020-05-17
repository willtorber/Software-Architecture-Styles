package ufps.architecture.controller;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ufps.architecture.messenger.interfaces.IMessengerSocket;

public class ControllerSocket extends IMessengerSocket {
	
	private Socket socket;
	private Controller clr;
	
	public ControllerSocket (String hostname, String port) throws URISyntaxException {
		this.clr = new Controller(hostname, port);
		this.socket = IO.socket("http://" + hostname + ":" + port);
		this.setConfigClient();
		this.runConection();		
	}
	
	
	@Override
	protected void setConfigClient() {
		socket.on("alertness", new Emitter.Listener() {

			public void call(Object... arg0) {				
				final String json = arg0[0].toString();				
		    	final Gson gson = new Gson();
		    	final Type tipoListaProperties = new TypeToken<Properties>(){}.getType();
		    	final Properties properties = gson.fromJson(json, tipoListaProperties);	
		    	System.out.println("La probalididad de un tornado en cucuta es del " + properties.getProperty("indicator") +"%");
			}			
		});
	}
			
	@Override
	protected void runConection() {
		socket.connect();
	}


	@Override
	public void closeConection() {
		socket.disconnect();
	}
		

	@Override
	public void sendData() {	
		//socket.emit("set data", this.ia.returnData());
	}
	

	@Override
	public void registrer() {
		//socket.emit("add", this.ia.getName_agent());
	}
		

	private static String getMessage(String args, String field) {			
		JSONParser parser = new JSONParser();		        
        String message = "";
        try {
	        Object obj = parser.parse(args);
	        JSONObject objJson = null;
	        if (obj != null) {
	        	objJson = (JSONObject) obj;	        
	        	message = (String) objJson.get(field);
	        }	        		        	        
		} catch (ParseException e) {
			System.out.println("Los datos entrantes tiene un formato no soportado");
		} 
        return message;
	}


}
