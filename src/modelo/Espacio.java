package modelo;

import java.util.ArrayList;

public class Espacio {
	private int ancho;
	private int alto;
	private Jugador jugador;
	private ArrayList<ObjetoJuegoActualizable> listaObjetoJuego = new ArrayList<>();
	
	
	public Espacio(int ancho, int alto, int posicionNaveJugadorX, int
		posicionNaveJugadorY, Observador imagenNave) {
		this.ancho = ancho;
		this.alto = alto;
		jugador = new Jugador(posicionNaveJugadorX, posicionNaveJugadorY,
		imagenNave, ancho);
	}
	
	public Jugador getNaveJugador() {
		return jugador;
	}
	
	public void agregar(ObjetoJuegoActualizable actualizable) {
		listaObjetoJuego.add(actualizable);
	}
	
	public void acualizarPosiciones() {
		for (ObjetoJuegoActualizable actualizable: listaObjetoJuego) {
			actualizable.actualizarPosicion();
		}
	}
}