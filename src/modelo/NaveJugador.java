package modelo;

public class NaveJugador {
	private int posX;
	private int posY;
	private int velocidad; // pixels por tick al moverse
	private int vidas;
	private int puntos;

	public NaveJugador(int posX, int posY, int vidas) {
		this.posX = posX;
		this.posY = posY;
		this.vidas = vidas;
		this.puntos = 0;
		this.velocidad = 8;
	}

	/**
	 * Mueve la nave horizontalmente; direccion: -1 (izq), 0, 1 (der).
	 */
	public void mover(int direccion, int anchoArea) {
		posX += direccion * velocidad;
		if (posX < 0) {
			posX = 0;
		}
		if (posX > anchoArea) {
			posX = anchoArea;
		}
	}

	/**
	 * Crea un nuevo proyectil aliado desde la posición de la nave.
	 */
	public Proyectil disparar() {
		// Proyectil aliado, velocidad positiva hacia arriba
		return new Proyectil(posX, posY - 10, 10, true);
	}

	public void sumarPuntos(int pts) {
		puntos += pts;
	}

	public void recibirImpacto() {
		vidas--;
		if (vidas < 0) {
			vidas = 0;
		}
	}

	public void ganarVidaExtra() {
		vidas++;
	}

	public boolean estaVivo() {
		return vidas > 0;
	}

	// Getters
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getVidas() {
		return vidas;
	}

	public int getPuntos() {
		return puntos;
	}
}