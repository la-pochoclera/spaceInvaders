package controlador;

import modelo.Espacio;
import modelo.Jugador;
import modelo.Observador;
import modelo.Rayo;

public class JuegoControlador {
	private Espacio espacio;
	
	public JuegoControlador(int anchoEspacio, int altoEspacio, int posicionNaveJugadorX, 
			int posicionNaveJugadorY, Observador observadorNave) {
		espacio = new Espacio(anchoEspacio, altoEspacio, posicionNaveJugadorX, 
			posicionNaveJugadorY, observadorNave);
	}
	
	public void moverNaveJugadorDerecha() {
		Jugador nave = espacio.getNaveJugador();
		nave.moverDerecha();
	}
	
	public void moverNaveJugadorIzquierda() {
		Jugador nave = espacio.getNaveJugador();
		nave.moverIzquierda();
	}
	
	public void disparar(Observador observador) {
		Jugador naveJugador = espacio.getNaveJugador();
		Rayo disparo = naveJugador.disparar(observador);
		espacio.agregar(disparo);
	}
	
	public void actualizarPosiciones() {
		espacio.acualizarPosiciones();
	}
}
