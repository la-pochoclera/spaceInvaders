package modelo;

public class Rayo extends ObjetoJuegoActualizable {
	public Rayo(int x, int y, Observador observador, int anchoEspacio) {
		super(x, y, 10, observador, anchoEspacio);
	}
	
	public void actualizarPosicion() {
		moverArriba();
	}
}