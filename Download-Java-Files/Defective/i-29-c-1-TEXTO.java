package Proyecto_Segundo_Trimestre;

import java.util.Scanner;

public class TEXTO {

	private boolean pasar = false;
	Scanner sc = new Scanner(System.in);

	public TEXTO() {

	}

	GESTION gt = new GESTION();

	/**
	 * 
	 */
	protected TEXTO(GESTION gt) {
		this.gt = gt;
	}

	public void menu() { // Menu pricipal
		do {
			String N;
			System.out.println("Bienvenido a Eventually");
			System.out.println("Inserte '1' para acceder como desarrollador ");
			System.out.println("Inserte '2' para acceder al menu de cliente (No incluido en esta version).");
			System.out.println("Inserte 3 para salir del programa");
			N = sc.nextLine();

			switch (N) {

			case "1":
				menuDesarrollador();

				break;

			case "2":
				System.out.println("Actualmente esta opcion no esta implementada");
				System.out.println(" ");
				// Llamar a menu de clientes(No en esta version)
				break;
			case "3":
				System.out.println(" Gracias por probar la version en desarrollo de nuestro programa");
				System.out.println(" Esperamos que les haya convencido para que sigan confiando en nosotros");
				pasar = true;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;

			}

		} while (!pasar);
	}// fin metodo menu

	public void menuDesarrollador() {
		String N,nombre;
		boolean salir = true;
		boolean comprobar = true;
		int contador = 1;
		do {

			System.out.println("Inserte 1 para ver todos los eventos");
			System.out.println("Inserte 2 para comprar entradas");
			System.out.println("Inserte 3 para ver las facturas");
			System.out.println("Inserte 4 para añadir eventos al programa (Desarrollador)");
			System.out.println("Inserte 5 para editar el contenido de los eventos");
			System.out.println("Inserte 6 para borrar un evento");
			System.out.println("Inserte 7 para salir al menu anterior");
			N = sc.nextLine();

			switch (N) {

			case "1":// menu de mostrar los eventos(guia) No DONE

				menuMostrar();

				break;

			case "2":// Menu de comprar las entradas

				menuComprar();

				break;

			case "3":

				gt.imprimirFactura();

				break;

			case "4":// Crear objetos de cada clase DONE
				menuAñadir();

				break;
			case "5":

				menuEditar();

				break;

			case "6":
				do {
					System.out.println("Dame el nombre del evento");
					nombre = sc.nextLine();

					comprobar = gt.buscarEvento(nombre, comprobar);

					if (!comprobar) {

						gt.borrarEvento(nombre);

					} else if (comprobar) {
						System.out.println("El evento no existe");
						System.out.println(" ");
						
						if(contador >= 3) {
							System.out.println("Numero de intentos excedido");
							System.out.println(" ");
							break;
						}
						contador ++;
					}

				} while (comprobar);

				comprobar = true; 

				break;
				
			case "7":// salir
				salir = false;
				System.out.println("Volviendo al menu anterior");
				System.out.println(" ");
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;

			}// Fine switch
		} while (salir);
	}// Fin menuCliente

	public void menuMostrar() {
		String N;
		boolean salir = false;
		do {

			System.out.println("Inserte '1' para ver la lista de eventos programados");
			System.out.println("Inserte '2' para ver la lista de eventos de Arte");
			System.out.println("Inserte '3' para ver la lista de eventos de Cine");
			System.out.println("Inserte '4' para ver la lista de eventos de Deporte");
			System.out.println("Inserte '5' para ver la lista de eventos de Empresa");
			System.out.println("Inserte '6' para ver la lista de eventos de Moda");
			System.out.println("Inserte '7' para ver la lista de eventos de Musical");
			System.out.println("Inserte '8' para salir");
			N = sc.nextLine();

			switch (N) {

			case "1":// Mostrar todos los eventos disponibles en el momento

				gt.imprimirTodo();

				break;

			case "2":// Mostrar los eventos de Arte(Filtrar)

				gt.imprimirArte();

				break;

			case "3":// Mostrar los eventos de Cine

				gt.imprimirCine();

				break;

			case "4": // Mostrar los eventos de Deporte

				gt.imprimirdeporte();

				break;

			case "5": // Mostrar los eventos de Empresa

				gt.imprimirempresa();

				break;

			case "6": // Mostrar los eventos de Moda

				gt.imprimirmoda();

				break;

			case "7": // Mostrar los eventos de Musca llamar a metodo en gestion

				gt.imprimirmusica();

				break;

			case "8": // Salir al menu anterior
				// Cambiar variable boolean para salir del bucle
				System.out.println("Volviendo al menu anterior");
				System.out.println(" ");
				salir = true;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;

			}
		} while (!salir);

	}// Fin metodo mostrar eventos

