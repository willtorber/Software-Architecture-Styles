package ufps.architecture.agents.analist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import ufps.architecture.database.DataBase;


public class AnalistAgent {

	private String name;
	private DataBase database;

	public AnalistAgent() {
		this.name = "Analist";
		this.database = DataBase.getInstance("jdbc:mysql://localhost:3306/arq_soft_tornados", "root", "");
	}

	private ArrayList<Double> getReportsFromThePast() {
		ArrayList<Double> historicos = new ArrayList<>();

		ResultSet query = database
				.query("SELECT `id`, `fecha`, `inestabilidad`, `temperatura`, `humedad` FROM `historicos`");

		try {
			while (query.next()) {
				historicos.add((double) (Double.parseDouble(query.getString("inestabilidad"))
						+ Double.parseDouble(query.getString("temperatura"))
						+ Double.parseDouble(query.getString("humedad"))) / 3);
			}
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en el proceso de consulta de historicos");
		}
		return historicos;
	}

	public Double analizeData(Double data) {
		ArrayList<Double> historicos = this.getReportsFromThePast();
		Double probability = 0.0d;
		int contCritical = 0;	
		for (Double item : historicos) {			
			boolean critical = this.isCriticalData(item, data/3);
			if (critical) {
				contCritical++;				
			}
		}		
		return (double)(contCritical * 100)/ historicos.size(); 
				
	}

	private boolean isCriticalData(Double xH, Double xN) {
		return (xN>=xH) || (Math.abs(xH-xN) < 0.5d);
	}

	public TreeMap<String, String> giveName() {
		// TODO Auto-generated method stub
		TreeMap<String, String> data = new TreeMap<>();
		data.put("indicator", "");
		data.put("agent_name", this.name);
		return data;
	}

}
