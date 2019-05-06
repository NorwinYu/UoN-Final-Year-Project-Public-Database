package ejecucion;

import controlador.Controlador;
import modelo.Modelo;
import vista.Vista;
import modelo.Punto;

public class Ejecucion {

	public static void main(String[] args) {

		Modelo modelo = new Modelo();
		Vista vista = new Vista();
		Controlador controlador = new Controlador(vista, modelo);
		Punto punto = new Punto();

		modelo.conexion.conectarBase();

	}

}
