package celulas;

import java.io.PrintWriter;

import logica.Casilla;
import logica.Superficie;

public interface Celula {

	
	//=======================
	//	METODOS ABSTRACTOS
	//=======================
	
	/**Devuelve si la celula es comestible o no*/
	public abstract boolean soyComestible();
	/**Devuelve la representación de la celula en un String*/
	public abstract String toString();
	/**Devuelve la representación de la celula en un StringBuffer*/
	public abstract StringBuffer toStringBuffer();
	/**Ejecuta el movimiento de la celula y su comportamiento correspondiente*/
	public abstract Casilla ejecutaMovimiento(Casilla casilla, Superficie superficie, StringBuffer cad);
	/**Dada una posicion y una dimension, devuelve true si la posicion se encuentra dentro de la dimension*/
	public abstract boolean estoyFuera(Casilla pos, Casilla limites);
	/**Elige un lado de una matriz de lados*/
	public abstract Casilla eligeUnaCasilla(Casilla[] casillas);
	/**Lee una célula de un fichero, se le pasa un flujo abierto, y devuelve la posición y la célula leida*/
	public abstract boolean cargarCelula(String leo);
	/**Guarda una célula en un fichero*/
	public abstract void guardarCelula(PrintWriter pinto, Casilla pos);
	/**Decuelve las posiciones a las que opta a moverse ese tipo de celulas*/
	public abstract Casilla[] recorroLados(Casilla pos, Superficie superficie);
}
