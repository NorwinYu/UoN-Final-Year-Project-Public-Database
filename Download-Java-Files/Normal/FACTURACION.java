package Proyecto_Segundo_Trimestre;

public class FACTURACION {

	private String DNIcomprador, numEntradasCompradas, precioPagado,tarjeta;

	/**
	 * 
	 */
	protected FACTURACION() {
		super();
	}

	/**
	 * @param dNIcomprador
	 * @param numEntradasCompradas
	 */
	protected FACTURACION(String dNIcomprador, String numEntradasCompradas, String precioPagado,String tarjeta) {
		super();
		DNIcomprador = dNIcomprador;
		this.numEntradasCompradas = numEntradasCompradas;
		this.precioPagado = precioPagado;
		this.tarjeta = tarjeta;
	}

	/**
	 * @return the precioPagado
	 */
	protected String getPrecioPagado() {
		return precioPagado;
	}

	/**
	 * @param precioPagado the precioPagado to set
	 */
	protected void setPrecioPagado(String precioPagado) {
		this.precioPagado = precioPagado;
	}

	/**
	 * @return the dNIcomprador
	 */
	protected String getDNIcomprador() {
		return DNIcomprador;
	}

	/**
	 * @param dNIcomprador the dNIcomprador to set
	 */
	protected void setDNIcomprador(String dNIcomprador) {
		DNIcomprador = dNIcomprador;
	}

	/**
	 * @return the numEntradasCompradas
	 */
	protected String getNumEntradasCompradas() {
		return numEntradasCompradas;
	}

	/**
	 * @param numEntradasCompradas the numEntradasCompradas to set
	 */
	protected void setNumEntradasCompradas(String numEntradasCompradas) {
		this.numEntradasCompradas = numEntradasCompradas;
	}

	public String toString() {
		String mensaje;

		mensaje = "\n DNI del cliente : " + DNIcomprador;
		mensaje += "\n Numero de entradas compradas : " + numEntradasCompradas;
		mensaje += "\n Importe de  : " + precioPagado + " euros";
		mensaje += "\n Numero de tarjeta  : " + tarjeta ;
		return mensaje;
	}

}
