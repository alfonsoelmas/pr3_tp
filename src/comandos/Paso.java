package comandos;

import Controlador.Controlador;

public class Paso implements Comandos{

	//=======================================================
	// 					Constructora
	//=======================================================
	
	/**Constructora del comando paso*/
	public Paso(){
		
		
	}
	
	//=======================================================
	//					Metodos
	//=======================================================
	
	/**Ejecuta el comando paso */
	@Override
	public StringBuffer ejecuta(Controlador controlador)
	{
		return (controlador.paso().append(controlador.superficieToStringBuffer()));
		
	}

	/**Parsea el comando paso devolviendo null si no es el comando*/
	@Override
	public Comandos parsea(String[] cadenaComando) 
	{

		Comandos comando= null;
		
		if(cadenaComando[0].equalsIgnoreCase("PASO")) comando = new Paso();
		
		return comando;
	}
	
	/**Devuelve un string explicando lo que realiza el comando Paso*/
	@Override
	public String textoAyuda() {

		return "PASO: ejecuta el mundo";
	}

}
