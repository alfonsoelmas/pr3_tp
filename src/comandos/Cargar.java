package comandos;


import Controlador.Controlador;

public class Cargar implements Comandos {

	/**Atributo ruta. Almacena el nombre del archivo a cargar*/
	private String ruta;
	
	/**Constructora. Inicia una instancia, deficiendo el nombre del archivo desde el que cargar*/
	public Cargar(String ruta)
	{
		this.ruta = ruta;
	}
	
	/**Ejecuta el comando Cargar */
	@Override
	public StringBuffer ejecuta(Controlador controlador)
	{
		return controlador.cargar(this.ruta);
	}
	
	/**Parsea el comando. Lo devuelve si lo interpreta bien*/
	@Override
	public Comandos parsea(String[] cadenaComando)
	{
		
		Comandos comando = null;
		
		if(cadenaComando.length == 2 && cadenaComando[0].equalsIgnoreCase("CARGAR")) 
		{
				comando = new Cargar(cadenaComando[1]);
		}
		
		
		return comando;
	}

	/**Devuelve un String con la ayuda del comando*/
	@Override
	public String textoAyuda()
	{
		return "CARGAR nombre: Carga una partida";
	}

}
