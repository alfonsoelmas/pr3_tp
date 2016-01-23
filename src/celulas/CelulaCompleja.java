package celulas;


import java.io.PrintWriter;
import java.util.ArrayList;

import logica.Casilla;
import logica.Superficie;

public class CelulaCompleja implements Celula{


	
	//========================
	//		ATRIBUTOS
	//========================
	
	/**Contiene el numero de celulas que lleva comidas la cellula compleja*/
	private int celulasComidas;
	
	//========================
	//		CONSTANTES
	//========================
	
	
	/**Numero de celulas máximas que puede comer una celula compleja.*/
	private static final int MAX_COMER = 4;
	
	
	//========================
	//		CONSTRUCTORA
	//========================
	
	/**Constructora de celula compleja. Inicializa a 0 el numero de celulas comidas.*/
	public CelulaCompleja()
	{
		this.celulasComidas = 0;
	}
	
	//========================
	//	METODOS SOBREESCRITOS
	//========================
	
	/**Devuelve si la célula es comestible o no*/
	@Override
	public boolean soyComestible() {
		return false;
	}
	
	/**Devuelve una representación de la célula compleja*/
	@Override
	public String toString() {
		return "*";
	}

	/**Devuelve una representación de la célula compleja en StringBuffer*/
	@Override
	public StringBuffer toStringBuffer() {
		return new StringBuffer("*");
	}

	/**Realiza el movimiento de la celula compleja, con su correspondiente comportamiento*/
	@Override
	public Casilla ejecutaMovimiento(Casilla casilla, Superficie superficie, StringBuffer cad){
		
		Casilla devuelvoLado = null;
		
		Casilla[] casillasLibres = this.recorroLados(casilla, superficie);
		

		//Sino... Si me puedo mover...
		if(casillasLibres.length > 0)
		{
			//Me muevo
			
			devuelvoLado = this.eligeUnaCasilla(casillasLibres);
			int queHay = superficie.queCelulaHay(devuelvoLado);
			superficie.mueveCelula(casilla, devuelvoLado);
			cad.append("Celula compleja en " + casilla.posToStringBuffer()+ " se mueve a " + devuelvoLado.posToStringBuffer());
			//y si a donde me he movido, he comido...
			if(queHay == Superficie.ES_SIMPLE)
			{	
				this.celulasComidas++;
				cad.append("-- COME --");
			}
			else
			{
				cad.append("-- NO COME --");
			}
		}
		//Si he llegado a MAX_COMER...
		if (this.celulasComidas >= MAX_COMER)
		{
			//Me muero
			superficie.destruyeCelula(devuelvoLado);
			cad.append("\nExplota la celula compleja en " + casilla.posToStringBuffer());

		}
		cad.append("\n");
		//El lado que devuelve es el que habrá que intentar eliminar del array.
		return devuelvoLado;
	}
	
	/**Recorre los lados de la superficie y devuelve las casillas a las que puede moverse la celula compleja*/
	public Casilla[] recorroLados(Casilla casilla, Superficie superficie) {

		ArrayList <Casilla> posicionesLibres = new ArrayList<Casilla>();
		for(int i=0; i<superficie.getCasillas().getFila(); i++)
		{
			for(int j=0; j<superficie.getCasillas().getCol(); j++)
			{
				//Si esta dentro de la superficie, y la celula no es compleja...
				if( (!this.estoyFuera(new Casilla(i,j), superficie.getCasillas())) && superficie.queCelulaHay(new Casilla(i,j)) != Superficie.ES_COMPLEJA)
					//Añado la posición a la que opta a moverse
					posicionesLibres.add(new Casilla(i,j));
			}
		}
		
		//Transformamos el arrayList a array
		Casilla [] posiciones = posicionesLibres.toArray(new Casilla[posicionesLibres.size()]);
		//Si no hay ninguna libre, devolverá un array de tamaño 0.
		return posiciones;
	}

	/**Dada una posicion y una dimension, comprueba si la posicion esta dentro de la dimension*/
	@Override
	public boolean estoyFuera(Casilla pos, Casilla limites) {
		boolean comp = true;
		
		if(pos.getFila() >= 0 && pos.getFila() < limites.getFila() && pos.getCol() >= 0 && pos.getCol() < limites.getCol())
			comp = false;
		
		return comp;
	}

	/**Dado un array de casillas, elige una posicion del array de forma aleatoria y devuelve la casilla*/
	@Override
	public Casilla eligeUnaCasilla(Casilla[] casillas) {
		int aleatorio = (int)(Math.random() * (casillas.length)); 	//Elige una posición desde 0 hasta casillas.length
		return casillas[aleatorio];	
	}
	
	/**Guarda una celula*/
	@Override
	public void guardarCelula(PrintWriter pinto, Casilla pos) {
	
		pinto.println(pos.getFila() + " " + pos.getCol() + " compleja " + this.celulasComidas);
	}

	/**Carga los datos de una celula*/
	@Override
	public boolean cargarCelula(String leo) {
		
		boolean comp = true;
		
		String []datos = leo.split(" ");
		try {
			int cuantas = Integer.parseInt(datos[0]);
			if(MAX_COMER > cuantas)
			{
				this.celulasComidas = cuantas;
			}else{comp = false;}
			
		} catch (NumberFormatException e) {comp = false;}
		
		return comp;

	}


}