	public void menuAñadir() {
		String N;
		boolean pasar = false;
		do {

			System.out.println("Menu añadir objetos para uso de pruebas");
			System.out.println("Inserte 1 para añadir objetos de clase Arte");
			System.out.println("Inserte 2 para añadir objetos de clase Cine");
			System.out.println("Inserte 3 para añadir objetos de clase Deporte");
			System.out.println("Inserte 4 para añadir objetos de clase Empresa");
			System.out.println("Inserte 5 para añadir objetos de clase Moda");
			System.out.println("Inserte 6 para añadir objetos de clase Musical");
			System.out.println("Inserte 7 para volver al menu anterior");
			N = sc.nextLine();
			switch (N) {

			case "1":// objetos de arte
				gt.añadirArte(recogerArte());
				break;

			case "2":// Objetos de Cine
				gt.añadirCine(recogerCine());
				break;

			case "3": // Objetos de Deporte
				gt.añadirDeporte(recogerDeporte());
				break;

			case "4": // Objetos de Empresa
				gt.añadirEmpresa(recogerEmpresa());
				break;

			case "5": // Objetos de Moda
				gt.añadirModa(recogerModa());
				break;

			case "6": // Objetos de Musical
				gt.añadirMusical(recogerMusical());
				break;

			case "7":// salir
				System.out.println("Volviendo al menu anteior");
				System.out.println(" ");
				pasar = true;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;
			}

		} while (!pasar);
	}

	public void menuComprar() {

		String N;
		boolean salir = false;
		
		do {

			System.out.println("Inserte '1' para ver la lista de eventos programados");
			System.out.println("Inserte '2' para comprar entradas");
			System.out.println("Inserte '3' para volver al menu anterior");
			N = sc.nextLine();

			switch (N) {
			case "1":
				menuMostrar();
				break;
			case "2":
				comprarEntrada();
				break;			

			case "3":
				System.out.println("Volviendo al menu anterior");
				System.out.println(" ");
				salir = true;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;
			}

		} while (!salir);
	}

