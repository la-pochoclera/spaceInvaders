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
	private Dificultad dificultad;

	private static final int ANCHO_DEFAULT = 800;
	private static final int ALTO_DEFAULT = 600;

	public Partida() {
		this(Dificultad.CADETE);
	}

	public Partida(Dificultad dificultad) {
		this.anchoArea = ANCHO_DEFAULT;
		this.altoArea = ALTO_DEFAULT;
		this.muros = new ArrayList<>();
		this.proyectiles = new ArrayList<>();
		this.terminada = false;
		this.dificultad = dificultad != null ? dificultad : Dificultad.CADETE;
	}

	public void inicializar() {
		this.nivelActual = 1;
		this.enEjecucion = true;

		// centrado en la parte inferior
		this.naveJugador = new NaveJugador(anchoArea / 2, altoArea - 50, 3);

		// Crear oleada
		this.oleada = new Oleada();
		this.oleada.inicializar(ajustarVelocidad(calcularVelocidadBaseNivel()), anchoArea);

		// Crear muros
		inicializarMuros();
	}

	private void inicializarMuros() {
		muros.clear();
		// 4 muros: 1/5, 2/5, 3/5, 4/5 del ancho
		int[] posicionesX = { anchoArea / 5, (anchoArea * 2) / 5, (anchoArea * 3) / 5, (anchoArea * 4) / 5 };
		for (int x : posicionesX) {
			Muro m = new Muro(x, altoArea - 150);
			m.inicializar();
			muros.add(m);
		}
	}

	public void actualizarLogica() {
		if (!enEjecucion || terminada)
			return;

		// 1. Mover naves
		oleada.moverNaves();

		// 1.1 Colisión invasores-jugador => termina la partida
		if (ColisionInvasoresConJugador()) {
			terminada = true;
			return;
		}

		// 2. Disparo enemigo aleatorio
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

		// 6. Vida extra
		verificarVidaExtra();

		// 7. Fin de oleada
		if (oleada.todasDestruidas()) {
			nivelActual++;
			naveJugador.sumarPuntos(200);
			cambiarNivel();
			for (Muro m : muros)
				m.reiniciarMuro();
		}

		// Game over por quedarse sin vidas
		if (!naveJugador.isVivo()) {
			terminada = true;
		}
	}

	public void procesarInput(int direccion, boolean disparar) {
		if (!enEjecucion || terminada)
			return;
		naveJugador.mover(direccion);
		if (disparar) { // Solo un proyectil aliado activo a la vez
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

	public boolean estaTerminada() {
		return terminada;
	}

	public int getPuntuacion() {
		if (naveJugador != null) {
			return naveJugador.getPuntuacion();
		} else {
			return 0;
		}
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

	// UML: agregarProyectil
    public void agregarProyectil(Proyectil proyectil) {
        if (proyectil != null)
            this.proyectiles.add(proyectil);
    }

    // verificarColisiones entre proyectiles y naves/muros/jugador
    public void verificarColisiones() {
        for (Proyectil p : proyectiles) {
            if (!p.isActivo())
                continue;

            if (p.isAliado()) {
                for (NaveInvasora n : oleada.getNaves()) {
                    if (n.isViva() && proximidad(p.getPosX(), p.getPosY(), n.getPosX(), n.getPosY(), 20)) {
                        n.recibirImpacto();
                        p.destruir();
                        naveJugador.sumarPuntos(10);
                        break;
                    }
                }
                if (!p.isActivo())
                    continue;
                for (Muro m : muros) {
                    for (SegmentoMuro s : m.getSegmentos()) {
                        if (!s.estaDestruido() && proximidad(p.getPosX(), p.getPosY(), s.getPosX(), s.getPosY(), 15)) {
                            // Cambio: delegar el daño al muro completo (aplicarImpacto)
                            m.recibirImpacto(true);
                            p.destruir();
                            break;
                        }
                    }
                    if (!p.isActivo())
                        break;
                }
            } else {
                if (proximidad(p.getPosX(), p.getPosY(), naveJugador.getPosX(), naveJugador.getPosY(), 25)) {
                    naveJugador.recibirImpacto();
                    p.destruir();
                    continue;
                }
                for (Muro m : muros) {
                    for (SegmentoMuro s : m.getSegmentos()) {
                        if (!s.estaDestruido() && proximidad(p.getPosX(), p.getPosY(), s.getPosX(), s.getPosY(), 15)) {
                            // Cambio: delegar el daño al muro completo (aplicarImpacto)
                            m.recibirImpacto(false);
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

    public void limpiarProyectiles() {
        Iterator<Proyectil> iterator = proyectiles.iterator();
        while (iterator.hasNext()) {
            Proyectil p = iterator.next();
            if (!p.isActivo() || p.estaFueraDeArea(anchoArea, altoArea)) {
                iterator.remove();
            }
        }
    }
    
    // Cambia el nivel actual y ajusta la velocidad de las naves invasoras
	public void cambiarNivel() {
		if (oleada != null) {
			oleada.inicializar(calcularVelocidadNaves(), anchoArea);
		}
	}
	
    // Calcula la velocidad de las naves invasoras según el nivel actual
	public int calcularVelocidadNaves() {
		return 2 + nivelActual;
	}

	public void verificarVidaExtra() {
		if (naveJugador != null) {
			naveJugador.aplicarVidaExtra();
		}
	}
	private boolean ColisionInvasoresConJugador() {
		final int UMBRAL = 10;// distancia mínima para considerar colisión
		for (NaveInvasora n : oleada.getNaves()) {
			if (n.isViva()
					&& proximidad(n.getPosX(), n.getPosY(), naveJugador.getPosX(), naveJugador.getPosY(), UMBRAL)) {
				return true;
			}
		}
		return false;
	}
	private boolean proximidad(int x1, int y1, int x2, int y2, int umbral) {
		int dx = x1 - x2;
		int dy = y1 - y2;
		return dx * dx + dy * dy <= umbral * umbral;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}
	// a ver
	private int ajustarVelocidad(int velocidadBase) {
		return dificultad.aplicarIncremento(velocidadBase);
	}
	// a ver
	private int calcularVelocidadBaseNivel() {
		return Math.max(1, nivelActual + 1);
	}

}