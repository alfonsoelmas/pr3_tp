package main;

import Controlador.Controlador;
import entradaSalida.Entrada;

public class Main {

	public static void main(String [] args)
	{
		Entrada in = new Entrada();
		Controlador controlador = new Controlador(in);
		controlador.ejecutaSimulacion();
	}
}
