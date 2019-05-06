package Proyecto_Segundo_Trimestre;


public class Obje_event_deporte  extends evento_General{

	private String Deporte,Premio;

	
	protected Obje_event_deporte() {
		super();
	}

	



	/**
	 * @param nomEvento
	 * @param localizacion
	 * @param duracion
	 * @param fecha
	 * @param entradas
	 * @param precioEntrada
	 * @param deporte
	 * @param premio
	 */
	protected Obje_event_deporte(String nomEvento, String localizacion, String duracion, String fecha, String entradas,
			String precioEntrada, String deporte, String premio) {
		super(nomEvento, localizacion, duracion, fecha, entradas, precioEntrada);
		Deporte = deporte;
		Premio = premio;
	}





	protected String getDeporte() {
		return Deporte;
	}

	protected void setDeporte(String deporte) {
		Deporte = deporte;
	}

	protected String getPremio() {
		return Premio;
	}

	protected void setPremio(String premio) {
		Premio = premio;
	}


	/* (non-Javadoc)
	 * @see Proyecto_Segundo_Trimestre.evento_General#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "\n Deporte: " + Deporte + "\n Premio a ganar: " + Premio;
	}
	
	
	
}
