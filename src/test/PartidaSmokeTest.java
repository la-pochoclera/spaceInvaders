package test;

import java.util.List;

import modelo.NaveInvasora;
import modelo.Oleada;
import modelo.Partida;
import modelo.Proyectil;
import modelo.Dificultad;

public class PartidaSmokeTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando test rápido de Partida y Oleada...");
        Partida p = new Partida(Dificultad.CADETE);
        p.inicializar();

        // 1) Jugador solo un proyectil
        System.out.println("-- Test jugador 1 proyectil --");
        p.procesarInput(0, true);
        int aliados = countAliados(p);
        System.out.println("Aliados tras primer disparo: " + aliados);
        p.procesarInput(0, true);
        aliados = countAliados(p);
        System.out.println("Aliados tras segundo disparo inmediato: " + aliados);
        if (aliados != 1) {
			System.out.println("ERROR: El jugador tiene más de un proyectil activo.");
		} else {
			System.out.println("OK: El jugador tiene solo un proyectil activo.");
		}

        // 2) Enemigos: disparos con cooldown y límite
        System.out.println("-- Test disparos enemigos (limite y cooldown) --");
        // limpiar proyectiles aliados para simplificar conteo
        Thread.sleep(100);
        // For the purpose of testing, we'll call actualizarLogica repetidamente with sleeps
        int maxExpected = 3;
        for (int i = 0; i < 5; i++) {
            p.actualizarLogica();
            int enem = countEnemigos(p);
            System.out.println("Iter " + i + " - proyectiles enemigos: " + enem);
            if (enem >= maxExpected) {
				break;
			}
            // esperar suficiente para superar cooldown base
            Thread.sleep(1600);
        }
        int enemAct = countEnemigos(p);
        System.out.println("Proyectiles enemigos después de intentos: " + enemAct);
        if (enemAct > maxExpected) {
			System.out.println("ERROR: Superó el máximo de proyectiles enemigos");
		} else {
			System.out.println("OK: No se supera el máximo de proyectiles enemigos.");
		}

        // 3) Reducir naves a <=3 y verificar cooldown más lento
        System.out.println("-- Test cooldown dinámico cuando quedan pocas naves --");
        Oleada ole = p.getOleada();
        List<NaveInvasora> naves = ole.getNaves();
        // Matar naves hasta que queden 3
        int vivas = 0;
        for (NaveInvasora n : naves) {
			if (n.isViva()) {
				vivas++;
			}
		}
        System.out.println("Naves vivas iniciales: " + vivas);
        int toKill = vivas - 3;
        int killed = 0;
        for (NaveInvasora n : naves) {
            if (killed >= toKill) {
				break;
			}
            if (n.isViva()) { n.morir(); killed++; }
        }
        System.out.println("Naves muertas para test: " + killed);
        // Eliminar proyectiles enemigos existentes para aislar la prueba de cooldown
        System.out.println("Eliminando proyectiles enemigos existentes para aislar cooldown...");
        p.getProyectiles().removeIf(pr -> !pr.isAliado());

        // esperar un tiempo menor al low cooldown pero mayor al base cooldown
        System.out.println("Intentando disparo tras " + 1600 + "ms (debe NO disparar si cooldown=2500ms)...");
        Thread.sleep(1600);
        p.actualizarLogica();
        int enemAfter = countEnemigos(p);
        System.out.println("Proyectiles enemigos: " + enemAfter + " (no debería haber aumentado si cooldown es mayor)");
        // ahora esperar tiempo extra hasta superar lowNavesCooldownMs
        System.out.println("Esperando 2600ms y volviendo a intentar (debe poder disparar)...");
        Thread.sleep(2600);
        p.actualizarLogica();
        int enemAfter2 = countEnemigos(p);
        System.out.println("Proyectiles enemigos tras esperar: " + enemAfter2);

        System.out.println("Test finalizado.");
    }

    private static int countAliados(Partida p) {
        int c = 0;
        for (Proyectil pr : p.getProyectiles()) {
            if (pr.isActivo() && pr.isAliado()) {
				c++;
			}
        }
        return c;
    }

    private static int countEnemigos(Partida p) {
        int c = 0;
        for (Proyectil pr : p.getProyectiles()) {
            if (pr.isActivo() && !pr.isAliado()) {
				c++;
			}
        }
        return c;
    }
}