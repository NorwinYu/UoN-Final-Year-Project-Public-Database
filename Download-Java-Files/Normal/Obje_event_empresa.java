package Proyecto_Segundo_Trimestre;


public class Obje_event_empresa  extends evento_General{

	
	private String Tema,numInvitados;

	protected Obje_event_empresa() {
		super();
	}

	
	


	/**
	 * @param nomEvento
	 * @param localizacion
	 * @param duracion
	 * @param fecha
	 * @param entradas
	 * @param precioEntrada
	 * @param tema
	 * @param invitados
	 */
	protected Obje_event_empresa(String nomEvento, String localizacion, String duracion, String fecha, String entradas,
			String precioEntrada, String tema, String numinvitados) {
		super(nomEvento, localizacion, duracion, fecha, entradas, precioEntrada);
		Tema = tema;
		numInvitados = numinvitados;
	}





	protected String getTema() {
		return Tema;
	}

	protected void setTema(String tema) {
		Tema = tema;
	}

	protected String getInvitados() {
		return numInvitados;
	}

	protected void setInvitados(String invitados) {
		numInvitados = invitados;
	}



	/* (non-Javadoc)
	 * @see Proyecto_Segundo_Trimestre.evento_General#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "\n Tema a hablar: " + Tema + "\n Numero de invitados: " + numInvitados ;
	}
	
	
}
