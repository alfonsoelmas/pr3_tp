package comandos;

import Controlador.Controlador;

public class Vaciar implements Comandos{

	//=======================================================
	//						Constructora
	//=======================================================
	
	/**Constructora del comando vaciar.*/
	public Vaciar()
	{
		
	}
	//=======================================================
	//						Metodos
	//=======================================================
	
	/**Ejecuta el comando vaciar */
	@Override
	public StringBuffer ejecuta(Controlador controlador)
	{
		controlador.vaciarMundo();
		return new StringBuffer("Se ha vaciado el mundo.");
	}

	//=======================================================
	
	/**Parsea el comando. Devuelve null si no se corresponde*/
	@Override
	public Comandos parsea(String[] cadenaComando) 
	{
		Comandos comando= null;
		try
		{
			if(cadenaComando[0].equalsIgnoreCase("VACIAR")) comando = new Vaciar();
		}
		catch(Exception e){ comando = null;}
		
		return comando;
	}

	//=======================================================
	
	/**Devuelve la ayuda del comando*/
	@Override
	public String textoAyuda() {
		// TODO Auto-generated method stub
		return "VACIAR: vacía el mundo de células.";
	}

}
