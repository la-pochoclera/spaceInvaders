package modelo;

public class SegmentoMuro {
	private int posX;
	private int posY;
	private double salud; // 1.0 = 100%
	private boolean esTronera;
	private int ancho;
	private int alto;

	public SegmentoMuro(int posX, int posY, int ancho, int alto, boolean esTronera) {
		this.posX = posX;
		this.posY = posY;
		this.ancho = ancho;
		this.alto = alto;
		this.esTronera = esTronera;
		this.salud = esTronera ? 0.0 : 1.0; // si es tronera, no se puede dañar (vacío)
	}

	/**
	 * Aplica el impacto sobre el segmento. Si es tronera, no recibe daño. Si el
	 * proyectil es aliado se reduce un 10% (0.10), si es enemigo un 5% (0.05).
	 */
	public void recibirImpacto(boolean esAliado) {
		if (esTronera || estaDestruido()) {
			return;
		}
		double porcentaje = esAliado ? 0.10 : 0.05; // 10% aliado, 5% enemigo
		salud -= porcentaje;
		if (salud < 0)
			salud = 0;
	}

	public boolean estaDestruido() {
		return salud <= 0.0;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public double getSalud() {
		return salud;
	}

	public boolean isTronera() {
		return esTronera;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void reiniciar() {
		this.salud = esTronera ? 0.0 : 1.0;
	}
}