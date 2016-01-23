package logica;


import java.io.PrintWriter;
import java.util.Scanner;

import excepciones.DestruirCelulaException;

import excepciones.FormatoNumeroIncorrecto;
import excepciones.IndicesFueraDeRango;

public abstract class Mundo {
	
	//================================
	//			 ATRIBUTOS
	//================================
	
	/**Atributo superficie. Contiene una superficie dentro del mundo*/
	protected Superficie  superficie;
	
	/**Atributo casillas. Contiene las dimensiones del mundo*/
	protected Casilla 	casillas;
	
	
	//=================================
	//			CONSTRUCTORA		
	//=================================
	
	/**Constructora simple de mundo. Inicializa la superficie a null y la dimension a 0*/
	public Mundo()
	{
		this.casillas = new Casilla(0,0);
		this.superficie = null;
	}
	
	/**Constructora de mundo. Lo inicializa con unas dimensiones concretas y un numero de celulas concretas (simples o complejas)*/
	public Mundo(Casilla casillas)
	{
		this.casillas = casillas;
		this.superficie = null;
	}
	
	//================================
	//	  	 METODOS ABSTRACTOS
	//================================
	
	/**Metodo abstracto. Inicializa la superficie con una tamaño determinado y unas celulas determinadas */
	public abstract void inicializaMundo();
	
	/**Metodo abstracto. Guarda el mundo*/
	public abstract void   guardar(PrintWriter pinto);
	
	/**Metodo abstracto. Crea una celula en una posición */
	public abstract StringBuffer crearCelula(Casilla pos);

	
	//===================================
	//			   METODOS
	//===================================
	
	/**Metodo abstracto. Carga el mundo. */
	public boolean cargar(Scanner sc)
	{
		boolean comp = true;
		try
		{
			int fila = Integer.parseInt(sc.nextLine());
			int col  = Integer.parseInt(sc.nextLine());
			this.casillas = new Casilla(fila,col);
			inicializaMundo();
			comp = this.superficie.cargar(sc);
		}catch(NumberFormatException e)
		{
			try {
				throw new FormatoNumeroIncorrecto("Formato de numero Incorrecto. Dimension mal interpretada.");
			} catch (FormatoNumeroIncorrecto e1) {
				System.out.println(e1.getMessage());
			}
			comp = false;
		}
		return comp;

	}
	
	/**Realiza la evolución del mundo. */
	public StringBuffer evoluciona()
	{
		return this.superficie.evoluciona();
	}

	/**Pone a null una casilla de la superficie*/
	public StringBuffer destruirCelula(Casilla pos)
	{
		StringBuffer datos = new StringBuffer("");
		try
		{
			if(this.superficie.queCelulaHay(pos) == Superficie.ES_VACIA)
			{
				throw new DestruirCelulaException("No se puede destruir una posicion vacia.");
			}
			else
			{
				this.superficie.destruyeCelula(pos);
				datos.append("Se ha destruido la celula de la posicion" + pos.posToStringBuffer());
			}
		}
		catch(NullPointerException e)
		{
			try {
				throw new IndicesFueraDeRango("Esa posición no se encuentra en la superficie");
			} catch (IndicesFueraDeRango e1) {
				System.out.println(e1.getMessage());
			}
		} catch (DestruirCelulaException e) {
			System.out.println(e.getMessage());
		}
		
		return datos;
	}
	
	/**Devuelve un StringBuffer de la superficie*/
	public StringBuffer toStringBuffer()
	{
		return this.superficie.toStringBuffer();
	}
	
	/**Vacía la superficie*/
	public void vaciar()
	{
		this.superficie.reset();
	}
	
	public int queCelulaEs(Casilla pos)
	{
		int tipo = 0;
		try
		{
			tipo = this.superficie.queCelulaHay(pos);
		}catch(NullPointerException e)
		{
			try {
				throw new IndicesFueraDeRango("Esa posición no se encuentra en la superficie.");
			} catch (IndicesFueraDeRango e1) {
				System.out.println(e1.getMessage());
			}
		}
		return tipo;
	}
}
