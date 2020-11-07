package data.grafoDirigido;

import data.*;

public class Arco<K, V extends IVertice<K>, A extends IArco> {

	private Vertice<K, V, A> origen;

	private Vertice<K, V, A> destino;

	private A infoArco;

	public Arco(Vertice<K, V, A> pOrigen, Vertice<K, V, A> pDestino, A pInfoArco) {
		origen = pOrigen;
		destino = pDestino;
		infoArco = pInfoArco;
	}

	public A darInfoArco() {
		return infoArco;
	}

	public Vertice<K, V, A> darVerticeDestino() {
		return destino;
	}

	public Vertice<K, V, A> darVerticeOrigen() {
		return origen;
	}

	public int darPeso() {
		return infoArco.darPeso();
	}
}
