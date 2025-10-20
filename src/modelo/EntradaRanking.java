package modelo;

public class EntradaRanking {
    private String nombre;
    private int puntuacion;

    public EntradaRanking(String nombre, int puntuacion) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public String getNombre() { return nombre; }
    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int p) { this.puntuacion = p; }
}