package modelo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Representa un nivel de dificultad configurable y reutilizable dentro del juego.
 * Se proveen instancias predefinidas, pero la clase permite extenderse si hiciera falta.
 */
public class Dificultad {
    public static final Dificultad CADETE = new Dificultad("Cadete", 0.0);
    public static final Dificultad GUERRERO = new Dificultad("Guerrero", 0.35);
    public static final Dificultad MASTER = new Dificultad("Master", 0.7);

    private static final List<Dificultad> PREDEFINIDAS = Collections.unmodifiableList(
        Arrays.asList(CADETE, GUERRERO, MASTER));

    private final String etiqueta;
    private final double incrementoVelocidad;

    public Dificultad(String etiqueta, double incrementoVelocidad) {
        this.etiqueta = etiqueta;
        this.incrementoVelocidad = incrementoVelocidad;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public double getIncrementoVelocidad() {
        return incrementoVelocidad;
    }

    public static List<Dificultad> obtenerPredefinidas() {
        return PREDEFINIDAS;
    }

    public static Dificultad desdeEtiqueta(String etiquetaBuscada) {
        if (etiquetaBuscada == null) {
			return null;
		}
        for (Dificultad dificultad : PREDEFINIDAS) {
            if (dificultad.etiqueta.equalsIgnoreCase(etiquetaBuscada)) {
				return dificultad;
			}
        }
        return null;
    }

    /**
     * Calcula la velocidad ajustada según el incremento configurado.
     * @param velocidadBase velocidad original a ajustar.
     * @return velocidad resultante redondeada y con un mínimo de 1.
     */
    public int aplicarIncremento(int velocidadBase) {
        double ajustada = velocidadBase * (1.0 + incrementoVelocidad);
        return Math.max(1, (int) Math.ceil(ajustada));
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
