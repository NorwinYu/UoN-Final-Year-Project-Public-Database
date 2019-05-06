package Proyecto_Segundo_Trimestre;


public abstract class evento_General {
	
	protected String Duracion,Fecha,Localizacion,Entradas,precioEntrada,nomEvento;
	//Entradas vip

	/**
	 * @return the duracion
	 */
	protected String getDuracion() {
		return Duracion;
	}
	/**
	 * @param duracion the duracion to set
	 */
	protected void setDuracion(String duracion) {
		Duracion = duracion;
	}
	/**
	 * @return the fecha
	 */
	protected String getFecha() {
		return Fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	protected void setFecha(String fecha) {
		Fecha = fecha;
	}
	/**
	 * @return the localizacion
	 */
	protected String getLocalizacion() {
		return Localizacion;
	}
	/**
	 * @param localizacion the localizacion to set
	 */
	protected void setLocalizacion(String localizacion) {
		Localizacion = localizacion;
	}
	/**
	 * @return the entradas
	 */
	protected String getEntradas() {
		return Entradas;
	}
	/**
	 * @param entradas the entradas to set
	 */
	protected void setEntradas(String entradas) {
		Entradas = entradas;
	}
	/**
	 * @return the precioEntrada
	 */
	protected String getPrecioEntrada() {
		return precioEntrada;
	}
	/**
	 * @param precioEntrada the precioEntrada to set
	 */
	protected void setPrecioEntrada(String precioEntrada) {
		this.precioEntrada = precioEntrada;
	}
	/**
	 * @return the nomEvento
	 */
	protected String getNomEvento() {
		return nomEvento;
	}
	/**
	 * @param nomEvento the nomEvento to set
	 */
	protected void setNomEvento(String nomEvento) {
		this.nomEvento = nomEvento;
	}
	/**
	 * 
	 */
	protected evento_General() {
		super();
	}
	/**
	 * @param duracion
	 * @param fecha
	 * @param localizacion
	 * @param nomEvento
	 */
	
	protected evento_General(String nomEvento,String localizacion,String duracion, String fecha) {
		Duracion = duracion;
		Fecha = fecha;
		Localizacion = localizacion;
		this.nomEvento = nomEvento;
	}
	/**
	 * @param duracion
	 * @param fecha
	 * @param localizacion
	 * @param entradas
	 * @param eLaboral
	 * @param precioEntrada
	 * @param nomEvento
	 */
	protected evento_General(String nomEvento, String localizacion, String duracion, String fecha, String entradas,String precioEntrada) {
		super();
		Duracion = duracion;
		Fecha = fecha;
		Localizacion = localizacion;
		Entradas = entradas;	
		this.precioEntrada = precioEntrada;
		this.nomEvento = nomEvento;
	}
	
	public String toString() {
		String mensaje;
		
		mensaje="Nombre del evento : " + nomEvento;
		mensaje+="\n Localizacion : " + Localizacion;
		mensaje+="\n Duración de : " +Duracion;
		mensaje+="\n Fecha : " + Fecha;
		mensaje+="\n Entradas disponibles : " + Entradas;
		mensaje+="\n Precio entrada ud : " + precioEntrada;
		
		return mensaje;
	}
	
	
	
}
