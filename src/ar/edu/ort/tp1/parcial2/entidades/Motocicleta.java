/**
 * 
 */
package ar.edu.ort.tp1.parcial2.entidades;

/**
 * Representa una motocicleta a ser estacionada. el precio de los autos se
 * fracciona cada 5 minutos.
 */
public class Motocicleta extends Vehiculo {
	/**
	 * Precio de los 5 minutos de estacionamiento
	 */
	private float precioCincoMinutos;

	/**
	 * Constructor de motocicleta
	 * 
	 * @param patente       patente de la motocicleta
	 * @param horaIngreso   hora del ingreso
	 * @param minutoIngreso minutos del ingreso
	 * @param precioPorHora precio de la hora completa de las motocicletas
	 */
	public Motocicleta(String patente, Hora horaIngreso, float precioPorHora) {
		super(patente, horaIngreso);
		this.setPrecioCincoMinutos(precioPorHora / 12f);
	}

	/**
	 * @param precioCincoMinutos
	 */
	private void setPrecioCincoMinutos(float precioCincoMinutos) {
		this.precioCincoMinutos = precioCincoMinutos;
	}

	/**
	 * Valida que una patente sea válida para el tipo de vehiculo auto. debe tener
	 * formato de tres números y tres letas por ejemplo '182ABC'.
	 */
	public void validarPatente(String patente) throws IllegalArgumentException {

		if (patente.length() != 6) {
			throw new IllegalArgumentException("Longitud de patente errónea");
		}

		if (!patente.matches(TipoVehiculo.MOTOCICLETA.getRegex())) {
			throw new IllegalArgumentException("Patente errónea");
		}

	}

	/**
	 * Calcula el importe de la estadia del auto, recibiendo la hora y minutos de
	 * salida. Debe redondearse a 5 minutos la cantidad de minutos de la estadía, si
	 * la duración da 12 minutos, se deben cobrar 15. si la duración da 7 minutos,
	 * se deben cobrar 10.
	 */

	@Override
	public float calcularImporte(Hora horaEgreso) throws IllegalArgumentException {
		this.validarHoraEgreso(horaEgreso);

		Hora tiempo = this.calcularTiempoEstadia(horaEgreso);
		int minutos = this.redondear(tiempo.getMinuto(), CINCO);
		float importe = 0;
		if (tiempo.getHora() == 0 && minutos < MEDIA_HORA) {
			importe = (float) (MEDIA_HORA / CINCO) * this.precioCincoMinutos;
		} else {
			importe += (float) (minutos / CINCO) * this.precioCincoMinutos;
			importe += (float) (tiempo.getHora() * CINCO_MINUTOS_POR_HORA) * this.precioCincoMinutos;
		}

		return importe;
	}

}
