package modelo;

public class NaveJugador {
	private String nombre;
	private int puntuacion;
	private int vidas;
	private int posX;
	private int posY;
	private int velocidad; // pixels por tick al moverse
	private int proximoUmbralVidaExtra;

	public NaveJugador(int posX, int posY, int vidas) {
		this.posX = posX;
		this.posY = posY;
		this.vidas = vidas;
		this.puntuacion = 0;
		this.velocidad = 8;
		this.nombre = "Jugador123";
		this.proximoUmbralVidaExtra = 500;
	}

	/**
	 * Mueve la nave horizontalmente; direccion: -1 (izq), 0, 1 (der). Respeta
	 * límites básicos del área (0..800) para no salir de pantalla.
	 */
	public void mover(int direccion) {
		posX =posX+ direccion * velocidad;
		
		if (posX < 0) {
			posX = 0;
		}
		if (posX > 800) {// límite por defecto (coincide con ANCHO_DEFAULT)
			posX = 800;
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
		proximoUmbralVidaExtra += 500;
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

	boolean aplicarVidaExtraSiCorresponde() {
		if (puntuacion >= proximoUmbralVidaExtra) {
			ganarVidaExtra();
			return true;
		}
		return false;
	}

	// Getters
	public String getNombre() {
		return nombre;
	}

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