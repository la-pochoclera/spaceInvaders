package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ranking {
	private List<EntradaRanking> entradas;

	public Ranking() {
		this.entradas = new ArrayList<>();
	}

	public void agregarOActualizarEntrada(String nombre, int puntuacion) {
		// Buscar existente
		for (EntradaRanking e : entradas) {
			if (e.getNombre().equalsIgnoreCase(nombre)) {
				if (puntuacion > e.getPuntuacion()) {
					e.setPuntuacion(puntuacion);
				}
				ordenarRanking();
				return;
			}
		}
		// No existe -> añadir
		entradas.add(new EntradaRanking(nombre, puntuacion));
		ordenarRanking();
	}

	public void ordenarRanking() {
		Collections.sort(entradas, Comparator.comparingInt(EntradaRanking::getPuntuacion).reversed());
	}

	public List<EntradaRanking> getEntradas() {
		return new ArrayList<>(entradas);
	}
}