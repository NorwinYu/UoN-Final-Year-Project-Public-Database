package Proyecto_Segundo_Trimestre;


public class Obje_event_cine  extends evento_General {

	private String nombreArtistaP,Pelicula;

	public Obje_event_cine() {
		super();
	}

	


	/**
	 * @param nomEvento
	 * @param localizacion
	 * @param duracion
	 * @param fecha
	 * @param entradas
	 * @param precioEntrada
	 * @param nombreArtistaP
	 * @param pelicula
	 */
	protected Obje_event_cine(String nomEvento, String localizacion, String duracion, String fecha, String entradas,
			String precioEntrada, String nombreArtistaP, String pelicula) {
		super(nomEvento, localizacion, duracion, fecha, entradas, precioEntrada);
		this.nombreArtistaP = nombreArtistaP;
		Pelicula = pelicula;
	}




	public String getNombreArtistaP() {
		return nombreArtistaP;
	}

	public void setNombreArtista(String nombreArtistaP) {
		this.nombreArtistaP = nombreArtistaP;
	}

	public String getPelicula() {
		return Pelicula;
	}

	public void setPelicula(String pelicula) {
		Pelicula = pelicula;
	}

	/* (non-Javadoc)
	 * @see Proyecto_Segundo_Trimestre.evento_General#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "\n Nombre artista: " + nombreArtistaP + "\n Nombre de la pelicula: " + Pelicula;
	}
	
	
}
