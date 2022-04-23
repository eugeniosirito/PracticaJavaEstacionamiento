package ar.edu.ort.tp1.parcial2.tad;

public class Nodo<T> {
	private T dato;
	private Nodo<T> next;
	
	public Nodo(T dato) {
		this.dato = dato;
		this.next = null;
	}

	/**
	 * @return El dato
	 */
	public T getDato() {
		return dato;
	}

	/**
	 * @return the siguiente
	 */
	public Nodo<T> getNext() {
		return next;
	}
	
	public void setNext(Nodo<T> next) {
		this.next = next;
	}
	
	/**
	 * @return the siguiente
	 */
	public boolean hasNext() {
		return next!=null;
	}
	
	
}
