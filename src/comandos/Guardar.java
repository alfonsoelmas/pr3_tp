package comandos;

import Controlador.Controlador;
import excepciones.ErrorDeInicializacion;

public class Guardar implements Comandos{

	/**Atributo. Almacena el nombre con el que se va a guardar la partida*/
	private String ruta;
	
	/**Constructora. Crea una instancia e inicializa el nombre del archivo (ruta)*/
	public Guardar(String ruta)
	{
		this.ruta = ruta;
		
	}
	
	/**Ejecuta el comando. Llama a controlador para ello.*/
	@Override
	public StringBuffer ejecuta(Controlador controlador)
	{
		return controlador.guardar(ruta);
	}

	/**Parsea el comando. Devuelve null si no es el comando.*/
	@Override
	public Comandos parsea(String[] cadenaComando)
	{
		Comandos comando = null;
		try
		{
			if(cadenaComando[0].equalsIgnoreCase("GUARDAR")) 
			{
					comando = new Guardar(cadenaComando[1]);
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			try {
				throw new ErrorDeInicializacion("Escribe 'guardar + ruta' ");
			} catch (ErrorDeInicializacion e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		return comando;
	}

	/**Devuelve un string con la ayuda del comando*/
	@Override
	public String textoAyuda() {
		return "GUARDAR: Guarda la partida";
	}
	

}
