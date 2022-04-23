package ar.edu.ort.tp1.parcial2.tad;

public class ColaNodos<TipoElemento> implements Cola<TipoElemento> {

	private Nodo<TipoElemento> cabecera;
	private Nodo<TipoElemento> cola;
	private int limite;
	private int cantidad;

	public ColaNodos() {
		this(SIN_LIMITE);
	}

	public ColaNodos(int limite) {
		if (limite != SIN_LIMITE && limite < LIMITE_MINIMO_POSIBLE) {
			throw new IllegalArgumentException(ERR_TAM_ILEGAL);
		}
		this.limite = limite;
		this.cabecera = null;
		this.cola = null;
		this.cantidad = 0;
	}

	@Override
	public void add(TipoElemento elemento) {
		if (isFull()) {
			throw new RuntimeException(ERR_COLA_LLENA);
		}
		Nodo<TipoElemento> nuevoNodo = new Nodo<TipoElemento>(elemento);
		if (isEmpty()) {
			cabecera = nuevoNodo;
		} else {
			cola.setNext(nuevoNodo);
		}
		cola = nuevoNodo;
		cantidad++;
	}

	@Override
	public TipoElemento remove() {
		if (isEmpty()) {
			throw new RuntimeException(ERR_COLA_VACIA);
		}
		Nodo<TipoElemento> aux = cabecera;
		cabecera = cabecera.getNext();
		cantidad--;
		return aux.getDato();
	}

	@Override
	public TipoElemento get() {
		if (isEmpty()) {
			throw new RuntimeException(ERR_COLA_VACIA);
		}
		return cabecera.getDato();
	}

	@Override
	public boolean isEmpty() {
		return cantidad == 0;
	}

	@Override
	public boolean isFull() {
		return cantidad == limite;
	}
}