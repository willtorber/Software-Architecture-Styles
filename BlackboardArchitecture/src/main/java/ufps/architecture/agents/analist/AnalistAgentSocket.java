package ufps.architecture.agents.analist;


import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ufps.architecture.messenger.interfaces.IMessengerSocket;


public class AnalistAgentSocket extends IMessengerSocket {

	private Socket socket;
	private AnalistAgent ia;
	
	public AnalistAgentSocket (String ip_server, String port) throws URISyntaxException {
		this.socket = IO.socket("http://" + ip_server + ":" + port);
		this.setConfigClient();
		this.runConection();
		this.ia = new AnalistAgent();
	}
	
	
	@Override
	protected void setConfigClient() {
		socket.on("query analist", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				final String json = arg0[0].toString();				
		    	final Gson gson = new Gson();
		    	final Type tipoListaProperties = new TypeToken<Properties>(){}.getType();
		    	final Properties properties = gson.fromJson(json, tipoListaProperties);		        
				TreeMap<String, String> rest =  new TreeMap<>();
				String porcentaje = String.valueOf(ia.analizeData(Double.parseDouble(properties.getProperty("indicator"))));
				rest.put("indicator", porcentaje);
				socket.emit("rest analist", rest);
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
		socket.emit("set data");
	}
	

	@Override
	public void registrer() {
		socket.emit("add", this.ia.giveName());
	}
		
}

