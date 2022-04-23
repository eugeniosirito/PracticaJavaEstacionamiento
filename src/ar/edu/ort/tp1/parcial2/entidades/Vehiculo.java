/**
 * 
 */
package ar.edu.ort.tp1.parcial2.entidades;

import java.time.Duration;
import java.time.LocalTime;

import ar.edu.ort.tp1.parcial2.exceptions.EstacionamientoException;

/**
 * Vehículos estacionables en un estacionamiento
 */
/**
 * @author Julio Sejtman
 *
 */
public abstract class Vehiculo {

	protected static final int HORA = 60;
	protected static final int MEDIA_HORA = 30;
	protected static final int CINCO = 5;
	protected static final int CINCO_MINUTOS_POR_HORA = 12;
	protected static final int DIEZ = 10;
	protected static final int DIEZ_MINUTOS_POR_HORA = 6;
	protected static final String REGEX_SOLO_3_NUMEROS = "^\\d{3}$";
	protected static final String REGEX_SOLO_LETRAS = "^[a-zA-Z]+$";

	/**
	 * Patente del vehículo
	 */
	private String patente;
	/**
	 * Hora de ingreso al estacionamiento
	 */
	private Hora horaIngreso;

	/**
	 * Constructor del vehículo
	 * 
	 * @param patente       Patente
	 * @param horaIngreso   Hora del horario de ingreso
	 * @param minutoIngreso minutos del horario de ingreso
	 */
	public Vehiculo(String patente, Hora horaIngreso) {
		this.setPatente(patente);
		this.setHoraIngreso(horaIngreso);
	}

	/**
	 * Valida que los minutos sean válidos (de 0 a 23 inclusive) debe lanzar una
	 * excepción {@link EstacionamientoException} si no es válido
	 * 
	 * @param horaIngreso
	 * @throws EstacionamientoException Si el horario es erroneo
	 */
	private void setHoraIngreso(Hora horaIngreso) throws EstacionamientoException {
		this.validarHora(horaIngreso);
		this.horaIngreso = horaIngreso;

	}

	/**
	 * 
	 * @param horaIngreso
	 * @throws EstacionamientoException
	 */
	protected void validarHora(Hora horaIngreso) throws EstacionamientoException {
		if (horaIngreso.getHora() < 0 || horaIngreso.getHora() >= 24) {
			throw new EstacionamientoException("Hora de ingreso inválidos");
		}
		if (horaIngreso.getMinuto() < 0 || horaIngreso.getMinuto() >= 60) {
			throw new EstacionamientoException("Minutos de ingreso inválidos");
		}
	}

	/**
	 * Setter de patente, que debe ser validada segun cada tipo de vehículo
	 * 
	 * @param patente
	 */
	private void setPatente(String patente) {
		this.validarPatente(patente);
		this.patente = patente;
	}

	/**
	 * Devuelve la patente
	 * 
	 * @return
	 */
	public String getPatente() {
		return this.patente;
	}

	/**
	 * Valida la patente en forma especifica para cada tipo de vehículo. Para
	 * realizar la validación pueden utilizar el método
	 * <code>String.matches(regex)</code> utilizando las regex provistas como
	 * constantes REGEX_SOLO_3_NUMEROS y REGEX_SOLO_3_NUMEROS REGEX_SOLO_LETRAS
	 * 
	 * @param patente
	 * @throws EstacionamientoException si la patente es inválida
	 */
	protected abstract void validarPatente(String patente) throws EstacionamientoException;

	/**
	 * Calcula el importe que debe abonar el vehículo en base a su tipo, el horario
	 * de ingreso que posee y el horario de egreso indicado Cada vehículo debe
	 * poseer su algoritmo de calculo en base al fraccionamiento de minutos
	 * 
	 * @param horaEgreso
	 * @return
	 * @throws EstacionamientoException si existe algun problema al calcular el
	 *                                  importe.
	 */
	protected abstract float calcularImporte(Hora horaEgreso) throws EstacionamientoException;

	/**
	 * Metodo helper que calcula el tiempo de la estadia, siendo devuelto en un
	 * objeto del tipo {@link Hora}
	 * 
	 * @param horaSalida
	 * @param minutoSalida
	 * @return
	 */
	public Hora calcularTiempoEstadia(Hora horaSalida) {

		LocalTime horarioIngreso = LocalTime.of(this.horaIngreso.getHora(), this.horaIngreso.getMinuto());
		LocalTime horarioEgreso = LocalTime.of(horaSalida.getHora(), horaSalida.getMinuto());
		Duration duration = Duration.between(horarioIngreso, horarioEgreso);

		return new Hora((int) duration.toHours(), (int) duration.toMinutes() % 60);

	}

	/**
	 * Redondea el número de minutos de egreso a una base, si la base es 5, y el nro
	 * de minutos es 2 devolverá 5, si el nro de minutos es 7 devolvera 10
	 * 
	 * @param minutos
	 * @param i
	 * @return
	 */
	protected int redondear(int minutos, int base) {
		return minutos + (base - minutos % base);
	}

	/**
	 * @param horasEgreso
	 * @param minutosEgreso
	 */
	public void validarHoraEgreso(Hora hora) throws EstacionamientoException {
		this.validarHora(hora);
		LocalTime horarioIngreso = LocalTime.of(this.horaIngreso.getHora(), this.horaIngreso.getMinuto());
		LocalTime horarioEgreso = LocalTime.of(hora.getHora(), hora.getMinuto());

		if (horarioEgreso.compareTo(horarioIngreso) < 0) {
			throw new EstacionamientoException("Horario de egreso anterior al de ingreso");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vehiculo [patente=" + patente + ", horaIngreso=" + horaIngreso + "]";
	}

}
