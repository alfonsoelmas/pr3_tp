package entradaSalida;

import java.util.Scanner;

public class Entrada {

	/**Constructora (vacia)*/
	public Entrada()
	{
		
	}
	
	/**Lee datos de entrada desde el System.in. (Similar a un nextLine)*/
	public String leo()
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String devuelvo = null;
		
		
		devuelvo = scanner.nextLine();

		return devuelvo;
	}
	

}
