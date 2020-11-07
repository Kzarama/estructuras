package data.grafoNoDirigido;

import data.*;

public class ArcoMatriz<K, A extends IArco> {

	private boolean marcado;

	private A arco;

	private K vertice1;

	private K vertice2;

	public ArcoMatriz(A arco, K vertice1, K vertice2) {
		marcado = false;
		this.arco = arco;
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
	}

	public boolean marcado() {
		return marcado;
	}

	public void marcar() {
		marcado = true;
	}

	public void desmarcar() {
		marcado = false;
	}

	public A darArco() {
		return arco;
	}

	public K darVertice1() {
		return vertice1;
	}

	public K darVertice2() {
		return vertice2;
	}
}
