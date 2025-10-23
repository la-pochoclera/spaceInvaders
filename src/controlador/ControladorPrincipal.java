package controlador;

import modelo.Sistema;
import modelo.Partida;

public class ControladorPrincipal {
	private Sistema sistema;
	private Partida partidaActual;

	public ControladorPrincipal() {
		this.sistema = new Sistema();
		this.partidaActual = null;
	}

	public void cargarCreditos(int cantidad) {
		sistema.cargarCreditos(cantidad);
	}

	/**
	 * Intenta iniciar una nueva partida: comprueba créditos, los consume y crea e
	 * inicializa la partida.
	 */
	public void iniciarNuevaPartida() {
		if (!sistema.tieneCreditos()) {
			System.out.println("No hay créditos disponibles.");
			return;
		}
		sistema.consumirCredito();
		partidaActual = new Partida();
		partidaActual.inicializar();
		System.out.println("Partida iniciada.");
	}

	/**
	 * Representa un tick del juego: delega la actualización a la partida actual y
	 * finaliza si es necesario.
	 */
	public void actualizar() {
		if (partidaActual == null)
			return;
		partidaActual.actualizarLogica();
		if (partidaActual.estaTerminada()) {
			finalizarPartida(false);
		}
	}

	/**
	 * Procesa la entrada (dirección y disparo) delegando a la partida.
	 */
	public void procesarInput(int direccion, boolean disparar) {
		if (partidaActual == null)
			return;
		partidaActual.procesarInput(direccion, disparar);
	}

	/**
	 * Finaliza la partida actual: actualiza ranking y pone partidaActual a null.
	 * El parámetro "voluntariamente" permite distinguir un fin voluntario del involuntario,
	 * pero en esta implementación no altera la lógica de negocio (compatibilidad con comportamiento previo).
	 */
	public void finalizarPartida(boolean voluntariamente) {
		if (partidaActual == null)
			return;
		int puntos = partidaActual.getPuntuacion();
		sistema.actualizarRanking("Jugador", puntos);
		System.out.println("Partida finalizada. Puntos: " + puntos + (voluntariamente ? " (voluntaria)" : ""));
		partidaActual = null;
	}

	public void finalizarPartida() {
		finalizarPartida(false);
	}

	public boolean estaPartidaActiva() {
		return partidaActual != null && !partidaActual.estaTerminada();
	}

	public Sistema getSistema() {
		return sistema;
	}

	public Partida getPartidaActual() {
		return partidaActual;
	}
}