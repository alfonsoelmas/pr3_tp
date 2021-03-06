package comandos;

import Controlador.Controlador;

import logica.Casilla;


public class CrearCelula implements Comandos{

	//=======================================================
	//					Atributos
	//=======================================================
	
	/**Contiene la posici�n donde a�adir la celula*/
	private Casilla posicion;
	
	//=======================================================
	//					Constructora
	//=======================================================
	
	/**Constructora de la clase. Crea una instancia del comando crearcelula*/
	public CrearCelula(Casilla pos) 
	{
		// TODO Auto-generated constructor stub
		this.posicion = pos;
		
	}
	
	//=======================================================
	//						Metodos
	//=======================================================
	
	/** Ejecuta el comando crearCelula */
	@Override
	public StringBuffer ejecuta(Controlador controlador)
	{

		return controlador.crearCelula(this.posicion);
	}

	//=======================================================
	
	/** Dado una cadenaComando, comprueba que se trata del comando crearCelula, ignorando las
	 *  mayusculas y minusculas. A su vez, devuelve el comando o null en caso contrario */
	
	@Override
	public Comandos parsea(String[] cadenaComando)
	{
		// TODO Auto-generated method stub
		Comandos comando= null;
		
		// Compruebas que la longitud de la cadena es 3, pues la primera posicion sera el comando, la segunda posicion
		// sera la fila y la tercera la columna correspondiente de la posicion de la celula que se vaya a crear
		//Controlamos la excepcion...

			if(cadenaComando.length == 3 && cadenaComando[0].equalsIgnoreCase("CREARCELULA")) 
			{
					Casilla pos = new Casilla(Integer.parseInt(cadenaComando[1]),Integer.parseInt(cadenaComando[2]));
					comando = new CrearCelula(pos);
			}

		
		return comando;
	}
	
	//=======================================================

	/** Devuelve un string indicando lo que hace el comando crearCelula*/
	
	@Override
	public String textoAyuda() 
	{
		return "CREARCELULA f c: Crea una c�lula en fila columna.";
	}


}
