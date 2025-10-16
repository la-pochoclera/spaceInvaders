package modelo;

public class Jugador extends ObjetoJuego {
	private int x;
	private int y;
	private int velocidad;
	private Observador observador;
	private int xMax;
	
	public Jugador(int x, int y, Observador observador,  int anchoEspacio) {
		super(x, y, 10, observador, anchoEspacio);
		this.x = x;
		this.y = y;
		velocidad = 10;
		this.observador = observador;
		observador.mover(x, y);
		this.xMax = anchoEspacio - observador.getAncho();
	}
	
	public Rayo disparar(Observador observadorDisparo) {
		int posicionX = getPosicionMediaX();
		Rayo disparo = new Rayo(posicionX, getY(), observadorDisparo, getAnchoEspacio());
		return disparo;
	}
}