	public void menuEditar() {

		String N;
		boolean salir = true;
		String nombre;
		boolean comprobar = true;
		boolean ayuda = true;
		int contador = 1;
		do {

			System.out.println("Que tipo de evento quiere modificar");
			System.out.println("1 Arte");
			System.out.println("2 Cine");
			System.out.println("3 Deporte");
			System.out.println("4 Empresa");
			System.out.println("5 Moda");
			System.out.println("6 Musical");
			System.out.println("7 Salir");
			N = sc.nextLine();
			contador = 1;
			switch (N) {

			case "1":

				do {
					System.out.println("Dame el nombre del evento");
					nombre = sc.nextLine();

					comprobar = gt.buscarEvento(nombre, comprobar);

					if (!comprobar) {
						ayuda = gt.comprobarArte(nombre, ayuda);

						menueditarArte(nombre);

					} else if (comprobar) {
						System.out.println("El evento no existe");
						System.out.println("");

						if(contador >=3) {
							System.out.println("Numero de intentos excedido");
							System.out.println(" ");
							break;
						}
						contador ++;
					}

				} while (comprobar && ayuda);

				comprobar = true;
				ayuda = true;

				break;

			case "2":
				do {
					System.out.println("Dame el nombre del evento");
					nombre = sc.nextLine();

					comprobar = gt.buscarEvento(nombre, comprobar);

					if (!comprobar) {
						ayuda = gt.comprobarCine(nombre, ayuda);

						menueditarCine(nombre);

					} else if (comprobar) {
						System.out.println("El evento no existe");
						System.out.println("");
						
						if(contador >=3) {
							System.out.println("Numero de intentos excedido");
							System.out.println(" ");
							break;
						}
						contador ++;
					}

				} while (comprobar && ayuda);

				comprobar = true;
				ayuda = true;
				break;

			case "3":
				do {
					System.out.println("Dame el nombre del evento");
					nombre = sc.nextLine();

					comprobar = gt.buscarEvento(nombre, comprobar);

					if (!comprobar) {
						ayuda = gt.comprobarDeporte(nombre, ayuda);

						menueditarDeporte(nombre);

					} else if (comprobar) {
						System.out.println("El evento no existe");
						System.out.println("");
						
						if(contador >=3) {
							System.out.println("Numero de intentos excedido");
							System.out.println(" ");
							break;
						}
						contador ++;
					}

				} while (comprobar && ayuda);

				comprobar = true;
				ayuda = true;
				break;

			case "4":
				do {
					System.out.println("Dame el nombre del evento");
					nombre = sc.nextLine();

					comprobar = gt.buscarEvento(nombre, comprobar);

					if (!comprobar) {
						ayuda = gt.comprobarEmpresa(nombre, ayuda);

						menueditarEmpresa(nombre);

					} else if (comprobar) {
						System.out.println("El evento no existe");
						System.out.println("");
						
						if(contador >=3) {
							System.out.println("Numero de intentos excedido");
							System.out.println(" ");
							break;
						}
						contador ++;
					}

				} while (comprobar && ayuda);

				comprobar = true;
				ayuda = true;
				break;

			case "5":
				do {
					System.out.println("Dame el nombre del evento");
					nombre = sc.nextLine();

					comprobar = gt.buscarEvento(nombre, comprobar);

					if (!comprobar) {
						ayuda = gt.comprobarModa(nombre, ayuda);

						menueditarModa(nombre);

					} else if (comprobar) {
						System.out.println("El evento no existe");
						System.out.println("");
						
						if(contador >=3) {
							System.out.println("Numero de intentos excedido");
							System.out.println(" ");
							break;
						}
						contador ++;
					}

				} while (comprobar && ayuda);

				comprobar = true;
				ayuda = true;
				break;

			case "6":
				do {
					System.out.println("Dame el nombre del evento");
					nombre = sc.nextLine();

					comprobar = gt.buscarEvento(nombre, comprobar);

					if (!comprobar) {
						ayuda = gt.comprobarMusical(nombre, ayuda);

						menueditarMusical(nombre);

					} else if (comprobar) {
						System.out.println("El evento no existe");
						System.out.println("");
						
						if(contador >=3) {
							System.out.println("Numero de intentos excedido");
							System.out.println(" ");
							break;
						}
						contador ++;
					}

				} while (comprobar && ayuda);

				comprobar = true;
				ayuda = true;
				break;

			case "7":
				System.out.println("Volviendo al menu anterior");
				System.out.println(" ");
				salir = false;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;
			}

			/*
			 * caso 1 Editar arte distintos menus case 1 arte pedir nombre y comprobar caso
			 * 2 cine
			 * 
			 * caso editar 2 wdawd
			 * 
			 */

		} while (salir);

	}

	public void comprarEntrada() {
		String dniComprador, nombre;
		String precioEntrada;
		String numEntradas;// Maximo entradas evento
		int numEntradasCompra;// Compra cliente
		int precioFinal = 0;
		int numero;		
		boolean comprobar = true;
		String tarjeta = null, quedarbien;
	
		do {
			System.out.println("Dame el nombre del evento");
			nombre = sc.nextLine();

			comprobar = gt.buscarEvento(nombre, comprobar);
			
		} while (comprobar);

		numEntradas = gt.buscarEntradas(nombre);
		precioEntrada = gt.buscarPrecio(nombre);
		numero = Integer.parseInt(numEntradas);
		// Busco el evento usando el nombre del evento
		System.out.println(
				"Hay " + numEntradas + " entradas disponibles con un precio de " + precioEntrada + " €");
		
			do {

				// Muestro numero de entradas disponibles para comprar de ese evento
				

				System.out.println("Introduce el numero de entradas que deseas comprar");
				numEntradasCompra = sc.nextInt();
				sc.nextLine();
				
				
				if (numero >= numEntradasCompra) {
					precioFinal = Integer.parseInt(precioEntrada) * numEntradasCompra;

					numero = numero - numEntradasCompra;

					
					break;
				} else {
					System.out.println("No hay tantas entradas");
					System.out.println(" ");
				}

			} while (true);
			
		if(numEntradasCompra != 0) {			
			if (numero >= 0) {
				System.out.println("Introduce tu dni para realizar la compra");
				dniComprador = sc.nextLine();
					
				if(dniComprador.length()>9) {
					System.out.println("DNI incorrecto");
				}else {

					System.out.println("Introduce numero de tarjeta");
					tarjeta = sc.nextLine();
					System.out.println("Introduce el numero posterior");
					quedarbien = sc.nextLine();
					
					System.out.println("Compra realizada con éxito, con un importe de " + precioFinal + " €");
					System.out.println(" ");

					FACTURACION ff = new FACTURACION(dniComprador, Integer.toString(numEntradasCompra),
							Integer.toString(precioFinal), tarjeta);
					gt.añadirFactura(ff);
					gt.actualizar(nombre, Integer.toString(numero));
				}
			
			
		} else {
			System.out.println("No quedan entradas para el evento seleccionado");
			System.out.println(" ");
		}
			
		}	
	}
	
