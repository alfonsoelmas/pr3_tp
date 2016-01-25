package celulas;

import java.io.PrintWriter;
import java.util.ArrayList;

import logica.Casilla;
import logica.Superficie;

public class CelulaSimple implements Celula{


	
	//=======================
	//		ATRIBUTOS
	//=======================
	
	/**Almacena los pasos que una celula simple lleva sin moverse*/
	private int pasosSinMov;
	/**Almacena cada paso que la celula simple realiza*/
	private int pasosTot;
	
	//=======================
	//		CONSTANTES
	//=======================
	
	/**Define el valor del número máximo de pasos sin mover que puede tener la célula simple*/
	public static final int MAX_PASOS_SIN_MOVER = 3;
	/**Define el valor del número máximo de pasos que debe dar para que la célula se reproduzca*/
	public static final int PASOS_REPRODUCCION  = 3;
	
	//=======================
	//		CONSTRUCTORA
	//=======================
	
	public CelulaSimple()
	{
		super();
		this.pasosSinMov = 0;
		this.pasosTot = 0;
	}
	
	//=======================
	//	METODOS SOBREESCRITOS
	//=======================
	

	/**Devuelve una representación.*/
	@Override
	public String toString() {
		return "X";
	}

	@Override
	public StringBuffer toStringBuffer() {
		return new StringBuffer("X");
	}

	@Override
	public Casilla ejecutaMovimiento(Casilla casilla, Superficie superficie, StringBuffer cad)
	{
		Casilla devuelvoLado = null;	
		// Si llevo muchos pasos sin mover...
		if(this.pasosSinMov >= MAX_PASOS_SIN_MOVER)
		{
			// me muero
			superficie.destruyeCelula(casilla);
			cad.append("Se ha muerto la celula de la posicion " + casilla.posToStringBuffer()+ "por inactividad.");
			
		}
		// Si llego a los pasos_reproduccion...
		else if(this.pasosTot >= PASOS_REPRODUCCION)
		{
			// miro si puedo moverme... 
			Casilla[] posicionesLibres = this.recorroLados(casilla, superficie);
			if (posicionesLibres.length>0)
			{
				// si me puedo mover me reproduzco...
				devuelvoLado = this.eligeUnaCasilla(posicionesLibres);
				this.reproducete(casilla, superficie, cad, devuelvoLado);
			}
			else
			{
				// sino me muero.
				superficie.destruyeCelula(casilla);		
				cad.append("Se ha muerto la celula de la pos" + casilla.posToStringBuffer() + "por  no poder reproducirse");
			}
		}
		// En otro caso...
		else
		{
			// miro si puedo moverme...
			Casilla[] posicionesLibres = this.recorroLados(casilla, superficie);
			if (posicionesLibres.length>0)
			{
				// si puedo moverme me muevo
				devuelvoLado = this.eligeUnaCasilla(posicionesLibres);
				this.muevete(casilla, superficie, cad, devuelvoLado);
			}
			else
			{
				// sino aumento los pasos sin mover
				cad.append("La celula" + casilla.posToStringBuffer() + "no se ha podido mover.");
				this.pasosSinMov++;
			}
		}
		cad.append("\n");
		
		return devuelvoLado;
	}
	
	//==================================================================================

	public Casilla[] recorroLados(Casilla casilla, Superficie superficie)
	{


		ArrayList <Casilla> posicionesLibres = new ArrayList<Casilla>();
			
		for(int i=casilla.getFila()-1; i<=casilla.getFila()+1; i++)
		{
			for(int j=casilla.getCol()-1; j<=casilla.getCol()+1; j++)
			{
				//Si esta dentro de la superficie, y no hay algun tipo de celula...
				if(!this.estoyFuera(new Casilla(i,j), superficie.getCasillas()) && superficie.checkCasillaVacia(new Casilla(i,j)))
					posicionesLibres.add(new Casilla(i,j));
			}
		}
			
		//Transformamos el arrayList a array
		Casilla [] posiciones = posicionesLibres.toArray(new Casilla[posicionesLibres.size()]);
		//Si no hay ninguna libre, devolverá un array de tamaño 0.
		return posiciones;
	}

	/**Reproduce una celula de una casilla, en una superficie, y se mueve a devuelvoLado. Devuelve lo sucedido */
	private void reproducete(Casilla casilla, Superficie superficie, StringBuffer cad, Casilla devuelvoLado)
	{
		superficie.mueveCelula(casilla, devuelvoLado);
		superficie.meterCelula(new CelulaSimple(), casilla, null);//En este caso nunca nos va a dar error. por eso el stringBuffer lo pongo a null
		this.resetea();
		cad.append("Se ha creado una nueva celula en " + casilla.posToStringBuffer() + "cuyo padre está en " + devuelvoLado.posToStringBuffer());
	
	}
		
	//==================================================================================
		
		/**Mueve una celula de una casilla, en una superficie a devuelvoLado. Devuelve lo sucedido*/
	private void muevete(Casilla casilla, Superficie superficie, StringBuffer cad, Casilla devuelvoLado)
	{
		superficie.mueveCelula(casilla, devuelvoLado);
		cad.append("Movimiento de " + casilla.posToStringBuffer() + " a " + devuelvoLado.posToStringBuffer());
		this.pasosTot++;
	}
		
	/**Resetea la celula, poniendo los atributos a 0*/
	public void resetea()
	{
		this.pasosSinMov 	= 0;
		this.pasosTot 		= 0;
	}

	@Override
	public boolean estoyFuera(Casilla pos, Casilla limites) 
	{
		boolean comp = true;
		
		if(pos.getFila() >= 0 && pos.getFila() < limites.getFila() && pos.getCol() >= 0 && pos.getCol() < limites.getCol())
			comp = false;
		
		return comp;
	}

	@Override
	public Casilla eligeUnaCasilla(Casilla[] casillas) 
	{
		int aleatorio = (int)(Math.random() * (casillas.length)); 	//Elige una posición desde 0 hasta casillas.length
		return casillas[aleatorio];
	}
	
	@Override
	public boolean cargarCelula(String leo) 
	{

		boolean comp = true;
		String []datos = leo.split(" ");
		try {
			int pasT = Integer.parseInt(datos[0]);
			int pasS = Integer.parseInt(datos[1]);
			if(pasT < PASOS_REPRODUCCION && pasS < MAX_PASOS_SIN_MOVER)
			{
				this.pasosTot = pasT;
				this.pasosSinMov = pasS;
			}else{comp=false;}
			
		} catch (NumberFormatException e) {comp = false;}
		
		return comp;
	}

	@Override
	public void guardarCelula(PrintWriter pinto, Casilla pos) 
	{

		pinto.println(pos.getFila() + " " + pos.getCol() + " simple " + this.pasosTot + " " + this.pasosSinMov);

	}

	@Override
	public boolean soyComestible() 
	{
		return true;
	}

	

	


}
