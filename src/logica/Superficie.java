package logica;



import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import celulas.Celula;
import celulas.CelulaCompleja;
import celulas.CelulaSimple;
import excepciones.FicheroIncompleto;
import excepciones.FormatoNumeroIncorrecto;
import excepciones.IndicesFueraDeRango;
import excepciones.MeterCelulaException;


public class Superficie {

	//=================================================================
	//							Atributos
	//=================================================================
	
	/**Contiene las dimensiones del tablero*/
	private Casilla casillas;
	/**Contiene la superficie, que es una matriz de células*/
	private Celula [][]superficie;
	
	//=================================================================
	//							Constantes
	//=================================================================
	
	/**Valor que indica que la célula es simple*/
	public static final int ES_SIMPLE = -1;
	
	/**Valor que indica que la célula es compleja*/
	public static final int ES_COMPLEJA = 1;
	
	/**Valor que indica que la casilla está vacía*/
	public static final int ES_VACIA = 0;
	
	//=================================================================
	//							Constructora
	//=================================================================
	
	/**Constructora de Superficie. La inicializa con un numero de celdas*/
	public Superficie(Casilla numCeldas)
	{
		this.casillas = numCeldas;
		
		superficie = new Celula [casillas.getFila()][];
		
		for(int i=0; i<casillas.getFila(); i++)	//recorrido de filas para crear N columnas por fila
		{
			superficie[i] = new Celula[casillas.getCol()];
			
			for(int j=0; j<casillas.getCol(); j++)	//Inicialización de cada casilla
				superficie[i][j] = null;
		}	
			
	}
	
	//=================================================================
	//METODOS
	//=================================================================
	
	/**Metodo evoluciona. Realiza un paso en la superficie*/
	
	public StringBuffer evoluciona()
	{
		StringBuffer hechos = new StringBuffer("");
		
		//Me creo una superficie de booleanos para saber si hay célula o no en una posición....
		boolean [][]supBool = this.supBooleana();
		//Recorrido de la superficie...
		for(int i=0; i<this.casillas.getFila(); i++)
		{
			for(int j=0; j<this.casillas.getCol(); j++)
			{
				//Si en esa posición hay célula...
				if(supBool[i][j] == true)
				{
					//Ordeno ejecutarla, y la casilla a la que se ha movido, la pongo a false en la superficie booleana.
					Casilla c = this.superficie[i][j].ejecutaMovimiento(new Casilla(i,j), this, hechos);
					if(c!=null)supBool[c.getFila()][c.getCol()] = false;
				}
			}
		}
		//Meto el tablero actualizado.
		hechos.append("\n");
		
		return hechos;
	}
	
	//=================================================================
	
	/**Devuelve el numero de celulas que hay en la superficie*/
	public int cuentaCelulas()
	{
		return this.dondeHayCelulas().length;
	}
	
	
	//=================================================================
	
	
	/**Mete una celula en una posición, devuelve si ha funcionado o no.*/
	public boolean meterCelula(Celula celula, Casilla pos, StringBuffer datos)
	{
		boolean comp = false;
		try
		{
			if(this.checkCasillaVacia(pos))
			{
				this.superficie[pos.getFila()][pos.getCol()] = celula;
				comp=true;
			}
			else
			{
				throw new MeterCelulaException("\nERROR. Casilla no vacía o fuera de la superficie. No se puede añadir una nueva celula.\n");
			}
		}
		catch(NullPointerException e)
		{
			try {
				throw new IndicesFueraDeRango("\nERROR. Indica una posicion valida\n");
			} catch (IndicesFueraDeRango noSePuede) {
				datos.append(noSePuede.getMessage());
			}
		} 
		catch (MeterCelulaException e) 
		{
			datos.append(e.getMessage());
		}
		
		return comp;
	}
	
	//=================================================================
	
	/**Obtiene el numero de casillas de la superficie*/
	public Casilla getCasillas()
	{
		return casillas;
	}
	
	//=================================================================

	/**Devuelve la superficie*/
	public Celula[][] getSuperficie()
	{
		return superficie;
	}
	
	//=================================================================
	
	/**Devuelve 0 si no hay nada, -1 si es simple y 1 si es compleja*/
	public int 	queCelulaHay(Casilla pos)
	{
		
		int queHay = ES_VACIA;
		
		//si no esta vacia miro que celula es
		if(superficie[pos.getFila()][pos.getCol()]!=null)
		{
			if(superficie[pos.getFila()][pos.getCol()].soyComestible()) queHay = ES_SIMPLE;
			else if(!superficie[pos.getFila()][pos.getCol()].soyComestible()) queHay = ES_COMPLEJA;
		}
		
		return queHay;
	
	}
	
	//=================================================================
	
	/**Vacía el tablero*/
	public void reset()
	{
		for(int i=0; i<this.casillas.getFila(); i++)
			for(int j=0; j<this.casillas.getCol(); j++)
				this.superficie[i][j] = null;
	}
	
	//=================================================================
	
	/**Destruye una célula en una posición */
	public boolean destruyeCelula(Casilla pos)
	{
		boolean exito = false;
		
		// Compruebo que la posicion esta dentro del tablero y que la posicion no esta vacia
		if (pos.getFila() < this.casillas.getFila() && pos.getCol() < this.casillas.getCol() && !checkCasillaVacia(pos))
		{
			setCasillaVacia(pos);
			exito = true;
		}
		return exito;
	}
	
	//=================================================================
	
	/**Devuelve si la casilla esta vacia o no */
	public boolean checkCasillaVacia(Casilla pos)
	{
		boolean comp = false;
		try
		{
			comp = (superficie[pos.getFila()][pos.getCol()] == null);
		}catch(ArrayIndexOutOfBoundsException e){comp = false;}
		return comp;
	}
	
