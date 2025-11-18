package views;

public class EntradaRankingView {
    private String nombre;
	private int puntuacion;

	public EntradaRankingView(String nombre, int puntuacion) {
		this.nombre = nombre;
		this.puntuacion = puntuacion;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	public void setPuntuacion(int p) {
		this.puntuacion = p;
	}
    
}
