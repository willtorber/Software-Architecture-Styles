package ufps.architecture.agents;

import java.util.TreeMap;

public interface ISensor {
	
	public void makeMeasurements();
	public Double geIndicator();
	public TreeMap<String, String> returnData();
	public TreeMap<String, String> giveName();

}
