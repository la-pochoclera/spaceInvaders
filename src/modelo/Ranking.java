package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import views.EntradaRankingView;

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

	public List<EntradaRankingView> getEntradas() {
		List<EntradaRankingView> vistas = new ArrayList<>();
		for (EntradaRanking entrada : entradas) {
			vistas.add(entrada.toView());
		}
		return vistas;
	}
}