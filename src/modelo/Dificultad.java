package modelo;

import java.util.ArrayList;
import java.util.List;

public class Dificultad {
    public static final Dificultad CADETE   = new Dificultad("Cadete",   0.0);
    public static final Dificultad GUERRERO = new Dificultad("Guerrero", 0.35);
    public static final Dificultad MASTER   = new Dificultad("Master",   0.70);

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
        List<Dificultad> lista = new ArrayList<>();
        lista.add(CADETE);
        lista.add(GUERRERO);
        lista.add(MASTER);
        return lista;
    }

    public int aplicarIncremento(int velocidadBase) {
        double ajustada = velocidadBase * (1.0 + incrementoVelocidad);
        return Math.max(1, (int) Math.ceil(ajustada));
    }
}