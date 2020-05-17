package ufps.architecture.agents.wet;

import java.util.ArrayList;
import java.util.TreeMap;
import ufps.architecture.agents.ISensor;

public class WetAgent implements ISensor {
	
	private ArrayList<Double> data;
	private String name;
	
	public WetAgent() {
		this.data = null;
		this.name = "Wet";
	}		

	@Override
	public TreeMap<String, String> returnData() {
		this.makeMeasurements();
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("agent_name", this.name);
		map.put("indicator", String.valueOf(this.geIndicator()));		
		return map;
	}

	@Override
	public void makeMeasurements() {
		// El sensor toma 35 muestras
		ArrayList<Double> data = new ArrayList<>();
		for (int i = 0; i < 35; i++) {
			data.add((Math.random()*8)+2);  
		}
		this.data = data;
	}

	@Override
	public Double geIndicator() {
		Double indicator = 0.0d;
		
		for (int i = 0; i < this.data.size(); i++) {					
			indicator += this.data.get(i);			
		}
		return indicator/this.data.size();
	}	
	
	@Override
	public TreeMap<String, String> giveName() {
		// TODO Auto-generated method stub
		TreeMap<String, String> data = new TreeMap<>();
		data.put("indicator", "");
		data.put("agent_name", this.name);
		return data;
	}		
}
