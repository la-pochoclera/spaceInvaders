package modelo;

import views.DificultadView;

public class Dificultad {
    private String etiqueta;
    private double incrementoVelocidad;

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

    public int aplicarIncremento(int velocidadBase) {
        double ajustada = velocidadBase * (1.0 + incrementoVelocidad);
        return Math.max(1, (int) Math.ceil(ajustada));
    }

    public DificultadView toView() {
        return new DificultadView(etiqueta, incrementoVelocidad);
    }
}