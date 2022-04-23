/**
 * 
 */
package ar.edu.ort.tp1.parcial2.entidades;

/**
 * Entidad que representa un horario con hora y minutos
 * 
 * @author Julio Sejtman
 *
 */
public class Hora {
	private int hora;
	private int minuto;

	/**
	 * @param hora
	 * @param minuto
	 */
	public Hora(int hora, int minuto) {
		this.setHora(hora);
		this.setMinuto(minuto);
	}

	/**
	 * @return the hora
	 */
	public int getHora() {
		return hora;
	}

	/**
	 * @return the minuto
	 */
	public int getMinuto() {
		return minuto;
	}

	/**
	 * @param hora the hora to set
	 */
	private void setHora(int hora) {
		this.hora = hora;
	}

	/**
	 * @param minuto the minuto to set
	 */
	private void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hora [hora=" + hora + ", minuto=" + minuto + "]";
	}

}
