/**
 * 
 */
package ar.edu.ort.tp1.parcial2.entidades;

import ar.edu.ort.tp1.parcial2.exceptions.EstacionamientoException;

/**
 * Representa un auto a ser estacionado. El precio de los autos se fracciona
 * cada 10 minutos.
 */
public class Auto extends Vehiculo {

	/**
	 * Precio de los 10 minutos de estacionamiento
	 */
	private float precioDiezMinutos;

	/**
	 * Constructor de auto
	 * 
	 * @param patente       patente del auto
	 * @param horaIngreso   hora del ingreso
	 * @param minutoIngreso minutos del ingreso
	 * @param precioPorHora precio de la hora completa de los autos
	 */
	public Auto(String patente, Hora hora, float precioPorHora) {
		super(patente, hora);
		this.setPrecioDiezMinutos(precioPorHora / 6f);
	}

	/**
	 * @param precioCincoMinutos
	 */
	private void setPrecioDiezMinutos(float precioDiezMinutos) {
		this.precioDiezMinutos = precioDiezMinutos;
	}

	/**
	 * Valida que una patente sea válida para el tipo de vehiculo auto. debe tener
	 * formato de tres letras y tres números por ejemplo 'DSA182' o dos letras tres
	 * numeros y dos letras ejemplo 'AB123CD'
	 */
	@Override
	public void validarPatente(String patente) throws EstacionamientoException {

		if (patente.length() != 6) {
			throw new EstacionamientoException("Longitud de patente errónea");
		}

		if (!patente.matches(TipoVehiculo.AUTO.getRegex())) {
			throw new EstacionamientoException("Patente errónea");
		}

	}

	/**
	 * Calcula el importe de la estadia del auto, recibiendo el horario de salida.
	 * Debe redondearse a 10 minutos la cantidad de minutos de la estadía, si la
	 * duración da 7 minutos, se deben cobrar 10.
	 */
	@Override
	public float calcularImporte(Hora hora) throws IllegalArgumentException {

		super.validarHoraEgreso(hora);

		Hora tiempo = this.calcularTiempoEstadia(hora);
		int minutos = this.redondear(tiempo.getMinuto(), DIEZ);
		float importe = 0;
		if (tiempo.getHora() == 0 && minutos < MEDIA_HORA) {
			importe = (float) (MEDIA_HORA / DIEZ) * this.precioDiezMinutos;
		} else {
			importe += (float) (minutos / DIEZ) * this.precioDiezMinutos;
			importe += (float) (tiempo.getHora() * DIEZ_MINUTOS_POR_HORA) * this.precioDiezMinutos;
		}
		return importe;
	}

}