	// Crear cada tipo de evento (Urgente)
	public Obje_event_arte recogerArte() {

		String Nombre, Localizacion, Duracion, Fecha, numEntradas, precioEntradas, nombreArtista, numObras;

		System.out.println("Dame nombre");
		Nombre = sc.nextLine();
		System.out.println("Dame Localizacion");
		Localizacion = sc.nextLine();
		System.out.println("Dame duracion");
		Duracion = sc.nextLine();
		System.out.println("fecha");
		Fecha = sc.nextLine();
		System.out.println("Dame num Entradas");
		numEntradas = sc.nextLine();
		System.out.println("Dame precio");
		precioEntradas = sc.nextLine();
		System.out.println("artista");
		nombreArtista = sc.nextLine();
		System.out.println("numero obras");
		numObras = sc.nextLine();

		Obje_event_arte ea = new Obje_event_arte(Nombre, Localizacion, Duracion, Fecha, numEntradas, precioEntradas,
				nombreArtista, numObras);

		return ea;
	}

	public Obje_event_cine recogerCine() {

		String Nombre, Localizacion, Duracion, Fecha, numEntradas, precioEntradas, nombreArtistaP, Pelicula;

		System.out.println("Dame nombre");
		Nombre = sc.nextLine();
		System.out.println("Dame Localizacion");
		Localizacion = sc.nextLine();
		System.out.println("Dame duracion");
		Duracion = sc.nextLine();
		System.out.println("fecha");
		Fecha = sc.nextLine();
		System.out.println("Dame num Entradas");
		numEntradas = sc.nextLine();
		System.out.println("Dame precio");
		precioEntradas = sc.nextLine();
		System.out.println("artista");
		nombreArtistaP = sc.nextLine();
		System.out.println("numero obras");
		Pelicula = sc.nextLine();

		Obje_event_cine ec = new Obje_event_cine(Nombre, Localizacion, Duracion, Fecha, numEntradas, precioEntradas,
				nombreArtistaP, Pelicula);
		return ec;
	}

	public Obje_event_deporte recogerDeporte() {

		String Nombre, Localizacion, Duracion, Fecha, numEntradas, precioEntradas, deporte, premio;

		System.out.println("Dame nombre");
		Nombre = sc.nextLine();
		System.out.println("Dame Localizacion");
		Localizacion = sc.nextLine();
		System.out.println("Dame duracion");
		Duracion = sc.nextLine();
		System.out.println("fecha");
		Fecha = sc.nextLine();
		System.out.println("Dame num Entradas");
		numEntradas = sc.nextLine();
		System.out.println("Dame precio");
		precioEntradas = sc.nextLine();
		System.out.println("artista");
		deporte = sc.nextLine();
		System.out.println("numero obras");
		premio = sc.nextLine();

		Obje_event_deporte ed = new Obje_event_deporte(Nombre, Localizacion, Duracion, Fecha, numEntradas,
				precioEntradas, deporte, premio);
		return ed;
	}

	public Obje_event_empresa recogerEmpresa() {

		String Nombre, Localizacion, Duracion, Fecha, numEntradas, precioEntradas, Tema, numInvitados;

		System.out.println("Dame nombre");
		Nombre = sc.nextLine();
		System.out.println("Dame Localizacion");
		Localizacion = sc.nextLine();
		System.out.println("Dame duracion");
		Duracion = sc.nextLine();
		System.out.println("fecha");
		Fecha = sc.nextLine();
		System.out.println("Dame num Entradas");
		numEntradas = sc.nextLine();
		System.out.println("Dame precio");
		precioEntradas = sc.nextLine();
		System.out.println("artista");
		Tema = sc.nextLine();
		System.out.println("numero obras");
		numInvitados = sc.nextLine();

		Obje_event_empresa ee = new Obje_event_empresa(Nombre, Localizacion, Duracion, Fecha, numEntradas,
				precioEntradas, Tema, numInvitados);
		return ee;
	}

