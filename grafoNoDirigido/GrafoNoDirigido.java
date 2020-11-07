package data.grafoNoDirigido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import data.*;

public class GrafoNoDirigido<K, V extends IVertice<K>, A extends IArco> implements Serializable {

	private MatrizAdyacencia<K, V, A> matrizAdyacencia;

	private List<VerticeGrafo<K, V>> vertices;

	public GrafoNoDirigido() {
		matrizAdyacencia = new MatrizAdyacencia<K, V, A>();
		vertices = new ArrayList<VerticeGrafo<K, V>>();
	}

	public void agregarVertice(V elemento) throws VerticeYaExisteException {
		try {
			darVertice(elemento.darId());
			throw new VerticeYaExisteException("El vértice ya existe", elemento.darId());
		} catch (VerticeNoExisteException e) {
			vertices.add(new VerticeGrafo<K, V>(elemento));
			matrizAdyacencia.agregarVertice(elemento.darId());
		}
	}

	public void eliminarVertice(K idVertice) throws VerticeNoExisteException {
		matrizAdyacencia.eliminarVertice(idVertice);
		for (int i = 0; i < vertices.size(); i++) {
			V vert = vertices.get(i).darInfoVertice();
			if (vert.darId().equals(idVertice)) {
				vertices.remove(i);
				return;
			}
		}
	}

	public void agregarArco(K idVerticeOrigen, K idVerticeDestino, A infoArco)
			throws VerticeNoExisteException, ArcoYaExisteException {
		matrizAdyacencia.agregarArco(infoArco, idVerticeOrigen, idVerticeDestino);
	}

	public void eliminarArco(K idV1, K idV2) throws VerticeNoExisteException, ArcoNoExisteException {
		matrizAdyacencia.eliminarArco(idV1, idV2);

	}

	public V darVertice(K idVertice) throws VerticeNoExisteException {
		for (int i = 0; i < vertices.size(); i++) {
			V vert = vertices.get(i).darInfoVertice();
			if (vert.darId().equals(idVertice))
				return vert;
		}
		throw new VerticeNoExisteException("El vértice buscado no existe", idVertice);
	}

	public boolean existeVertice(K idVertice) {
		for (int i = 0; i < vertices.size(); i++) {
			V vert = vertices.get(i).darInfoVertice();
			if (vert.darId().equals(idVertice))
				return true;
		}
		return false;
	}

	public List<V> darVertices() {
		List<V> vs = new ArrayList<V>();
		for (int i = 0; i < vertices.size(); i++) {
			vs.add(vertices.get(i).darInfoVertice());
		}
		return vs;
	}

	public int darOrden() {
		return vertices.size();
	}

	public boolean existeArco(K v1, K v2) throws VerticeNoExisteException {
		return matrizAdyacencia.existeArco(v1, v2);
	}

	public A darArco(K v1, K v2) throws VerticeNoExisteException, ArcoNoExisteException {
		return matrizAdyacencia.darArco(v1, v2);
	}

	public int darNArcos() {
		return matrizAdyacencia.darNArcos();
	}

	public List<A> darArcos() {
		return matrizAdyacencia.darArcos();
	}

	public int darPeso() {
		return matrizAdyacencia.darPeso();
	}

	public List<V> darSucesores(K idVertice) throws VerticeNoExisteException {
		List<V> sucesores = new ArrayList<V>();
		for (int i = 0; i < vertices.size(); i++) {
			V vertice2 = vertices.get(i).darInfoVertice();
			if (matrizAdyacencia.existeArco(idVertice, vertice2.darId()))
				sucesores.add(vertice2);
		}
		return sucesores;
	}

	public boolean hayCamino(K idVerticeOrigen, K idVerticeDestino) throws VerticeNoExisteException {
		reiniciarMarcasArcos();
		return hayCaminoRecursivo(idVerticeOrigen, idVerticeDestino);
	}

	public Camino<K, V, A> darCaminoMasCorto(K idVerticeOrigen, K idVerticeDestino) throws VerticeNoExisteException {
		reiniciarMarcasVertices();
		reiniciarMarcasArcos();
		return darCaminoMasCortoRecursivo(idVerticeOrigen, idVerticeDestino);
	}

	public Camino<K, V, A> darCaminoMasBarato(K idVerticeOrigen, K idVerticeDestino) throws VerticeNoExisteException {
		reiniciarMarcasVertices();
		reiniciarMarcasArcos();
		return darCaminoMasBaratoRecursivo(idVerticeOrigen, idVerticeDestino);
	}

	public boolean hayCiclo(K idVertice) throws VerticeNoExisteException {
		reiniciarMarcasArcos();
		return hayCaminoRecursivo(idVertice, idVertice);
	}

	public boolean esAciclico() {
		for (int i = 0; i < vertices.size(); i++) {
			try {
				if (hayCiclo(vertices.get(i).darInfoVertice().darId()))
					return true;
			} catch (VerticeNoExisteException e) {
			}
		}
		return false;
	}

