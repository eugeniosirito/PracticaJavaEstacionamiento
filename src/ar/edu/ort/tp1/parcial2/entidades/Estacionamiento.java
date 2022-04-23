/**
 * 
 */
package ar.edu.ort.tp1.parcial2.entidades;

import java.util.ArrayList;

import ar.edu.ort.tp1.parcial2.exceptions.EstacionamientoException;
import ar.edu.ort.tp1.parcial2.tad.Pila;
import ar.edu.ort.tp1.parcial2.tad.PilaAL;
import ar.edu.ort.tp1.parcial2.tad.TAD;

/**
 * Entidad que engloba el funcionamiento de un estacionamiento de autos y
 * motocicletas. cada tipo de vehículo será estacionado de la forma determinada
 * y en base a su capacidad asignada al momento de la creación del
 * estacionamiento.
 * 
 * Se debe tene en cuenta que el funcionamiento del estacionamiento para cada
 * tipo de vehículo es del tipo LIFO Utilizar la implementación de TAD que
 * corresponda
 */
public class Estacionamiento {

	/**
	 * TAD para alojar las motocicletas estacionadas.
	 */
	private TAD<Vehiculo> tadMotos;
	/**
	 * TAD para alojar los autos estacionados.
	 */
	private TAD<Vehiculo> tadAutos;

	private ArrayList<RegistroVehiculoEstacionado> autosEstacionados;
	private ArrayList<RegistroVehiculoEstacionado> motosEstacionadas;

	private float recaudacionTotalAutosEstacionados;
	private float recaudacionTotalMotocicletasEstacionados;

	/**
	 * Precio de la hora completa para motocicletas
	 */
	private float precioMotocicletasPorHora;
	/**
	 * Precio de la hora completa para autos
	 */
	private float precioAutosPorHora;

	/**
	 * Constructor del estacionamiento, recibe las capacidades de autos y motos y
	 * los precios por hora completas.
	 * 
	 * @param capacidadAutos
	 * @param capacidadMotocicletas
	 * @param precioAutosPorHora
	 * @param precioMotocicletasPorHora
	 */
	public Estacionamiento(int capacidadAutos, int capacidadMotocicletas, float precioAutosPorHora,
			float precioMotocicletasPorHora) {
		this.tadAutos = new PilaAL<>(capacidadAutos);
		this.tadMotos = new PilaAL<>(capacidadMotocicletas);
		this.setPrecioAutosPorHora(precioAutosPorHora);
		this.setPrecioMotocicletasPorHora(precioMotocicletasPorHora);
		this.autosEstacionados = new ArrayList<>();
		this.motosEstacionadas = new ArrayList<>();
		this.recaudacionTotalAutosEstacionados = 0;
		this.recaudacionTotalMotocicletasEstacionados = 0;
	}

	/**
	 * Setea el precio de la hora de auto, debe ser mayor a 0
	 * 
	 * @param precioAutos the precioAutos to set
	 */
	private void setPrecioAutosPorHora(float precioAutosPorHora) {
		if (precioAutosPorHora <= 0) {
			throw new IllegalArgumentException("Precio de auto inválido");
		}
		this.precioAutosPorHora = precioAutosPorHora;
	}

	/**
	 * Setea el precio de la hora de la motocicleta , debe ser mayor a 0
	 * 
	 * @param precioMotocicletas the precioMotocicletas to set
	 */
	private void setPrecioMotocicletasPorHora(float precioMotocicletasPorHora) {
		if (precioMotocicletasPorHora <= 0) {
			throw new IllegalArgumentException("Precio de motocicleta inválido");
		}
		this.precioMotocicletasPorHora = precioMotocicletasPorHora;
	}

	/**
	 * Permite estacionar un vehículo en el estacionamiento. La patente debe ser
	 * válida segun el tipo de vehículo el horario de ingreso debe ser válido
	 * 
	 * @param tipo
	 * @param patente
	 * @param horaIngreso
	 * @param minutosIngreso
	 */
	public void estacionar(TipoVehiculo tipo, String patente, Hora hora) {

		switch (tipo) {
		case AUTO:
			this.estacionarAuto(patente, hora);
			System.out.println("Se estacionó correctamente el auto patente: " + patente);
			break;
		case MOTOCICLETA:
			this.estacionarMoto(patente, hora);
			System.out.println("Se estacionó correctamente la motocicleta patente: " + patente);
			break;
		}
	}

	/**
	 * Permite estacionar una motocicleta.
	 * 
	 * @param patente
	 * @param horaIngreso
	 * @param minutosIngreso
	 */
	private void estacionarMoto(String patente, Hora hora) throws EstacionamientoException {
		Motocicleta moto = new Motocicleta(patente, hora, this.precioMotocicletasPorHora);
		try {
			this.tadMotos.push(moto);
		} catch (EstacionamientoException e) {
			throw new EstacionamientoException("Estacionamiento de motos completo");
		}

	}

