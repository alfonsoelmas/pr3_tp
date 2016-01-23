package comandos;

import Controlador.Controlador;

public class Salir implements Comandos{

	//=======================================================
	//					CONSTRUCTORA
	//=======================================================
	public Salir()
	{
		
	}
	
	//=======================================================
	//					Metodos
	//=======================================================
	
	/** Ejecuta el comando Salir devolviendo un StringBuffer*/
	
	@Override
	public StringBuffer ejecuta(Controlador controlador) 
	{
		controlador.setFinalizado(true);
		return new StringBuffer("Se ha finalizado la ejecución del programa");
	}
	
	//=======================================================

	/** Dado una cadenaComando, comprueba que se trata del comando Salir, ignorando las
	 *  mayusculas y minusculas. A su vez, devuelve el comando o null en caso contrario */
	
	@Override
	public Comandos parsea(String[] cadenaComando) 
	{
		Comandos comando= null;
		
		if(cadenaComando[0].equalsIgnoreCase("SALIR")) comando = new Salir();
		
		return comando;
	}
	
	//=======================================================

	/** Devuelve un String indicando lo que hace el comando Salir */
	
	@Override
	public String textoAyuda() 
	{
		// TODO Auto-generated method stub
		return "SALIR: Cierra la ejecución del programa";
	}
	
	
}
