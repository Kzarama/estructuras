package data.grafoDirigido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import data.*;
import data.clausuraTransitiva.ArcoCT;
import data.grafoDirigido.*;
import data.dijkstra.*;
import data.matrizAdyacencia.*;
import iterador.*;

public class GrafoDirigido<K, V extends IVertice<K>, A extends IArco> implements Serializable {
	public static final int INFINITO = -1;

	private HashMap<K, Vertice<K, V, A>> vertices;

	public GrafoDirigido() {
		vertices = new HashMap<K, Vertice<K, V, A>>();
	}

	public boolean existeVertice(K idVertice) {
		return vertices.get(idVertice) != null;
	}

	public List<V> darVertices() {
		List<V> vs = new ArrayList<V>();
		for (Vertice<K, V, A> v : vertices.values()) {
			vs.add(v.darInfoVertice());
		}
		return vs;
	}

	public Collection<Vertice<K, V, A>> darObjetosVertices() {
		return vertices.values();
	}

	public IArco darArco(K idVerticeOrigen, K idVerticeDestino) throws VerticeNoExisteException, ArcoNoExisteException {
		Vertice<K, V, A> vertice = darObjetoVertice(idVerticeOrigen);
		if (existeVertice(idVerticeDestino)) {
			Arco<K, V, A> arco = vertice.darArco(idVerticeDestino);
			if (arco == null)
				throw new ArcoNoExisteException("No existe un arco entre los vértices seleccionados", idVerticeOrigen,
						idVerticeDestino);
			else
				return arco.darInfoArco();
		} else
			throw new VerticeNoExisteException("Vértice destino no existe", idVerticeDestino);
	}

	public boolean existeArco(K idVerticeOrigen, K idVerticeDestino) throws VerticeNoExisteException {
		Vertice<K, V, A> vertice = darObjetoVertice(idVerticeOrigen);
		if (existeVertice(idVerticeDestino))
			return vertice.darArco(idVerticeDestino) != null;
		else
			throw new VerticeNoExisteException("Vértice destino no existe", idVerticeDestino);
	}

	public List<A> darArcos() {
		List<A> arcos = new ArrayList<A>();
		for (Vertice<K, V, A> vertice : vertices.values()) {
			for (Arco<K, V, A> arco : vertice.darSucesores())
				arcos.add(arco.darInfoArco());
		}
		return arcos;
	}

	public ArrayList<Arco<K, V, A>> darObjetosArco() {
		ArrayList<Arco<K, V, A>> arcos = new ArrayList<Arco<K, V, A>>();
		for (Vertice<K, V, A> vertice : vertices.values())
			arcos.addAll(vertice.darSucesores());
		return arcos;
	}

	public List<V> darSucesores(K idVertice) throws VerticeNoExisteException {
		List<V> lista = new ArrayList<V>();
		for (Arco<K, V, A> a : darObjetoVertice(idVertice).darSucesores()) {
			lista.add(a.darVerticeDestino().darInfoVertice());
		}
		return lista;
	}

	public List<V> darPredecesores(K idVertice) throws VerticeNoExisteException {
		List<V> lista = new ArrayList<V>();
		for (Arco<K, V, A> a : darObjetoVertice(idVertice).darPredecesores()) {
			lista.add(a.darVerticeDestino().darInfoVertice());
		}
		return lista;
	}

	public void agregarVertice(V elemento) throws VerticeYaExisteException {
		if (existeVertice(elemento.darId()))
			throw new VerticeYaExisteException("Elemento ya existe", elemento.darId());
		else {
			Vertice<K, V, A> vertice = new Vertice<K, V, A>(elemento);
			vertices.put(elemento.darId(), vertice);
		}
	}

	public void eliminarVertice(K idVertice) throws VerticeNoExisteException {
		Vertice<K, V, A> vertice = darObjetoVertice(idVertice);
		vertice.eliminarArcos();
		for (Vertice<K, V, A> vert : vertices.values()) {
			try {
				vert.eliminarArco(vertice.darId());
			} catch (ArcoNoExisteException e) {
			}
		}
		vertices.remove(vertice.darId());
	}

