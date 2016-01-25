package comandos;

import Controlador.Controlador;

public class Ver implements Comandos{

	//=======================================================
	//						Constructora
	//=======================================================
	
	/**Constructora del comando ver.*/
	public Ver()
	{
		
	}
	//=======================================================
	//						Metodos
	//=======================================================
	
	/**Ejecuta el comando ver */
	@Override
	public StringBuffer ejecuta(Controlador controlador)
	{
		return controlador.superficieToStringBuffer();
	}

	//=======================================================
	
	/**Parsea el comando. Devuelve null si no se corresponde*/
	@Override
	public Comandos parsea(String[] cadenaComando) 
	{
		Comandos comando= null;

		if(cadenaComando[0].equalsIgnoreCase("VER")) comando = new Ver();
		
		return comando;
	}

	//=======================================================
	
	/**Devuelve la ayuda del comando*/
	@Override
	public String textoAyuda() {
		// TODO Auto-generated method stub
		return "VER: muestra el mundo.";
	}

}