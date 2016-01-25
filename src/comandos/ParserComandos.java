package comandos;


import logica.Casilla;

public class ParserComandos {
	
	//=======================================================
	//					Atributos
	//=======================================================
	
	// Los atributos estaticos( o de la clase ) son datos unicos que quedan fuera de todos las instancias/objetos de la clase
	// y pueden ser accedidos por cualquiera de ellos
	
	/**Contiene un array con una posicion*/
	private static Casilla pos = new Casilla(0,0);
	/**Contiene un array con los comandos existentes*/
	private static Comandos[] comandos = {
			new EliminarCelula(pos),
			new CrearCelula(pos),
			new Ayuda(),
			new Paso(),
			new Salir(),
			new Guardar(""),
			new Cargar(""),
			new Jugar("", pos, 0 , 0),
			new Vaciar(),
			new Ver()
	};
	
	//=======================================================
	//Constructora
	//=======================================================

	//=======================================================
	//Metodos
	//=======================================================
	
	/** Recorro el Array de comandos y ejecuto el metodo textoAyuda de todos los comandos del array.
	 *  De esta manera imprimo por pantalla lo que hace cada comando.
	 *  -- Se trata de un metodo estatico -- */
	

	public static String AyudaComandos(){
		String ayuda = "";
		
		for(int i=0; i < comandos.length; i++)
			ayuda += (comandos[i].textoAyuda() + "\n");
			
		return ayuda;
	}
	
	/** Dada un array de cadenas, lo interpreta y devuelve el comando correspondiente.
	 *  -- Se trata de un metodo estatico -- */
	
	public static Comandos parseaComando(String[ ] cadenas)
	{
		Comandos comando = null;
		int i=0;
		
		/* Recorro el array de comandos para parsear la correspondiente cadena y determinar de que comando se trata
		   Una vez determinado de que comando se trata nuestra cadena me salgo del bucle
		 */
		try
		{
			while(comando==null && i<comandos.length)
			{
				// Para cada posicion parseo la cadena parea determinar el comando
				comando = comandos[i].parsea(cadenas);
				i++;
			}
		}catch(ArrayIndexOutOfBoundsException e){comando = null;}
		
		return comando;
	}
}

		