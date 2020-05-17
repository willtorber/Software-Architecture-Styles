package ufps.architecture.controller;

import java.net.URISyntaxException;

import ufps.architecture.agents.analist.AnalistAgentSocket;
import ufps.architecture.agents.instability.InstabilityAgentSocket;
import ufps.architecture.agents.temperature.TemperatureAgentSocket;
import ufps.architecture.agents.wet.WetAgentSocket;
import ufps.architecture.blackboard.Blackboard;

public class Controller {

	private Blackboard blackboard;
	private TemperatureAgentSocket agentTemp;
	private WetAgentSocket agentWet;
	private InstabilityAgentSocket agentInsta;
	private AnalistAgentSocket agentAnal;
	
	
	public Controller(String hostname, String port) {
		
		this.blackboard = new Blackboard(hostname, Integer.parseInt(port));
		
		try {			
			this.agentInsta = new InstabilityAgentSocket(hostname, port);
			this.agentWet = new WetAgentSocket(hostname, port);
			this.agentTemp = new TemperatureAgentSocket(hostname, port);
			this.agentAnal = new AnalistAgentSocket(hostname, port);
		} catch (URISyntaxException e) {		
			System.out.println("Error al ejecutar las fuentes de conocimiento");
		}
		
		this.startAgents();
	}
	
	private void startAgents() {
		this.agentInsta.registrer();
		this.agentTemp.registrer();
		this.agentWet.registrer();
		this.agentAnal.registrer();		
	}
	
	/*public static void x() {
		InstabilityAgentSocket i;
		try {
			i = new InstabilityAgentSocket("localhost", "2020");
			i.registrer();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
		
}
