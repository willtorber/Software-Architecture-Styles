package ufps.architecture.util;

public class Datos {
	
	private String indicator;
	private String agent_name;
	
	public Datos(String indicator, String agent_name) {
		super();
		this.indicator = indicator;
		this.agent_name = agent_name;
	}
	
	public Datos () {
		
	}

	public String getIndicator() {
		return indicator;
	}

	public String getAgent_name() {
		return agent_name;
	}
}
