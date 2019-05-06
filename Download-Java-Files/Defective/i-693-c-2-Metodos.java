package modelo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import vista.Vista;

public class Metodos {

	// metodo para cifrar la contraseña

	public static String encriptaString(String pass) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(pass.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	// metodo para comprobar que la contraseña escrita sea igual que la guardada en
	// la base

	// metodo para mostrar pantalla al pulsar un boton

	public void mostrarPantalla(JPanel panel, JFrame ventana) {
		ventana.getContentPane().add(panel);
		panel.setVisible(true);
	}

	//metodo para cifrar la contraseña
	
	private static String encriptarPass(String pass) {
		Vista vista=new Vista();
		
		pass=vista.login.pFLogin.getName();
		
		 try {
			 MessageDigest md = MessageDigest.getInstance("MD5");
			 byte[] messageDigest = md.digest(pass.getBytes());
			 BigInteger number = new BigInteger(1, messageDigest);
			 pass= number.toString(16);

			 while (pass.length() < 32) {
			 pass = "0" + pass;
			 }
			 return pass;
			 }
			 catch (NoSuchAlgorithmException e) {
			 throw new RuntimeException(e);
			 }
	}
	//Método para comprobar que el DNI introducido en el registro no existe en la base de datos
	public static boolean dniExistente(String dni) {
		boolean dniExistente=false;
		Vista vista=new Vista();
		dni=vista.registro.tfDNIRegistro.getName();
		//hacer un bucle que compare el String recibido de la pantalla con los dni's existentes
		
		return dniExistente;
	}
	//Método para comprobar que el usuario introducido en el registro no existe en la base de datos
	public static boolean userExistente(String user) {
		boolean userExistente=false;
		Vista vista=new Vista();
		user=vista.registro.tFNombreRegistro.getName();
		//bucle que compara el nombre escrito con los existentes en la bbdd 
		//si existe devuelve true, si no, false
		
		return userExistente;
	}
	
	
	
	//Método para comprobar que el login del usuario ha sido correcto
	public static boolean comprobarLogin(String user, String pass) {
		boolean pass_comp=false;
		boolean user_comp=comprobarUsuario(user);
		
		if(user_comp=true) {
			pass_comp=comprobarPassword(pass);
		}
		
		if(user_comp==true && pass_comp==true) {
			return true;
		}else {
			return false;
		}
	}
	
	//método para comprobar que el usuario introducido en el login existe en la base de datos
	
	private static boolean comprobarUsuario(String user) {
		String usuarioAcomparar=null;
		//cogemos el valor del textField que nos han pasado como usuario
		Vista vista=new Vista();
		vista.login.tFLogin.getName();
		//hacemos la consulta para conseguir el dato del usuario y guardarlo en la variable
		
		//comparamos el campo dado en el textField con el usuario de la BBDD
		if(user.equals(usuarioAcomparar)) {
			return true;
		}else {
			return false;
		}
	}
	
	//metodo para comprobar que la contraseña escrita sea igual que la guardada en la base

	private static boolean comprobarPassword(String passRecibida) {
		String passAComparar=null;
		//Encriptamos la contraseña que recibimos
		
		String passEncriptada= encriptarPass(passRecibida);
		
		//consultar en la base la contraseña del usuario que ha tenido que ser validado anteriormente
		
		if(passEncriptada.equals(passAComparar)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	//Método para cargar array de Lineas con los datos de la BBDD
	public static Lineasdeautobuses[] cargarArrLineas() {
		Lineasdeautobuses[] lineas;
		String tablaconsulta="";
		//llamamos al metodo contLargoArr para saber el largo que tendrá nuestro array
		int contLargo=contarLargoArr(tablaconsulta);
		
		//asignamos el largo al array
		lineas=new Lineasdeautobuses[contLargo];
		
		
		//hacemos la consulta a la tabla
		
		//cargamos el array
		
		
		return lineas;
	}
	
	//Método que recibe la tabla a consultar y devuelve el numero 
	private static int contarLargoArr(String nombreTablaConsulta) {
		int cont=0;
		//consulta para obtener el count 
		
		
		return cont;
	}
	//Método cargar array de Usurios con los datos de la BBDD
	
	//Método para cargar array de Municipios con los datos de la BBDD
	
	//Método para cargar array parada con los datos de la BBDD
	


	public static double calcularDistanciaEuclediana() {
		Punto p1 = new Punto(5, 10);
		Punto p2 = new Punto(3, 7);
		double resultado = 0;
		try {
			resultado = p1.distancia(p2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
}
