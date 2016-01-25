package comandos;

import Controlador.Controlador;

import logica.Casilla;
import logica.Superficie;

public class EliminarCelula implements Comandos{
	
	//=======================================================
	//					Atributos
	//=======================================================
	
	/**Contiene la posicion donde eliminar la celula*/
	private Casilla posicion;
	
	//=======================================================
	//					Constructora
	//=======================================================
	
	/**Crea una instancia de la clase. Inicializa el atributo posición.*/
	public EliminarCelula(Casilla pos)
	{
		this.posicion = pos;
	}
	
	//=======================================================
	//					Metodos
	//=======================================================
	
	
	/** Ejecuta el comando EliminarCelula */
	
	@Override
	public StringBuffer ejecuta(Controlador controlador) 
	{
		StringBuffer texto = new StringBuffer("");

			if( (controlador.queCelulaEs(this.posicion) != Superficie.ES_VACIA))
			{
				controlador.eliminarCelula(this.posicion);
				texto.append("Se ha eliminado la celula de la posicion" + this.posicion.posToStringBuffer() + "\n");
				texto.append(controlador.superficieToStringBuffer());
			}
			else
				texto.append("No se ha podido eliminar nada");

		return texto;
	}

	/** Dado una cadenaComando, comprueba que se trata del comando EliminarCelula, ignorando las
	 *  mayusculas y minusculas. A su vez, devuelve el comando o null en caso contrario	 */
	
	@Override
	public Comandos parsea(String[] cadenaComando) 
	{
		
		// TODO Auto-generated method stub
		Comandos comando= null;
		//controlamos la excepcion...

			if(cadenaComando.length == 3 && cadenaComando[0].equalsIgnoreCase("ELIMINARCELULA")) 
			{
					Casilla pos = new Casilla(Integer.parseInt(cadenaComando[1]),Integer.parseInt(cadenaComando[2]));
					comando = new EliminarCelula(pos);
			}

		
		return comando;
	}
	
	/** Devuelve un string indicando lo que hace el comando EliminarCelula */

	@Override
	public String textoAyuda() 
	{
		return "ELIMINARCELULA f c: Elimina una celula en fila columna";
	}
	
}
