package entradaSalida;

public class Salida {
	
	/**Constructora vacía*/
	public Salida()
	{
		
	}
	
	/**Pinta el string en consola. Sin salto de linea.*/
	public void pinta(String mensaje)
	{	
		System.out.print(mensaje);	
	}
	
	
	
	
	public void pinta(StringBuffer mensaje)
	{	
		System.out.print(mensaje);	
	}
	
	/**Pinta el string en consola. Con salto de linea.*/
	public void pintaln(String mensaje)
	{	
		System.out.println(mensaje);	
	}
	
	public void pintaln(StringBuffer mensaje)
	{	
		System.out.println(mensaje);	
	}

}
