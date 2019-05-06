package Proyecto_Segundo_Trimestre;


public class Obje_event_arte extends evento_General {

	
	private String nombreArtista,numObras;

	public Obje_event_arte() {
		super();
	}

	/**
	 * @param nomEvento
	 * @param localizacion
	 * @param duracion
	 * @param fecha
	 * @param entradas
	 * @param precioEntrada
	 * @param nombreArtista
	 * @param numObras
	 */
	protected Obje_event_arte(String nomEvento, String localizacion, String duracion, String fecha, String entradas,
			String precioEntrada, String nombreArtista, String numObras) {
		super(nomEvento, localizacion, duracion, fecha, entradas, precioEntrada);
		this.nombreArtista = nombreArtista;
		this.numObras = numObras;
	}


	public String getNombreArtista() {
		return nombreArtista;
	}
	
	public void setNombreArtista(String nombreArtista) {
		this.nombreArtista = nombreArtista;
	}

	public String getNumObras() {
		return numObras;
	}

	public void setNumObras(String numObras) {
		this.numObras = numObras;
	}

	/* (non-Javadoc)
	 * @see Proyecto_Segundo_Trimestre.evento_General#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "\n Nombre del artista : " + nombreArtista + "\n Numero de obras: " + numObras;
	}
	
	
	
	
	
}
