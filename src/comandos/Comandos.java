package comandos;

import Controlador.Controlador;


public interface Comandos {
	
	//=======================================================
	//Metodos abstractos
	//=======================================================
	
	/**Ejecuta el comando correspondiente sobre el mundo   */
	public abstract StringBuffer ejecuta(Controlador controlador);
	/**Recibe un array de String que procesa devolviendo el comando que representa el String */
	public abstract Comandos parsea(String []cadenaComando);
	/**Devuelve un String con la información de ayuda*/
	public abstract String textoAyuda();
}