	public void agregarArco(K idVerticeOrigen, K idVerticeDestino, A infoArco)
			throws VerticeNoExisteException, ArcoYaExisteException {
		Vertice<K, V, A> verticeOrigen = darObjetoVertice(idVerticeOrigen);
		Vertice<K, V, A> verticeDestino = darObjetoVertice(idVerticeDestino);
		Arco<K, V, A> arco = new Arco<K, V, A>(verticeOrigen, verticeDestino, infoArco);
		verticeOrigen.agregarArco(arco);
	}

	public void eliminarArco(K idVerticeOrigen, K idVerticeDestino)
			throws VerticeNoExisteException, ArcoNoExisteException {
		Vertice<K, V, A> verticeOrigen = darObjetoVertice(idVerticeOrigen);
		verticeOrigen.eliminarArco(idVerticeDestino);
	}

	public int darNArcos() {
		return darArcos().size();
	}

	public int darOrden() {
		return vertices.size();
	}

	public boolean hayCamino(K idVerticeOrigen, K idVerticeDestino) throws VerticeNoExisteException {
		reiniciarMarcas();
		Vertice<K, V, A> verticeOrigen = darObjetoVertice(idVerticeOrigen);
		Vertice<K, V, A> verticeDestino = darObjetoVertice(idVerticeDestino);
		return verticeOrigen.hayCamino(verticeDestino);
	}

	public Camino<K, V, A> darCaminoMasCorto(K idVerticeOrigen, K idVerticeDestino) throws VerticeNoExisteException {
		reiniciarMarcas();
		Vertice<K, V, A> verticeOrigen = darObjetoVertice(idVerticeOrigen);
		Vertice<K, V, A> verticeDestino = darObjetoVertice(idVerticeDestino);
		return verticeOrigen.darCaminoMasCorto(verticeDestino);
	}

	public Camino<K, V, A> darCaminoMasBarato(K idVerticeOrigen, K idVerticeDestino) throws VerticeNoExisteException {
		reiniciarMarcas();
		Vertice<K, V, A> verticeOrigen = darObjetoVertice(idVerticeOrigen);
		Vertice<K, V, A> verticeDestino = darObjetoVertice(idVerticeDestino);
		return verticeOrigen.darCaminoMasBarato(verticeDestino);
	}

	public boolean hayCiclo(K idVertice) throws VerticeNoExisteException {
		reiniciarMarcas();
		Vertice<K, V, A> vertice = darObjetoVertice(idVertice);
		return vertice.hayCiclo();
	}

	public boolean hayCaminoHamilton() {
		for (Vertice<K, V, A> vertice : vertices.values()) {
			reiniciarMarcas();
			if (vertice.hayCaminoHamilton(0, darOrden()))
				return true;
		}
		return false;
	}

	public boolean hayCicloHamilton() {
		for (Vertice<K, V, A> vertice : vertices.values()) {
			reiniciarMarcas();
			if (vertice.hayCicloHamilton(0, darOrden(), vertice.darId()))
				return true;
			else
				return false;
		}
		return false;
	}

	public Camino<K, V, A> darCaminoHamilton() {
		for (Vertice<K, V, A> vertice : vertices.values()) {
			reiniciarMarcas();
			Camino<K, V, A> hamilton = new Camino<K, V, A>(vertice.darInfoVertice());
			if (vertice.darCaminoHamilton(hamilton, darOrden()))
				return hamilton;
		}
		return null;
	}

	public Camino<K, V, A> darCicloHamilton() {
		for (Vertice<K, V, A> vertice : vertices.values()) {
			reiniciarMarcas();
			Camino<K, V, A> ch = new Camino<K, V, A>(vertice.darInfoVertice());
			if (vertice.darCicloHamilton(ch, darOrden()))
				return ch;
			else
				return null;
		}
		return null;
	}

	public CaminosMinimos<K, V, A> dijkstra(K idVertice) throws VerticeNoExisteException {
		reiniciarMarcas();
		Vertice<K, V, A> vertice = darObjetoVertice(idVertice);
		CaminosMinimos<K, V, A> minimos = new CaminosMinimos<K, V, A>(vertice, darObjetosVertices());
		return vertice.dijkstra(minimos);
	}

	public boolean esConexo() {
		reiniciarMarcas();
		return contarComponentesConexos() <= 1 ? true : false;
	}

