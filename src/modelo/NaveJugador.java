package modelo;

public class NaveJugador {
	private int puntuacion;
	private int vidas;
	private int posX;
	private int posY;
	private int velocidad; // pixels por tick al moverse
	private int proximoUmbralVidaExtra;

	// Límites dinámicos del área de juego
	private int anchoMax; // por defecto 800
	private int altoMax; // por defecto 600

	public NaveJugador(int posX, int posY, int vidas) {
		this.posX = posX;
		this.posY = posY;
		this.vidas = vidas;
		this.puntuacion = 0;
		this.velocidad = 8;
		this.proximoUmbralVidaExtra = 500;
		this.anchoMax = 800;
		this.altoMax = 600;
	}

	public void setLimites(int ancho, int alto) {
		this.anchoMax = Math.max(1, ancho);
		this.altoMax = Math.max(1, alto);
		if (posX < 0)
			posX = 0;
		if (posX > anchoMax)
			posX = anchoMax;
		if (posY < 0)
			posY = 0;
		if (posY > altoMax)
			posY = altoMax;
	}
	/**
	 * Mueve la nave horizontalmente; direccion: -1 (izq), 0, 1 (der). Respeta
	 * límites básicos del área (0..800) para no salir de pantalla.
	 */
	public void mover(int direccion) {
		posX = posX + direccion * velocidad;
		if (posX < 0) {
			posX = 0;
		}
		if (posX > anchoMax) {
			posX = anchoMax;
		}
	}

	/**
	 * Crea un nuevo proyectil aliado desde la posición de la nave.
	 */
	public Proyectil disparar() {
		// Proyectil aliado, velocidad positiva hacia arriba
		return new Proyectil(posX, posY - 10, 10, true);
	}

	public void sumarPuntos(int puntos) {
		this.puntuacion += puntos;
	}

	public void recibirImpacto() {
		perderVida();
	}

	public void ganarVidaExtra() {
		vidas++;
		proximoUmbralVidaExtra =proximoUmbralVidaExtra + 500;
	}

	public void perderVida() {
		vidas--;
		if (vidas < 0) {
			vidas = 0;
		}
	}

	public boolean isVivo() {
		return vidas > 0;
	}

	boolean aplicarVidaExtra() {
		if (puntuacion >= proximoUmbralVidaExtra) {
			ganarVidaExtra();
			return true;
		}
		return false;
	}

	// Getters
	public int getPuntuacion() {
		return puntuacion;
	}

	public int getVidas() {
		return vidas;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
}