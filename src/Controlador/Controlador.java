package Controlador;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import comandos.Comandos;
import comandos.ParserComandos;
import entradaSalida.Entrada;
import entradaSalida.Salida;
import excepciones.FicheroIncompleto;
import excepciones.IndicesFueraDeRango;
import excepciones.PalabraIncorrecta;
import logica.Casilla;
import logica.Mundo;
import logica.MundoComplejo;
import logica.MundoSimple;

public class Controlador {


	//=======================================================
	//Atributos
	//=======================================================
	
	/**Contiene el mundo sobre el que trabaja*/
	private Mundo mundo;
	/**Contiene la entrada de la que interpreta datos*/
	private Entrada entrada;
	/**Indica si la simulación está en marcha o finalizada*/
	private boolean simulacionTerminada;
	
	//=======================================================
	//Constructora
	//=======================================================
	
	/**Inicializa los atributos a los que se les pase por parametro*/
	public Controlador(Entrada entrada)
	{
		this.entrada = entrada;
		this.simulacionTerminada = false;
	}
	
	//=======================================================
	//Metodos
	//=======================================================
	
	/**Ejecuta el controlador y la simulación */
	public void ejecutaSimulacion()
	{
		Salida salida = new Salida();
		do
		{
			//Leemos el comando
			salida.pinta("Comando > ");
			Comandos comando = null;
				comando = ParserComandos.parseaComando(entrada.leo().split(" "));
			
			//Si se ha interpretado bien, lo ejecutamos y pintamos.
			if(comando!=null)
			{
				// Pinta el string que devuelve cada comando
				salida.pintaln(comando.ejecuta(this));

			}
			else
			{
				salida.pintaln("Comando mal interpretado");
			}
			
		}while(!this.simulacionTerminada);
	}

	/**Realiza el comando guardar. Guarda la partida.*/
	public StringBuffer guardar(String ruta)
	{
		StringBuffer datos = new StringBuffer("Se ha guardado.");
		try {
			PrintWriter pinto = new PrintWriter(new BufferedWriter(new FileWriter(ruta)));
			mundo.guardar(pinto);
			pinto.close();
		} catch (IOException e) {
			try {
				throw new FicheroIncompleto("Error de entrada y salida.");
			} catch (FicheroIncompleto e1) {
				datos.append(e1.getMessage());
			}
		}
		catch(NullPointerException e)
		{
			try {
				throw new IndicesFueraDeRango("ERROR. Mundo no creado.");
			} catch (IndicesFueraDeRango e1) {
				System.out.println(e1.getMessage());
				datos = new StringBuffer("");
			}
		}
		
		return datos;
		
	}

	/**Devuelve el tipo de celula que hay en una posicion del mundo*/
	public int queCelulaEs(Casilla posicion)
	{
		int dato = 0;
		try{dato = this.mundo.queCelulaEs(posicion);}
		catch(NullPointerException e)
		{
			try {
				throw new IndicesFueraDeRango("ERROR. Mundo no creado");
			} catch (IndicesFueraDeRango e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		return dato;
	}

	/**Devuelve un string con todas las ayudas de los comandos*/
	public String ayuda() {
		return ParserComandos.AyudaComandos();
	}

	public StringBuffer superficieToStringBuffer() {
		StringBuffer datos = new StringBuffer("");
		try{datos = this.mundo.toStringBuffer();}
		catch(NullPointerException e)
		{
		}
		return datos;
	}

	/**Crea una celula en una posicion si se puede*/
	public StringBuffer crearCelula(Casilla posicion)
	{
		StringBuffer datos = new StringBuffer("");
		try{return this.mundo.crearCelula(posicion);}
		catch(NullPointerException e)
		{
			try {
				throw new IndicesFueraDeRango("ERROR. Mundo no creado");
			} catch (IndicesFueraDeRango e1) {
				datos= new StringBuffer(e1.getMessage());
			}
		}
		return datos;
	}

	/**Elimina una celula de una posicion*/
	public StringBuffer eliminarCelula(Casilla posicion)
	{
		return this.mundo.destruirCelula(posicion);
	}
	
	/**Realiza un paso en el juego.*/
	public StringBuffer paso()
	{
		StringBuffer datos = new StringBuffer("");
		try
		{
			datos = this.mundo.evoluciona();
		}
		catch(NullPointerException e)
		{
			try {
				throw new IndicesFueraDeRango("ERROR. Mundo no creado.");
			} catch (IndicesFueraDeRango e1) {
				System.out.println(e1.getMessage());
			}
			datos = new StringBuffer("");
		}
		return datos;
	}

	/**Vacia el mundo*/
	public StringBuffer vaciarMundo()
	{
		StringBuffer datos = new StringBuffer("Mundo vaciado con exito.");
		try{this.mundo.vaciar();}
		catch(NullPointerException e)
		{
			try {
				throw new IndicesFueraDeRango("ERROR. Mundo no creado.");
			} catch (IndicesFueraDeRango e1) {
				System.out.println(e1.getMessage());
			}
			
			datos = new StringBuffer("");
		}
		return datos;
	}
	
	/**Carga una partida*/
	public StringBuffer cargar(String ruta)
	{
		
		Mundo nuevoMundo = null;
		File lectura = new File(ruta);
		StringBuffer datos = new StringBuffer("");
		
		if(!lectura.exists()) datos.append("Error. El archivo no existe");
		Scanner sc = null;

		try {
			sc = new Scanner(lectura);
			String comoEs = sc.nextLine();
			if(comoEs.equalsIgnoreCase("simple"))
			{
				Mundo m = this.mundo;
				this.mundo = new MundoSimple();
				if(!mundo.cargar(sc))
				{
					this.mundo = m;
					throw new FicheroIncompleto("Error al cargar");
				}
				datos.append("Exito.");
			}
			else if(comoEs.equalsIgnoreCase("complejo"))
			{
				Mundo m = this.mundo;
				this.mundo = new MundoComplejo();
				if(!this.mundo.cargar(sc))
				{
					this.mundo = m;
					throw new FicheroIncompleto("Error al cargar.");
				}
				datos.append("Exito.");
			}
			else{ throw new FicheroIncompleto("Error. No se lee bien que tipo de mundo es");}

		} 
		catch (FileNotFoundException e) 
		{
			try 
			{
				throw new PalabraIncorrecta("Fichero no encontrado");
			} catch (PalabraIncorrecta e1) {
				datos.append(e1.getMessage());
			}
			
		} catch (FicheroIncompleto e) 
		{
			datos.append(e.getMessage());
		} 
		catch (NoSuchElementException e)
		{
			try {
				throw new FicheroIncompleto("No hay elementos en el archivo");
			} catch (FicheroIncompleto e1) {
				System.out.println(e.getMessage());
			}
		}
		
		finally{ if(sc!= null )sc.close();}
		
		return datos;

	}

	public void juegaSimple(Casilla dim, int nCel)
	{
		this.mundo = new MundoSimple(dim, nCel);
	}
	
	public void juegaComplejo(Casilla dim, int sCel, int cCel)
	{
		this.mundo = new MundoComplejo(dim, sCel, cCel);
	}

	public void setFinalizado(boolean b)
	{
		this.simulacionTerminada = b;
	}

}
