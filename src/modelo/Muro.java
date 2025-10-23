package modelo;

import java.util.ArrayList;
import java.util.List;

public class Muro {
	private int posX;
	private int posY;
	private List<SegmentoMuro> segmentos;

	/**
	 * Crea un muro centrado en (posX, posY). Se compone de una matriz de segmentos.
	 */
	public Muro(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.segmentos = new ArrayList<>();
	}

	// UML: inicializar(): construye los segmentos
	public void inicializar() {
		segmentos.clear();
		// Crear una matriz 5x3 de segmentos (ancho x alto)
		int cols = 5;
		int rows = 3;
		int segAncho = 10;
		int segAlto = 6;
		int spacingX = 12; // separación entre segmentos
		int spacingY = 8;
		int startX = posX - (cols / 2) * spacingX;
		int startY = posY - (rows / 2) * spacingY;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				boolean esTronera = (r == 1 && c == 2); // ejemplo: una tronera central en el muro
				int x = startX + c * spacingX;
				int y = startY + r * spacingY;
				segmentos.add(new SegmentoMuro(x, y, segAncho, segAlto, esTronera));
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

	// UML: segmentoEnPosicion(x,y)
	public SegmentoMuro segmentoEnPosicion(int x, int y) {
		for (SegmentoMuro s : segmentos) {
			if (!s.estaDestruido()) {
				int left = s.getPosX() - s.getAncho() / 2;
				int right = s.getPosX() + s.getAncho() / 2;
				int top = s.getPosY() - s.getAlto() / 2;
				int bottom = s.getPosY() + s.getAlto() / 2;
				if (x >= left && x <= right && y >= top && y <= bottom) {
					return s;
				}
			}
		}
		return null;
	}
}