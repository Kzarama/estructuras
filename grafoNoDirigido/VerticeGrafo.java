package data.grafoNoDirigido;

import data.*;

public class VerticeGrafo<K, V extends IVertice<K>> {

	private V vertice;

	private boolean marcado;

	public VerticeGrafo(V vertice) {
		this.vertice = vertice;
		marcado = false;
	}

	public V darInfoVertice() {
		return vertice;
	}

	public boolean estaMarcado() {
		return marcado;
	}

	public void marcar() {
		marcado = true;
	}

	public void desmarcar() {
		marcado = false;
	}
}