	//=================================================================
	
	/**Pone a null una posición*/
	public void setCasillaVacia (Casilla pos)
	{
		this.superficie[pos.getFila()][pos.getCol()] = null;
	}
	
	//=================================================================
	
	/**Mueve una celula de una posición a otra*/
	public void mueveCelula(Casilla pos, Casilla nPos)
	{
		this.superficie[nPos.getFila()][nPos.getCol()] = this.superficie[pos.getFila()][pos.getCol()];
		this.superficie[pos.getFila()][pos.getCol()]   = null;
	}
	
	//=================================================================

	/**Devuelve un array con la posicion donde está cada célula*/
	public Casilla[] dondeHayCelulas()
	{
		/*NOTA:
		 * 	Esta función la usábamos anteriormente para que en this.evoluciona(), en vez de hacer un 
		 *  recorrido de una matriz, hiciese un recorrido unicamente hacia las posiciones de cada célula
		 *  pero por falta de tiempo, y por complejitud, se ha vuelto a la idea de la matriz de booleanos
		 *  y este método se ha mantenido y usado para otros métodos como "this.cuentaCelulas" 
		 */
		ArrayList <Casilla> posicionesLibres = new ArrayList<Casilla>();
		
		//Hago un recorrido de la superficie, y guardo en el arraylist cada posicion con celula
		for(int i=0; i<this.casillas.getFila(); i++)
		{
			for(int j=0; j<this.casillas.getCol(); j++)
			{
				//Si hay celula...
				if(superficie[i][j]!=null)
					//Añado...
					posicionesLibres.add(new Casilla(i,j));
			}
		}
		
		//Transformamos el arrayList a array
		return posicionesLibres.toArray(new Casilla[posicionesLibres.size()]);
		
	}
	
	//=================================================================
	
	/**Devuelve una copia de la superficie(En booleano), en la que indica si en una posicion hay celula o no*/
	public boolean[][] supBooleana()
	{
		boolean [][]supBooleana = new boolean[this.casillas.getFila()][this.casillas.getCol()];
		
		for(int i=0; i<this.casillas.getFila(); i++)
		{
			for(int j=0; j<this.casillas.getCol(); j++)
			{
				if(this.checkCasillaVacia(new Casilla(i,j)))
					supBooleana[i][j] = false;
				else
					supBooleana[i][j] = true;
			}
		}
		
		return supBooleana;
	}
	
	//=================================================================
	
	/**Devuelve un StringBuffer representado la superficie*/
	public StringBuffer toStringBuffer()
	{
		StringBuffer stringSuperficie = new StringBuffer("");
		for(int i = 0; i < this.casillas.getFila(); i++)
		{
			for(int j = 0; j < this.casillas.getCol(); j++)
			{
				if(this.queCelulaHay(new Casilla(i,j)) != ES_VACIA)
					stringSuperficie.append(" " + this.superficie[i][j].toStringBuffer() + " ");
				else
					stringSuperficie.append(" - ");
			}
			stringSuperficie.append("\n");
		}
		return stringSuperficie;
	}
	
	/**Guarda la superficie*/
	public void guardar(PrintWriter pinto)
	{

		Casilla []pos = dondeHayCelulas();
		
		pinto.println(this.casillas.getFila());
		pinto.println(this.casillas.getCol() );
		
		
		for(int i=0; i< pos.length; i++)
		{
			this.superficie[pos[i].getFila()][pos[i].getCol()].guardarCelula(pinto, pos[i]);
		}

	}
	
	/**Carga la superficie*/
	public boolean cargar(Scanner sc)
	{
		boolean comp = true;
		String linea = null;
		
		try
		{
			while((linea = sc.nextLine()) != null)
			{
				String []lineas = linea.split(" ");
				if(lineas.length == 5 || lineas.length == 4)
				{
					
					int fila = Integer.parseInt(lineas[0]);
					int col  = Integer.parseInt(lineas[1]);
					
					if(lineas[2].equalsIgnoreCase("simple") && lineas.length == 5)
					{
						this.superficie[fila][col] = new CelulaSimple();
						comp = this.superficie[fila][col].cargarCelula(lineas[3] + " " + lineas[4]);
					}
					else if(lineas[2].equalsIgnoreCase("compleja") && lineas.length == 4)
					{
						this.superficie[fila][col] = new CelulaCompleja();
						comp = this.superficie[fila][col].cargarCelula(lineas[3]);
					}
					else {throw new FicheroIncompleto("Error de lectura de tipo de celula. No se reconoce el tipo (simple/compleja...). Archivo mal escrito.");}
					
				}
				else{comp = false;}
				if(comp == false){throw new FicheroIncompleto("Error al leer los valores de la celula. Archivo mal escrito"); }
				
			}
		} 
		catch (NumberFormatException  e)  
		{
			try {throw new FormatoNumeroIncorrecto("Error al leer el archivo. Formato de numero incorrectos.");}
			catch (FormatoNumeroIncorrecto e1) {System.out.println(e1.getMessage());}
			comp = false;
		}
		catch(NullPointerException e)
		{
			try{throw new IndicesFueraDeRango("ERROR: El archivo no está adaptado a la lógica del juego");}
			catch(IndicesFueraDeRango e1){System.out.println(e1.getMessage());}
			comp = false;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			try{throw new IndicesFueraDeRango("ERROR: El archivo no está adaptado a la lógica del juego");}
			catch(IndicesFueraDeRango e1){System.out.println(e1.getMessage());}
			comp = false;
		}
		catch (FicheroIncompleto e){System.out.println(e.getMessage());}
		catch (NoSuchElementException e){}
		return comp;
	}
}

