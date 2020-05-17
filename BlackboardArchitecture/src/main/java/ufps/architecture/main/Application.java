package ufps.architecture.main;

import java.net.URISyntaxException;
import ufps.architecture.controller.ControllerSocket;


public class Application {
	
	public static void main(String[] args) {
		
		try {
			ControllerSocket c = new ControllerSocket("localhost", "2020");
		} catch (URISyntaxException e) {
			System.out.println("Error al lanzar el sistema");
		}		
	}
		
}
