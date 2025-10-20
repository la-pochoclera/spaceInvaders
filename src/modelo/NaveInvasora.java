package modelo;

public class NaveInvasora {
	private int posX;
	private int posY;
	private int velocidad;
	private boolean viva;

	public NaveInvasora(int posX, int posY, int velocidad) {
		this.posX = posX;
		this.posY = posY;
		this.velocidad = velocidad;
		this.viva = true;
	}

	public void mover(int direccion) {
		posX += direccion * velocidad;
	}

	public void bajar(int deltaY) {
		posY += deltaY;
	}

	public void recibirImpacto() {
		morir();
	}

	public void morir() {
		viva = false;
	}

	public boolean isViva() {
		return viva;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
}