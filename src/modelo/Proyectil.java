package modelo;

public class Proyectil {
	private int posX;
	private int posY;
	private int velocidad; // pixels por tick
	private boolean esAliado;
	private boolean activo;

	public Proyectil(int posX, int posY, int velocidad, boolean esAliado) {
		this.posX = posX;
		this.posY = posY;
		this.velocidad = velocidad;
		this.esAliado = esAliado;
		this.activo = true;
	}

	/**
	 * Actualiza la posición del proyectil. Los aliados se mueven hacia arriba (y-),
	 * los enemigos hacia abajo (y+).
	 */
	public void mover() {
		if (!activo) {
			return;
		}
		if (esAliado) {
			posY -= velocidad;
		} else {
			posY += velocidad;
		}
	}

	public void destruir() {
		this.activo = false;
	}

	public boolean estaFueraDeArea(int ancho, int alto) {
		return posX < 0 || posX > ancho || posY < 0 || posY > alto;
	}

	// Getters / setters
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public boolean isAliado() {
		return esAliado;
	}

	public boolean isActivo() {
		return activo;
	}
}