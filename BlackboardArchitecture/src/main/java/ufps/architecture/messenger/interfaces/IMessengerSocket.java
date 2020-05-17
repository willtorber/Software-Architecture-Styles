package ufps.architecture.messenger.interfaces;
/**
 *  
 * @author  William Torres
 * @version 2.0.0
 * @since   2018-03-28 
 */
public abstract class IMessengerSocket {
			
	/**
	 * Se establecen todos los parametros de configuración
	 * */
	protected abstract void setConfigClient();
	
	/**
	 * Establece una conexión con la pizarra
	 * */
	protected abstract void runConection();
	
	/**
	 * Cierra la conexión con la pizarra
	 * */
	public abstract void closeConection();
	
	/**
	 * Envia datos a la pizarra
	 * */
	public abstract void sendData();
	
	
	/**
	 * Establece una sesión en el servidor encargado de transmitir los mensajes
	 * */
	public abstract void registrer();

}
