package logica;


import java.io.PrintWriter;

import celulas.CelulaSimple;
import excepciones.IndicesFueraDeRango;
import excepciones.MeterCelulaException;
import excepciones.NumeroCelulasException;

public class MundoSimple extends Mundo{

	//==========================
	//		  ATRIBUTOS
	//==========================
	
	/**Numero de celulas simples que contendrá el mundo inicialmente*/
	private int numCelSimples;
	
	//==========================
	//		CONSTRUCTORA
	//==========================
	
	/**Constructora basica. Pone a cero y a null los atributos.*/
	public MundoSimple()
	{
		super();
		this.numCelSimples = 0;	
	}
	
	/**Constructora que inicializa el mundo con unas dimensiones y un numero concreto de celulas simples*/
	public MundoSimple(Casilla casillas, int cSimples)
	{
		super(casillas);
		this.numCelSimples = cSimples;
		this.inicializaMundo();
	}
	
	

	//=========================
	//	METODOS SOBREESCRITOS
	//=========================
	
	/**Inicializa el mundo con unas dimensiones concretas y un numero de celulas determinado*/
	@Override
	public void inicializaMundo()
	{
		this.superficie = new Superficie(this.casillas);
		
		try
		{
			superficie = new Superficie(this.casillas);
			if(this.numCelSimples < this.casillas.getCol()*this.casillas.getFila())
			{
				for(int i=0; i < numCelSimples; i++)
				{
				
					int numFil, numCol;
					do
					{
						numFil = (int)(Math.random()* this.casillas.getFila()); 	//desde 0 hasta Numfilas
						numCol = (int)(Math.random()* this.casillas.getCol() );		//desde 0 hasta NumColum
						
					}while(superficie.queCelulaHay(new Casilla(numFil,numCol)) != Superficie.ES_VACIA);
					
					superficie.meterCelula(new CelulaSimple(), new Casilla(numFil, numCol));
		
				}
			}
			else
			{
				throw new NumeroCelulasException("Numero de celulas excedido. No se puede inicializar el mundo.");
			}
		}catch(NumeroCelulasException e)
		{
			System.out.println(e.getMessage());
		}
		
	}

	
	/**Guarda el mundo*/
	@Override
	public void guardar(PrintWriter escribo) {
		
		escribo.println("simple");
		
		this.superficie.guardar(escribo);

	}

	/**Crea una celula. Si se intenta crear una celula compleja lanza una excepcion.*/
	@Override
	public StringBuffer crearCelula(Casilla pos)
	{
		StringBuffer datos = new StringBuffer("");
		//Si la casilla está vacía, y es una célula simple...
		try
		{
			if(this.superficie.checkCasillaVacia(pos))
			{
				this.superficie.meterCelula(new CelulaSimple(), pos);
				datos.append("Se ha creado una celula simple en" + pos.posToStringBuffer());
			}
			else
			{
				throw new MeterCelulaException("La casilla no está vacía.");
			}
		}
		catch(NullPointerException | ArrayIndexOutOfBoundsException e)
		{
			try {
				throw new IndicesFueraDeRango("Posicion fuera del mundo.");
			} catch (IndicesFueraDeRango e1) {
				System.out.println(e1.getMessage());
			}
		} catch (MeterCelulaException e) {
			System.out.println(e.getMessage());
			datos = null;
		}
		
		return datos;
	}



}
