package modelo;

import java.util.ArrayList;
import java.util.List;

public class Muro {
	private int origenX;
	private int origenY;
	private List<SegmentoMuro> segmentos;

	/**
	 * Crea un muro centrado en (origenX, origenY). Se compone de una matriz de
	 * segmentos.
	 */
	public Muro(int origenX, int origenY) {
		this.origenX = origenX;
		this.origenY = origenY;
		this.segmentos = new ArrayList<>();
		construirMuro();
	}

	private void construirMuro() {
		segmentos.clear();
		// Crear una matriz 5x3 de segmentos (ancho x alto)
		int cols = 5;
		int rows = 3;
		int spacingX = 12; // separación entre segmentos
		int spacingY = 8;
		int startX = origenX - (cols / 2) * spacingX;
		int startY = origenY - (rows / 2) * spacingY;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				boolean esTronera = (r == 1 && c == 2); // ejemplo: una tronera central en el muro
				int x = startX + c * spacingX;
				int y = startY + r * spacingY;
				segmentos.add(new SegmentoMuro(x, y, esTronera));
			}
		}
	}

	public List<SegmentoMuro> getSegmentos() {
		return segmentos;
	}

	public void reiniciarMuro() {
		for (SegmentoMuro s : segmentos)
			s.reiniciar();
	}
}