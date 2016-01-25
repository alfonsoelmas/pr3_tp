package logica;


import java.io.PrintWriter;

import celulas.CelulaCompleja;
import celulas.CelulaSimple;
import entradaSalida.Entrada;
import entradaSalida.Salida;
import excepciones.PalabraIncorrecta;



public class MundoComplejo extends Mundo{

	//======================
	//		ATRIBUTOS	
	//======================
	
	/**	Numero de celulas complejas al comenzar el mundo*/
	private int numCelComplejas;
	
	/**	Numero de celulas simples al comenzar el mundo*/
	private int numCelSimples;
	
	/**Constructora basica. Pone a cero y a null la superficie*/
	public MundoComplejo()
	{
		super();
		this.numCelComplejas = 0;
		this.numCelSimples = 0;
	}
	
	/**Constructora que define el tamaño del mundo, y añade un numero de celulas simples y complejas a este */
	public MundoComplejo(Casilla casillas, int cSimples, int cComplejas)
	{
		super(casillas);
		this.numCelComplejas = cComplejas;
		this.numCelSimples   = cSimples;
		this.inicializaMundo();
	}
	
	/**Inicializa el mundo creando una superficie y metiendo celulas correspondientes.*/
	@Override
	public void inicializaMundo()
	{
	
		
		this.superficie = new Superficie(this.casillas);
		
		if(this.numCelSimples+this.numCelComplejas < this.casillas.getCol()*this.casillas.getFila())
		{
			
			//Creamos las células simples...
			for(int i=0; i < this.numCelSimples; i++)
			{
			
				int numFil, numCol;
				do
				{
					numFil = (int)(Math.random()* this.casillas.getFila()); 	//desde 0 hasta Numfilas
					numCol = (int)(Math.random()* this.casillas.getCol());		//desde 0 hasta NumColum
					
				}while(!this.superficie.checkCasillaVacia(new Casilla(numFil,numCol)) && this.superficie.cuentaCelulas() < this.casillas.getFila()*this.casillas.getCol());
				superficie.meterCelula(new CelulaSimple(), new Casilla(numFil,numCol));
			}
			
			//Creamos las células complejas...
			for(int i=0; i < this.numCelComplejas; i++)
			{
			
				int numFil, numCol;
				do
				{
					numFil = (int)(Math.random()* this.casillas.getFila()); 	//desde 0 hasta Numfilas
					numCol = (int)(Math.random()* this.casillas.getCol());		//desde 0 hasta NumColum
					
				}while(!this.superficie.checkCasillaVacia(new Casilla(numFil,numCol)) && this.superficie.cuentaCelulas() < this.casillas.getFila()*this.casillas.getCol());
				superficie.meterCelula(new CelulaCompleja(), new Casilla(numFil,numCol));
			}
		}else{System.out.println("Numero de celulas excedido. No se puede inicializar el mundo. Se ha creado un mundo vacio");};

	}

	/**Guarda el mundo*/
	@Override
	public void guardar(PrintWriter escribo) 
	{
		
		escribo.println("complejo");
		
		this.superficie.guardar(escribo);
	}

	/**Crea una celula simple.*/
	@Override
	public StringBuffer crearCelula(Casilla pos)
	{
		
		StringBuffer datos = new StringBuffer("");
		Salida salida = new Salida();
		salida.pinta("SIMPLE O COMPLEJA: ");
		Entrada entrada = new Entrada();
		String tipo  = entrada.leo();
		try
		{
			if(tipo.equalsIgnoreCase("SIMPLE"))
			{
				this.superficie.meterCelula(new CelulaSimple(), pos);
				datos.append("Se ha creado una celula simple en" + pos.posToStringBuffer());
			}
			else if(tipo.equalsIgnoreCase("COMPLEJA"))
			{
				this.superficie.meterCelula(new CelulaCompleja(), pos);
				datos.append("Se ha creado una celula compleja en" + pos.posToStringBuffer());
			}
			else
			{
				throw new PalabraIncorrecta("ERROR. ¿Celula SIMPLE O COMPLEJA?");
			}
		} catch (PalabraIncorrecta e) {
			datos = new StringBuffer(e.getMessage());
		}
		
		return datos;

	}

}