	public boolean esCompleto() {
		try {
			for (int i = 0; i < vertices.size(); i++)
				for (int j = i + 1; j < vertices.size(); j++)
					if (!matrizAdyacencia.existeArco(vertices.get(i).darInfoVertice().darId(),
							vertices.get(j).darInfoVertice().darId()))
						return false;
		} catch (VerticeNoExisteException e) {
		}
		return true;
	}

	public boolean esConexo() {
		reiniciarMarcasVertices();
		if (vertices.size() != 0) {
			try {
				K idVert = vertices.get(0).darInfoVertice().darId();
				marcarSucesoresAProfundidad(idVert);
				for (int i = 0; i < vertices.size(); i++) {
					K idVertice = vertices.get(i).darInfoVertice().darId();
					if (!estaMarcadoVertice(idVertice))
						return false;
				}
				return true;
			} catch (VerticeNoExisteException e) {
				return false;
			}
		} else {
			return true;
		}
	}

	public GrafoNoDirigido<K, V, A> prim(K idOrigen) throws VerticeNoExisteException {
		GrafoNoDirigido<K, V, A> prim = new GrafoNoDirigido<K, V, A>();
		reiniciarMarcasVertices();
		try {
			prim.agregarVertice(darVertice(idOrigen));
			marcarVertice(idOrigen);
			while (prim.darOrden() != darOrden()) {
				K menorVertice = null;
				K origenCamino = null;
				int menorPeso = darPeso();
				for (int ver = 0; ver < prim.vertices.size(); ver++) {
					V verticePrim = prim.vertices.get(ver).darInfoVertice();
					List<V> sucesores = darSucesores(verticePrim.darId());
					for (int suc = 0; suc < sucesores.size(); suc++) {
						V sucesor = sucesores.get(suc);
						if (!estaMarcadoVertice(sucesor.darId())) {
							int peso = darArco(verticePrim.darId(), sucesor.darId()).darPeso();
							if (peso <= menorPeso) {
								menorVertice = sucesor.darId();
								menorPeso = peso;
								origenCamino = verticePrim.darId();
							}
						}
					}
				}
				prim.agregarVertice(darVertice(menorVertice));
				prim.agregarArco(origenCamino, menorVertice, darArco(origenCamino, menorVertice));
				marcarVertice(menorVertice);
			}
		} catch (VerticeYaExisteException e) {
		} catch (ArcoNoExisteException e) {
		} catch (ArcoYaExisteException e) {
		}
		return prim;
	}

	public GrafoNoDirigido<K, V, A> kruskal() {
		GrafoNoDirigido<K, V, A> kruskal = new GrafoNoDirigido<K, V, A>();
		reiniciarMarcasVertices();
		reiniciarMarcasArcos();
		boolean termino = false;
		while (!termino) {
			List<ArcoMatriz<K, A>> arcos = matrizAdyacencia.darArcosMatriz();
			ArcoMatriz<K, A> menorArco = null;
			try {
				for (int iA = 0; iA < arcos.size(); iA++) {
					ArcoMatriz<K, A> arco = arcos.get(iA);
					K v1 = arco.darVertice1();
					K v2 = arco.darVertice2();
					if (!estaMarcadoArco(arco.darVertice1(), arco.darVertice2()) && !kruskal.hayCamino(v1, v2)
							&& (menorArco == null || menorArco.darArco().darPeso() > arco.darArco().darPeso()))
						menorArco = arco;
				}
				if (menorArco == null)
					termino = true;
				else {
					try {
						marcarArco(menorArco.darVertice1(), menorArco.darVertice2());
						if (!kruskal.existeVertice(menorArco.darVertice1()))
							kruskal.agregarVertice(darVertice(menorArco.darVertice1()));
						if (!kruskal.existeVertice(menorArco.darVertice2()))
							kruskal.agregarVertice(darVertice(menorArco.darVertice2()));
						kruskal.agregarArco(menorArco.darVertice1(), menorArco.darVertice2(), menorArco.darArco());
					} catch (VerticeYaExisteException e) {
					} catch (ArcoYaExisteException e) {
					}
				}
			} catch (VerticeNoExisteException e) {
			} catch (ArcoNoExisteException e) {
			}
		}
		return kruskal;
	}