	public Obje_event_moda recogerModa() {

		String Nombre, Localizacion, Duracion, Fecha, numEntradas, precioEntradas, Diseñador, numModelos;

		System.out.println("Dame nombre");
		Nombre = sc.nextLine();
		System.out.println("Dame Localizacion");
		Localizacion = sc.nextLine();
		System.out.println("Dame duracion");
		Duracion = sc.nextLine();
		System.out.println("fecha");
		Fecha = sc.nextLine();
		System.out.println("Dame num Entradas");
		numEntradas = sc.nextLine();
		System.out.println("Dame precio");
		precioEntradas = sc.nextLine();
		System.out.println("artista");
		Diseñador = sc.nextLine();
		System.out.println("numero obras");
		numModelos = sc.nextLine();

		Obje_event_moda em = new Obje_event_moda(Nombre, Localizacion, Duracion, Fecha, numEntradas, precioEntradas,
				Diseñador, numModelos);
		return em;
	}

	public Obje_event_musical recogerMusical() {

		String Nombre, Localizacion, Duracion, Fecha, numEntradas, precioEntradas, nombreArtistaM, nomAlbum;

		System.out.println("Dame nombre");
		Nombre = sc.nextLine();
		System.out.println("Dame Localizacion");
		Localizacion = sc.nextLine();
		System.out.println("Dame duracion");
		Duracion = sc.nextLine();
		System.out.println("fecha");
		Fecha = sc.nextLine();
		System.out.println("Dame num Entradas");
		numEntradas = sc.nextLine();
		System.out.println("Dame precio");
		precioEntradas = sc.nextLine();
		System.out.println("artista");
		nombreArtistaM = sc.nextLine();
		System.out.println("numero obras");
		nomAlbum = sc.nextLine();

		Obje_event_musical eu = new Obje_event_musical(Nombre, Localizacion, Duracion, Fecha, numEntradas,
				precioEntradas, nombreArtistaM, nomAlbum);
		return eu;
	}

	public void menueditarArte(String nombre) {
		String N;
		boolean salir = true;
		String nuevo;
		String funciona = nombre;
		do {
			System.out.println("Que quieres editar");
			System.out.println("Inserte 1 para editar el nombre [Necesita reinicio]");
			System.out.println("Inserte 2 para editar el locaclizacion");
			System.out.println("Inserte 3 para editar el duracion");
			System.out.println("Inserte 4 para editar el fecha");
			System.out.println("Inserte 5 para editar el entradas");
			System.out.println("Inserte 6 para editar el precio entradas");
			System.out.println("Inserte 7 para editar artista");
			System.out.println("Inserte 8 para editar el numero de obras a exponer");
			System.out.println("Inserte 9 SALIR");
			N = sc.nextLine();

			switch (N) {

			case "1":// editar nombre
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarNombre(funciona, nuevo);
				System.out.println(
						"Al modificar el nombre, es necesario que salga al menu anterior para guardar los cambios");

				break;

			case "2": // editar localizacion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarLocalizacion(funciona, nuevo);

				break;

			case "3": // editar duracion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarDuracion(funciona, nuevo);

				break;

			case "4": // editar fecha

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarFecha(funciona, nuevo);

				break;

			case "5": // editar entradas
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarEntradas(funciona, nuevo);
				break;

			case "6": // editar precio
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarPrecio(funciona, nuevo);
				break;

			case "7": // editar artista
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarArtista(funciona, nuevo);
				break;

			case "8": // editar obras
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarObras(funciona, nuevo);
				break;

			case "9": // salir
				System.out.println("Volviendo al menu anteior");
				System.out.println(" ");
				salir = false;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;

			}
		} while (salir);

	}// salir de modificar arte

