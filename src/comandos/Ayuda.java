package comandos;

import Controlador.Controlador;

public class Ayuda implements Comandos{
	
	//=======================================================
	// 					Constructora
	//=======================================================
	
	
	/**Constructora de ayuda (vacía)*/
	public Ayuda() 
	{
		
	}
	
	
	//=======================================================
	// 					Metodos
	//=======================================================
	
	/** Ejecuta el comando Ayuda devolviendo un StringBuffer */
	
	@Override
	public StringBuffer ejecuta(Controlador controlador) 
	{
		return new StringBuffer(controlador.ayuda());
	}
	
	//=======================================================
	
	/** Dado una cadenaComando, comprueba que se trata del comando ayuda, ignorando las
	 *  mayusculas y minusculas. A su vez, devuelve el comando o null en caso contrario */
		
	@Override
	public Comandos parsea(String[] cadenaComando) 
	{
		// TODO Auto-generated method stub
		Comandos comando= null;

		if(cadenaComando[0].equalsIgnoreCase("AYUDA")) comando = new Ayuda();

		return comando;
	}
	
	//=======================================================
	
	/** Devuelve un String indicando lo que hace el comando ayuda*/
	
	@Override
	public String textoAyuda() 
	{
		// TODO Auto-generated method stub
		return "AYUDA: Muestra la ayuda";
	}

	
}