	private void reiniciarMarcasVertices() {
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).desmarcar();
		}
	}

	private void reiniciarMarcasArcos() {
		matrizAdyacencia.reiniciarMarcas();
	}

	private void marcarVertice(K idVertice) throws VerticeNoExisteException {
		for (int i = 0; i < vertices.size(); i++) {
			VerticeGrafo<K, V> vert = vertices.get(i);
			if (vert.darInfoVertice().darId().equals(idVertice)) {
				vert.marcar();
				return;
			}
		}
		throw new VerticeNoExisteException("El vértice buscado no existe", idVertice);
	}

	private void marcarArco(K v1, K v2) throws VerticeNoExisteException, ArcoNoExisteException {
		matrizAdyacencia.marcarArco(v1, v2);
	}

	private void desmarcarVertice(K idVertice) throws VerticeNoExisteException {
		for (int i = 0; i < vertices.size(); i++) {
			VerticeGrafo<K, V> vert = vertices.get(i);
			if (vert.darInfoVertice().darId().equals(idVertice)) {
				vert.desmarcar();
				return;
			}
		}
		throw new VerticeNoExisteException("El vértice buscado no existe", idVertice);
	}

	private boolean estaMarcadoVertice(K idVertice) throws VerticeNoExisteException {
		for (int i = 0; i < vertices.size(); i++) {
			VerticeGrafo<K, V> vert = vertices.get(i);
			if (vert.darInfoVertice().darId().equals(idVertice)) {
				return vert.estaMarcado();
			}
		}
		throw new VerticeNoExisteException("El vértice buscado no existe", idVertice);
	}

	private boolean estaMarcadoArco(K v1, K v2) throws VerticeNoExisteException, ArcoNoExisteException {
		return matrizAdyacencia.estaMarcado(v1, v2);
	}

	private Camino<K, V, A> darCaminoMasCortoRecursivo(K v1, K v2) throws VerticeNoExisteException {
		try {
			if (matrizAdyacencia.existeArco(v1, v2) && !estaMarcadoArco(v1, v2)) {
				Camino<K, V, A> camino = new Camino<K, V, A>(darVertice(v1));
				camino.agregarArcoFinal(darVertice(v2), matrizAdyacencia.darArco(v1, v2));
				return camino;
			} else {
				marcarVertice(v1);
				List<V> sucesores = darSucesores(v1);
				Camino<K, V, A> camino = null;
				V sucesorCorto = null;
				for (int i = 0; i < sucesores.size(); i++) {
					V vert = sucesores.get(i);
					if (!estaMarcadoVertice(vert.darId())) {
						marcarArco(v1, vert.darId());
						Camino<K, V, A> camAux = darCaminoMasCortoRecursivo(vert.darId(), v2);
						if (camAux != null && (camino == null || camAux.darLongitud() < camino.darLongitud())) {
							camino = camAux;
							sucesorCorto = vert;
						}
					}
				}
				desmarcarVertice(v1);
				if (camino == null)
					return null;
				else {
					camino.agregarArcoComienzo(darVertice(v1), matrizAdyacencia.darArco(v1, sucesorCorto.darId()));
					return camino;
				}
			}
		} catch (ArcoNoExisteException e) {
			return null;
		}
	}

	private Camino<K, V, A> darCaminoMasBaratoRecursivo(K v1, K v2) throws VerticeNoExisteException {
		try {
			if (matrizAdyacencia.existeArco(v1, v2) && !estaMarcadoArco(v1, v2)) {
				Camino<K, V, A> camino = new Camino<K, V, A>(darVertice(v1));
				camino.agregarArcoFinal(darVertice(v2), matrizAdyacencia.darArco(v1, v2));
				return camino;
			} else {
				marcarVertice(v1);
				List<V> sucesores = darSucesores(v1);
				Camino<K, V, A> camino = null;
				V sucesorBarato = null;
				A arcoBarato = null;
				for (int i = 0; i < sucesores.size(); i++) {
					V vert = sucesores.get(i);
					if (!estaMarcadoVertice(vert.darId())) {
						marcarArco(v1, vert.darId());
						Camino<K, V, A> camAux = darCaminoMasBaratoRecursivo(vert.darId(), v2);
						if (camAux != null && (camino == null || camAux.darCosto()
								+ darArco(v1, vert.darId()).darPeso() < camino.darCosto() + arcoBarato.darPeso())) {
							camino = camAux;
							sucesorBarato = vert;
							arcoBarato = darArco(v1, vert.darId());
						}
					}
				}
				desmarcarVertice(v1);
				if (camino == null)
					return null;
				else {
					camino.agregarArcoComienzo(darVertice(v1), matrizAdyacencia.darArco(v1, sucesorBarato.darId()));
					return camino;
				}
			}
		} catch (ArcoNoExisteException e) {
			return null;
		}
	}

	private boolean hayCaminoRecursivo(K vertActual, K vertCierre) throws VerticeNoExisteException {
		List<V> sucesores = darSucesores(vertActual);
		for (int i = 0; i < sucesores.size(); i++) {
			try {
				V sucesor = sucesores.get(i);
				if (!estaMarcadoArco(vertActual, sucesor.darId())) {
					if (sucesor.darId().equals(vertCierre))
						return true;
					else {
						marcarArco(vertActual, sucesor.darId());
						if (hayCaminoRecursivo(sucesor.darId(), vertCierre))
							return true;
					}
				}
			} catch (ArcoNoExisteException e) {
			}
		}
		return false;
	}

	private void marcarSucesoresAProfundidad(K idVertice) throws VerticeNoExisteException {
		marcarVertice(idVertice);
		List<V> sucesores = darSucesores(idVertice);
		for (int i = 0; i < sucesores.size(); i++) {
			K idSucesor = sucesores.get(i).darId();
			if (!estaMarcadoVertice(idSucesor)) {
				marcarSucesoresAProfundidad(idSucesor);
			}
		}
	}

}
