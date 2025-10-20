package modelo;

import modelo.Ranking;

public class Sistema {
    private int creditosCargados;
    private int creditosDisponibles;
    private Ranking ranking;

    public Sistema() {
        this.creditosCargados = 0;
        this.creditosDisponibles = 0;
        this.ranking = new Ranking();
    }

    public void cargarCreditos(int cantidad) {
        if (cantidad > 0) {
            this.creditosCargados += cantidad;
            this.creditosDisponibles += cantidad;
        }
    }

    public boolean tieneCreditos() {
        return creditosDisponibles > 0;
    }

    public void consumirCredito() {
        if (tieneCreditos()) {
            creditosDisponibles--;
        }
    }

    public void reintegrarCreditos() {
        this.creditosDisponibles = this.creditosCargados;
        this.creditosCargados = 0;
    }

    public void actualizarRanking(String nombre, int puntuacion) {
        ranking.agregarOActualizarEntrada(nombre, puntuacion);
    }

    // Getters
    public int getCreditosDisponibles() { return creditosDisponibles; }
    public int getCreditosCargados() { return creditosCargados; }
    public Ranking getRanking() { return ranking; }
}