package ar.edu.ort.tp1.parcial2.tad;

public class PilaNodos<TipoElemento> implements Pila<TipoElemento> {

	private Nodo<TipoElemento> cabecera;
	private int limite;
	private int cantidad;

	public PilaNodos() {
		this(SIN_LIMITE);
	}

	public PilaNodos(int limite) {
		if (limite != SIN_LIMITE && limite < LIMITE_MINIMO_POSIBLE) {
			throw new IllegalArgumentException(ERR_TAM_PILA_ILEGAL);
		}
		this.limite = limite;
		this.cabecera = null;
		this.cantidad = 0;
	}

	@Override
	public void push(TipoElemento elemento) {
		if (isFull()) {
			throw new RuntimeException(ERR_PILA_LLENA);
		}
		Nodo<TipoElemento> nuevoNodo = new Nodo<TipoElemento>(elemento);
		nuevoNodo.setNext(cabecera);
		cabecera = nuevoNodo;
		cantidad++;
	}

	@Override
	public TipoElemento pop() {
		if (isEmpty()) {
			throw new RuntimeException(ERR_PILA_VACIA);
		}
		Nodo<TipoElemento> aux = cabecera;
		cabecera = cabecera.getNext();
		cantidad--;
		return aux.getDato();
	}

	@Override
	public TipoElemento peek() {
		if (isEmpty()) {
			throw new RuntimeException(ERR_PILA_VACIA);
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