	public boolean esFuertementeConexo() {
		for (Vertice<K, V, A> v : vertices.values()) {
			for (Vertice<K, V, A> v2 : vertices.values())
				try {
					if (v != v2 && !hayCamino(v.darId(), v2.darId()))
						return false;
				} catch (VerticeNoExisteException e) {
				}
		}
		return true;
	}

	public boolean esAciclico() {
		for (Vertice<K, V, A> vertice : vertices.values()) {
			try {
				reiniciarMarcas();
				if (hayCiclo(vertice.darId()))
					return false;
			} catch (VerticeNoExisteException e) {
			}
		}
		return true;
	}

	public boolean hayCaminoEuler() {
		MatrizAdyacencia<K, V, A> matriz = new MatrizAdyacencia<K, V, A>(this);
		int nArcos = darNArcos();
		for (Vertice<K, V, A> vertice : vertices.values()) {
			matriz.reiniciarMarcas();
			if (vertice.hayCaminoEuler(0, nArcos, matriz))
				return true;
		}
		return false;
	}

	public Camino<K, V, A> darCaminoEuler() {
		MatrizAdyacencia<K, V, A> matriz = new MatrizAdyacencia<K, V, A>(this);
		int nArcos = darNArcos();
		for (Vertice<K, V, A> vertice : vertices.values()) {
			matriz.reiniciarMarcas();
			Camino<K, V, A> euler = new Camino<K, V, A>(vertice.darInfoVertice());
			if (vertice.darCaminoEuler(euler, nArcos, matriz))
				return euler;
		}
		return null;
	}

	public boolean hayCicloEuler() {
		MatrizAdyacencia<K, V, A> matriz = new MatrizAdyacencia<K, V, A>(this);
		for (Vertice<K, V, A> vertice : vertices.values()) {
			reiniciarMarcas();
			if (vertice.hayCicloEuler(0, darNArcos(), matriz, vertice.darId()))
				return true;
		}
		return false;
	}

	public Camino<K, V, A> darCicloEuler() {
		MatrizAdyacencia<K, V, A> matriz = new MatrizAdyacencia<K, V, A>(this);
		for (Vertice<K, V, A> vertice : vertices.values()) {
			reiniciarMarcas();
			Camino<K, V, A> euler = new Camino<K, V, A>(vertice.darInfoVertice());
			if (vertice.darCicloEuler(euler, darNArcos(), matriz))
				return euler;
		}
		return null;
	}

	public boolean esCompleto() {
		for (Vertice<K, V, A> v : vertices.values()) {
			for (Vertice<K, V, A> v2 : vertices.values()) {
				if (!v.darId().equals(v2.darId()) && !(v.esSucesor(v2.darId()) || v2.esSucesor(v.darId())))
					return false;
			}
		}
		return true;
	}

	public Iterador<Vertice<K, V, A>> darRecorridoPlano() {
		IteradorSimple<Vertice<K, V, A>> itera = new IteradorSimple<Vertice<K, V, A>>(vertices.size());
		for (Vertice<K, V, A> v : vertices.values()) {
			try {
				itera.agregar(v);
			} catch (IteradorException e) {
			}
		}
		return itera;
	}

	public Iterador<Vertice<K, V, A>> darRecorridoProfundidad() {
		IteradorSimple<Vertice<K, V, A>> itera = new IteradorSimple<Vertice<K, V, A>>(vertices.size());
		for (Vertice<K, V, A> v : vertices.values()) {
			if (!v.marcado())
				v.darRecorridoProfundidad(itera);
		}
		return itera;
	}

	public Iterador<Vertice<K, V, A>> darRecorridoNiveles() {
		IteradorSimple<Vertice<K, V, A>> itera = new IteradorSimple<Vertice<K, V, A>>(vertices.size());
		Queue<Vertice<K, V, A>> frenteExploracion = new LinkedList<Vertice<K, V, A>>();
		for (Vertice<K, V, A> v : vertices.values()) {
			if (!v.marcado()) {
				frenteExploracion.add(v);
				while (frenteExploracion.size() != 0) {
					try {
						Vertice<K, V, A> actual = frenteExploracion.poll();
						if (!actual.marcado()) {
							actual.marcar();
							itera.agregar(actual);
							for (Arco<K, V, A> a : actual.darSucesores()) {
								Vertice<K, V, A> sucesor = a.darVerticeDestino();
								if (!sucesor.marcado())
									frenteExploracion.add(sucesor);
							}
						}
					} catch (IteradorException e) {
					}
				}
			}
		}
		return itera;
	}

