package logica;



public class Casilla {
	
	//==================================================================================
	//Atributos
	//==================================================================================
	
	/**Define la fila de la casilla*/
	private int fila;
	/**Define la columna de la casilla*/
	private int col;
	
	//==================================================================================
	//Constructora
	//==================================================================================
	
	/**Constructora de Casilla, crea una casilla dada una fila y una posición de entrada*/
	public Casilla(int fila, int col)
	{
		this.fila = fila;
		this.col  = col;
	}
	
	//==================================================================================
	//Metodos
	//==================================================================================
	
	/** Modificamos la Casilla llamando al setFila y setCol */
	public void setCasilla(int fila, int columna)
	{
		setFila(fila);
		setCol(columna);
	}
	
	
	//==================================================================================
	
	/** Devolvemos el valor del atributo casilla permitiendo al usuario acceder al atributo	 */
	public Casilla getCasilla()
	{
		Casilla devuelvo = new Casilla(this.fila,this.col);
		
		return devuelvo;
	}

	//==================================================================================
	
	/** Devolvemos el valor del atributo fila permitindo al usuario acceder al atributo	 */
	public int getFila() 
	{
		return fila;
	}

	//==================================================================================
	
	/** Modificamos el atributo fila */
	public void setFila(int fila)
	{
		this.fila = fila;
	}
	
	//==================================================================================

	/** Devolvemos el valor del atributo permitindo al usuario acceder al atributo */
	public int getCol() 
	{
		return col;
	}

	//==================================================================================
	
	/** Modificamos el atributo col	*/
	public void setCol(int col) 
	{
		this.col = col;
	}
	
	//==================================================================================
	
	/**Devuelve un StringBuffer de la posición*/
	public StringBuffer posToStringBuffer()
	{
		StringBuffer datos = new StringBuffer("");
		datos.append("(" + this.fila +", " + this.col + ")");
		
		return datos;
	}
	
	public String toString()
	{
		return this.fila + " " + this.col;
	}
	//==================================================================================
	
	/**Devuelve true si la posición es la misma que la que le pasa por parámetro*/
	public boolean es_igual(Casilla casilla)
	{
		boolean comp = false;
		if(this.fila == casilla.getFila() && this.col == casilla.getCol())
			comp = true;
		return comp;
	}
	
}