	public void menueditarCine(String nombre) {
		String N;
		boolean salir = true;
		String nuevo;
		String funciona = nombre;
		do {
			System.out.println("Que quieres editar");
			System.out.println("Inserte 1 para editar el nombre [Necesita reinicio]");
			System.out.println("Inserte 2 para editar el locaclizacion");
			System.out.println("Inserte 3 para editar el duracion");
			System.out.println("Inserte 4 para editar el fecha");
			System.out.println("Inserte 5 para editar el entradas");
			System.out.println("Inserte 6 para editar el precio entradas");
			System.out.println("Inserte 7 para editar artista");
			System.out.println("Inserte 8 para editar la pelicula a exponer");
			System.out.println("Inserte 9 SALIR");
			N = sc.nextLine();

			switch (N) {

			case "1":// editar nombre
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				funciona = gt.actualizarNombre(funciona, nuevo);
				System.out.println(
						"Al modificar el nombre, es necesario que salga al menu anterior para guardar los cambios");
				break;

			case "2": // editar localizacion
				System.out.println(funciona);
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();

				gt.actualizarLocalizacion(funciona, nuevo);

				break;

			case "3": // editar duracion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarDuracion(funciona, nuevo);

				break;

			case "4": // editar fecha

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarFecha(funciona, nuevo);

				break;

			case "5": // editar entradas
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarEntradas(funciona, nuevo);

				break;

			case "6": // editar precio
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarPrecio(funciona, nuevo);
				break;

			case "7": // editar director
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarDirector(nombre, nuevo);
				break;

			case "8": // editar pelicula
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarPelicula(nombre, nuevo);

				break;

			case "9": // salir
				System.out.println("Volviendo al menu anteior");
				System.out.println(" ");
				salir = false;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;

			}
		} while (salir);

	}// salir de modificar cine

	public void menueditarDeporte(String nombre) {
		String N;
		boolean salir = true;
		String nuevo;
		String funciona = nombre;
		do {
			System.out.println("Que quieres editar");
			System.out.println("Inserte 1 para editar el nombre");
			System.out.println("Inserte 2 para editar la locaclizacion");
			System.out.println("Inserte 3 para editar la duracion");
			System.out.println("Inserte 4 para editar la fecha");
			System.out.println("Inserte 5 para editar las entradas");
			System.out.println("Inserte 6 para editar el precio de las entradas");
			System.out.println("Inserte 7 para editar el deporte");
			System.out.println("Inserte 8 para editar el premio");
			System.out.println("Inserte 9 SALIR");
			N = sc.nextLine();

			switch (N) {

			case "1":// editar nombre
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				funciona = gt.actualizarNombre(funciona, nuevo);
				System.out.println(
						"Al modificar el nombre, es necesario que salga al menu anterior para guardar los cambios");
				break;

			case "2": // editar localizacion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarLocalizacion(funciona, nuevo);

				break;

			case "3": // editar duracion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarDuracion(funciona, nuevo);

				break;

			case "4": // editar fecha

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarFecha(funciona, nuevo);

				break;

			case "5": // editar entradas
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarEntradas(funciona, nuevo);
				break;

			case "6": // editar precio
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarPrecio(funciona, nuevo);
				break;

			case "7": // editar deporte
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarDeporte(funciona, nuevo);
				break;

			case "8": // editar premio
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarPremio(funciona, nuevo);
				break;

			case "9": // salir
				System.out.println("Volviendo al menu anteior");
				System.out.println(" ");
				salir = false;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;

			}
		} while (salir);

	}// salir de modificar deporte

	public void menueditarEmpresa(String nombre) {
		String N;
		boolean salir = true;
		String nuevo;
		String funciona = nombre;
		do {
			System.out.println("Que quieres editar");
			System.out.println("Inserte 1 para editar el nombre");
			System.out.println("Inserte 2 para editar la locaclizacion");
			System.out.println("Inserte 3 para editar la duracion");
			System.out.println("Inserte 4 para editar la fecha");
			System.out.println("Inserte 5 para editar las entradas");
			System.out.println("Inserte 6 para editar el precio de las entradas");
			System.out.println("Inserte 7 para editar el tema");
			System.out.println("Inserte 8 para editar el numero de invitados");
			System.out.println("Inserte 9 SALIR");
			N = sc.nextLine();

			switch (N) {
			case "1":// editar nombre
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				funciona = gt.actualizarNombre(funciona, nuevo);
				System.out.println(
						"Al modificar el nombre, es necesario que salga al menu anterior para guardar los cambios");
				break;

			case "2": // editar localizacion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarLocalizacion(funciona, nuevo);

				break;

			case "3": // editar duracion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarDuracion(funciona, nuevo);

				break;

			case "4": // editar fecha

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarFecha(funciona, nuevo);

				break;

			case "5": // editar entradas
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarEntradas(funciona, nuevo);
				break;

			case "6": // editar precio
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarPrecio(funciona, nuevo);
				break;

			case "7": // editar tema
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarTema(nombre, nuevo);
				break;

			case "8": // editar numero de invitados
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarInvitados(nombre, nuevo);
				break;

			case "9": // salir
				System.out.println("Volviendo al menu anteior");
				System.out.println(" ");
				salir = false;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;

			}
		} while (salir);

	}// salir de modificar empresa

