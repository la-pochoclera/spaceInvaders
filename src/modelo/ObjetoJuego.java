package modelo;

public abstract class ObjetoJuego {
	private int x;
	private int y;
	private int velocidad;
	private Observador observador;
	private int anchoEspacio;
	private int xMax;
	
	public ObjetoJuego(int x, int y, int velocidad, Observador observador, int anchoEspacio) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.observador = observador;
		observador.mover(x, y);
		this.anchoEspacio = anchoEspacio;
		this.xMax = anchoEspacio - observador.getAncho();
	}
	
	public void mover(int x, int y) {
		if (x < xMax && x > 0) {
			this.x = x;
			this.y = y;
			observador.mover(x, y);
		}
	}
	
	public void moverDerecha() {
		mover(x + velocidad, y);
	}
	
	public void moverIzquierda() {
		mover(x - velocidad, y);
	}
		
	public void moverArriba() {
		mover(x, y - velocidad);
	}
	
	public void mover(int x) {
		mover(x, y);
	}

	public int getPosicionMediaX() {
		return x + observador.getAncho()/2;
	}
	
	public int getY() {
		return y;
	}

	public int getAnchoEspacio() {
		return xMax + observador.getAncho();
	}
}