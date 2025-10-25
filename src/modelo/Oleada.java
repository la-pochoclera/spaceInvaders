package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Oleada {
	private List<NaveInvasora> naves;
	private int direccion; // 1 = derecha, -1 = izquierda

	private int velocidadBase;
	private int anchoArea;
	private Random rnd;
	// Control de disparos enemigos
	private int maxProyectilesEnemigos = 5; // límite máximo simultáneo
	private long lastDisparoTime = 0; // timestamp en ms del último disparo
	private long baseCooldownMs = 1500; // cooldown base entre disparos
	private long lowNavesCooldownMs = 2500; // cooldown cuando quedan pocas naves (<=3)

	public Oleada() {
		this.naves = new ArrayList<>();
		this.direccion = 1;
		this.rnd = new Random();
	}

	public void inicializar(int velocidadBase, int anchoArea) {
		this.velocidadBase = velocidadBase;
		this.anchoArea = anchoArea;
		naves.clear();
		// reset disparos
		this.lastDisparoTime = 0;

		// Crear formación de 3 filas x 5 columnas = 15 naves
		int filas = 3;
		int cols = 5;
		int spacingX = 60;
		int spacingY = 40;
		int startX = 100;
		int startY = 50;
		for (int r = 0; r < filas; r++) {
			for (int c = 0; c < cols; c++) {
				int x = startX + c * spacingX;
				int y = startY + r * spacingY;
				naves.add(new NaveInvasora(x, y, velocidadBase));
			}
		}
	}

	public void moverNaves() {
		// Mover naves horizontalmente según la dirección.
		for (NaveInvasora n : naves) {
			if (n.isViva()) {
				n.mover(direccion);
			}
		}

		// Si alcanzamos un límite, aplicar lógico para cambiar dirección y bajar naves.
		if (verificarLimites()) {
			cambiarDireccion();
			bajarNaves();
		}
	}

	public boolean verificarLimites() {
		for (NaveInvasora n : naves) {
			if (!n.isViva()) {
				continue;
			}
			if (n.getPosX() <= 10 && direccion < 0) {
				return true;
			}
			if (n.getPosX() >= anchoArea - 10 && direccion > 0) {
				return true;
			}
		}
		return false;
	}

	public void cambiarDireccion() {
		direccion = direccion * -1;
	}

	public void bajarNaves() {
		for (NaveInvasora n : naves) {
			if (n.isViva()) {
				n.bajar(20); // bajar 20 píxeles
			}
		}
	}

	/**
	 * Intento de disparo de un enemigo. Se respeta un límite máximo de proyectiles
	 * enemigos simultáneos y un cooldown dinámico que incrementa cuando quedan
	 * pocas naves.
	 * 
	 * @param proyectilesEnemigosActivos cantidad actual de proyectiles enemigos
	 *                                   activos en pantalla
	 * @return nuevo Proyectil enemigo o null si no se puede disparar ahora
	 */
	public Proyectil dispararAleatorio(int proyectilesEnemigosActivos) {
		// Si ya hay el máximo de proyectiles enemigos, no disparar
		if (proyectilesEnemigosActivos >= maxProyectilesEnemigos)
			return null;

		// Recopilar naves vivas
		List<NaveInvasora> vivas = new ArrayList<>();
		for (NaveInvasora n : naves) {
			if (n.isViva())
				vivas.add(n);
		}

		if (vivas.isEmpty())
			return null;

		// Determinar cooldown dinámico según número de naves vivas
		int nVivas = vivas.size();
		long cooldown = baseCooldownMs;
		if (nVivas <= 3) {
			cooldown = lowNavesCooldownMs;
		}

		long ahora = System.currentTimeMillis();
		if (ahora - lastDisparoTime < cooldown) {
			return null; // todavía en cooldown
		}

		// Seleccionar atacante aleatorio entre las vivas y delegar disparo
		NaveInvasora atacante = vivas.get(rnd.nextInt(vivas.size()));
		Proyectil p = atacante.disparar();
		lastDisparoTime = ahora;
		return p;
	}

	public List<NaveInvasora> obtenerNavesVivas() {
		List<NaveInvasora> res = new ArrayList<>();
		for (NaveInvasora n : naves)
			if (n.isViva())
				res.add(n);
		return res;
	}

	public boolean todasDestruidas() {
		for (NaveInvasora n : naves) {
			if (n.isViva())
				return false;
		}
		return true;
	}

	public List<NaveInvasora> getNaves() {
		return naves;
	}
}