	public void menueditarModa(String nombre) {
		String N;
		boolean salir = true;
		String nuevo;
		String funciona = nombre;
		do {
			System.out.println("Que quieres editar");
			System.out.println("Inserte 1 para editar el nombre");
			System.out.println("Inserte 2 para editar la locaclizacion");
			System.out.println("Inserte 3 para editar la duracion");
			System.out.println("Inserte 4 para editar la fecha");
			System.out.println("Inserte 5 para editar las entradas");
			System.out.println("Inserte 6 para editar el precio de las entradas");
			System.out.println("Inserte 7 para editar el diseñador");
			System.out.println("Inserte 8 para editar el numero de modelos");
			System.out.println("Inserte 9 SALIR");
			N = sc.nextLine();

			switch (N) {

			case "1":// editar nombre
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				funciona = gt.actualizarNombre(funciona, nuevo);
				System.out.println(
						"Al modificar el nombre, es necesario que salga al menu anterior para guardar los cambios");
				break;

			case "2": // editar localizacion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarLocalizacion(funciona, nuevo);

				break;

			case "3": // editar duracion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarDuracion(funciona, nuevo);

				break;

			case "4": // editar fecha

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarFecha(funciona, nuevo);

				break;

			case "5": // editar entradas
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarEntradas(funciona, nuevo);
				break;

			case "6": // editar precio
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarPrecio(funciona, nuevo);
				break;

			case "7": // editar diseñador
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarDiseñador(nombre, nuevo);
				break;

			case "8": // editar numero de modeslos
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarModelos(nombre, nuevo);
				break;

			case "9": // salir
				System.out.println("Volviendo al menu anterior");
				System.out.println(" ");
				salir = false;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;

			}
		} while (salir);

	}// salir de modificar moda

	public void menueditarMusical(String nombre) {
		String N;
		boolean salir = true;
		String nuevo;
		String funciona = nombre;
		do {
			System.out.println("Que quieres editar");
			System.out.println("Inserte 1 para editar el nombre");
			System.out.println("Inserte 2 para editar la locaclizacion");
			System.out.println("Inserte 3 para editar la duracion");
			System.out.println("Inserte 4 para editar la fecha");
			System.out.println("Inserte 5 para editar las entradas");
			System.out.println("Inserte 6 para editar el precio de las entradas");
			System.out.println("Inserte 7 para editar artista");
			System.out.println("Inserte 8 para editar el album");
			System.out.println("Inserte 9 SALIR");
			N = sc.nextLine();

			switch (N) {
			case "1":// editar nombre
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				funciona = gt.actualizarNombre(funciona, nuevo);
				System.out.println(
						"Al modificar el nombre, es necesario que salga al menu anterior para guardar los cambios");
				break;

			case "2": // editar localizacion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarLocalizacion(funciona, nuevo);

				break;

			case "3": // editar duracion

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarDuracion(funciona, nuevo);

				break;

			case "4": // editar fecha

				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarFecha(funciona, nuevo);

				break;

			case "5": // editar entradas
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarEntradas(funciona, nuevo);
				break;

			case "6": // editar precio
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarPrecio(funciona, nuevo);
				break;

			case "7": // editar cantante
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarCantante(nombre, nuevo);
				break;

			case "8": // editar album
				System.out.println("Introduzca el nuevo valor");
				nuevo = sc.nextLine();
				gt.actualizarAlbum(nombre, nuevo);
				break;

			case "9": // salir
				System.out.println("Volviendo al menu anteior");
				System.out.println(" ");
				salir = false;
				break;

			default:
				System.out.println("Introduce una opcion correcta");
				System.out.println(" ");
				break;

			}
		} while (salir);

	}// salir de modificar musica

}
