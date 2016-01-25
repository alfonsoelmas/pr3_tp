package comandos;

import Controlador.Controlador;
import entradaSalida.Salida;
import excepciones.FormatoNumeroIncorrecto;
import excepciones.PalabraIncorrecta;
import logica.Casilla;

public class Jugar implements Comandos{
	
	/**Almacena el modo de juego.*/
	private String tipoDeJuego;
	
	/**Contiene la informacion con las dimensiones*/
	private Casilla dim;
	
	/**Contiene el numero de celulas simples*/
	private int celSimples;
	
	/**Contiene el numero de celulas complejas*/
	private int celComplejas;
	
	
	/**Constructora. Inicializa los atributos a lo pasado por parametro.*/
	public Jugar(String juego, Casilla pos, int sCel, int cCel)
	{
		this.tipoDeJuego = juego;
		this.dim = pos;
		this.celComplejas = cCel;
		this.celSimples = sCel;
	}
	
	/**Ejecuta el comando jugar*/
	@Override
	public StringBuffer ejecuta(Controlador controlador)
	{
		StringBuffer datos = new StringBuffer("");
		
		if(this.tipoDeJuego.equalsIgnoreCase("simple"))
		{
			
			controlador.juegaSimple(this.dim, this.celSimples);
			datos.append("Se ha creado un mundo simple");
		}
		else if(this.tipoDeJuego.equalsIgnoreCase("complejo"))
		{
			
			controlador.juegaComplejo(this.dim, this.celSimples, this.celComplejas);
			datos.append("Se ha creado un mundo compejo");
		}
		else
		{
			try {
				throw new PalabraIncorrecta("");
			} catch (PalabraIncorrecta e) {
				datos = null;
				System.out.println(e.getMessage());
			}
		}
		
		return datos;
		
	}

	/**Interpreta el comando y devuelve null si no lo reconoce*/
	@Override
	public Comandos parsea(String[] cadenaComando)
	{
		
		Comandos comando = null;
		Casilla pos = null;
		try
		{
		if ( cadenaComando[0].equalsIgnoreCase("JUGAR"))
		{
			if ( cadenaComando[1].equalsIgnoreCase("simple"))
			{
				pos = new Casilla(Integer.parseInt(cadenaComando[2]),Integer.parseInt(cadenaComando[3]));
				
				comando = new Jugar(cadenaComando[1], pos, Integer.parseInt(cadenaComando[4]),0);
				
			}else if( cadenaComando[1].equalsIgnoreCase("complejo"))
			{
				pos = new Casilla(Integer.parseInt(cadenaComando[2]),Integer.parseInt(cadenaComando[3]));
				
				comando = new Jugar(cadenaComando[1], pos, Integer.parseInt(cadenaComando[4]),Integer.parseInt(cadenaComando[5]));
			}
			
		}
		}catch(NumberFormatException e)
		{
			try {
				throw new FormatoNumeroIncorrecto("ERROR. Introduce bien el comando Jugar. No se han leido bien los numeros");
			} catch (FormatoNumeroIncorrecto e1) {
				System.out.println(e1.getMessage());
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			try {
				throw new PalabraIncorrecta("ERROR. Introduce: JUGAR + TIPO DE JUEGO + DIMENSION FILA + DIMENSION COLUMNA"
						+ " + NUMERO  CELULAS SIMPLE + NUMERO  CELULAS COMPLEJAS. "
						+ "\n \t \t  Se debe dejar vacio aquellas celulas que no existe en el mundo correspondiente. "
						+ "\n \t \t  Ejemplo: jugar simple 3 3 4");
			} catch (PalabraIncorrecta e1) {
				Salida salida = new Salida();
				salida.pintaln(e1.getMessage());
			}
		}
		return comando;
	}


	/**Devuelve un string con la ayuda del comando*/
	@Override
	public String textoAyuda() {
	
		
		// TODO Auto-generated method stub
		return "JUGAR (simple/complejo): Inicia el modo de juego";
	}

	
	
}
