package modelo;

import views.EntradaRankingView;

public class EntradaRanking {
	private String nombre;
	private int puntuacion;

	public EntradaRanking(String nombre, int puntuacion) {
		this.nombre = nombre;
		this.puntuacion = puntuacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int p) {
		this.puntuacion = p;
	}

	public EntradaRankingView toView() {
		return new EntradaRankingView(nombre, puntuacion);
	}
}