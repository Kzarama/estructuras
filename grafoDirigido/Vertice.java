package data.grafoDirigido;

import java.util.*;
import data.*;
import data.dijkstra.CaminosMinimos;
import data.matrizAdyacencia.MatrizAdyacencia;
import iterador.IteradorException;
import iterador.IteradorSimple;

public class Vertice<K, V extends IVertice<K>, A extends IArco> {

	private V infoVertice;

	private ArrayList<Arco<K, V, A>> predecesores;

	private ArrayList<Arco<K, V, A>> sucesores;

	private boolean marcado;

	public Vertice(V pInfoVertice) {
		infoVertice = pInfoVertice;
		sucesores = new ArrayList<Arco<K, V, A>>();
		predecesores = new ArrayList<Arco<K, V, A>>();
		marcado = false;
	}

	public K darId() {
		return infoVertice.darId();
	}

	public V darInfoVertice() {
		return infoVertice;
	}

	public ArrayList<Arco<K, V, A>> darSucesores() {
		return sucesores;
	}

	public ArrayList<Arco<K, V, A>> darPredecesores() {
		return predecesores;
	}

	public Arco<K, V, A> darArco(K idDestino) {
		for (int i = 0; i < sucesores.size(); i++) {
			Arco<K, V, A> arco = sucesores.get(i);
			if (idDestino.equals(arco.darVerticeDestino().darId())) {
				return arco;
			}
		}
		return null;
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

	public void eliminarArco(K idDestino) throws ArcoNoExisteException {
		Arco<K, V, A> arco = darArco(idDestino);
		if (arco == null) {
			throw new ArcoNoExisteException("El arco no existe", darId(), idDestino);
		}
		sucesores.remove(arco);
		arco.darVerticeDestino().eliminarArcoPredecesor(arco);
	}

	private void eliminarArcoPredecesor(Arco<K, V, A> arco) {
		predecesores.remove(arco);
	}

	public void agregarArco(Arco<K, V, A> arco) throws ArcoYaExisteException {
		K idDestino = arco.darVerticeDestino().darId();
		if (esSucesor(idDestino)) {
			throw new ArcoYaExisteException("El arco ya existe", darId(), idDestino);
		}
		sucesores.add(arco);
		arco.darVerticeDestino().agregarArcoPredecesor(arco);
	}

	private void agregarArcoPredecesor(Arco<K, V, A> arco) throws ArcoYaExisteException {
		predecesores.add(arco);
	}

	public void eliminarArcos() {
		sucesores.clear();
	}

	public boolean esSucesor(K idDestino) {
		return darArco(idDestino) != null;
	}

	public int darNumeroSucesores() {
		return sucesores.size();
	}

	public int darNumeroPredecesores() {
		return predecesores.size();
	}

	public boolean hayCamino(Vertice<K, V, A> destino) {
		if (infoVertice.darId().equals(destino.darId()))
			return true;
		else {
			marcar();
			for (Arco<K, V, A> arco : darSucesores()) {
				Vertice<K, V, A> vert = arco.darVerticeDestino();
				if (!vert.marcado() && vert.hayCamino(destino)) {
					return true;
				}
			}
		}
		return false;
	}

	public Camino<K, V, A> darCaminoMasCorto(Vertice<K, V, A> destino) {
		if (infoVertice.darId().equals(destino.darId()))
			return new Camino<K, V, A>(this.infoVertice);
		else {
			marcar();
			ArrayList<Arco<K, V, A>> sucesores = darSucesores();
			Camino<K, V, A> camino = null;
			Arco<K, V, A> arcoEnCamino = null;
			for (int i = 0; i < sucesores.size(); i++) {
				Arco<K, V, A> arco = sucesores.get(i);
				Vertice<K, V, A> vert = arco.darVerticeDestino();
				if (!vert.marcado()) {
					Camino<K, V, A> cam = vert.darCaminoMasCorto(destino);
					if (cam != null) {
						if (camino == null || cam.darLongitud() < camino.darLongitud()) {
							camino = cam;
							arcoEnCamino = arco;
						}
					}
				}
			}
			desmarcar();
			if (camino == null)
				return null;
			else {
				camino.agregarArcoComienzo(darInfoVertice(), arcoEnCamino.darInfoArco());
				return camino;
			}
		}
	}

	public Camino<K, V, A> darCaminoMasBarato(Vertice<K, V, A> destino) {
		if (infoVertice.darId().equals(destino.darId()))
			return new Camino<K, V, A>(this.infoVertice);
		else {
			marcar();
			ArrayList<Arco<K, V, A>> sucesores = darSucesores();
			Camino<K, V, A> camino = null;
			Arco<K, V, A> arcoEnCamino = null;
			for (int i = 0; i < sucesores.size(); i++) {
				Arco<K, V, A> arco = sucesores.get(i);
				Vertice<K, V, A> vert = arco.darVerticeDestino();
				if (!vert.marcado()) {
					Camino<K, V, A> cam = vert.darCaminoMasBarato(destino);
					if (cam != null) {
						if (camino == null
								|| cam.darCosto() + arco.darPeso() < camino.darCosto() + arcoEnCamino.darPeso()) {
							camino = cam;
							arcoEnCamino = arco;
						}
					}
				}
			}
			desmarcar();
			if (camino == null)
				return null;
			else {
				camino.agregarArcoComienzo(darInfoVertice(), arcoEnCamino.darInfoArco());
				return camino;
			}
		}
	}

	public boolean hayCiclo() {
		ArrayList<Arco<K, V, A>> sucesores = darSucesores();
		for (Arco<K, V, A> arco : sucesores) {
			Vertice<K, V, A> vert = arco.darVerticeDestino();
			if (vert.hayCamino(this)) {
				return true;
			}
		}
		return false;
	}

	public boolean hayCaminoHamilton(int longActual, int ordenGrafo) {
		longActual++;
		if (longActual == ordenGrafo)
			return true;
		else {
			marcar();
			for (Arco<K, V, A> arco : darSucesores()) {
				Vertice<K, V, A> vert = arco.darVerticeDestino();
				if (!vert.marcado() && vert.hayCaminoHamilton(longActual, ordenGrafo)) {
					return true;
				}
			}
			desmarcar();
			return false;
		}
	}

	public boolean hayCaminoEuler(int longitudActual, int nArcos, MatrizAdyacencia<K, V, A> matriz) {
		if (longitudActual == nArcos)
			return true;
		else {
			for (Arco<K, V, A> arco : darSucesores()) {
				try {
					if (!matriz.marcado(darId(), arco.darVerticeDestino().darId())) {
						matriz.marcarArco(darId(), arco.darVerticeDestino().darId());
						if (arco.darVerticeDestino().hayCaminoEuler(longitudActual + 1, nArcos, matriz))
							return true;
						matriz.desmarcarArco(darId(), arco.darVerticeDestino().darId());
					}
				} catch (ArcoNoExisteException e) {
				} catch (VerticeNoExisteException e) {
				}
			}
			return false;
		}
	}

	public boolean darCaminoHamilton(Camino<K, V, A> hamilton, int ordenGrafo) {
		if (hamilton.darLongitud() + 1 == ordenGrafo) {
			return true;
		} else {
			marcar();
			for (Arco<K, V, A> arco : darSucesores()) {
				Vertice<K, V, A> vert = arco.darVerticeDestino();
				if (!vert.marcado()) {
					hamilton.agregarArcoFinal(arco.darVerticeDestino().darInfoVertice(), arco.darInfoArco());
					if (vert.darCaminoHamilton(hamilton, ordenGrafo))
						return true;
					hamilton.eliminarUltimoArco();
				}
			}
			desmarcar();
		}
		return false;
	}

	public boolean darCaminoEuler(Camino<K, V, A> camino, int nArcos, MatrizAdyacencia<K, V, A> matriz) {
		if (camino.darLongitud() == nArcos)
			return true;
		else {
			for (Arco<K, V, A> arco : darSucesores()) {
				try {
					if (!matriz.marcado(darId(), arco.darVerticeDestino().darId())) {
						matriz.marcarArco(darId(), arco.darVerticeDestino().darId());
						camino.agregarArcoFinal(arco.darVerticeDestino().darInfoVertice(), arco.darInfoArco());
						if (arco.darVerticeDestino().darCaminoEuler(camino, nArcos, matriz))
							return true;
						matriz.desmarcarArco(darId(), arco.darVerticeDestino().darId());
						camino.eliminarUltimoArco();
					}
				} catch (ArcoNoExisteException e) {
				} catch (VerticeNoExisteException e) {
				}
			}
			return false;
		}
	}

	public boolean hayCicloHamilton(int longActual, int ordenGrafo, K origenCiclo) {
		longActual++;
		if (longActual == ordenGrafo && esSucesor(origenCiclo))
			return true;
		else {
			marcar();
			for (Arco<K, V, A> arco : darSucesores()) {
				Vertice<K, V, A> vert = arco.darVerticeDestino();
				if (!vert.marcado() && vert.hayCicloHamilton(longActual, ordenGrafo, origenCiclo)) {
					return true;
				}
			}
			desmarcar();
			return false;
		}
	}

	public boolean hayCicloEuler(int longActual, int nArcos, MatrizAdyacencia<K, V, A> matriz, K origenCiclo) {
		try {
			if (longActual + 1 == nArcos && esSucesor(origenCiclo) && !matriz.marcado(darId(), origenCiclo))
				return true;
			else {
				for (Arco<K, V, A> arco : darSucesores()) {
					if (!matriz.marcado(darId(), arco.darVerticeDestino().darId())) {
						matriz.marcarArco(darId(), arco.darVerticeDestino().darId());
						if (arco.darVerticeDestino().hayCicloEuler(longActual + 1, nArcos, matriz, origenCiclo))
							return true;
						matriz.desmarcarArco(darId(), arco.darVerticeDestino().darId());
					}
				}
				return false;
			}
		} catch (ArcoNoExisteException e) {
		} catch (VerticeNoExisteException e) {
		}
		return false;
	}

	public boolean darCicloHamilton(Camino<K, V, A> hamilton, int ordenGrafo) {
		V origenCamino = hamilton.darOrigen();
		if (hamilton.darLongitud() + 1 == ordenGrafo && esSucesor(origenCamino.darId())) {
			hamilton.agregarArcoFinal(darArco(origenCamino.darId()).darVerticeDestino().darInfoVertice(),
					darArco(origenCamino.darId()).darInfoArco());
			return true;
		} else {
			marcar();
			for (Arco<K, V, A> arco : darSucesores()) {
				Vertice<K, V, A> vert = arco.darVerticeDestino();
				if (!vert.marcado()) {
					hamilton.agregarArcoFinal(arco.darVerticeDestino().darInfoVertice(), arco.darInfoArco());
					if (vert.darCicloHamilton(hamilton, ordenGrafo))
						return true;
					hamilton.eliminarUltimoArco();
				}
			}
			desmarcar();
		}
		return false;
	}

	public boolean darCicloEuler(Camino<K, V, A> euler, int nArcos, MatrizAdyacencia<K, V, A> matriz) {
		K origenCiclo = euler.darOrigen().darId();
		int longActual = euler.darLongitud();
		try {
			if (longActual + 1 == nArcos && esSucesor(origenCiclo) && !matriz.marcado(darId(), origenCiclo)) {
				euler.agregarArcoFinal(darArco(origenCiclo).darVerticeDestino().darInfoVertice(),
						darArco(origenCiclo).darInfoArco());
				return true;
			} else {
				for (Arco<K, V, A> arco : darSucesores()) {
					if (!matriz.marcado(darId(), arco.darVerticeDestino().darId())) {
						matriz.marcarArco(darId(), arco.darVerticeDestino().darId());
						euler.agregarArcoFinal(arco.darVerticeDestino().darInfoVertice(), arco.darInfoArco());
						if (arco.darVerticeDestino().darCicloEuler(euler, nArcos, matriz))
							return true;
						matriz.desmarcarArco(darId(), arco.darVerticeDestino().darId());
						euler.eliminarUltimoArco();
					}

				}
				return false;
			}
		} catch (ArcoNoExisteException e) {
		} catch (VerticeNoExisteException e) {
		}
		return false;
	}

	public CaminosMinimos<K, V, A> dijkstra(CaminosMinimos<K, V, A> minimos) {
		Vertice<K, V, A> vert = minimos.darSiguienteVertice();
		while (vert != null) {
			minimos.recalcularCaminosEspeciales(vert);
			vert = minimos.darSiguienteVertice();
		}
		return minimos;
	}

	public void darRecorridoProfundidad(IteradorSimple<Vertice<K, V, A>> itera) {
		try {
			itera.agregar(this);
		} catch (IteradorException e) {
		}
		marcar();
		for (Arco<K, V, A> arco : darSucesores()) {
			Vertice<K, V, A> vert = arco.darVerticeDestino();
			if (!vert.marcado()) {
				vert.darRecorridoProfundidad(itera);
			}
		}
	}

	public void darArbolParcialRecubrimiento(GrafoDirigido<K, V, A> arbolPR) {
		marcar();
		for (Arco<K, V, A> sucesor : sucesores) {
			if (!sucesor.darVerticeDestino().marcado())
				try {
					arbolPR.agregarArco(darId(), sucesor.darVerticeDestino().darId(), sucesor.darInfoArco());
					sucesor.darVerticeDestino().darArbolParcialRecubrimiento(arbolPR);
				} catch (VerticeNoExisteException e) {
				} catch (ArcoYaExisteException e) {
				}
		}
	}

	public void marcarAdyacentes() {
		for (Arco<K, V, A> sucesor : sucesores) {
			if (!sucesor.darVerticeDestino().marcado()) {
				sucesor.darVerticeDestino().marcar();
				sucesor.darVerticeDestino().marcarAdyacentes();
			}
		}
		for (Arco<K, V, A> predecesor : predecesores) {
			if (!predecesor.darVerticeOrigen().marcado()) {
				predecesor.darVerticeOrigen().marcar();
				predecesor.darVerticeOrigen().marcarAdyacentes();
			}
		}
	}

}