	/**
	 * permite estacionar un auto
	 * 
	 * @param patente
	 * @param horaIngreso
	 * @param minutosIngreso
	 */
	private void estacionarAuto(String patente, Hora hora) throws EstacionamientoException {
		Auto auto = new Auto(patente, hora, this.precioAutosPorHora);
		try {
			this.tadAutos.push(auto);
		} catch (EstacionamientoException e) {
			throw new EstacionamientoException("Estacionamiento de autos completo");
		}

	}

	/**
	 * Retira un vehículo del estacionamiento. debe detectar el tipo de vehículo en
	 * base a su patente (ver diferencias entre la patente de los autos y las motos)
	 * el horario de egreso debe ser válido si el vehículo no está estacionado debe
	 * lanzar una excepción.
	 * 
	 * @param patente
	 * @param horaEgreso
	 * @param minutosEgreso
	 * @return
	 */
	public float retirar(String patente, Hora hora) {

		TipoVehiculo tipo = patente.matches(TipoVehiculo.MOTOCICLETA.getRegex()) ? TipoVehiculo.MOTOCICLETA
				: TipoVehiculo.AUTO;
		float importe = 0;
		Vehiculo encontrado = null;
		switch (tipo) {
		case AUTO:
			encontrado = this.retirar(patente, this.tadAutos);
			importe = encontrado.calcularImporte(hora);
			this.autosEstacionados.add(new RegistroVehiculoEstacionado(encontrado.getPatente(), importe));
			this.recaudacionTotalAutosEstacionados += importe;
			break;
		case MOTOCICLETA:
			encontrado = this.retirar(patente, this.tadMotos);
			importe = encontrado.calcularImporte(hora);
			this.motosEstacionadas.add(new RegistroVehiculoEstacionado(encontrado.getPatente(), importe));
			this.recaudacionTotalMotocicletasEstacionados += importe;
			break;
		}

		return importe;
	}

	/**
	 * Retira el vehículo de la TAD especificada
	 * 
	 * @param patente
	 * @param horaIngreso
	 * @param minutosIngreso
	 * @throws EstacionamientoException Cuando no se encuentra el vehículo
	 */
	private Vehiculo retirar(String patente, TAD<Vehiculo> tad) throws EstacionamientoException {
		Pila<Vehiculo> pilaAuxiliar = new PilaAL<>();
		Vehiculo encontrado = null;

		while (!tad.isEmpty() && encontrado == null) {
			Vehiculo v = tad.pop();
			if (v.getPatente().equals(patente)) {
				encontrado = v;
			} else {
				pilaAuxiliar.push(v);
			}
		}
		while (!pilaAuxiliar.isEmpty()) {
			Vehiculo v = pilaAuxiliar.pop();
			tad.push(v);
		}
		if (encontrado == null) {
			throw new EstacionamientoException("Vehículo con patente " + patente + " No encontrado");
		}

		return encontrado;
	}

	/**
	 * Muestra por pantalla el resumen del final del día (cantidad de autos y motos
	 * estacionados, total recaudado para autos y para motos y los listados de los
	 * autos y motos estacionados durante el día con el importe abonado por cada
	 * uno)
	 */
	public void finalizarDia() {
		System.out.println("\r\n--------- Resumen final del día --------------\r\n");
		System.out.printf("Se han estacionado %d autos\n", this.autosEstacionados.size());
		System.out.printf("Se han estacionado %d motocicletas\n", this.motosEstacionadas.size());
		System.out.println("\r\n----------------------------------------------");
		System.out.printf("Por estacionamiento de autos se ha recaudado $ %4.2f\n",
				this.recaudacionTotalAutosEstacionados);
		System.out.printf("Por estacionamiento de motocicletas se ha recaudado $ %4.2f\n",
				this.recaudacionTotalMotocicletasEstacionados);
		System.out.println("\r\n----------------------------------------------");
		System.out.println("Listado de autos estacionados");
		this.mostrarListadoFinalDia(this.autosEstacionados);
		System.out.println("\r\n----------------------------------------------");
		System.out.println("Listado de motocicletas estacionados");
		this.mostrarListadoFinalDia(this.motosEstacionadas);
		System.out.println("\r\n--------- Fin del reporte resumen final del día --------------\r\n");
	}

	/**
	 * @param motosEstacionadas2
	 */
	private void mostrarListadoFinalDia(ArrayList<RegistroVehiculoEstacionado> listado) {

		for (RegistroVehiculoEstacionado registro : listado) {
			System.out.printf("- Vehículo patente: %s - Importe abonado: %4.2f\n", registro.getPatente(),
					registro.getImporte());
		}
	}

	private class RegistroVehiculoEstacionado {

		private String patente;
		private float importe;

		/**
		 * @param patente
		 * @param importe
		 */
		public RegistroVehiculoEstacionado(String patente, float importe) {
			this.patente = patente;
			this.importe = importe;
		}

		/**
		 * @return the patente
		 */
		public String getPatente() {
			return patente;
		}

		/**
		 * @return the importe
		 */
		public float getImporte() {
			return importe;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "RegistroVehiculoEstacionado [patente=" + patente + ", importe=" + importe + "]";
		}

	}
}
