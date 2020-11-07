package data.grafoNoDirigido;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import data.*;;

public class MatrizAdyacencia<K, V extends IVertice<K>, A extends IArco> {

	private int tamMatriz;

	private ArcoMatriz<K, A>[][] matriz;

	private int delta;

	private Hashtable<K, Integer> posiciones;

	public MatrizAdyacencia() {
		tamMatriz = 10;
		delta = 20;
		matriz = new ArcoMatriz[10][10];
		posiciones = new Hashtable<K, Integer>();
	}

	public MatrizAdyacencia(int tamInicial, int delta) {
		tamMatriz = tamInicial;
		this.delta = delta;
		matriz = new ArcoMatriz[tamInicial][tamInicial];
		posiciones = new Hashtable<K, Integer>();
	}

	public void agregarVertice(K idVertice) {
		posiciones.put(idVertice, darNVertices());
		if (tamMatriz < darNVertices()) {
			redimencionarMatriz();
		}
	}

	public void eliminarVertice(K idVertice) throws VerticeNoExisteException {
		Integer v = posiciones.get(idVertice);
		if (v == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v);
		for (int c = v; c < darNVertices() - 1; c++) {
			for (int f = 0; f < darNVertices(); f++) {
				matriz[f][c] = matriz[f][c + 1];
			}
		}
		for (int f = v; f < darNVertices() - 1; f++) {
			for (int c = 0; c < darNVertices(); c++) {
				matriz[f][c] = matriz[f + 1][c];
			}
		}
		posiciones.remove(idVertice);
	}

	public void agregarArco(A arco, K v1, K v2) throws VerticeNoExisteException, ArcoYaExisteException {
		Integer posV1 = posiciones.get(v1);
		Integer posV2 = posiciones.get(v2);
		if (posV1 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v1);
		if (posV2 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v2);
		if (matriz[posV1][posV2] != null)
			throw new ArcoYaExisteException("Ya existe un arco entre esos dos vértices", v1, v2);
		matriz[posV1][posV2] = matriz[posV2][posV1] = new ArcoMatriz(arco, v1, v2);
	}

	public void eliminarArco(K v1, K v2) throws VerticeNoExisteException, ArcoNoExisteException {
		Integer posV1 = posiciones.get(v1);
		Integer posV2 = posiciones.get(v2);
		if (posV1 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v1);
		if (posV2 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v2);
		if (matriz[posV1][posV2] == null)
			throw new ArcoNoExisteException("No existe un arco entre esos dos vértices", v1, v2);
		matriz[posV1][posV2] = matriz[posV2][posV1] = null;
	}

	public void marcarArco(K v1, K v2) throws VerticeNoExisteException, ArcoNoExisteException {
		Integer posV1 = posiciones.get(v1);
		Integer posV2 = posiciones.get(v2);
		if (posV1 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v1);
		if (posV2 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v2);
		if (matriz[posV1][posV2].darArco() != null)
			matriz[posV1][posV2].marcar();
		else
			throw new ArcoNoExisteException("No existe un arco entre los vértices seleccionados", v1, v2);
	}

	public void desmarcarArco(K v1, K v2) throws VerticeNoExisteException, ArcoNoExisteException {
		Integer posV1 = posiciones.get(v1);
		Integer posV2 = posiciones.get(v2);
		if (posV1 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v1);
		if (posV2 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v2);
		if (matriz[posV1][posV2].darArco() != null)
			matriz[posV1][posV2].desmarcar();
		else
			throw new ArcoNoExisteException("No existe un arco entre los vértices seleccionados", v1, v2);
	}

	public void reiniciarMarcas() {
		for (int c = 0; c < darNVertices() - 1; c++) {
			for (int f = 0; f < darNVertices(); f++) {
				if (matriz[f][c] != null)
					matriz[f][c].desmarcar();
			}
		}
	}

	public boolean existeArco(K v1, K v2) throws VerticeNoExisteException {
		Integer posV1 = posiciones.get(v1);
		Integer posV2 = posiciones.get(v2);
		if (posV1 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v1);
		if (posV2 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v2);
		return matriz[posV1][posV2] != null;
	}

	public A darArco(K v1, K v2) throws VerticeNoExisteException, ArcoNoExisteException {
		Integer posV1 = posiciones.get(v1);
		Integer posV2 = posiciones.get(v2);
		if (posV1 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v1);
		if (posV2 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v2);
		if (matriz[posV1][posV2].darArco() != null)
			return (A) matriz[posV1][posV2].darArco();
		else
			throw new ArcoNoExisteException("No existe un arco entre los vértices seleccionados", v1, v2);
	}

	public List<A> darArcos() {
		List<A> arcos = new ArrayList<A>();
		for (int f = 0; f < darNVertices(); f++) {
			for (int c = f; c < darNVertices(); c++) {
				if (matriz[f][c] != null)
					arcos.add((A) matriz[f][c].darArco());
			}
		}
		return arcos;
	}

	protected List<ArcoMatriz<K, A>> darArcosMatriz() {
		List<ArcoMatriz<K, A>> arcos = new ArrayList<ArcoMatriz<K, A>>();
		for (int f = 0; f < darNVertices(); f++) {
			for (int c = f; c < darNVertices(); c++) {
				if (matriz[f][c] != null)
					arcos.add((ArcoMatriz<K, A>) matriz[f][c]);
			}
		}
		return arcos;
	}

	public int darNArcos() {
		return darArcos().size();
	}

	public int darPeso() {
		int peso = 0;
		for (int f = 0; f < darNVertices(); f++) {
			for (int c = f; c < darNVertices(); c++) {
				if (matriz[f][c] != null)
					peso += matriz[f][c].darArco().darPeso();
			}
		}
		return peso;
	}

	public boolean estaMarcado(K v1, K v2) throws VerticeNoExisteException, ArcoNoExisteException {
		Integer posV1 = posiciones.get(v1);
		Integer posV2 = posiciones.get(v2);
		if (posV1 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v1);
		if (posV2 == null)
			throw new VerticeNoExisteException("El vértice seleccionado no existe", v2);
		if (matriz[posV1][posV2].darArco() != null)
			return matriz[posV1][posV2].marcado();
		else
			throw new ArcoNoExisteException("No existe un arco entre los vértices seleccionados", v1, v2);
	}

	private void redimencionarMatriz() {
		ArcoMatriz[][] aux = matriz;
		matriz = new ArcoMatriz[tamMatriz + delta][tamMatriz + delta];
		for (Integer f = 0; f < tamMatriz; f++) {
			for (Integer c = 0; c < tamMatriz; c++) {
				matriz[f][c] = aux[f][c];
			}
		}
		tamMatriz += delta;
	}

	private int darNVertices() {
		return posiciones.size();
	}

}
