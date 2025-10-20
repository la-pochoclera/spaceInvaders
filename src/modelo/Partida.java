package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Partida {
	private NaveJugador naveJugador;
	private Oleada oleada;
	private List<Muro> muros;
	private List<Proyectil> proyectiles;
	private int nivelActual;
	private int anchoArea;
	private int altoArea;
	private boolean enEjecucion;
	private boolean terminada;
	private int puntuacion;
	private int proximoUmbralVidaExtra;

	private static final int ANCHO_DEFAULT = 800;
	private static final int ALTO_DEFAULT = 600;

	public Partida() {
		this.anchoArea = ANCHO_DEFAULT;
		this.altoArea = ALTO_DEFAULT;
		this.muros = new ArrayList<>();
		this.proyectiles = new ArrayList<>();
		this.puntuacion = 0;
		this.proximoUmbralVidaExtra = 500;
		this.terminada = false;
	}

	public void inicializar() {
		this.nivelActual = 1;
		this.enEjecucion = true;
		this.puntuacion = 0;

		// Crear jugador centrado en la parte inferior
		this.naveJugador = new NaveJugador(anchoArea / 2, altoArea - 50, 3);

		// Crear oleada
		this.oleada = new Oleada();
		this.oleada.inicializar(2, anchoArea);

		// Crear muros
		inicializarMuros();
	}

	private void inicializarMuros() {
		muros.clear();
		int[] posicionesX = { 200, 400, 600 };
		for (int x : posicionesX) {
			muros.add(new Muro(x, altoArea - 150));
		}
	}

	public void actualizarLogica() {
		if (!enEjecucion || terminada)
			return;

		// 1. Mover naves
		oleada.moverNaves();

		// 2. Disparo enemigo aleatorio
		// Contar proyectiles enemigos activos y pedir a la oleada que dispare si procede
		int proyectilesEnemigosActivos = 0;
		for (Proyectil p : proyectiles) {
			if (p.isActivo() && !p.isAliado())
				proyectilesEnemigosActivos++;
		}
		Proyectil disparoEnemigo = oleada.dispararAleatorio(proyectilesEnemigosActivos);
		if (disparoEnemigo != null)
			proyectiles.add(disparoEnemigo);

		// 3. Mover proyectiles
		for (Proyectil p : proyectiles)
			p.mover();

		// 4. Verificar colisiones
		verificarColisiones();

		// 5. Limpiar proyectiles inactivos o fuera de área
		limpiarProyectiles();

		// 6. Verificar vida extra
		verificarVidaExtra();

		// 7. Comprobar fin de nivel u otras condiciones
		if (oleada.todasDestruidas()) {
			nivelActual++;
			// bonus y reiniciar oleada con mayor velocidad
			puntuacion += 200;
			oleada.inicializar(2 + nivelActual, anchoArea);
			for (Muro m : muros)
				m.reiniciarMuro();
		}

		if (!naveJugador.estaVivo()) {
			terminada = true;
		}
	}

	public void procesarInput(int direccion, boolean disparar) {
		if (!enEjecucion || terminada)
			return;
		naveJugador.mover(direccion, anchoArea);
		if (disparar) {
			// Verificar si ya existe un proyectil aliado activo: el jugador sólo puede tener uno a la vez
			boolean aliadoActivo = false;
			for (Proyectil p : proyectiles) {
				if (p.isActivo() && p.isAliado()) {
					aliadoActivo = true;
					break;
				}
			}
			if (!aliadoActivo) {
				Proyectil pj = naveJugador.disparar();
				if (pj != null)
					proyectiles.add(pj);
			}
		}
	}

	private void verificarColisiones() {
		// Colisiones entre proyectiles aliados y naves invasoras, y con muros
		Iterator<Proyectil> it = proyectiles.iterator();
		while (it.hasNext()) {
			Proyectil p = it.next();
			if (!p.isActivo())
				continue;

			if (p.isAliado()) {
				// con naves
				for (NaveInvasora n : oleada.getNaves()) {
					if (n.isViva() && proximidad(p.getPosX(), p.getPosY(), n.getPosX(), n.getPosY(), 20)) {
						n.recibirImpacto();
						p.destruir();
						puntuacion += 10;
						naveJugador.sumarPuntos(10);
						break;
					}
				}
				if (!p.isActivo())
					continue;
				// con muros
				for (Muro m : muros) {
					for (SegmentoMuro s : m.getSegmentos()) {
						if (!s.estaDestruido() && proximidad(p.getPosX(), p.getPosY(), s.getPosX(), s.getPosY(), 15)) {
							s.recibirImpacto(true);
							p.destruir();
							break;
						}
					}
					if (!p.isActivo())
						break;
				}
			} else {
				// proyectil enemigo: con jugador
				if (proximidad(p.getPosX(), p.getPosY(), naveJugador.getPosX(), naveJugador.getPosY(), 25)) {
					naveJugador.recibirImpacto();
					p.destruir();
					continue;
				}
				// con muros
				for (Muro m : muros) {
					for (SegmentoMuro s : m.getSegmentos()) {
						if (!s.estaDestruido() && proximidad(p.getPosX(), p.getPosY(), s.getPosX(), s.getPosY(), 15)) {
							s.recibirImpacto(false);
							p.destruir();
							break;
						}
					}
					if (!p.isActivo())
						break;
				}
			}
		}
	}

	private void limpiarProyectiles() {
		Iterator<Proyectil> iterator = proyectiles.iterator();
		while (iterator.hasNext()) {
			Proyectil p = iterator.next();
			if (!p.isActivo() || p.estaFueraDeArea(anchoArea, altoArea)) {
				iterator.remove();
			}
		}
	}

	private boolean proximidad(int x1, int y1, int x2, int y2, int umbral) {
		int dx = x1 - x2;
		int dy = y1 - y2;
		return dx * dx + dy * dy <= umbral * umbral;
	}

	private void verificarVidaExtra() {
		if (naveJugador.getPuntos() >= proximoUmbralVidaExtra) {
			naveJugador.ganarVidaExtra();
			proximoUmbralVidaExtra += 500;
		}
	}

	// Getters y utilidades
	public boolean estaTerminada() {
		return terminada;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public NaveJugador getNaveJugador() {
		return naveJugador;
	}

	public Oleada getOleada() {
		return oleada;
	}

	public List<Muro> getMuros() {
		return muros;
	}

	public List<Proyectil> getProyectiles() {
		return proyectiles;
	}
}