	public Vertice<K, V, A> darCentro() {
		int menorExcentricidad = 0;
		Vertice<K, V, A> centro = null;
		for (Vertice<K, V, A> vertice : vertices.values()) {
			try {
				int excen = darExcentricidad(vertice.darId());
				if (centro == null && excen != INFINITO) {
					centro = vertice;
					menorExcentricidad = excen;
				} else if (excen != INFINITO && menorExcentricidad > excen) {
					centro = vertice;
					menorExcentricidad = excen;
				}
			} catch (VerticeNoExisteException e) {
			}
		}
		return centro;
	}

	public int darExcentricidad(K idVertice) throws VerticeNoExisteException {
		CaminosMinimos<K, V, A> cm = dijkstra(idVertice);
		Integer mayorPeso = null;
		for (Vertice<K, V, A> destino : vertices.values()) {
			int costo = cm.darCostoCamino(destino);
			if (costo == -1)
				return INFINITO;
			if (mayorPeso == null)
				mayorPeso = costo;
			else if (mayorPeso < costo) {
				mayorPeso = costo;
			}
		}
		return mayorPeso.intValue();
	}

	public int darPeso() {
		int peso = 0;
		List<A> arcos = darArcos();
		for (int i = 0; i < arcos.size(); i++) {
			peso += arcos.get(i).darPeso();
		}
		return peso;
	}

	public GrafoDirigido<K, V, A> darArbolParcialRecubrimiento(K idVertice) throws VerticeNoExisteException {
		Vertice<K, V, A> vertice = darObjetoVertice(idVertice);
		reiniciarMarcas();
		GrafoDirigido<K, V, A> arbolPR = new GrafoDirigido<K, V, A>();
		vertice.darArbolParcialRecubrimiento(arbolPR);
		return arbolPR;
	}

	public GrafoDirigido<K, V, ArcoCT> darClausuraTransitiva() {
		GrafoDirigido<K, V, ArcoCT> cTransitiva = new GrafoDirigido<K, V, ArcoCT>();
		for (Vertice<K, V, A> vertice : vertices.values()) {
			try {
				cTransitiva.agregarVertice(vertice.darInfoVertice());
			} catch (VerticeYaExisteException e) {
			}
		}
		for (Vertice<K, V, A> vertice : vertices.values()) {
			try {
				CaminosMinimos<K, V, A> cMinimos = dijkstra(vertice.darId());
				for (Vertice<K, V, A> destino : vertices.values()) {
					int costoMinimo = cMinimos.darCostoCamino(destino);
					if (!destino.darId().equals(vertice.darId()) && costoMinimo != INFINITO) {
						ArcoCT arc = new ArcoCT(costoMinimo);
						cTransitiva.agregarArco(vertice.darId(), destino.darId(), arc);
					}
				}
			} catch (VerticeNoExisteException e) {
			} catch (ArcoYaExisteException e) {
			}
		}
		return cTransitiva;
	}

	public int contarComponentesConexos() {
		int compConexos = 0;

		reiniciarMarcas();

		for (Vertice<K, V, A> vertice : vertices.values()) {
			if (!vertice.marcado()) {
				vertice.marcarAdyacentes();
				compConexos++;
			}
		}
		return compConexos;
	}

	private Vertice<K, V, A> darObjetoVertice(K idVertice) throws VerticeNoExisteException {
		Vertice<K, V, A> vertice = vertices.get(idVertice);
		if (vertice == null) {
			throw new VerticeNoExisteException("El vértice buscado no existe en el grafo", idVertice);
		}
		return vertice;
	}

	public V darVertice(K idVertice) throws VerticeNoExisteException {
		return darObjetoVertice(idVertice).darInfoVertice();
	}

	private void reiniciarMarcas() {
		for (Vertice<K, V, A> vertice : vertices.values()) {
			vertice.desmarcar();
		}
	}

}
