package ufps.architecture.agents.instability;


import java.net.URISyntaxException;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import ufps.architecture.messenger.interfaces.IMessengerSocket;

public class InstabilityAgentSocket extends IMessengerSocket {
	
	private Socket socket;
	private InstabilityAgent ia;
	
	public InstabilityAgentSocket(String ip_server, String port) throws URISyntaxException {
		this.socket = IO.socket("http://" + ip_server + ":" + port);
		this.setConfigClient();
		this.runConection();
		this.ia = new InstabilityAgent();
	}
	
	
	@Override
	protected void setConfigClient() {
		socket.on("get data", new Emitter.Listener() {

			public void call(Object... arg0) {	
			    sendData();
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
		socket.emit("set data", this.ia.returnData());
	}
	

	@Override
	public void registrer() {
		socket.emit("add", this.ia.giveName());
	}	

}
