package modelo;

public abstract class ObjetoJuegoActualizable extends ObjetoJuego {
	public ObjetoJuegoActualizable(int x, int y, int velocidad, Observador observador,
		int anchoEspacio) {
		super(x, y, velocidad, observador, anchoEspacio);
	}
	public abstract void actualizarPosicion();
}