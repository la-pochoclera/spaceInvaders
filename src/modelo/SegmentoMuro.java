package modelo;

public class SegmentoMuro {
	private int posX;
	private int posY;
	private double salud; // 1.0 = 100%
	private int ancho;
	private int alto;

	public SegmentoMuro(int posX, int posY, int ancho, int alto) {
		this.posX = posX;
		this.posY = posY;
		this.ancho = ancho;
		this.alto = alto;
		this.salud = 1.0; // siempre visible al inicio
	}

	public boolean estaDestruido() {
		return salud <= 0.0;
	}

	public void reiniciar() {
		this.salud = 1.0;
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


	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	// Setter para que Muro controle cuáles segmentos están activos
	public void setSalud(double salud) {
		this.salud = salud <= 0.0 ? 0.0 : 1.0;
	}
}