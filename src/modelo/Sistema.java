package modelo;

import java.util.ArrayList;
import java.util.List;

import views.DificultadView;

public class Sistema {
	private static Sistema instancia;
	private int creditosCargados;
	private int creditosDisponibles;
	private Ranking ranking;
	private Dificultad dificultad;
	private List<Dificultad> dificultades;

	public Sistema() {
		this.creditosCargados = 0;
		this.creditosDisponibles = 0;
		this.ranking = new Ranking();
		dificultades = new ArrayList<>();
		dificultades.add(new Dificultad("Cadete", 0.0));
        dificultades.add(new Dificultad("Guerrero", 0.35));
        dificultades.add(new Dificultad("Master", 0.70));
		dificultad = dificultades.get(0);
	}

	public static Sistema getInstancia(){
		if(instancia == null){
			instancia = new Sistema();
		}
		return instancia;
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
	public int getCreditosDisponibles() {
		return creditosDisponibles;
	}

	public int getCreditosCargados() {
		return creditosCargados;
	}

	public Ranking getRanking() {
		return ranking;
	}

	public DificultadView getDificultad() {
		return dificultad.toView();
	}

	public List<DificultadView> obtenerDificultadesPredefinidas() {
        List<DificultadView> lista = new ArrayList<>();
        for (Dificultad d : dificultades) {
            lista.add(d.toView());
        }
        return lista;
    }
}